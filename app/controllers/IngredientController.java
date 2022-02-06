package controllers;

import play.mvc.Result;
import play.mvc.Controller;

public class IngredientController extends Controller {

    public Result createIngredient() {
        return ok("Created ingredient!\n");
    }

    public Result getAllIngredients() {
        return ok("Return of all the ingredients\n");
    }
  
}
