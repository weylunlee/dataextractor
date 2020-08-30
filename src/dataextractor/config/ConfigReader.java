package dataextractor.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.InputStream;

public final class ConfigReader {

    public <T> T loadConfigRelativePath(String configFile, Class<T> clazz) {
        Yaml yaml = new Yaml(new Constructor(clazz));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);
        return yaml.load(inputStream);
    }

    public <T> T loadConfigAbsolutePath(String configFile, Class<T> clazz) {
        try {
            Yaml yaml = new Yaml(new Constructor(clazz));
            InputStream inputStream = new FileInputStream(configFile);
            return yaml.load(inputStream);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
