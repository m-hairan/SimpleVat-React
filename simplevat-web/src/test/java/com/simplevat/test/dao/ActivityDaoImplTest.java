package com.simplevat.test.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.simplevat.dao.ActivityDao;
import com.simplevat.entity.Activity;
import com.simplevat.test.common.BaseManagerTest;

@Ignore
public class ActivityDaoImplTest extends BaseManagerTest {
	
	@Autowired
	private ActivityDao activityDao;
	
	private static final String MODULE_CODE="MODULE_CODE";
	private static final String ACTIVITY_CODE="ACTIVITY_CODE";
	private int activityId;
	
	@Before
	public void setUp() {
		Activity activity = activityDao.update(getActivity());
		activityId = activity.getActivityId();
		activity = activityDao.findByPK(activityId);
		assertNotNull(activity);
	}
	
	
	@After
	public void tearDown() {
		Activity activity = activityDao.findByPK(activityId);
		activityDao.delete(activity);
		activity = activityDao.findByPK(activityId);
		assertNull(activity);
	}

	@Test
	public void testUpdate() {
		Activity activity = activityDao.findByPK(activityId);
		activity.setField1("CHANGED");
		activity = activityDao.update(activity);
		assertNotNull(activity);
		assertTrue("Field1 did not change ", activity.getField1().equals("CHANGED"));
	}
	
	private Activity getActivity() {
		Activity activity = new Activity();
		activity.setModuleCode(MODULE_CODE);
		activity.setActivityCode(ACTIVITY_CODE);
		activity.setField1("FIELD_1");
		activity.setField2("FIELD_2");
		activity.setField3("FIELD_3");
		activity.setUpdatedBy(3);
		return activity;
		
	}
	
}
