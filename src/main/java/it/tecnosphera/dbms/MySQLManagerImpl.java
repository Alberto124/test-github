package it.tecnosphera.dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLManagerImpl implements MySQLManager{

	private int indiceRigaCorrente = 0;
	private int pageSize = 5;
	
	private static final String url = "127.0.0.1";
	private static final String port = "3306";
	private static final String user = "root";
	private static final String password = "rootmachine";
	private static final String nomeDatabase = "awesome";
	Connection conn = null;
	public static final String filePath= "C:\\Work\\Titoli.txt";
	
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");

		String connectionString = "jdbc:mysql://" +  url + ":" + port + "/" + 
				nomeDatabase + "?user=" + user + "&password=" + password;
		
	    Connection conn = DriverManager.getConnection( connectionString );
	    return conn;
		
	}
	
	public void writeBooks(List<Book> books) {

		PreparedStatement stmt = null;
		PreparedStatement stmtPublisher = null;
		
		for(Book book1:books){
			
			Book book = book1; 
			
			try{
				//conn=getConnection();
				
				int idPublisher=getIdPublisher(book.getPublisher());
				
				if(idPublisher==0){
					insertPublisher(book.getPublisher());
				}	
				
				insertBook(book);
				
			}catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (SQLException e) {
				
				e.printStackTrace();
			} finally {
					try {
						if (null != stmt) stmt.close();
						if (null != conn) conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		
		}
	}
	
	public boolean insertBook(Book book) throws ClassNotFoundException, SQLException{
		
		String sql = "INSERT INTO awesome.book(title, author, edition, publisher) VALUES (?, ?, ?, ?)";
		
		conn=getConnection();
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, book.getTitle());
		stmt.setString(2, book.getAuthor());
		stmt.setInt(3, book.getEdition());
		stmt.setInt(4, book.getPublisher().getId());
		
		boolean retVal = stmt.execute();
		
		return retVal;
	}
	
	public boolean insertPublisher(Publisher publisher) throws  ClassNotFoundException, SQLException{
		
		int id=-1;
		
		String sqlPublisher="INSERT INTO awesome.publisher(name) VALUES(?)";
		
		conn=getConnection();
		
		PreparedStatement stmtPublisher = conn.prepareStatement(sqlPublisher, PreparedStatement.RETURN_GENERATED_KEYS);
		
		stmtPublisher.setString(1, publisher.getNome());
		
		boolean retValPublisher = stmtPublisher.execute();
		
		ResultSet rs= stmtPublisher.getGeneratedKeys();
		
		if (rs != null && rs.next()) {
		    id = rs.getInt(1);
		    publisher.setId(id);
		}
		
		return retValPublisher;
		
	}
	
	public int getIdPublisher(Publisher publisher) throws ClassNotFoundException, SQLException{
			
		String sql = "SELECT * FROM awesome.publisher WHERE name='" + publisher.getNome() + "'";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int id=0;
		//Fare sempre una connection e non solo una connection !!!!
		
			conn=getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery(sql);
			
			if (rs.first()) {
				id=rs.getInt("id");		
				publisher.setId(id);
			} 
				
		return id;
		
	}
	
	public static void main(String args[]){
		//Fare un unica classe Main
		MySQLManagerImpl managerImpl=new MySQLManagerImpl();
		
		
		
		try{
			ReadFileImpl app = new ReadFileImpl();
			
			List<Book> books=app.readFile(filePath);
			
			managerImpl.writeBooks(books);
		}catch(Exception exc){
			
			exc.printStackTrace();
			
		}
	}

}

