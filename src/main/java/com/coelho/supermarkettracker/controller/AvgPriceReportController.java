package com.coelho.supermarkettracker.controller;

import com.coelho.supermarkettracker.service.AvgPriceReportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.coelho.supermarkettracker.domain.Const.LINEBREAK;

@RestController
@RequestMapping("/avgpricereport")
public class AvgPriceReportController {

    private AvgPriceReportService service;

    public AvgPriceReportController(AvgPriceReportService service) {
        this.service = service;
    }

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> download() throws IOException {
        String filename = "report-" + getFormattedDate(new Date(), "yyyy-mm-dd-hhmmss") + ".csv";
        List<String> result = new ArrayList<>();
        result.add("Product name; Highest price; Lowest price; Number of orders".concat(LINEBREAK));

        service.getReport().stream().forEach(prodLoop -> {
            result.add(prodLoop.toString().concat(LINEBREAK));
        });

        ByteArrayOutputStream bOutStr = new ByteArrayOutputStream();

        for (String line : result) {
            bOutStr.write(line.getBytes());
        }
        ByteArrayResource bais = new ByteArrayResource (bOutStr.toByteArray());

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(bOutStr.size())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bais);
    }

    private String getFormattedDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
