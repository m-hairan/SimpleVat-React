package com.simplevat.web.home.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Activity;
import com.simplevat.service.ActivityService;

import lombok.Getter;
import lombok.Setter;

@Controller
@SpringScopeView
@Getter
@Setter
public class ActivityController {

	@Autowired
	private ActivityService activityService;
	
	private List<Activity> activities;
	
	@PostConstruct
	protected void init() {
		activities = activityService.getLatestActivites(6);
	}
	
	public String getLastUpdatedTime(Activity activity) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
		return activity.getLastUpdateDate().format(formatter);
	}
	
}
