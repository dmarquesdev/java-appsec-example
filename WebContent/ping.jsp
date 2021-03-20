<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.io.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ping Utility</title>
</head>
<body>
	<div class="container">
		<form action="ping.jsp" method="post">
			<label for="cmd">
				Input an IP address to ping.
			</label>
			<input name="cmd" type="text"></input>
			<input type="submit" name="submit" value="Ping!"></input>
		</form>
		<br />
		<%
		String cmd = request.getParameter("cmd");
		if(cmd != null && !cmd.equals("")) {
			String fullCmd = "ping -t 4 " + cmd;
			out.println(fullCmd);
			Process p = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", fullCmd});
			InputStreamReader isr = new InputStreamReader(p.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while((line = br.readLine()) != null) {
				out.println(line + "<br/>");
			}
		}
		%>
	</div>
</body>
</html>