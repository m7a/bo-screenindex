package ma.screenindex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

/**
 * This <code>Thread</code> is started when Screenindex is shutted down.
 * @author Linux-Fan, Ma_Sys.ma
 * @since Screenindex 1.0.0.0
 * @see java.lang.Thread
 * @see java.lang.Runtime#addShutdownHook(Thread)
 */
public class LoggingHook extends Thread {

	/**
	 * Saves the start date
	 */
	private Date startDate;
	
	/**
	 * Saves the location of the lock File, which is used to 
	 * make sure that there are not two Screenindex instances
	 * at the same time for the same database or user.
	 */
	private File lockFile;
	
	/**
	 * Saves the configuration
	 */
	private Hashtable<String, String> cfg;
	
	/**
	 * Saves the <code>SimpleDateFormat</code> to read and write
	 * dates.
	 */
	private SimpleDateFormat sdf;
	
	/**
	 * Internally allows the application to verfy weahter the
	 * application is still running.
	 */
	private static boolean applicationIsRunning;
	
	/**
	 * Creates a new <code>LoggingHook</code>.
	 * @param startDate The start date of the <code>Activity</code> which will be recorded
	 * @param lockFile 
	 * 		The file which indicates that Screenindex is still running.
	 * 		It won't be checked, this is done in {@link Main#main(String[])}
	 * @param cfg Configuration <code>Hashtable</code>
	 * @param sdf A <code>SimpleDateFormat</code> for reading or writing dates
	 */
	public LoggingHook(Date startDate, File lockFile, Hashtable<String, String> cfg, SimpleDateFormat sdf) {
		this.startDate = startDate;
		this.lockFile  = lockFile;
		this.cfg       = cfg;
		this.sdf       = sdf;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(lockFile));
			writer.write(sdf.format(startDate));
			writer.write(" to ");
			writer.write("unknown");
			writer.close();
		} catch (IOException ex) {
			System.err.println("Unable to create lock file \"" + lockFile.getAbsolutePath() + "\":"); 
			ex.printStackTrace();
			System.err.println("This is not a real problem if you do not start an other instance.");
			System.err.println("But it might be a problem when Screenindex wants to write the database and it can not.");
			System.err.println("It is recommended to check if you have writing permissions to the following folder:");
			System.err.println(cfg.get("database-dir"));
		}
	}
	
	/**
	 * Executed when screenindex is shutting down.
	 * This saves the activity
	 */
	public void run() {
		Date endDate = TimeActions.date();
		YearAccess.saveActivity(startDate, endDate, sdf, cfg, lockFile);
		System.out.println("done.");
		applicationIsRunning = false;
	}

	/**
	 * Starts logging and creates backups of the current activity length.
	 * (The time when a backup is created is given in the <code>cfg</code> parameter.)
	 * @param lockFile Already-running-indicator-file
	 * @param cfg Configuration
	 * @param sdf Date Format
	 */
	protected static void startLogging(File lockFile, Hashtable<String, String> cfg, SimpleDateFormat sdf) {
		applicationIsRunning = true;
		LoggingHook h = new LoggingHook(TimeActions.date(), lockFile, cfg, sdf);
		Runtime.getRuntime().addShutdownHook(h);
		final long sleepDuration = Long.parseLong(cfg.get("backup-interval-secs")) * 1000;
		while(applicationIsRunning) {
			try {
				Thread.sleep(sleepDuration);
				// Create backup of activity
				try {
					BufferedReader reader = new BufferedReader(new FileReader(lockFile));
					String line;
					StringBuffer messages = new StringBuffer();
					// Skip first line, because it contains data which needs to be updated
					reader.readLine(); 
					while((line = reader.readLine()) != null) {
						messages.append(line);
						messages.append('\n');
					}
					reader.close();
					BufferedWriter writer = new BufferedWriter(new FileWriter(lockFile));
					writer.write(sdf.format(h.startDate));
					writer.write(" to ");
					writer.write(sdf.format(TimeActions.date()));
					writer.newLine();
					writer.write(messages.toString());
					writer.close();
				} catch(Exception ex) {
					System.err.println("Unable to save backup data.");
					ex.printStackTrace();
				}
			} catch(InterruptedException ex) {
				System.err.println("Thread was interrupted. Logging cancelled.");
				ex.printStackTrace();
				lockFile.delete();
				Runtime.getRuntime().halt(2);
			}
		}
	}
	
}
