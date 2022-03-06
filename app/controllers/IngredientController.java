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

        if(ingredientForm.hasErrors()) {
            JsonNode errors = ingredientForm.errorsAsJson();
            return Results.notAcceptable(errors);
        }

        Ingredient ingredient = ingredientForm.get();
        
        ingredient.save();

        JsonNode node = Json.toJson(ingredient);

        if(request.accepts("application/xml"))
            return created(views.xml.ingredient.render(ingredient));

        return created(node).as("application/json");
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

    public Result updateIngredient(Http.Request request, Long id) {
        JsonNode jsonNode = request.body().asJson();
        JsonNode nameNode = jsonNode.get("name");

        Ingredient ingredient = Ingredient.findById(id);
        
        if(ingredient != null) {
            if(nameNode != null)
                ingredient.setName(nameNode.toString());

            ingredient.save();
            JsonNode node = Json.toJson(ingredient);

            if(request.accepts("application/xml"))
                return Results.ok(views.xml.ingredient.render(ingredient));

            return ok(node).as("application/json");
        } else {
            return notFound();
        }
    }

    public Result deleteIngredient(Long id) {
        Ingredient ingredient = Ingredient.findById(id);
    
        if(ingredient != null) {
            List<IngredientQuantity> iqList = IngredientQuantity.findByIngredient(ingredient);
            List<Recipe> recipes = new ArrayList<Recipe>();

            for(IngredientQuantity iq: iqList) {
                recipes = Recipe.findByIngredientQuantity(iq);            
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
