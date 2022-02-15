package controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import models.Difficulty;
import models.Ingredient;
import models.Rating;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
//import play.mvc.Results;

public class RecipeManagerController extends Controller {
  
    public Result createRecipe() {
        return ok("Recipe created!\n");
    }

    // public Result getAllRecipes(Http.Request request) {
    //     return ok("Return of all the recipes\n");
    // } 

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
