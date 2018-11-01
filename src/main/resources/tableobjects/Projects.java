package tableobjects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Projects {
    private Integer id;
    private String name;
    private String description;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projects projects = (Projects) o;
        return Objects.equals(id, projects.id) &&
                Objects.equals(name, projects.name) &&
                Objects.equals(description, projects.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
