
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

/* Left padding for subtitles */
.subtitle {
    padding-left: 12px;
}

/* poner color a los hr */
hr { border-color: grey }

/* sangria de los small */
small {
    margin-left: 15px;
}

/* Neccesary to adapt a table in the modal */
.table-responsive {
    max-height:150px;
    padding: 0px;
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
				<li class="active"><a href="#"><font size="3">Tarea</font></a></li>
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
									<a href="#" class="active">Carga de trabajo 
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

			<!-- Interface Project information
    		================================================== -->
			<div class="col-md-9">
			
				<h3>
					Carga de trabajo<br> 
					<small>
						Horas trabajadas diariamente por las distintas personas vinculadas a la tarea
					</small>
				</h3>
				<hr width="110%">
				<br>
				
				<div class="row">
					<div class="form-group col-md-10">
						<h4 class="subtitle">
							Selecciona un día concreto y luego a las personas de las que desees obtener su carga de trabajo<br> 
						</h4>
					</div>
				</div>
			
				
			<!-- Calendar and Person table
    		================================================== -->
				<div class="row">
					<div class="col-md-3">
						<div id="calendar" class="datepicker"></div>
					</div>
					
					<div class="col-md-offset-1 col-md-8">
						<table id="personTable" class="table table-bordered"	
						data-toggle="table" data-height="229" data-click-to-select="true">
						<thead>
							<tr>
								<th class="col-md-1" data-field="state" data-checkbox="true"></th>
								<th class="col-md-1">ID</th>
								<th class="col-md-4">Persona</th>
								<th class="col-md-4">Perfil</th>
								<th class="col-md-1"><span class="glyphicon glyphicon-euro"></span>/h</th>
								<th class="col-md-1"><span class="glyphicon glyphicon-euro"></span>/h extra</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="hPerson" items="${assignedPersons}">
							<tr>
								<td></td>
								<td class="text-info">${hPerson.id}</td>
								<td>${hPerson.namePerson}</td>
								<td>${hPerson.nameProfCatg}</td>
								<td>${hPerson.sal}</td>
								<td>${hPerson.salExtra}</td>
								
							</tr>
							</c:forEach>
						</tbody>
						</table>
					</div>
				</div>
				
				<div class="row">
					<div class=" col-md-3">
						<button id="reset-date" type="button" class="btn btn-primary center-block"
							data-toggle="modal" data-target="#formProfileAssignation">
							<span class="glyphicon glyphicon-refresh"></span> 
							Reset
						</button>
					</div>
				</div>
				
				<!-- Workload table
    			================================================== -->
    			<br><br><br>
				<div class="row">
					<div class="form-group col-md-offset-1 col-md-4 pull-left">
						<h4 class="subtitle">
							Carga de trabajo<br> 
						</h4>
					</div>
					<div class="col-md-offset-2 col-md-1">
						<button type="button" class="btn btn-success pull-right"
							data-toggle="modal" data-target="#createWorkload">
							<span class="glyphicon glyphicon-plus"></span> 
						</button>
					</div>
					<div class="col-md-1">
						<button type="button" id="updateWorkloadButton" class="btn btn-primary pull-right" 
								data-toggle="modal"	data-target="#updateWorkload" disabled>
							<span class="glyphicon glyphicon-edit"></span> 
						</button>
					</div>
					<div class="col-md-1">
						<button type="button" id="deleteWorkloadButton" class="btn btn-danger pull-right"	
								data-toggle="modal" data-target="#deleteWorkload" disabled>
							<span class="glyphicon glyphicon-remove"></span> 
						</button>
					</div>
				</div>
			
				<div class="row">
					<div class="form-group col-md-offset-2 col-md-8">
					<table id="workloadTable" class="table table-bordered"	
						   data-toggle="table" data-height="229" data-single-select="true">
						<thead>
							<tr>
								<th class="col-md-1" data-checkbox="true" data-field="check"></th>
								<th class="col-md-1" data-field="id"        >ID</th>
								<th class="col-md-2" data-field="day"       >Día</th>
								<th class="col-md-4" data-field="person"    >Persona</th>
								<th class="col-md-2" data-field="hours"     >Horas</th>
								<th class="col-md-2" data-field="extrahours">H. extra</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="workload" items="${workloads}">
							<tr>
								<td></td>
								<td class="text-info">${workload.id}</td>
								<td>${workload.day}</td>
								<td>${workload.personName}</td>
								<td>${workload.hours}</td>
								<td>${workload.extraHours}</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
				</div>

				<br><br>
			</div>
		</div>
		
		
	<!-- Modal: Creation of a new Workload
	===================================================================================================== -->
		<div class="modal fade modal" id="createWorkload" role="dialog">
			<div class="modal-dialog modal-lg">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Creación de carga de trabajo</h4>
					</div>

					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Selección de persona<br> 
									<small>
										Selecciona a una persona para asignarle carga de trabajo
									</small>
								</h4>
							</div>
						</div>
						
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
							<div class="panel panel-default">
                        	<div class="panel-body table-responsive">
							<table id="personTable2" class="table table-bordered"	
							data-toggle="table" data-click-to-select="true" data-single-select="true">
							<thead>
								<tr>
									<th class="col-md-1" data-field="state" data-checkbox="true"></th>
									<th class="col-md-1">ID</th>
									<th class="col-md-4">Persona</th>
									<th class="col-md-4">Perfil</th>
									<th class="col-md-1"><span class="glyphicon glyphicon-euro"></span>/h</th>
									<th class="col-md-1"><span class="glyphicon glyphicon-euro"></span>/h extra</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="hPerson" items="${assignedPersons}">
								<tr>
									<td></td>
									<td class="text-info">${hPerson.id}</td>
									<td>${hPerson.namePerson}</td>
									<td>${hPerson.nameProfCatg}</td>
									<td>${hPerson.sal}</td>
									<td>${hPerson.salExtra}</td>
									
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
										Establece el día y el número de horas de carga de trabajo
									</small>
								</h4>
							</div>
						</div>
					
						<form:form id="assignAPForm" class="form" method="post" modelAttribute="workload"
						role="form" action='/projects/${idProject}/phases/${idPhase}/tasks/${idTask}/workloads'
						data-toggle="validator">

							<!-- First row -->
							<div class="form-group">
								<div class="row">								
									<div class="form-group col-md-offset-3 col-md-2">
										<label for="inputName" class="control-label">Día</label> 
										<input type="text" class="form-control datepicker"
									   data-format="dd/MM/yyyy" placeholder="dd/mm/aaaa" name="dayDate" required>
									</div>

									<div class="form-group has-feedback col-md-2">
										<label for="inputSurname1" class="control-label">Horas</label> 
										<div class="right-inner-addon">
											<input type="text" class="form-control" placeholder="0-8"
										   		   name="hours" pattern="^[0-8]\d*$" maxlength="1" required>
										   	<span class="glyphicon form-control-feedback"></span>
										</div>
									</div>
									
									<div class="form-group has-feedback col-md-2">
										<label for="inputSurname1" class="control-label">Horas extra</label> 
										<div class="right-inner-addon">
											<input type="text" class="form-control" placeholder="0-8"
										   		   name="extraHours" pattern="^[0-8]\d*$" maxlength="1" required>
										   	<span class="glyphicon form-control-feedback"></span>
										</div>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
									<input type="hidden" name="idTask" id="idTask" value="${idTask}">
									<input type="hidden" name="idHPerson" id="idHPerson">
								</div>
								
								<div class="row">
									<button id="createWorkloadButton" type="submit"
									 class="btn btn-primary center-block" disabled >
										Crear
									</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		
	
	<!-- Modal: Update a Workload
	===================================================================================================== -->
		<div class="modal fade modal" id="updateWorkload" role="dialog">
			<div class="modal-dialog">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualización de carga de trabajo</h4>
					</div>

					<div class="modal-body">
										
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Datos relacionados<br> 
									<small>
										Cambia el número de horas de carga de trabajo asignada a esta persona
									</small>
								</h4>
							</div>
						</div>
					
						<form:form id="updateWorkloadForm" class="form" method="post" modelAttribute="workload"
						role="form" action='' data-toggle="validator">

							<!-- First row -->
							<div class="form-group">
								<div class="row">								
									<div class="form-group col-md-offset-1 col-md-4">
										<label for="inputName" class="control-label">Día</label> 
										<input type="text" class="form-control datepicker" id="dayUpdate"
									   data-format="dd/MM/yyyy" placeholder="dd/mm/aaaa" name="dayDate" readOnly>
									</div>

									<div class="form-group has-feedback col-md-3">
										<label for="inputSurname1" class="control-label">Horas</label> 
										<div class="right-inner-addon">
											<input type="text" class="form-control" id="hoursUpdate"
										   		   name="hours" pattern="^[0-8]\d*$" maxlength="1" required>
										   	<span class="glyphicon form-control-feedback"></span>
										</div>
									</div>
									
									<div class="form-group has-feedback col-md-3">
										<label for="inputSurname1" class="control-label">Horas extra</label> 
										<div class="right-inner-addon">
											<input type="text" class="form-control" id="extraHoursUpdate"
										   		   name="extraHours" pattern="^[0-8]\d*$" maxlength="1" required>
										   	<span class="glyphicon form-control-feedback"></span>
										</div>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
									<input type="hidden" name="idTask" id="idTask" value="${idTask}">
									<input type="hidden" name="idHPerson" id="idHPerson">
								</div>
								
								<div class="row">
									<button id="createWorkloadButton" type="submit"
									 class="btn btn-primary center-block">
										Crear
									</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		
		
	<!-- Modal: Confirmation of delete Workload
	===================================================================================================== -->
		<div class="modal fade" id="deleteWorkload" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Borrado de carga de trabajo</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<p align="center">
									¿Seguro que desea eliminar esta carga de trabajo?
								</p>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="row center-block">
							<div class="col-md-offset-3 col-md-3">
								<form id="confirmDeleteWorkloadButton" action='' method="post">
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
		
		<!-- Modal: Feedback modal
    	================================================== -->
		<div class="modal fade" id="feedbackModal" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Operación exitosa</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12 center-block">${msg}</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success center-block"
							data-dismiss="modal">Aceptar</button>
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
		var forbidden=['14-11-2016']

		$(document).ready(function() {
			$('.datepicker').datepicker({
				language : 'es',
				inline: true,
                sideBySide: true,
                autoclose: true,
                datesDisabled: forbidden,
                daysOfWeekDisabled: [0,6],
                
                beforeShowDay: function(date) {
                    var hilightedDays = [1,3,8,20,21,16,26,30];
                    if (~hilightedDays.indexOf(date.getDate())) {
                       return {classes: 'highlight', tooltip: 'Title'};
                    }
                 }
      
			})
	        ;

		})
	</script>
	
	<!-- Script that fires when the window is loaded
    ================================================== -->
	<script type="text/javascript">
		$(window).load(function() {
			if ('${feedback}' == "active") {
				$('#feedbackModal').modal('show');
			}
		});
	</script>
	
	
	<!-- Dynamic loads of workload
	===================================================================================================== -->
	<script>

		var $table = $('#workloadTable');	
		
		// PERSON TABLE CHANGES
		//------------------------------------------------------------------------------------------------
		$(document).ready(function() { 
			$('#personTable').change(function() {    
				thereAreChanges();			        
			}); 
		});
		
		// CALENDAR CHANGES
		//------------------------------------------------------------------------------------------------
		$(document).ready(function () {
			$('#calendar').datepicker().on('changeDate', function (ev) {
				thereAreChanges();
			});
		});
		
		$(document).ready(function() { 
			$("#reset-date").click(function(){
				$('#calendar').val('').datepicker('update');
				thereAreChanges();
			})
		});
		
		// DYNAMIC LOAD
		//------------------------------------------------------------------------------------------------
		function thereAreChanges() {
			// Get info of the table
			var json = JSON.parse(JSON.stringify($('#personTable').bootstrapTable('getSelections')));

		  	// Load dynamicly the data
		  	if (json.length > 0) {
		  		$table.bootstrapTable('load', loadDataWorkload(json));
		  	} 
		  	
		  	// If there arent selections the table is empty
		  	else {
		  		$table.bootstrapTable('removeAll');	
		  	}
		}
		
		
	    function loadDataWorkload(json) {
	    	
	    	// Init variables
	        var rows = [];
	        var nWorkloads = ${workloads}.length;
	        
	        // Get the select day
	        var calendarDay = $("#calendar").data("datepicker").getDate();
	        var day = null;
	        
	        if (calendarDay != null) {
	        	numberDay = calendarDay.getDate();
	        	if (numberDay < 10) {
	        		numberDay = "0" + numberDay;
	        	}
	        	
	        	numberMonth = (calendarDay.getMonth() + 1);
	        	if (numberMonth < 10) {
	        		numberMonth = "0" + numberMonth;
	        	}
	        	
				day = numberDay + "/" + numberMonth + "/" + calendarDay.getFullYear();
	        }
	        
	        for (var i = 0; i < nWorkloads; i++) {
	        	for (j = 0; j < json.length; j++) {
					var idHPerson = json[j]['1'];
									   
		        	if (${workloads}[i].idHPerson == idHPerson) {
		        		//alert("PickerDay: " + day + " || WorkDay: " + ${workloads}[i].day)
		        		if (day==null || ${workloads}[i].day==day)
			            rows.push({
			            	check: false,
			            	id:${workloads}[i].id,
			                day: ${workloads}[i].day,
			                person:${workloads}[i].personName,
			                hours: ${workloads}[i].hours,
			                extrahours: ${workloads}[i].extraHours
			            });
		        	} 
	        	}
	        }
	        return rows;
	    } 
	</script>
	
	
	<!-- Enable the create button when the user selects a HistoryPerson at the Task. 
	===================================================================================================== -->
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#personTable2').change(function() { 
			   
			   // Get info of the table
			   var json = JSON.parse(JSON.stringify($('#personTable2').bootstrapTable('getSelections')));
			   var size = json.length;
			   			   
			   // If a option is selected then we can add a Person as manager
			   if (size > 0) {
				   var idPerson = json['0']['1'];

				   document.getElementById("idHPerson").value = idPerson;
				   document.getElementById("createWorkloadButton").disabled = false;
			   } 
			   else {
				   document.getElementById("createWorkloadButton").disabled = true;
			   }		        
		   }); 
		});
	</script>
	
	
	<!-- Enable the edit/remove button of the workload table. 
	===================================================================================================== -->
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#workloadTable').change(function() { 
			   
			   	// Get info of the table
			   	var json = JSON.parse(JSON.stringify($('#workloadTable').bootstrapTable('getSelections')));
			   	var size = json.length;
			   			
			   	var data = $('#workloadTable').bootstrapTable('getSelections');
			   	var id = $.map(data, function (item) {
				    return item.id;
				});
			   	
			   	// If a option is selected then we can add a Person as manager
			  	if (id != "") {
			  		
				   	// Create the urls
					var urlUpdate =  "/projects/" + ${idProject} + "/phases/" + ${idPhase} + "/tasks/" + 
						${idTask} + "/workloads/" + id + "/update";
					  
					var urlDelete =  "/projects/" + ${idProject} + "/phases/" + ${idPhase} + "/tasks/" + 
						${idTask} + "/workloads/" + id + "/delete";
						
					// Get the data for the update modal
					var day = $.map(data, function (item) { return item.day; });
					var hours = $.map(data, function (item) { return item.hours; });
					var extraHours = $.map(data, function (item) { return item.extrahours; });
					
							
					// Set the values at the modal components
					document.getElementById("updateWorkloadForm").action = urlUpdate;
					$('#confirmDeleteWorkloadButton').get(0).setAttribute('action', urlDelete);
					
					document.getElementById("dayUpdate").value = day;
					document.getElementById("hoursUpdate").value = hours;
					document.getElementById("extraHoursUpdate").value = extraHours;
					
					// Enable the buttons
				   	document.getElementById("updateWorkloadButton").disabled = false;
				   	document.getElementById("deleteWorkloadButton").disabled = false;
			   	} 
			   	else {
				   	document.getElementById("updateWorkloadButton").disabled = true;
				   	document.getElementById("deleteWorkloadButton").disabled = true;
			   	}		        
		   }); 
		});
	</script>

</body>
</html>
