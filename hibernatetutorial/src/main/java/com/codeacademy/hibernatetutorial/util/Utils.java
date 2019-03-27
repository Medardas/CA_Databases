package com.codeacademy.hibernatetutorial.util;

import com.codeacademy.hibernatetutorial.logger.AppLoggerLevel;
import com.codeacademy.hibernatetutorial.model.Employee;
import org.apache.log4j.Logger;
import org.hibernate.stat.CacheRegionStatistics;
import org.hibernate.stat.Statistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class Utils {

    private static String milisToTime(long milis) {
        Date date = new Date(milis);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public static String random15CharString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
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
