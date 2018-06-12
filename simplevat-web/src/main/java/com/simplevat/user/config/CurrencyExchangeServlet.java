/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.user.config;

import com.simplevat.entity.Company;
import com.simplevat.entity.Currency;
import com.simplevat.service.CompanyService;
import com.simplevat.service.CurrencyExchangeService;
import com.simplevat.service.CurrencyService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author admin
 */
@WebServlet
@Component
public class CurrencyExchangeServlet extends HttpServlet {

    @Autowired
    MyTimerTask myTimerTask;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        // every night at 12am you run your task
         Timer timer = new Timer();
         myTimerTask.servletContext = servletContext;
         timer.scheduleAtFixedRate(myTimerTask, today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // period: 1 day
         //timer.schedule(myTimerTask, 0, 180000); // period: 1 day

    }

    @Component
    public class MyTimerTask extends TimerTask {

        private ServletContext servletContext;
        @Autowired
        private CompanyService companyService;
        @Autowired
        private CurrencyExchangeService currencyExchangeService;
        @Autowired
        private CurrencyService currencyService;

        public MyTimerTask(ServletContext servletContext) {
            this.servletContext = servletContext;
        }

        @Override
        public void run() {
            System.out.println("Timer task started at:" + new Date());
            completeTask();
            System.out.println("Timer task finished at:" + new Date());
        }

        private void completeTask() {
            try {
                System.out.println("companyId");
                Object companyId = servletContext.getAttribute("companyId");
                System.out.println("companyId==="+companyId);
                if (companyId != null) {
                    Company company = companyService.findByPK(Integer.parseInt(companyId.toString()));
                    List<Currency> currencys = currencyService.getCurrencyList(company.getCurrencyCode());
                    currencyExchangeService.saveExchangeCurrencies(company.getCurrencyCode(), currencys);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
