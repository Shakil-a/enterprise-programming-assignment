package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import models.Book;


public class BookDAO {
	
	Book oneBook = null;
	Connection conn = null;
    Statement stmt = null;
	String user = "ahmeshak";
    String password = "8Wakefhyog";
    // Note none default port used, 6306 not 3306
    String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/" + user;

	public BookDAO() {}

	
	private void openConnection(){
		// loading jdbc driver for mysql
		try{
		    Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		} catch(Exception e) { System.out.println(e); }

		// connecting to database
		try{
			// connection string for demos database, username demos, password demos
 			conn = DriverManager.getConnection(url, user, password);
		    stmt = conn.createStatement();
		} catch(SQLException se) { System.out.println(se); }	   
    }
	private void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Book getNextBook(ResultSet rs){
    	Book thisBook=null;
		try {
			
			thisBook = new Book(
					rs.getInt("id"),
					rs.getString("title"),
					rs.getString("author"),
					rs.getString("date"),
					rs.getString("genres"),
					rs.getString("characters"),
					rs.getString("synopsis"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return thisBook;		
	}
	
	
	
   public ArrayList<Book> getAllBooks(){
	   
		ArrayList<Book> allBooks = new ArrayList<Book>();
		openConnection();
		
	    // Create select statement and execute it
		try{
		    String selectSQL = "select * from books";
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
	    // Retrieve the results
		    while(rs1.next()){
		    	oneBook = getNextBook(rs1);
		    	allBooks.add(oneBook);
		   }

		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }

	   return allBooks;
   }
   
   public int insertBook(Book b) throws SQLException {

	   int rowsAffected = 0;
	    openConnection();

	    String sql = "INSERT INTO books (title, author, date, genres, characters, synopsis) VALUES (?, ?, ?, ?, ?, ?)";

	    try {
	        PreparedStatement preparedStatement = conn.prepareStatement(sql);

	        preparedStatement.setString(1, b.getTitle());
	        preparedStatement.setString(2, b.getAuthor());
	        preparedStatement.setString(3, b.getDate());
	        preparedStatement.setString(4, b.getGenres());
	        preparedStatement.setString(5, b.getCharacters());
	        preparedStatement.setString(6, b.getSynopsis());

	        rowsAffected = preparedStatement.executeUpdate();

	        preparedStatement.close();
	        closeConnection();
	    } catch (SQLException e) {
	        throw new SQLException("This book has not been saved to the database successfully");
	    }

	    return rowsAffected;
   }
   
   public void updateBook(Book b) throws SQLException {

	    openConnection();

	    String sql = "UPDATE books SET title=?, author=?, date=?, genres=?, characters=?, synopsis=? WHERE id=?";

	    try {
	        PreparedStatement preparedStatement = conn.prepareStatement(sql);

	        preparedStatement.setString(1, b.getTitle());
	        preparedStatement.setString(2, b.getAuthor());
	        preparedStatement.setString(3, b.getDate());
	        preparedStatement.setString(4, b.getGenres());
	        preparedStatement.setString(5, b.getCharacters());
	        preparedStatement.setString(6, b.getSynopsis());
	        preparedStatement.setInt(7, b.getId());

	        preparedStatement.executeUpdate();

	        preparedStatement.close();
	        closeConnection();

	    } catch (SQLException e) {
	        throw new SQLException("Book was not updated successfully");
	    }
	}

   public void deleteBook(Book b) throws SQLException {

	    openConnection();

	    String sql = "DELETE FROM books WHERE id=?";

	    try {
	        PreparedStatement preparedStatement = conn.prepareStatement(sql);

	        preparedStatement.setInt(1, b.getId());

	        preparedStatement.executeUpdate();

	        preparedStatement.close();
	        closeConnection();

	    } catch (SQLException e) {
	        throw new SQLException("Book was not deleted successfully");
	    }
	}
   
   public Collection<Book> searchbooks(String searchStr) throws SQLException {

	    ArrayList<Book> books = new ArrayList<>();

	    openConnection();

	    String sql = "SELECT * FROM books WHERE title LIKE ?";

	    try {
	        PreparedStatement preparedStatement = conn.prepareStatement(sql);

	        preparedStatement.setString(1, "%" + searchStr + "%");

	        ResultSet rs = preparedStatement.executeQuery();

	        while (rs.next()) {
	            books.add(getNextBook(rs));
	        }

	        preparedStatement.close();
	        closeConnection();

	    } catch (SQLException e) {
	        throw new SQLException("Search failed");
	    }

	    return books;
	}
   
   public Book getBookById(int id) {

	    Book book = null;
	    openConnection();

	    String sql = "SELECT * FROM books WHERE id=?";

	    try {
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, id);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            book = getNextBook(rs);
	        }

	        ps.close();
	        closeConnection();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return book;
	}
   
}
