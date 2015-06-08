package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmi.MovieDatabase;
import test.TestTool;

public class Main {
	
	private static final String SERVICE_NAME = "MovieDatabase";

	public static void main(String[] args) {
		if(args.length != 3) {
			System.out.println("usage java -jar " +  '"' + " Client.jar" + '"' + "serverIP serverPort testsPerType");	
			System.exit(1);
		}
		String serverIP = args[0];
		int serverPort = Integer.parseInt(args[1]);
		int testsPerType = Integer.parseInt(args[2]);
		try {
			//captura referencia para o registro utilizado pelo servidor
			Registry myRegistry = LocateRegistry.getRegistry(serverIP, serverPort);
			//captura referencia para objeto do tipo MovieDatabase
			//(as chamadas de metodos efetuadas nele sao executadas pelo servidor)
			MovieDatabase movieDatabase = (MovieDatabase) myRegistry.lookup(SERVICE_NAME);
			//iniciar bateria de testes
			TestTool.start(movieDatabase, testsPerType);
		} catch (Exception e) {
			//imprime erro e encerra execucao
			System.err.println(e);
			System.exit(1);
		}
	}
}