package controllers;

import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Difficulty;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Http;

public class DifficultyController extends Controller {

    @Inject
    private FormFactory formFactory;

    public Result addDifficulty(Http.Request request) {
        Form<Difficulty> difficultyForm = formFactory.form(Difficulty.class).bindFromRequest(request);

        if(difficultyForm.hasErrors()) {
            JsonNode errors = difficultyForm.errorsAsJson();
            return Results.notAcceptable(errors);
        }

        Difficulty difficulty = difficultyForm.get();
        
        difficulty.save();

        JsonNode node = Json.toJson(difficulty);

        return created(node)
                .as("application/json");
    }

    public Result getAllDifficulties(Http.Request request) {
        List<Difficulty> difficulties = Difficulty.findAll();

        if(request.accepts("application/xml")) {
            return Results.ok();
        } else if(request.accepts("application/json")) {
            return ok(Json.toJson(difficulties));
        } else {
            ObjectNode result = Json.newObject();
            result.put("error", "Unssupported format");
            return Results.status(406, result);
        }
    }
}
