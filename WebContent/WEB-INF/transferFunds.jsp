<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transfer funds</title>
</head>
<body>
	<div class="container">
		<form method="post" action="transferFunds">
			<label for="from">From</label>
			<select name="from">
				<option value="" selected>-- Select a user --</option>
				<c:forEach items="${users}" var="user">
					<option value="${user.id}"><c:out value="${user.id} - ${user.name} - ${user.funds}" />
				</c:forEach>
			</select>
			<br />
			<label for="to">To</label>
			<select name="to">
				<option value="" selected>-- Select a user --</option>
				<c:forEach items="${users}" var="user">
					<option value="${user.id}"><c:out value="${user.id} - ${user.name} - ${user.funds}" />
				</c:forEach>
			</select>
			<br />
			<label for="value">Value</label>
			<input type="text" name="value">
			<input type="submit" value="Transfer">
		</form>
	</div>
</body>
</html>