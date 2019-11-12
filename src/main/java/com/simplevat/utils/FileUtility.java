/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author admin
 */
public class FileUtility {

    private final String LOGO_IMAGE_PATH = "images/SimpleVatLogoFinalFinal.png";

    public static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    public MimeMultipart getMessageBody(String htmlText) throws MessagingException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file;
        if (classLoader.getResource(LOGO_IMAGE_PATH).getFile() != null) {
            file = new File(classLoader.getResource(LOGO_IMAGE_PATH).getFile());

            String logoImagePath = file.getAbsolutePath();
            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setContent(htmlText, "text/html");
            // add it
            multipart.addBodyPart(messageBodyPart);

            // second part (the image)
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(logoImagePath);

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<simplevatlogo>");

            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);

            return multipart;
        }
        return new MimeMultipart();
    }

}
