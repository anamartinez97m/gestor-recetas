package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class IngredientQuantity extends BaseModel {
    /**
     * Class attributes
     */

    @ManyToOne
    private Ingredient ingredient;

    private float quantity;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "ingredientsQuantityList", fetch = FetchType.LAZY)
    private List<Recipe> recipesList;

    /**
     * Getters and Setters
     */

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "IngredientQuantity [ingredient=" + ingredient + ", quantity=" + quantity + ", recipesList="
                + recipesList + "]";
    }
}
