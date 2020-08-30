package dataextractor.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public final class ConfigReader {

    public <T> T loadConfig(String configFile, Class<T> clazz) {

        Yaml yaml = new Yaml(new Constructor(clazz));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile);
        return yaml.load(inputStream);
    }

}
