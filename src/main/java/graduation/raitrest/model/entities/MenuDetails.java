package graduation.raitrest.model.entities;

import graduation.raitrest.model.AbstractBaseEntity;
import graduation.raitrest.model.to.HasId;
import graduation.raitrest.util.DateTimeUtil;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "MENU_DETAILS" )
public class MenuDetails implements HasId {
    public static final int START_SEQ = 100;

    @Id
    @SequenceGenerator(name = "EXTRA_SEQ", sequenceName = "EXTRA_SEQ", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXTRA_SEQ")
    protected Integer id;

    @Basic
    @Column(name = "DESCRIPTION")
    @NotBlank
    private String description;


    @Column(name = "DATE_TIME")
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    @NotNull
    private LocalDateTime dateTime;

    @Basic
    @Column(name = "TYPE_DISH")
    private String typeDish;

    @Basic
    @Column(name = "QUANTITY")
    private String quantity;



    @Basic
    @Column(name = "PRICE", precision=10, scale=2,columnDefinition="Decimal(10,2) default '0.00'")
    private BigDecimal  price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID", nullable = false)
 //   @NotNull
    private Restaurant restaurant;

    public MenuDetails(Integer id,  Restaurant restaurant, String typeDish, String description,
                       String quantity, BigDecimal price, LocalDateTime dateTime) {
       // super(id);
        this.id = id;
        this.restaurant = restaurant;
        this.typeDish = typeDish;
        this.description = description;
        this.quantity = quantity;

        this.price = price;
        this.dateTime = dateTime;
    }

    public MenuDetails( Restaurant restaurant, String typeDish, String description,
                       String quantity,  BigDecimal price, LocalDateTime dateTime) {
       this(null,restaurant,typeDish,description,quantity,price,dateTime);
    }

    public MenuDetails() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date LocalDateTime) {
        this.dateTime = dateTime;
    }

    public String getTypeDish() {
        return typeDish;
    }

    public void setTypeDish(String typeDish) {
        this.typeDish = typeDish;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }



    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        MenuDetails that = (MenuDetails) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }


}
