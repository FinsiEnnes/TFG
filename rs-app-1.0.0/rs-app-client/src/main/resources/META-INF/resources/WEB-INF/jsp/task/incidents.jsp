
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

/* Left padding for subtitles */
.subtitle {
    padding-left: 12px;
}

/* poner color a los hr */
hr { border-color: grey }

/* sangria de los small */
small {
    margin-left: 15px;
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
				<li><a href="/projects/${idProject}"><font size="3">Proyecto</font></a></li>
				<li class="active"><a href="#"><font size="3">Tarea</font></a></li>
				<li><a href="/projects/${idProject}/milestones"><font size="3">Hito</font></a></li>
				<li><a href="/persons"><font size="3">Personas</font></a></li>
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
									<a href="/projects/${idProject}/phases/${idPhase}/tasks/${idTask}">
										Información 
										<span class="glyphicon glyphicon-info-sign pull-right"></span>
									</a>
								</li>
								</ul>
								
								<ul>
								<li>
									<a href="/projects/${idProject}/phases/${idPhase}/tasks/${idTask}/predecessors">
										Predecesoras
										<span class="glyphicon glyphicon-indent-right pull-right"></span>
									</a>
								</li>
								</ul>
						</ul>

						<br>
						<ul>
							<li class="menu-head"><b>ASIGNACIONES</b></li>
							<li class="menu">
								<ul>
								<li>
									<a href="/projects/${idProject}/phases/${idPhase}/tasks/${idTask}/persons">
										Perfiles y personas 
										<span class="glyphicon glyphicon-user pull-right"></span>
									</a>
								</li>
								</ul>
								
								<ul>
								<li>
									<a href="/projects/${idProject}/phases/${idPhase}/tasks/${idTask}/workloads">
										Carga de trabajo 
										<span class="glyphicon glyphicon-time pull-right"></span>
									</a>
								</li>
								</ul>
								
								<ul>
								<li>
									<a href="/projects/${idProject}/phases/${idPhase}/tasks/${idTask}/materials">
										Materiales
										<span class="glyphicon glyphicon-wrench pull-right"></span>
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
									<a href="#" class="active">Incidencias
										<span class="glyphicon glyphicon-fire pull-right"></span>
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
					Listado de incidencias<br> 
					<small>
						Problemas relacionadas con esta tarea que ocasionan pérdidas y retrasos en el proyecto
					</small>
				</h3>
				<hr width="110%">
				<br>
				
				<div class="row">
					<div class="form-group col-md-2">
						<h4 class="subtitle">
							Incidencias<br> 
						</h4>
					</div>
					<div class="col-md-offset-7 col-md-2">
						<button id="addPlanMaterialButton" type="button" class="btn btn-success pull-right"
							data-toggle="modal" data-target="#addTaskIncident">
							<span class="glyphicon glyphicon-plus"></span> 
							Nueva incidencia
						</button>
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-md-offset-1 col-md-10">
					<table id="incidentTable" class="table table-bordered"	
					data-toggle="table" data-height="162" data-click-to-select="true" data-single-select="true">
						<thead>
							<tr>
								<th class="col-md-1" data-field="state" data-checkbox="true"></th>
								<th class="col-md-1">ID</th>
								<th class="col-md-4">Motivo</th>
								<th class="col-md-2">Daño</th>
								<th class="col-md-2">Pérdidas</th>
								<th class="col-md-2">Acción</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="incident" items="${incidents}">
							<tr>
								<td></td>
								<td class="text-info">${incident.id}</td>
								<td>${incident.reason}</td>
								<td>${incident.damage}</td>
								<td>${incident.loss}</td>
								<td>
									<table>
										<tr>
											<td>
												<button type="button" class="btn btn-primary btn-xs center-block"
													data-toggle="modal" data-target="#updateTaskIncident"
													data-id="${incident.id}" data-reason="${incident.reason}"
													data-idincident="${incident.idIncident}"
													data-damage="${incident.damage}" 
													data-iddamage="${incident.idDamage}"
												 	data-loss="${incident.loss}"
													data-result="${incident.result}">
													<span class="glyphicon glyphicon-edit"></span>
												</button>
											</td>
											<td>
												<button id="deleteMaterial" type="button"
													class="btn btn-danger btn-xs center-block"
													data-toggle="modal" data-target="#confirmDeleteIncident"
													data-id="${incident.id}">
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
				
				<div class="row">
					<div class="form-group col-md-6">
						<h4 class="subtitle">
							Resultados ocasionados por la incidencia seleccionada<br> 
						</h4>
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-md-offset-3 col-md-6">
						<textarea class="form-control" rows="6" id="taskIncidentResult">
						</textarea>
					</div>
				</div>
			</div>
		</div>
		
		
	<!-- Modal: New Task incident
	===================================================================================================== -->
		<div class="modal fade modal" id="addTaskIncident" role="dialog">
			<div class="modal-dialog">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Creación de nueva incidencia</h4>
					</div>

					<div class="modal-body">
												
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Datos de incidencia<br> 
									<small>
										Establece el motivo de la nueva incidencia, el grado de daño o las 
									</small>
									<small>
										pérdidas ocasionadas. Explica opcionalmente el resultado de la incidencia
									</small>
									<small>
										 sobre el proyecto.
									</small>
								</h4>
							</div>
						</div>
					
						<form:form id="incidentCreationForm" class="form-horizontal" method="post" 
						modelAttribute="incident" data-toggle="validator"
						role="form" action='/projects/${idProject}/phases/${idPhase}/tasks/${idTask}/incidents'>

							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-2 control-label">Motivo</label>
									<div class="form-group col-md-6">
										<input class="form-control" name="reason" id="reason"
										type="text" required>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-2 control-label">Daño</label>
									<input type="hidden" name="idDamage" id="idDamage" value="IRLV">
									<div class="form-group col-md-3">
										<select class="form-control" name="damage" id="damage">
											<option id="IRLV">Irrelevante</option>
											<option id="MENR">Menor</option>
											<option id="IMPT">Importante</option>
											<option id="GRAV">Grave</option>
											<option id="MGRV">Muy grave</option>
										</select>
									</div>
								</div>
							</div>
											
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-2 control-label">
										Pérdidas
									</label>
									<div class="form-group col-md-3  has-feedback">
										<input type="text" class="form-control" pattern="^[1-9]\d*$" 
										maxlength="5" placeholder="1-99999" name="loss" required>
										<span class="glyphicon form-control-feedback"></span>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-2 control-label">Resultados</label>
									<div class="form-group col-md-6">
										<textarea class="form-control" rows="4" name="result">
										</textarea>
									</div>
								</div>
							</div>
							

							<div class="form-group">
								<div class="row">
								<button id="assignPlanMaterialButton" type="submit"
								 class="btn btn-primary center-block">
									Crear
								</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		
		
	<!-- Modal: Update Task incident
	===================================================================================================== -->
		<div class="modal fade modal" id="updateTaskIncident" role="dialog">
			<div class="modal-dialog">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualización de incidencia</h4>
					</div>

					<div class="modal-body">
												
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Datos de incidencia<br> 
									<small>
										Cambia los datos relativos a la incidencia seleccionada 
									</small>
								</h4>
							</div>
						</div>
					
						<form:form id="incidentUpdateForm" class="form-horizontal" method="post" 
						modelAttribute="incident" data-toggle="validator" role="form" action=''>

							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-2 control-label">Motivo</label>
									<input type="hidden" name="id" id="idUpdate">
									<input type="hidden" name="idIncident" id="idIncidentUpdate">
									<div class="form-group col-md-6">
										<input class="form-control" name="reason" id="reasonUpdate"
										type="text" required>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-2 control-label">Daño</label>
									<input type="hidden" name="idDamage" id="idDamageUpdate" value="IRLV">
									<div class="form-group col-md-3">
										<select class="form-control" name="damage" id="damageUpdate">
											<option id="IRLV">Irrelevante</option>
											<option id="MENR">Menor</option>
											<option id="IMPT">Importante</option>
											<option id="GRAV">Grave</option>
											<option id="MGRV">Muy grave</option>
										</select>
									</div>
								</div>
							</div>
											
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-2 control-label">
										Pérdidas
									</label>
									<div class="form-group col-md-3  has-feedback">
										<input type="text" class="form-control" pattern="^[1-9]\d*$" 
										maxlength="5" placeholder="1-99999" name="loss" id="lossUpdate" required>
										<span class="glyphicon form-control-feedback"></span>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-2 control-label">Resultados</label>
									<div class="form-group col-md-6">
										<textarea class="form-control" rows="4" name="result" id="resultUpdate">
										</textarea>
									</div>
								</div>
							</div>
							

							<div class="form-group">
								<div class="row">
								<button id="updateTaskIncidentButton" type="submit"
								 class="btn btn-primary center-block" disabled>
									Cambiar
								</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		
		
	<!-- Modal: Confirmation of delete Incident
	===================================================================================================== -->
		<div class="modal fade" id="confirmDeleteIncident" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Borrado de incidencia</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<p align="center">
									¿Seguro que desea eliminar esta incidencia de la tarea?
								</p>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="row center-block">
							<div class="col-md-offset-3 col-md-3">
								<form id="confirmDeleteIncidentButton" action='' method="post">
									<button type="submit" class="btn btn-danger btn-block">Si</button>
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
	
	
	<!-- If the select damage change the idDamage must change
    ================================================== -->	
	<script type="text/javascript">
		$("#damage").change(function() {
			  var id = $(this).children(":selected").attr("id");
			  document.getElementById("idDamage").value = id;
			  
		});
	</script>
	
	<script type="text/javascript">
		$("#damageUpdate").change(function() {
			  var id = $(this).children(":selected").attr("id");
			  document.getElementById("idDamageUpdate").value = id;
			  
		});
	</script>
	
	
	<!-- Dynamic load of the material description in the textarea
	===================================================================================================== -->
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#incidentTable').change(function() { 
			   
			   // Get info of the table
			   var json = JSON.parse(JSON.stringify($('#incidentTable').bootstrapTable('getSelections')));
			   var size = json.length;
			   
			   
			   // If a option is selected then we can add a Person as manager
			   if (size > 0) {
				   var idTaskIncident = json['0']['1'];
				   var result = "";
				   				   
				   // Find the description of this Material
				   for (i = 0; i < ${results}.length; i++) {
    					if (${results}[i].id == idTaskIncident) {
    						result = ${results}[i].result;
    					}
					}
				   document.getElementById("taskIncidentResult").value = result;
			   } 
	        
		   }); 
		});
	</script>
	
	
	<!-- Data transfer to the modal updateTaskIncident
	===================================================================================================== -->
	<script type='text/javascript'>
		$(function() {
			$('#updateTaskIncident').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var id = button.data('id');
				var idIncident = button.data('idincident');
				var reason = button.data('reason');
				var idDamage = button.data('iddamage');
				var damage = button.data('damage');
				var loss = button.data('loss');
				var result = button.data('result');
				  
				// Create the url
				var url = "/projects/" + ${idProject} + "/phases/" + ${idPhase} + "/tasks/" + 
						  ${idTask} + "/incidents/" + id + "/update";
						
				// Set the values at the modal components
				document.getElementById('incidentUpdateForm').action = url;
				document.getElementById('idUpdate').value = id;
				document.getElementById('idIncidentUpdate').value = idIncident;
				document.getElementById('reasonUpdate').value = reason;
				document.getElementById('damageUpdate').value = damage;
				document.getElementById('idDamageUpdate').value = idDamage;
				document.getElementById('lossUpdate').value = loss;
				document.getElementById('resultUpdate').value = result;

			})
		});
	</script>
	
	
	<!-- Able the edit button when the data change
    ================================================== -->	
	<script type="text/javascript">
		$("#incidentUpdateForm").change(function() {
			  document.getElementById("updateTaskIncidentButton").disabled = false;
			  
		});
	</script>
	
	
	<!-- Data transfer to the modal confirmDeleteIncident
	===================================================================================================== -->
	<script type='text/javascript'>
		$(function() {
			$('#confirmDeleteIncident').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var id = button.data('id');
				  
				// Create the url
				var url =  "/projects/" + ${idProject} + "/phases/" + ${idPhase} + "/tasks/" + 
				  ${idTask} + "/incidents/" + id + "/delete";
						
				// Set the values at the modal components
				$('#confirmDeleteIncidentButton').get(0).setAttribute('action', url);
			})
		});
	</script>
	

</body>
</html>
