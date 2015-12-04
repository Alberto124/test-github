package it.tecnosphera.dbms;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MySQLManager {
	public Connection getConnection() throws ClassNotFoundException, SQLException;
	public  void writeBooks(List<Book> books);
}
