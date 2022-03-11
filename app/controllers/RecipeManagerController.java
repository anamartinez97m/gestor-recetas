package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
                return notFound(
                    Json.toJson(
                        new NotFound(
                            messages.at("ingredient-error-not-found", ingredientId.toString())
                        )
                    )
                );
            }

            iq.setIngredient(foundIngredient);
            iq.setQuantity(iqNode.get("quantity").doubleValue());

            ingredientsQuantityList.add(iq);
            iq.save();
        }

        Integer difficultyValue = jsonNode.get("difficultyValue").asInt();

        Difficulty difficulty = Difficulty.findByValue(difficultyValue);

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

        if(request.accepts("application/xml"))
            return created(views.xml.recipe.render(recipe));

        return created(node).as("application/json");
    }

    public Result getRecipes(Http.Request request) {
        Map<String, String[]> queryParamsMap = request.queryString();
        String[] difficulties = queryParamsMap.get("difficulty");
        String[] ratings = queryParamsMap.get("rating");
        String[] ingredients = queryParamsMap.get("ingredient");
        List<Recipe> recipes = new ArrayList<Recipe>();

        if(difficulties == null && ratings == null && ingredients == null)
            recipes = Recipe.findAll();
        else
            recipes = Recipe.findByDifficultyAndRatingValuesAndIngredients(difficulties, ratings, ingredients);

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

    public Result updateRecipe(Http.Request request, Long id) {
        JsonNode jsonNode = request.body().asJson();
        JsonNode nameNode = jsonNode.get("name");
        JsonNode stepsDescriptionNode = jsonNode.get("stepsDescription");
        JsonNode difficultyValueNode = jsonNode.get("difficultyValue");
        JsonNode iqListNode = jsonNode.get("ingredientsQuantityList");

        Recipe recipe = Recipe.findById(id);
        
        if(recipe != null) {
            if(nameNode != null)
                recipe.setName(nameNode.asText());

            if(stepsDescriptionNode != null)
                recipe.setStepsDescription(stepsDescriptionNode.toString());

            if(difficultyValueNode != null) {
                Difficulty difficulty = Difficulty.findByValue(difficultyValueNode.asInt());
                recipe.setDifficulty(difficulty);
            }

            if(iqListNode != null) {
                List<IngredientQuantity> iqList = new ArrayList<IngredientQuantity>();
                for(JsonNode node: iqListNode) {
                    IngredientQuantity iq = new IngredientQuantity();
                    Ingredient i = Ingredient.findById(node.get("ingredientId").asLong());
                    Double quantity = node.get("quantity").doubleValue();
                    iq.setId(node.get("id").asLong());
                    iq.setIngredient(i);
                    iq.setQuantity(quantity);

                    iqList.add(iq);
                }

                recipe.setIngredientsQuantityList(iqList);
            }

            recipe.save();               
            JsonNode node = Json.toJson(recipe);

            if(request.accepts("application/xml"))
                return Results.ok(views.xml.recipe.render(recipe));

            return ok(node).as("application/json");
        } else {
            return notFound();
        }
    }

    public Result deleteRecipe(Long id) {
        Recipe recipe = Recipe.findById(id);
        if(recipe != null) {
            recipe.delete();
            return Results.status(204);
        } else {
            return notFound();
        }
    }
}
