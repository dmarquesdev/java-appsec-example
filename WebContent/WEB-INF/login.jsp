<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<div class="container">
		<form action="login" method="post">
			<label for="username">
				Username
			</label>
			<input type="text" name="username"><br/>
			<label for="password">
				Password
			</label>
			<input type="password" name="password"><br/>
			<input type="submit" value="Log in">
		</form>
		${errorMessage}
	</div>
</body>
</html>