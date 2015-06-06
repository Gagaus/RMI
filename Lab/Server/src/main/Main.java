package main;

import database.MovieDatabaseImpl;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
	
	private static final String SERVICE_NAME = "MovieDatabase";
	
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind(SERVICE_NAME, new MovieDatabaseImpl());
			System.out.println("Server is running...");
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}
}
