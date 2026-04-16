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


@WebServlet("/update-book")
public class UpdateBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        BookDAO dao = new BookDAO();
        Book book = dao.getBookById(id);

        request.setAttribute("book", book);

        RequestDispatcher rd = request.getRequestDispatcher("updateBook.jsp");
        rd.forward(request, response);
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String date = request.getParameter("date");
            String genres = request.getParameter("genres");
            String characters = request.getParameter("characters");
            String synopsis = request.getParameter("synopsis");

            Book book = new Book();
            book.setId(id);
            book.setTitle(title);
            book.setAuthor(author);
            book.setDate(date);
            book.setGenres(genres);
            book.setCharacters(characters);
            book.setSynopsis(synopsis);

            BookDAO dao = new BookDAO();
            dao.updateBook(book);

            response.sendRedirect("home");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error updating book");
        }
    }

}
