
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<title>Project statics</title>

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
				<li><a href="/project/${project.id}/planning"><font size="3">Planificaci�n</font></a></li>
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
									<a href="/projects/${project.id}"> Informaci�n 
										<span class="glyphicon glyphicon-info-sign pull-right"></span>
									</a>
								</li>
								<li>
									<a href="/projects/${project.id}/states"> Estado 
										<span class="glyphicon glyphicon-check pull-right"></span>
									</a>
								</li>
								<li>
									<a href="#" class="active"> Estad�sticas
										<span class="glyphicon glyphicon-stats pull-right"></span>
									</a>
								</li>
								<li>
									<a href="/projects/${project.id}/customer">Cliente
										<span class="glyphicon glyphicon-user pull-right"></span>
									</a>
								</li>
								</ul>
							</li>
						</ul>

						<br>
						<ul>
							<li class="menu-head"><b>DIRECCI�N</b></li>
							<li class="menu">
								<ul>
								<li>
									<a href="/projects/${project.id}/managers">Jefes de proyecto 
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
					Estad�sticas<br> 
					<small>Recopilaci�n de los diversos datos procedentes de las tareas</small>
				</h3>
				<hr width="110%">
				<br>

				<div class="row">
					<div class="form-group col-md-9">
						<h4>
							Inicio y fin<br> 
							<small> 
								Per�odo abarcado desde la fecha marcada como inicio de 
								proyecto hasta la finalizaci�n de la �ltima tarea. 
							</small>
						</h4>
					</div>
				</div>

				<div class="row">
					<div class="form-group col-md-offset-3 col-md-6">
					<table id="person_table" class="table table-bordered"
						data-toggle="table">
						<thead>
							<tr>
								<th class="col-md-1"></th>
								<th class="col-md-1">Inicio</th>
								<th class="col-md-1">Fin</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-info">Previsto</td>
								<td>${project.iniPlan}</td>
								<td>${project.endPlan}</td>
							</tr>
							
							<tr>
								<td class="text-info">Real</td>
								<td>${project.iniReal}</td>
								<td>${project.endReal}</td>
							</tr>
							
							<tr>
								<td class="text-info">Variaci�n</td>
								<td>${project.iniVar}</td>
								<td>${project.endVar}</td>
							</tr>
						</tbody>
					</table>
					</div>
				</div>
				
				<br>
				<div class="row">
					<div class="form-group col-md-9">
						<h4>
							Duraci�n, horas y coste<br> 
							<small> 
								Suma total de d�as, horas invertidas y coste de las tareas del proyecto.
							</small>
						</h4>
					</div>
				</div>

				<div class="row">
					<div class="form-group col-md-offset-2 col-md-9">
					<table id="person_table" class="table table-bordered"
						data-toggle="table">
						<thead>
							<tr>
								<th class="col-md-1"></th>
								<th class="col-md-1">Duraci�n</th>
								<th class="col-md-1">Horas</th>
								<th class="col-md-1">Coste</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-info">Previsto</td>
								<td>${project.daysPlan}</td>
								<td>${project.hoursPlan}</td>
								<td>${project.costPlan}</td>
							</tr>
							
							<tr>
								<td class="text-info">Real</td>
								<td>${project.daysReal}</td>
								<td>${project.hoursReal}</td>
								<td>${project.costReal}</td>
							</tr>
							
							<tr>
								<td class="text-info">Variaci�n</td>
								<td>${project.daysVar}</td>
								<td>${project.hoursVar}</td>
								<td>${project.costVar}</td>
							</tr>
						</tbody>
					</table>
					</div>
				</div>

				<br>
				<div class="row">
					<div class="form-group col-md-8">
						<h4>
							Ganancias y p�rdidas<br> 
							<small> 
								Beneficios obtenidos en funci�n del presupuesto y los costes del proyecto.
								Adem�s tambi�n se tienen
							</small>
							<small>
								en cuenta las p�rdidas producidas por incidencias.
							</small>
						</h4>
					</div>
				</div>
			
				<br>
				<div class="row">
					<div class="form-group col-md-offset-1 col-md-4">
					<table id="person_table" class="table table-bordered"
						data-toggle="table">
						<thead>
							<tr>
								<th class="col-md-2"></th>
								<th class="col-md-8">Beneficios</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-info">Previsto</td>
								<td>${project.profitPlan}</td>
							</tr>
							
							<tr>
								<td class="text-info">Real</td>
								<td>${project.profitReal}</td>
							</tr>
							
							<tr>
								<td class="text-info">Variaci�n</td>
								<td>${project.profitVar}</td>
							</tr>
						</tbody>
					</table>
					</div>
					
					<div class="form-group col-md-3">
						<table id="person_table" class="table table-bordered" data-toggle="table">
							<thead>
								<tr>
									<th>P�rdidas</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${project.loss}</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="form-group col-md-4">
						<table id="person_table" class="table table-bordered" data-toggle="table">
							<thead>
								<tr>
									<th class="col-md-1"></th>
									<th class="col-md-9">C�lculo de beneficios</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="text-info">Previsto</td>
									<td>Presupuesto - Costes</td>
								</tr>
								
								<tr>
									<td class="text-info">Real</td>
									<td>Presupuesto - (Costes + P�rdidas)</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				
				<br>
				<div class="row">
					<div class="form-group col-md-8">
						<h4>
							Progreso<br> 
							<small> 
								Porcentaje del proyecto en ejecuci�n completado
							</small>
						</h4>
					</div>
				</div>

				<div class="row">
					<div class="form-group col-md-offset-2 col-md-8">
						<div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="${project.progress}"
								aria-valuemin="0" aria-valuemax="100" style="width: ${project.progress}%">
								${project.progress}%</div>
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
