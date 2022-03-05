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
        
        // TODO Devolver error en JSON o XML según te manden en el header Accept
        if(ratingForm.hasErrors()) {
            JsonNode errors = ratingForm.errorsAsJson();
            return Results.notAcceptable(errors);
        }

        Rating rating = ratingForm.get();
        JsonNode jsonNode = request.body().asJson();
        Long recipeId = jsonNode.get("recipeId").asLong();

        Recipe recipe = Recipe.findById(recipeId);

        // TODO Devolver error en JSON o XML según te manden en el header Accept
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

    public Result deleteRating(Float value) {
        Rating rating = Rating.findByValue(value);
    
        // TODO Devolver error en JSON o XML según te manden en el header Accept
        if(rating != null) {
            Recipe recipe = Recipe.findByRating(rating);
            recipe.setRating(null);
            recipe.save();
            rating.delete();
            return Results.status(204);
        } else {
            return notFound();
        }
    }
}
