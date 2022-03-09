package controllers;

import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import errors.NotFound;
import models.Rating;
import models.Recipe;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.Http;

public class RatingController extends Controller {

    @Inject
    private FormFactory formFactory;

    @Inject
    MessagesApi messagesApi;

    public Result addRating(Http.Request request) {
        Messages messages = messagesApi.preferred(request);
        Form<Rating> ratingForm = formFactory.form(Rating.class).bindFromRequest(request);
        
        if(ratingForm.hasErrors()) {
            JsonNode errors = ratingForm.errorsAsJson();
            return Results.notAcceptable(errors);
        }

        Rating rating = ratingForm.get();
        JsonNode jsonNode = request.body().asJson();
        Long recipeId = jsonNode.get("recipeId").asLong();

        Recipe recipe = Recipe.findById(recipeId);

        if(recipe == null) {
            return notFound(
                Json.toJson(
                    new NotFound(
                        messages.at("recipe-error-not-found", recipeId.toString())
                    )
                )
            );
        }

        rating.save();
        
        recipe.setRating(rating);
        recipe.save();

        ObjectNode node = (ObjectNode) Json.toJson(rating);
        node.put("recipeId", recipeId);
        
        if(request.accepts("application/xml"))
            return created(views.xml.rating.render(rating));
        
        return created(node).as("application/json");
    }

    public Result getAllRatings(Http.Request request) {
        List<Rating> ratings = Rating.findAll();

        if(request.accepts("application/xml")) {
            return Results.ok(views.xml.ratings.render(ratings));
        } else if(request.accepts("application/json")) {
            return ok(Json.toJson(ratings));
        } else {
            ObjectNode result = Json.newObject();
            result.put("error", "Unssupported format");
            return Results.status(406, result);
        }
    }

    public Result updateRating(Http.Request request, Long id) {
        JsonNode jsonNode = request.body().asJson();
        JsonNode commentNode = jsonNode.get("comment");
        JsonNode valueNode = jsonNode.get("value");

        Rating rating = Rating.findById(id);
        
        if(rating != null) {
            if(commentNode != null)
                rating.setComment(commentNode.toString());

            if(valueNode != null)
                rating.setValue(valueNode.doubleValue());

            rating.save();               
            JsonNode node = Json.toJson(rating);
            if(request.accepts("application/xml"))
                return Results.ok(views.xml.rating.render(rating));
            
            return ok(node).as("application/json");
        } else {
            return notFound();
        }
    }

    public Result deleteRating(Double value) {
        Rating rating = Rating.findByValue(value);
    
        if(rating != null) {
            Recipe recipe = Recipe.findByRating(rating);
            if(recipe != null) {
                recipe.setRating(null);
                recipe.save();
            }
            rating.delete();
            return Results.status(204);
        } else {
            return notFound();
        }
    }
}
