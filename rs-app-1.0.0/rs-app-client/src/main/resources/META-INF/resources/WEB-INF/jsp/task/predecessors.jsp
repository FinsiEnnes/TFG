
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<title>Task Predecessors</title>

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

/* Neccesary to adapt a table in the modal */
.table-responsive {
    max-height:154px;
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
									<a href="#"
									   class="active">
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

			<!-- Interface Project information
    		================================================== -->
			<div class="col-md-9">
			
				<h3>
					Tareas predecesoras<br> 
					<small>
						Vínculos de la tarea seleccionada con otras y las cuales determinan su comienzo o fin
					</small>
				</h3>
				<hr width="110%">
				<br>
				
				
				<!-- Predecessor list
    			================================================== -->
				<div class="row">
					<div class="form-group col-md-9">
						<h4 class="subtitle">
							Predecesoras<br> 
							<small> 
								Listado de tareas que preceden a la tarea seleccionada
							</small>
						</h4>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-offset-8 col-md-2">
						<button id="addPredecessorButton" type="button" class="btn btn-success pull-right"
							data-toggle="modal" data-target="#addPredecessor">
							<span class="glyphicon glyphicon-plus"></span> 
							Nueva predecesora
						</button>
					</div>
				</div>
				
				<br>
				<div class="row">
					<div class="form-group col-md-offset-2 col-md-8">
					<table id="managers_table" class="table table-bordered"	data-toggle="table" data-height="240">
						<thead>
							<tr>
								<th class="col-md-6">Nombre de la predecesora</th>
								<th class="col-md-2">Tipo</th>
								<th class="col-md-1">Acción</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="predecessor" items="${predecessors}">
							<tr>
								<td>${predecessor.taskPredName}</td>
								<td>${predecessor.linkType}</td>
								<td>
									<table>
										<tr>
											<td>
												<button id="editManager" type="button"
													class="btn btn-primary btn-xs center-block"
													data-toggle="modal" data-target="#updatePredecessor"
													data-id="${predecessor.id}">
													<span class="glyphicon glyphicon-edit"></span>
												</button>
											</td>
											<td>
												<button id="deletePredecessor" type="button"
													class="btn btn-danger btn-xs center-block"
													data-toggle="modal" data-target="#confirmDelete"
													data-id="${predecessor.id}">
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
				
				
				<br><br>
				<div class="row">
					<div class="form-group col-md-9">
						<h4 class="subtitle">
							Tipo de relaciones<br>
							<small> 
								Tabla resumen en la que se explican las posibles relaciones entre tareas
							</small> 
						</h4>
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-md-offset-2 col-md-8">
					<table id="person_table" class="table table-bordered"
						data-toggle="table">
						<thead>
							<tr>
								<th class="col-md-1">Tipo</th>
								<th class="col-md-3">Nombre</th>
								<th class="col-md-8">Descripción</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-info">FC</td>
								<td>Fin - Comienzo</td>
								<td>Para que la tarea pueda comenzar su ejecución, su predecesora
									debe haber terminado. Esta es la relación más habitual de todas.
								</td>
							</tr>
							
							<tr>
								<td class="text-info">FF</td>
								<td>Fin - Fin</td>
								<td>Relación en la que el fin de la tarea predecesora supone el hecho
									de que la sucesora pueda finalizar.
								</td>
							</tr>
							
							<tr>
								<td class="text-info">CC</td>
								<td>Comienzo - Comienzo</td>
								<td>El comienzo de la tarea predecesora implica que la
									sucesora pueda comenzar.</td>
							</tr>
							
							<tr>
								<td class="text-info">CF</td>
								<td>Comienzo - Fin</td>
								<td>La finalización de la tarea sucesora depende del momento 
									en el que se comience a ejecutar su tarea predecesora.</td>
							</tr>
						</tbody>
					</table>
					</div>
				</div>

				<br><br>
			</div>
		</div>
		
		
		<!-- Modal: Creation of a new link between task
    	================================================== -->
		<div class="modal fade modal" id="addPredecessor" role="dialog">
			<div class="modal-dialog modal-lg">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Creación de nueva predecesora</h4>
					</div>

					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Selección de tarea predecesora<br> 
									<small>
										Elige una tarea del proyecto para asignarla como predecesora de la actual
										y el tipo de relación entre ellas.
									</small>
								</h4>
							</div>
						</div>
						
						<form:form>		
							<div class="row">
								<div class="form-group col-md-offset-4 col-md-4">
									<label for="inputName" class="control-label">Fase</label> 
									<select id="selectPhase" class="form-control">
										<c:forEach var="phase" items="${phases}">
											<option id="${phase.id}">${phase.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</form:form>
						
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
							<div class="panel panel-default">
                        	<div class="panel-body table-responsive">
							<table id="taskTable" class="table table-bordered table-responsive" 
								   data-toggle="table" data-click-to-select="true" data-single-select="true">
								<thead>
									<tr>
										<th class="col-md-1" data-field="state" data-checkbox="true"></th>
										<th data-field="id" class="col-md-1">ID</th>
										<th data-field="name" class="col-md-5">Nombre tarea</th>
										<th data-field="ini" class="col-md-2">Inicio</th>
										<th data-field="days" class="col-md-2">Duración (días)</th>
									</tr>
								</thead>
								
							</table>
							</div>
							</div>
							</div>
						</div>
					
						<form:form class="form" method="post" 
							action='/projects/${idProject}/phases/${idPhase}/tasks/${idTask}/predecessors'
							modelAttribute="predecessor" role="form" data-toggle="validator">
							
							<div class="form-group">
								<div class="row">
									<input type="hidden" name="task" id="task" value="${idTask}">
									<input type="hidden" name="taskPred" id="taskPred">
									<input type="hidden" name="linkType" id="linkType" value="FC">
								
									<div class="form-group col-md-offset-4 col-md-4">
										<label for="inputName" class="control-label">Tipo de relación</label> 
										<select id="selectLinkType" class="form-control">
											<option id="FC">Fin - Comienzo</option>
											<option id="FF">Fin - Fin</option>
											<option id="CC">Comienzo - Comienzo</option>
											<option id="CF">Comienzo - Fin</option>
										</select>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
									<button id="addButton" type="submit" disabled
											class="btn btn-primary center-block">
										Asignar predecesora
									</button>
								</div>
							</div>
						</form:form>
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
		
		
		<!-- Modal: Update Predecessor
    	================================================== -->
		<div class="modal fade" id="updatePredecessor" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualización de tarea predecesora</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Tipo de relación<br> 
									<small>
										Cambia el tipo de relación entre la tarea actual y su predecesora
									</small>
								</h4>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-12">
								<form:form class="form" id="updatePredecessorForm" method="post" action=''
									modelAttribute="predecessor" role="form" data-toggle="validator">
									
									<div class="form-group">
										<div class="row">
											<input type="hidden" name="linkType" id="updateLinkType" value="FC">
										
											<div class="form-group col-md-offset-4 col-md-4">
												<label for="inputName" class="control-label">Tipo de relación</label> 
												<select id="updateLinkTypeSelect" class="form-control">
													<option id="FC">Fin - Comienzo</option>
													<option id="FF">Fin - Fin</option>
													<option id="CC">Comienzo - Comienzo</option>
													<option id="CF">Comienzo - Fin</option>
												</select>
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


	<!-- Changes the content of the table dynamicly
    ================================================== -->
	<script>
	    var $table = $('#taskTable');
	    var $button = $('#addPredecessorButton');
	    
	    /* When the user click on addPredecessor, the data is loaded */
		$(function () {
			$button.click(function () {
			var id = ${firstIdPhase};
			$table.bootstrapTable('load', loadDataPhase(id));
        	});
   		 });
	    
	    /* Load the data when the Phase has changed */
	    $(function () {
	    	$("#selectPhase").change(function() {
				  var id = $(this).children(":selected").attr("id");
				  $table.bootstrapTable('load', loadDataPhase(id));
				  
			});
	    });
	    
	    
	    function loadDataPhase(phase) {
	    	
	        var rows = [];
	        var nTask = ${taskjson}.length;
	        
	        for (var i = 0; i < nTask; i++) {
	        	if (${taskjson}[i].phase == phase && ${taskjson}[i].id != ${idTask}) {
		            rows.push({
		                id: ${taskjson}[i].id,
		                name: ${taskjson}[i].name,
		                ini: ${taskjson}[i].ini,
		                days: ${taskjson}[i].days
		            });
	        	}

	        }
	        return rows;
	    }
	</script>
	
	
	<!-- Set the taskPred and linkType when the user selects them
    ================================================================== -->
	<script type="text/javascript">
		$("#taskTable").change(function() {
			
			// Get info of the table
			var data = $('#taskTable').bootstrapTable('getSelections');
			var id = $.map(data, function (item) {
			    return item.id;
			});
			   			   
			// If a option is selected then we can assign the Task as Predecessor
			if (id != "") {
				document.getElementById("taskPred").value = id;
				document.getElementById("addButton").disabled = false;
			} 
			else {
				document.getElementById("addButton").disabled = true;
			}
			  
		});
	</script>
	
	<script type="text/javascript">
		$("#selectLinkType").change(function() {
			  var id = $(this).children(":selected").attr("id");
			  document.getElementById("linkType").value = id;
			  
		});
	</script>
	
	<script type="text/javascript">
		$("#updateLinkTypeSelect").change(function() {
			  var id = $(this).children(":selected").attr("id");
			  document.getElementById("updateLinkType").value = id;
			  
		});
	</script>
	
	<!-- Data transfer to the modal updatePredecessor
    ================================================== -->	
	<script type='text/javascript'>
		$(function() {
			$('#updatePredecessor').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var id = button.data('id');
				var url = "/projects/" + ${idProject} + "/phases/" +  ${idPhase} + "/tasks/" + 
						  ${idTask} + "/predecessors/" + id + "/update";
						
				// Set the values at the modal components
				document.getElementById('updatePredecessorForm').action = url;
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
				var id = button.data('id');
				var url = "/projects/" + ${idProject} + "/phases/" +  ${idPhase} + "/tasks/" + 
						  ${idTask} + "/predecessors/" + id + "/delete";
						
				// Set the values at the modal components
				$('#confirmDeleteButton').get(0).setAttribute('action', url);
			})
		});
	</script>

</body>
</html>
