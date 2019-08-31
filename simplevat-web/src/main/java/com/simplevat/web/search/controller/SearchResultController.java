/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.search.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.SearchView;
import com.simplevat.service.SearchViewService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author h
 */
@Controller
@SpringScopeView
public class SearchResultController implements Serializable {
    
    @Autowired
    private SearchViewService searchViewService;
    
    @Getter
    @Setter
    private String searchToken;
    
    @Getter
    @Setter
    private List<SearchView> searchResultList = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        searchToken = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("searchToken");
        searchResultList = searchViewService.getSearchedItem(searchToken);
    }
    
}
