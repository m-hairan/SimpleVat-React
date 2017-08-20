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
import com.simplevat.entity.ActivityId;
import com.simplevat.test.common.BaseManagerTest;

@Ignore
public class ActivityDaoImplTest extends BaseManagerTest {
	
	@Autowired
	private ActivityDao activityDao;
	
	private static final String MODULE_CODE="MODULE_CODE";
	private static final String ACTIVITY_CODE="ACTIVITY_CODE";
	
	@Before
	public void setUp() {
		activityDao.update(getActivity());
		Activity activity = activityDao.findByPK(getActivity().getActivityId());
		assertNotNull(activity);
	}
	
	
	@After
	public void tearDown() {
		activityDao.delete(getActivity());
		Activity activity = activityDao.findByPK(getActivity().getActivityId());
		assertNull(activity);
	}

	@Test
	public void testUpdate() {
		Activity activity = getActivity();
		activity.setField1("CHANGED");
		activity = activityDao.update(activity);
		assertNotNull(activity);
		assertTrue("Field1 did not change ", activity.getField1().equals("CHANGED"));
	}
	
	private Activity getActivity() {
		Activity activity = new Activity();
		activity.setField1("FIELD_1");
		activity.setField2("FIELD_2");
		activity.setField3("FIELD_3");
		activity.setUpdatedBy(3);
		ActivityId id = new ActivityId();
		id.setActivityCode(ACTIVITY_CODE);
		id.setModuleCode(MODULE_CODE);
		activity.setActivityId(id);
		return activity;
		
	}
	
}
