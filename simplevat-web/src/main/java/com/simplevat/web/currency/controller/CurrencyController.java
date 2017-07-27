package com.simplevat.web.currency.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.web.project.controller.ProjectController;
import com.simplevat.entity.Currency;
import com.simplevat.service.CurrencyService;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
public class CurrencyController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);
	
	@Getter
	@Setter
	private List<Currency> currencies;
	
	@Autowired
	private CurrencyService currencyService;
	
	@PostConstruct
    public void init() throws Exception {
        this.currencies = currencyService.getCurrencies();
    }
	
	public List<Currency> completeCurrency(String currencyStr) {
		List<Currency> currencySuggestion = new ArrayList<>();
		Iterator<Currency> currencyIterator = this.currencies.iterator();

		LOGGER.debug(" Size :" + currencies.size());

		while (currencyIterator.hasNext()) {
			Currency currency = currencyIterator.next();
			if (currency.getCurrencyName() != null
					&& !currency.getCurrencyName().isEmpty()
					&& currency.getCurrencyName().toUpperCase()
							.contains(currencyStr.toUpperCase())) {
				LOGGER.debug(" currency :" + currency.getCurrencyDescription());
				currencySuggestion.add(currency);
			} else if (currency.getCurrencyDescription() != null
					&& !currency.getCurrencyDescription().isEmpty()
					&& currency.getCurrencyDescription().toUpperCase()
							.contains(currencyStr.toUpperCase())) {
				currencySuggestion.add(currency);
				LOGGER.debug(" currency :" + currency.getCurrencyDescription());
			} else if (currency.getCurrencyIsoCode() != null
					&& !currency.getCurrencyIsoCode().isEmpty()
					&& currency.getCurrencyIsoCode().toUpperCase()
							.contains(currencyStr.toUpperCase())) {
				currencySuggestion.add(currency);
				LOGGER.debug(" currency :" + currency.getCurrencyIsoCode());
			}
		}

		LOGGER.debug(" Size :" + currencySuggestion.size());

		return currencySuggestion;
	}


}
