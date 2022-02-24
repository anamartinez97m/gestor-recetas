package controllers;

import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Rating;
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
        
        rating.save();

        JsonNode node = Json.toJson(rating);

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
