package com.ondam.ai.dto;

import java.util.Vector;

public class aiSearchDTO {
    private int rank;
    private double score;
    private int productNo;
    private String productName;
    private int productPrice;
    private String imgFile;
    private Vector<String> seasons; // [변경] Vector 사용

    public aiSearchDTO() {}

    public aiSearchDTO(int rank, double score, int productNo, String productName, int productPrice, String imgFile,
			Vector<String> seasons) {
		super();
		this.rank = rank;
		this.score = score;
		this.productNo = productNo;
		this.productName = productName;
		this.productPrice = productPrice;
		this.imgFile = imgFile;
		this.seasons = seasons;
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
    public Vector<String> getSeasons() { return seasons; }
    public void setSeasons(Vector<String> seasons) { this.seasons = seasons; }
}