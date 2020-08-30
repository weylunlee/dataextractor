package dataextractor;

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

public class DataExtractor {
    public static final void main(String[] args) {
        new DataExtractor().test();
    }

    private void test() {
        ExtractConfig extractConfig = new ConfigReader().loadConfig("config/extractconfig.yaml", ExtractConfig.class);

        for (ExtractDetailConfig extractDetailConfig : extractConfig.getExtractDetails()) {
            String s = readExtractDetail(extractDetailConfig);
            setValueToTemplate(extractConfig, extractDetailConfig, s);
        }
    }

    private void setValueToTemplate(ExtractConfig extractConfig, ExtractDetailConfig detailConfig, String value) {
        try {
            FileInputStream file = new FileInputStream(extractConfig.getTemplateFilename());

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(extractConfig.getTemplateSheet());
            XSSFRow row = sheet.getRow(detailConfig.getTemplateRow());
            Cell cell = row.getCell(detailConfig.getTemplateCol(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellValue(value);

            FileOutputStream outputStream = new FileOutputStream("c:/extractsample/folder1/out.xlsx");
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readExtractDetail(ExtractDetailConfig detailConfig) {
        FileLineReader fileLineReader = new FileLineReader(detailConfig.getSourceFilename());
        String line;

        while(true) {
            line = fileLineReader.readLine();
            if (null == line) {
                return null;
            }
            if (line.indexOf(detailConfig.getKey()) == detailConfig.getKeyColStart()-1) {
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
        return line.substring(detailConfig.getValueColStart()-1, detailConfig.getValueColStart()-1+detailConfig.getValueColLength());
    }
}
