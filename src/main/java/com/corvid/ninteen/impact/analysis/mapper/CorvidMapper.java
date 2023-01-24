package com.corvid.ninteen.impact.analysis.mapper;

import com.corvid.ninteen.impact.analysis.model.CountryModel;
import com.corvid.ninteen.impact.analysis.model.ErrorModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.corvid.ninteen.impact.analysis.util.ConstantUtil.FAILED;

/**
 * @author shahzeb.latif
 */

@Component
public class CorvidMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorvidMapper.class);

    public String convertObjectToJson(Object obj) {
        ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            LOGGER.error(ex.toString());
        }
        return json == null || json.isEmpty() ? "" : json;
    }

    public String getError(Exception ex) {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setErrorMessage(FAILED);
        LOGGER.error(ex.getMessage());
        return this.convertObjectToJson(errorModel);
    }

    public String getCustomError(String error) {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setErrorMessage(error);
        LOGGER.error(error);
        return this.convertObjectToJson(errorModel);
    }

    public void sortCountryData(List<CountryModel> countryData) {
        Comparator<CountryModel> compareByTotalCases = (CountryModel o1, CountryModel o2) -> o1.getCorvidCountryTotalCases().compareTo(o2.getCorvidCountryTotalCases());
        Collections.sort(countryData, compareByTotalCases.reversed());
    }

    public boolean isDateValid(String date) {
        String dateArray[] = date.split("-");
        if (dateArray == null || dateArray.length != 3) {
            return false;
        }
        try {
            for(int i=0;i<dateArray.length;i++) {
                Integer num = Integer.parseInt(dateArray[i]);
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
