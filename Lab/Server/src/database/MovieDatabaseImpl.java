package database;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import rmi.MovieDatabase;
import rmi.QueryResult;

public class MovieDatabaseImpl extends UnicastRemoteObject implements MovieDatabase {

	private Connection connection;
	private Statement statement;
	
	public MovieDatabaseImpl() throws RemoteException {
		super();
		try {
			//cria conexao com o banco SQLite
			connection = DriverManager.getConnection("jdbc:sqlite:movies.db");
			statement = connection.createStatement();
		} catch (Exception e) {
			//em caso de erro, imprime o mesmo e encerra execucao
			System.err.println(e);
			System.exit(1);
		}
	}

	@Override
	public QueryResult executeUpdateQuery(String query) throws RemoteException, SQLException {
		long t0 = System.nanoTime();
//		printClientInfo(query, "Update");
		QueryResult result = new QueryResult();
		statement.executeUpdate(query);
		//query do tipo update nao possui retorno de dados
		result.data = null;
		//captura tempo transcorrido desde a solicitacao de execucao ao banco
		result.serverProcessingTime = (float)(System.nanoTime() - t0)/1000000.0f;
		return result;
	}

	@Override
	public QueryResult executeSearchQuery(String query) throws RemoteException, SQLException {
		long t0 = System.nanoTime();
//		printClientInfo(query, "Search");
		QueryResult result = new QueryResult();
		//matriz contendo os dados retornados pelo banco
		result.data = getSearchQueryResult(query);
		//captura tempo transcorrido desde a solicitacao de execucao ao banco		
		result.serverProcessingTime = (float)(System.nanoTime() - t0)/1000000.0f;
		return result;
	}
	//metodo auxiliar para imprimir na tela informacoes do cliente solicitante
	private void printClientInfo(String query, String requestType) {
		try {
			System.out.println("\n" + requestType + " Request: " + query);
			System.out.println("From          : " + getClientHost());
		} catch (Exception e) {}	
	}
	
	//metodo responsavel por criar matriz com o resultado da pesquisa realidada no banco
	private List<List<String>> getSearchQueryResult(String query) throws SQLException {
		List<List<String>> ans;
		try (ResultSet resultSet = statement.executeQuery(query)) {
			int numCols = resultSet.getMetaData().getColumnCount();
			ans = new ArrayList<>();
			while (resultSet.next()) {
				List<String> row = new ArrayList<>(numCols);
				for(int i=1; i<=numCols; ++i)
					row.add(resultSet.getString(i));
				ans.add(row);
			}
		}
		return ans;
	}
}
