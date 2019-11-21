/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.datalistcontroller;

import com.simplevat.entity.Country;
import com.simplevat.entity.Currency;
import com.simplevat.service.CountryService;
import com.simplevat.service.CurrencyService;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sonu
 */
@RestController
@RequestMapping(value = "/rest/datalist")
public class DataListController implements Serializable {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CurrencyService currencyService;

    @GetMapping(value = "/getcountry")
    public ResponseEntity getCountry() {
        try {
            List<Country> countryList = countryService.getCountries();
            if (countryList != null && !countryList.isEmpty()) {
                return new ResponseEntity<>(countryList, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/getcurrenncy")
    public ResponseEntity getCurrency() {
        try {
            List<Currency> currencies = currencyService.getCurrencies();
            if (currencies != null && !currencies.isEmpty()) {
                return new ResponseEntity<>(currencies, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
