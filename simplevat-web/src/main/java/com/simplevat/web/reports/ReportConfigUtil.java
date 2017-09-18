package com.simplevat.web.reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simplevat.web.contact.controller.ContactController;
import java.net.URISyntaxException;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class ReportConfigUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(ReportConfigUtil.class);

    public static String compileReport(String compileDir, String filename) {
        try {
            String jrxmlFile = compileDir + "/" + filename + ".jrxml";
            System.out.println("jrxmlFile  " + jrxmlFile);
            URL url = ReportConfigUtil.class.getClassLoader().getResource(jrxmlFile);
            File file = Paths.get(url.toURI()).toFile();
            String jrxmlFileAbsolutePath = file.getAbsolutePath();
            String jarsperFileAbsolutePath = jrxmlFileAbsolutePath.replaceAll("jrxml", "jasper");
            System.out.println("jrxmlFileAbsolutePath  " + jrxmlFileAbsolutePath);
            System.out.println("jarsperFileAbsolutePath  " + jarsperFileAbsolutePath);
            File tempFile = new File(jarsperFileAbsolutePath);
            if (tempFile.exists()) {
                LOGGER.info(jarsperFileAbsolutePath + " file Exists, Not compiling ");
            } else {
                LOGGER.info(jarsperFileAbsolutePath + " file does not Exists, compiling ");
                JasperCompileManager.compileReportToFile(file.getAbsolutePath(), jarsperFileAbsolutePath);
            }

            return jarsperFileAbsolutePath;
        } catch (Exception e) {
            LOGGER.error("Exception while compiling the JasperReport " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static String compileReportForStandalone(String compileDir, String filename) {
        try {
            String jrxmlFile = compileDir + "\\" + filename + ".jrxml";
            System.out.println("jrxmlFile  " + jrxmlFile);
            String jrxmlFileAbsolutePath = jrxmlFile;
            String jarsperFileAbsolutePath = jrxmlFileAbsolutePath.replaceAll("jrxml", "jasper");
            System.out.println("jrxmlFileAbsolutePath  " + jrxmlFileAbsolutePath);
            System.out.println("jarsperFileAbsolutePath  " + jarsperFileAbsolutePath);
            System.out.println(jarsperFileAbsolutePath + " file does not Exists, compiling ");
            JasperCompileManager.compileReportToFile(jrxmlFile, jarsperFileAbsolutePath);

            return jarsperFileAbsolutePath;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static JasperPrint fillReport(File reportFile, Map<String, Object> parameters, Connection conn)
            throws JRException {
        parameters.put("BaseDir", reportFile.getParentFile());

        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), parameters, conn);

        return jasperPrint;
    }

    public static JasperPrint fillReport(File reportFile, Map<String, Object> parameters)
            throws JRException {
        parameters.put("BaseDir", reportFile.getParentFile());
        JRDataSource dataSource = new net.sf.jasperreports.engine.JREmptyDataSource();
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), parameters);

        return jasperPrint;
    }
    
    

    private static void exportReport(JRAbstractExporter exporter, JasperPrint jasperPrint, PrintWriter out)
            throws JRException {
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);

        exporter.exportReport();
    }

    public static void exportReportAsHtml(JasperPrint jasperPrint, PrintWriter out) throws JRException {
        JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "ISO-8859-9");
        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "/SampleReportJSF/servlets/image?image=");

        exportReport(exporter, jasperPrint, out);
    }

    public static void exportReportAsExcelOld(JasperPrint jasperPrint, PrintWriter out)
            throws JRException, FileNotFoundException, IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        OutputStream outputfile = new FileOutputStream(new File("d:/dev/JasperReport1.xls"));//make sure to have the directory. excel
        // file will export here

        // coding For Excel:
        JRXlsExporter exporterXLS = new JRXlsExporter();
        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporterXLS.exportReport();
        outputfile.write(output.toByteArray());
        //exportReport(exporterXLS, jasperPrint, out);
    }

    public static void exportReportAsExcel(JasperPrint jasperPrint, HttpServletResponse response)
            throws JRException, FileNotFoundException, IOException {
        ServletOutputStream out = response.getOutputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JRXlsExporter exporterXLS = new JRXlsExporter();
        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
        //exporterXLS.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        exporterXLS.exportReport();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "inline; filename=SimpleVatReport.xls");
        out.write(output.toByteArray());
        out.flush();
        out.close();
    }

    public static String getFileRealPath(String compileDir, String filename) throws Exception {
        String jrxmlFile = compileDir + "/" + filename;
        URL url = ReportConfigUtil.class.getClassLoader().getResource(jrxmlFile);
        return Paths.get(url.toURI()).toRealPath().toString();
    }

}
