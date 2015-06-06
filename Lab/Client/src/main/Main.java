package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmi.MovieDatabase;
import test.TestTool;

public class Main {
	
	private static final String SERVICE_NAME = "MovieDatabase";
	private static final String SERVER_IP = "192.168.0.14";

	public static void main(String[] args) {
		try {
			Registry myRegistry = LocateRegistry.getRegistry(SERVER_IP);             
			MovieDatabase movieDatabase = (MovieDatabase) myRegistry.lookup(SERVICE_NAME);						
			TestTool.start(movieDatabase, 50);
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}
}