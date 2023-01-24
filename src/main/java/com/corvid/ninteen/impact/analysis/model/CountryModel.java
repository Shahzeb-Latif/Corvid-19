package com.corvid.ninteen.impact.analysis.model;

/**
 * @author shahzeb.latif
 */

public class CountryModel {
    public String state;
    public String countryName;
    public Integer corvidCountryTotalCases;
    public Integer corvidCountryDateWiseCases;
    public String date;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getCorvidCountryTotalCases() {
        return corvidCountryTotalCases;
    }

    public void setCorvidCountryTotalCases(Integer corvidCountryTotalCases) {
        this.corvidCountryTotalCases = corvidCountryTotalCases;
    }

    public Integer getCorvidCountryDateWiseCases() {
        return corvidCountryDateWiseCases;
    }

    public void setCorvidCountryDateWiseCases(Integer corvidCountryDateWiseCases) {
        this.corvidCountryDateWiseCases = corvidCountryDateWiseCases;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}