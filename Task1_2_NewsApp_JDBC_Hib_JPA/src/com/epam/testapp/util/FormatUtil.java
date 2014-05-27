package com.epam.testapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public final class FormatUtil {
	private static final Logger LOGGER = Logger.getLogger(FormatUtil.class);

	public static Date getDateFromString(String dateAsString, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = formatter.parse(dateAsString);
		} catch (ParseException ex) {
			LOGGER.debug("Wrong date format");
		} catch (NullPointerException ex) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Wrong data to parse date: [dateAsString="
						+ dateAsString + ", pattern for parsing=" + pattern);
			}
		}
		return date;
	}
}
