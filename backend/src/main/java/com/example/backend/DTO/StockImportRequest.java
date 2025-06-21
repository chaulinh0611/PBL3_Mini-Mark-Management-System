package com.example.backend.DTO;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class StockImportRequest {
    @JsonProperty("import_id")
    private String importId;

    @JsonProperty("employee_id")
    private String employeeId;

    @JsonProperty("total_cost")
    private Integer totalCost;

    @JsonProperty("stock_import_detail")
    private List<StockImportDetailRequest> stockImportDetail;

    public String getImportId() {
        return importId;
    }

    public void setImportId(String importId) {
        this.importId = importId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public List<StockImportDetailRequest> getStockImportDetail() {
        return stockImportDetail;
    }

    public void setStockImportDetail(List<StockImportDetailRequest> stockImportDetail) {
        this.stockImportDetail = stockImportDetail;
    }
}