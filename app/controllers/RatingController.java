package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class RatingController extends Controller {

    public Result addRating() {
        return ok("Rating created!\n");
    }

    public Result getAllRatings() {
        return ok("Return of all the ratings\n");
    }
}
