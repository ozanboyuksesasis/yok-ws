package com.sesasis.donusum.yok.core.jasper;

import com.sesasis.donusum.yok.core.enums.JasperFileNames;
import com.sesasis.donusum.yok.core.enums.JasperMediaTypes;
import net.sf.jasperreports.engine.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class JasperUtil {
    Map<String, Object> parameters;
    URL jrxmlFileUrl;
    File jrxmlFile;
    URL logoUrl;

    public JasperUtil(Map<String, Object> parameters, URL jrxmlFileUrl) {
        this.parameters = parameters;
        this.jrxmlFileUrl = jrxmlFileUrl;
        this.logoUrl = jrxmlFileUrl;
    }
    public JasperUtil(Map<String, Object> parameters, File jrxmlFile) {
        this.parameters = parameters;
        this.jrxmlFile = jrxmlFile;
        this.logoUrl = jrxmlFileUrl;
    }

    public void saveFileToSystem(JasperFileNames jasperFileNames, String savedFileName) {
        try {
            JasperReport report = JasperCompileManager.compileReport(jrxmlFileUrl.getPath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            switch (jasperFileNames) {
                case PDF:
                    JasperExportManager.exportReportToPdfFile(jasperPrint, savedFileName + ".pdf");
                    break;
                case HTML:
                    JasperExportManager.exportReportToHtmlFile(jasperPrint, savedFileName + ".html");
                    break;
            }
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getFile(JasperFileNames jasperFileNames) {
        try {
            JasperReport report = JasperCompileManager.compileReport(jrxmlFileUrl.getPath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            switch (jasperFileNames) {
                case PDF:
                    return JasperExportManager.exportReportToPdf(jasperPrint);
                case HTML:
                    return JasperExportManager.exportReportToPdf(jasperPrint);
            }
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public byte[] getFileWithFile(JasperFileNames jasperFileNames) {
        try {
            JasperReport report = JasperCompileManager.compileReport(new FileInputStream(jrxmlFile));
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            switch (jasperFileNames) {
                case PDF:
                    return JasperExportManager.exportReportToPdf(jasperPrint);
                case HTML:
                    return JasperExportManager.exportReportToPdf(jasperPrint);
            }
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public JasperPrint getJasperPrint() {
        try {
            JasperReport report = JasperCompileManager.compileReport(jrxmlFileUrl.getPath());
            return JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void downloadFile(HttpServletResponse response, JasperMediaTypes jasperMediaTypes){
        try {
            response.setContentType(jasperMediaTypes.getDesc());
            //TODO:file name i parametrik almak gerekebilir
            response.setHeader("Content-disposition", "attachment; filename=test.pdf");
            ServletOutputStream output = response.getOutputStream();
            switch (jasperMediaTypes){
                case PDF: JasperExportManager.exportReportToPdfStream(getJasperPrint(), output); break;
                //case HTML: JasperExportManager.exportReportToHtmlFile(getJasperPrint(), output); break;
            }

            output.close();
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
