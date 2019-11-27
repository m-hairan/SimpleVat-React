package com.simplevat.dao;

import com.simplevat.entity.Project;
import java.util.List;

/**
 * Created by Utkarsh Bhavsar on 20/03/17.
 */
public interface ProjectDao extends Dao<Integer, Project> {

    public void deleteByIds(List<Integer> ids);

}
