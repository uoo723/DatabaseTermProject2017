package com.sangwoo.possystem.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilsTest {

    private static final Logger logger = LogManager.getLogger();

    @Test
    public void convertToBirth() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(0, 8, 6);
        assertEquals("0906", DateUtils.convertToBirthString(cal.getTime()));
    }

    @Test
    public void convertToBirthDate() throws Exception {
        Date date = DateUtils.convertToBirthDate("0906");
        logger.info(date);
        assertNotNull(date);
    }

    @Test
    public void convertToString() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 5, 10, 14, 30, 0);
        assertEquals("2017-06-10 14:30:00", DateUtils.convertToString(cal.getTime()));
    }

    @Test
    public void convertBeginOfDay() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 5, 10, 14, 30, 0);
        Date beginOfDay = DateUtils.convertBeginOfDay(cal.getTime());
        assertEquals("2017-06-10 00:00:00", DateUtils.convertToString(beginOfDay));
    }

    @Test
    public void convertEndOfDay() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 5, 10, 14, 30, 0);
        Date endOfDay = DateUtils.convertEndOfDay(cal.getTime());
        assertEquals("2017-06-10 23:59:59", DateUtils.convertToString(endOfDay));
    }
}