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
				<li class="active"><a href="#section1" data-toggle="tab">Información</a></li>
				<li><a href="#section2" data-toggle="tab">Aptitudes</a></li>
				<li><a href="#messages-v" data-toggle="tab">Bajas</a></li>
			</ul>
		</div>

		<div class="col-md-9">

			<!-- First section: Basic person information  -->
			<div class="tab-content">
				<div class="tab-pane active" id="section1">
					<h3>
						Información <br> <small>Cambia la información básica
							de esta persona</small>
					</h3>
					<hr>
					<form:form class="form-horizontal" method="POST" action="/persons"
						modelAttribute="person">
						<div class="modal-body row">
							<div class="col-md-5">
								<div class="row">
									<label class="col-md-5 control-label">Nombre</label>
									<div class="col-md-7">
										<input class="form-control" id="namePerson" type="text"
											value=${person.name}>
									</div>

								</div>
								<br>
								<div class="row">
									<label class="col-md-5 control-label">Apellido 1</label>
									<div class="col-md-7">
										<input class="form-control" id="surname1Person" type="text"
											value=${person.surname1}>
									</div>
								</div>
								<br>
								<div class="row">
									<label class="col-md-5 control-label">Apellido 2</label>
									<div class="col-md-7">
										<input class="form-control" id="surname2Person" type="text"
											value=${person.surname2}>
									</div>
								</div>
								<br>
								<div class="row">
									<label class="col-md-5 control-label">DNI</label>
									<div class="col-md-7">
										<input class="form-control" id="nifPerson" type="text"
											value=${person.nif}>
									</div>
								</div>
								<br>
								<div class="row">
									<label class="col-md-5 control-label">Email</label>
									<div class="col-md-7">
										<input class="form-control" id="emailPerson" type="text"
											value=${person.email}>
									</div>
								</div>
								<br>
								<div class="row">
									<label class="col-md-5 control-label">Fecha de alta</label>
									<div class="col-md-7">
										<input class="form-control" id="hiredatePerson" type="text"
											value=${person.hiredate}>
									</div>
								</div>
							</div>
							<div class="col-md-offset-1 col-md-6">
								<label for="comment">Comentarios</label>
								<textarea class="form-control pull-right" rows="13"
								 id="commentPerson">${person.comment}</textarea>
							</div>
						</div>

						<br>

						<br>
							<button type="submit" class="btn btn-primary center-block">Guardar
								cambios</button>

					</form:form>
				</div>

				<!-- Second section: Aptitudes information  -->
				<div class="tab-pane" id="section2">
					<h3>
						Aptitudes <br> <small>Virtudes destacadas de esta
							persona</small>
					</h3>
					<hr>
					<div class="row">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#myModal">
							<span class="glyphicon glyphicon-plus"></span> Crear aptitud
						</button>
						<button type="button" class="btn btn-danger pull-right">
							<span class="glyphicon glyphicon-remove"></span> Borrar
							seleccionados
						</button>
					</div>

					<br>
					<table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>Id</th>
								<th>Nombre</th>
								<th>Tipo</th>
								<th>Valoración</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="aptitude" items="${aptitudes}">
								<tr>
									<td class="col-md-1 text-info">${aptitude.id}</td>
									<td class="col-md-2">${aptitude.name}</td>
									<td class="col-md-1">${aptitude.type.name}</td>
									<td class="col-md-1">${aptitude.value}</td>
									<td class="vert-align">
										<div class="col-md-1 checkbox-inline">
											<label><input type="checkbox" value=""></label>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
				<div class="tab-pane" id="messages-v">Messages Tab.</div>
			</div>
		</div>
		<!-- Modal -->
		<div id="myModal" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Nueva aptitud</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-offset-1 col-md-4 form-group">
								<label for="usr">Nombre</label> <input type="text"
									class="form-control" id="usr">
							</div>
							<div class="col-md-4 form-group">
								<label for="usr">Tipo</label> <input type="text"
									class="form-control" id="usr">
							</div>
							<div class="col-md-2 form-group">
								<label for="selectValue">Valoración</label> <select
									class="form-control" id="selectValue">
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
						<div class="modal-footer">
							<button type="button" class="btn btn-success"
								data-dismiss="modal">Crear</button>

							<button type="button" class="btn btn-default"
								data-dismiss="modal">Cerrar</button>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>


	<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>