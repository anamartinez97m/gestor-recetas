package controllers;

import java.util.List;

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

    public Result getAllRecipes(Http.Request request) {
        return ok("Return of all the recipes\n");
    } 

    public Result getRecipeByName(String name) {
        return ok("Return of the recipe: " + name + "\n");
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

}
