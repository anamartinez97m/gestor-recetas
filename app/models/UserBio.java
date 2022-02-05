package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import io.ebean.Finder;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;

import java.sql.Timestamp;
import java.util.List;


@Entity //javax
public class UserBio extends Model { //io.ebean.Model

    private static final Finder<Long, User> finder = new Finder<Long, User>(User.class);
    
    @Id
    private Long id;

    private String texto;

    @Version
    private Long version;

    @WhenCreated
    private Timestamp whenCreated; //sql

    @WhenModified
    // @JsonIgnore
    private Timestamp whenModified;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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