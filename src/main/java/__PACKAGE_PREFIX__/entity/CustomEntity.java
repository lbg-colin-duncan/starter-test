package __PACKAGE_PREFIX__.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class CustomEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @Column(name = "name")
    String name;

    public CustomEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomEntity customEntity = (CustomEntity) o;
        return Objects.equals(id, customEntity.id) &&
                Objects.equals(name, customEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CustomEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
