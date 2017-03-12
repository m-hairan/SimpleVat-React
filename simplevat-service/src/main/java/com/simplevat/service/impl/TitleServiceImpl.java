package com.simplevat.service.impl;

import com.simplevat.dao.TitleDao;
import com.simplevat.entity.Title;
import com.simplevat.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mohsin on 3/12/2017.
 */

@Service
public class TitleServiceImpl implements TitleService {

    @Autowired
    private TitleDao titleDao;

    @Override
    public List<Title> getTitles() {
        return titleDao.getTitles();
    }
}
