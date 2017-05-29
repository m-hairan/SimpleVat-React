package com.simplevat.service;

import java.util.List;

/**
 * Created by mohsin on 3/12/2017.
 */
public interface TitleService<Integer, Title> extends SimpleVatService<Integer, Title> {

    List<Title> getTitles();
}
