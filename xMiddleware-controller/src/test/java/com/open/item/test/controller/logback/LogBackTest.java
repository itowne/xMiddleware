/**
 * 
 */
package com.open.item.test.controller.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.open.item.test.controller.AbstractBaseTest;

/**
 * @author towne
 * @date Sep 14, 2018
 */
public class LogBackTest extends AbstractBaseTest {
    private static Logger log = LoggerFactory.getLogger(LogBackTest.class);

    @Test
    public void test() {
        log.debug("###i am debug");
        log.info("###i am info");
        log.error("###i am error");
    }

}
