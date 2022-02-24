package controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Difficulty;
import models.Ingredient;
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

        Recipe recipe = recipeForm.get();
        
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

        if(difficulty != null) {
            return ok("Return of the recipes filtered by difficulty " + Arrays.toString(difficulty) + "\n");
        }
        
        if(rating != null) {
            return ok("Return of the recipes filtered by rating " + Arrays.toString(rating) + "\n");
        }

        if(ingredients != null) {
            return ok("Return of the recipes filtered by ingredients " + Arrays.toString(ingredients) + "\n");
        }

        return ok("Return of all the recipes\n");
    }

}
