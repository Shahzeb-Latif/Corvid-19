package com.corvid.ninteen.impact.analysis.service;

public interface CorvidService {
     String getAllCasesToday() throws Exception;
     String getAllCountryWiseCases() throws Exception;
     String getCasesByACountry(String country) throws Exception;
     String getTopNCountriesCases(Integer number) throws Exception;
     String getCasesByACountryAndByDate(String country, String date) throws Exception;
}
