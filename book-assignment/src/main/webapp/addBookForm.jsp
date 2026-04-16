<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add new book</title>
</head>
<body>
	<h1>this is the add new book view</h1>
	<form action="add-new-book" method="post">

    Title: <input type="text" name="title"><br>
    Author: <input type="text" name="author"><br>
    Date: <input type="text" name="date"><br>
    Genres: <input type="text" name="genres"><br>
    Characters: <input type="text" name="characters"><br>
    Synopsis: <input type="text" name="synopsis"><br>

    <input type="submit" value="Add Book">

</form>
</body>
</html>