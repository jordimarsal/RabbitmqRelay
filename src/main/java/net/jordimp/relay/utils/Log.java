package net.jordimp.relay.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

	private static final Logger LOGGER = LoggerFactory.getLogger(Log.class);
	
	private Log() {
	}
	
	public static void debug(String clazz, String message) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("%s -> %s", clazz, message));
		}
	}

}
