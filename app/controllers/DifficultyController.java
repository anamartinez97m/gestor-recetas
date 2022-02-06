package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class DifficultyController extends Controller {

    public Result addDifficulty() {
        return ok("Difficulty created!\n");
    }

    public Result getAllDifficulties() {
        return ok("Return of all the difficulties\n");
    }
}
