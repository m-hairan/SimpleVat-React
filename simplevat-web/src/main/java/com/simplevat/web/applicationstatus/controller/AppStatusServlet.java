/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.applicationstatus.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet("/appStatusServlet")
public class AppStatusServlet extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbHost = System.getenv("SIMPLEVAT_DB_HOST");
            String dbName = System.getenv("SIMPLEVAT_DB");
            String dbUserName = System.getenv("SIMPLEVAT_DB_USER");
            String dbPass = System.getenv("SIMPLEVAT_DB_PASSWORD");
            connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + dbName, dbUserName, dbPass);
            String query = "SELECT * FROM USER";
            PreparedStatement ps = connection.prepareStatement(query.toUpperCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                resp.getWriter().write("userfound");
            } else {
                resp.getWriter().write("notfound");
            }
        } catch (Exception ex) {
            Logger.getLogger(AppStatusServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AppStatusServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
