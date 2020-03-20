package ma.screenindex;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the old activity class. Activity objects were stored
 * in a serialized file.
 * @author Linux-Fan, Ma_Sys.ma
 * @since Screenindex 1.0.0.0
 */
public class Activity implements Serializable {
	
	/**
	 * Allows Screenindex to read serialized objects, even
	 * if this class has been changed a bit.
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = -4533924675019972509L;
	
	/**
	 * Stores the start date.
	 * @see java.util.Date
	 */
	private Date start;
	
	/**
	 * Stores the end date.
	 * @see java.util.Date
	 */
	private Date end;
	
	/**
	 * Creates a new <code>Activity</code> object
	 * @param start Activity start date.
	 * @param end   Activity end date.
	 * @see #start
	 * @see #end
	 */
	public Activity(Date start, Date end) {
		this.start = start;
		this.end = end;
	}
	
	/**
	 * @return The start date which was set by the constructor.
	 */
	public Date getStart() {
		return start;
	}
	
	/**
	 * @return The end date which was set by the constructor.
	 */
	public Date getEnd() {
		return end;
	}
	
	/**
	 * @return The length in seconds between <code>start</code> and <code>end</code>.
	 * @see #start
	 * @see #end 
	 */
	public long getLength() {
		return (end.getTime() - start.getTime()) / 1000;
	}
	
	/**
	 * Formats the given number of seconds into a <code>String</code>, which can
	 * be simply read.
	 * 
	 * This method is <strong>important</strong>.
	 * 
	 * @param seconds The number of seconds which should be formatted
	 * @return e.g.: <code>5 h, 10 min, 30 sec (xxx sec)</code> 
	 */
	public static String formatSeconds(long seconds) {
		double minutes_d = seconds/60.0d;
		long minutes_l = seconds/60;
		long seconds_g = (long) ((minutes_d - minutes_l)*60.0d);
		long hours_l = minutes_l/60;
		double hours_d = minutes_l/60.0d;
		long minutes_g = (long) ((hours_d - hours_l)*60.0d);
		return hours_l + " h, " + minutes_g + " min, " + seconds_g + " sec (" + seconds + " sec)";
	}
	
	/**
	 * Returns a <code>String</code> representation with the given <code>SimpleDateFormat</code>
	 * @param sdf A <code><u>S</u>imple<u>D</u>ate<u>F</u>ormat</code> instance.
	 * @return A string representation
	 * @see java.text.SimpleDateFormat
	 * @see #formatSeconds(long)
	 */
	public String toString(SimpleDateFormat sdf) {
		return "Activity from " + sdf.format(start) + " to " + sdf.format(end) + ": " + formatSeconds(getLength());
	}
	
	/**
	 * Invokes {@link #toString(SimpleDateFormat)} with the following
	 * <code>SimpleDateFormat</code>: <code>HH:mm:ss</code>.
	 * This method is not efficient, because it always needs to create
	 * a new <code>SimpleDateFormat</code> Object.
	 * @see #toString(SimpleDateFormat)
	 */
	public String toString() {
		return toString(new SimpleDateFormat("HH:mm:ss"));
	}

}
