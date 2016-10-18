<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="/webjars/bootstrap-datepicker/1.6.1/css/bootstrap-datepicker.min.css"
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
					<input type="text" class="form-control" id="pwd"
						placeholder="B�squeda">
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
				<button type="button" class="btn btn-success pull-right"
					data-toggle="modal" data-target="#myModal">
					<span class="glyphicon glyphicon-plus"></span> A�adir nueva persona
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
					<th></th>
					<th></th>
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
						<td class="col-md-1">
							<form action="/persons/${person.id}" method="get">
								<button type="submit" class="btn btn-info btn-xs center-block">
									<span class="glyphicon glyphicon-info-sign"></span> Info
								</button>
							</form>
						</td>
						<td class="col-md-1">
							<button type="button" class="btn btn-danger btn-xs center-block">
								<span class="glyphicon glyphicon-remove"></span> Borrado
							</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="row">
			<div class="col-md-2">
				<p class="text-info text-center">P�gina ${pageNumber} de
					${totalPage}</p>
			</div>
			<div class="col-md-offset-7 col-md-1">
				<form action="/persons" method="get">
					<button type="submit" name="page" value="${previousPage}"
						class="btn btn-default ${previousActive}">
						<span class="glyphicon glyphicon-chevron-left"></span> Anterior
					</button>
				</form>
			</div>
			<div class="col-md-offset-1 col-md-1">
				<form action="/persons" method="get">
					<button type="submit" name="page" value="${nextPage}"
						class="btn btn-default pull-right ${nextActive}">
						Siguiente <span class="glyphicon glyphicon-chevron-right"></span>
					</button>
				</form>
			</div>
		</div>
		
		<!-- ------------------------ Modal: Form Person creation  ------------------------ -->
		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Creaci�n de nueva persona</h4>
					</div>
					<div class="modal-body">

						<form:form class="form-horizontal" method="post" action='/persons' modelAttribute="person"
							name="employeeForm" id="employeeForm">
							<div class="form-group">
								<div class="col-md-4">
									<label>Nombre</label> <input type="text"
										class="form-control" name="name" id="name">
								</div>

								<div class="col-md-4">
									<label>Apellido 1</label> <input type="text"
										class="form-control" name="surname1" id="surname1">
								</div>

								<div class="col-md-4">
									<label>Apellido 2</label> <input type="text"
										class="form-control" name="surname2" id="surname2">
								</div>
							</div>

							<div class="form-group">
								<div class="col-md-4">
									<label>DNI</label> <input type="text"
										class="form-control" name="nif" id="nif" placeholder="12345678A">
								</div>

								<div class="col-md-4">
									<label>Email</label> <input type="text"
										class="form-control" name="email" id="email" placeholder="ejemplo@dominio.com">
								</div>

								<div class="col-md-4">
									<label>Fecha de alta</label> <input type="text"
										class="form-control datepicker" data-format="dd/MM/yyyy" name="hiredate"
										id="hiredate" placeholder="dd/mm/aaaa">
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-md-12">
									<label>Cometarios</label>
									<textarea class="form-control" rows="2" id="comment" name="comment" placeholder="Detalles a destacar..."></textarea>
								</div>
							</div>

							<div class="form-group">
								<button type="submit" class="btn btn-success center-block">A�adir</button>
							</div>
						</form:form>
					</div>
				</div>

			</div>
		</div>
		
		<div class="modal fade" id="successCreation" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Operaci�n exitosa</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12 center-block">
								Persona con id ${idPerson} a�adida correctamente.
							</div>
						</div>
					</div>
					<div class="modal-footer">
          				<button type="button" class="btn btn-success center-block" data-dismiss="modal">Aceptar</button>
        			</div>
				</div>

			</div>
		</div>
	</div>

	<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script src="/webjars/bootstrap-datepicker/1.6.1/js/bootstrap-datepicker.min.js"></script>
	<script src="/webjars/bootstrap-datepicker/1.6.1/locales/bootstrap-datepicker.es.min.js"></script>
	<script>
		$(document).ready(function() {
			$('.datepicker').datepicker({
				language : 'es'
			});
		})
	</script>
	<script type="text/javascript">
		$(window).load(function() {
			if ( "correctCreation" == '${action}' ) {
				$('#successCreation').modal('show');
			}
		});
	</script>
</body>

</html>