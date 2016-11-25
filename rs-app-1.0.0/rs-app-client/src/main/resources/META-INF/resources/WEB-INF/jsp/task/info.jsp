
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


/* Necessary to center the textarea */
textarea {    
    position: absolute;
    left: -5px;
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
									<a href="#"  class="active"> Información 
										<span class="glyphicon glyphicon-info-sign pull-right"></span>
									</a>
								</li>
								</ul>
								
								<ul>
								<li>
									<a href="#">Predecesoras
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
									<a href="#">Perfiles y personas 
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
					Información básica de la tarea<br> 
					<small>
						Datos principales de la tarea seleccionada en el apartado <em>Planificación</em>
					</small>
				</h3>
				<hr width="110%">
				<br>

				<div class="row">
					<div class="col-md-6">
						<form:form class="form-horizontal">
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
										<select class="form-control" name="namePhase" id="namePhaseNewTask">
											<option id="${task.idPhase}">${task.namePhase}</option>
											<c:forEach var="phase" items="${phases}">
												<option id="${phase.id}">${phase.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-3 control-label">Estado actual</label>
									<div class="form-group col-md-4">
										<input class="form-control" name="name" id="name" type="text"
											value="${task.state}" readOnly>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-1 col-md-3 control-label">Prioridad</label>
									<input type="hidden" name="idPriority" id="ididPriorityPhase" 
										   value="${task.idPriority}">
									<div class="form-group col-md-4">
										<select class="form-control" name="namePhase" id="namePhaseNewTask">
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
										<input class="form-control" name="name" id="name" type="text"
											value="${task.nameResponsible}" required>
									</div>
									<div class="col-md-1 note">
										<form action="#" method="get">
											<button type="submit"
												class="btn btn-primary btn-xs center-block">
												<span class="glyphicon glyphicon-edit"></span>
											</button>
										</form>
									</div>
									<div class="col-md-1 note">
										<form action="#" method="get">
											<button type="submit"
												class="btn btn-info btn-xs center-block">
												<span class="glyphicon glyphicon-info-sign"></span>
											</button>
										</form>
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
								<button type="submit" class="btn btn-primary center-block">
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
							<button id="deleteCustomer" type="button"
								class="btn btn-success center-block"
								data-toggle="modal" data-target="#confirmDelete">
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
							<button id="deleteCustomer" type="button"
								class="btn btn-warning center-block"
								data-toggle="modal" data-target="#confirmDelete">
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
							<button id="deleteCustomer" type="button"
								class="btn btn-danger center-block"
								data-toggle="modal" data-target="#confirmDelete">
								<span class="glyphicon glyphicon-remove"></span> Borrar tarea
							</button>
						</div>
					</div>
				</div>
				
				<br><br>
				<h3>
					Estadísticas de la tarea<br> 
					<small>
						Inicio y fin, duración total, número de horas invertidas y costes finales de la tarea
					</small>
				</h3>
				<hr width="110%">
				<br>
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
	
	<!-- Bootstrap Datapicker initialation
    ================================================== -->	
	<script>
		$(document).ready(function() {
			$('.datepicker').datepicker({
				language : 'es'
			});
		})
	</script>

</body>
</html>
