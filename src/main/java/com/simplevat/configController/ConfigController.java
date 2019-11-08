package com.simplevat.configController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping("/config")
public class ConfigController implements Serializable {

	@GetMapping(value = "/getreleasenumber")
	private SimpleVatConfigModel getReleaseNumber()
	{
		SimpleVatConfigModel config = new SimpleVatConfigModel();
		config.setSimpleVatRelease("0-0-2-beta-2-50");
		return config;
	}
}
