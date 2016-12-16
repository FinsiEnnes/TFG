
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<title>Project customer</title>

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

/* Left padding for subtitles */
.subtitle {
    padding-left: 12px;
}

/* Left padding in the small attribute */
small {
    margin-left: 15px;
}

/* poner color a los hr */
hr { border-color: grey }

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
				<li class="active"><a href="#"><font size="3">Proyecto</font></a></li>
				<li><a href="/projects/${idProject}/phases/${idPhase}/tasks/${idTask}"><font size="3">Tarea</font></a></li>
				<li><a href="/projects/${idProject}/milestones"><font size="3">Hito</font></a></li>
				<li><a href="/persons"><font size="3">Personas</font></a></li>
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
									<a href="/projects/${idProject}"> Información 
										<span class="glyphicon glyphicon-info-sign pull-right"></span>
									</a>
								</li>
								<li>
									<a href="/projects/${idProject}/states"> Estado 
										<span class="glyphicon glyphicon-check pull-right"></span>
									</a>
								</li>
								<li>
									<a href="/projects/${idProject}/statics"> Estadísticas
										<span class="glyphicon glyphicon-stats pull-right"></span>
									</a>
								</li>
								<li>
									<a href="#" class="active">Cliente
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
									<a href="/projects/${idProject}/managers">Jefes de proyecto 
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
			
				<!-- Customer information
    			================================================== -->
				<h3>
					Cliente<br> 
					<small>
						Empresa externa para la que estamos desarrollando el proyecto
					</small>
				</h3>
				<hr width="110%">
				<br>
				
				<div class="row">
					<div class="col-md-8">
						<form:form>
							<div class="row">
								<div class="form-group col-md-offset-1 col-md-3">
									<label class="control-label">Nombre</label> 
									<input type="text" class="form-control" value="${customer.name}" readOnly>
								</div>
		
								<div class="form-group col-md-3">
									<label class="control-label">País</label> 
									<input type="text" class="form-control" value="${customer.country}" readOnly>
								</div>
								
								<div class="form-group col-md-3">
									<label class="control-label">Provincia/Estado</label> 
									<input type="text" class="form-control" value="${customer.province}" readOnly>
								</div>
							</div>
							<br>
							<div class="row">
								<div class="form-group col-md-offset-1 col-md-3">
									<label class="control-label">Tipo</label> 
									<input type="text" class="form-control" value="${customer.type}" readOnly>
								</div>
		
								<div class="form-group col-md-3">
									<label class="control-label">Categoría</label> 
									<input type="text" class="form-control" value="${customer.category}" readOnly>
								</div>
								
								<div class="form-group col-md-3">
									<label class="control-label">Tamaño</label> 
									<input type="text" class="form-control" value="${customer.size}" readOnly>
								</div>
							</div>
						</form:form>
					</div>
					
					<div class="col-md-4">
						<h4>
							Desvinculación de cliente<br> 
							<small>
								El proyecto dejará de tener un cliente para el 
							</small>
							<small>
								cual se está realizando. De esta forma el proyecto
							</small>
							<small>
								pasa a ser interno a la propia empresa.
							</small>
						</h4>
						<br>
						<div class="row">
							<button id="deleteCustomer" type="button"
								class="btn btn-warning center-block"
								data-toggle="modal" data-target="#confirmDelete">
								<span class="glyphicon glyphicon-remove"></span> Desvincular cliente
							</button>
						</div>
					</div>
				</div>
				
				
				<!-- Contact Customer information
    			================================================== -->
				<br>
				<h3>
					Contacto<br> 
					<small>
						Datos de la persona que responde en nombre de la empresa
					</small>
				</h3>
				<hr width="110%">
				<br>

				<div class="row">
					<div class="col-md-12">
						<form:form id="updateContactForm" action='/projects/${idProject}/customer/update' 
							method="post" modelAttribute="project" role="form" data-toggle="validator">
							
							<div class="row">
								<div class="form-group col-md-offset-1 col-md-3">
									<label class="control-label">Nombre</label> <input type="text"
										class="form-control" name="nameContact" id="nameContact"
										value="${project.nameContact}" required>
								</div>

								<div class="form-group col-md-3">
									<label class="control-label">Apellidos</label> <input
										type="text" class="form-control" name="surnameContact"
										id="surnameContact" value="${project.surnameContact}" required>
								</div>
								
								<div class="form-group col-md-2 has-feedback">
									<label class="control-label">DNI</label>
									<div class="right-inner-addon">
										<input type="text" class="form-control" name="nifContact"
											id="nifContact" pattern="^(\d{8})([A-Z]{1})$" maxlength="9"
											placeholder="12345678A" value="${project.nifContact}" required> 
										<span class="glyphicon form-control-feedback"></span>
									</div>
								</div>
								
								<div class="form-group col-md-3 has-feedback">
									<label class="control-label">Email</label>
									<div class="right-inner-addon">
										<input type="email" class="form-control" name="emailContact"
											id="emailContact" placeholder="ejemplo@dominio.com" 
											value="${project.emailContact}" required>
										<span class="glyphicon form-control-feedback"></span>
									</div>
								</div>
							</div>

							<button id="saveChangesButton" type="submit" disabled
								class="btn btn-primary center-block">Guardar cambios</button>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- Modal: Confirmation of customer delete
    	================================================== -->
		<div class="modal fade" id="confirmDelete" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Confirmación de desvinculación</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<p align="center">¿Seguro que desea desvincular el cliente del proyecto?</p>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="row center-block">
							<div class="col-md-offset-3 col-md-3">
								<form action='/projects/${idProject}/customer/delete' method="post">
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
	
	<!-- Bootstrap Datapicker initialation
    ================================================== -->	
	<script>
		$(document).ready(function() {
			$('.datepicker').datepicker({
				language : 'es'
			});
		})
	</script>
	
	
	<!-- Enable the update button when the data contact is changed
    =============================================================== -->	
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#updateContactForm').change(function() { 
				document.getElementById("saveChangesButton").disabled = false;
	        
		   }); 
		});
	</script>

</body>
</html>
