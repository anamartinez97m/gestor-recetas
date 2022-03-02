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
import play.libs.Json;
import play.mvc.Http;

public class RatingController extends Controller {

    @Inject
    private FormFactory formFactory;

    public Result addRating(Http.Request request) {
        Form<Rating> ratingForm = formFactory.form(Rating.class).bindFromRequest(request);

        if(ratingForm.hasErrors()) {
            JsonNode errors = ratingForm.errorsAsJson();
            return Results.notAcceptable(errors);
        }

        Rating rating = ratingForm.get();
        JsonNode jsonNode = request.body().asJson();
        Long recipeId = jsonNode.get("recipeId").asLong();

        Recipe recipe = Recipe.findById(recipeId);

        // Devolver error en JSON o XML según te manden en el header Accept
        if(recipe == null) {
            // TODO: Traducción
            return notFound(Json.toJson(new NotFound("Recipe with id " + recipeId + " could not be found")));
        }

        rating.save();
        
        recipe.setRating(rating);
        recipe.save();

        ObjectNode node = (ObjectNode) Json.toJson(rating);
        node.put("recipeId", recipeId);

        return created(node)
                .as("application/json");
    }

    public Result getAllRatings(Http.Request request) {
        List<Rating> ratings = Rating.findAll();

        if(request.accepts("application/xml")) {
            return Results.ok();
        } else if(request.accepts("application/json")) {
            return ok(Json.toJson(ratings));
        } else {
            ObjectNode result = Json.newObject();
            result.put("error", "Unssupported format");
            return Results.status(406, result);
        }
    }
}
