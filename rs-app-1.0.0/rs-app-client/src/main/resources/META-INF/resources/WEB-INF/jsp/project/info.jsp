
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
									<a href="/persons" class="active"> Información 
										<span class="glyphicon glyphicon-info-sign pull-right"></span>
									</a>
								</li>
								<li>
									<a href="#"> Estado 
										<span class="glyphicon glyphicon-check pull-right"></span>
									</a>
								</li>
								<li>
									<a href="#"> Estadísticas
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
					Información del proyecto<br> <small>Cambia la
						configuración básica del proyecto</small>
				</h3>
				<hr width="110%">
				<br>

				<form:form id="projectInfoUpdateForm" class="form-horizontal"
					method="post" action="/projects/${project.id}/update"
					modelAttribute="project" role="form" data-toggle="validator">

					<div class="form-group">
						<div class="row">
							<label class="col-md-2 control-label">Nombre</label>
							<div class="form-group col-md-8">
								<input class="form-control" name="name" id="name" type="text"
									value="${project.name}" required>
							</div>
						</div>
					</div>
					
					
					<div class="form-group">
						<div class="row">
							<label class="col-md-2 control-label">Tipo</label>
							<div class="form-group col-md-2">
								<input class="form-control" name="type" id="type" type="text"
									value="${project.type}" required>
							</div>
							<label class="col-md-7 note"> 
								<font color="grey"> 
									Indica si el proyecto pertenece a la empresa (Interno) 
									o es para un cliente (Externo).
								</font> 
							</label>
						</div>
					</div>
					
					<div class="form-group">
						<div class="row">
							<label class="col-md-2 control-label">Inicio</label>
							<div class="form-group col-md-2">
								<input type="text" class="form-control datepicker"
									   data-format="dd/MM/yyyy" name="iniPlan" id="iniPlan"
									   placeholder="dd/mm/aaaa" value="${iniProject}" required>
							</div>
							<label class="col-md-4 note"> 
								<font color="grey"> 
									Comienzo de la ejecución del proyecto.
								</font> 
							</label>
						</div>
					</div>

					<div class="form-group">
						<div class="row">
							<label class="col-md-2 control-label">País</label> 
							<div class="form-group col-md-3">
								<select class="form-control" name="country" id="country">
									<option id="1">${project.country}</option>
									<c:forEach var="country" items="${countries}">
										<option id="${country.id}">${country.name}</option>
									</c:forEach>
								</select>
							</div>
							
							<label class="col-md-2 control-label">Provincia/Estado</label> 
							<input type="hidden" name="idProvince" id="idProvince" value="${project.idProvince}">
							<div class="form-group col-md-3">
								<select class="form-control" name="province" id="province">
									<option id="1">${project.province}</option>
									<c:forEach var="province" items="${provinces}">
										<option id="${province.id}">${province.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="row">
							<label class="col-md-2 control-label">Fecha estado</label>
							<div class="form-group col-md-2">
								<input type="text" class="form-control datepicker"
									   data-format="dd/MM/yyyy" name="stateDate" id="stateDate"
									   placeholder="dd/mm/aaaa" value="${project.stateDate}" required>
							</div>
							<label class="col-md-4 note"> 
								<font color="grey"> 
									Fecha de la última modificación del proyecto.
								</font> 
							</label>
						</div>
					</div>
					
					<div class="form-group">
						<div class="row">
							<label class="col-md-2 control-label">Presupuesto</label>
							<div class="form-group has-feedback col-md-2">
								<input type="text" class="form-control" name="budget"
									   id="budget" pattern="^[1-9]\d*$" value="${project.budget}"> 
								<span class="glyphicon form-control-feedback"></span>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="row">
							<label class="col-md-2 control-label" for="comment">
								Descripción del proyecto
							</label>
							<div class="form-group col-md-8">
								<textarea class="form-control pull-right" rows="6" name="description"
									id="description">${project.description}
								</textarea>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="row">
							<label class="col-md-2 control-label" for="comment">Comentarios</label>
							<div class="form-group col-md-8">
								<textarea class="form-control pull-right" rows="6" name="comment"
									id="comment">${project.comment}
								</textarea>
							</div>
						</div>
					</div>
					
					<div class="row">
						<button type="submit" class="btn btn-primary center-block">Guardar cambios</button>
					</div>
				</form:form>
				
				<br><br>
				
				<h3>
					Operaciones disponibles<br> 
				</h3>
				<hr width="110%">
				<div class="row">
					<div class="form-group col-md-9">
				<h4>
					Borrado del proyecto<br> 
					<small>
						Borrado completo del proyecto y todos los datos vinculados a él. Esta acción supone un 
						borrado en cascada de las
					</small>
					<small>
					fases, tareas, recursos humanos y materiales relacionados con el proyecto. 
					</small>
				</h4>
					</div>
				</div>
				<div class="row">
					<form action="/projects/${project.id}/delete" method="post">
						<button type="submit" name="page" class="btn btn-danger center-block">
						<span class="glyphicon glyphicon-remove"></span> Eliminar proyecto
					</button>
				</form>
				</div>

				<br><br>
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
