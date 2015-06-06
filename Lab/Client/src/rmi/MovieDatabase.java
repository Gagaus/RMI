package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface MovieDatabase extends Remote {
	QueryResult executeUpdateQuery(String query) throws RemoteException, SQLException;
	QueryResult executeSearchQuery(String query) throws RemoteException, SQLException;
}
