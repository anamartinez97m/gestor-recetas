package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class IngredientQuantity {
    /**
     * Class attributes
     */

    @Id
    private Long id;

    private Ingredient ingredient;

    private Float quantity;

    /**
     * Getters and Setters
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }
    
}
