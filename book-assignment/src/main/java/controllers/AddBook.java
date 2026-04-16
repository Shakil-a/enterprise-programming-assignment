package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.BookDAO;
import models.Book;


@WebServlet("/add-new-book")
public class AddBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("addBookForm.jsp");
		rd.forward(request, response);

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
	        // 1. Get form data
	        String title = request.getParameter("title");
	        String author = request.getParameter("author");
	        String date = request.getParameter("date");
	        String genres = request.getParameter("genres");
	        String characters = request.getParameter("characters");
	        String synopsis = request.getParameter("synopsis");

	        // 2. Create Book object
	        Book newBook = new Book();
	        newBook.setTitle(title);
	        newBook.setAuthor(author);
	        newBook.setDate(date);
	        newBook.setGenres(genres);
	        newBook.setCharacters(characters);
	        newBook.setSynopsis(synopsis);

	        // 3. Call DAO
	        BookDAO dao = new BookDAO();
	        dao.insertBook(newBook);

	        // 4. Redirect to home (VERY IMPORTANT)
	        response.sendRedirect("home");

	    } catch (Exception e) {
	        e.printStackTrace();

	        response.getWriter().println("Error adding book");
	    }
		
		
	}

}
