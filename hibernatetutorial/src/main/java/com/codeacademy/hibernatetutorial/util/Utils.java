package com.codeacademy.hibernatetutorial.util;

import com.codeacademy.hibernatetutorial.logger.AppLoggerLevel;
import com.codeacademy.hibernatetutorial.model.Employee;
import org.apache.log4j.Logger;
import org.hibernate.stat.CacheRegionStatistics;
import org.hibernate.stat.Statistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    private static String milisToTime(long milis) {
        Date date = new Date(milis);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public static void printStats(Logger logger, Statistics stats, int i) {
        CacheRegionStatistics secondLevelCacheStatistics =
                stats.getDomainDataRegionStatistics("employee");
        logger.log(AppLoggerLevel.APPLOGGER, "***** " + i + " *****");
        logger.log(AppLoggerLevel.APPLOGGER, "Fetch Count="
                + stats.getEntityFetchCount());
        logger.log(AppLoggerLevel.APPLOGGER, "Second Level Hit Count="
                + secondLevelCacheStatistics.getHitCount());
        logger.log(AppLoggerLevel.APPLOGGER, "Second Level Miss Count="
                + secondLevelCacheStatistics.getMissCount());
        logger.log(AppLoggerLevel.APPLOGGER, "Second Level Put Count="
                + secondLevelCacheStatistics.getPutCount());
    }

    public static void printData(Logger logger, Employee emp, Statistics stats, int count) {
        logger.log(AppLoggerLevel.APPLOGGER, count + ":: Name = " + emp.getName() + ", City = " + emp.getAddress().getCity());
        printStats(logger, stats, count);
    }

}
