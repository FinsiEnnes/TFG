
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
				<li><a href="/projects/${idProject}/phases/${idPhase}/tasks/${idTask}"><font size="3">Tarea</font></a></li>
				<li><a href="/projects/${idProject}/milestones"><font size="3">Hito</font></a></li>
				<li class="active"><a href="#"><font size="3">Personas</font></a></li>
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
									<a href="/persons">
										Lista de personas
										<span class="glyphicon glyphicon-info-sign pull-right"></span>
									</a>
								</li>
								</ul>
								
								<ul>
								<li>
									<a href="#" class="active">
										Detalles persona
										<span class="glyphicon glyphicon-indent-right pull-right"></span>
									</a>
								</li>
								</ul>
						</ul>
					</div>
				</div>
			</div>

			<!-- Interface Project information
    		================================================== -->
			<div class="col-md-9">
			
				<h3>
					Información<br> 
					<small>
					Cambia la información básica de esta persona
					</small>
				</h3>
				<hr width="110%">
				<br>
				
				<form:form id="personForm" class="form-horizontal" method="post" 
						action="/persons/${person.id}/update" modelAttribute="person"
						role="form" data-toggle="validator">

						<!-- First row -->
						<div class="row">
							<label class="col-md-2 control-label">Nombre</label>
							<div class="form-group col-md-3">
								<input class="form-control" name="name" id="name" type="text"
									value="${person.name}" required>
							</div>

							<label class="col-md-2 control-label">DNI</label>
							<div class="form-group col-md-3">
								<input class="form-control" readonly name="nif" id="nif" type="text"
									value="${person.nif}" required>
							</div>
						</div>
						<br>
						
						<!-- Second row -->
						<div class="row">
							<label class="col-md-2 control-label">Apellido 1</label>
							<div class="form-group col-md-3">
								<input class="form-control" name="surname1" id="surname1"
									type="text" value="${person.surname1}" required>
							</div>

							<label class="col-md-2 control-label">Email</label>
							<div class="form-group col-md-3">
								<input class="form-control" name="email" id="email" type="text"
									value="${person.email}" required>
							</div>
						</div>
						<br>
						
						<!-- Third row -->
						<div class="row">
							<label class="col-md-2 control-label">Apellido 2</label>
							<div class="form-group col-md-3">
								<input class="form-control" name="surname2" id="surname2"
									type="text" value="${person.surname2}" required>
							</div>

							<label class="col-md-2 control-label">Fecha de alta</label>
							<div class="form-group col-md-3 date">
								<input type="text" class="form-control datepicker"
									data-format="dd/MM/yyyy" name="hiredate" id="hiredate"
									placeholder="dd/mm/aaaa" required value="${person.hiredate}">
							</div>
						</div>
						<br>

						<!-- Fourth row -->
						<div class="row">
							<label class="col-md-2 control-label" for="comment">Comentarios</label>
							<div class="form-group col-md-8" style="width: 63.8%">
								<textarea class="form-control pull-right" rows="6" name="comment"
									id="comment">${person.comment}
									</textarea>
							</div>
						</div>
						<br>

						<!-- Submit button -->
						<div class="row">
							<div class="col-md-offset-5 col-md-2">
								<button id="saveChangesButton" type="submit" disabled
									class="btn btn-primary">Guardar cambios</button>
							</div>
						</div>
					</form:form>

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
	
	<!-- --------- Able the save button for Person updates when its data has changed  --------- -->
	<script type="text/javascript">
		var somethingChanged = false;
		$(document).ready(function() { 
		   $('#personForm').change(function() { 
		        document.getElementById("saveChangesButton").disabled = false;
		        
		   }); 
		});
	</script>


</body>
</html>
