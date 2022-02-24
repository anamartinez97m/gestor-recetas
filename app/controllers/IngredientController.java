package controllers;

import play.mvc.Result;
import play.mvc.Results;

import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Ingredient;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;

public class IngredientController extends Controller {

    @Inject
    private FormFactory formFactory;

    public Result createIngredient(Http.Request request) {
        Form<Ingredient> ingredientForm = formFactory.form(Ingredient.class).bindFromRequest(request);

        if(ingredientForm.hasErrors()) {
            JsonNode errors = ingredientForm.errorsAsJson();
            return Results.notAcceptable(errors);
        }

        Ingredient ingredient = ingredientForm.get();
        
        ingredient.save();

        JsonNode node = Json.toJson(ingredient);

        return created(node)
                // .withHeader("mi-cabecera", "mi-valor")
                .as("application/json");
    }

    public Result getAllIngredients(Http.Request request) {
        List<Ingredient> ingredients = Ingredient.findAll();

        if(request.accepts("application/xml")) {
            
            // Content content = views.xml.user.render(userFound);
            return Results.ok();

        } else if(request.accepts("application/json")) {
            return ok(Json.toJson(ingredients));
        } else {
            ObjectNode result = Json.newObject();
            result.put("error", "Unssupported format");
            return Results.status(406, result);
        }
    }
  
}
