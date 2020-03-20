package ma.screenindex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Allows you to read and write the Screenindex configuration file.
 * The configuration object is a <code>Hashtable&lt;String, String&gt;</code>
 * @author Linux-Fan, Ma_Sys.ma
 * @since Screenindex 1.0.3.0
 * @see Main#CONFIGURATION_FILE
 */
public class Configuration {

	/**
	 * Writes the given configuration to the given file.
	 * @param configurationFile The file to write to
	 * @param cfg The configuration which should be written
	 * @throws IOException Allows you to react on an <code>IOException</code>
	 */
	public static void write(String configurationFile, Hashtable<String, String> cfg) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(configurationFile));
		out.write("[configuration]");
		out.newLine();
		out.write("; Screen Index configuration, created by version " + Main.VERSION);
		out.newLine();
		Enumeration<String> keys = cfg.keys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			out.write(key + "=" + cfg.get(key));
			out.newLine();
		}
		out.close();
	}

	/**
	 * Reads a configuration from the given <code>File</code>.
	 * @param configurationFile The file to read from
	 * @return a <code>Hashtable</code> containing the configuration data.
	 * @throws IOException Allows you to reacto on an <code>IOException</code>.
	 */
	public static Hashtable<String, String> readConfiguration(File configurationFile) throws IOException {
		Hashtable<String, String> ret = new Hashtable<String, String>();
		BufferedReader in = new BufferedReader(new FileReader(configurationFile));
		String line;
		while((line = in.readLine()) != null) {
			if(line.charAt(0) != '[' && line.charAt(0) != ';') {
				String[] keyVal = line.split("=");
				ret.put(keyVal[0], keyVal[1]);
			}
		}
		in.close();
		return ret;
	}
	
}
