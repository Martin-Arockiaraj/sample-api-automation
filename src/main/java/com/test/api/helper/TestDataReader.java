package com.test.api.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

// This page defines the base test class which provides helper methods and initialization helper which will be helpful to debug and reuse the code

public class TestDataReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataReader.class);
    private static String envfile = "env.properties";
    private static Properties prop;

    @BeforeClass
    public static void setup() {
        prop = initProperties();
    }

    // This method will load env.properties file when a child extends it
    private static Properties initProperties() {
        Properties prop = new Properties();

        try {
            prop.load(TestDataReader.class.getClassLoader().getResourceAsStream(envfile));
        } catch (Exception ex) {
            throw new RuntimeException("Unable to load property file " + envfile);
        }

        return prop;
    }

    // This method is used to extract data from property file
    protected static String getPropValue(String key) {
        String value = prop.getProperty(key);
        Assert.assertNotNull(value, String.format("%s key is missing", key));

        return value;
    }

    // This method will load the content of any given file and returns it as a string
    protected static String loadFile(String file) {
        try {
            InputStream stream = TestDataReader.class.getClassLoader().getResourceAsStream(file);
            return IOUtils.toString(stream);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Unable load file " + file);
        }
    }
}