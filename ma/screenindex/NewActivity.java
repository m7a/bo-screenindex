package ma.screenindex;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A <code>NewActivity</code> is nothing else than an <code>Activity</code>,
 * but it can contain <code>messages</code>.
 * @author Linux-Fan, Ma_Sys.ma
 * @since Screenindex 1.0.3.0
 */
public class NewActivity extends Activity {

	/**
	 * The <code>NewActivity</code> is usually not serialized, so it
	 * does not have a "real" <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Stores the <code>messages</code> which are linked to
	 * this <code>Activity</code>
	 */
	private LinkedList<String> messages;
	
	/**
	 * This is the "usual" clock format. It is just <code>HH:mm:ss</code>
	 * @see java.text.SimpleDateFormat
	 */
	private static SimpleDateFormat clockFormat = new SimpleDateFormat("HH:mm:ss");
	
	/**
	 * Creates a <code>NewActivity</code> usigng the {@link Activity#Activity(Date, Date)}
	 * constructor and initializing the <code>messages</code> with the given parameter.
	 * @param start    Passed to {@link Activity#Activity(Date, Date)}
	 * @param end      Passed to {@link Activity#Activity(Date, Date)}
	 * @param messages Stored in the {@link #messages} field
	 * @see Activity#Activity(Date, Date)
	 */
	public NewActivity(Date start, Date end, LinkedList<String> messages) {
		super(start, end);
		this.messages = messages;
	}

	/**
	 * @return The message list.
	 * @see java.util.LinkedList
	 */
	public LinkedList<String> getMessages() {
		return messages;
	}
	
	/**
	 * Creates a <code>String</code> representation also including the
	 * messages.
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer(super.toString(clockFormat));
		for(Iterator<String> i = messages.iterator(); i.hasNext();) {
			ret.append('\n');
			ret.append("  Message: ");
			ret.append(i.next());
		}
		return ret.toString();
	}
	
}
