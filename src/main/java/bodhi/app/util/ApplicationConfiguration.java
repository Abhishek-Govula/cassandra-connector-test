package bodhi.app.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfiguration {
	public static Properties props = null;
	private static Object lock = new Object();

	private ApplicationConfiguration() {
		// Private constructor
	}

	public static boolean loadProperties() {
		System.out.println("Loading the properties...");
		if (props == null) {
			synchronized (lock) {
				if (props == null) {
					InputStream fis = null;
					try {
//						fis = ApplicationConfiguration.class.getClassLoader().getResourceAsStream("application.properties");
						
						// Local build
						fis = ApplicationConfiguration.class.getClassLoader().getResourceAsStream("application.properties");
						
						if (fis!=null) {
							props = new Properties();
							props.load(fis);
							System.out.println("Local Properties loaded successfully");
						} else {
							System.out.println("Could not read the local properties file.");
						}
						
						try {
							props.load(new FileInputStream("./application.properties"));
							System.out.println("Loaded the jar file props");
						} catch(Exception e) {
							System.out.println("Could not load the jar file props | Ignore if running locally");
						}
						
					} catch (Exception e) {
						System.out.println("Exception while reading the properties file.");
						e.printStackTrace();
						
						return false;
					} finally {
						try {
							if (fis!=null) {
								fis.close();
							}
						} catch (Exception e) {
							System.out.println("Exception while closing the application properties file.");
							e.printStackTrace();
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public static Properties getProperties() {
		if (props == null) {
			loadProperties();
		}
		return props;
	}
	
	public static String getProperty(String name) {
		if (props==null) {
			loadProperties();
		}
		if (props==null) {
			return "";
		}
		return props.getProperty(name);
	}
}
