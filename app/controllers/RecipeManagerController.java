package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import errors.*;
import models.Difficulty;
import models.Ingredient;
import models.IngredientQuantity;
import models.Rating;
import models.Recipe;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

public class RecipeManagerController extends Controller {

    @Inject
    private FormFactory formFactory;
  
    public Result createRecipe(Http.Request request) {
        Form<Recipe> recipeForm = formFactory.form(Recipe.class).bindFromRequest(request);

        if(recipeForm.hasErrors()) {
            JsonNode errors = recipeForm.errorsAsJson();
            return Results.notAcceptable(errors);
        }

        List<IngredientQuantity> ingredientsQuantityList = new ArrayList<IngredientQuantity>();
        
        JsonNode jsonNode = request.body().asJson();
        
        for(int i = 0; i < jsonNode.get("ingredientsQuantityList").size(); i++) {
            IngredientQuantity iq = new IngredientQuantity();
            JsonNode iqNode = jsonNode.get("ingredientsQuantityList").get(i);

            Long ingredientId = iqNode.get("ingredientId").asLong();

            Ingredient foundIngredient = Ingredient.findById(ingredientId);

            if(foundIngredient == null) {
                // TODO: Traducción
                return notFound(Json.toJson(new NotFound("Ingredient with id " + ingredientId + " could not be found")));
            }

            iq.setIngredient(foundIngredient);
            iq.setQuantity(iqNode.get("quantity").floatValue());

            ingredientsQuantityList.add(iq);
            iq.save();
        }

        Integer difficultyValue = jsonNode.get("difficultyValue").asInt();

        Difficulty difficulty = Difficulty.findByValue(difficultyValue);

        if(difficulty == null) {
            // TODO: Traducción
            return notFound(Json.toJson(new NotFound("Difficulty with value " + difficultyValue + " could not be found")));
        }
        
        Recipe recipe = recipeForm.get();

        recipe.setIngredientsQuantityList(ingredientsQuantityList);
        recipe.setDifficulty(difficulty);

        recipe.save();

        JsonNode node = Json.toJson(recipe);

        return created(node)
                .as("application/json");
    }

    public Result getRecipeById(Long id) {
        return ok("Return of the recipe: " + id + "\n");
    }

    public Result getRecipeByIngredients(List<Ingredient> ingredients) {
        return ok("Return of the recipes with the ingredients: " + ingredients.toString() + "\n");
    }

    public Result getRecipesByDifficulty(Difficulty difficulty) {
        return ok("Return of the recipes with difficulty: " + difficulty.getValue() + "\n");
    }

    public Result getRecipesByRating(Rating rating) {
        return ok("Return of the recipes with rating: " + rating.getValue() + "\n");
    }

    public Result getRecipesByFilter(Http.Request request) {
        Map<String, String[]> queryParamsMap = request.queryString();
        String[] difficulty = queryParamsMap.get("difficulty");
        String[] rating = queryParamsMap.get("rating");
        String[] ingredients = queryParamsMap.get("ingredients");
        List<Recipe> recipes;

        if(difficulty != null) {
            return ok("Return of the recipes filtered by difficulty " + Arrays.toString(difficulty) + "\n");
        }
        
        if(rating != null) {
            return ok("Return of the recipes filtered by rating " + Arrays.toString(rating) + "\n");
        }

        if(ingredients != null) {
            return ok("Return of the recipes filtered by ingredients " + Arrays.toString(ingredients) + "\n");
        }

        recipes = Recipe.findAll();

        if(request.accepts("application/xml")) {
            return Results.ok(views.xml.recipe.render(recipes));
        } else if(request.accepts("application/json")) {
            return ok(Json.toJson(recipes));
        } else {
            ObjectNode result = Json.newObject();
            result.put("error", "Unssupported format");
            return Results.status(406, result);
        }
    }

}
