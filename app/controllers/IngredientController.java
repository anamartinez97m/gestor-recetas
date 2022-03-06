package controllers;

import play.mvc.Result;
import play.mvc.Results;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Ingredient;
import models.IngredientQuantity;
import models.Recipe;
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

        // TODO Devolver error en JSON o XML según te manden en el header Accept
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
            return Results.ok(views.xml.ingredients.render(ingredients));
        } else if(request.accepts("application/json")) {
            return ok(Json.toJson(ingredients));
        } else {
            ObjectNode result = Json.newObject();
            result.put("error", "Unssupported format");
            return Results.status(406, result);
        }
    }

    // TODO UPDATE INGREDIENT

    public Result deleteIngredient(Long id) {
        Ingredient ingredient = Ingredient.findById(id);
    
        // TODO Devolver error en JSON o XML según te manden en el header Accept
        if(ingredient != null) {
            List<IngredientQuantity> iqList = IngredientQuantity.findByIngredient(ingredient);
            List<Recipe> recipes = new ArrayList<Recipe>();

            for(IngredientQuantity iq: iqList) {
                recipes = Recipe.findByIngredientQuantity(iq);            
                // TODO Devolver error en JSON o XML según te manden en el header Accept
                if(recipes != null) {
                    for(Recipe r: recipes) {
                        r.getIngredientsQuantityList().remove(iq);
                        r.save();
                    }
                    iq.delete();
                }
            }
            ingredient.delete();
            return Results.status(204);
        } else {
            return notFound();
        }
    }
  
}
