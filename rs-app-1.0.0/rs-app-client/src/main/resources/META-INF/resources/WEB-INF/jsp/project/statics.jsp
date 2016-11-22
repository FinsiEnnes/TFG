
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<title>Dashboard Template for Bootstrap</title>

<!-- Bootstrap core CSS -->
<link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap Datepicker -->
<link href="/webjars/bootstrap-datepicker/1.6.1/css/bootstrap-datepicker.min.css" rel="stylesheet">

<!-- Custom colors for the navigation bar -->
<link href="/css/navbarColors.css" rel="stylesheet">

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

.container dt,.container dd {
  width:auto !important;
  margin-left:auto !important;
  display:inline-block !important;
}

/* el latoooooooo */
.container {
	width: 800px;
	overflow: hidden;
	display: inline-block;
}

.side-bar {
	background: #74AFAD;
	position: absolute;
	height: 100%;
	width: 200px;
	color: #fff;
	transition: margin-left 0.5s;
}

.side-bar ul {
	list-style: none;
	padding: 0px;
}

.side-bar ul li.menu-head {
	font-family: 'Lato', sans-serif;
	padding: 20px;
}

.side-bar ul li.menu-head a {
	color: #fff;
	text-decoration: none;
	height: 50px;
}

.side-bar ul .menu-head  a {
	color: #fff;
	text-decoration: none;
	height: 50px;
}

.side-bar ul .menu li  a {
	color: #fff;
	text-decoration: none;
	display: inline-table;
	width: 100%;
	padding-left: 20px;
	padding-right: 20px;
	padding-top: 10px;
	padding-bottom: 10px;
}

.side-bar ul .menu li  a:hover {
	border-left: 3px solid #ECECEA;
	padding-left: 17px;
	background: #fb9d4b;
}

.side-bar ul .menu li  a.active {
	padding-left: 17px;
	background: #D9853B;
	border-left: 3px solid #ECECEA;
}

.side-bar ul .menu li  a.active:before {
	content: "";
	position: absolute;
	width: 0;
	height: 0;
	border-top: 20px solid transparent;
	border-bottom: 20px solid transparent;
	border-left: 7px solid #D9853B;
	margin-top: -10px;
	margin-left: 180px;
}

.content {
	padding-left: 200px;
	transition: padding-left 0.5s;
}

.active>.side-bar {
	margin-left: -150px;
	transition: margin-left 0.5s;
}

.active>.content {
	padding-left: 50px;
	transition: padding-left 0.5s;
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
				<li><a href="/project/${project.id}/planning"><font size="3">Planificación</font></a></li>
				<li class="active"><a href="#"><font size="3">Proyecto</font></a></li>
				<li><a href="#"><font size="3">Tarea</font></a></li>
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
									<a href="/projects/${project.id}"> Información 
										<span class="glyphicon glyphicon-info-sign pull-right"></span>
									</a>
								</li>
								<li>
									<a href="#"> Estado 
										<span class="glyphicon glyphicon-check pull-right"></span>
									</a>
								</li>
								<li>
									<a href="#" class="active"> Estadísticas
										<span class="glyphicon glyphicon-stats pull-right"></span>
									</a>
								</li>
								<li>
									<a href="#">Cliente
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
									<a href="#">Jefes de proyecto 
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
									<a href="#">Calendario
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
					Estadísticas<br> 
					<small>Recopilación de los diversos datos procedentes de las tareas</small>
				</h3>
				<hr width="110%">
				<br>

				<div class="row">
					<div class="form-group col-md-9">
						<h4>
							Inicio y fin<br> 
							<small> 
								Período abarcado desde la fecha marcada como inicio de 
								proyecto hasta la finalización de la última tarea. 
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
								<td></td>
								<td></td>
							</tr>
							
							<tr>
								<td class="text-info">Real</td>
								<td></td>
								<td></td>
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
					<div class="form-group col-md-9">
						<h4>
							Duración, horas y coste<br> 
							<small> 
								Suma total de días, horas invertidas y coste de las tareas del proyecto.
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
								<th class="col-md-1">Duración</th>
								<th class="col-md-1">Horas</th>
								<th class="col-md-1">Coste</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-info">Previsto</td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							
							<tr>
								<td class="text-info">Real</td>
								<td></td>
								<td></td>
								<td></td>
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

				<br>
				<div class="row">
					<div class="form-group col-md-8">
						<h4>
							Ganancias y pérdidas<br> 
							<small> 
								Beneficios obtenidos en función del presupuesto y los costes del proyecto.
								Además también se tienen
							</small>
							<small>
								en cuenta las pérdidas producidas por incidencias.
							</small>
						</h4>
					</div>
				</div>
			
				<br>
				<div class="row">
					<div class="form-group col-md-offset-1 col-md-6">
					<table id="person_table" class="table table-bordered"
						data-toggle="table">
						<thead>
							<tr>
								<th class="col-md-1"></th>
								<th class="col-md-1">Beneficios</th>
								<th class="col-md-1">Pérdidas</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-info">Previsto</td>
								<td></td>
								<td></td>
							</tr>
							
							<tr>
								<td class="text-info">Real</td>
								<td></td>
								<td></td>
							</tr>
							
							<tr>
								<td class="text-info">Variación</td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
					</div>
					
					<div class="form-group col-md-offset-1 col-md-4">
						<div class="well">
							<strong>Cálculo de beneficios</strong>
							<dl class="dl-horizontal">
							  <dt>Previsto</dt>
							  <dd>Presupuesto - Costes</dd>
							  <dt>Real</dt>
							  <dd>Presupuesto - (Costes + Pérdidas)</dd>
							</dl>
						</div>
					</div>
				</div>
				
				<br>
				<div class="row">
					<div class="form-group col-md-8">
						<h4>
							Progreso<br> 
							<small> 
								Porcentaje del proyecto en ejecución completado
							</small>
						</h4>
					</div>
				</div>

				<div class="row">
					<div class="form-group col-md-offset-2 col-md-8">
						<div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="35"
								aria-valuemin="0" aria-valuemax="100" style="width: 35%">
								35%</div>
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
