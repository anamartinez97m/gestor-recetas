package controllers;

import play.mvc.Result;
import play.mvc.Controller;

public class RecipeManagerController extends Controller {
  
    public Result getAllRecipes() {
        return ok("Return of all the recipes\n");
    }

    public Result createRecipe() {
        return ok("Created recipe!\n");
    }

    public Result getRecipeByName(String name) {
        return ok("Return of the recipe: " + name + "\n");
    }


}
