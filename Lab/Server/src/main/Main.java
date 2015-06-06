package main;

import database.MovieDatabaseImpl;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
	
	private static final String SERVICE_NAME = "MovieDatabase";
	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("usage main.Main serverPort");
			System.exit(1);
		}
		int serverPort = Integer.parseInt(args[0]);
		try {
			//cria registro na porta SERVER_PORT
			Registry registry = LocateRegistry.createRegistry(serverPort);
			//disponibiliza referencia para uma instancia de MovieDatabase
			registry.rebind(SERVICE_NAME, new MovieDatabaseImpl());
			//instrucoes auxiliares para exibicao das informacoes do servidor
			InetAddress serverAddr = InetAddress.getLocalHost();
			System.out.println("Server name   : " + serverAddr.getHostName());
			System.out.println("Running on    : " + serverAddr.getHostAddress());
		} catch (Exception e) {
			//em caso de erro, imprime o mesmo e encerra execucao
			System.err.println(e);
			System.exit(1);
		}
	}
}
