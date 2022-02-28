package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import io.ebean.Finder;
import play.data.validation.Constraints.Required;

@Entity
public class Ingredient extends BaseModel {
    /**
     * Class attributes
     */
    private static final Finder<Long, Ingredient> finder = new Finder<Long, Ingredient>(Ingredient.class);

    @Required(message="ingredient-name-required")
    private String name;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="ingredient")
    private List<IngredientQuantity> ingredientQuantityList;

    /**
     * Getters and Setters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Equals
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof Ingredient) {
            Ingredient other = (Ingredient) obj;

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
     * toString
     */
    @Override
    public String toString() {
        return "Ingredient [name=" + name + "]";
    }
    

    /**
     * Class finders
     */
    public static Ingredient findById(Long id) {
        return finder.byId(id);
    }

    public static List<Ingredient> findByName(String name) {
        return finder.query().where().eq("name", name).findList();
    }    

    public static List<Ingredient> findAll() {
        return finder.query().findList();
    } 
    
}
