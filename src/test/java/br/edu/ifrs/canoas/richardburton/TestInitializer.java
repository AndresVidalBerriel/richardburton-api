package br.edu.ifrs.canoas.richardburton;

import io.undertow.util.FileUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class TestInitializer implements ServletContextListener {

    private static String TEST_INDEX_BASE = "hibernate.search.test.indexBase";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        InputStream propertiesStream = TestInitializer.class.getClassLoader().getResourceAsStream("app.properties");
        Properties properties = new Properties();

        try {

            properties.load(propertiesStream);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        try {

            File testIndexBase = new File(properties.getProperty(TEST_INDEX_BASE));
            if (testIndexBase.exists()) FileUtils.deleteRecursive(testIndexBase.toPath());
            if(!testIndexBase.mkdirs()) throw new RuntimeException("Could not create test index base directory");

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }

}