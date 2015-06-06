package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import rmi.MovieDatabase;
import rmi.QueryResult;

public class Main {
	
	private static final String SERVICE_NAME = "MovieDatabase";
	private static final String SERVER_IP = "192.168.0.14";

	public static void main(String[] args) {
		try {
			Registry myRegistry = LocateRegistry.getRegistry(SERVER_IP);             
			MovieDatabase impl = (MovieDatabase) myRegistry.lookup(SERVICE_NAME);						
			 QueryResult result = impl.executeUpdateQuery("UPDATE Movies SET Genero = 'opa2' where Genero = 'opaa'");
			 System.out.println(result.serverProcessingTime);
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}
}