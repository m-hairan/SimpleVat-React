package com.simplevat.dao;

import java.util.List;

import com.simplevat.entity.Activity;

public interface ActivityDao extends Dao<Integer, Activity>{

	public List<Activity> getLatestActivites(int maxActiviyCount);
}
