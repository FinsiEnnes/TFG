
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<meta charset="UTF-8">
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

/* Chang the color of hr */
hr { border-color: grey }

/* sangria de los small */
small {
    margin-left: 15px;
}

/* Left padding for subtitles */
.subtitle {
    padding-left: 12px;
}

/* Neccesary to adapt a table in the modal */
.table-responsive {
    max-height:150px;
    padding: 0px;
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


.btn-circle {
  width: 20px;
  height:20px;
  text-align: center;
  padding: 2px 0;
  font-size: 12px;
  line-height: 1.428571429;
  border-radius: 15px;
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
				<li><a href="#"><font size="3">Hito</font></a></li>
				<li><a href="#"><font size="3">Personas</font></a></li>
				<li><a href="/materials"><font size="3">Materiales</font></a></li>
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
									<a href="#">Carga de trabajo 
										<span class="glyphicon glyphicon-time pull-right"></span>
									</a>
								</li>
								</ul>
								
								<ul>
								<li>
									<a href="#" class="active">
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
									<a href="/projects/${idProject}/phases/${idPhase}/tasks/${idTask}/incidents">
										Incidencias
										<span class="glyphicon glyphicon-fire pull-right"></span>
									</a>
								</li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
			</div>

			<!-- Interface Task information
    		================================================== -->
			<div class="col-md-9">
			
				<!-- Planned Materials assigned in the Task
    			================================================== -->
				<h3>
					Planificación - Material<br> 
					<small>
						Materiales previstos necesarios para realizar la tarea
					</small>
				</h3>
				<hr width="110%">
				<br>
						
				<div class="row">
					<div class="col-md-offset-10 col-md-1">
						<button id="addPlanMaterialButton" type="button" class="btn btn-success pull-right"
							data-toggle="modal" data-target="#assignMaterial" data-type="plan">
							<span class="glyphicon glyphicon-plus"></span> 
							Asignar material
						</button>
					</div>
				</div>
				
				<br>
				<div class="row">
					<div class="form-group col-md-offset-1 col-md-10">
					<table id="planMaterialTable" class="table table-bordered"	
					data-toggle="table" data-height="240">
						<thead>
							<tr>
								<th class="col-md-5">Nombre</th>
								<th class="col-md-1">Coste</th>
								<th class="col-md-2">Tipo</th>
								<th class="col-md-1">Unidades</th>
								<th class="col-md-1">Coste total</th>
								<th class="col-md-2">Acción</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="material" items="${assignedPlanMaterials}">
							<tr>
								<td>${material.name}</td>
								<td>${material.cost}</td>
								<td>${material.type}</td>
								<td>${material.unitsPlan}</td>
								<td>${material.costPlan}</td>
								<td>
									<table>
										<tr>
											<td>
												<button id="editPlanMaterial" type="button"
													class="btn btn-primary btn-xs center-block"
													data-toggle="modal" data-target="#updateAsignmtMaterial"
													data-id="${material.id}" data-units="${material.unitsPlan}"
													data-type="plan">
													<span class="glyphicon glyphicon-edit"></span>
												</button>
											</td>
											<td>
												<button id="deletePlanMaterial" type="button"
													class="btn btn-danger btn-xs center-block"
													data-toggle="modal" data-target="#confirmDeleteMaterial"
													data-id="${material.id}" data-name="${material.name}">
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
				
				
				<!-- Real Materials assigned in the Task
    			================================================== -->
    			<br>
				<h3>
					Ejecución - Material<br> 
					<small>
						Materiales finalmente utilizados para llevar a cabo la tarea
					</small>
				</h3>
				<hr width="110%">
				<br>
						
				<div class="row">
					<div class="col-md-offset-10 col-md-1">
						<button id="addPlanMaterialButton" type="button" class="btn btn-success pull-right"
							data-toggle="modal" data-target="#assignMaterial" data-type="real">
							<span class="glyphicon glyphicon-plus"></span> 
							Asignar material
						</button>
					</div>
				</div>
				
				<br>
				<div class="row">
					<div class="form-group col-md-offset-1 col-md-10">
					<table id="realMaterialTable" class="table table-bordered"	
					data-toggle="table" data-height="240">
						<thead>
							<tr>
								<th class="col-md-5">Nombre</th>
								<th class="col-md-1">Coste</th>
								<th class="col-md-2">Tipo</th>
								<th class="col-md-1">Unidades</th>
								<th class="col-md-1">Coste total</th>
								<th class="col-md-2">Acción</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="material" items="${assignedRealMaterials}">
							<tr>
								<td>${material.name}</td>
								<td>${material.cost}</td>
								<td>${material.type}</td>
								<td>${material.unitsReal}</td>
								<td>${material.costReal}</td>
								<td>
									<table>
										<tr>
											<td>
												<button id="editRealMaterial" type="button"
													class="btn btn-primary btn-xs center-block"
													data-toggle="modal" data-target="#updateAsignmtMaterial"
													data-id="${material.id}" data-units="${material.unitsReal}"
													data-type="real">
													<span class="glyphicon glyphicon-edit"></span>
												</button>
											</td>
											<td>
												<button id="deleteRealMaterial" type="button"
													class="btn btn-danger btn-xs center-block"
													data-toggle="modal" data-target="#confirmDeleteMaterial"
													data-id="${material.id}" data-name="${material.name}">
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
		<br><br>
		
		
	<!-- Modal: Assignation of a new Material
	===================================================================================================== -->
		<div class="modal fade modal" id="assignMaterial" role="dialog">
			<div class="modal-dialog modal-lg">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Asignación de un nuevo material</h4>
					</div>

					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Selección de material<br> 
									<small>
										Asigna un material previsto y necesario para poder realizar la tarea
									</small>
								</h4>
							</div>
						</div>
						
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
							<div class="panel panel-default">
                        	<div class="panel-body table-responsive">
							<table id="selectPlanMaterialTable" class="table table-bordered" data-toggle="table" 
							data-click-to-select="true" data-single-select="true">
								<thead>
									<tr>
										<th class="col-md-1" data-field="state" data-checkbox="true"></th>
										<th class="col-md-1">ID</th>
										<th class="col-md-6">Nombre</th>
										<th class="col-md-3">Coste</th>
										<th class="col-md-1">Tipo</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="material" items="${materials}">
									<tr>
										<td></td>
										<td class="text-info">${material.id}</td>
										<td>${material.name}</td>
										<td>${material.cost}</td>
										<td>${material.type}</td>
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
									Datos relacionados<br> 
									<small>
										Establece el nº de unidades previstas a usar de este material
									</small>
								</h4>
							</div>
						</div>
					
						<form:form id="assignMaterialForm" class="form" method="post" modelAttribute="assignMaterial" 
						role="form" action='/projects/${idProject}/phases/${idPhase}/tasks/${idTask}/materials' 
						data-toggle="validator">

							<div class="form-group">
								<div class="row">
									<input type="hidden" name="idTask" id="idTask" value="${idTask}">
									<input type="hidden" name="idMaterial" id="idMaterial">
									<input type="hidden" name="plan" id="addPlanType">
									<input type="hidden" name="real" id="addRealType">
								
									<div class="form-group col-md-offset-5 col-md-2 has-feedback">
										<label class="control-label">Unidades</label> 
										<div class="right-inner-addon">
											<input type="text" class="form-control" pattern="^[1-9]\d*$" 
											maxlength="3" placeholder="1-999" name="unitsPlan" required>
											<span class="glyphicon form-control-feedback"></span>
										</div>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
								<button id="assignPlanMaterialButton" type="submit"
								 class="btn btn-primary center-block" disabled>
									Asignar
								</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		
		
	<!-- Modal: Update Assignment Plan Material 
	===================================================================================================== -->
		<div class="modal fade" id="updateAsignmtMaterial" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualización de la asignación</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Información sobre el material<br> 
									<small>
										Cambia el número de unidades previstas para este material
									</small>
								</h4>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-12">
								<form:form class="form" id="updateAssignmtPlanAMForm" method="post" action=''
									modelAttribute="asignmntProf" role="form" data-toggle="validator">
									
									<div class="form-group">
										<div class="row">
											<input type="hidden" name="id" id="idAsignmtPlanMaterial">
											<input type="hidden" name="plan" id="updatePlanType">
											<input type="hidden" name="real" id="updateRealType">
											<div class="form-group col-md-offset-4 col-md-4 has-feedback">
												<label class="control-label">Unidades</label> 
												<div class="right-inner-addon">
													<input type="text" class="form-control" pattern="^[1-9]\d*$" 
													maxlength="3" placeholder="1-999" name="unitsPlan" required
													id="unitsPlanAM">
													<span class="glyphicon form-control-feedback"></span>
												</div>
											</div>
										</div>
									</div>
		
									<div class="form-group">
										<div class="row">
											<button id="updateAsignmtMaterialButton" type="submit" 
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
			</div>
		</div>
		
		
	<!-- Modal: Confirmation of delete Material
	===================================================================================================== -->
		<div class="modal fade" id="confirmDeleteMaterial" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Borrado de asignación</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<p id="msgMaterialPlan" align="center"></p>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="row center-block">
							<div class="col-md-offset-3 col-md-3">
								<form id="confirmDeleteMaterialPlanButton" action='' method="post">
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
	
	
	<!-- Enable in the modal, the assign button when the 
		 user selects a Plan Material at the Task. 
	===================================================================================================== -->
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#selectPlanMaterialTable').change(function() { 
			   
			   // Get info of the table
			   var json = JSON.parse(JSON.stringify($('#selectPlanMaterialTable').bootstrapTable('getSelections')));
			   var size = json.length;
			   
			   // If a option is selected then we can assign a Material
			   if (size > 0) {
				   var id = json['0']['1'];
				   document.getElementById("idMaterial").value = id;
				   document.getElementById("assignPlanMaterialButton").disabled = false;
			   } 
			   else {
				   document.getElementById("assignPlanMaterialButton").disabled = true;
			   }		        
		   }); 
		});
	</script>
	
	
	<!-- Data transfer to the modal assignMaterial
	===================================================================================================== -->
	<script type='text/javascript'>
		$(function() {
			$('#assignMaterial').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var type = button.data('type');
				var units = button.data('units');

				if (type == "plan") {
					document.getElementById('addPlanType').value = true;
					document.getElementById('addRealType').value = false;
				} else {
					document.getElementById('addPlanType').value = false;
					document.getElementById('addRealType').value = true;
				}
			})
		});
	</script>
	
	
	<!-- Data transfer to the modal updateAsignmtMaterial
	===================================================================================================== -->
	<script type='text/javascript'>
		$(function() {
			$('#updateAsignmtMaterial').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget);
				
				// Extract info from data attributes
				var id = button.data('id');
				var type = button.data('type');
				var units = button.data('units');

				var url = "/projects/" + ${idProject} + "/phases/" +  ${idPhase} + "/tasks/" + 
				  ${idTask} + "/materials/" + id + "/update";
				  
				// Set the values at the modal components
				document.getElementById('unitsPlanAM').value = units;
				document.getElementById('updateAssignmtPlanAMForm').action = url;
				
				if (type == "plan") {
					document.getElementById('updatePlanType').value = true;
					document.getElementById('updateRealType').value = false;
				} else {
					document.getElementById('updatePlanType').value = false;
					document.getElementById('updateRealType').value = true;
				}
			})
		});
	</script>
	
	
	<!-- Enable the update button when the units has changed. 
	===================================================================================================== -->
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#unitsPlanAM').change(function() { 
			   
			   document.getElementById("updateAsignmtMaterialButton").disabled = false;	        
		   }); 
		});
	</script>
	
	
	<!-- Data transfer to the modal confirmDeleteMaterial
	===================================================================================================== -->
	<script type='text/javascript'>
		$(function() {
			$('#confirmDeleteMaterial').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var id = button.data('id');
				var name = button.data('name');
				
				// Set the message and the url
				var msg = "¿Seguro que desea desasignar el material "
						  + name.bold() + " de la planificación de la tarea?";

				var url = "/projects/" + ${idProject} + "/phases/" +  ${idPhase} + "/tasks/" + 
				  ${idTask} + "/materials/" + id + "/delete";
				  
				// Set the values at the modal components
				document.getElementById('msgMaterialPlan').innerHTML = msg;
				$('#confirmDeleteMaterialPlanButton').get(0).setAttribute('action', url);
			})
		});
	</script>

</body>
</html>
