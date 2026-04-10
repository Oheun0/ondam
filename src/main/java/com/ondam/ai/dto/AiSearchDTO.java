package com.ondam.ai.dto;

import java.util.Vector;

public class AiSearchDTO {
    private int rank;
    private double score;
    private int productNo;
    private String productName;
    private int productPrice;
    private String imgFile;

    private int productOriginPrice;
    private String productBrand;
    
    public AiSearchDTO() {}

    public AiSearchDTO(int rank, double score, int productNo, String productName, int productPrice, String imgFile,
			Vector<String> seasons) {
		super();
		this.rank = rank;
		this.score = score;
		this.productNo = productNo;
		this.productName = productName;
		this.productPrice = productPrice;
		this.imgFile = imgFile;
		
		
	}

	// Getter & Setter
    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    public int getProductNo() { return productNo; }
    public void setProductNo(int productNo) { this.productNo = productNo; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getProductPrice() { return productPrice; }
    public void setProductPrice(int productPrice) { this.productPrice = productPrice; }
    public String getImgFile() { return imgFile; }
    public void setImgFile(String imgFile) { this.imgFile = imgFile; }

	public int getProductOriginPrice() {
		return productOriginPrice;
	}

	public void setProductOriginPrice(int productOriginPrice) {
		this.productOriginPrice = productOriginPrice;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
    
}	