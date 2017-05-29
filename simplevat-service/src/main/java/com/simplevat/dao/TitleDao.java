package com.simplevat.dao;

import java.util.List;

/**
 * Created by mohsin on 3/12/2017.
 */
public interface TitleDao<Integer, Title> extends Dao<Integer, Title> {

    List<Title> getTitles();
}
