package ma.screenindex;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Allows you to store one or more activities recursively.
 * @author Linux-Fan, Ma_Sys.ma
 * @since Screenindex 1.0.3.0
 */
public class ActivityList extends NewActivity {
	
	/**
	 * This is only for compatibility reasons.
	 * Do not rely on this UID being correct.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * If this <code>ActivityList</code> contains other
	 * <code>ActivityList</code> Objects, they are stored
	 * in this list. If not, it is <code>null</code>
	 */
	private LinkedList<ActivityList> internalActivityList;
	
	/**
	 * If this <code>ActivityList</code> for example contains
	 * a month, it has an <code>internalActivityList</code>
	 * object, containing all days of this month.
	 * The month number is stored in this field then.
	 * 
	 * If this <code>ActivityList</code> for example contains
	 * a day, this field contains the day number.
	 * 
	 * This allows <code>Main</code> to display an activity
	 * correctly.
	 * 
	 * @see ma.screenindex.Main
	 */
	private String subNumber;
	
	/**
	 * Creates a new <code>ActivityList</code>.
	 * When using this constructor, it is set to be not using
	 * an <code>internalActivityList</code>
	 * @param start    Activity start date
	 * @param end      Activity end date
	 * @param messages Message list
	 * @see #internalActivityList
	 * @see #ActivityList(String)
	 * @see ma.screenindex.NewActivity#NewActivity(Date, Date, LinkedList)
	 */
	public ActivityList(Date start, Date end, LinkedList<String> messages) {
		super(start, end, messages);
		internalActivityList = null;
	}
	
	/**
	 * Ceates a new <code>ActivityList</code>. This
	 * constructor creates an <code>ActivityList</code> object, which
	 * needs to contain an <code>internalActivityList</code>.
	 * @param subNumber See {@link #subNumber} for details.
	 * @see #subNumber
	 * @see #ActivityList(Date, Date, LinkedList)
	 */
	public ActivityList(String subNumber) {
		super(null, null, null);
		this.subNumber = subNumber;
		internalActivityList = new LinkedList<ActivityList>();
	}
	
	/**
	 * Adds an <code>ActivityList</code> Object to the
	 * <code>internalActivityList</code>. This can only be done
	 * when this <code>ActivityList</code> was created via the
	 * {@link #ActivityList(String)} constructor.
	 * @param activityList The <code>ActivityList</code> to be added.
	 * @see #ActivityList(String)
	 */
	public void addToInternalActivityList(ActivityList activityList) {
		internalActivityList.add(activityList);
	}
	
	/**
	 * @return An <code>Iterator</code> of <code>internalActivityList</code>
	 * @see #internalActivityList
	 * @see java.util.Iterator
	 */
	public Iterator<ActivityList> getInternalActivityList() {
		return internalActivityList.iterator();
	}
	
	/**
	 * @return {@link #subNumber}
	 */
	public String getSubNumber() {
		return subNumber;
	}
	
	/**
	 * Gets the length of all <code>Activity</code> objects from
	 * <code>internalActivityList</code> or the length of the
	 * <code>Activity</code> stored within this object.
	 * If this is rather an Activity Object than a list, it
	 * returns the activity length in seconds.
	 * 
	 * @see #internalActivityList
	 * @see #ActivityList(String)
	 * @see #ActivityList(Date, Date, LinkedList)
	 * @return The total length in seconds
	 */
	public long getLength() {
		if(getStart() == null && getEnd() == null) {
			long ret = 0;
			for(Iterator<ActivityList> i = internalActivityList.iterator(); i.hasNext();) {
				ret = ret + i.next().getLength();
			}
			return ret;
		} else {
			return super.getLength();
		}
	}
	
	/**
	 * @return 
	 * 		A <code>String</code> representation created from 
	 * 		the data stored in this <code>ActivityList</code>.
	 * @see ma.screenindex.NewActivity#toString()
	 * @see ma.screenindex.Activity#formatSeconds(long)
	 */
	public String toString() {
		if(getStart() == null && getEnd() == null) {
			return formatSeconds(getLength());
		} else {
			return super.toString();
		}
	}
	
	/**
	 * @return Returns the number of days in <code>internalActivityList</code>.
	 */
	public int getDayCount() {
		return internalActivityList.size();
	}
	
}
