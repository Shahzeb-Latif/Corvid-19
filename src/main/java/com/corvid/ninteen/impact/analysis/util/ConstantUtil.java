package com.corvid.ninteen.impact.analysis.util;

import com.corvid.ninteen.impact.analysis.model.CountryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

public abstract class ConstantUtil {

    public static Map<String, Date> userLists = new HashMap<>();
    public static List<CountryModel> countryModels = new ArrayList<>();
    public static String lastUpdateDate;
    public static Integer corvidTotalCases;

    public static final String SUCCESS = "Success";
    public static final String FAILED = "Failed";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";
    public static final String USER = "USER";
    public static final String COMMA_SEPERATED_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    public static final SimpleDateFormat EXPECTED_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat ORIGINAL_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    public static final SimpleDateFormat FORMAT_TOKEN_ISSUE_AT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

}
