package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import io.ebean.Finder;

// TODO: falla la compilacion, dice que javax no tiene persistence dentro
@Entity
public class Recipe extends BaseModel {
    /**
     * Class attributes
     */
    private static final Finder<Long, Recipe> finder = new Finder<Long, Recipe>(Recipe.class);

    private String name;

    private String stepsDescription;

    @OneToOne(cascade=CascadeType.ALL)
    private Rating rating;

    @ManyToOne
    private Difficulty levelDifficulty;

    @ManyToMany(cascade=CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();


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

    public Difficulty getLevelDifficulty() {
        return levelDifficulty;
    }

    public void setLevelDifficulty(Difficulty levelDifficulty) {
        this.levelDifficulty = levelDifficulty;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
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
    public static Recipe findUserbyId(Long id) {
        return finder.byId(id);
    }

    public static List<Recipe> findByName(String name) {
        return finder.query().where().eq("name", name).findList();
    }

    public static List<Recipe> findByIngredients(List<Ingredient> ingredients) {
        return finder.query().where().in("ingredients", ingredients).findList();
    }
}

// TODO:
/**
 * Book libro = new Book();
 * Page pagina = new Page();
 * libro.addPagina(pagina)
 * libro.save();
 * public void addPagina(Pagina p) {
 *  this.paginas.add(p);
 *  p.libro = this;
 * }
 */
