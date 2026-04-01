package com.ondam.situation.dto;

public class SituationDTO {

    private int situationNo;
    private int upSituationNo;
    private int situationLevel;
    private String situationName;

    public SituationDTO() {}

    public SituationDTO(int situationNo, int upSituationNo, int situationLevel, String situationName) {
        this.situationNo = situationNo;
        this.upSituationNo = upSituationNo;
        this.situationLevel = situationLevel;
        this.situationName = situationName;
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
}
