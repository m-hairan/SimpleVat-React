package com.simplevat.test.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.simplevat.dao.CountryDao;
import com.simplevat.entity.Country;
import com.simplevat.test.common.BaseManagerTest;

public class CountryDaoImplTest extends BaseManagerTest {
	
	@Autowired
	CountryDao dao;
	private static final String PREFIX = "TEST";
	private static final int TEST_SIZE=12;
	@Ignore
	@Test
	public void importDataTest() {
		List<Country> countries = getTestData();
		dao.importData(countries);
		Map<String,String> attributes = new HashMap<String,String>();
		attributes.put("countryName", PREFIX);
		countries = dao.findByAttributes(attributes);
		assertNotNull(countries);
		assertTrue("Size Not Matching ", countries.size() == TEST_SIZE);
		for(Country country : countries) {
			assertTrue("Name Not matching ", country.getCountryName().contains(PREFIX+ "_NAME_"));
		}
	}
	@Ignore
	@Test
	public void testDumpData() {
		List<Country> countries = dao.dumpData();
		assertNotNull(countries);
		assertTrue("Size Not Matching ", countries.size() == 266);
	}
	
	@After
	public void destroy() {
		deleteAllTestData();
	}
	
	private List<Country> getTestData() {
		
		List<Country> countries = new ArrayList<Country>();
		
		for(int i=0; i < TEST_SIZE; i++) {
			Country country = new Country();
			country.setCountryDescription(PREFIX + "_DESCRIPTION_" + i);
			country.setCountryFullName(PREFIX + "_FULL_NAME_" + i);
			country.setCountryName(PREFIX+ "_NAME_"+i);
			country.setCreatedBy(3);
//			country.setDefaltFlag('C');
			country.setIsoAlpha3Code("A");
			country.setOrderSequence(i);
			countries.add(country);
		}
		return countries;
		
	}
	
	private void deleteAllTestData() {
		
		List<Country> countries;
		Map<String,String> attributes = new HashMap<String,String>();
		attributes.put("countryName", PREFIX);
		countries = dao.findByAttributes(attributes);
		//assertTrue("Size should be " + TEST_SIZE, countries.size() == TEST_SIZE);
		for(Country country : countries) {
			dao.delete(country);
		}
	}

	
}
