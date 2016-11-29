
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

/* poner color a los hr */
hr { border-color: grey }

/* sangria de los small */
small {
    margin-left: 15px;
}

/* Left padding for subtitles */
.subtitle {
    padding-left: 12px;
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
				<li><a href="#"><font size="3">Materiales</font></a></li>
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
									<a href="/projects/${idProject}/phases/${task.idPhase}/tasks/${task.id}">
										Información 
										<span class="glyphicon glyphicon-info-sign pull-right"></span>
									</a>
								</li>
								</ul>
								
								<ul>
								<li>
									<a href="/projects/${idProject}/phases/${task.idPhase}/tasks/${task.id}/predecessors">
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
									<a href="#" class="active">Perfiles y personas 
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
									<a href="#">Materiales
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
									<a href="#">Incidencias
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
					Planificación - Perfiles<br> 
					<small>
						Planificación de los perfiles profesionales necesarios para poder llevar a cabo la tarea
					</small>
				</h3>
				<hr width="110%">
				<br>
				
				<!-- Profiles list
    			================================================== -->
				<div class="row">
					<div class="form-group col-md-9">
						<h4 class="subtitle">
							Perfiles<br> 
						</h4>
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-md-offset-1 col-md-10">
					<table id="profiles_table" class="table table-bordered"	data-toggle="table" data-height="162">
						<thead>
							<tr>
								<th class="col-md-1"></th>
								<th class="col-md-3">Nombre del perfil</th>
								<th class="col-md-1">Exp. </th>
								<th class="col-md-2">Nivel</th>
								<th class="col-md-1"><span class="glyphicon glyphicon-euro"></span>/h</th>
								<th class="col-md-1"><span class="glyphicon glyphicon-euro"></span>/h extra</th>
								<th class="col-md-1">Acción</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="profcatg" items="${profcatgs}">
							<tr>
								<td>
									<button id="infoAsignmtProfile" type="button"
										class="btn btn-info btn-xs center-block"
										onclick="myFunction(${profcatg.id})">
										<span class="glyphicon glyphicon-info-sign"></span>
									</button>
								</td>
								<td>${profcatg.name}</td>
								<td>${profcatg.minExp}</td>
								<td>${profcatg.level}</td>
								<td align="center">${profcatg.sal}</td>
								<td align="center"> ${profcatg.salExtra}</td>
								<td>
									<table>
										<tr>
											<td>
												<button id="editAsignmtProfile" type="button"
													class="btn btn-primary btn-xs center-block"
													data-toggle="modal" data-target="#updateAsignmtProfile"
													data-id="${profcatg.id}">
													<span class="glyphicon glyphicon-edit"></span>
												</button>
											</td>
											<td>
												<button id="deleteAsignmtProfile" type="button"
													class="btn btn-danger btn-xs center-block"
													data-toggle="modal" data-target="#confirmDelete"
													data-id="${profcatg.id}">
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
				
				<br>
				<div class="row">
					<div class="form-group col-md-9">
						<h4 class="subtitle">
							Previsión del nº de personas y horas necesarias para cubrir el perfil seleccionado 
						</h4>
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-md-offset-2 col-md-8">
					<table id="taskAsigmntProf" class="table table-bordered"	data-toggle="table">
						<thead>
							<tr>
								<th class="col-md-2"  data-field="units"      >Nº de personas</th>
								<th class="col-md-2"  data-field="personhours">Horas/personas</th>
								<th class="col-md-2"  data-field="totalhours" >Horas totales</th>
								<th class="col-md-2"  data-field="cost"       >Coste total</th>
							</tr>
						</thead>
					</table>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Modal: Update Predecessor
    	================================================== -->
		<div class="modal fade" id="updateAsignmtProfile" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualización del perfil</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Información sobre el perfil<br> 
									<small>
										Cambia el número de personas previstas y necesarias para llevar a
										cabo la 
									</small>
									<small>
										tarea, así como las horas dedicadas por cada una.
									</small>
								</h4>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-12">
								<form:form class="form" id="updatePredecessorForm" method="post" action=''
									modelAttribute="asignmntProf" role="form" data-toggle="validator">
									
									<div class="form-group">
										<div class="row">
											<input type="hidden" name="id" id="idAsignmtProf">
										
											<div class="form-group col-md-offset-3 col-md-3">
												<label for="inputName" class="control-label">Nº de personas</label>
												<input type="text" class="form-control"	name="units" id="units">
											</div>
											
											<div class="form-group col-md-3">
												<label for="inputName" class="control-label">Horas/Persona</label>
												<input type="text" class="form-control"
													name="hoursPerPerson" id="hoursPerPerson">
											</div>
										</div>
									</div>
		
									<div class="form-group">
										<div class="row">
											<button id="addButton" type="submit" 
											class="btn btn-primary center-block">
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
		
		
		<!-- Modal: Confirmation of task Predecessor
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
								<p align="center"> 
									¿Seguro que desea eliminar esta tarea como predecesora
									de la actual?
								</p>
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
	
	<script>
		var $table = $('#taskAsigmntProf');	
	
		/* Load the data when the Profile selected has changed */
		function myFunction(idProfCatg) {
			$table.bootstrapTable('load', loadData(idProfCatg));
		}	    
	    
	    function loadData(id) {
	    	
	        var rows = [];
	        var nAssignment = ${assignmtProfJson}.length;
	        
	        for (var i = 0; i < nAssignment; i++) {
	        	if (${assignmtProfJson}[i].idProfCatg == id) {
		            rows.push({
		                units: ${assignmtProfJson}[i].units,
		                personhours: ${assignmtProfJson}[i].personhours,
		                totalhours: ${assignmtProfJson}[i].totalhours,
		                cost: ${assignmtProfJson}[i].cost
		            });
	        	}
	        }
	        return rows;
	    }
	</script>
	
	<!-- Data transfer to the modal updateAsignmtProfile
    ================================================== -->	
	<script type='text/javascript'>
		$(function() {
			$('#updateAsignmtProfile').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var idProfile = button.data('id');
						
				// Get the data of the assignmentProfile
				var units = 0;
				var personhours = 0;
				var idAsignmtProf = 0;
				var nAssignment = ${assignmtProfJson}.length;
				
				for (var i = 0; i < nAssignment; i++) {
		        	if (${assignmtProfJson}[i].idProfCatg == idProfile) {
		        		idAsignmtProf = ${assignmtProfJson}[i].id;
			        	units = ${assignmtProfJson}[i].units;
			   			personhours = ${assignmtProfJson}[i].personhours;

		        	}
		        }
				
				// Set the url
				var url = "/projects/" + ${idProject} + "/phases/" +  ${idPhase} + "/tasks/" + 
				  ${idTask} + "/profiles/" + idAsignmtProf + "/update";
				  
				// Set the values at the modal components
				document.getElementById('updatePredecessorForm').action = url;
				document.getElementById('units').value = units;
				document.getElementById('hoursPerPerson').value = personhours;
				document.getElementById('idAsignmtProf').value = idAsignmtProf;
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
				var idProfile = button.data('id');
						  
				// Get the data of the assignmentProfile
				var idAsignmtProf = 0;
				var nAssignment = ${assignmtProfJson}.length;
							
				for (var i = 0; i < nAssignment; i++) {
			       	if (${assignmtProfJson}[i].idProfCatg == idProfile) {
			       		idAsignmtProf = ${assignmtProfJson}[i].id;
					}
				}
				
				// Set the url
				var url = "/projects/" + ${idProject} + "/phases/" +  ${idPhase} + "/tasks/" + 
				  ${idTask} + "/profiles/" + idAsignmtProf + "/delete";
						
				// Set the values at the modal components
				$('#confirmDeleteButton').get(0).setAttribute('action', url);
			})
		});
	</script>

</body>
</html>
