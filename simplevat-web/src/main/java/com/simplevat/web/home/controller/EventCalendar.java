package com.simplevat.web.home.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Event;
import com.simplevat.service.EventService;

import lombok.Getter;
import lombok.Setter;

@Controller
@SpringScopeView
@Getter
@Setter
public class EventCalendar implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ScheduleModel eventModel;
	
	@Autowired
	private EventService eventService;
	
    @PostConstruct
	public void init() {
    	eventModel = new DefaultScheduleModel();
    	List<Event> events = eventService.getEvents();
    	for(Event event : events) {
    		eventModel.addEvent(new DefaultScheduleEvent(event.getTitle(), event.getStartDate(), event.getEndDate()));
    	}
    }

}
