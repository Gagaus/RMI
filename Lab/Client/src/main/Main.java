package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmi.MovieDatabase;
import test.TestTool;

public class Main {
	
	private static final String SERVICE_NAME = "MovieDatabase";
	private static final String SERVER_IP = "192.168.0.14";
	//prefere-se usar a porta padrao(1099), porem, caso esta ja esteja em uso, seta-se outra aqui
	private static final int SERVER_PORT = Registry.REGISTRY_PORT;

	public static void main(String[] args) {
		try {
			//captura referencia para o registro utilizado pelo servidor
			Registry myRegistry = LocateRegistry.getRegistry(SERVER_IP, SERVER_PORT);
			//captura referencia para objeto do tipo MovieDatabase
			//(as chamadas de metodos efetuadas nele sao executadas pelo servidor)
			MovieDatabase movieDatabase = (MovieDatabase) myRegistry.lookup(SERVICE_NAME);
			//iniciar bateria de testes
			TestTool.start(movieDatabase, 50);
		} catch (Exception e) {
			//imprime erro e encerra execucao
			System.err.println(e);
			System.exit(1);
		}
	}
}