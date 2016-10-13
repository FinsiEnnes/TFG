<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link href="/css/customTabs.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<h3>Persona</h3>
		<br>

		<div class="col-md-3">
			<!-- required for floating -->
			<!-- Nav tabs -->
			<ul class="nav nav-tabs tabs-left sideways">
				<li class="${section1State}"><a href="#section1"
					data-toggle="tab">Información</a></li>
				<li class="${section2State}"><a href="#section2"
					data-toggle="tab">Aptitudes</a></li>
				<li class="${section3State}"><a href="#messages-v"
					data-toggle="tab">Bajas</a></li>
			</ul>
		</div>

		<div class="col-md-9">

			<!-- First section: Basic person information  -->
			<div class="tab-content">
				<div class="tab-pane ${section1State}" id="section1">
					<h3>
						Información <br> <small>Cambia la información básica
							de esta persona</small>
					</h3>
					<hr>
					<form:form class="form-horizontal" method="POST" action="/persons"
						modelAttribute="person">

						<div class="row">
							<label class="col-md-2 control-label">Nombre</label>
							<div class="col-md-4">
								<input class="form-control" id="namePerson" type="text"
									value="${person.name}">
							</div>

							<label class="col-md-2 control-label">DNI</label>
							<div class="col-md-4">
								<input class="form-control" id="nifPerson" type="text"
									value=${person.nif}>
							</div>
						</div>

						<br>
						<div class="row">
							<label class="col-md-2 control-label">Apellido 1</label>
							<div class="col-md-4">
								<input class="form-control" id="surname1Person" type="text"
									value=${person.surname1}>
							</div>

							<label class="col-md-2 control-label">Email</label>
							<div class="col-md-4">
								<input class="form-control" id="emailPerson" type="text"
									value=${person.email}>
							</div>
						</div>

						<br>
						<div class="row">
							<label class="col-md-2 control-label">Apellido 2</label>
							<div class="col-md-4">
								<input class="form-control" id="surname2Person" type="text"
									value=${person.surname2}>
							</div>

							<label class="col-md-2 control-label">Fecha de alta</label>
							<div class="col-md-4">
								<input class="form-control" id="hiredatePerson" type="text"
									value=${person.hiredate}>
							</div>
						</div>

						<br>
						<div class="row">
							<label class="col-md-2 control-label" for="comment">Comentarios</label>
							<div class="col-md-10">
								<textarea class="form-control" rows="8" id="commentPerson">${person.comment}</textarea>
							</div>
							<div class="col-md-3"></div>
						</div>

						<br>
						<div class="row">
							<div class="col-md-offset-10 col-md-2">
								<button type="submit" class="btn btn-primary pull-right">Guardar
									cambios</button>
							</div>
						</div>
					</form:form>
				</div>

				<!-- Second section: Aptitudes information  -->
				<div class="tab-pane ${section2State}" id="section2">
					<h3>
						Aptitudes <br> <small>Virtudes destacadas de esta
							persona</small>
					</h3>
					<hr>
					<div class="row">
						<div class="col-md-offset-7 col-md-2">
							<button type="button" class="btn btn-success" data-toggle="modal"
								data-target="#myModal">
								<span class="glyphicon glyphicon-plus"></span> Crear aptitud
							</button>
						</div>
						<div class="col-md-offset-1 col-md-2">
							<button type="button" class="btn btn-danger pull-right">
								<span class="glyphicon glyphicon-remove"></span> Borrar
								seleccionados
							</button>
						</div>
					</div>

					<br>
					<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>Id</th>
								<th>Nombre</th>
								<th>Tipo</th>
								<th>Valoración</th>
								<th>Comentario</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="aptitude" items="${aptitudes}">
								<tr>
									<td class="col-md-1 text-info">${aptitude.id}</td>
									<td class="col-md-2">${aptitude.name}</td>
									<td class="col-md-2">${aptitude.type.name}</td>
									<td class="col-md-1">${aptitude.value}</td>
									<td class="col-md-5">${aptitude.comment}</td>
									<td class="col-md-1">
										<div class="col-md-1 checkbox-inline">
											<label><input type="checkbox"></label>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
				<div class="tab-pane ${section3State}" id="messages-v">Messages
					Tab.</div>
			</div>
		</div>

		<!-- Modal -->
		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Creación de nueva aptitud</h4>
					</div>
					<div class="modal-body">
						
						<form class="form-horizontal" method="post" action='/persons/${person.id}' name="employeeForm" id="employeeForm">
							<div class="form-group">
								<div class="col-md-7">
									<label for="usr">Nombre</label> 
									<input type="text" class="form-control" name="nameAptitude" id="nameAptitude" title="First Name">
								</div>

								<div class="col-md-3">
									<label for="selectValue">Tipo</label> <select
										class="form-control" name="typeAptitude" id="typeAptitude">
										<option>Artistica</option>
										<option>Cientifica</option>
										<option>Directiva</option>
										<option>Espacial</option>
										<option>Mecanica</option>
										<option>Numerica</option>
										<option>Organizativa</option>
										<option>Social</option>
									</select>
								</div>

								<div class="col-md-2">
									<label for="selectValue">Valoración</label> <select
										class="form-control" name="valueAptitude" id="valueAptitude">
										<option>1</option>
										<option>2</option>
										<option>3</option>
										<option>4</option>
										<option>5</option>
										<option>6</option>
										<option>7</option>
										<option>8</option>
										<option>9</option>
										<option>10</option>
									</select>
								</div>
							</div>
							
							<div class="form-group">
								<button type="submit" class="btn btn-success center-block">Crear aptitud</button>
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>

	</div>


	<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>