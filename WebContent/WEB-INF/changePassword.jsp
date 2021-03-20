<%@page import="com.appsec.java.model.Person"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Change Password</title>
</head>
<body>
	<div class="container">
		<form action="changePassword" method="post" onSubmit="return validatePassword(this)">
			<c:choose>
			<c:when test="${admin}">
				<label for="username">
					User
				</label>
				<select name="username">
					<option value="" selected>-- Select a user --</option>
					<c:forEach items="${users}" var="user">
						<option value="${user.username}"><c:out value="${user.id} - ${user.name}" />
					</c:forEach>
				</select>
			</c:when>
			<c:otherwise>
				<label for="oldPassword">
					Old Password
				</label>
				<input type="password" name="oldPassword"><br/>
			</c:otherwise>
			</c:choose>
			<label for="newPassword">
				New Password
			</label>
			<input type="password" name="newPassword"><br/>
			<input type="submit" value="Change Password">
			<br />
			<p><c:out value="${message}"></c:out></p>
		</form>
	</div>
	<script>
		var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/
		function validatePassword(form) {
			if (form.newPassword.value.search(regex)) {
				alert("Password must contain at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character");
				return false;
			}

			return true;
		}
	</script>
</body>
</html>