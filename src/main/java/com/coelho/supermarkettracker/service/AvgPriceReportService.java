package com.coelho.supermarkettracker.service;

import com.coelho.supermarkettracker.domain.AvgReportDTO;
import com.coelho.supermarkettracker.domain.MinMaxEnum;
import com.coelho.supermarkettracker.domain.Order;
import com.coelho.supermarkettracker.domain.Product;
import com.coelho.supermarkettracker.repo.OrderRepository;
import com.coelho.supermarkettracker.repo.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AvgPriceReportService {

    private final OrderService orderService;

    private final ProductService productService;

    public AvgPriceReportService(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    public List<AvgReportDTO> getReport() {
        List<AvgReportDTO> result = new ArrayList<>();
        productService.findAll().forEach(prodLoop  -> {

            AvgReportDTO reportLine = new AvgReportDTO();
            reportLine.setProduct(prodLoop);

            Order orderMaxPrice = orderService.getHighestLowestPrice(prodLoop, null, MinMaxEnum.MAX);
            if (orderMaxPrice != null) reportLine.setHighestPrice(orderMaxPrice.getPrice());

            Order orderMinPrice = orderService.getHighestLowestPrice(prodLoop, null, MinMaxEnum.MIN);
            if (orderMinPrice != null) reportLine.setLowestPrice(orderMinPrice.getPrice());

            result.add(reportLine);

        });
        return result;
    }
}
