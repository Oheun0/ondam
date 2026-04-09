package com.ondam.ai.dto;

public class AiRecommendDTO {
	private String phrase;             // 파이썬이 생성한 전체 추천 사유 (멘트)
    private String recommendedSize;    // 파이썬이 골라준 추천 사이즈 (예: 100)
    private String recommendedColor;   // 파이썬이 골라준 추천 색상 (예: 검정색)
    
    // --- 상품 기본 정보 ---
    private int productNo;             
    private String productName;       
    private String productBrand;       
    private int productPrice;         
    private int productOriginPrice;    
    private String imgFile;            
    private int productWishCount;      
    private int productState;          
    private int productGender;         // 상품 성별 (0:공용, 1:남, 2:여)
    private String productSeason;      // 상품 해당 계절
    private boolean wishActive;        // 유저의 찜 여부
    
    // --- 유저 정보 (필요 시 유지) ---
    private int userNo;            
    private String userName;         
    private int age;                
    private double height;          
    private double weight;             
    private int gender;                // 유저 성별
    private String userHobby;         
    private String prefColor;          
    private int familyNo;
    
	public AiRecommendDTO() {
		
	}


	

	public AiRecommendDTO(String phrase, String recommendedSize, String recommendedColor, int productNo,
			String productName, String productBrand, int productPrice, int productOriginPrice, String imgFile,
			int productWishCount, int productState, int productGender, String productSeason, boolean wishActive,
			int userNo, String userName, int age, double height, double weight, int gender, String userHobby,
			String prefColor, int familyNo) {
		this.phrase = phrase;
		this.recommendedSize = recommendedSize;
		this.recommendedColor = recommendedColor;
		this.productNo = productNo;
		this.productName = productName;
		this.productBrand = productBrand;
		this.productPrice = productPrice;
		this.productOriginPrice = productOriginPrice;
		this.imgFile = imgFile;
		this.productWishCount = productWishCount;
		this.productState = productState;
		this.productGender = productGender;
		this.productSeason = productSeason;
		this.wishActive = wishActive;
		this.userNo = userNo;
		this.userName = userName;
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.gender = gender;
		this.userHobby = userHobby;
		this.prefColor = prefColor;
		this.familyNo = familyNo;
	}




	public String getPhrase() {
		return phrase;
	}


	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}


	public String getRecommendedSize() {
		return recommendedSize;
	}


	public void setRecommendedSize(String recommendedSize) {
		this.recommendedSize = recommendedSize;
	}


	public String getRecommendedColor() {
		return recommendedColor;
	}


	public void setRecommendedColor(String recommendedColor) {
		this.recommendedColor = recommendedColor;
	}


	public int getProductNo() {
		return productNo;
	}


	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getProductBrand() {
		return productBrand;
	}


	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}


	public int getProductPrice() {
		return productPrice;
	}


	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}


	public int getProductOriginPrice() {
		return productOriginPrice;
	}


	public void setProductOriginPrice(int productOriginPrice) {
		this.productOriginPrice = productOriginPrice;
	}


	public String getImgFile() {
		return imgFile;
	}


	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}


	public int getProductWishCount() {
		return productWishCount;
	}


	public void setProductWishCount(int productWishCount) {
		this.productWishCount = productWishCount;
	}


	public int getProductState() {
		return productState;
	}


	public void setProductState(int productState) {
		this.productState = productState;
	}


	public int getProductGender() {
		return productGender;
	}


	public void setProductGender(int productGender) {
		this.productGender = productGender;
	}


	public String getProductSeason() {
		return productSeason;
	}


	public void setProductSeason(String productSeason) {
		this.productSeason = productSeason;
	}


	public boolean isWishActive() {
		return wishActive;
	}


	public void setWishActive(boolean wishActive) {
		this.wishActive = wishActive;
	}


	public int getUserNo() {
		return userNo;
	}


	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public double getHeight() {
		return height;
	}


	public void setHeight(double height) {
		this.height = height;
	}


	public double getWeight() {
		return weight;
	}


	public void setWeight(double weight) {
		this.weight = weight;
	}


	public int getGender() {
		return gender;
	}


	public void setGender(int gender) {
		this.gender = gender;
	}


	public String getUserHobby() {
		return userHobby;
	}


	public void setUserHobby(String userHobby) {
		this.userHobby = userHobby;
	}


	public String getPrefColor() {
		return prefColor;
	}


	public void setPrefColor(String prefColor) {
		this.prefColor = prefColor;
	}


	public int getFamilyNo() {
		return familyNo;
	}


	public void setFamilyNo(int familyNo) {
		this.familyNo = familyNo;
	}
	private String targetName;
	public String getTargetName() { return targetName; }
	public void setTargetName(String targetName) { this.targetName = targetName; }
}
