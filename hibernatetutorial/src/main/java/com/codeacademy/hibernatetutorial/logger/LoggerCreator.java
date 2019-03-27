package com.codeacademy.hibernatetutorial.logger;

import com.codeacademy.hibernatetutorial.FirstLevelCachingExample;
import org.apache.log4j.*;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.xml.DOMConfigurator;

import static org.apache.log4j.spi.Filter.DENY;
import static org.apache.log4j.spi.Filter.NEUTRAL;

public class LoggerCreator {
    public static Logger buildLogger(Class clazz) {
        Logger logger = Logger.getLogger(clazz);
        PropertyConfigurator.configure("log4j.properties");
        return logger;
    }
}
