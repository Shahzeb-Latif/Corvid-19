package com.corvid.ninteen.impact.analysis.controller;

import com.corvid.ninteen.impact.analysis.mapper.CorvidMapper;
import com.corvid.ninteen.impact.analysis.service.CorvidService;
import com.corvid.ninteen.impact.analysis.service.impl.CorvidServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author shahzeb.latif
 */

@RestController
@RequestMapping("/corvid/cases/api/")
public class CorvidController {

    @Autowired
    CorvidService corvidService;

    @Autowired
    CorvidMapper corvidMapper;

    @GetMapping("new")
    public String getAllCasesToday() {

        try {
            return corvidService.getAllCasesToday();
        } catch (Exception ex) {
            return corvidMapper.getError(ex);
        }
    }

    @GetMapping("new/country_wise")
    public String getAllCountryWiseCases() {
        try {
            return corvidService.getAllCountryWiseCases();
        } catch (Exception ex) {
            return corvidMapper.getError(ex);
        }
    }

    @GetMapping("new/{country}")
    public String getCasesByACountry(@PathVariable("country") String country) {
        try {
            return corvidService.getCasesByACountry(country);
        } catch (Exception ex) {
            return corvidMapper.getError(ex);
        }
    }

    @GetMapping("/new/top/{number}")
    public String getTopNCountriesCases(@PathVariable("number") Integer number) {
        try {
            return corvidService.getTopNCountriesCases(number);
        } catch (Exception ex) {
            return corvidMapper.getError(ex);
        }
    }

    @GetMapping("all/date_and_country_wise")
    public String getCasesByACountryAndByDate(@RequestParam("country") String country, @RequestParam("date") String date) {
        try {
            return corvidService.getCasesByACountryAndByDate(country,date);
        } catch (Exception ex) {
            return corvidMapper.getError(ex);
        }
    }
}
