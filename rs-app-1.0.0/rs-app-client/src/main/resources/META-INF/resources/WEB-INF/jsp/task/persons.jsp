
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
				<li><a href="/projects/${idProject}/milestones"><font size="3">Hito</font></a></li>
				<li><a href="/persons"><font size="3">Personas</font></a></li>
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
									<a href="#" class="active">Perfiles y personas 
										<span class="glyphicon glyphicon-user pull-right"></span>
									</a>
								</li>
								</ul>
								
								<ul>
								<li>
									<a href="/projects/${idProject}/phases/${idPhase}/tasks/${idTask}/workloads">
										Carga de trabajo 
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
									<a href="/projects/${idProject}/phases/${idPhase}/tasks/${idTask}/incidents">
										Incidencias
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
			
				<h3>
					Planificación - Perfiles<br> 
					<small>
						Planificación de los perfiles profesionales necesarios para poder llevar a cabo la tarea
					</small>
				</h3>
				<hr width="110%">
				<br>
				
				<!-- Profiles list
    			================================================== -->
				<div class="row">
					<div class="form-group col-md-2">
						<h4 class="subtitle">
							Perfiles<br> 
						</h4>
					</div>
					<div class="col-md-offset-7 col-md-2">
						<button type="button" class="btn btn-success pull-right"
							data-toggle="modal" data-target="#formProfileAssignation">
							<span class="glyphicon glyphicon-plus"></span> 
							Asignar perfil
						</button>
					</div>
				</div>
			

				<div class="row">
					<div class="form-group col-md-offset-1 col-md-10">
					<table id="profilesAssignTable" class="table table-bordered"	
						   data-toggle="table" data-height="162">
						<thead>
							<tr>
								<th class="col-md-1"></th>
								<th class="col-md-3">Nombre del perfil</th>
								<th class="col-md-1">
									<div class="row">
										<div class="col-md-4">
										Exp
										</div>
										<div class="col-md-6">
										<button type="button" class="btn btn-info btn-xs btn-circle center-block"
												data-toggle="modal" data-target="#infoExp">
												<span class="glyphicon glyphicon-info-sign"></span>
										</button>
										</div>	
									</div>
								</th>
								<th class="col-md-3">Nivel</th>
								<th class="col-md-1"><span class="glyphicon glyphicon-euro"></span>/h</th>
								<th class="col-md-1"><span class="glyphicon glyphicon-euro"></span>/h extra</th>
								<th class="col-md-2">Acción</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="profcatgAssigned" items="${profcatgsAssigned}">
							<tr>
								<td>
									<button id="infoAsignmtProfile" type="button"
										class="btn btn-info btn-xs center-block"
										onclick="loadAssignProf('${profcatgAssigned.id}','${profcatgAssigned.name}')">
										<span class="glyphicon glyphicon-info-sign"></span>
									</button>
								</td>
								<td>${profcatgAssigned.name}</td>
								<td>${profcatgAssigned.minExp}</td>
								<td>${profcatgAssigned.level}</td>
								<td align="center">${profcatgAssigned.sal}</td>
								<td align="center"> ${profcatgAssigned.salExtra}</td>
								<td>
									<table>
										<tr>
											<td>
												<button id="editAsignmtProfile" type="button"
													class="btn btn-primary btn-xs center-block"
													data-toggle="modal" data-target="#updateAsignmtProfile"
													data-id="${profcatgAssigned.id}">
													<span class="glyphicon glyphicon-edit"></span>
												</button>
											</td>
											<td>
												<button id="deleteAsignmtProfile" type="button"
													class="btn btn-danger btn-xs center-block"
													data-toggle="modal" data-target="#confirmDeleteProfile"
													data-id="${profcatgAssigned.id}"
													data-name="${profcatgAssigned.name}">
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
				
				<br>
				<div class="row">
					<div class="form-group col-md-9">
						<h4 class="subtitle">
							Previsión del nº de personas y horas necesarias para cubrir el perfil seleccionado 
						</h4>
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-md-offset-2 col-md-8">
					<table id="tableProfiles" class="table table-bordered" 
						   data-toggle="table">
						<thead>
							<tr>
								<th class="col-md-4"  data-field="profile"     >Perfil</th>
								<th class="col-md-2"  data-field="units"      >Nº de personas</th>
								<th class="col-md-2"  data-field="personhours">Horas/personas</th>
								<th class="col-md-2"  data-field="totalhours" >Horas totales</th>
								<th class="col-md-2"  data-field="cost"       >Coste total</th>
							</tr>
						</thead>
					</table>
					</div>
				</div>
				
				<br><br>
				<h3>
					Ejecución - Personas<br> 
					<small>
						Personas finalmente asignadas a la tarea durante la ejecución del proyecto
					</small>
				</h3>
				<hr width="110%">
				<br>
				
				<!-- Person list
	    		================================================== -->
				<div class="row">
					<div class="form-group col-md-3">
						<h4 class="subtitle">
							Personas asignadas<br> 
						</h4>
					</div>
					<div class="col-md-offset-6 col-md-2">
						<button type="button" class="btn btn-success pull-right"
							data-toggle="modal" data-target="#formPersonAssignation">
							<span class="glyphicon glyphicon-plus"></span> 
							Asignar persona
						</button>
					</div>
				</div>
			
				<div class="row">
					<div class="form-group col-md-offset-1 col-md-10">
					<table id="profilesAssignTable" class="table table-bordered"	
						   data-toggle="table" data-height="162"  data-click-to-select="true" 
							data-single-select="true">
						<thead>
							<tr>
								<th class="col-md-1"></th>
								<th class="col-md-3">Persona</th>
								<th class="col-md-3">Perfil</th>
								<th class="col-md-1">Exp desde</th>
								<th class="col-md-1"><span class="glyphicon glyphicon-euro"></span>/h</th>
								<th class="col-md-1"><span class="glyphicon glyphicon-euro"></span>/h extra</th>
								<th class="col-md-2">Acción</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="hPerson" items="${hPersonsAssigned}">
							<tr>
								<td>
									<button id="infoAsignmtPerson" type="button"
										class="btn btn-info btn-xs center-block"
										onclick="loadAssignPerson('${hPerson.id}','${hPerson.namePerson}')">
										<span class="glyphicon glyphicon-info-sign"></span>
									</button>
								</td>
								<td>${hPerson.namePerson}</td>
								<td>${hPerson.nameProfCatg}</td>
								<td>${hPerson.ini}</td>
								<td>${hPerson.sal}</td>
								<td>${hPerson.salExtra}</td>
								<td>
									<table>
										<tr>
											<td>
												<button id="deleteAsignmtProfile" type="button"
												class="btn btn-success btn-xs center-block"
												data-toggle="modal" data-target="#concludePerson"
												data-id="${hPerson.id}" data-name="${hPerson.namePerson}">
												<span class="glyphicon glyphicon-ok"></span>
												</button>
											</td>
											<td>
												<button id="deleteAsignmtProfile" type="button"
												class="btn btn-danger btn-xs center-block"
												data-toggle="modal" data-target="#confirmDeletePerson"
												data-id="${hPerson.id}" data-name="${hPerson.namePerson}">
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
				
				<br>
				<div class="row">
					<div class="form-group col-md-12">
						<h4 class="subtitle">
							Horas y coste total<br> 
							<small>
								Una vez que la persona seleccionada concluye su trabajo obtenemos lo siguiente
							</small>
						</h4>
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-md-offset-2 col-md-8">
					<table id="tableAssignmtPersons" class="table table-bordered" data-toggle="table">
						<thead>
							<tr>
								<th class="col-md-4"  data-field="person"     >Persona</th>
								<th class="col-md-2"  data-field="conclude"   >Trabajo finalizado</th>
								<th class="col-md-2"  data-field="hours"      >Horas</th>
								<th class="col-md-2"  data-field="extrahours" >Horas extra</th>
								<th class="col-md-2"  data-field="cost"       >Coste</th>
							</tr>
						</thead>
					</table>
					</div>
				</div>
				
				<br><br>
			</div>
		</div>
		
		
	<!-- Modal: Assignation of a new Profile Category
	===================================================================================================== -->
		<div class="modal fade modal" id="formProfileAssignation" role="dialog">
			<div class="modal-dialog modal-lg">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Asignación de un nuevo perfil</h4>
					</div>

					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Selección de perfil<br> 
									<small>
										Asigna a la tarea un perfil previsto para llevar a cabo el trabajo
									</small>
								</h4>
							</div>
						</div>
						
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
							<div class="panel panel-default">
                        	<div class="panel-body table-responsive">
							<table id="profilesTable" class="table table-bordered"	
							data-toggle="table"  data-click-to-select="true" 
							data-single-select="true">
								<thead>
									<tr>
										<th class="col-md-1" data-field="state" data-checkbox="true"></th>
										<th class="col-md-1">ID</th>									
										<th class="col-md-4">Nombre del perfil</th>
										<th class="col-md-1">Exp. </th>
										<th class="col-md-3">Nivel</th>
										<th class="col-md-1"><span class="glyphicon glyphicon-euro"></span>/h</th>
										<th class="col-md-1"><span class="glyphicon glyphicon-euro"></span>/h extra</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="profcatg" items="${profcatgs}">
								<tr>
									<td></td>
									<td class="text-info">${profcatg.id}</td>
									<td>${profcatg.name}</td>
									<td>${profcatg.minExp}</td>
									<td>${profcatg.level}</td>
									<td align="center">${profcatg.sal}</td>
									<td align="center"> ${profcatg.salExtra}</td>
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
									Datos relacionados<br> 
									<small>
										Establece el nº de personas previstas que ocuparán ese perfil y las horas
										que le dedicará cada uno
									</small>
								</h4>
							</div>
						</div>
					
						<form:form id="assignAPForm" class="form" method="post" modelAttribute="asignmtProf" role="form" 
						action='/projects/${idProject}/phases/${idPhase}/tasks/${idTask}/profiles' 
						data-toggle="validator">

							<!-- First row -->
							<div class="form-group">
								<div class="row">
									<input type="hidden" name="idTask" id="idTask" value="${idTask}">
									<input type="hidden" name="idProfCatg" id="idProfCatg">
									<input type="hidden" name="cost" id="cost" value="0">
								
									<div class="form-group col-md-offset-3 col-md-3">
										<label for="inputName" class="control-label">Nº personas</label> 
										<input type="text" class="form-control"
									   		   name="units" required>
									</div>

									<div class="form-group col-md-3">
										<label for="inputSurname1" class="control-label">Horas/persona</label> 
										<input type="text" class="form-control"
									   		   name="hoursPerPerson" required>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
								<button id="assignProfileButton" type="submit"
								 class="btn btn-primary center-block" disabled >
									Asignar
								</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		
		
	<!-- Modal: Assignation of a new Person in the Task
	===================================================================================================== -->
		<div class="modal fade modal" id="formPersonAssignation" role="dialog">
			<div class="modal-dialog modal-lg">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Asignación de persona</h4>
					</div>

					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Selección de persona<br> 
									<small>
										Asigna a una persona para trabajar en la tarea durante su ejecución
									</small>
								</h4>
							</div>
						</div>
						
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
							<div class="panel panel-default">
                        	<div class="panel-body table-responsive">
							<table id="personsTable" class="table table-bordered"	
							data-toggle="table"  data-click-to-select="true" 
							data-single-select="true">
								<thead>
									<tr>
										<th class="col-md-1" data-field="state" data-checkbox="true"></th>
										<th class="col-md-1">ID</th>									
										<th class="col-md-3">Nombre</th>
										<th class="col-md-3">Perfil</th>
										<th class="col-md-2">Exp. desde</th>
										<th class="col-md-1"><span class="glyphicon glyphicon-euro"></span>/h</th>
										<th class="col-md-1">Extra</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="hperson" items="${hpersons}">
								<tr>
									<td></td>
									<td class="text-info">${hperson.id}</td>
									<td>${hperson.namePerson}</td>
									<td>${hperson.nameProfCatg}</td>
									<td>${hperson.ini}</td>
									<td align="center">${hperson.sal}</td>
									<td align="center"> ${hperson.salExtra}</td>
								</tr>
								</c:forEach>
								</tbody>
							</table>
							</div>
							</div>
							</div>
						</div>
					
						<form:form id="assignAPForm" class="form" method="post" modelAttribute="asignmtPerson"
						role="form" action='/projects/${idProject}/phases/${idPhase}/tasks/${idTask}/persons'>

							<div class="form-group">
								<div class="row">
									<input type="hidden" name="idTask" id="idTask" value="${idTask}">
									<input type="hidden" name="idHistoryPerson" id="idHPerson">
								</div>
								
								<div class="row">
									<button id="assignPersonButton" type="submit"
									 class="btn btn-primary center-block" disabled >
										Asignar
									</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>

			</div>
		</div>
		
		
	<!-- Modal: Update Assignment Profile
	===================================================================================================== -->
		<div class="modal fade" id="updateAsignmtProfile" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualización del perfil</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Información sobre el perfil<br> 
									<small>
										Cambia el número de personas previstas y necesarias para llevar a
										cabo la 
									</small>
									<small>
										tarea, así como las horas dedicadas por cada una.
									</small>
								</h4>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-12">
								<form:form class="form" id="updateAssignmtProfForm" method="post" action=''
									modelAttribute="asignmntProf" role="form" data-toggle="validator">
									
									<div class="form-group">
										<div class="row">
											<input type="hidden" name="id" id="idAsignmtProf">
										
											<div class="form-group col-md-offset-3 col-md-3">
												<label for="inputName" class="control-label">Nº de personas</label>
												<input type="text" class="form-control"	name="units" id="units">
											</div>
											
											<div class="form-group col-md-3">
												<label for="inputName" class="control-label">Horas/Persona</label>
												<input type="text" class="form-control"
													name="hoursPerPerson" id="hoursPerPerson">
											</div>
										</div>
									</div>
		
									<div class="form-group">
										<div class="row">
											<button id="addButton" type="submit" 
											class="btn btn-primary center-block">
												Cambiar
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
		
		
	<!-- Modal: Confirmation of delete of Profile
	===================================================================================================== -->
		<div class="modal fade" id="confirmDeleteProfile" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Confirmación de desasignación</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<p id="msgProfile" align="center"></p>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="row center-block">
							<div class="col-md-offset-3 col-md-3">
								<form id="confirmDeleteProfileButton" name="confirmDeleteButton" action='' method="post">
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
		
		
	<!-- Modal: Experience feedback modal
	===================================================================================================== -->
		<div class="modal fade" id="infoExp" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Información de columna</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12 center-block">
								Esta columna representa la <b>experiencia mínima</b> necesaria de un determinado
								perfil profesional. Dicha experiencia se representa en años.
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary center-block"
							data-dismiss="modal">Cerrar</button>
					</div>
				</div>
			</div>
		</div>
			
	
	<!-- Modal: Conclude Person
	===================================================================================================== -->
		<div class="modal fade" id="concludePerson" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Concluír trabajo</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<p id="msgConcludePerson" align="center"></p>
							</div>
						</div>
						
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<div class="alert alert-warning">
								<strong>Atención:</strong> Finalización del trabajo y cálculo de las
								horas y costes totales. Concluida la labor no podremos asignar
								a la persona más trabajo en esta tarea.
							</div>
						</div>
					</div>
					</div>
					<div class="modal-footer">
						<div class="row center-block">
							<div class="col-md-offset-3 col-md-3">
								<form id="concludePersonButton" action='' method="post">
									<button type="submit" class="btn btn-success btn-block">Si</button>
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
	
	
	<!-- Modal: Confirmation of delete Person
	===================================================================================================== -->
		<div class="modal fade" id="confirmDeletePerson" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Borrado de asignación</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<p id="msgPerson" align="center"></p>
							</div>
						</div>
						
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<div class="alert alert-warning">
								<strong>Atención:</strong> Esto supone el borrado en cascada de los registros 
								de carga diaria de esa persona en la tarea.
							</div>
						</div>
					</div>
					</div>
					<div class="modal-footer">
						<div class="row center-block">
							<div class="col-md-offset-3 col-md-3">
								<form id="confirmDeletePersonButton" action='' method="post">
									<button type="submit" class="btn btn-danger btn-block">Si</button>
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
	
	
	<!-- Dynamic loads
	===================================================================================================== -->
	<script>
	
		// PROFILES
		//----------------------------------------------------------------------------------------
		var $tableAssigProf = $('#tableProfiles');	
	
		/* Load the data when the Profile selected has changed */
		function loadAssignProf(idProfCatg,nameProfile) {
			$tableAssigProf.bootstrapTable('load', loadDataAssignProf(idProfCatg,nameProfile));
		}	    
	    
	    function loadDataAssignProf(id,nameProfile) {
	    	
	        var rows = [];
	        var nAssignment = ${assignmtProfJson}.length;
	        
	        for (var i = 0; i < nAssignment; i++) {
	        	if (${assignmtProfJson}[i].idProfCatg == id) {
		            rows.push({
		            	profile:nameProfile,
		                units: ${assignmtProfJson}[i].units,
		                personhours: ${assignmtProfJson}[i].personhours,
		                totalhours: ${assignmtProfJson}[i].totalhours,
		                cost: ${assignmtProfJson}[i].cost
		            });
	        	}
	        }
	        return rows;
	    }
	    
	    
		// PERSONS
		//----------------------------------------------------------------------------------------
		var $tableAssigPerson = $('#tableAssignmtPersons');	
		
		/* Load the data when the Person selected has changed */
		function loadAssignPerson(idHPerson, namePerson) {
			$tableAssigPerson.bootstrapTable('load', loadDataAssignPerson(idHPerson,namePerson));
		}	    
	    
	    function loadDataAssignPerson(id,namePerson) {
	    	
	        var rows = [];
	        var nAssignment = ${assignmtPersonJson}.length;
	        var idAssigPerson = "0";
	        
	        for (var i = 0; i < nAssignment; i++) {	        	
	        	if (${assignmtPersonJson}[i].idHPerson == id) {
		            rows.push({
		            	person:namePerson,
		            	conclude: ${assignmtPersonJson}[i].conclude,
		                hours: ${assignmtPersonJson}[i].hours,
		                extrahours: ${assignmtPersonJson}[i].extraHours,
		                cost: ${assignmtPersonJson}[i].cost
		            });
	        	}
	        }
	        return rows;
	    }
	</script>
	
	
	<!-- Enable in the modal, the assign button when the 
	 	 user selects a ProfileCategory at the Task. 
	===================================================================================================== -->
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#profilesTable').change(function() { 
			   
			   // Get info of the table
			   var json = JSON.parse(JSON.stringify($('#profilesTable').bootstrapTable('getSelections')));
			   var size = json.length;
			   
			   // If a option is selected then we can add a Person as manager
			   if (size > 0) {
				   var idProfile = json['0']['1'];
				   document.getElementById("idProfCatg").value = idProfile;
				   document.getElementById("assignProfileButton").disabled = false;
			   } 
			   else {
				   document.getElementById("assignProfileButton").disabled = true;
			   }		        
		   }); 
		});
	</script>
	
	
	<!-- Enable the assign button when the user selects a HistoryPerson at the Task. 
	===================================================================================================== -->
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#personsTable').change(function() { 
			   
			   // Get info of the table
			   var json = JSON.parse(JSON.stringify($('#personsTable').bootstrapTable('getSelections')));
			   var size = json.length;
			   			   
			   // If a option is selected then we can add a Person as manager
			   if (size > 0) {
				   var idPerson = json['0']['1'];

				   document.getElementById("idHPerson").value = idPerson;
				   document.getElementById("assignPersonButton").disabled = false;
			   } 
			   else {
				   document.getElementById("assignPersonButton").disabled = true;
			   }		        
		   }); 
		});
	</script>
	
	
	<!-- Data transfer to the modal updateAsignmtProfile
	===================================================================================================== -->
	<script type='text/javascript'>
		$(function() {
			$('#updateAsignmtProfile').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var idProfile = button.data('id');
						
				// Get the data of the assignmentProfile
				var units = 0;
				var personhours = 0;
				var idAsignmtProf = 0;
				var nAssignment = ${assignmtProfJson}.length;
				
				for (var i = 0; i < nAssignment; i++) {
		        	if (${assignmtProfJson}[i].idProfCatg == idProfile) {
		        		idAsignmtProf = ${assignmtProfJson}[i].id;
			        	units = ${assignmtProfJson}[i].units;
			   			personhours = ${assignmtProfJson}[i].personhours;
		        	}
		        }
				
				// Set the url
				var url = "/projects/" + ${idProject} + "/phases/" +  ${idPhase} + "/tasks/" + 
				  ${idTask} + "/profiles/" + idAsignmtProf + "/update";
				  
				// Set the values at the modal components
				document.getElementById('updateAssignmtProfForm').action = url;
				document.getElementById('units').value = units;
				document.getElementById('hoursPerPerson').value = personhours;
				document.getElementById('idAsignmtProf').value = idAsignmtProf;
			})
		});
	</script>
	
	
	<!-- Data transfer to the modal confirmDeleteProfile
	===================================================================================================== -->
	<script type='text/javascript'>
		$(function() {
			$('#confirmDeleteProfile').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var idProfile = button.data('id');
				var nameProfile = button.data('name');
						  
				// Get the data of the assignmentProfile
				var idAsignmtProf = 0;
				var nAssignment = ${assignmtProfJson}.length;
							
				for (var i = 0; i < nAssignment; i++) {
			       	if (${assignmtProfJson}[i].idProfCatg == idProfile) {
			       		idAsignmtProf = ${assignmtProfJson}[i].id;
					}
				}
				
				// Set the message and the url
				var msg = "¿Seguro que desea desasignar el perfil de "
						  + nameProfile.bold() + " de la planificación de la tarea?";

				var url = "/projects/" + ${idProject} + "/phases/" +  ${idPhase} + "/tasks/" + 
				  ${idTask} + "/profiles/" + idAsignmtProf + "/delete";
				  
						
				// Set the values at the modal components
				document.getElementById('msgProfile').innerHTML = msg;
				$('#confirmDeleteProfileButton').get(0).setAttribute('action', url);
			})
		});
	</script>
	
	
	<!-- Data transfer to the modal concludePerson
	===================================================================================================== -->
	<script type='text/javascript'>
		$(function() {
			$('#concludePerson').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var idPerson = button.data('id');
				var namePerson = button.data('name');
						  
				// Get the data of the assignmentProfile
				var idAsignmtPerson = 0;
				var nAssignment = ${assignmtPersonJson}.length;
							
				for (var i = 0; i < nAssignment; i++) {
			       	if (${assignmtPersonJson}[i].idHPerson == idPerson) {
			       		idAsignmtPerson = ${assignmtPersonJson}[i].id;
					}
				}
				
				// Set the message and the url
				var msg = "¿Seguro que desea finalizar el trabajo de " + namePerson.bold() + " en esta tarea?";

				var url = "/projects/" + ${idProject} + "/phases/" +  ${idPhase} + "/tasks/" + 
				  ${idTask} + "/persons/" + idAsignmtPerson + "/conclude";
				  
						
				// Set the values at the modal components
				document.getElementById('msgConcludePerson').innerHTML = msg;
				$('#concludePersonButton').get(0).setAttribute('action', url);
			})
		});
	</script>
	
	
	<!-- Data transfer to the modal confirmDeletePerson
	===================================================================================================== -->
	<script type='text/javascript'>
		$(function() {
			$('#confirmDeletePerson').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var idPerson = button.data('id');
				var namePerson = button.data('name');
						  
				// Get the data of the assignmentProfile
				var idAsignmtPerson = 0;
				var nAssignment = ${assignmtPersonJson}.length;
							
				for (var i = 0; i < nAssignment; i++) {
			       	if (${assignmtPersonJson}[i].idHPerson == idPerson) {
			       		idAsignmtPerson = ${assignmtPersonJson}[i].id;
					}
				}
				
				// Set the message and the url
				var msg = "¿Seguro que desea desasignar a " + namePerson.bold() + " de la ejecución de la tarea?";

				var url = "/projects/" + ${idProject} + "/phases/" +  ${idPhase} + "/tasks/" + 
				  ${idTask} + "/persons/" + idAsignmtPerson + "/delete";
				  
						
				// Set the values at the modal components
				document.getElementById('msgPerson').innerHTML = msg;
				$('#confirmDeletePersonButton').get(0).setAttribute('action', url);
			})
		});
	</script>

</body>
</html>
