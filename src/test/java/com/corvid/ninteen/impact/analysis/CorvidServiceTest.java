package com.corvid.ninteen.impact.analysis;

import com.corvid.ninteen.impact.analysis.mapper.CorvidMapper;
import com.corvid.ninteen.impact.analysis.model.CountryModel;
import com.corvid.ninteen.impact.analysis.model.TotalCases;
import com.corvid.ninteen.impact.analysis.service.CorvidService;
import com.corvid.ninteen.impact.analysis.service.impl.CorvidServiceImplementation;
import com.corvid.ninteen.impact.analysis.util.ConstantUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static com.corvid.ninteen.impact.analysis.util.ConstantUtil.lastUpdateDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.doReturn;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.ResourceUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
@PropertySource("classpath:application.properties")
public class CorvidServiceTest {

    @InjectMocks
    private CorvidServiceImplementation corvidService;

    @InjectMocks
    CorvidMapper corvidMapper;

    @Before
    public void before(){
        corvidService.filePath = "classpath:time_series_covid19_confirmed_global.csv";
        corvidService.corvidMapper = corvidMapper;
    }

    @Test
    public void testGetAllCasesToday() throws Exception {

        //Cases Today list not null
        assertNotEquals(corvidService.getAllCasesToday(), null);

        //Cases Today list not empty
        assertNotEquals(corvidService.getAllCasesToday(), "");

        TotalCases totalCases = new TotalCases();
        totalCases.setCases(ConstantUtil.corvidTotalCases);

        //Cases Today list must be equal to this result
        assertEquals(corvidService.getAllCasesToday(), corvidMapper.convertObjectToJson(totalCases));
    }

    @Test
    public void testGetAllCountryWiseCases() throws Exception {

        //Cases Country wise list not null
        assertNotEquals(corvidService.getAllCountryWiseCases(), null);

        //Cases Country wise list not empty
        assertNotEquals(corvidService.getAllCountryWiseCases(), "");

        List<CountryModel> countryModelList = new ObjectMapper().readValue(corvidService.getAllCountryWiseCases(), new TypeReference<List<CountryModel>>() {});

        List<String> dates = countryModelList.stream().map(CountryModel::getDate).collect(Collectors.toList());
        List<String> lastUpdateDateList = new ArrayList<>();
        countryModelList.forEach(countryModel -> lastUpdateDateList.add(lastUpdateDate));

        //actual and expected list
        assertEquals(dates, lastUpdateDateList);
    }

    @Test
    public void testGetCasesByACountry() throws Exception {

        //Cases of Pakistan list not null
        assertNotEquals(corvidService.getCasesByACountry("pakistan"), null);

        //Cases of Pakistan  list not empty
        assertNotEquals(corvidService.getCasesByACountry("pakistan"), "");

        List<CountryModel> countryModelList = new ObjectMapper().readValue(corvidService.getCasesByACountry("pakistan"), new TypeReference<List<CountryModel>>() {});

        List<String> dates = countryModelList.stream().map(CountryModel::getDate).collect(Collectors.toList());
        List<String> lastUpdateDateList = new ArrayList<>();
        countryModelList.forEach(countryModel -> lastUpdateDateList.add(lastUpdateDate));

        //actual and expected list
        assertEquals(dates, lastUpdateDateList);
    }

    @Test
    public void testGetTopNCountriesCases() throws Exception {

        //Cases Top 5 Country list not null
        assertNotEquals(corvidService.getTopNCountriesCases(5), null);

        //Cases Top 5 Country list not empty
        assertNotEquals(corvidService.getTopNCountriesCases(5), "");

        List<CountryModel> countryModelList = new ObjectMapper().readValue(corvidService.getTopNCountriesCases(5), new TypeReference<List<CountryModel>>() {});

        List<String> dates = countryModelList.stream().map(CountryModel::getDate).collect(Collectors.toList());
        List<String> lastUpdateDateList = new ArrayList<>();
        countryModelList.forEach(countryModel -> lastUpdateDateList.add(lastUpdateDate));

        //actual and expected list
        assertEquals(dates, lastUpdateDateList);
    }

    @Test
    public void testGetCasesByACountryAndByDate() throws Exception {

        //Cases of Pakistan till 14th April 2020 list not null
        assertNotEquals(corvidService.getCasesByACountryAndByDate("pakistan","14-04-2020"), null);

        //Cases of Pakistan till 14th April 2020 list not empty
        assertNotEquals(corvidService.getCasesByACountryAndByDate("pakistan","14-04-2020"), "");

        List<CountryModel> countryModelList = new ObjectMapper().readValue(corvidService.getCasesByACountryAndByDate("pakistan","14-04-2020"), new TypeReference<List<CountryModel>>() {});

        List<String> countryNames = countryModelList.stream().map(CountryModel::getCountryName).collect(Collectors.toList());
        List<String> countryList = new ArrayList<>();
        countryModelList.forEach(countryModel -> countryList.add("Pakistan"));

        //actual and expected list
        assertEquals(countryNames, countryList);
    }

}
