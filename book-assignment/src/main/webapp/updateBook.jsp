<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="models.Book" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Book</title>
</head>

<body>

<%
    Book book = (Book) request.getAttribute("book");

    if (book == null) {
%>
    <p>Error: Book not found</p>
<%
        return;
    }
%>

<h2>Update Book</h2>

<form action="update-book" method="post">

    <input type="hidden" name="id" value="<%= book.getId() %>">

    Title: <input type="text" name="title" value="<%= book.getTitle() %>"><br>
    Author: <input type="text" name="author" value="<%= book.getAuthor() %>"><br>
    Date: <input type="text" name="date" value="<%= book.getDate() %>"><br>
    Genres: <input type="text" name="genres" value="<%= book.getGenres() %>"><br>
    Characters: <input type="text" name="characters" value="<%= book.getCharacters() %>"><br>
    Synopsis: <input type="text" name="synopsis" value="<%= book.getSynopsis() %>"><br>

    <input type="submit" value="Update Book">

</form>

</body>
</html>