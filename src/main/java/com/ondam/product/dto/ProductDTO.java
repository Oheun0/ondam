package com.ondam.product.dto;

public class ProductDTO {

    private int productNo;
    private int vendorNo;
    private int categoryNo;
    private int situationNo;
    private String productName;
    private String productBrand;
    private String productEx;
    private String easyOneLine;
    private String easyFor;
    private String easyComfort;
    private int productGender;
    private int productPrice;
    private int productOriginPrice;
    private String productMaterial;
    private String productPattern;
    private String productFit;
    private String productThickness;
    private String productDate; 
    private int wishCount;
    private int saleCount;
    private int productState;

    public ProductDTO() {}

    public ProductDTO(int productNo, int vendorNo, int categoryNo, int situationNo, String productName, String productBrand,
                      String productEx, String easyOneLine, String easyFor, String easyComfort, int productGender, int productPrice, int productOriginPrice, String productMaterial,
                      String productPattern, String productFit, String productThickness, 
                      String productDate, int wishCount, int saleCount, int productState) {
        this.productNo = productNo;
        this.vendorNo = vendorNo;
        this.categoryNo = categoryNo;
        this.situationNo = situationNo;
        this.productName = productName;
        this.productBrand = productBrand;
        this.productEx = productEx;
        this.easyOneLine = easyOneLine;
        this.easyFor = easyFor;
        this.easyComfort = easyComfort;
        this.productGender = productGender;
        this.productPrice = productPrice;
        this.productOriginPrice = productOriginPrice;
        this.productMaterial = productMaterial;
        this.productPattern = productPattern;
        this.productFit = productFit;
        this.productThickness = productThickness;
        this.productDate = productDate;
        this.wishCount = wishCount;
        this.saleCount = saleCount;
        this.productState = productState;
    }

    public int getProductNo() { return productNo; }
    public void setProductNo(int productNo) { this.productNo = productNo; }

    public int getVendorNo() { return vendorNo; }
    public void setVendorNo(int vendorNo) { this.vendorNo = vendorNo; }

    public int getCategoryNo() { return categoryNo; }
    public void setCategoryNo(int categoryNo) { this.categoryNo = categoryNo; }

    public int getSituationNo() { return situationNo; }
    public void setSituationNo(int situationNo) { this.situationNo = situationNo; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductBrand() { return productBrand; }
    public void setProductBrand(String productBrand) { this.productBrand = productBrand; }

    public String getProductEx() { return productEx; }
    public void setProductEx(String productEx) { this.productEx = productEx; }
    
    public String getEasyOneLine() { return easyOneLine; }
    public void setEasyOneLine(String easyOneLine) { this.easyOneLine = easyOneLine; }
    
    public String getEasyFor() { return easyFor; }
    public void setEasyFor(String easyFor) { this.easyFor = easyFor; }
    
    public String getEasyComfort() { return easyComfort; }
    public void setEasyComfort(String easyComfort) { this.easyComfort = easyComfort; }

    public int getProductGender() { return productGender; }
    public void setProductGender(int productGender) { this.productGender = productGender; }

	public int getProductPrice() { return productPrice; }
    public void setProductPrice(int productPrice) { this.productPrice = productPrice; }

    public int getProductOriginPrice() { return productOriginPrice; }
    public void setProductOriginPrice(int productOriginPrice) { this.productOriginPrice = productOriginPrice; }

    public String getProductMaterial() { return productMaterial; }
    public void setProductMaterial(String productMaterial) { this.productMaterial = productMaterial; }

    public String getProductPattern() { return productPattern; }
    public void setProductPattern(String productPattern) { this.productPattern = productPattern; }

    public String getProductFit() { return productFit; }
    public void setProductFit(String productFit) { this.productFit = productFit; }

    public String getProductThickness() { return productThickness; }
    public void setProductThickness(String productThickness) { this.productThickness = productThickness; }

    public String getProductDate() { return productDate; }
    public void setProductDate(String productDate) { this.productDate = productDate; }

    public int getWishCount() { return wishCount; }
    public void setWishCount(int wishCount) { this.wishCount = wishCount; }

    public int getSaleCount() { return saleCount; }
    public void setSaleCount(int saleCount) { this.saleCount = saleCount; }

    public int getProductState() { return productState; }
    public void setProductState(int productState) { this.productState = productState; }

}