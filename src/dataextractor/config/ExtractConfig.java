package dataextractor.config;

import java.util.ArrayList;
import java.util.List;

public class ExtractConfig {

    private String templateFilename;
    private List<ExtractDetailConfig> extractDetails = new ArrayList<>();

    public String getTemplateFilename() {
        return templateFilename;
    }

    public void setTemplateFilename(String templateFilename) {
        this.templateFilename = templateFilename;
    }

    public List<ExtractDetailConfig> getExtractDetails() {
        return extractDetails;
    }

    public void setExtractDetails(List<ExtractDetailConfig> extractDetails) {
        this.extractDetails = extractDetails;
    }

    @Override
    public String toString() {
        return "templateFilename='" + templateFilename + '\'' +
                ", extractDetails=" + extractDetails;
    }
}
