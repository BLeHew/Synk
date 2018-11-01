package tableobjects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Users {
    private Integer id;
    private String username;
    private String email;
    private Long passHash;
    private Integer privLvl;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "pass_hash")
    public Long getPassHash() {
        return passHash;
    }

    public void setPassHash(Long passHash) {
        this.passHash = passHash;
    }

    @Basic
    @Column(name = "priv_lvl")
    public Integer getPrivLvl() {
        return privLvl;
    }

    public void setPrivLvl(Integer privLvl) {
        this.privLvl = privLvl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(id, users.id) &&
                Objects.equals(username, users.username) &&
                Objects.equals(email, users.email) &&
                Objects.equals(passHash, users.passHash) &&
                Objects.equals(privLvl, users.privLvl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, passHash, privLvl);
    }
}
