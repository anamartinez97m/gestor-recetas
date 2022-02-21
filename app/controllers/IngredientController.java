package controllers;

import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

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

        return ok   (node)
                // .withHeader("mi-cabecera", "mi-valor")
                .as("application/json");
    }

    public Result getAllIngredients() {
        return ok("Return of all the ingredients\n");
    }
  
}
