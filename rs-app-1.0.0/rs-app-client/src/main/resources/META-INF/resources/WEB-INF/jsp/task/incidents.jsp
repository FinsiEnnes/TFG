
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
						Lista completa de las incidencias relacionadas con esta tarea
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
							data-toggle="modal" data-target="#addIncident">
							<span class="glyphicon glyphicon-plus"></span> 
							Nuevo incidencia
						</button>
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-md-offset-1 col-md-10">
					<table id="materialTable" class="table table-bordered"	
					data-toggle="table" data-height="162" data-click-to-select="true" data-single-select="true">
						<thead>
							<tr>
								<th class="col-md-1" data-field="state" data-checkbox="true"></th>
								<th class="col-md-1">ID</th>
								<th class="col-md-4">Razón</th>
								<th class="col-md-2">Daños</th>
								<th class="col-md-2">Pérdidas</th>
								<th class="col-md-2">Acción</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="incident" items="${incidents}">
							<tr>
								<td></td>
								<td class="text-info">${incident.idIncident}</td>
								<td>${incident.reason}</td>
								<td>${incident.damage}</td>
								<td>${incident.losses}</td>
								<td>
									<table>
										<tr>
											<td>
												<button type="button" class="btn btn-primary btn-xs center-block"
													data-toggle="modal" data-target="#editIncident"
													data-id="${incident.idIncident}" data-reason="${incident.reason}"
													data-damage="${incident.damage}" data-losses="${incident.losses}"
													data-result="${incident.result}">
													<span class="glyphicon glyphicon-edit"></span>
												</button>
											</td>
											<td>
												<button id="deleteMaterial" type="button"
													class="btn btn-danger btn-xs center-block"
													data-toggle="modal" data-target="#confirmDeleteIncident"
													data-id="${incident.idIncident}">
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
							Resultados ocasionados por el incidente seleccionado<br> 
						</h4>
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-md-offset-3 col-md-6">
						<textarea class="form-control" rows="6" id="descMaterial">
						</textarea>
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
