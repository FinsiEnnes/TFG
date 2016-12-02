
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

.note-button {
    padding-top: 5px;
    padding-left: 8px;
}

/* Neccesar to adapt a table in the modal */
.table-responsive {
    max-height:150px;
    padding: 0px;
}

/* Necessary to center the textarea */
textarea {    
    position: absolute;
    left: 15px;
}

/* Color modification to the progress bar */
.progress { 
	background: rgba(212, 212, 212, 1); 
	border: 0px solid rgba(245, 245, 245, 1); border-radius: 4px; height: 20px;
}

.progress-bar-custom {
	background: rgba(66, 139, 202, 1);
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
									<a href="#"  class="active"> Información 
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
									<a href="/projects/${idProject}/phases/${task.idPhase}/tasks/${task.id}/persons">
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
									<a href="/projects/${idProject}/phases/${task.idPhase}/tasks/${task.id}/materials">
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
			
			
				<!-- Basic information of the Task
    			================================================== -->
				<h3>
					Información básica de la tarea<br> 
					<small>
						Datos principales de la tarea seleccionada en el apartado <em>Planificación</em>
					</small>
				</h3>
				<hr width="110%">
				<br>

				<div class="row">
					<div class="col-md-6">
						<form:form class="form-horizontal" id="updateTaskBasic" method="post" 
							action="/projects/${idProject}/phases/${task.idPhase}/tasks/${task.id}/update/basic"
							modelAttribute="task" role="form" data-toggle="validator">
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-3 control-label">Nombre</label>
									<div class="form-group col-md-8">
										<input class="form-control" name="name" id="name" type="text"
											value="${task.name}" required>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-3 control-label">Fase</label> 
									<input type="hidden" name="idPhase" id="idPhase" value="${task.idPhase}">
									<div class="form-group col-md-8">
										<input class="form-control" name="namePhase" id="namePhase" type="text"
											value="${task.namePhase}" readOnly>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-3 control-label">Estado actual</label>
									<input type="hidden" name="idState" id="idState" value="${task.idState}">
									<div class="form-group col-md-4">
										<input class="form-control" name="state" id="state" type="text"
											value="${task.state}" readOnly>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-3 control-label">Prioridad</label>
									<input type="hidden" name="idPriority" id="idPriority" 
										   value="${task.idPriority}">
									<div class="form-group col-md-4">
										<select class="form-control" name="priority" id="priority">
											<option id="${task.idPriority}">${task.priority}</option>
											<c:forEach var="priority" items="${priorities}">
												<option id="${priority.id}">${priority.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-3 control-label">Responsable</label>
									<input type="hidden" name="idResponsible" id="idResponsible" 
										   value="${task.idResponsible}">
									<div class="form-group col-md-6">
										<input class="form-control" name="nameResponsible" id="nameResponsible" type="text"
											value="${task.nameResponsible}" readOnly>
									</div>
									<div class="col-md-1 note">
										
											<button type="button" class="btn btn-primary btn-xs center-block"
													data-toggle="modal" data-target="#selectNewResponsible">
												<span class="glyphicon glyphicon-edit"></span>
											</button>
										
									</div>
									<div class="col-md-1 note">
											<a type="submit" href="/projects/1"
												class="btn btn-info btn-xs">
												<span class="glyphicon glyphicon-info-sign"></span>
											</a>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-3 control-label">Comentarios</label>
									<div class="form-group col-md-8">
										<textarea class="form-control" rows="6" name="comment"
											id="comment" style="width: 332px;">${task.comment}
										</textarea>
									</div>
								</div>
							</div>

							<br><br><br><br><br><br>
							<div class="row">
								<div class="col-md-offset-3 col-md-9">
								<button id="saveBasicChangesButton" type="submit" 
										class="btn btn-primary center-block" disabled>
									<span class="glyphicon glyphicon-save"></span>
									 Guardar cambios
								</button>
								</div>
							</div>
						</form:form>
					</div>

					<div class="col-md-offset-1 col-md-3">
						
						<h4>
							Cambio de estado<br>
							<small>
								  Actualización del estado actual
							</small>
							<small>
								  de la tarea por otro compatible.
							</small>
						</h4>
						<div class="row">
							<button id="changeStateButton" type="button"
								class="btn btn-success center-block"
								data-toggle="modal" data-target="#changeToPrpd">
								<span class="glyphicon glyphicon-edit"></span> Cambiar estado
							</button>
						</div>
					
						<br><br>
						<h4>
							Cancelación de la tarea<br>
							<small>
								Suspensión de la tarea. Los datos 
							</small>
							<small>
								 relativos a la tarea no se borrarán, 
							</small> 
							<small>
								sin embargo la edición ya no estará 
							</small>
							<small>
								disponible. El cambio es irreversible.
							</small>
						</h4>
						<div class="row">
							<button id="cancelTaskButton" type="button"
								class="btn btn-warning center-block"
								data-toggle="modal" data-target="#confirmCancel">
								<span class="glyphicon glyphicon-pause"></span> Cancelar tarea
							</button>
						</div>
						
						<br><br>
						<h4>
							Borrado de tarea<br>
							<small>
								  Borrado completo de todos los
							</small>
							<small>
								  datos de la tarea, además de las 
							</small> 
							<small>
								asignaciones de perfiles, personas o 
							</small>
							<small>
								materiales y vinculaciones con otras
							</small>
							<small>
								tareas.
							</small>
						</h4>
						<div class="row">
							<button id="deleteTaskButton" type="button"
								class="btn btn-danger center-block"
								data-toggle="modal" data-target="#confirmDelete">
								<span class="glyphicon glyphicon-remove"></span> Borrar tarea
							</button>
						</div>
					</div>
				</div>
				
				<!-- Statics of the Task
    			================================================== -->
				<br><br>
				<h3>
					Estadísticas de la tarea<br> 
					<small>
						Inicio y fin, duración total, número de horas invertidas y costes finales de la tarea
					</small>
				</h3>
				<hr width="110%">
				<br>
				
				<div class="row">
					<div class="form-group col-md-3">
						<div class="alert alert-info">
							<span class="glyphicon glyphicon-info-sign"></span>
							Solo el inicio y la duración previstos son editables. 
							El resto derivan de la actividad de la tarea.
						</div>
					</div>
					<div class="form-group col-md-offset-1 col-md-4">
						<table id="person_table" class="table table-bordered"
							data-toggle="table">
							<thead>
								<tr>
									<th class="col-md-2"></th>
									<th class="col-md-5">Inicio</th>
									<th class="col-md-5">Fin</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="text-info">Previsto</td>
									<td>${task.iniPlan}</td>
									<td>${task.endPlan}</td>
								</tr>
								
								<tr>
									<td class="text-info">Real</td>
									<td>${task.iniReal}</td>
									<td>${task.endReal}</td>
								</tr>
								
								<tr>
									<td class="text-info">Variación</td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
					
				<br>
				<div class="row">
					<div class="form-group col-md-offset-3 col-md-6">
						<table id="person_table" class="table table-bordered"
							data-toggle="table">
							<thead>
								<tr>
									<th class="col-md-2"></th>
									<th class="col-md-3">Duración</th>
									<th class="col-md-3">Horas</th>
									<th class="col-md-3">Coste</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="text-info">Previsto</td>
									<td>${task.daysPlan}</td>
									<td>${task.hoursPlan}</td>
									<td>${task.costPlan}</td>
								</tr>
								
								<tr>
									<td class="text-info">Real</td>
									<td>${task.daysReal}</td>
									<td>${task.hoursReal}</td>
									<td>${task.costReal}</td>
								</tr>
								
								<tr>
									<td class="text-info">Variación</td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-md-offset-3 col-md-6">
						<button id="editStaticsButton" type="button"
							class="btn btn-primary center-block"
							data-toggle="modal" data-target="#updateStatics">
							<span class="glyphicon glyphicon-edit"></span>
							Editar
						</button>
					</div>
				</div>
				
				<!-- Progress Task
    			================================================== -->
				<br><br>
				<h3>
					Progreso<br> 
					<small>
						Porcentaje del trabajo completado de la tarea durante su ejecución
					</small>
				</h3>
				<hr width="110%">
				<br>
				<div class="row">
					<div class="form-group col-md-offset-2 col-md-8">
						<div class="progress">
							<div class="progress-bar progress-bar-custom" role="progressbar" aria-valuenow="${task.progress}"
								aria-valuemin="0" aria-valuemax="100" style="width: ${task.progress}%">
								${task.progress}%</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-offset-2 col-md-8">
						<button id="updateProgressButton" type="button"
								class="btn btn-info center-block" disabled
								data-toggle="modal" data-target="#updateProgress">
								<span class="glyphicon glyphicon-refresh"></span>
								 Actualizar
						</button>
					</div>
				</div>
				<br>
			</div>
		</div>	
		
		<!-- Modal: Update of task responsible
    	================================================== -->
		<div class="modal fade modal" id="selectNewResponsible" role="dialog">
			<div class="modal-dialog modal-lg">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualización de responsable de tarea</h4>
					</div>

					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Selección de persona<br> 
									<small>
										Asigna a un nuevo responsable para esta tarea
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
										<td  class="text-info">${persons.id}</td>
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
						
						<form:form class="form" method="post" 
						 action='/projects/${idProject}/phases/${task.idPhase}/tasks/${task.id}/update/responsible'
						 modelAttribute="historyPerson" role="form" data-toggle="validator">

							<!-- First row -->
							<div class="form-group">
								<div class="row">
									<input type="hidden" name="id" id="idHistoryPerson" required>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
								<button id="selectPersonButton" type="submit" 
										class="btn btn-primary center-block" disabled>
									Seleccionar
								</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>

			</div>
		</div>
		
		<!-- Modal: Change state to Preparation
    	================================================== -->
		<div class="modal fade" id="changeToPrpd" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Cambio de estado</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Nuevo estado: <em>Preparado</em><br> 
									<small>
										La tarea está lista para ser ejecutada. Se calcula el total
										de días, horas y 
									</small>
									<small>
										coste previstos. Realizado el cambio, esos datos ya no serán editables.
									</small>
								</h4>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-offset-4 col-md-4">
								<form method="post"
								action='/projects/${idProject}/phases/${task.idPhase}/tasks/${task.id}/state/PRPD'>
									<button type="submit" class="btn btn-primary btn-block">
										Cambiar estado
									</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- Modal: Change state to Ejecution
    	================================================== -->
		<div class="modal fade" id="changeToEjec" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Cambio de estado</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-9">
								<h4>
									Nuevo estado: <em>Ejecución</em><br> 
									<small>
										Momento en el que la tarea comienza su ejecución real. Es 
									</small>
									<small>
										necesario establecer la fecha del inicio de la tarea.
									</small>
								</h4>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-12">
								<form:form class="form" method="post" id="updateManagerForm"
								action='/projects/${idProject}/phases/${task.idPhase}/tasks/${task.id}/state/EJEC'
								modelAttribute="task" role="form" data-toggle="validator">

									<!-- First row -->
									<div class="form-group">
										<div class="row">
											<div class="form-group col-md-offset-4 col-md-4">
												<label for="inputName" class="control-label">Inicio real</label>
												<input type="text" class="form-control datepicker"
													data-format="dd/MM/yyyy" name="iniReal" id="iniReal"
													placeholder="dd/mm/aaaa" required>
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="row">
											<button type="submit" class="btn btn-primary center-block">
												Cambiar estado
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
		
		
		<!-- Modal: Change state to Terminate
    	================================================== -->
		<div class="modal fade" id="changeToTerm" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Cambio de estado</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Nuevo estado: <em>Terminado</em><br> 
									<small>
										Finalización de la ejecución de la tarea. Se establece el fin de la
										tarea a  
									</small>
									<small>
										partir del inicio y duración reales. Además se calcula el total
										de días, horas y
									</small>
									<small>
										coste reales en base a la ejecución de la tarea. 
									</small>
								</h4>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-offset-4 col-md-4">
								<form method="post"
								action='/projects/${idProject}/phases/${task.idPhase}/tasks/${task.id}/state/TERM'>
									<button type="submit" class="btn btn-primary btn-block">
										Cambiar estado
									</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- Modal: Confirmation of task cancelation
    	================================================== -->
		<div class="modal fade" id="confirmCancel" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Confirmación de cancelación</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<p align="center">¿Seguro que desea cancelar esta tarea?</p>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="row center-block">
							<div class="col-md-offset-3 col-md-3">
								<form method="post"
								action='/projects/${idProject}/phases/${task.idPhase}/tasks/${task.id}/state/CANC'>
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
		
		
		<!-- Modal: Confirmation of task delete
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
								<p align="center">¿Seguro que desea eliminar esta tarea?</p>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="row center-block">
							<div class="col-md-offset-3 col-md-3">
								<form method="post"
								action='/projects/${idProject}/phases/${task.idPhase}/tasks/${task.id}/delete'>
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
		
		
		<!-- Modal: Change task statics
    	================================================== -->
		<div class="modal fade" id="updateStatics" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualización de estadísticas</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-9">
								<h4>
									Datos previstos<br> 
									<small>
										Modifica el inicio y duración previstos para esta tarea.
									</small>
								</h4>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-12">
								<form:form class="form" method="post" id="updateManagerForm"
								action='/projects/${idProject}/phases/${task.idPhase}/tasks/${task.id}/update/statics'
								modelAttribute="task" role="form" data-toggle="validator">

									<!-- First row -->
									<div class="form-group">
										<div class="row">
											<div class="form-group col-md-offset-3 col-md-3">
												<label for="inputName" class="control-label">Inicio</label>
												<input type="text" class="form-control datepicker"
													data-format="dd/MM/yyyy" name="iniPlan" id="iniPlan"
													placeholder="dd/mm/aaaa" value="${task.iniPlan}" required>
											</div>
											
											<div class="form-group col-md-3">
												<label for="inputName" class="control-label">Duración</label>
												<input type="text" class="form-control"
													name="daysPlan" id="daysPlan"
													value="${task.daysPlan}" required>
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="row">
											<button type="submit" class="btn btn-primary center-block">
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
	
		
		<!-- Modal: Update progress Task
    	================================================== -->
		<div class="modal fade" id="updateProgress" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualización de progreso</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-9">
								<h4>
									Progreso<br> 
									<small>
										Cambio el porcentaje de trabajo realizado en la tarea. El nuevo
									</small>
									<small>
										porcentaje debe ser mayor al actual. Si se alcanza el 100% esto
									</small>
									<small>
										supondrá la terminación de la tarea.
									</small>
								</h4>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-12">
								<form:form class="form" method="post" id="updateManagerForm"
								action='/projects/${idProject}/phases/${task.idPhase}/tasks/${task.id}/update/progress'
								modelAttribute="task" role="form" data-toggle="validator">

									<!-- First row -->
									<div class="form-group">
										<div class="row">
											<div class="form-group has-feedback col-md-offset-4 col-md-4">
												<label for="inputName" class="control-label">Progreso</label>
												<div class="right-inner-addon">
													<input type="text" class="form-control"
														pattern="^(?:100|[1-9]?[0-9])$" maxlength="3"
														name="progress" id="progress"
														value="${task.progress}" required>
													<span class="glyphicon form-control-feedback"></span>
												</div>
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="row">
											<button type="submit" class="btn btn-primary center-block">
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
		
		
		<!-- Modal: Feedback modal
    	================================================== -->
		<div class="modal fade" id="feedbackModal" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Operación exitosa</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12 center-block">${msg}</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success center-block"
							data-dismiss="modal">Aceptar</button>
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
	
	
	<!-- Script that fires when the window is loaded
    ================================================== -->
	<script type="text/javascript">
		$(window).load(function() {
			
			if ('${task.idState}' == "PLAN") {
				document.getElementById("updateProgressButton").disabled = true;	
			}
			
			if ('${task.idState}' == "PRPD") {
				$('#changeStateButton').attr('data-target','#changeToEjec');
				document.getElementById("editStaticsButton").disabled = true;	
			}
			
			if ('${task.idState}' == "EJEC") {
				$('#changeStateButton').attr('data-target','#changeToTerm');
				document.getElementById("editStaticsButton").disabled = true;
				document.getElementById("updateProgressButton").disabled = false;
			}
			
			if ('${task.idState}' == "CANC" || '${task.idState}' == "TERM") {
				document.getElementById("saveBasicChangesButton").disabled = true;
				document.getElementById("changeStateButton").disabled = true;
				document.getElementById("cancelTaskButton").disabled = true;
				document.getElementById("editStaticsButton").disabled = true;
				document.getElementById("updateProgressButton").disabled = true;
			}
			
			if ('${feedback}' == "active") {
				$('#feedbackModal').modal('show');
			}
		});
	</script>
	
	
	<!-- Scripts neccesaries to get the id in selects
    ================================================== -->
	<script type="text/javascript">
		$("#namePhase").change(function() {
			  var id = $(this).children(":selected").attr("id");
			  document.getElementById("idPhase").value = id;
			  
		});
	</script>
	
	<script type="text/javascript">
		$("#priority").change(function() {
			  var id = $(this).children(":selected").attr("id");
			  document.getElementById("idPriority").value = id;
			  
		});
	</script>
	
	<!-- Enable the update button when the data contact is changed
    =============================================================== -->	
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#updateTaskBasic').change(function() { 
				document.getElementById("saveBasicChangesButton").disabled = false;
	        
		   }); 
		});
	</script>
	
	<!-- Enable in the modal, the select responsible button
	     when the user selects a Person to add. 
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
				   document.getElementById("selectPersonButton").disabled = false;
			   } 
			   else {
				   document.getElementById("selectPersonButton").disabled = true;
			   }		        
		   }); 
		});
	</script>

</body>
</html>
