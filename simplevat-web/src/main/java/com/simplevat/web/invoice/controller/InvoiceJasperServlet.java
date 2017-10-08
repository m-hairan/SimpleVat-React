package com.simplevat.web.invoice.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author admin
 */
@WebServlet("/invoiceJaper.pdf")
public class InvoiceJasperServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Autowired
    private InvoiceUtil invoiceUtil;
    
    @Override
     public void init(ServletConfig config) throws ServletException {
        super.init(config);
         SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
          config.getServletContext());
      }
     
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            invoiceUtil.prepareReport(request, response,Integer.parseInt(request.getSession().getAttribute("invoiceId").toString()));
        } catch (JRException ex) {
            Logger.getLogger(InvoiceJasperServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
