<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="/webjars/bootstrap-table/1.11.0/src/bootstrap-table.css">
<link
	href="/webjars/bootstrap-datepicker/1.6.1/css/bootstrap-datepicker.min.css"
	rel="stylesheet">
<link href="/css/customTabs.css" rel="stylesheet">

</head>
<body>
	<div class="container">
		<h3>Persona</h3>
		<br>

		<!-- ------ Interface sections. From the controller we set which one is actived.  ----- -->
		<div class="col-md-3">
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

		<!-- --------------------- First section: Basic person information -------------------- -->
			<div class="tab-content">
				<div id="section1" class="tab-pane ${section1State}">
					<h3>
						Información <br> 
						<small>Cambia la información básica	de esta persona</small>
					</h3>
					<hr>
					<form:form id="personForm" class="form-horizontal" method="post" 
						action="/persons/${person.id}/update" modelAttribute="person"
						role="form" data-toggle="validator">

						<!-- First row -->
						<div class="row">
							<label class="col-md-2 control-label">Nombre</label>
							<div class="form-group col-md-4">
								<input class="form-control" name="name" id="name" type="text"
									value="${person.name}" required>
							</div>

							<label class="col-md-2 control-label">DNI</label>
							<div class="form-group col-md-4">
								<input class="form-control" readonly name="nif" id="nif" type="text"
									value="${person.nif}" required>
							</div>
						</div>
						<br>
						
						<!-- Second row -->
						<div class="row">
							<label class="col-md-2 control-label">Apellido 1</label>
							<div class="form-group col-md-4">
								<input class="form-control" name="surname1" id="surname1"
									type="text" value="${person.surname1}" required>
							</div>

							<label class="col-md-2 control-label">Email</label>
							<div class="form-group col-md-4">
								<input class="form-control" name="email" id="email" type="text"
									value="${person.email}" required>
							</div>
						</div>
						<br>
						
						<!-- Third row -->
						<div class="row">
							<label class="col-md-2 control-label">Apellido 2</label>
							<div class="form-group col-md-4">
								<input class="form-control" name="surname2" id="surname2"
									type="text" value="${person.surname2}" required>
							</div>

							<label class="col-md-2 control-label">Fecha de alta</label>
							<div class="form-group col-md-4 date">
								<input type="text" class="form-control datepicker"
									data-format="dd/MM/yyyy" name="hiredate" id="hiredate"
									placeholder="dd/mm/aaaa" required value="${person.hiredate}">
							</div>
						</div>
						<br>

						<!-- Fourth row -->
						<div class="row">
							<label class="col-md-2 control-label" for="comment">Comentarios</label>
							<div class="form-group col-md-10" style="width: 79.8%">
								<textarea class="form-control pull-right" rows="6" name="comment"
									id="comment">${person.comment}
									</textarea>
							</div>
						</div>
						<br>

						<!-- Submit button -->
						<div class="row">
							<div class="col-md-offset-5 col-md-2">
								<button id="saveChangesButton" type="submit" disabled='true'
									class="btn btn-primary">Guardar cambios</button>
							</div>
						</div>
					</form:form>
				</div>


		<!-- ---------------------- Second section: Aptitudes information -------------------- -->
				<div id="section2" class="tab-pane ${section2State}">
					<h3>
						Aptitudes <br> <small>Virtudes destacadas de esta
							persona</small>
					</h3>
					<hr>
					<div class="row">
						<div class="col-md-offset-7 col-md-2">
							<button type="button" class="btn btn-success" data-toggle="modal"
								data-target="#aptitudeCreate">
								<span class="glyphicon glyphicon-plus"></span> Crear aptitud
							</button>
						</div>
						<div class="col-md-offset-1 col-md-2">
							<form action="/persons/${person.id}/aptitude/delete" method="post">
								<input type="hidden" name="ids" value="0" id="idsToDelete" class="form-control">
								<button id="deleteAptitude" type="submit" name="ids" value="0"
									class="btn btn-danger pull-right" >
									<span class="glyphicon glyphicon-remove"></span> Borrar
									seleccionados
								</button>
							</form>
						</div>
					</div>

					<br>
					<table id="aptitudeTable" class="table table-bordered" data-toggle="table">
						<thead>
							<tr>
								<th>Id</th>
								<th>Nombre</th>
								<th>Tipo</th>
								<th>Valoración</th>
								<th>Comentario</th>
								<th data-field="state" data-checkbox="true"></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="aptitude" items="${aptitudes}">
								<tr>
									<td class="col-md-1 text-info">${aptitude.id}</td>
									<td class="col-md-2">${aptitude.name}</td>
									<td class="col-md-2">${aptitude.type}</td>
									<td class="col-md-1">${aptitude.value}</td>
									<td class="col-md-5">${aptitude.comment}</td>
									<td class="col-md-1"></td>
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
		<div class="modal fade" id="aptitudeCreate" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Creación de nueva aptitud</h4>
					</div>
					<div class="modal-body">

						<form:form class="form" method="post"
							action='/persons/${person.id}/aptitude' modelAttribute="aptitude"
							role="form" data-toggle="validator">

							<!-- First row -->
							<div class="form-group">
								<div class="row">
									<div class="form-group col-md-7">
										<label for="inputName" class="control-label">Nombre</label> <input
											type="text" class="form-control" name="name" id="name"
											required>
									</div>

									<div class="form-group col-md-3">
										<label for="selectValue">Tipo</label> <select
											class="form-control" name="type" id="type">
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

									<div class="form-group col-md-2">
										<label for="selectValue">Valoración</label> <select
											class="form-control" name="value" id="value">
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
							</div>

							<!-- Second row -->
							<div class="form-group">
								<div class="row">
									<div class="col-md-12">
										<label>Cometario (max 50 caracteres)</label>
										<textarea class="form-control" rows="1" id="comment"
											name="comment" placeholder="Detalles a destacar..."
											maxlength="50"></textarea>
									</div>
								</div>
							</div>

							<!-- Submit button -->
							<div class="form-group">
								<button type="submit" class="btn btn-primary center-block">Añadir</button>
							</div>
						</form:form>
					</div>
				</div>

			</div>
		</div>

	</div>


	<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script src="/webjars/bootstrap-validator/0.11.5/dist/validator.js"></script>
	<script src="/webjars/bootstrap-validator/0.11.5/dist/validator.min.js"></script>
	<script src="/webjars/bootstrap-table/1.11.0/src/bootstrap-table.js"></script>
	<script src="/webjars/bootstrap-datepicker/1.6.1/js/bootstrap-datepicker.min.js"></script>
	<script src="/webjars/bootstrap-datepicker/1.6.1/locales/bootstrap-datepicker.es.min.js"></script>
	
	<!-- ------------------------------ Datapicker language: es  ------------------------------ -->
	<script>
		$(document).ready(function() {
			$('.datepicker').datepicker({
				language : 'es'
			});
		})
	</script>

	<!-- --------- Able the save button for Person updates when its data has changed  --------- -->
	<script type="text/javascript">
		var somethingChanged = false;
		$(document).ready(function() { 
		   $('#section1 input').change(function() { 
		        document.getElementById("saveChangesButton").disabled = false;
		        
		   }); 
		   $('#section1 textarea').change(function() { 
		        document.getElementById("saveChangesButton").disabled = false;
		        
		   }); 
		});
	</script>
	
	<script type='text/javascript'>
		$(function() {            
			$('#deleteAptitude').click(function () {
				var json = JSON.parse(JSON.stringify($('#aptitudeTable').bootstrapTable('getSelections')));
				var idApts = "";
				
				for (i = 0; i < json.length; i++) {
					idApts += json[i]['0'] + "-";
				}
				
				var idApts = idApts.substring(0, (idApts.length-1));
				
				alert(idApts);
				document.getElementById("idsToDelete").value = idApts;		
            });
		});
	</script>

</body>

</html>