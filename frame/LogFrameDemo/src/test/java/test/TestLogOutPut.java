package test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogOutPut {

	public static Logger logger = LoggerFactory.getLogger(TestLogOutPut.class);
	
	@Test
	public void testLog4j() {
		logger.debug("dd");
	}

	@Test
	public void testLogback() {
		logger.debug("dd");
	}

	@Test
	public void testJdkLog() {
		logger.info("dd");
		logger.error("dd");
	}

	@Test
	public void testCommonLog() {
		logger.info("dd");
		logger.error("dd");
	}

	@Test
	public void testSlf4j() {
		logger.info("dd");
		logger.error("dd");
	}
}
