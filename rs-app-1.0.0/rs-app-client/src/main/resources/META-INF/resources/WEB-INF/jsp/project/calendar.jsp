
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

/* poner color a los hr */
hr { border-color: grey }

/* sangria de los small */
small {
    margin-left: 15px;
}

/* Left padding for subtitles */
.subtitle {
    padding-left: 12px;
}

/* Neccesar to adapt a table in the modal */
.table-responsive {
    max-height:150px;
    padding: 0px;
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
				<li><a href="/project/${idProject}/planning"><font size="3">Planificaci�n</font></a></li>
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
									<a href="/projects/${idProject}"> Informaci�n 
										<span class="glyphicon glyphicon-info-sign pull-right"></span>
									</a>
								</li>
								<li>
									<a href="/projects/${idProject}/states"> Estado 
										<span class="glyphicon glyphicon-check pull-right"></span>
									</a>
								</li>
								<li>
									<a href="/projects/${idProject}/statics"> Estad�sticas
										<span class="glyphicon glyphicon-stats pull-right"></span>
									</a>
								</li>
								<li>
									<a href="/projects/${idProject}/customer">Cliente
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
									<a href="#" class="active">Calendario
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
					Calendario <br> 
					<small>
						Conjunto de d�as no laborables dentro del proyecto
					</small>
				</h3>
				<hr width="110%">
				<br>
				
				<h4 class="subtitle">
					D�as de la semana	
				</h4>
				<div class="row">
					<div class="form-group col-md-7">
					<table id="managers_table" class="table table-bordered"	data-toggle="table"
						   data-height="280" data-click-to-select="true">
						<thead>
							<tr>
								<th class="col-md-1" data-field="state" data-checkbox="true"></th>
								<th class="col-md-3">D�a</th>
								<th class="col-md-8">Motivo</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="days" items="${freeWeekDays}">
							<tr>
								<td></td>
								<td>${days.weekDayString}</td>
								<td>${days.name}</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
					
					<div class="col-md-offset-1 col-md-4">
						<h4>
							Agregar d�a de la semana<br> 
							<small>
								Establece un d�a de la semana como no 
							</small>
							<small>
								laborable. Durante todo el a�o cualquier actividad 
							</small>
							<small>
								realizada en ese d�a no estar� permitida.
							</small>
						</h4>
						<br>
						<div class="row">
							<button id="deleteCustomer" type="button"
								class="btn btn-success center-block"
								data-toggle="modal" data-target="#newDay">
								<span class="glyphicon glyphicon-plus"></span> Agregar d�a
							</button>
						</div>
						
						<br>
						<h4>
							Eliminar seleccionados<br> 
							<small>
								Los d�as marcados vuelven a ser laborables.
							</small>
						</h4>
						<br>
						<div class="row">
							<button id="deleteCustomer" type="button"
								class="btn btn-danger center-block"
								data-toggle="modal" data-target="#confirmDelete">
								<span class="glyphicon glyphicon-remove"></span> Eliminar d�as
							</button>
						</div>
					</div>
				</div>
				
				<br><br>
				<h4 class="subtitle">
					Per�odos de tiempo
				</h4>
				<div class="row">
					<div class="form-group col-md-7">
					<table id="managers_table" class="table table-bordered"	data-toggle="table"
						   data-height="280" data-click-to-select="true">
						<thead>
							<tr>
								<th class="col-md-1" data-field="state" data-checkbox="true"></th>
								<th class="col-md-3">Per�odo</th>
								<th class="col-md-8">Motivo</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="days" items="${freeWeekDays}">
							<tr>
								<td></td>
								<td>${days.weekDayString}</td>
								<td>${days.name}</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
					
					<div class="col-md-offset-1 col-md-4">
						<h4>
							Agregar per�odo<br> 
							<small>
								Definici�n de un nuevo per�odo que definen 
							</small>
							<small>
								un rango de fechas no laborables en el proyecto.
							</small>
						</h4>
						<br>
						<div class="row">
							<button id="deleteCustomer" type="button"
								class="btn btn-success center-block"
								data-toggle="modal" data-target="#newPeriod">
								<span class="glyphicon glyphicon-plus"></span> Agregar per�odo
							</button>
						</div>
						
						<br>
						<h4>
							Eliminar seleccionados<br> 
							<small>
								Los per�odos marcados vuelven a ser laborables.
							</small>
						</h4>
						<br>
						<div class="row">
							<button id="deleteCustomer" type="button"
								class="btn btn-danger center-block"
								data-toggle="modal" data-target="#confirmDelete">
								<span class="glyphicon glyphicon-remove"></span> Eliminar per�odos
							</button>
						</div>
					</div>
				</div>
				
				<!-- Modal: New day assing
		    	================================================== -->
				<div class="modal fade modal" id="newDay" role="dialog">
					<div class="modal-dialog modal-lg">
		
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Asignaci�n de d�a no laborable</h4>
							</div>
		
							<div class="modal-body">
								<div class="row">
									<div class="form-group col-md-offset-1 col-md-10">
										<h4>
											Selecci�n de d�a<br> 
											<small>
												Selecciona un d�a ya predefinido para marcarlo como no laboral
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
												<th class="col-md-2">D�a</th>
												<th class="col-md-8">Motivo</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="days" items="${otherFreeWeekDays}">
											<tr>
												<td></td>
												<td  class="text-info">${days.id}</td>
												<td>${days.weekDayString}</td>
												<td>${days.name}</td>
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
											Nuevo d�a no laborable<br> 
											<small>
												Si ninguno de los anteriores se adapta a tu proyecto, establece
												otro d�a como no laborable y el motivo de ello.
											</small>
										</h4>
									</div>
								</div>
							
								<form:form class="form" method="post" action='/projects/${idProject}/calendar'
									modelAttribute="freeDay" role="form" data-toggle="validator">
		
									<!-- First row -->
									<div class="form-group">
										<div class="row">
											<input type="hidden" name="weekDay" id="weekDay" required>
										
											<div class="form-group col-md-offset-2 col-md-2">
												<label for="inputName" class="control-label">D�a</label> 											   		   
											 	<select class="form-control" name="weekDayString" 
											 			id="weekDayString">
													<option id="0">Lunes</option>
													<option id="1">Martes</option>
													<option id="2">Mi�rcoles</option>
													<option id="3">Jueves</option>
													<option id="4">Viernes</option>
													<option id="5">S�bado</option>
													<option id="6">Domingo</option>
												</select>
											</div>
		
											<div class="form-group col-md-6">
												<label for="inputSurname1" class="control-label">Motivo </label> 
												<input type="text" class="form-control"
											   		   name="name" id="name">
											</div>
										</div>
									</div>
		
									<div class="form-group">
										<div class="row">
										<button id="addButtonDay" type="submit" 
												class="btn btn-primary center-block">
											A�adir
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
	
	
	<!-- Update the weekDay attribute if the select changes  
    ================================================== -->	
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#weekDayString').change(function() { 
			   var id = $(this).children(":selected").attr("id");
			   document.getElementById("weekDay").value = id;  
		   }); 
		});
	</script>

</body>
</html>
