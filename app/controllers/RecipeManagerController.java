package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

        return created(node).as("application/json");
    }

    public Result getRecipes(Http.Request request) {
        Map<String, String[]> queryParamsMap = request.queryString();
        String[] id = queryParamsMap.get("id");
        String[] difficulty = queryParamsMap.get("difficulty");
        String[] rating = queryParamsMap.get("rating");
        String[] ingredients = queryParamsMap.get("ingredient");
        List<Recipe> recipes = new ArrayList<Recipe>();
        // List<Recipe> recipesResult = new ArrayList<Recipe>();

        if(id == null && difficulty == null && rating == null && ingredients == null)
            recipes = Recipe.findAll();

        // TODO: juntar los filtros

        if(id != null) {
            for(int i = 0; i < id.length; i++) {
                //recipes = recipes.stream().filter(recipe -> recipe.getId() == Long.parseLong(id[i])).collect(Collectors.toList());
                
                recipes.stream()
                    .filter(r -> r.getId() == Long.parseLong(id[i]))
                    .collect(Collectors.toList());

                for(Recipe r: recipes)
                    System.out.println(r.toString());

                // for(Recipe r: recipes) {
                //     if(r.getId() == Long.parseLong(id[i])) 
                //         recipesResult.add(r);
                // }

                // recipes.add(Recipe.findById(Long.parseLong(id[i])));
            }
        }

        if(difficulty != null) {
            for(int i = 0; i < difficulty.length; i++) {
                recipes.addAll(Recipe.findByDifficultyValue(Integer.parseInt(difficulty[i])));
            }
        }

        if(rating != null) {
            for(int i = 0; i < rating.length; i++) {
                recipes.add(Recipe.findByRatingValue(Float.parseFloat(rating[i])));
            }
        }

        if(ingredients != null) {
            for(int i = 0; i < ingredients.length; i++) {
                List<IngredientQuantity> iqListFound = new ArrayList<IngredientQuantity>();
                List<Ingredient> ingredientsFound = Ingredient.findByName(ingredients[i]);
                
                for(Ingredient ing: ingredientsFound)
                    iqListFound.addAll(IngredientQuantity.findByIngredient(ing));

                for(IngredientQuantity iq: iqListFound) 
                    recipes.addAll(Recipe.findByIngredientQuantity(iq));
            }
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

    public Result updateRecipe(Http.Request request, Long id) {
        JsonNode jsonNode = request.body().asJson();
        JsonNode nameNode = jsonNode.get("name");
        JsonNode stepsDescriptionNode = jsonNode.get("stepsDescription");
        JsonNode difficultyValueNode = jsonNode.get("difficultyValue");

        Recipe recipe = Recipe.findById(id);
        
        if(recipe != null) {
            if(nameNode != null)
                recipe.setName(nameNode.toString());

            if(stepsDescriptionNode != null)
                recipe.setStepsDescription(stepsDescriptionNode.toString());

            if(difficultyValueNode != null) {
                Difficulty difficulty = Difficulty.findByValue(difficultyValueNode.asInt());
                recipe.setDifficulty(difficulty);
            }

            recipe.save();               
            JsonNode node = Json.toJson(recipe);
            return ok(node).as("application/json");
        } else {
            return notFound();
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

    // TODO añadir actualizar
}
