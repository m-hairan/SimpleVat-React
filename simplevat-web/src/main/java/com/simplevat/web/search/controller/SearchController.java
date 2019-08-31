/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.search.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

/**
 *
 * @author h
 */
@Controller
@SpringScopeView
public class SearchController implements Serializable {

    @Getter
    @Setter
    private String token;
    @PostConstruct
    public void init() {
    }

    public String redirectToSearchResult(String searchToken) {
        return "/pages/secure/search/search-result.xhtml?faces-redirect=true&searchToken=" + searchToken;
    }

}
