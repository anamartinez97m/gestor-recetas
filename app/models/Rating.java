package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.ebean.Finder;
import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.Required;

@Entity
public class Rating extends BaseModel {
    /**
     * Class attributes
     */
    private static final Finder<Long, Rating> finder = new Finder<Long, Rating>(Rating.class);

    @Required(message="rating-value-required")
    @Min(0)
    @Max(5)
    private Double value;

    private String comment;

    /*
    Se está dando por hecho que una receta sólo va a poder tener un rating hecho por una persona
    */
    @JsonIgnore
    @OneToOne(mappedBy="rating")
    private Recipe recipe;


    /**
     * Getters and Setters
     */
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }


    /**
     * Equals
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof Rating) {
            Rating other = (Rating) obj;

            if(other.getValue() == null) {
                return false;
            } else {
                return other.getValue().equals(this.getValue()) && other.getComment().equals(this.getComment());
            }
        } else {
            return false;
        }
    }

    /**
     * Class finders
     */
    public static Rating findById(Long id) {
        return finder.byId(id);
    }

    public static Rating findByValue(Double value) {
        return finder.query().where().eq("value", value).findOne();
    }

    public static List<Rating> findAll() {
        return finder.query().findList();
    } 
}
