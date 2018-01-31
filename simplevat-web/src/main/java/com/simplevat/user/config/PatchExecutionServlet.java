/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.user.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.poi.hssf.record.Record;

/**
 *
 * @author admin
 */
@WebServlet
public class PatchExecutionServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        Connection connection = null;
        try {
            StringBuilder patchPath = new StringBuilder(getServletContext().getRealPath("/resources/patch/"));
            Class.forName("com.mysql.jdbc.Driver");
            String dbHost = System.getenv("SIMPLEVAT_DB_HOST");
            String dbName = System.getenv("SIMPLEVAT_DB");
            String dbUserName = System.getenv("SIMPLEVAT_DB_USER");
            String dbPass = System.getenv("SIMPLEVAT_DB_PASSWORD");
            connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + dbName, dbUserName, dbPass);
            String query = "SELECT * FROM PATCH WHERE PATCH_NO = ?";
            File file = new File(patchPath.toString());
            List<String> patchNoList = Arrays.asList(file.list());
            Collections.sort(patchNoList);
            System.out.println("=========================="+patchNoList);
            for (String folderName : patchNoList) {
                PreparedStatement ps = connection.prepareStatement(query.toUpperCase());
                ps.setString(1, folderName);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    File patchfile = new File(patchPath.toString() + "/" + folderName);
                    for (String scriptName : patchfile.list()) {
                        File scriptfile = new File(patchPath.toString() + "/" + folderName + "/" + scriptName);
                        if (scriptfile.exists()) { 
                            ScriptRunner sr = new ScriptRunner(connection);
                            Reader reader = new BufferedReader(
                                    new FileReader(scriptfile.getPath()));
                            sr.runScript(reader);
                        }
                    } 
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(PatchExecutionServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(PatchExecutionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
