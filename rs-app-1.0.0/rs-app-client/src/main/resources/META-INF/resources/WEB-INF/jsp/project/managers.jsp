
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<title>Project Managers</title>

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

/* Neccesary to adapt a table in the modal */
.table-responsive {
    max-height:150px;
    padding: 0px;
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
				<li><a href="/project/${idProject}/planning"><font size="3">Planificación</font></a></li>
				<li class="active"><a href="#"><font size="3">Proyecto</font></a></li>
				<li><a href="/projects/${idProject}/phases/${idPhase}/tasks/${idTask}"><font size="3">Tarea</font></a></li>
				<li><a href="/projects/${idProject}/milestones"><font size="3">Hito</font></a></li>
				<li><a href="/projects/${idProject}/persons"><font size="3">Personas</font></a></li>
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
									<a href="/projects/${idProject}"> Información 
										<span class="glyphicon glyphicon-info-sign pull-right"></span>
									</a>
								</li>
								<li>
									<a href="/projects/${idProject}/states"> Estado 
										<span class="glyphicon glyphicon-check pull-right"></span>
									</a>
								</li>
								<li>
									<a href="/projects/${idProject}/statics"> Estadísticas
										<span class="glyphicon glyphicon-stats pull-right"></span>
									</a>
								</li>
								<li>
									<a href="/projects/${idProject}/customer">Cliente
										<span class="glyphicon glyphicon-user pull-right"></span>
									</a>
								</li>
								</ul>
							</li>
						</ul>

						<br>
						<ul>
							<li class="menu-head"><b>DIRECCIÓN</b></li>
							<li class="menu">
								<ul>
								<li>
									<a href="#" class="active">Jefes de proyecto 
										<span class="glyphicon glyphicon-briefcase pull-right"></span>
									</a>
								</li>
								</ul>
							</li>
						</ul>
						
						<br>
						<ul>	
							<li class="menu-head"><b>OTROS</b></li>
							<li class="menu">
								<ul>
								<li>
									<a href="/projects/${idProject}/calendar">Calendario
										<span class="glyphicon glyphicon-calendar pull-right"></span>
									</a>
								</li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
			</div>

			<!-- Interface Project information
    		================================================== -->
			<div class="col-md-9">
				<h3>
					Jefes de proyecto <br> 
					<small>
						Listado de los directores encargados de la gestión del proyecto
					</small>
				</h3>
				<hr width="110%">
				<br>

				<div class="row">
					<div class="col-md-offset-10 col-md-2">
						<button type="button" class="btn btn-success pull-right"
							data-toggle="modal" data-target="#formManagerCreation">
							<span class="glyphicon glyphicon-plus"></span> 
							Nuevo jefe de proyecto
						</button>
					</div>
				</div>

				<br>
				<div class="row">
					<div class="form-group col-md-offset-1 col-md-11">
					<table id="managers_table" class="table table-bordered"	data-toggle="table" data-height="280">
						<thead>
							<tr>
								<th class="col-md-3">Nombre</th>
								<th class="col-md-3">Categoría profesional</th>
								<th class="col-md-2">Nivel</th>
								<th class="col-md-3">Período como director</th>
								<th class="col-md-1">Acción</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="manager" items="${managers}">
							<tr>
								<td>${manager.nameManager}</td>
								<td>${manager.profCatgManager}</td>
								<td>${manager.levelManager}</td>
								<td>
									<table>
										<tr>
											<td class="col-md-6">${manager.ini}</td>
											<td class="col-md-6">${manager.end}</td>
										</tr>
									</table>
								</td>
								<td>
									<table>
										<tr>
											<td>
												<button id="editManager" type="button"
													class="btn btn-primary btn-xs center-block"
													data-toggle="modal" data-target="#updateManager"
													data-id="${manager.id}" data-name="${manager.nameManager}"
													data-idproject="${idProject}" data-ini="${manager.ini}" 
													data-end="${manager.end}"  data-idhp="${manager.idHistoryPerson}">
													<span class="glyphicon glyphicon-edit"></span>
												</button>
											</td>
											<td>
												<button id="deleteManager" type="button"
													class="btn btn-danger btn-xs center-block"
													data-toggle="modal" data-target="#confirmDelete"
													data-id="${manager.id}" data-name="${manager.nameManager}"
													data-idproject="${idProject}">
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
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- Modal: Creation of a new project manager
    	================================================== -->
		<div class="modal fade modal" id="formManagerCreation" role="dialog">
			<div class="modal-dialog modal-lg">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Creación de nuevo Jefe de Proyecto</h4>
					</div>

					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Selección de persona<br> 
									<small>
										Asigna a una persona para el cargo de director de proyecto
									</small>
								</h4>
							</div>
						</div>
						
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
							<div class="panel panel-default">
                        	<div class="panel-body table-responsive">
							<table id="personsTable" class="table table-bordered table-responsive" 
								   data-toggle="table" data-click-to-select="true" data-single-select="true">
								<thead>
									<tr>
										<th class="col-md-1" data-field="state" data-checkbox="true"></th>
										<th class="col-md-1">ID</th>
										<th class="col-md-4">Nombre</th>
										<th class="col-md-3">Categoría profesional</th>
										<th class="col-md-3">Nivel</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="persons" items="${persons}">
									<tr>
										<td></td>
										<td class="text-info">${persons.id}</td>
										<td>${persons.namePerson}</td>
										<td>${persons.nameProfCatg}</td>
										<td>${persons.levelProfCatg}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
							</div>
							</div>
							</div>
						</div>
						
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Período como director<br> 
									<small>
										Período en el que esta persona ejercerá este cargo. No definir la fecha Fin
										supone un período indefinido.
									</small>
								</h4>
							</div>
						</div>
					
						<form:form class="form" method="post" action='/projects/${idProject}/managers'
							modelAttribute="projectMgmt" role="form" data-toggle="validator">

							<!-- First row -->
							<div class="form-group">
								<div class="row">
									<input type="hidden" name="idHistoryPerson" id="idHistoryPerson" required>
								
									<div class="form-group col-md-offset-3 col-md-3">
										<label for="inputName" class="control-label">Inicio</label> 
										<input type="text" class="form-control datepicker"
									   		   data-format="dd/MM/yyyy" name="ini" id="ini"
									   		   placeholder="dd/mm/aaaa" required>
									</div>

									<div class="form-group col-md-3">
										<label for="inputSurname1" class="control-label">Fin </label> 
										<input type="text" class="form-control datepicker"
									   		   data-format="dd/MM/yyyy" name="end" id="end"
									   		   placeholder="dd/mm/aaaa">
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
								<button id="addButton" type="submit" class="btn btn-primary center-block" disabled >
									Añadir
								</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>

			</div>
		</div>
		
		
		<!-- Modal: Update manager
    	================================================== -->
		<div class="modal fade" id="updateManager" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualización de director</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Período como director<br> 
									<small>
										Cambia las fechas durante las que esta persona ha dirigido el proyecto
									</small>
								</h4>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-12">
								<form:form class="form" method="post" id="updateManagerForm"
									action='url' modelAttribute="projectMgmt" role="form"
									data-toggle="validator">

									<!-- First row -->
									<div class="form-group">
										<div class="row">
											<input type="hidden" name="id" id="idUpdate" required>
												
											<input type="hidden" name="idHistoryPerson"
												id="idHistoryPersonUpdate" required>

											<div class="form-group col-md-offset-3 col-md-3">
												<label for="inputName" class="control-label">Inicio</label>
												<input type="text" class="form-control datepicker"
													data-format="dd/MM/yyyy" name="ini" id="iniUpdate"
													placeholder="dd/mm/aaaa" required>
											</div>

											<div class="form-group col-md-3">
												<label for="inputSurname1" class="control-label">Fin
												</label> <input type="text" class="form-control datepicker"
													data-format="dd/MM/yyyy" name="end" id="endUpdate"
													placeholder="dd/mm/aaaa">
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="row">
											<button id="addButton" type="submit"
												class="btn btn-primary center-block">
												Guardar cambios
											</button>
										</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- Modal: Confirmation of project manager delete
    	================================================== -->
		<div class="modal fade" id="confirmDelete" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Confirmación de borrado</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<p id="confirmMsg" align="center"></p>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="row center-block">
							<div class="col-md-offset-3 col-md-3">
								<form id="confirmDeleteButton" name="confirmDeleteButton" action='' method="post">
									<button type="submit" class="btn btn-primary btn-block">Si</button>
								</form>
							</div>
							<div class="col-md-3">
								<button type="button" class="btn btn-default btn-block"
									data-dismiss="modal">No</button>
							</div>
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
	
	
	<!-- Enable in the modal, the add button when the 
		 user selects a Person to add. 
    ================================================== -->	
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#personsTable').change(function() { 
			   
			   // Get info of the table
			   var json = JSON.parse(JSON.stringify($('#personsTable').bootstrapTable('getSelections')));
			   var size = json.length;
			   
			   // If a option is selected then we can add a Person as manager
			   if (size > 0) {
				   var idPerson = json['0']['1'];
				   document.getElementById("idHistoryPerson").value = idPerson;
				   document.getElementById("addButton").disabled = false;
			   } 
			   else {
				   document.getElementById("addButton").disabled = true;
			   }		        
		   }); 
		});
	</script>
	
	
	<!-- Data transfer to the modal updateManager
    ================================================== -->	
	<script type='text/javascript'>
		$(function() {
			$('#updateManager').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget)
				var b = event.relatedTarget;
				
				// Only set the modal data if the event comes from the main edit botton
				if (b != null)
			    {		     	
			        // Extract info from data attributes
					var id = button.data('id');
					var idproject = button.data('idproject');
					var idhp = button.data('idhp');
					var ini = button.data('ini');
					var end = button.data('end');
					
					// Create the url 
					var url = "/projects/" + idproject + "/managers/" + id + "/update";
							
					// Set the values at the modal components
					document.getElementById('updateManagerForm').action = url;
					document.getElementById('idUpdate').value = id;
					document.getElementById('idHistoryPersonUpdate').value = idhp;
					document.getElementById('iniUpdate').value = ini;
					document.getElementById('endUpdate').value = end;
			    } 

			})
		});
	</script>
	
	
	<!-- Data transfer to the modal confirmDelete
    ================================================== -->	
	<script type='text/javascript'>
		$(function() {
			$('#confirmDelete').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var modal = $(this)
				var id = button.data('id');
				var idproject = button.data('idproject');
				var name = button.data('name');
				var url = "/projects/" + idproject + "/managers/" + id + "/delete";
				var msg = "¿Seguro que desea eliminar el cargo de director a " + name + "?";
						
				// Set the values at the modal components
				document.getElementById('confirmMsg').innerHTML = msg;
				$('#confirmDeleteButton').get(0).setAttribute('action', url);
			})
		});
	</script>

</body>
</html>
