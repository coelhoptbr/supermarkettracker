package com.coelho.supermarkettracker.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection="order")
public class Order {

    private ObjectId id;
    private UserInfo userInfo;
    private Product product;
    private Shop shop;
    private Double price;
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
}
