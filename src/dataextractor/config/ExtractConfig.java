package dataextractor.config;

import java.util.ArrayList;
import java.util.List;

public class ExtractConfig {

    private String templateFilename;
    private int templateSheet;
    private List<ExtractDetailConfig> extractDetails = new ArrayList<>();

    public String getTemplateFilename() {
        return templateFilename;
    }

    public void setTemplateFilename(String templateFilename) {
        this.templateFilename = templateFilename;
    }

    public int getTemplateSheet() {
        return templateSheet;
    }

    public void setTemplateSheet(int templateSheet) {
        this.templateSheet = templateSheet;
    }

    public List<ExtractDetailConfig> getExtractDetails() {
        return extractDetails;
    }

    public void setExtractDetails(List<ExtractDetailConfig> extractDetails) {
        this.extractDetails = extractDetails;
    }
}
