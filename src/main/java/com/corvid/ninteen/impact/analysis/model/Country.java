package com.corvid.ninteen.impact.analysis.model;

import java.util.Date;

/**
 * @author shahzeb.latif
 */

public class Country {
    public String state;
    public String countryName;
    public Date date;
    public Integer cases;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }
}
