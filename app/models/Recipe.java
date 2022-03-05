package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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

    /**
     * Class finders
     */
    public static Recipe findById(Long id) {
        return finder.byId(id);
    }

    public static List<Recipe> findByName(String name) {
        return finder.query().where().eq("name", name).findList();
    }

    public static List<Recipe> findByIngredients(List<Ingredient> ingredients) {
        return finder.query().where().in("ingredients", ingredients).findList();
    }

    public static List<Recipe> findAll() {
        return finder.query().findList();
    }
}
