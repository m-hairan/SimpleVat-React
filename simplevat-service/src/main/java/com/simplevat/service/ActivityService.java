package com.simplevat.service;

import com.simplevat.entity.Activity;
import com.simplevat.entity.ActivityId;

public abstract class ActivityService extends SimpleVatService<ActivityId, Activity> {
	
	  public abstract void logActivity(Activity activity);
}
