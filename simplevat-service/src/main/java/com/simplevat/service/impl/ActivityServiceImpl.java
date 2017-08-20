package com.simplevat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplevat.dao.ActivityDao;
import com.simplevat.dao.Dao;
import com.simplevat.entity.Activity;
import com.simplevat.entity.ActivityId;
import com.simplevat.service.ActivityService;

//@Service(value = "activityService")
public class ActivityServiceImpl extends ActivityService {

	@Autowired
	ActivityDao activityDao;
	
	@Override
	protected Dao<ActivityId, Activity> getDao() {
		return activityDao;
	}

	@Override
	public synchronized void logActivity(Activity activity) {
		try {
			activityDao.update(activity);
		} catch(Exception e) {
			e.printStackTrace();
		}
	
	}

}
