package ma.screenindex;

import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Allows you to export a year recorded by Screenindex as
 * a XHMTL file.
 * 
 * @author Linux-Fan, Ma_Sys.ma
 * @since Screenindex 1.0.3.0
 * @version 1.0.0.1
 */
public class HTMLExport {
	
	private static final Hashtable<Integer, String> MONTH_FOR_NUMBER;
	
	static {
		MONTH_FOR_NUMBER = new Hashtable<Integer, String>();
		MONTH_FOR_NUMBER.put( 1, "January");
		MONTH_FOR_NUMBER.put( 2, "Febuary");
		MONTH_FOR_NUMBER.put( 3, "March");
		MONTH_FOR_NUMBER.put( 4, "April");
		MONTH_FOR_NUMBER.put( 5, "May");
		MONTH_FOR_NUMBER.put( 6, "June");
		MONTH_FOR_NUMBER.put( 7, "July");
		MONTH_FOR_NUMBER.put( 8, "August");
		MONTH_FOR_NUMBER.put( 9, "September");
		MONTH_FOR_NUMBER.put(10, "October");
		MONTH_FOR_NUMBER.put(11, "November");
		MONTH_FOR_NUMBER.put(12, "December");
	}
	
	/**
	 * Gets a whole XHTML file string for the specified data.
	 * @param activities The data to use.
	 * @return A <code>String</code> containing a complete XHTML document.
	 */
	public static String getHTMLRepresentationFor(ActivityList activities, String year) throws Exception {
		SimpleDateFormat clockFormat = new SimpleDateFormat("HH:mm:ss");
		StringBuffer r = new StringBuffer();
		o(r, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		o(r, "<!-- Exported Screenindex data file for " + year + " -->");
		o(r, "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		o(r, "<html version=\"-//W3C//DTD XHTML 1.1//EN\" xmlns=\"http://www.w3.org/1999/xhtml\" " + 
				"xml:lang=\"en\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " + 
				"xsi:schemaLocation=\"http://www.w3.org/1999/xhtml http://www.w3.org/MarkUp/SCHEMA/xhtml11.xsd\" " + 
				"lang=\"en\">");
		o(r, "\t<head>");
		o(r, "\t\t<title>Screenindex exported data year " + year + "</title>");
		o(r, "\t\t<style type=\"text/css\">");
		o(r, "\t\t\t<![CDATA[");
		o(r, "\t\t\t* {");
		o(r, "\t\t\t\tmargin: 0px;");
		o(r, "\t\t\t\tpadding: 0px;");
		o(r, "\t\t\t}");
		o(r, "\t\t\tbody {");
		o(r, "\t\t\t\tfont-size: 90%;");
		o(r, "\t\t\t\tfont-family: \"Times\", serif;");
		o(r, "\t\t\t\tbackground-color: #ffccee;");
		o(r, "\t\t\t}");
		o(r, "\t\t\tul {");
		o(r, "\t\t\t\tmargin-left: 22px;");
		o(r, "\t\t\t}");
		o(r, "\t\t\th2 {");
		o(r, "\t\t\t\tfont-size: 150%;");
		o(r, "\t\t\t}");
		o(r, "\t\t\th3 {");
		o(r, "\t\t\t\tfont-size: 120%;");
		o(r, "\t\t\t}");
		o(r, "\t\t\tsvg text {");
		o(r, "\t\t\t\tfont-family: \"Times\", serif;");
		o(r, "\t\t\t\tfont-size: 73%;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t#header {");
		o(r, "\t\t\t\tbackground-color: #ffaaff;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t#header h1 {");
		o(r, "\t\t\t\tfont-size: 200%;");
		o(r, "\t\t\t\tfont-weight: bold;");
		o(r, "\t\t\t\ttext-align: left;");
		o(r, "\t\t\t\tfloat: left;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t#date_enter {");
		o(r, "\t\t\t\tpadding-top: 4px;");
		o(r, "\t\t\t\tpadding-right: 4px;");
		o(r, "\t\t\t\ttext-align: right;");
		o(r, "\t\t\t\tfloat: right;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t#copyright {");
		o(r, "\t\t\t\tfont-size: 80%;");
		o(r, "\t\t\t\tpadding-bottom: 8px;");
		o(r, "\t\t\t\tclear: both;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t#layer_year, #layer_month, #layer_day {");
		o(r, "\t\t\t\tfloat: left;");
		o(r, "\t\t\t\tpadding-right: 5px;");
		o(r, "\t\t\t\tpadding-left: 3px;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t#layer_year table tr:hover, #layer_month div table tr:hover {");
		o(r, "\t\t\t\tcursor: pointer;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t#layer_year {");
		o(r, "\t\t\t\tbackground-color: #ffbbbb;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t#layer_year table tr:hover {");
		o(r, "\t\t\t\tbackground-color: #dd9999;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t#layer_month {");
		o(r, "\t\t\t\tbackground-color: #ffddbb;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t#layer_month div table tr:hover {");
		o(r, "\t\t\t\tbackground-color: #ddbb99;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t#layer_day {");
		o(r, "\t\t\t\tbackground-color: #ffffbb;");
		o(r, "\t\t\t\twidth: auto;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t.twoline_table {");
		o(r, "\t\t\t\tfloat: left;");
		o(r, "\t\t\t\tpadding-right: 10px;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t.breaking_heading {");
		o(r, "\t\t\t\tclear: both;");
		o(r, "\t\t\t}");
		o(r, "\t\t\t]]>");
		o(r, "\t\t</style>");
		o(r, "\t\t<script type=\"text/javascript\">");
		o(r, "\t\t\t// <![CDATA[");
		o(r, "\t\t\tvar currentMonth = 0;");
		o(r, "\t\t\tvar currentDay   = 0;");
		o(r, "\t\t\tfunction displayMonth(month) {");
		o(r, "\t\t\t\tif(currentMonth != 0) {");
		o(r, "\t\t\t\t\tdocument.getElementById(\"layer_month_\" + currentMonth).style.display = \"none\";");
		o(r, "\t\t\t\t\tif(currentDay != 0) {");
		o(r, "\t\t\t\t\t\tdocument.getElementById(\"layer_day_\" + currentDay + \".\" + currentMonth).style.display = \"none\";");
		o(r, "\t\t\t\t\t\tcurrentDay = 0;");
		o(r, "\t\t\t\t\t}");
		o(r, "\t\t\t\t}");
		o(r, "\t\t\t\tdocument.getElementById(\"layer_month_\" + month).style.display = \"block\";");
		o(r, "\t\t\t\tcurrentMonth = month;");
		o(r, "\t\t\t\tdocument.getElementById(\"enter_month\").value = month;");
		o(r, "\t\t\t\tdocument.getElementById(\"enter_day\").value = \"d\";");
		o(r, "\t\t\t}");
		o(r, "\t\t\tfunction displayDay(day) {");
		o(r, "\t\t\t\tif(currentDay != 0) {");
		o(r, "\t\t\t\t\tdocument.getElementById(\"layer_day_\" + currentDay + \".\" + currentMonth).style.display = \"none\";");
		o(r, "\t\t\t\t}");
		o(r, "\t\t\t\tdocument.getElementById(\"layer_day_\" + day + \".\" + currentMonth).style.display = \"block\";");
		o(r, "\t\t\t\tcurrentDay = day;");
		o(r, "\t\t\t\tdocument.getElementById(\"enter_day\").value = day;");
		o(r, "\t\t\t}");
		o(r, "\t\t\tfunction displayEntered() {");
		o(r, "\t\t\t\tvar month = document.getElementById(\"enter_month\").value;");
		o(r, "\t\t\t\tvar day   = document.getElementById(\"enter_day\").value;");
		o(r, "\t\t\t\tdisplayMonth(month);");
		o(r, "\t\t\t\tdisplayDay(day);");
		o(r, "\t\t\t}");
		o(r, "\t\t\t// ]]>");
		o(r, "\t\t</script>");
		o(r, "\t</head>");
		o(r, "\t<body>");
		o(r, "\t\t<div id=\"header\">");
		o(r, "\t\t\t<h1>Screenindex Statistics</h1>");
		o(r, "\t\t\t<div id=\"date_enter\">");
		o(r, "\t\t\t\t<form method=\"get\" action=\"javascript: void(0);\">");
		o(r, "\t\t\t\t\t<input type=\"text\" value=\"d\" size=\"2\" id=\"enter_day\" /> .");
		o(r, "\t\t\t\t\t<input type=\"text\" value=\"M\" size=\"2\" id=\"enter_month\" /> .");
		o(r, "\t\t\t\t\t<input type=\"text\" value=\"" + year + "\" size=\"4\" readonly=\"readonly\" />");
		o(r, "\t\t\t\t\t<button id=\"enter_button\" onclick=\"displayEntered();\">Display</button>");
		o(r, "\t\t\t\t</form>");
		o(r, "\t\t\t</div>");
		o(r, "\t\t\t<div id=\"copyright\">Copyright (c) 2009, 2010 Ma_Sys.ma. For further info send an e-mail to Ma_Sys.ma@web.de.</div>");
		o(r, "\t\t</div>");
		o(r, "\t\t<div id=\"layer_year\">");
		o(r, "\t\t\t<h2>Year " + year + "</h2>");
		o(r, "\t\t\t<h3>Month overview</h3>");
		o(r, "\t\t\t<table summary=\"Year data for " + year + "\">");
		o(r, "\t\t\t\t<thead>");
		o(r, "\t\t\t\t\t<tr><th>No.</th><th>Month</th><th>Time total</th></tr>");
		o(r, "\t\t\t\t</thead>");
		o(r, "\t\t\t\t<tbody>");
		LinkedHashMap<String, Integer> activityLengthAt = new LinkedHashMap<String, Integer>();
		ActivityList currentList;
		long total      = 0;
		int  monthCount = 0;
		int  dayCount   = 0;
		for(Iterator<ActivityList> i = activities.getInternalActivityList(); i.hasNext();) {
			currentList = i.next();
			String monthName = MONTH_FOR_NUMBER.get(Integer.parseInt(currentList.getSubNumber()));
			long length = currentList.getLength();
			o(r, "\t\t\t\t\t<tr onclick=\"javascript: displayMonth(" + currentList.getSubNumber() + ");\"><td>" + currentList.getSubNumber() + "</td><td>" + monthName + "</td><td>" + Activity.formatSeconds(length) + "</td></tr>");
			total += length;
			monthCount++;
			dayCount += currentList.getDayCount();
			activityLengthAt.put(monthName.substring(0, 3), (int)(length/60/60));
		}
		o(r, "\t\t\t\t</tbody>");
		o(r, "\t\t\t</table>");
		o(r, "\t\t\t<h3>Year statistics</h3>");
		o(r, "\t\t\t<ul>");
		o(r, "\t\t\t\t<li>Total: " + Activity.formatSeconds(total) + "</li>");
		o(r, "\t\t\t\t<li>Avg/month: " + Activity.formatSeconds(total/monthCount) + "</li>");
		o(r, "\t\t\t\t<li>Avg/day: " + Activity.formatSeconds(total/dayCount) + "</li>");
		o(r, "\t\t\t</ul>");
		o(r, "\t\t\t<h3>Activity Diagram</h3>");
		o(r, "\t\t\t<p>");
		o(r, createSVGDiagram("Month", "Activity", "h", 10, "\t\t\t\t", 1, 315, activityLengthAt));
		o(r, "\t\t\t</p>");
		o(r, "\t\t</div>");
		o(r, "\t\t<div id=\"layer_month\">");
		for(Iterator<ActivityList> i = activities.getInternalActivityList(); i.hasNext();) {
			currentList = i.next();
			o(r, "\t\t\t<div id=\"layer_month_" + currentList.getSubNumber() + "\" style=\"display: none;\">");
			o(r, "\t\t\t\t<h2>Month " + currentList.getSubNumber() + "</h2>");
			o(r, "\t\t\t\t<h3>Day overview</h3>");
			o(r, "\t\t\t\t<table summary=\"Month data for " + year + "\" class=\"twoline_table\">");
			o(r, "\t\t\t\t\t<thead>");
			o(r, "\t\t\t\t\t\t<tr><th>No.</th><th>Time total</th></tr>");
			o(r, "\t\t\t\t\t</thead>");
			o(r, "\t\t\t\t\t<tbody>");
			activityLengthAt = new LinkedHashMap<String, Integer>();
			total       = 0;
			dayCount    = 0;
			for(Iterator<ActivityList> j = currentList.getInternalActivityList(); j.hasNext();) {
				currentList = j.next();
				long length = currentList.getLength();
				o(r, "\t\t\t\t\t\t<tr onclick=\"javascript: displayDay(" + currentList.getSubNumber() + ");\"><td>" + currentList.getSubNumber() + "</td><td>" + Activity.formatSeconds(length) + "</td></tr>");
				total += length;
				dayCount++;
				if(dayCount == 16 && j.hasNext()) {
					o(r, "\t\t\t\t\t</tbody>");
					o(r, "\t\t\t\t</table>");
					o(r, "\t\t\t\t<table summary=\"Month data for " + year + "\" class=\"twoline_table\">");
					o(r, "\t\t\t\t\t<thead>");
					o(r, "\t\t\t\t\t\t<tr><th>No.</th><th>Time total</th></tr>");
					o(r, "\t\t\t\t\t</thead>");
					o(r, "\t\t\t\t\t<tbody>");
				}
				activityLengthAt.put(currentList.getSubNumber(), (int)(length/60));
			}
			o(r, "\t\t\t\t\t</tbody>");
			o(r, "\t\t\t\t</table>");
			o(r, "\t\t\t\t<h3 class=\"breaking_heading\">Month statistics</h3>");
			o(r, "\t\t\t\t<ul>");
			o(r, "\t\t\t\t\t<li>Total: " + Activity.formatSeconds(total) + "</li>");
			o(r, "\t\t\t\t\t<li>Avg/day: " + Activity.formatSeconds(total/dayCount) + "</li>");
			o(r, "\t\t\t\t</ul>");
			o(r, "\t\t\t\t<h3>Activity Diagram</h3>");
			o(r, "\t\t\t\t<p>");
			o(r, createSVGDiagram("Day", "Activity", "min", 60, "\t\t\t\t\t", 0.75f, 690, activityLengthAt));
			o(r, "\t\t\t\t</p>");
			o(r, "\t\t\t</div>");
		}
		o(r, "\t\t</div>");
		o(r, "\t\t<div id=\"layer_day\">");
		for(Iterator<ActivityList> i = activities.getInternalActivityList(); i.hasNext();) {
			currentList = i.next();
			for(Iterator<ActivityList> j = currentList.getInternalActivityList(); j.hasNext();) {
				ActivityList currentSubList = j.next();
				o(r, "\t\t\t<div id=\"layer_day_" + currentSubList.getSubNumber() + "." + currentList.getSubNumber() + "\" style=\"display: none;\">");
				o(r, "\t\t\t\t<h2>Day " + currentSubList.getSubNumber() + "</h2>");
				o(r, "\t\t\t\t<h3>Activity overview</h3>");
				o(r, "\t\t\t\t<ul>");
				total = 0;
				for(Iterator<ActivityList> k = currentSubList.getInternalActivityList(); k.hasNext();) {
					currentSubList = k.next();
					long length = currentSubList.getLength();
					total += length;
					o(r, "\t\t\t\t\t<li>From " + clockFormat.format(currentSubList.getStart()) + " to " + clockFormat.format(currentSubList.getEnd()) + ": " + Activity.formatSeconds(length) +"</li>");
					LinkedList<String> messages = currentSubList.getMessages();
					if(!messages.isEmpty()) {
						o(r, "\t\t\t\t\t<ul>");
						for(Iterator<String> l = messages.iterator(); l.hasNext();) {
							o(r, "\t\t\t\t\t\t<li class=\"message\">" + l.next() + "</li>");
						}
						o(r, "\t\t\t\t\t</ul>");
					}
				}
				o(r, "\t\t\t\t\t<li>Total: " + Activity.formatSeconds(total) +"</li>");
				o(r, "\t\t\t\t</ul>");
				o(r, "\t\t\t</div>");
			}
		}
		o(r, "\t\t</div>");
		o(r, "\t</body>");
		o(r, "</html>");
		return r.toString();
	}
	
	/**
	 * Creates a SVG diagram for an XHTML Document by using the given <code>data</code>.
	 * There will be no tailing '\n'.
	 * 
	 * @param xAxis The text to be displayed at the X-Axis.
	 * @param yAxis The text to be displayed at the Y-Axis.
	 * @param unit  The symbol of the unit to display, e.g.: "h" for hours
	 * @param divideValuesBy A number to divide all values of <code>data</code> trough.
	 * @param indent The indentation string, e.g.: "\t\t\t"
	 * @param scale A scale factor for the diagram.
	 * @param width Pixel width of the diagram.
	 * @param data The data to be displayed in the diagram.
	 * @return The XHTML Code containing the <code>&lt;svg&gt;</code> tag.
	 */
	private static String createSVGDiagram(String xAxis, String yAxis, String unit, float divideValuesBy, String indent, float scale, int width, LinkedHashMap<String, Integer> data) {
		// 1. Determine units and maximum value
		String fullUnitString = unit;
		if(divideValuesBy > 1) { fullUnitString += "*" + (int)divideValuesBy; }
		fullUnitString = "[" + fullUnitString + "]";
		float maxUnit = 0;
		for(Iterator<Integer> values = data.values().iterator(); values.hasNext();) {
			int value = values.next();
			if(value > maxUnit) { maxUnit = value; }
		}
		maxUnit     = maxUnit / divideValuesBy;
		float units = maxUnit / 5.0f;
		if((int)units <= 0) { units = 1; }
		// 2. Initialize output and draw background
		StringBuffer ret = new StringBuffer();
		o(ret, indent + "<svg height=\"" + (int)(scale * 120) + "px\" width=\"" + (int)(scale * width) + 
				"px\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">");
		o(ret, indent + "\t<g transform=\"scale(" + scale + ")\">");
		o(ret, indent + "\t\t<rect x=\"0px\" y=\"0px\" height=\"120px\" width=\"" + width + "px\" fill=\"#dddddd\" />");
		for(int i = 85, j = 1; i >= 25; i = i - 20, j++) {
			o(ret, indent + "\t\t<text x=\"20px\" y=\"" + i + "px\" text-anchor=\"end\" dominant-baseline=\"middle\">" + 
					(int)(units * j) + "</text>");
		}
		// 3. Display data bars
		int index = 0;
		for(Iterator<String> names = data.keySet().iterator(); names.hasNext();) {
			String cName  = names.next();
			float  cValue = (float)data.get(cName) / divideValuesBy;
			int    height = (int)((cValue / units) * 20.0f);
			int    rectX  = index * 20 + 35;
			int    rectY  = 105 - height;
			o(ret, indent + "\t\t<text x=\"" + (rectX + 5) + "px\" y=\"105px\" text-anchor=\"middle\" " + 
					"dominant-baseline=\"hanging\">" + cName + "</text>\t");
			o(ret, indent + "\t\t<rect x=\"" + rectX +"px\" y=\"" + rectY + "px\" height=\"" + height + 
					"px\" width=\"10px\" fill=\"#ff8800\" />");
			index++;
		}
		// Display coordinate system
		int bw = width - 5;
		o(ret, indent + "\t\t<text x=\"" + (bw - 10) + "px\" y=\"103px\" text-anchor=\"end\">" + xAxis + "</text>");
		o(ret, indent + "\t\t<path d=\"M 20 105 L " + bw + " 105 M " + bw + " 105 L " + (bw - 3) + " 102 M " + 
				bw + " 105 L " + (bw - 3) + " 108 z\" stroke=\"#000000\" stroke-width=\"1\" />");
		o(ret, indent + "\t\t<text x=\"27px\" y=\"7px\" dominant-baseline=\"hanging\">" + yAxis + " " + 
				fullUnitString + "</text>");
		o(ret, indent + "\t\t<path d=\"M 25 5 L 25 110 M 25 5 L 22 8 M 25 5 L 28 8 z\" stroke=\"#000000\" " + 
				"stroke-width=\"1\" />");
		o(ret, indent + "\t\t<path d=\"M 25 105 m 0 -20 l -3 0 m 3 -20 l -3 0 m 3 -20 l -3 0 m 3 -20 l -3 0 z\"" + 
				" stroke=\"#444444\" stroke-width=\"1\" />");
		o(ret, indent + "\t</g>"); 
		// Do not add a tailing '\n'
		ret.append(indent + "</svg>"); 
		return ret.toString();
	}
	
	/**
	 * Appends a line to <code>stringBuffer</code>
	 * @param stringBuffer This <code>StringBuffer</code> to use
	 * @param line The line to add
	 */
	private static void o(StringBuffer stringBuffer, String line) {
		stringBuffer.append(line);
		stringBuffer.append('\n');
	}
	
}
