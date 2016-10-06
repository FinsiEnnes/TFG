<!DOCTYPE html>
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
		<hr>
		<br>

		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<input type="password" class="form-control" id="pwd"
						placeholder="Búsqueda">
				</div>
			</div>
			<div class="col-md-2">
				<div class="btn-group">
					<button type="button" class="btn btn-primary">Filtrar</button>
					<button type="button" class="btn btn-primary dropdown-toggle"
						data-toggle="dropdown">
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li><a href="#">ID</a></li>
						<li><a href="#">Nombre</a></li>
						<li><a href="#">Nif</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-offset-5 col-md-2">
				<button type="button" class="btn btn-success"> 
					<span class="glyphicon glyphicon-plus"></span>  Crear nueva persona
				</button>
			</div>
		</div>

		<br>
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>Id</th>
					<th>Nombre</th>
					<th>Nif</th>
					<th>Email</th>
					<th>Alta</th>
					<th>Acción</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="person" items="${persons}">
					<tr>
						<td class="col-md-1 text-info">${person.id}</td>
						<td class="col-md-3">${person.name} ${person.surname1}
							${person.surname2}</td>
						<td class="col-md-1">${person.nif}</td>
						<td class="col-md-3">${person.email}</td>
						<td class="col-md-2">${person.hiredate}</td>
						<td class="col-md-2">
							<button type="button" class="btn btn-info btn-xs">
								<span class="glyphicon glyphicon-info-sign"></span> Info
							</button>
							<button type="button" class="btn btn-danger btn-xs pull-right">
								<span class="glyphicon glyphicon-remove"></span> Borrado
							</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="row">
			<div class="col-md-2">
				<p class="text-info text-center">Página ${pageNumber} de ${totalPage}</p>
			</div>
			<div class="col-md-offset-6 col-md-1">
				<button type="button" class="btn btn-default">
					<span class="glyphicon glyphicon-chevron-left"></span> Anterior
				</button>
			</div>
			<div class="col-md-offset-1 col-md-1">
				<button type="button" class="btn btn-default">
					Siguiente <span class="glyphicon glyphicon-chevron-right"></span>
				</button>
			</div>
		</div>
	</div>

	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>