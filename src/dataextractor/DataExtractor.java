package dataextractor;

import dataextractor.config.AppConfig;
import dataextractor.config.ConfigReader;
import dataextractor.config.ExtractConfig;
import dataextractor.config.ExtractDetailConfig;
import dataextractor.filereader.FileLineReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataExtractor {
    public static final void main(String[] args) {
        new DataExtractor().extract();
    }

    private void extract() {
        AppConfig appConfig = new ConfigReader().loadConfigRelativePath("config/appconfig.yaml", AppConfig.class);
        appConfig.getExtractFolders().forEach(this::extractValueSet);
    }

    private void extractValueSet(String setFolderName) {
        System.out.println("____________________\nPROCESSING " + setFolderName);

        String xxname = setFolderName + "extractconfig.yaml";

        ExtractConfig extractConfig = new ConfigReader().loadConfigAbsolutePath(
                setFolderName + "extractconfig.yaml", ExtractConfig.class);

        XSSFWorkbook workbook = getTemplateWorkbook(setFolderName, extractConfig.getTemplateFilename());

        if (workbook == null) {
            System.out.println("ERROR reading template file " + setFolderName + extractConfig.getTemplateFilename());
            return;
        }

        for (ExtractDetailConfig extractDetailConfig : extractConfig.getExtractDetails()) {
            Double value = extractValue(setFolderName, extractDetailConfig);

            if (null == value) {
                System.out.println("ERROR trying to extract value from " + extractDetailConfig);
                return;
            }

            setValueToTemplate(workbook,
                    extractDetailConfig.getTemplateSheet()-1,
                    extractDetailConfig.getTemplateRow()-1,
                    extractDetailConfig.getTemplateCol()-1,
                    value);
        }

        writeTemplate(workbook, setFolderName, extractConfig.getTemplateFilename());
    }

    private XSSFWorkbook getTemplateWorkbook(String setFolderName, String filename) {
        XSSFWorkbook workbook;

        try {
            FileInputStream file = new FileInputStream(setFolderName + filename);
            workbook = new XSSFWorkbook(file);
        }
        catch(Exception e) {
            workbook = null;
        }

        return workbook;
    }

    private void setValueToTemplate(XSSFWorkbook workbook, int sheetNum, int rowNum, int colNum, Double value) {
        XSSFSheet sheet = workbook.getSheetAt(sheetNum);
        XSSFRow row = sheet.getRow(rowNum);
        Cell cell = row.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        cell.setCellValue(value);
    }

    private void writeTemplate(XSSFWorkbook workbook, String setFolderName, String templateFilename) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd.HHmm").format(new Date());
            String filename = setFolderName + timestamp + "_" + templateFilename;

            FileOutputStream outputStream = new FileOutputStream(filename);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();

            System.out.println("SUCCESSFULLY written out " + filename);
        }
        catch (Exception e) {
            System.out.println("ERROR: processing folder setFolderName");
        }
    }

    private Double extractValue(String setFolderName, ExtractDetailConfig detailConfig) {
        FileLineReader fileLineReader = new FileLineReader(setFolderName + detailConfig.getSourceFilename());
        String line;

        while(true) {
            line = fileLineReader.readLine();
            if (null == line) {
                return null;
            }

            int indexof = line.indexOf(detailConfig.getKey());
            if (indexof != -1 && indexof == detailConfig.getKeyColStart()-1) {
                // found key
                break;
            }
        }

        // find value line by skipping offset
        for (int i=0; i<detailConfig.getValueRowOffset(); i++) {
            line = fileLineReader.readLine();
            if (null == line) {
                return null;
            }
        }

        // line at this point should contain value
        String stringValue = line.substring(
                detailConfig.getValueColStart()-1,
                detailConfig.getValueColStart()-1+detailConfig.getValueColLength());

        try {
            System.out.println("Read value [" + stringValue + "] from " + detailConfig);
            return Double.valueOf(stringValue);
        }
        catch(Exception e) {
            return -999.0;
        }
    }
}
