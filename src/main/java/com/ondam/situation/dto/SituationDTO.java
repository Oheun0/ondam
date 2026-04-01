package com.ondam.situation.dto;

public class SituationDTO {

    private int situationNo;
    private int upSituationNo;
    private int situationLevel;
    private String situationName;
    private String situationImg;

    public SituationDTO() {}

    public SituationDTO(int situationNo, int upSituationNo, int situationLevel, String situationName, String situationImg) {
        this.situationNo = situationNo;
        this.upSituationNo = upSituationNo;
        this.situationLevel = situationLevel;
        this.situationName = situationName;
        this.situationImg = situationImg;
    }

    public int getSituationNo() {
        return situationNo;
    }

    public void setSituationNo(int situationNo) {
        this.situationNo = situationNo;
    }

    public int getUpSituationNo() {
        return upSituationNo;
    }

    public void setUpSituationNo(int upSituationNo) {
        this.upSituationNo = upSituationNo;
    }

    public int getSituationLevel() {
        return situationLevel;
    }

    public void setSituationLevel(int situationLevel) {
        this.situationLevel = situationLevel;
    }

    public String getSituationName() {
        return situationName;
    }

    public void setSituationName(String situationName) {
        this.situationName = situationName;
    }

	public String getSituationImg() {
		return situationImg;
	}

	public void setSituationImg(String situationImg) {
		this.situationImg = situationImg;
	}
}