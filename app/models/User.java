package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import play.data.validation.*;


@Entity
public class User extends Model {

    private static final Finder<Long, User> finder = new Finder<Long, User>(User.class);
    
    @Id
    private Long id;

    @Constraints.Required
    private String name;
    
    @Constraints.Min(value = 18, message = "error-menor-de-edad")
    // @Constraints.Max(value = 30)
    private Integer age;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private UserBio bio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentUser")
    @JsonManagedReference
    private List<UserAddress> userAddresses = new ArrayList<UserAddress>();

    @Version
    private Long version;

    @WhenCreated
    private Timestamp whenCreated;

    @WhenModified
    private Timestamp whenModified;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public UserBio getBio() {
        return bio;
    }

    public void setBio(UserBio bio) {
        this.bio = bio;
    }

    public List<UserAddress> getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(List<UserAddress> userAddresses) {
        this.userAddresses = userAddresses;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Timestamp getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Timestamp whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Timestamp getWhenModified() {
        return whenModified;
    }

    public void setWhenModified(Timestamp whenModified) {
        this.whenModified = whenModified;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if(obj instanceof User) {
            User objUser = (User) obj;

            if(objUser.getName() == null) {
                return false;
            } else {
                return objUser.getName().equals(this.getName());
            }
        } else {
            return false;
        }
    }

    public static User findUserbyId(Long id) {
        return finder.byId(id);
    }

    public static List<User> findByName(String name) {
        return finder.query().where().eq("name", name).findList();
    }
}