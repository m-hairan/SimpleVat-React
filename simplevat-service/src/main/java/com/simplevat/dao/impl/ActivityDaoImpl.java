package com.simplevat.dao.impl;


import org.springframework.stereotype.Repository;

import com.simplevat.dao.AbstractDao;
import com.simplevat.dao.ActivityDao;
import com.simplevat.entity.Activity;
import com.simplevat.entity.ActivityId;

//@Repository(value = "activityDao")
public class ActivityDaoImpl extends AbstractDao<ActivityId, Activity> implements ActivityDao {


}
