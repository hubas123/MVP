package nz.co.udenbrothers.clockwork.models.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 *
 */

@Entity
public class UserItem {
    @Id
    private Long id;

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    public int active;


    @Generated(hash = 1211112315)
    public UserItem(Long id, String userId, String firstName, String lastName,
            String email, int active) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.active = active;
    }

    @Generated(hash = 402134942)
    public UserItem() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getActive() {
        return this.active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public UserItem (String uid, String fn, String ln, String emai){
        userId = uid;
        firstName = fn;
        lastName = ln;
        email = emai;
        active = 0;
    }


}
