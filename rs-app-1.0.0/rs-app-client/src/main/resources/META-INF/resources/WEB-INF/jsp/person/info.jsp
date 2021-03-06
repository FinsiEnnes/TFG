
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<title>Task Information</title>

<!-- Bootstrap core CSS
========================================================== -->
<link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap Datepicker
========================================================== -->
<link href="/webjars/bootstrap-datepicker/1.6.1/css/bootstrap-datepicker.min.css" rel="stylesheet">

<!-- Bootstrap Table
========================================================== -->
<link href="/webjars/bootstrap-table/1.11.0/src/bootstrap-table.css" rel="stylesheet">


<!-- Custom colors for the navigation and subsection bar 
========================================================== -->
<link href="/css/navbarColors.css" rel="stylesheet">
<link href="/css/subsectionColors.css" rel="stylesheet">

<style>
body {
	padding-top: 50px;
}

.well {
	margin-left: 0px;
}

.nopadding {
	padding: 0 !important;
	margin: 0 !important;
}

#sidebar.affix {
	position: fixed;
	top: 70px;
	width: 300px;
}

.note {
    font-weight: normal !important;
    padding-top: 8px;
}

/* poner color a los hr */
hr { border-color: grey }

/* sangria de los small */
small {
    margin-left: 15px;
}

/* Necessary to put the icons inside the inputs */
.right-inner-addon {
	position: relative;
}

.right-inner-addon input {
	padding-right: 30px;
}

.right-inner-addon i {
	position: absolute;
	right: 0px;
	padding: 10px 12px;
}


</style>
</head>

<body>
	<!-- Main navigation bar 
    ================================================== -->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	
		<!-- ======== HEADER ======== -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
			</button>
			<a class="navbar-brand" href="#"><span class="glyphicon glyphicon-home"></span></a>
		</div>
		
		<!-- ========= MENU ========= -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="/project/${idProject}/planning"><font size="3">Planificaci�n</font></a></li>
				<li><a href="/projects/${idProject}"><font size="3">Proyecto</font></a></li>
				<li><a href="/projects/${idProject}/phases/${idPhase}/tasks/${idTask}"><font size="3">Tarea</font></a></li>
				<li><a href="/projects/${idProject}/milestones"><font size="3">Hito</font></a></li>
				<li class="active"><a href="#"><font size="3">Personas</font></a></li>
				<li><a href="/projects/${idProject}/materials"><font size="3">Materiales</font></a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</nav>
	
	
	<div class="container-fluid">
		<div class="row">
			
			<!-- Subsection navigation bar 
    		================================================== -->
			<div class="col-md-2 nopadding">
				<div class="sidebar-nav affix" style="height: 100%;">
					<div class="side-bar">
						<ul>
							<li class="menu-head"><b>GENERAL</b></li>
							<li class="menu">
								<ul>
								<li>
									<a href="/persons">
										Lista de personas
										<span class="glyphicon glyphicon-info-sign pull-right"></span>
									</a>
								</li>
								</ul>
								
								<ul>
								<li>
									<a href="#" class="active">
										Detalles persona
										<span class="glyphicon glyphicon-indent-right pull-right"></span>
									</a>
								</li>
								</ul>
						</ul>
					</div>
				</div>
			</div>

			<!-- Interface Project information
    		================================================== -->
			<div class="col-md-9">
			
				<!-- Person information
    			================================================== -->
				<h3>
					Informaci�n<br> 
					<small>
					Cambia la informaci�n b�sica de esta persona
					</small>
				</h3>
				<hr width="110%">
				<br>
				
				<form:form id="personForm" class="form-horizontal" method="post" 
						action="/persons/${person.id}/update" modelAttribute="person"
						role="form" data-toggle="validator">

						<!-- First row -->
						<div class="row">
							<label class="col-md-2 control-label">Nombre</label>
							<div class="form-group col-md-3">
								<input class="form-control" name="name" id="name" type="text"
									value="${person.name}" required>
							</div>

							<label class="col-md-2 control-label">DNI</label>
							<div class="form-group col-md-3">
								<input class="form-control" readonly name="nif" id="nif" type="text"
									value="${person.nif}" required>
							</div>
						</div>
						<br>
						
						<!-- Second row -->
						<div class="row">
							<label class="col-md-2 control-label">Apellido 1</label>
							<div class="form-group col-md-3">
								<input class="form-control" name="surname1" id="surname1"
									type="text" value="${person.surname1}" required>
							</div>

							<label class="col-md-2 control-label">Email</label>
							<div class="form-group col-md-3">
								<input class="form-control" name="email" id="email" type="text"
									value="${person.email}" required>
							</div>
						</div>
						<br>
						
						<!-- Third row -->
						<div class="row">
							<label class="col-md-2 control-label">Apellido 2</label>
							<div class="form-group col-md-3">
								<input class="form-control" name="surname2" id="surname2"
									type="text" value="${person.surname2}" required>
							</div>

							<label class="col-md-2 control-label">Fecha de alta</label>
							<div class="form-group col-md-3 date">
								<input type="text" class="form-control datepicker"
									data-format="dd/MM/yyyy" name="hiredate" id="hiredate"
									placeholder="dd/mm/aaaa" required value="${person.hiredate}">
							</div>
						</div>
						<br>

						<!-- Fourth row -->
						<div class="row">
							<label class="col-md-2 control-label" for="comment">Comentarios</label>
							<div class="form-group col-md-8" style="width: 63.8%">
								<textarea class="form-control pull-right" rows="6" name="comment"
									id="comment">${person.comment}
									</textarea>
							</div>
						</div>
						<br>

						<!-- Submit button -->
						<div class="row">
							<div class="col-md-offset-5 col-md-2">
								<button id="saveChangesButton" type="submit" disabled
									class="btn btn-primary">Guardar cambios</button>
							</div>
						</div>
					</form:form>
					<br><br>
					<!-- Aptitude information
    				================================================== -->
					<h3>
						Trayectoria profesional <br> <small>Cargos ocupados a lo largo de la carrera laboral</small>
					</h3>
					<hr>
					<div class="row">
						<div class="col-md-offset-7 col-md-2">
							<button type="button" class="btn btn-success" data-toggle="modal"
								data-target="#aptitudeCreate">
								<span class="glyphicon glyphicon-plus"></span> Nuevo cargo
							</button>
						</div>
						<div class="col-md-offset-1 col-md-2">
							<form id="deleteAptitude" action="javascript:void(0);" method="post">
								<input type="hidden" name="ids" value="0" id="idsToDelete" class="form-control">
								<button type="submit" class="btn btn-danger pull-right" >
									<span class="glyphicon glyphicon-remove"></span> Borrar
									seleccionados
								</button>
							</form>
						</div>
					</div>

					<br>
					<table id="historypersonTable" class="table table-bordered" data-toggle="table">
						<thead>
							<tr>
								<th>Id</th>
								<th>Cargo</th>
								<th>Nivel</th>
								<th>Inicio</th>
								<th>Fin</th>
								<th>Salario</th>
								<th>Salario extra</th>
								<th>Acci�n</th>
								<th data-field="state" data-checkbox="true"></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="hp" items="${hpersons}">
								<tr>
									<td class="col-md-1 text-info">${hp.id}</td>
									<td class="col-md-2">${hp.nameProfCatg}</td>
									<td class="col-md-2">${hp.levelProfCatg}</td>
									<td class="col-md-1">${hp.ini}</td>
									<td class="col-md-4">${hp.end}</td>
									<td class="col-md-1">${hp.sal}</td>
									<td class="col-md-4">${hp.salExtra}</td>
									<td>
										<button id="updateAptitude" type="button"
											class="btn btn-primary btn-xs center-block"
											 data-toggle="modal" data-target="#aptitudeUpdate" 
											 data-idapt="${hp.id}">
											<span class="glyphicon glyphicon-edit"></span>
										</button>
									</td>
									<td class="col-md-1"></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<br><br>
					<!-- Aptitude information
    				================================================== -->
					<h3>
						Aptitudes <br> <small>Competencias destacadas de esta
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
							<form id="deleteAptitude" action="javascript:void(0);" method="post">
								<input type="hidden" name="ids" value="0" id="idsToDelete" class="form-control">
								<button type="submit" class="btn btn-danger pull-right" >
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
								<th>Valoraci�n</th>
								<th>Comentario</th>
								<th>Acci�n</th>
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
									<td class="col-md-4">${aptitude.comment}</td>
									<td>
										<button id="updateAptitude" type="button"
											class="btn btn-primary btn-xs center-block"
											 data-toggle="modal" data-target="#aptitudeUpdate" 
											 data-idapt="${aptitude.id}"
											 data-nameapt="${aptitude.name}"
											 data-typeapt="${aptitude.type}"
											 data-valueapt="${aptitude.value}"
											 data-commentapt="${aptitude.comment}">
											<span class="glyphicon glyphicon-edit"></span>
										</button>
									</td>
									<td class="col-md-1"></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<br><br>
					
					<!-- Timeoff information
    				================================================== -->
    				<h3>
						Bajas <br> <small>Detalles de las bajas laborales</small>
					</h3>
					<hr>
					<div class="row">
						<div class="col-md-offset-7 col-md-2">
							<button type="button" class="btn btn-success" data-toggle="modal"
								data-target="#timeoffCreate">
								<span class="glyphicon glyphicon-plus"></span> Crear baja
							</button>
						</div>
						<div class="col-md-offset-1 col-md-2">
							<form id="deleteTimeOff" action="javascript:void(0);" method="post">
								<input type="hidden" name="ids" value="0" id="idsTimeOffToDelete" class="form-control">
								<button type="submit" class="btn btn-danger pull-right" >
									<span class="glyphicon glyphicon-remove"></span> Borrar
									seleccionados
								</button>
							</form>
						</div>
					</div>

					<br>
					<table id="timeoffTable" class="table table-bordered" data-toggle="table">
						<thead>
							<tr>
								<th>Id</th>
								<th>Inicio</th>
								<th>Fin</th>
								<th>Causa</th>
								<th>Acci�n</th>
								<th data-field="state" data-checkbox="true"></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="timeoff" items="${timeoffs}">
								<tr>
									<td class="col-md-1 text-info">${timeoff.id}</td>
									<td class="col-md-1">${timeoff.ini}</td>
									<td class="col-md-1">${timeoff.end}</td>
									<td class="col-md-7">${timeoff.reason}</td>
									<td class="col-md-1">
										<button id="updateTimeoff" type="button"
											class="btn btn-primary btn-xs center-block"
											 data-toggle="modal" data-target="#timeoffUpdate" 
											 data-idtime="${timeoff.id}"
											 data-initime="${timeoff.ini}"
											 data-endtime="${timeoff.end}"
											 data-reasontime="${timeoff.reason}">
											<span class="glyphicon glyphicon-edit"></span>
										</button>
									</td>
									<td class="col-md-1"></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>	
					
				<br><br>
			</div>
			
		<!-- ---------------------------- Modal: Aptitudes creation --------------------------- -->
		<div class="modal fade" id="aptitudeCreate" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Creaci�n de nueva aptitud</h4>
					</div>
					<div class="modal-body">

						<form:form id="submitAptitude" class="form" method="post"
							action='/projects/${idProject}/persons/${person.id}/aptitude' modelAttribute="aptitude"
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
										<label for="selectValue">Valoraci�n</label> <select
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
										<textarea class="form-control" rows="1"
											name="comment" placeholder="Detalles a destacar..."
											maxlength="50"></textarea>
									</div>
								</div>
							</div>

							<!-- Submit button -->
							<div class="form-group">
								<button type="submit" class="btn btn-primary center-block">A�adir</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- ----------------------------- Modal: Aptitudes update ---------------------------- -->
		<div class="modal fade" id="aptitudeUpdate" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualizaci�n de aptitud</h4>
					</div>
					<div class="modal-body">

						<form:form id="submitAptitude" class="form" method="post"
							action='/projects/${idProject}/persons/${person.id}/aptitude/update' modelAttribute="aptitude"
							role="form" data-toggle="validator">

							<!-- First row -->
							<div class="form-group">
								<div class="row">
									<input type="hidden" name="id" id="idAptToUpdate"
										class="form-control" value="0">
									<div class="form-group col-md-7">
										<label for="inputName" class="control-label">Nombre</label> <input
											type="text" class="form-control" name="name" id="nameApt"
											required>
									</div>

									<div class="form-group col-md-3">
										<label for="selectValue">Tipo</label> <select
											class="form-control" name="type" id="typeApt">
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
										<label for="selectValue">Valoraci�n</label> <select
											class="form-control" name="value" id="valueApt">
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
										<textarea class="form-control" rows="1" id="commentApt"
											name="comment" placeholder="Detalles a destacar..."
											maxlength="50"></textarea>
									</div>
								</div>
							</div>

							<!-- Submit button -->
							<div class="form-group">
								<button id="aptitudeSubmit" type="submit" class="btn btn-primary center-block">
									Guardar cambios
								</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- ----------------------------- Modal: TimeOff creation ---------------------------- -->
		<div class="modal fade" id="timeoffCreate" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Creaci�n de nueva baja</h4>
					</div>
					<div class="modal-body">
						<p><b>Importante</b>: Una vez establecida la fecha no podr� modificarse.</p>
						<br>
						<form:form id="submitAptitude" class="form" method="post"
							action='/projects/${idProject}/persons/${person.id}/timeoff' modelAttribute="timeoff"
							role="form" data-toggle="validator">

							<!-- First row -->
							<div class="form-group">
								<div class="row">
									<div class="form-group col-md-6">
										<label for="inputHiredate" class="control-label">Inicio
										</label> <input type="text" class="form-control datepicker"
											data-format="dd/MM/yyyy" name="ini" id="ini"
											placeholder="dd/mm/aaaa" required>
									</div>

									<div class="form-group col-md-6">
										<label for="inputHiredate" class="control-label">Fin
										</label> <input type="text" class="form-control datepicker"
											data-format="dd/MM/yyyy" name="end" id="end"
											placeholder="dd/mm/aaaa" required>
									</div>
								</div>
							</div>

							<!-- Second row -->
							<div class="form-group">
								<div class="row">
									<div class="col-md-12">
										<label>Causa (max 50 caracteres)</label>
										<textarea class="form-control" rows="1"
											name="reason" placeholder="Detalles a destacar..."
											maxlength="50"></textarea>
									</div>
								</div>
							</div>

							<!-- Submit button -->
							<div class="form-group">
								<button type="submit" class="btn btn-primary center-block">A�adir</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- ----------------------------- Modal: TimeOff update ---------------------------- -->
		<div class="modal fade" id="timeoffUpdate" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualizaci�n de baja</h4>
					</div>
					<div class="modal-body">

						<form:form id="submitAptitude" class="form" method="post"
							action='/projects/${idProject}/persons/${person.id}/timeoff/update' 
							modelAttribute="timeoff"
							role="form" data-toggle="validator">

							<!-- First row -->
							<div class="form-group">
								<div class="row">
									<input type="hidden" name="id" id="idTimeOffToUpdate"
										class="form-control" value="0">
									<div class="form-group col-md-6">
										<label for="inputHiredate" class="control-label">Inicio
										</label> <input type="text" class="form-control"
											data-format="dd/MM/yyyy" name="ini" id="iniTimeoff"
											readonly>
									</div>

									<div class="form-group col-md-6">
										<label for="inputHiredate" class="control-label">Fin
										</label> <input type="text" class="form-control"
											data-format="dd/MM/yyyy" name="end" id="endTimeoff"
											readonly>
									</div>
								</div>
							</div>

							<!-- Second row -->
							<div class="form-group">
								<div class="row">
									<div class="col-md-12">
										<label>Causa (max 50 caracteres)</label>
										<textarea class="form-control" rows="1"
											name="reason" id="reasonTimeoff" 
											placeholder="Detalles a destacar..." maxlength="50"></textarea>
									</div>
								</div>
							</div>

							<!-- Submit button -->
							<div class="form-group">
								<button type="submit" class="btn btn-primary center-block">Guardar cambios</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	
	<!-- Bootstrap Datapicker
    ================================================== -->
	<script src="/webjars/bootstrap-datepicker/1.6.1/js/bootstrap-datepicker.min.js"></script>
	<script	src="/webjars/bootstrap-datepicker/1.6.1/locales/bootstrap-datepicker.es.min.js"></script>
	
	<!-- Bootstrap Validator
    ================================================== -->
	<script src="/webjars/bootstrap-validator/0.11.5/dist/validator.js"></script>
	<script src="/webjars/bootstrap-validator/0.11.5/dist/validator.min.js"></script>
	
	<!-- Bootstrap Table
    ================================================== -->
	<script src="/webjars/bootstrap-table/1.11.0/src/bootstrap-table.js"></script>
	
	<!-- Bootstrap Datapicker initialation
    ================================================== -->	
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
		   $('#personForm').change(function() { 
		        document.getElementById("saveChangesButton").disabled = false;
		        
		   }); 
		});
	</script>
	
		
	<!-- --------- Pass the data of the selected Aptitude at the modal to update it  ---------- -->
	<script type='text/javascript'>
		$(function() {
			$('#aptitudeUpdate').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var id = button.data('idapt') 
				var name = button.data('nameapt')
				var type = button.data('typeapt')
				var value = button.data('valueapt')
				var comment = button.data('commentapt')

				// Set the values at the modal components
				document.getElementById('idAptToUpdate').value = id
				document.getElementById('nameApt').value = name
				document.getElementById('typeApt').value = type
				document.getElementById('valueApt').value = value
				document.getElementById('commentApt').value = comment
			})
		});
	</script>
	
	
	<!-- ----------- Get the selected aptitudes and send its id as the form param  ------------ -->
	<script type='text/javascript'>
		$(function() {            
			$('#deleteAptitude').click(function () {
				var json = JSON.parse(JSON.stringify($('#aptitudeTable').bootstrapTable('getSelections')));
				var size = json.length;
				
				if (size > 0) {
					var idApts = "";
					
					for (i = 0; i < json.length; i++) {
						idApts += json[i]['0'] + "-";
					}
					
					var idApts = idApts.substring(0, (idApts.length-1));
					
					document.getElementById("idsToDelete").value = idApts;
					document.getElementById("deleteAptitude").action = "/projects/${idProject}/persons/${person.id}/aptitude/delete"
				}
					
            });
		});
	</script>
		
	
	<!-- --------- Pass the data of the selected TimeOff at the modal to update it  ---------- -->
	<script type='text/javascript'>
		$(function() {
			$('#timeoffUpdate').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var id = button.data('idtime') 
				var ini = button.data('initime')
				var end = button.data('endtime')
				var reason = button.data('reasontime')

				// Set the values at the modal components
				document.getElementById('idTimeOffToUpdate').value = id
				document.getElementById('iniTimeoff').value = ini
				document.getElementById('endTimeoff').value = end
				document.getElementById('reasonTimeoff').value = reason
			})
		});
	</script>
	
	
	<!-- ----------- Get the selected timeOffs and send its id as the form param  ------------ -->
	<script type='text/javascript'>
		$(function() {            
			$('#deleteTimeOff').click(function () {
				var json = JSON.parse(JSON.stringify($('#timeoffTable').bootstrapTable('getSelections')));
				var size = json.length;

				if (size > 0) {
					var idTimeOffs = "";
					
					for (i = 0; i < json.length; i++) {
						idTimeOffs += json[i]['0'] + "-";
					}
					
					var idTimeOffs = idTimeOffs.substring(0, (idTimeOffs.length-1));
					alert(idTimeOffs);
					
					document.getElementById("idsTimeOffToDelete").value = idTimeOffs;
					document.getElementById("deleteTimeOff").action = "/projects/${idProject}/persons/${person.id}/timeoff/delete"
				}
					
            });
		});
	</script>


</body>
</html>
