/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.setting.controller;

import com.github.javaplugs.jsf.SpringScopeSession;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.stereotype.Controller;

/**
 *
 * @author HP
 */
@Controller
@SpringScopeSession
public class DefaultStreamContentInvoicePdf {

    public static final String STREAMED_CONTENT_PDF = "STREAMED_CONTENT_PDF";
    private  int count  = 0;

    public  DefaultStreamedContent getInvoicepdf() {
        count++;
        Object objPdf = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STREAMED_CONTENT_PDF);
        System.out.println("====Object===");
        if (objPdf != null) {
            return new DefaultStreamedContent(new ByteArrayInputStream(((ByteArrayOutputStream) objPdf).toByteArray()), "application/pdf");
        }
        return null;
    }
}
