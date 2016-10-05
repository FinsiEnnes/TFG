<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<h2>
			<b>Lista de Personas</b>
		</h2>
		<br>

		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<input type="password" class="form-control" id="pwd" placeholder="Búsqueda">
				</div>
			</div>
			<div class="col-md-1">
				<button type="button" class="btn btn-primary">Filtrar</button>
			</div>
			<div class="col-md-offset-6 col-md-2">
				<button type="button" class="btn btn-success">Crear nueva
					persona</button>
			</div>
		</div>
		
		<form>
			<label class="radio-inline"> <input type="radio"
				name="optradio">ID
			</label> <label class="radio-inline"> <input type="radio"
				name="optradio">Nombre
			</label> <label class="radio-inline"> <input type="radio"
				name="optradio">Nif
			</label>
		</form>
		
		<br>
		<table class="table  table-bordered table-striped">
			<thead>
				<tr>
					<th>Id</th>
					<th>Nombre</th>
					<th>Nif</th>
					<th>Email</th>
					<th>Alta</th>
					<th>Detalles</th>
				</tr>
			</thead>
			<tbody>
		        <c:forEach var="person" items="${persons}">
		            <tr>
		                <td>${person.id}</td>
		                <td>${person.name} ${person.surname1} ${person.surname2}</td>
		                <td>${person.nif}</td>
		                <td>${person.email}</td>
		                <td>${person.hiredate}</td>
						<td><button type="button" class="btn btn-link btn-xs">+</button></td>
					</tr>       
		        </c:forEach>
		    </tbody>
		</table>
		
		<div class="row">
			<div class="col-md-1">
			<button type="button" class="btn btn-default">&#10094; Anterior</button>
			</div>
			<div class="col-md-offset-2 col-md-1">
			<button type="button" class="btn btn-default">Siguiente &#10095;</button>
			</div>
		</div>
		
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>