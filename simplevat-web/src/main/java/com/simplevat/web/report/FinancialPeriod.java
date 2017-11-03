/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.report;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author admin
 */
@Getter
@Setter
public class FinancialPeriod {

    private Integer id;
    private String name;
    private Date startDate;
    private Date lastDate;
}
