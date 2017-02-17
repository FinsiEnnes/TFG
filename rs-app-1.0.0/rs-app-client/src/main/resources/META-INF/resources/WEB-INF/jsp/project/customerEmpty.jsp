
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<title>Dashboard Template for Bootstrap</title>

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


/* Increase size icons */
.gi-8x{font-size: 8em;}

/* Table on modal */
.table-responsive {
    max-height:154px;
    padding: 0px;
}

/* Add scrolls in the popup */
.modal .modal-body {
    max-height: 420px;
    overflow-y: auto;
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
			
				<br><br>
				
				<!-- Warning of that project doesnt have customer 
    			================================================== -->
				<div class="row">
					<div class="form-group col-md-offset-2 col-md-8">
						<div class="well">
							<div class="row">
								<div class="form-group col-md-3">
									<span class="glyphicon glyphicon-exclamation-sign gi-8x" aria-hidden="true"></span>
								</div>

								<div class="form-group col-md-9">
									<h3>
										Atención!<br>
										<small>
											Este proyecto todavía no cuenta con un cliente. Se trata de un
										</small>
										<small>
										 	proyecto propio, por lo que el proyecto es de tipo interno. 
										</small>
									</h3>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<!-- Available operations
    			================================================== -->
				<div class="row">
					<h3>
						Operaciones disponibles<br>
					</h3>
					<hr width="110%">
					<div class="row">
						<div class="form-group col-md-9">
							<h4 class="subtitle">
								Agregar cliente<br> 
								<small> 
									Al asignar a un nuevo cliente a este proyecto, este dejará de ser propio
									para cambiar su tipo a externo.
								</small> 
							</h4>
						</div>
					</div>
					<div class="row">
						<button type="submit" name="page" class="btn btn-success center-block"
								data-toggle="modal" data-target="#addCustomer">
								<span class="glyphicon glyphicon-plus"></span>
								Añadir cliente
						</button>
					</div>
				</div>
				
				<!-- Add new client
    			================================================== -->
    			<div class="modal fade" id="addCustomer" role="dialog">
					<div class="modal-dialog modal-lg">
						<!-- Modal content-->
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Añadir cliente</h4>
							</div>
							<div class="modal-body">
								<div class="row">
									<div class="form-group col-md-offset-1 col-md-10">
										<h4>
											Selección de cliente<br> 
											<small>
												Asigna a una de las siguientes empresas como cliente de tu proyecto
											</small>
										</h4>
									</div>
								</div>
								
								<div class="row">
									<div class="form-group col-md-offset-1 col-md-10">
									<div class="panel panel-default">
		                        	<div class="panel-body table-responsive">
									<table id="customerTable" class="table table-bordered table-responsive" 
										   data-toggle="table" data-click-to-select="true" data-single-select="true">
										<thead>
											<tr>
												<th class="col-md-1" data-field="state" data-checkbox="true"></th>
												<th class="col-md-1">ID</th>
												<th class="col-md-2">Nombre</th>
												<th class="col-md-2">País</th>
												<th class="col-md-2">Provincia/Estado</th>
												<th class="col-md-2">Tipo negocio</th>
												<th class="col-md-1">Categoría</th>
												<th class="col-md-1">Tamaño</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="customer" items="${customers}">
											<tr>
												<td></td>
												<td  class="text-info">${customer.id}</td>
												<td>${customer.name}</td>
												<td>${customer.country}</td>
												<td>${customer.province}</td>
												<td>${customer.type}</td>
												<td>${customer.category}</td>
												<td>${customer.size}</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
									</div>
									</div>
									</div>
								</div>
								
								<div class="row">
									<div class="form-group col-md-offset-1 col-md-10">
										<h4>
											Datos de contacto<br> 
											<small>
												Datos de la persona que hablará en nombre de la empresa cliente
											</small>
										</h4>
									</div>
								</div>
								
								<div class="row">
									<div class="col-md-11">
									<form:form action='/projects/${idProject}/customer' method="post" 
											   modelAttribute="project" role="form" data-toggle="validator">
										<input type="hidden" name="idCustomer" id="idCustomer">
										
										<div class="row">
											<div class="form-group col-md-offset-2 col-md-4">
												<label class="control-label">Nombre</label>
												<input type="text" class="form-control"
													name="nameContact" id="nameContact" required>
											</div>

											<div class="form-group col-md-4">
												<label class="control-label">Apellidos</label>
												<input type="text" class="form-control"
													   name="surnameContact" id="surnameContact" required>
											</div>
										</div>
										
										<div class="row">
											<div class="form-group col-md-offset-2 col-md-4 has-feedback">
												<label class="control-label">DNI</label>
												<div class="right-inner-addon">
													<input type="text" class="form-control" name="nifContact" 
														    id="nifContact" pattern="^(\d{8})([A-Z]{1})$" 
														    maxlength="9" placeholder="12345678A" required> 
													<span class="glyphicon form-control-feedback"></span>
												</div>
											</div>

											<div class="form-group col-md-4 has-feedback">
												<label class="control-label">Email</label>
												<div class="right-inner-addon">
													<input type="email" class="form-control" name="emailContact"
														   id="emailContact" placeholder="ejemplo@dominio.com"
														   required>
													<span class="glyphicon form-control-feedback"></span>
												</div>
											</div>
										</div>
										
										<button id="addButton" type="submit" disabled
											class="btn btn-primary center-block">
											Añadir
										</button>
									</form:form>
									</div>	
								</div>
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
	
	<!-- Bootstrap Validator
    ================================================== -->
	<script src="/webjars/bootstrap-validator/0.11.5/dist/validator.js"></script>
	<script src="/webjars/bootstrap-validator/0.11.5/dist/validator.min.js"></script>
	
	<!-- Bootstrap Table
    ================================================== -->
	<script src="/webjars/bootstrap-table/1.11.0/src/bootstrap-table.js"></script>
	
	
	<!-- Enable the add button when a customer is selected 
    ================================================== -->	
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#customerTable').change(function() { 
			   
			   // Get info of the table
			   var json = JSON.parse(JSON.stringify($('#customerTable').bootstrapTable('getSelections')));
			   var size = json.length;
			   
			   // If a option is selected then we can add a Person as manager
			   if (size > 0) {
				   var idPerson = json['0']['1'];
				   document.getElementById("idCustomer").value = idPerson;
				   document.getElementById("addButton").disabled = false;
			   } 
			   else {
				   document.getElementById("addButton").disabled = true;
			   }		        
		   }); 
		});
	</script>

</body>
</html>
