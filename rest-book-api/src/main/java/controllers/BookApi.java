package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;

import database.BookDAO;
import models.Book;

@WebServlet("/book-api")
public class BookApi extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        BookDAO dao = new BookDAO();
        ArrayList<Book> books = dao.getAllBooks();

        Gson gson = new Gson();
        String json = gson.toJson(books);

        out.write(json);
        out.close();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        try {
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String date = request.getParameter("date");
            String genres = request.getParameter("genres");
            String characters = request.getParameter("characters");
            String synopsis = request.getParameter("synopsis");

            Book book = new Book(0, title, author, date, genres, characters, synopsis);

            BookDAO dao = new BookDAO();
            dao.insertBook(book);

            out.write("Book inserted");

        } catch (Exception e) {
            e.printStackTrace();
            out.write("Error inserting book");
        }

        out.close();
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        PrintWriter out = response.getWriter();

        try {
            String json = request.getReader()
                    .lines()
                    .reduce("", (acc, line) -> acc + line);

            Gson gson = new Gson();
            Book book = gson.fromJson(json, Book.class);

            BookDAO dao = new BookDAO();
            dao.updateBook(book);

            out.write("Book updated");

        } catch (Exception e) {
            e.printStackTrace();
            out.write("Error updating book");
        }

        out.close();
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        PrintWriter out = response.getWriter();

        try {
            String json = request.getReader()
                    .lines()
                    .reduce("", (acc, line) -> acc + line);

            Gson gson = new Gson();
            Book book = gson.fromJson(json, Book.class);

            BookDAO dao = new BookDAO();
            dao.deleteBook(book);

            out.write("Book deleted");

        } catch (Exception e) {
            e.printStackTrace();
            out.write("Error deleting book");
        }

        out.close();
    }
    
}