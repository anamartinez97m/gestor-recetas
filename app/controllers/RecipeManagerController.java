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
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

public class RecipeManagerController extends Controller {

    @Inject
    private FormFactory formFactory;

    @Inject
    private MessagesApi messagesApi;
  
    public Result createRecipe(Http.Request request) {
        Messages messages = messagesApi.preferred(request);
        Form<Recipe> recipeForm = formFactory.form(Recipe.class).bindFromRequest(request);

        // TODO Devolver error en JSON o XML según te manden en el header Accept
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

            // TODO Devolver error en JSON o XML según te manden en el header Accept
            if(foundIngredient == null) {
                return notFound(
                    Json.toJson(
                        new NotFound(
                            messages.at("ingredient-error-not-found", ingredientId.toString())
                        )
                    )
                );
            }

            iq.setIngredient(foundIngredient);
            iq.setQuantity(iqNode.get("quantity").floatValue());

            ingredientsQuantityList.add(iq);
            iq.save();
        }

        Integer difficultyValue = jsonNode.get("difficultyValue").asInt();

        Difficulty difficulty = Difficulty.findByValue(difficultyValue);

        // TODO Devolver error en JSON o XML según te manden en el header Accept
        if(difficulty == null) {
            return notFound(
                Json.toJson(
                    new NotFound(
                        messages.at("difficulty-error-not-found", difficultyValue.toString())
                    )
                )
            );
        }
        
        Recipe recipe = recipeForm.get();

        recipe.setIngredientsQuantityList(ingredientsQuantityList);
        recipe.setDifficulty(difficulty);

        recipe.save();

        JsonNode node = Json.toJson(recipe);

        return created(node)
                .as("application/json");
    }

    public Result getRecipes(Http.Request request) {
        Map<String, String[]> queryParamsMap = request.queryString();
        String[] id = queryParamsMap.get("id");
        String[] difficulty = queryParamsMap.get("difficulty");
        String[] rating = queryParamsMap.get("rating");
        String[] ingredients = queryParamsMap.get("ingredients");
        List<Recipe> recipes = new ArrayList<Recipe>();

        if(id != null) {
            for(int i = 0; i < id.length; i++) {
                recipes.add(Recipe.findById(Long.parseLong(id[i])));
            }
        } else if(difficulty != null) {
            return ok("Return of the recipes filtered by difficulty " + Arrays.toString(difficulty) + "\n");
        } else if(rating != null) {
            return ok("Return of the recipes filtered by rating " + Arrays.toString(rating) + "\n");
        } else if(ingredients != null) {
            return ok("Return of the recipes filtered by ingredients " + Arrays.toString(ingredients) + "\n");
        } else {
            recipes = Recipe.findAll();
        }

        if(request.accepts("application/xml")) {
            return Results.ok(views.xml.recipes.render(recipes));
        } else if(request.accepts("application/json")) {
            return ok(Json.toJson(recipes));
        } else {
            ObjectNode result = Json.newObject();
            result.put("error", "Unssupported format");
            return Results.status(406, result);
        }
    }

    public Result deleteRecipe(Long id) {
        Recipe recipe = Recipe.findById(id);
        // TODO Devolver error en JSON o XML según te manden en el header Accept
        if(recipe != null) {
            recipe.delete();
            return Results.status(204);
        } else {
            return notFound();
        }
    }

    // TODO añadir filtros y actualizar
}
