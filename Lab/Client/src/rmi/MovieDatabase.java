package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

//interface disponivel no cliente e no servidor
//os metodos declarados aqui podem ser acessados remotamente
public interface MovieDatabase extends Remote {
	QueryResult executeUpdateQuery(String query) throws RemoteException, SQLException;
	QueryResult executeSearchQuery(String query) throws RemoteException, SQLException;
}
