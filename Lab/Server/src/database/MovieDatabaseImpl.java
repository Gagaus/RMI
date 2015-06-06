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
			connection = DriverManager.getConnection("jdbc:sqlite:movies.db");
			connection.setAutoCommit(false);
			statement = connection.createStatement();
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
	}

	@Override
	public QueryResult executeUpdateQuery(String query) throws RemoteException, SQLException {
		QueryResult result = new QueryResult();
		result.data = null;
		return result;
	}

	@Override
	public QueryResult executeSearchQuery(String query) throws RemoteException, SQLException {
		QueryResult result = new QueryResult();
		result.data = getSearchQueryResult(query);
		result.serverProcessingTime = 2.0f;
		return result;
	}
	
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
