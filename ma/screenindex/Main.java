package ma.screenindex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.TreeSet;

import javax.swing.JOptionPane;

/**
 * The <code>Main</code>-Class is where the <code>{@link #main(String[])}</code> method
 * is placed.
 * 
 * @author Linux-Fan, Ma_Sys.ma
 * @since Screenindex 1.0.0.0
 * @version 2.0.1.0
 */
public class Main {
	
	/**
	 * Stores the current Screenindex version <code>String</code>.
	 */
	protected static final String VERSION = "1.0.4.5";
	
	/**
	 * Contains the path of the configuration file.
	 */
	protected static final String CONFIGURATION_FILE = "configuration.ini";
	
	/**
	 * Stores the default configuration.
	 */
	private static final Hashtable<String, String> DEFAULT_CONFIGURATION;
	
	/**
	 * Stores a message which is displayed if the graphical message
	 * dialog is displayed.
	 * 
	 * @since Screenindex 1.0.4.0
	 */
	private static final String guiDialogMsg = 
		"<html><body style=\"font-family:serif;font-weight:normal;font-size:11px;\">" + 
		"<p style=\"font-weight:bold;\">Screenindex seems to be already running.</p>" + 
		"<p>This is possible due to several reasons:</p><ol>" + 
		"<li>Your Computer has crashed. If this was the case, just press <tt>[ENTER]</tt></li>" + 
		"<li>You logged out yourself and then logged in again. If that is the case, just press <tt>[ESC]</tt></li>" + 
		"<li>An other reason. If that is the case, you may check a Task-Manager, whether Screenindex is still running or not.</li></ol>" + 
		"<p>Screenindex is going to delete the file which indicates that Screenindex is still running.</p>" +
		"<p>As much as possible, will be restored then.</p>" +
		"<p>This is the right option, if Screenindex is not running anymore.</p>" + 
		"<p>If Screenindex is still running, just press <tt>[ESC]</tt>.</p></body></html>";
	
	/**
	 * Initializes the default configuartion.
	 */
	static {
		DEFAULT_CONFIGURATION = new Hashtable<String, String>();
		DEFAULT_CONFIGURATION.put("backup-interval-secs", String.valueOf(60 * 10)); // Each 10 min.
		DEFAULT_CONFIGURATION.put("locked-file",          "screenindex-locked.txt");
		DEFAULT_CONFIGURATION.put("database-dir",         "db");
		DEFAULT_CONFIGURATION.put("io-date-format",       "dd.MM.yyyy HH:mm:ss");
		DEFAULT_CONFIGURATION.put("delta-t-max-per-day",  String.valueOf(60 * 60 * 6)); // 6 hours ... 100 %
	}
	
	/**
	 * Displays application help.
	 */
	protected static void usage() {
		// -l for log
		// -g for graphical
		// -y for yaos|yr|yes
		// -d for delete
		System.out.println("USAGE: screenindex -l [-g|-y|-d]");
		System.out.println("       Starts logging.");
		System.out.println("       -g   shows a graphical dialog if Screenindex seems to be already running.");
		System.out.println("       -y   does not show that dialog but simply asumes OK, if it would have");
		System.out.println("            been shown. This is helpful, if you use a window manager, which");
		System.out.println("            kills all processes on exit and so does not allow Screenindex to");
		System.out.println("            save any activity information.");
		System.out.println("       -d   deletes a lock file, if there is any without asking.");
		// TODO planned: -a for notification on reaching maximum time...
		// -v for view
		// -i for info
		// -m for messages only
		// -r for raw
		System.out.println("USAGE: screenindex -v [d|m|y] <date|current> [-i|-m|-r|-t]");
		System.out.println("       Shows statistics of the given day/month/year.");
		System.out.println("       current represents the current day/month/year.");
		System.out.println("       -i   also displays all linked messages.");
		System.out.println("       -m   displays messages only.");
		System.out.println("       -r   displays percentage of the maximum time defined as");
		System.out.println("            delta-t-max-per-day. Can only be used with day.");
		System.out.println("       -t   displays time only. Can only be used with day.");
		// -p for pass
		System.out.println("USAGE: screenindex -p [message]");
		System.out.println("       Links the given message to the current time");
		System.out.println("       and stores it within the activites.");
		System.out.println("       If there is no message given, it just updates the backup data.");
		// -c for convert
		System.out.println("USAGE: screenindex -c");
		System.out.println("       Converts older activities into the new XML format.");
		// -s for save
		System.out.println("USAGE: screenindex -s <year> <file>");
		System.out.println("       Saves the given year statistics into the given XHTML File.");
		System.out.println("       This feature is deprecated.");
		System.out.println();
		System.out.println("Replace \"screenindex\" with the command you needed to run Screenindex.");
	}
	
	/**
	 * Called by the JVM. It decides which action to do and then executes it.
	 * @param args All parameters
	 */
	public static void main(String[] args) {
		System.out.println("Screen Index " + VERSION + ", Copyright (c) 2009, 2010, 2011, 2012, 2015 Ma_Sys.ma.");
		System.out.println("For further info send an e-mail to Ma_Sys.ma@web.de.");
		System.out.println();
		
		if(args.length == 0) {
			usage();
		} else {
			// Read configuration
			Hashtable<String, String> cfg = new Hashtable<String, String>();
			File configurationFile = new File(CONFIGURATION_FILE);
			try {
				cfg = Configuration.readConfiguration(configurationFile);
			} catch (IOException ex) {
				System.err.println("Unable to read configuration data: " + ex.getMessage());
				ex.printStackTrace();
				System.err.println("This can happen at the first startup.");
				cfg.putAll(DEFAULT_CONFIGURATION);
				try {
					Configuration.write(CONFIGURATION_FILE, cfg);
				} catch (IOException ex2) {
					System.err.println("Unable to write configuration data:");
					ex2.printStackTrace();
				}
			}
			// Check database dir and create it if it does not exist yet
			File databaseDir = new File(cfg.get("database-dir"));
			if(!databaseDir.exists()) {
				if(!databaseDir.mkdirs()) {
					System.err.println("Unable to create database directory \"" + databaseDir.getAbsolutePath() + "\"");
				}
			}
			// Frequently used variables
			final File lockFile        = new File(cfg.get("locked-file"));
			final SimpleDateFormat sdf = new SimpleDateFormat(cfg.get("io-date-format"));
			// Check parameters
			switch(args[0].charAt(1)) {
				case 'l': {
					// Logging
					boolean startLogging = true;
					if(args.length > 1 && (args[1].equals("-g") || args[1].equals("-y")) && lockFile.exists()) {
						// Very informative situation display dialog
						if(args[1].equals("-y") || JOptionPane.showConfirmDialog(null, guiDialogMsg, "Screenindex startup", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {	
							if(args[1].equals("-y")) {
								System.out.println("Using -y Option...");
							}
							// Restore old activity and delete lock file
							try {
								BufferedReader reader = new BufferedReader(new FileReader(lockFile));
								String[] dates = reader.readLine().split(" to "); // Separate both dates
								if(dates[1].equals("unknown")) {
									System.err.println("There is no backup which could be restored.");
								} else {
									YearAccess.saveActivity(sdf.parse(dates[0]), sdf.parse(dates[1]), sdf, cfg, lockFile);
								}
								reader.close();
							} catch(Exception ex) {
								System.err.println("Unable to restore old activity.");
								ex.printStackTrace();
								System.out.println("Messages from the old activity will also be not imported.");
							}
						} else {
							startLogging = false;
						}
					} else if(lockFile.exists() && args.length > 1 && args[1].equals("-d")) {
						startLogging = lockFile.delete();
						if(startLogging) {
							System.out.println("Successfully deleted lock file.");
						} else {
							System.err.println("Unable to delete lock file.");
							System.err.println("Possible reasons might be:");
							System.err.println("   1. Access rules");
							System.err.println("   2. An other application is accessing the file.");
							System.err.println("Screenindex was unable to delete the following file:");
							System.err.println(lockFile.getAbsolutePath());
						}
					} else if(lockFile.exists()) {
						System.out.println("There is a file called \"" + lockFile.getAbsolutePath() +"\".");
						System.out.println("That file indicates that Screenindex is already running.");
						System.out.println("If this is not true, you can just call screenindex with");
						System.out.println("the \"-l -d\" option to delete the file. Then just try to");
						System.out.println("start logging again.");
						startLogging = false;
					}
					if(startLogging) {
						System.out.println("Screenindex logging in progress -- waiting for shutdown...");
						LoggingHook.startLogging(lockFile, cfg, sdf);
					}
					break;
				}
				case 'd': {
					System.out.println("You used the obsolete parameter \"-d\".");
					System.out.println("We now recommend you to use \"-l -d\" for the same functionality.");
					System.out.println();
					usage();
					break;
				}
				case 'v': {
					try {
						char dmy = args[1].charAt(0);
						String date;
						if(args.length == 2) {
							date = args[1];
						} else {
							date = args[2];
						}
						if(date.equals("current")) {
							String format;
							switch(dmy) {
								case 'y': format =       "yyyy"; break;
								case 'm': format =    "MM.yyyy"; break;
								default:  format = "dd.MM.yyyy"; break;
							}
							date = new SimpleDateFormat(format).format(TimeActions.date());
						}
						if(!(dmy == 'd' || dmy == 'm' || dmy == 'y')) {
							int count = 0;
							char[] chars = date.toCharArray();
							for(int i = 0; i < chars.length; i++) {
								if(chars[i] == '.') {
									count++;
								}
							}
							switch(count) {
								case 0: { dmy = 'y'; break; }
								case 1: { dmy = 'm'; break; }
								case 2: { dmy = 'd'; break; }
								default: {
									System.err.println("Could not determine 'd', 'm' or 'y' for " + date);
									usage();
									return;
								}
							}
						}
						char pi = args[args.length - 1].charAt(1); // m, i
						int messageCount = 0;
						ActivityList activities = YearAccess.getActivityListFor(date, dmy, sdf, cfg);
						// i.a.l.: internal activity list
						Iterator<ActivityList> ial = activities.getInternalActivityList();
						long total = 0;
						int count  = 0;
						while(ial.hasNext()) {
							ActivityList current = ial.next();
							switch(dmy) {
								case 'd': {
									if(pi != 'r') {
										System.out.println(current.toString());
									}
									break;
								}
								case 'm': {
									if(pi != 'm') {
										System.out.println(current.getSubNumber() + "." + date + ": " + current.toString());
									}
									if(pi == 'm' || pi == 'i') {
										Iterator<ActivityList> inner1 = current.getInternalActivityList();
										while(inner1.hasNext()) {
											Iterator<String> messages = inner1.next().getMessages().iterator();
											while(messages.hasNext()) {
												System.out.println("  " + messages.next());
												messageCount++;
											}
										}
									}
									break;
								}
								case 'y': {
									System.out.println(current.getSubNumber() + "." + date +  ": " + current.toString());
									break;
								}
								default: {
									System.err.println("Cannot display activites for '" + dmy + "'.");
									usage();
									return;
								}
							}
							count++;
							total += current.getLength();
						}
						if(pi == 'r' || pi == 't') {
							// Get start of currently running screenindex from backup file
							// if exists (makes sense for an automatically updating display)
							if(lockFile.exists()) {
								try {
									BufferedReader in = new BufferedReader(new FileReader(lockFile));
									String firstLine = in.readLine();
									int divSpcPos = firstLine.indexOf(" to ");
									if(divSpcPos == -1) {
										System.out.println("Invalid lock file format.");
									} else {
										// Temporary acitvity
										total += new Activity(
											sdf.parse(firstLine.substring(0, divSpcPos)), // start time
											TimeActions.date() // current time
										).getLength();
									}
									in.close();
								} catch(IOException ex) {
									System.out.println("Could not read lock file.");
									ex.printStackTrace();
								}
							}
							if(pi == 'r') {
								int percentage = (int)(100 * ((double)total / (double)Long.parseLong(cfg.get("delta-t-max-per-day"))));
								if(percentage > 100) {
									System.out.println(100);
								} else {
									System.out.println(percentage);
								}
							} else {
								System.out.println(Activity.formatSeconds(total));
							}
						} else {
							System.out.println();
							System.out.println("Total:    " + Activity.formatSeconds(total));
							if(count > 0) {
								System.out.println("Averange: " + Activity.formatSeconds(total/count));
							}
							if(messageCount > 0) {
								System.out.println("Messages: " + messageCount);
							}
						}
					} catch(Exception ex) {
						System.err.println("Unable to read activities:");
						ex.printStackTrace();
					}
					break;
				}
				case 'p': {
					// Add a message
					if(!lockFile.exists()) {
						System.err.println("Screenindex seems to be not running.");
					} else {
						try {
							BufferedReader reader = new BufferedReader(new FileReader(lockFile));
							String line;
							StringBuffer messages = new StringBuffer();
							// Read the start date from the first line
							Date startDate = sdf.parse(reader.readLine().split(" to ")[0]); 
							while((line = reader.readLine()) != null) {
								messages.append(line);
								messages.append('\n');
							}
							if(args.length == 2) {
								messages.append(args[1]);
								messages.append('\n');
							}
							reader.close();
							BufferedWriter writer = new BufferedWriter(new FileWriter(lockFile));
							writer.write(sdf.format(startDate));
							writer.write(" to ");
							writer.write(sdf.format(TimeActions.date()));
							writer.newLine();
							writer.write(messages.toString());
							writer.close();
						} catch(Exception ex) {
							System.err.println("Unable to save lock file.");
							ex.printStackTrace();
						}
					}
					break;
				}
				case 'c': {
					// Convert older data into new XML format
					
					// Create action list sorted after year.
					// This is done by creating a Hashtable with the year as key.
					Hashtable<String, LinkedList<Activity>> actions = new Hashtable<String, LinkedList<Activity>>();
					File[] filesPlain = databaseDir.listFiles();
					TreeSet<File> filesSorted = new TreeSet<File>();
					for(int i = 0; i < filesPlain.length; i++) {
						if(filesPlain[i].getName().endsWith(".dat")) {
							filesSorted.add(filesPlain[i]);
						}
					}
					for(Iterator<File> i = filesSorted.iterator(); i.hasNext();) {
						File cFile = i.next();
						System.out.println(cFile.getName());
						LinkedHashMap<String, LinkedList<Activity>> month = null;
						try {
							ObjectInputStream is = new ObjectInputStream(new FileInputStream(cFile));
							month = read(is);
							is.close();
						} catch(Exception ex) {
							System.err.println("Unable to read old statistics file " + cFile.getName());
							ex.printStackTrace();
						}
						if(month != null) {
							String year = cFile.getName().substring(0, 4);
							Iterator<String> iterator = month.keySet().iterator();
							while(iterator.hasNext()) {
								if(!actions.containsKey(year)) {
									actions.put(year, new LinkedList<Activity>());
								}
								actions.get(year).addAll(month.get(iterator.next()));
							}
						}
					}
					// Import activities
					Enumeration<String> years = actions.keys();
					final LinkedList<String> defaultMessages = new LinkedList<String>();
					defaultMessages.add("Imported activity.");
					while(years.hasMoreElements()) {
						String year = years.nextElement();
						LinkedList<Activity> cActionList = actions.get(year);
						YearAccess access = new YearAccess(year, cfg, sdf);
						Iterator<Activity> yearActions = cActionList.iterator();
						while(yearActions.hasNext()) {
							Activity current = yearActions.next();
							access.addActivity(current.getStart(), current.getEnd(), defaultMessages);
						}
						try {
							access.writeData();
						} catch (Exception ex) {
							System.err.println("Unable to write year file " + year + ".xml");
							ex.printStackTrace();
						}
					}
					// Display warning
					System.out.println("We do not recommend to re-run this action.");
					
					break;
				}
				case 's': {
					// Save year data as XHTML file
					try {
						BufferedWriter out = new BufferedWriter(new FileWriter(args[2]));
						out.write(HTMLExport.getHTMLRepresentationFor(YearAccess.getActivityListFor(args[1], 'y', sdf, cfg), args[1]));
						out.close();
					} catch (Exception ex) {
						System.err.println("Unable to export year " + args[1]);
						ex.printStackTrace();
					}
					break;
				}
				case 'h': {
					// Many users think of this as a "help" command
					// It is when they enter -h, -help, /h, or sth. else which has 'h' at pos 2
					usage();
					break;
				}
				default: {
					System.err.println("Unknown action: \"" + args[0] + "\".");
					System.out.println();
					usage();
				}
			}
		}
		System.exit(0);
	}
	
	/**
	 * Compatibility method.
	 * 
	 * @param is A stream to read from
	 * @return 
	 * 		The object read converted to a <code>LinkedHashMap&lt;String, 
	 * 		LinkedList&lt;Activity&gt;&gt;</code>
	 * @throws IOException When there is an IO error
	 * @throws ClassNotFoundException 
	 * 		When the class does not exist (e.g. whent there is a <code>.dat</code>-file, 
	 * 		but it does not contain a serialized <code>LinkedHashMap</code>)
	 */
	@SuppressWarnings("unchecked")
	private static LinkedHashMap<String, LinkedList<Activity>> read(ObjectInputStream is) throws IOException, ClassNotFoundException {
		return (LinkedHashMap<String, LinkedList<Activity>>)is.readObject();
	}
	
}
