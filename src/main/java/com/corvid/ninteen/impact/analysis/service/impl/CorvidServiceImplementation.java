package com.corvid.ninteen.impact.analysis.service.impl;

import com.corvid.ninteen.impact.analysis.mapper.CorvidMapper;
import com.corvid.ninteen.impact.analysis.model.CountryModel;
import com.corvid.ninteen.impact.analysis.model.TotalCases;
import com.corvid.ninteen.impact.analysis.service.CorvidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.corvid.ninteen.impact.analysis.util.ConstantUtil.*;

/**
 * @author shahzeb.latif
 */

@Service
public class CorvidServiceImplementation implements CorvidService {

    @Autowired
    public CorvidMapper corvidMapper;

    @Value(value = "${file.path}")
    public String filePath;

    private void executeLogic() throws IOException, ParseException {

        if (lastUpdateDate == null || lastUpdateDate.isEmpty() || countryModels == null || countryModels.size() == 0 || corvidTotalCases == null || corvidTotalCases == 0) {
            this.prepareCorvidCasesData();
        } else {
            File corvidFile = ResourceUtils.getFile(filePath);
            BufferedReader br = new BufferedReader(new FileReader(corvidFile));

            String line = br.readLine();
            String[] columnIndex = line.split(COMMA_SEPERATED_REGEX, -1);

            if (!EXPECTED_DATE_FORMAT.format(ORIGINAL_DATE_FORMAT.parse(columnIndex[columnIndex.length - 1])).equals(lastUpdateDate)) {
                this.prepareCorvidCasesData();
            }
            br.close();
        }
    }

    private void prepareCorvidCasesData() throws IOException, ParseException {
        try {
            Integer totalCases = -1;
            List<CountryModel> countryModelList = new ArrayList<>();
            List<String> allDates = new ArrayList<>();
            CountryModel countryModel = null;

            File corvidFile = ResourceUtils.getFile(filePath);
            BufferedReader br = new BufferedReader(new FileReader(corvidFile));

            String line = br.readLine();

            while (line != null) {
                String[] columnIndex = line.split(COMMA_SEPERATED_REGEX, -1);

                if (totalCases >= 0) {

                    Integer totalCasesCountryWise = 0;
                    int count = 0;

                    for (int i = 4; i < columnIndex.length; i++) {

                        totalCasesCountryWise += Integer.parseInt(columnIndex[i]);

                        countryModel = new CountryModel();
                        countryModel.setCountryName(columnIndex[1]);
                        countryModel.setState(columnIndex[0]);
                        countryModel.setDate(allDates.get(count++));
                        countryModel.setCorvidCountryDateWiseCases(Integer.parseInt(columnIndex[i]));

                        countryModelList.add(countryModel);
                    }

                    countryModelList.get(countryModelList.size() - 1).setCorvidCountryTotalCases(totalCasesCountryWise);
                    totalCases += Integer.parseInt(columnIndex[columnIndex.length - 1]);
                } else {
                    countryModels = null;
                    corvidTotalCases = 0;

                    lastUpdateDate = EXPECTED_DATE_FORMAT.format(ORIGINAL_DATE_FORMAT.parse(columnIndex[columnIndex.length - 1]));

                    for (int i = 4; i < columnIndex.length; i++) {
                        allDates.add(EXPECTED_DATE_FORMAT.format(ORIGINAL_DATE_FORMAT.parse(columnIndex[i])));
                    }

                    totalCases++;
                }

                line = br.readLine();
            }

            countryModels = countryModelList;
            corvidTotalCases = totalCases;

            br.close();

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public String getAllCasesToday() throws Exception {
        this.executeLogic();
        TotalCases totalCases = new TotalCases();
        totalCases.setCases(corvidTotalCases);
        return corvidMapper.convertObjectToJson(totalCases);
    }

    @Override
    public String getAllCountryWiseCases() throws Exception {
        this.executeLogic();
        List<CountryModel> countryData = countryModels.stream().distinct().filter(p -> p.getDate().equals(lastUpdateDate))
                .collect(Collectors.toList());

        if (countryData != null && countryData.size() > 0) {
            corvidMapper.sortCountryData(countryData);
            return corvidMapper.convertObjectToJson(countryData);
        } else {
            return "";
        }
    }

    @Override
    public String getCasesByACountry(String country) throws Exception {
        this.executeLogic();
        List<CountryModel> countryData = countryModels.stream().distinct()
                .filter(p -> p.getCountryName().equalsIgnoreCase(country))
                .filter(p -> p.getDate().equals(lastUpdateDate))
                .collect(Collectors.toList());

        if (countryData != null && countryData.size() > 0) {
            corvidMapper.sortCountryData(countryData);
            return corvidMapper.convertObjectToJson(countryData);
        } else {
            return corvidMapper.getCustomError("Country Name not Found");
        }
    }

    @Override
    public String getTopNCountriesCases(Integer number) throws Exception {
        this.executeLogic();
        List<CountryModel> topNCountriesCases = countryModels
                .stream()
                .filter(p -> p.getDate().equals(lastUpdateDate)).distinct()
                .collect(Collectors.toList());

        corvidMapper.sortCountryData(topNCountriesCases);

        topNCountriesCases = topNCountriesCases
                .stream()
                .limit(number)
                .collect(Collectors.toList());

        return corvidMapper.convertObjectToJson(topNCountriesCases);
    }

    @Override
    public String getCasesByACountryAndByDate(String country, String date) throws Exception {

        if (!corvidMapper.isDateValid(date))
            return corvidMapper.getCustomError("Invalid Date");

        this.executeLogic();

        List<CountryModel> countryData = countryModels.stream()
                .filter(p -> p.getCountryName().equalsIgnoreCase(country))
                .filter(p -> {

                    try {
                        return ((EXPECTED_DATE_FORMAT.parse(p.getDate()).equals(EXPECTED_DATE_FORMAT.parse(countryModels.get(0).getDate())) ||
                                EXPECTED_DATE_FORMAT.parse(p.getDate()).equals(EXPECTED_DATE_FORMAT.parse(date))) ||
                                (EXPECTED_DATE_FORMAT.parse(p.getDate()).after(EXPECTED_DATE_FORMAT.parse(countryModels.get(0).getDate())) &&
                                        EXPECTED_DATE_FORMAT.parse(p.getDate()).before(EXPECTED_DATE_FORMAT.parse((date)))));

                    } catch (ParseException e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());


        if (countryData != null && countryData.size() > 0) {
            return corvidMapper.convertObjectToJson(countryData);
        } else {
            return "";
        }
    }
}

