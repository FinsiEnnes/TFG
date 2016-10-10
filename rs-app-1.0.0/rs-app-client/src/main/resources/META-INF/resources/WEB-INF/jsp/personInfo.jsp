<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<h3>
			Persona <br> <small>Cambia la información de esta persona</small>
		</h3>
		
		<form:form method="POST" action="/persons" modelAttribute="person">
		<table>
			<tr>
				<td><form:label path="name">Name</form:label></td>
				<td><form:input path="name" /></td>
			</tr>
			<tr>
				<td><form:label path="surname1">Surname 1</form:label></td>
				<td><form:input path="surname1" /></td>
			</tr>
			<tr>
				<td><form:label path="surname2">Surname 2</form:label></td>
				<td><form:input path="surname2" /></td>
			</tr>
			<tr>
				<td><form:label path="nif">Nif</form:label></td>
				<td><form:input path="nif" /></td>
			</tr>
			<tr>
				<td><form:label path="email">Email</form:label></td>
				<td><form:input path="email" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form:form>
	</div>

	<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>