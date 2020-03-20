package ma.screenindex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Allows you to access Screenindex year XML files using the
 * Document Object Model. 
 * 
 * INFO: Further Releases may use sax instead of dom
 * 
 * @author Linux-Fan, Ma_Sys.ma
 * @since Screenindex 1.0.3.0
 */
public class YearAccess {
	
	/**
	 * Provides the DOM <code>Document</code>
	 * @see org.w3c.dom.Document
	 */
	private Document doc;
	
	/**
	 * Saves the <code>File</code>-Object of the file containing
	 * the year data.
	 */
	private File dataFile;
	
	/**
	 * Provides a <code>Calendar</code> object, which is used
	 * by several methods.
	 * @see #YearAccess(String, Hashtable, SimpleDateFormat)
	 * @see #addActivity(Date, Date, LinkedList)
	 */
	private Calendar calendar;
	
	/**
	 * Provides a <code>SimpleDateFormat</code> for Date-IO
	 */
	private SimpleDateFormat sdf;
	
	/**
	 * Creates a <code>YearAccess</code> instance
	 * @param year The year to read, given as <code>String</code>
	 * @param cfg  The configuration data to use
	 * @param sdf  The <code>SimpleDateFormat</code> ({@link #sdf}) to use
	 */
	public YearAccess(String year, Hashtable<String, String> cfg, SimpleDateFormat sdf) {
		super();
		calendar = new GregorianCalendar();
		TimeActions.setCorrectTimeZoneFor(calendar);
		dataFile = new File(cfg.get("database-dir") + File.separator + year + ".xml");
		this.sdf = sdf;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			DocumentBuilder parser = factory.newDocumentBuilder();
			if(dataFile.exists()) {
				doc = parser.parse(dataFile);
			} else {
				// Create empty statistics file
				doc = parser.newDocument();
				doc.setXmlVersion("1.0");
				doc.setXmlStandalone(true);
				doc.appendChild(doc.createComment("Screenindex year data file, written by Screenindex Version " + Main.VERSION));
				Element stats = doc.createElement("stats");
				stats.setAttribute("year", year);
				stats.setIdAttribute("year", true);
				stats.setAttribute("created-by-version", Main.VERSION);
				doc.appendChild(stats);
			}
		} catch(Exception ex) {
			System.err.println("Unable to access year file \"" + dataFile.getAbsolutePath() + "\".");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Saves activity data given and reads data from lock file.
	 * Also reads and deletes lock file.
	 * 
	 * @param startDate Start date of activity
	 * @param endDate   End date of activity
	 * @param sdf       Passed to {@link #YearAccess(String, Hashtable, SimpleDateFormat)}
	 * @param cfg       Passed to {@link #YearAccess(String, Hashtable, SimpleDateFormat)}
	 * @param lockFile  The lock file to use.
	 */
	protected static void saveActivity(Date startDate, Date endDate, SimpleDateFormat sdf, Hashtable<String, String> cfg, File lockFile) {
		Calendar calendar = new GregorianCalendar();
		TimeActions.setCorrectTimeZoneFor(calendar);
		calendar.setTime(startDate);
		String yearString = String.valueOf(calendar.get(Calendar.YEAR));
		YearAccess xmlAccess = new YearAccess(yearString, cfg, sdf);
		// Read and then delete lock file
		LinkedList<String> messages = new LinkedList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(lockFile));
			String line;
			reader.readLine(); // Skip first line
			while((line = reader.readLine()) != null) {
				messages.add(line);
			}
			reader.close();
		} catch(IOException ex) {
			System.err.println("Unable to read lock file contents for getting messages.");
			ex.printStackTrace();
		}
		if(!lockFile.delete()) {
			System.err.println("Unable to delete lock file.");
		}
		// Save activity
		xmlAccess.addActivity(startDate, endDate, messages);
		try {
			xmlAccess.writeData();
		} catch(Exception ex) {
			System.err.println("Unable to write XML Activity file.");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Adds an Activity to the year saved in {@link #doc}
	 * @param start    Activity start
	 * @param end      Activity end
	 * @param messages Messages which belong to this activity
	 * @see NewActivity
	 */
	public void addActivity(Date start, Date end, LinkedList<String> messages) {
		calendar.setTime(start);
		// Get the month for "start"
		int monthNumber = calendar.get(Calendar.MONTH) + 1; 
		NodeList months = doc.getElementsByTagName("month");
		Node ourMonthNode = null;
		for(int i = 0; i < months.getLength(); i++) {
			Node cNode = months.item(i);
			int cMonthNumber = Integer.parseInt(cNode.getAttributes().getNamedItem("no").getNodeValue());
			if(cMonthNumber == monthNumber) {
				ourMonthNode = cNode;
				break;
			}
		}
		if(ourMonthNode == null) {
			ourMonthNode = doc.createElement("month");
			((Element) ourMonthNode).setAttribute("no", String.valueOf(monthNumber));
			doc.getDocumentElement().appendChild(ourMonthNode);
		}
		// Get the day for "start"
		int dayNumber = calendar.get(Calendar.DAY_OF_MONTH);
		NodeList days = ourMonthNode.getChildNodes();
		Node ourDayNode = null;
		for(int i = 0; i < days.getLength(); i++) {
			Node cNode = days.item(i);
			int cDayNumber = Integer.parseInt(cNode.getAttributes().getNamedItem("no").getNodeValue());
			if(cDayNumber == dayNumber) {
				ourDayNode = cNode;
				break;
			}
		}
		if(ourDayNode == null) {
			ourDayNode = doc.createElement("day");
			((Element) ourDayNode).setAttribute("no", String.valueOf(dayNumber));
			ourMonthNode.appendChild(ourDayNode);
		}
		// Create activity element
		Element activityElement = doc.createElement("activity");
		activityElement.setAttribute("start", sdf.format(start));
		activityElement.setAttribute("end",   sdf.format(end));
		// Create message tags
		for(Iterator<String> i = messages.iterator(); i.hasNext();) {
			Element messageElement = doc.createElement("message");
			messageElement.setTextContent(i.next());
			activityElement.appendChild(messageElement);
		}
		// Add activity element
		ourDayNode.appendChild(activityElement);
	}
	
	/**
	 * Returns an <code>ActivityList</code> for the given parameters.
	 * @param day   The day number (if any)
	 * @param month The month number (if any)
	 * @param dmy
	 * 		This parameter can have three values:
	 * 		<ul>
	 * 			<li>
	 * 				<b>d</b>: The <code>ActivityList</code> will just contain the activities of 
	 * 				the given day. Requires <code>day</code> and <code>month</code> to be given.
	 * 			</li>
	 * 			<li>
	 * 				<b>m</b>: The <code>ActivityList</code> will contain the month and day data.
	 * 				Requires <code>month</code> to be given.
	 * 			</li>
	 * 			<li>
	 * 				<b>y</b>: The <code>ActivityList</code> will contain all data of this year.
	 * 			</li>
	 * 		</ul>
	 * @return An <code>ActivityList</code> Object, influenced by the <code>dmy</code> parameter.
	 * @throws Exception If there is some error.
	 */
	private ActivityList getAcitivtyListFor(int day, int month, char dmy) throws Exception {
		// Remember:
		//  d        Day
		//  m        Month
		//  y        Year
		ActivityList ret = new ActivityList(String.valueOf(month));
		// Iterate trough all months
		NodeList months = doc.getElementsByTagName("month");
		for(int i = 0; i < months.getLength(); i++) {
			Node cMonth = months.item(i);
			// Iterate trough all days
			NodeList days = cMonth.getChildNodes();
			ActivityList cMonthList = new ActivityList(cMonth.getAttributes().getNamedItem("no").getNodeValue());
			for(int j = 0; j < days.getLength(); j++) {
				Node cDay = days.item(j);
				// Skip text nodes (19.03.2013)
				if(!cDay.hasChildNodes()) {
					continue;
				}
				// Create activity object (type is activity list)
				ActivityList activities = new ActivityList(cDay.getAttributes().getNamedItem("no").getNodeValue());
				NodeList activityList = cDay.getChildNodes();
				for(int k = 0; k < activityList.getLength(); k++) {
					Node cActivity = activityList.item(k);
					if(!cActivity.hasAttributes()) {
						continue;
					}
					NamedNodeMap attributes = cActivity.getAttributes();
					try {
						Date start = sdf.parse(attributes.getNamedItem("start").getNodeValue());
						Date end   = sdf.parse(attributes.getNamedItem("end").getNodeValue());
						LinkedList<String> messages = new LinkedList<String>();
						NodeList messageTags = cActivity.getChildNodes();
						for(int l = 0; l < messageTags.getLength(); l++) {
							messages.add(messageTags.item(l).getTextContent());
						}
						activities.addToInternalActivityList(new ActivityList(start, end, messages));
					} catch(Exception e) {
						Exception ex = new Exception("Unable to read activity " + k + ".");
						ex.initCause(e);
						throw ex;
					}
				}
				// Add activity object at the right place
				if((dmy == 'd' || dmy == 'm') && month == Integer.parseInt(cMonth.getAttributes().getNamedItem("no").getNodeValue())) {
					if(dmy == 'd' && day == Integer.parseInt((cDay.getAttributes().getNamedItem("no").getNodeValue()))) {
						ret = activities;
						break;
					} else if(dmy == 'm') {
						ret.addToInternalActivityList(activities);
					}
				} else if(dmy == 'y') {
					cMonthList.addToInternalActivityList(activities);
				}
			}
			if(dmy == 'y') {
				ret.addToInternalActivityList(cMonthList);
			}
		}
		return ret;
	}
	
	/**
	 * Creates an <code>AcitivtyList</code> using {@link #getAcitivtyListFor(int, int, char)}, but
	 * also parses the <code>in</code> param and creates the required <code>YearAccess</code> object.
	 * @param in  The string to be parsed (e.g.: "12.2010")
	 * @param dmy This is passed to {@link #getAcitivtyListFor(int, int, char)} as <code>dmy</code> parameter. E.g.: 'm'
	 * @param sdf Passed to {@link #YearAccess(String, Hashtable, SimpleDateFormat)}
	 * @param cfg Passed to {@link #YearAccess(String, Hashtable, SimpleDateFormat)}
	 * @return Return value of {@link #getAcitivtyListFor(int, int, char)}
	 * @throws Exception If {@link #getAcitivtyListFor(int, int, char)} returned an exception this method does, too.
	 * @see #YearAccess(String, Hashtable, SimpleDateFormat)
	 * @see #getAcitivtyListFor(int, int, char)
	 */
	public static ActivityList getActivityListFor(String in, char dmy, SimpleDateFormat sdf, Hashtable<String, String> cfg) throws Exception {
		String presetYear;
		int    presetMonth = -1;
		int    presetDay   = -1;
		switch(dmy) {
			case 'd': {
				presetDay   = Integer.parseInt(in.substring(0, 2));
				presetMonth = Integer.parseInt(in.substring(3, 5));
				presetYear  = in.substring(6);
				break;
			}
			case 'm': {
				presetMonth = Integer.parseInt(in.substring(0, 2));
				presetYear  = in.substring(3);
				break;
			}
			case 'y': {
				presetYear  = in;
				break;
			}
			default: {
				System.err.println("Cannot read data for '" + dmy + "'.");
				// Simply return an empty object.
				return new ActivityList("unknown"); 
			}
		}
		YearAccess access = new YearAccess(presetYear, cfg, sdf);
		return access.getAcitivtyListFor(presetDay, presetMonth, dmy);
	}
	
	/**
	 * Saves the XML file
	 * @throws IOException If there is an IO error
	 * @throws TransformerException If there is a problem with the DOM Tree
	 */
	protected void writeData() throws IOException, TransformerException {
		// TODO FORMAT OUTPUT!
		// Init
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		DOMSource src           = new DOMSource(doc);
		FileOutputStream out    = new FileOutputStream(dataFile);
		StreamResult result     = new StreamResult(out);
		// Write data
		transformer.transform(src, result);
	}
	
}
