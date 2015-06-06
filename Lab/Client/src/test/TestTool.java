package test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import rmi.MovieDatabase;
import rmi.QueryResult;

public final class TestTool {
	
	private static final int numTests = 7;
	//definicao das queries que serao utilizadas como testes
	private static final List<String> queries = Arrays.asList(
											"SELECT Titulo, Ano from MOVIES",
											"SELECT Titulo, Ano FROM movies WHERE Genero = 'Action' ORDER BY ID",
											"SELECT Sinopse FROM movies WHERE ID = 1",
											"SELECT * FROM movies WHERE ID = 1",
											"SELECT * FROM movies",
											"SELECT Qtd from MOVIES WHERE ID = 1",
											"UPDATE movies SET Qtd = 1 WHERE ID = 1"
										);
	//S = search query, U = update query
	private static final String queriesType = "SSSSSSU";
	
	private TestTool() {}
	
	public static final void start(MovieDatabase movieDatabase, int testsPerType) throws IOException, InterruptedException, SQLException {
		//cria pasta raiz na qual serao armazenados os tempos
		Runtime.getRuntime().exec("rm -rf Tests").waitFor();
		Runtime.getRuntime().exec("mkdir Tests").waitFor();
		for(int t=1; t<=numTests; ++t) {
			//cria pasta auxiliar para salva resultado do teste corrente
			Runtime.getRuntime().exec("mkdir Tests/" + t).waitFor();
			Writer totalTimeFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Tests/" + t + "/total.txt"), "utf-8"));
			Writer networkTimeFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Tests/" + t + "/network.txt"), "utf-8"));
			for(int i=0; i<testsPerType; ++i) {
				long t0 = System.nanoTime();
				QueryResult result;				
				if(queriesType.charAt(t-1) == 'S')
					result = movieDatabase.executeSearchQuery(queries.get(t-1));
				else
					result = movieDatabase.executeUpdateQuery(queries.get(t-1));
				//calcula tempo transcorrido desde t0 em ms
				float totalTime = (float)(System.nanoTime() - t0)/1000000.0f;
				//calcula tempo aprox de transito de dados na rede
				float networkTime = totalTime - result.serverProcessingTime;
				totalTimeFile.write(totalTime + "\n");
				networkTimeFile.write(networkTime + "\n");				
			}
			totalTimeFile.close();
			networkTimeFile.close();
		}
	}
}
