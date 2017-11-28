package bodhi.cassandra_test;

import java.io.IOException;

import bodhi.app.util.ApplicationConfiguration;

public class App {
	private static final String _cassandraHostIP = ApplicationConfiguration.getProperty("hostname");
	private static final String _cassandraPort = ApplicationConfiguration.getProperty("port");

	public static void main(String[] args) throws IOException {
		System.out.println("Cassandra host: " + _cassandraHostIP);
		System.out.println("Cassandra port: " + _cassandraPort);
		if (null != _cassandraHostIP && !"".equalsIgnoreCase(_cassandraHostIP) && null != _cassandraPort
				&& !"".equalsIgnoreCase(_cassandraPort)) {
			final String cassandraHostIP = _cassandraHostIP;
			final int port = Integer.parseInt(_cassandraPort);

			final CassandraConnector client = new CassandraConnector();
			System.out.println("Connecting to IP Address " + cassandraHostIP + ":" + port + "...");

			try {
				long startTime = System.currentTimeMillis();
				client.connect(cassandraHostIP, port);
				long endTime = System.currentTimeMillis();
				System.out.println("Connection estd, Time taken: " + (endTime - startTime) + "ms");
			} catch (Exception e) {
				System.out.println("Exception while trying to connect to the client");
				e.printStackTrace();
			} finally {
				client.close();
			}
		} else {
			System.out.println(
					"App could not connect to cassandra, configuration problem! Please verify the config files.");
		}

	}
}
