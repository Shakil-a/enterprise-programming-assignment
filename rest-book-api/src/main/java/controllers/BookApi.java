package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;

import database.BookDAO;
import models.Book;
import models.BookList;
import models.ApiResponse;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

@WebServlet("/book-api")
public class BookApi extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private void writeResponse(HttpServletResponse response, String format, Object data) throws IOException {

        PrintWriter out = response.getWriter();

        try {
            if (format != null && format.contains("application/xml")) {

                response.setContentType("application/xml");

                JAXBContext context;

                if (data instanceof ArrayList) {
                    BookList wrapper = new BookList((ArrayList<Book>) data);
                    context = JAXBContext.newInstance(BookList.class);

                    Marshaller marshaller = context.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    marshaller.marshal(wrapper, response.getWriter());

                } else {
                    context = JAXBContext.newInstance(data.getClass());

                    Marshaller marshaller = context.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    marshaller.marshal(data, response.getWriter());
                }

            } else if (format != null && format.contains("text/plain")) {

                response.setContentType("text/plain");
                if (data instanceof ApiResponse) {
                    out.write(((ApiResponse) data).getMessage());
                } else {
                    out.write(data.toString());
                }

            } else {

                response.setContentType("application/json");
                Gson gson = new Gson();
                out.write(gson.toJson(data));
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.write("Response error: " + e.getMessage());
        }

        out.close();
    }

 
    private Book parseRequestBody(HttpServletRequest request) throws Exception {

        String contentType = request.getContentType();

        String body = request.getReader()
                .lines()
                .reduce("", (a, b) -> a + b);

        if (contentType != null && contentType.contains("json")) {
            return new Gson().fromJson(body, Book.class);
        }

        if (contentType != null && contentType.contains("xml")) {

            JAXBContext context = JAXBContext.newInstance(Book.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (Book) unmarshaller.unmarshal(new StringReader(body));
        }

        throw new Exception("Unsupported Content-Type: " + contentType);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String format = request.getHeader("Accept");
        if (format == null) format = "application/json";

        BookDAO dao = new BookDAO();
        ArrayList<Book> books = dao.getAllBooks();

        writeResponse(response, format, books);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String format = request.getHeader("Accept");
        if (format == null) format = "application/json";

        try {
            Book book = parseRequestBody(request);

            BookDAO dao = new BookDAO();
            dao.insertBook(book);
            response.setStatus(HttpServletResponse.SC_CREATED);
            writeResponse(response, format, new ApiResponse("Book inserted", true));

        } catch (Exception e) {
        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeResponse(response, format,
                    new ApiResponse("Invalid request: " + e.getMessage(), false));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String format = request.getHeader("Accept");
        if (format == null) format = "application/json";

        try {
            Book book = parseRequestBody(request);

            BookDAO dao = new BookDAO();
            dao.updateBook(book);
            response.setStatus(HttpServletResponse.SC_OK);
            writeResponse(response, format, new ApiResponse("Book updated", true));

        } catch (Exception e) {
        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeResponse(response, format,
                    new ApiResponse("Invalid request: " + e.getMessage(), false));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String format = request.getHeader("Accept");
        if (format == null) format = "application/json";

        try {
            Book book = parseRequestBody(request);

            BookDAO dao = new BookDAO();
            dao.deleteBook(book);
            response.setStatus(HttpServletResponse.SC_OK);
            writeResponse(response, format, new ApiResponse("Book deleted", true));

        } catch (Exception e) {
        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeResponse(response, format,
                    new ApiResponse("Invalid request: " + e.getMessage(), false));
        }
    }
}