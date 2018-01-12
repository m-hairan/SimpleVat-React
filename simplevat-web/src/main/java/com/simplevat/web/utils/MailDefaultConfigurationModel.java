/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.utils;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author h
 */
@Data
public class MailDefaultConfigurationModel implements Serializable {

    String mailhost = System.getenv("SIMPLEVAT_MAIL_HOST");
    String mailport = System.getenv("SIMPLEVAT_MAIL_PORT");
    String mailusername = System.getenv("SIMPLEVAT_MAIL_USERNAME");
    String mailpassword = System.getenv("SIMPLEVAT_MAIL_PASSWORD");
    String mailsmtpAuth = System.getenv("SIMPLEVAT_MAIL_SMTP_AUTH");
    String mailstmpStartTLSEnable = System.getenv("SIMPLEVAT_MAIL_SMTP_STARTTLS_ENABLE");
}
