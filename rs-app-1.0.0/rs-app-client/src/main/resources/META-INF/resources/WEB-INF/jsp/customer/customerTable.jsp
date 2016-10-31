<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<link 
	href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="/webjars/bootstrap-datepicker/1.6.1/css/bootstrap-datepicker.min.css"
	rel="stylesheet">
<link 
	href="/webjars/bootstrap-table/1.11.0/src/bootstrap-table.css"
	rel="stylesheet">
</head>
<body>

	<div class="container">
		<h2>
			<b>Lista de Clientes</b>
		</h2>
		<hr>
		
		<!-- ------------------ Table whose rows include Customer information  ------------------ -->
		<table id="customer_table" class="table table-bordered"  data-toggle="table">
			<thead>
				<tr>
					<th>ID</th>
					<th>Nombre</th>
					<th>País</th>
					<th>Provincia</th>
					<th>Tipo</th>
					<th>Categoría</th>
					<th>Tamaño</th>
					<th>Acción</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="customer" items="${customers}">
					<tr>
						<td id="idPerson" class="col-md-1 text-info">${customer.id}</td>
						<td class="col-md-1">${customer.name}</td>
						<td class="col-md-1">${customer.country}</td>
						<td class="col-md-1">${customer.province}</td>
						<td class="col-md-1">${customer.type}</td>
						<td class="col-md-2">${customer.category}</td>
						<td class="col-md-1">
							${customer.size}
						</td>
						<td class="col-md-1">
							<table>
								<tr>
									<td>
									<form action="/persons" method="get">
										<button type="submit"
											class="btn btn-primary btn-xs center-block">
											<span class="glyphicon glyphicon-edit"></span>
										</button>
									</form>
									</td>
									<td>
										<button id="deletePerson" type="button"
											class="btn btn-danger btn-xs center-block"
											 data-toggle="modal" data-target="#confirmDelete" 
											 >
											<span class="glyphicon glyphicon-remove"></span>
										</button>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>
	
	</div>

	<!-- ---------------------------------- Script section  ----------------------------------- -->
	<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script
		src="/webjars/bootstrap-datepicker/1.6.1/js/bootstrap-datepicker.min.js"></script>
	<script
		src="/webjars/bootstrap-datepicker/1.6.1/locales/bootstrap-datepicker.es.min.js"></script>
	<script src="/webjars/bootstrap-validator/0.11.5/dist/validator.js"></script>
	<script src="/webjars/bootstrap-validator/0.11.5/dist/validator.min.js"></script>
	<script src="/webjars/bootstrap-table/1.11.0/src/bootstrap-table.js"></script>
</body>
</html>