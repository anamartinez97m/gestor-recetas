package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import io.ebean.Finder;
import play.data.validation.Constraints.Required;

@Entity
public class Difficulty extends BaseModel {
    /**
     * Class attributes
     */
    private static final Finder<Long, Difficulty> finder = new Finder<Long, Difficulty>(Difficulty.class);

    @Required(message = "difficulty-name-required")
    private String name;

    @Required(message = "difficulty-value-required")
    private Integer value;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="levelDifficulty")
    private List<Recipe> recipes;


    /**
     * Getters and Setters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    /**
     * Equals
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof Difficulty) {
            Difficulty other = (Difficulty) obj;

            if(other.getValue() == null) {
                return false;
            } else {
                return other.getValue().equals(this.getValue());
            }
        } else {
            return false;
        }
    }

    /**
     * Class finders
     */
    public static Difficulty findUserbyId(Long id) {
        return finder.byId(id);
    }

    public static List<Difficulty> findByValue(Integer value) {
        return finder.query().where().eq("value", value).findList();
    }

    public static List<Difficulty> findAll() {
        return finder.query().findList();
    } 
}
