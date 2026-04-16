<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Book" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home page</title>
</head>

<body>

<h2>All Books</h2>

<%
    ArrayList<Book> books = (ArrayList<Book>) request.getAttribute("books");

    if (books == null || books.isEmpty()) {
%>

    <p>No books found</p>

<%
    } else {
%>

<table border="1" cellpadding="10">
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Author</th>
        <th>Date</th>
    </tr>

<%
        for (Book b : books) {
%>

    <tr>
        <td><%= b.getId() %></td>
        <td><%= b.getTitle() %></td>
        <td><%= b.getAuthor() %></td>
        <td><%= b.getDate() %></td>
        <td><a href="update-book?id=<%= b.getId() %>">Edit</a></td>
        <td><a href="delete-book?id=<%= b.getId() %>">Delete</a></td>
    </tr>

<%
        }
%>

</table>

<%
    }
%>

</body>
</html>