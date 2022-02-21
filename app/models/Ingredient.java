package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

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

    @ManyToMany(mappedBy="ingredients")
    private List<Recipe> recipes = new ArrayList<Recipe>();

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
    public static Ingredient findUserbyId(Long id) {
        return finder.byId(id);
    }

    public static List<Ingredient> findByName(String name) {
        return finder.query().where().eq("name", name).findList();
    }    
    
}
