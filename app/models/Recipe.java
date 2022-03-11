package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.*;

import io.ebean.ExpressionList;
import io.ebean.Finder;
import play.data.validation.Constraints.Required;

@Entity
public class Recipe extends BaseModel {
    /**
     * Class attributes
     */
    private static final Finder<Long, Recipe> finder = new Finder<Long, Recipe>(Recipe.class);

    @Required(message="recipe-name-required")
    private String name;

    @Required(message="recipe-description-required")
    private String stepsDescription;

    @OneToOne(cascade=CascadeType.ALL)
    private Rating rating;

    @ManyToOne
    private Difficulty difficulty;

    @Required(message="recipe-ingredients-required")
    @ManyToMany(fetch = FetchType.LAZY)
    private List<IngredientQuantity> ingredientsQuantityList = new ArrayList<IngredientQuantity>();


    /**
     * Getters and Setters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStepsDescription() {
        return stepsDescription;
    }

    public void setStepsDescription(String stepsDescription) {
        this.stepsDescription = stepsDescription;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<IngredientQuantity> getIngredientsQuantityList() {
        return ingredientsQuantityList;
    }

    public void setIngredientsQuantityList(List<IngredientQuantity> ingredientsQuantityList) {
        this.ingredientsQuantityList = ingredientsQuantityList;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    /**
     * Equals
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof Recipe) {
            Recipe other = (Recipe) obj;

            if(other.getName() == null) {
                return false;
            } else {
                return other.getName().equals(this.getName());
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Recipe [difficulty=" + difficulty + ", ingredientsQuantityList=" + ingredientsQuantityList + ", name="
                + name + ", rating=" + rating + ", stepsDescription=" + stepsDescription + "]";
    }

    /**
     * Class finders
     */
    public static Recipe findById(Long id) {
        return finder.byId(id);
    }

    public static List<Recipe> findAll() {
        return finder.query().findList();
    }

    public static List<Recipe> findByName(String name) {
        return finder.query().where().eq("name", name).findList();
    }

    public static List<Recipe> findByDifficultyValues(String[] values) {
        List<String> list = Arrays.asList(values);
        return finder.query().where().in("difficulty.value", list).findList();
    }

    public static Recipe findByRating(Rating rating) {
        return finder.query().where().eq("rating", rating).findOne();
    }
    
    public static List<Recipe> findByRatingValues(String[] values) {
        List<String> list = Arrays.asList(values);
        List<Double> valueList = new ArrayList<Double>();
        for(String elem : list) {
            valueList.add(Double.parseDouble(elem));
        }

        return finder.query().where().in("rating.value", valueList).findList();
    }

    public static List<Recipe> findByDifficultyAndRatingValuesAndIngredients(
        String[] difficulties, String[] ratings, String[] ingredients) {
        List<String> difficultiesList = new ArrayList<String>();
        List<Double> ratingsList = new ArrayList<Double>();
        ExpressionList<Recipe> composedFinder = finder.query().where();

        if(difficulties != null) {
            difficultiesList = Arrays.asList(difficulties);
            composedFinder.in("difficulty.value", difficultiesList);
        }

        if(ratings != null) {
            List<String> list = Arrays.asList(ratings);
            for(String elem : list) {
                ratingsList.add(Double.parseDouble(elem));
            }
            composedFinder.and().in("rating.value", ratingsList);
        }

        if(ingredients != null) {
            List<Long> recipeIds = new ArrayList<Long>();
            for(int i = 0; i < ingredients.length; i++) {
                List<IngredientQuantity> iqListFound = new ArrayList<IngredientQuantity>();
                List<Ingredient> ingredientsFound = Ingredient.findByName(ingredients[i]);
                
                for(Ingredient ing: ingredientsFound)
                    iqListFound.addAll(IngredientQuantity.findByIngredient(ing));
                
                for(IngredientQuantity iq: iqListFound) 
                recipeIds.addAll(Recipe.findRecipeIdsByIngredientQuantity(iq));

                composedFinder.and().in("id", recipeIds);
                
            }
        }
        
        return composedFinder.findList();
    }

    public static List<Recipe> findByIngredientQuantity(IngredientQuantity iq) {
        return finder.query().where().eq("ingredientsQuantityList.id", iq.getId()).findList();
    }

    public static List<Long> findRecipeIdsByIngredientQuantity(IngredientQuantity iq) {
        return finder.query().where().eq("ingredientsQuantityList.id", iq.getId()).findIds();
    }
}
