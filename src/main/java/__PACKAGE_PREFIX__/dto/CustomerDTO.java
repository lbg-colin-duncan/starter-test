package __PACKAGE_PREFIX__.dto;

import java.util.Date;
import java.util.Objects;

/**
 * Sample DTO
 */
public class CustomerDTO {

    private String id;
    private String name;
    private String description;
    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return new Date();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO sampleDTO = (CustomerDTO) o;
        return Objects.equals(id, sampleDTO.id) &&
                Objects.equals(name, sampleDTO.name) &&
                Objects.equals(description, sampleDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
