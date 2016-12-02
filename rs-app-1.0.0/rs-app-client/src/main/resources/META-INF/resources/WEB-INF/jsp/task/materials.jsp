
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
							data-toggle="modal" data-target="#addPlanMaterial">
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
							<c:forEach var="material" items="${planMaterials}">
							<tr>
								<td>${material.name}</td>
								<td>${material.cost}</td>
								<td>${material.type}</td>
								<td>${material.unitsPlan}</td>
								<td>${material.totalCostPlan}</td>
								<td>
									<table>
										<tr>
											<td>
												<button id="editPlanMaterial" type="button"
													class="btn btn-primary btn-xs center-block"
													data-toggle="modal" data-target="#updatePlanMaterial"
													data-id="${material.id}" data-units="${material.units}">
													<span class="glyphicon glyphicon-edit"></span>
												</button>
											</td>
											<td>
												<button id="deletePlanMaterial" type="button"
													class="btn btn-danger btn-xs center-block"
													data-toggle="modal" data-target="#confirmDeletePlanMaterial"
													data-id="${material.id}" data-units="${material.units}">
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
							data-toggle="modal" data-target="#addPlanMaterial">
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
							<c:forEach var="material" items="${realMaterials}">
							<tr>
								<td>${material.name}</td>
								<td>${material.cost}</td>
								<td>${material.type}</td>
								<td>${material.unitsPlan}</td>
								<td>${material.totalCostPlan}</td>
								<td>
									<table>
										<tr>
											<td>
												<button id="editRealMaterial" type="button"
													class="btn btn-primary btn-xs center-block"
													data-toggle="modal" data-target="#updateRealMaterial"
													data-id="${material.id}" data-units="${material.units}">
													<span class="glyphicon glyphicon-edit"></span>
												</button>
											</td>
											<td>
												<button id="deleteRealMaterial" type="button"
													class="btn btn-danger btn-xs center-block"
													data-toggle="modal" data-target="#confirmDeleteRealMaterial"
													data-id="${material.id}" data-units="${material.units}">
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

</body>
</html>
