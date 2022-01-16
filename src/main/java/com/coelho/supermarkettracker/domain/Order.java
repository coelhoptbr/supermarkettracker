package com.coelho.supermarkettracker.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection="aleorder")
public class Order {

    @NotNull
    private ObjectId id;
    @NotNull
    private UserInfo userInfo;
    @NotNull
    private Product product;
    @NotNull
    private Shop shop;
    @NotNull
    private LocalDate date;
    @NotNull
    private Double price;
    @NotNull
    private Boolean isOffer;

    public Order() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getOffer() {
        return isOffer;
    }

    public void setOffer(Boolean offer) {
        isOffer = offer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId().equals(order.getId()) && getUserInfo().equals(order.getUserInfo()) && getProduct().equals(order.getProduct()) && getShop().equals(order.getShop());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserInfo(), getProduct(), getShop());
    }
}
