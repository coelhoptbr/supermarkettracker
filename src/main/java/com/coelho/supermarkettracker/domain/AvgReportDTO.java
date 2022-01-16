package com.coelho.supermarkettracker.domain;

import java.util.Objects;

public class AvgReportDTO {

    private Product product;

    private Double highestPrice;

    private Double lowestPrice;

    private Double averagePrice;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public Double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    @Override
    public String toString() {
        String result = this.getProduct().getName() + ";"
                + this.getHighestPrice() + ";"
                + this.getLowestPrice();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvgReportDTO that = (AvgReportDTO) o;
        return product.equals(that.product) && Objects.equals(highestPrice, that.highestPrice) && Objects.equals(lowestPrice, that.lowestPrice) && Objects.equals(averagePrice, that.averagePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, highestPrice, lowestPrice, averagePrice);
    }
}
