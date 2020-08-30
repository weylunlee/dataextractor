package dataextractor.filereader;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileLineReader {

    private BufferedReader bufferedReader;

    public FileLineReader(String filename) {
        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readLine() {
        String lineString = null;

        try {
            lineString = bufferedReader.readLine();

            if (null == lineString) {
                bufferedReader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return lineString;
    }

    public void skipLine() {
        try {
            String lineString = bufferedReader.readLine();

            if (null == lineString) {
                bufferedReader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
