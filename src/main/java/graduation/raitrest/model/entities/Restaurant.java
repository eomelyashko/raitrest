package graduation.raitrest.model.entities;

import graduation.raitrest.model.AbstractNamedEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "RESTAURANTS",  uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "address"}, name = "restaurant_unique_name_user_idx")})
public class Restaurant extends AbstractNamedEntity {

    @Column(name = "ADDRESS", nullable = false)
    @NotBlank
    private String address;

    @Column(name = "OWNER")
    private String owner;

    @Column(name = "REGISTERED", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date registered = new Date();

    @Column(name = "DESCRIPTION")
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User manager;

    public Restaurant(Integer id, @NotBlank String name, @NotBlank String address, String owner,  String description, User manager) {
        super(id, name);
        this.address = address;
        this.owner = owner;
        this.description = description;
        this.manager = manager;
    }

    public Restaurant(@NotBlank String name, @NotBlank String address, String owner,  String description, User manager) {
        this(null, name, address, owner,  description, manager);
    }


    public Restaurant() {

    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", owner='" + owner + '\'' +
                ", registered=" + registered +
                ", description='" + description + '\'' +
             //   ", managerId=" + manager != null ? manager.getId().toString() : "" +
                '}';
    }
}
