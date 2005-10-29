package org.opensails.sails.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creates a unique identifier, especially useful when testing, based on the
 * current time.
 */
public class TimeUniqueIdGenerator implements IIdGenerator<Long> {
	public static final DateFormat DATE_FOMATTER = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	protected String baseId;
	protected int extension;
	protected Date latestTimeStamp;

	public synchronized Long next() {
		Date timeStamp = new Date();
		if (timeStamp.equals(latestTimeStamp)) extension++;
		else {
			latestTimeStamp = timeStamp;
			baseId = DATE_FOMATTER.format(latestTimeStamp);
			extension = 0;
		}
		return Long.valueOf(baseId + extension);
	}
}
