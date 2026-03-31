package com.ondam.situation.dto;

public class SituationMappingDTO {

    private int situationMapNo;
    private int situationNo;
    private int productNo;

    public SituationMappingDTO() {}

    public SituationMappingDTO(int situationMapNo, int situationNo, int productNo) {
        this.situationMapNo = situationMapNo;
        this.situationNo = situationNo;
        this.productNo = productNo;
    }

    public int getSituationMapNo() {
        return situationMapNo;
    }

    public void setSituationMapNo(int situationMapNo) {
        this.situationMapNo = situationMapNo;
    }

    public int getSituationNo() {
        return situationNo;
    }

    public void setSituationNo(int situationNo) {
        this.situationNo = situationNo;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }
}
