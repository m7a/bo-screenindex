package ma.screenindex;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Allows you to access commonly used time functions.
 * @author Linux-Fan, Ma_Sys.ma
 * @since Screenindex 1.0.0.0
 */
public class TimeActions {

	/**
	 * A <code>Calendar</calendar> which will be used to return dates.
	 * @see java.util.Calendar
	 */
	private static Calendar calendar;
	
	/**
	 * initializes <code>calendar</code> as a <code>GregorianCalendar</code>
	 * and applies <code>setCorrectTimeZoneFor()</code>.
	 * @see java.util.java.util.GregorianCalendar
	 * @see #setCorrectTimeZoneFor(Calendar)
	 */
	static {
		calendar = new GregorianCalendar();
		setCorrectTimeZoneFor(calendar);
	}
	
	/**
	 * Sets the "CET" time zone for <code>c</code>
	 * @param c The <code>Calendar</code> which will be setted to the time Zone "CET"
	 * @see java.util.TimeZone
	 */
	public static void setCorrectTimeZoneFor(Calendar c) {
		c.setTimeZone(TimeZone.getTimeZone("CET"));
	}
	
	/**
	 * @return The date now from <code>calendar</code>
	 */
	public static Date date() {
		calendar.setTime(new Date());
		return calendar.getTime();
	}
	
}
