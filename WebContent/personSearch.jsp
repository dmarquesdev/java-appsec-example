<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Person Search</title>
</head>
<body>
	<div>
      <form action="person" method="get">
			<label for="name">
				Search by name
			</label>
			<input name="name" type="text"></input>
			<input name="orderBy" type="hidden" value="email"></input>
			<input type="submit" name="submit" value="Search"></input>
		</form>
    </div>
</body>
</html>