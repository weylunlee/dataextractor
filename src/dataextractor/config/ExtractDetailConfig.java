package dataextractor.config;

public class ExtractDetailConfig {
    private String sourceFilename;
    private int keyColStart;
    private String key;
    private int valueRowOffset;
    private int valueColStart;
    private int valueColLength;
    private int templateRow;
    private int templateCol;

    public String getSourceFilename() {
        return sourceFilename;
    }

    public void setSourceFilename(String sourceFilename) {
        this.sourceFilename = sourceFilename;
    }

    public int getKeyColStart() {
        return keyColStart;
    }

    public void setKeyColStart(int keyColStart) {
        this.keyColStart = keyColStart;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValueRowOffset() {
        return valueRowOffset;
    }

    public void setValueRowOffset(int valueRowOffset) {
        this.valueRowOffset = valueRowOffset;
    }

    public int getValueColStart() {
        return valueColStart;
    }

    public void setValueColStart(int valueColStart) {
        this.valueColStart = valueColStart;
    }

    public int getValueColLength() {
        return valueColLength;
    }

    public void setValueColLength(int valueColLength) {
        this.valueColLength = valueColLength;
    }

    public int getTemplateRow() {
        return templateRow;
    }

    public void setTemplateRow(int templateRow) {
        this.templateRow = templateRow;
    }

    public int getTemplateCol() {
        return templateCol;
    }

    public void setTemplateCol(int templateCol) {
        this.templateCol = templateCol;
    }

    @Override
    public String toString() {
        return "sourceFilename='" + sourceFilename + '\'' +
                ", keyColStart=" + keyColStart +
                ", key='" + key + '\'' +
                ", valueRowOffset=" + valueRowOffset +
                ", valueColStart=" + valueColStart +
                ", valueColLength=" + valueColLength +
                ", templateRow=" + templateRow +
                ", templateCol=" + templateCol +
                '}';
    }
}
