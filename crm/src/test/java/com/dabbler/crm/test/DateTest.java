package com.dabbler.crm.test;

import com.dabbler.crm.commons.utils.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class DateTest {

    @Test
    public void test() {
        System.out.println(DateUtils.formatDate(new Date()));
        System.out.println(DateUtils.formatTime(new Date()));
    }
}
