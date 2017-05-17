
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<title>Project state</title>

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
									<a href="#" class="active"> Estado 
										<span class="glyphicon glyphicon-check pull-right"></span>
									</a>
								</li>
								<li>
									<a href="/projects/${idProject}/statics"> Estadísticas
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
			
			
				<!-- Current project state
    			================================================== -->
				<h3>
					Estado actual <br> 
					<small>Situación en la que se encuentra el proyecto actualmente</small>
				</h3>
				<hr width="110%">
				<br>

				<div class="row">
					<dl class="dl-horizontal">
						<dt><font size="3">Estado</font></dt>
						<dd><font size="3">${currentState.nameState}</font></dd>
					</dl>
					
					<dl class="dl-horizontal">
						<dt><font size="3">Descripción</font></dt>
						<dd><font size="3">${stateDescription}</font></dd>
					</dl>
					
					<dl class="dl-horizontal">
						<dt><font size="3">Período</font></dt>
						<dd><font size="3">${currentState.ini} - ${currentState.end}</font></dd>
					</dl>
				</div>
				
				
				<!-- History project state
    			================================================== -->
				<br>
				<h3>
					Históricos de estados<br> 
					<small>Listado de los estados por los que el proyecto ha pasado</small>
				</h3>
				<hr width="110%">
				<br>
				<div class="row">
					<div class="form-group col-md-offset-2 col-md-9">
						<table id="historyProjectTable" class="table table-bordered" data-toggle="table">
							<thead>
								<tr>
									<th class="col-md-1"></th>
									<th class="col-md-1">Estado</th>
									<th class="col-md-1">Inicio</th>
									<th class="col-md-1">Fin</th>
									<th class="col-md-1">Acción</th>
								</tr>
							</thead>
							<c:forEach var="state" items="${historyProject}">
								<tr>
									<td class="col-md-1"></td>
									<td id="idPerson" class="col-md-1 text-info">${state.nameState}</td>
									<td class="col-md-1">${state.ini}</td>
									<td class="col-md-1">${state.end}</td>
									<td>
										<button id="updateHPbutton" type="button"
											class="btn btn-info btn-xs center-block" data-toggle="modal"
											data-target="#updateHP" data-id="${state.id}"
											data-idstate="${state.idState}" data-name="${state.nameState}"
											data-ini="${state.ini}"	data-end="${state.end}"
											data-cmt="${state.comment}">
											<span class="glyphicon glyphicon-edit"></span>
										</button>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				
				<!-- Available operations
    			================================================== -->
				<br>
				<h3>
					Operaciones disponibles<br> 
				</h3>
				<hr width="110%">
				<div class="row">
					<div class="form-group col-md-7">
						<h4>
							Nuevo estado<br> 
							<small> 
								Finalización del estado actual del proyecto. Se realiza una transición a un estado 
							</small> 
							<small>
								compatible. Este cambio supone la actualización de determinados 								datos. 
							</small>
						</h4>
					</div>
				</div>

				<div class="row">
					<button id="changeStateButton" type="submit" name="page" class="btn btn-success center-block" 
					data-toggle="modal" data-target="#changeToPlan">
					<span class="glyphicon glyphicon-plus"></span> Nuevo estado
					</button>
				</div>
				
				<br>
				<div class="row">
					<div class="form-group col-md-6">
						<h4>
							Cancelación del proyecto<br> 
							<small>
								Suspensión total del seguimiento del proyecto. La cancelación es irreversible 
							</small>
							<small>
								 y una vez llevada a cabo ya no podremos modificar el proyecto.
							</small>
						</h4>
					</div>
				</div>
				
				<div class="row">
					<form action="/projects/${project.id}/states" method="post">
						<button type="submit" name="page" class="btn btn-danger center-block">
						<span class="glyphicon glyphicon-off"></span> Cancelar proyecto
						</button>
					</form>
				</div>
				
				<br><br>
			</div>
		</div>
		
		
		<!-- Modal: Update history project
    	================================================== -->
		<div class="modal fade" id="updateHP" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualización de histórico</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-offset-1 col-md-10">
								<h4 id="stateSelected">
									Estado<br>
								</h4>
								<hr>
							</div>
						</div>
						<div class="row">
							<div class="col-md-offset-1 col-md-10">
								<form:form id="editFormHP" class="form" method="post" action='url'
									modelAttribute="historyProject" role="form" data-toggle="validator">

									<!-- First row -->
									<div class="form-group">
										<div class="row">
											<input type="hidden" name="id" id="id">
											<input type="hidden" name="idState" id="idState">
											<div class="form-group col-md-6">
												<label class="control-label">Inicio</label>
												<input type="text" class="form-control datepicker"
													data-format="dd/MM/yyyy" name="ini" id="ini"
													placeholder="dd/mm/aaaa" required>
											</div>
											
											<div class="form-group col-md-6">
												<label class="control-label">Fin</label>
												<input type="text" class="form-control datepicker"
													data-format="dd/MM/yyyy" name="end" id="end"
													placeholder="dd/mm/aaaa" required>
											</div>
										</div>
									</div>

									<!-- Third row -->
									<div class="form-group">
										<div class="row">
											<div class="col-md-12">
												<label>Cometarios</label>
												<textarea class="form-control" rows="3" id="comment"
													name="comment" placeholder="Detalles a destacar..."></textarea>
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="row">
											<button id="updateButton" type="submit" disabled
													class="btn btn-primary center-block">
												Guardar cambios
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
		
		<!-- Modal: Change state to Planificacion
    	================================================== -->
		<div class="modal fade" id="changeToPlan" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Cambio de estado</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-9">
								<h4>
									Nuevo estado: <em>Planificación</em><br> 
									<small>
										Comienzo de la planificación del proyecto.
									</small>
								</h4>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-12">
								<form:form class="form" method="post" id="updateManagerForm"
								action='/projects/${idProject}/states/PLAN'
								modelAttribute="hproject" role="form" data-toggle="validator">

									<!-- First row -->
									<div class="form-group">
										<div class="row">
											<div class="form-group col-md-offset-4 col-md-4">
												<label for="inputName" class="control-label">Inicio planificación</label>
												<input type="text" class="form-control datepicker"
													data-format="dd/MM/yyyy" name="ini" id="ini"
													placeholder="dd/mm/aaaa" required>
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="row">
											<button type="submit" class="btn btn-primary center-block">
												Cambiar estado
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
		
		<!-- Modal: Change state to Ejecution
    	================================================== -->
		<div class="modal fade" id="changeToEjec" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Cambio de estado</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-9">
								<h4>
									Nuevo estado: <em>Ejecución</em><br> 
									<small>
										Para que pueda comenzar la ejecución del proyecto todas sus
									</small>
									<small>
										tareas deben estar preparadas o canceladas. Realizado el cambio 
									</small>
									<small>
										de estado, se calcularán automáticamente los datos previstos.
									</small>
								</h4>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-12">
								<form:form class="form" method="post" id="updateManagerForm"
								action='/projects/${idProject}/states/EJEC'
								modelAttribute="hproject" role="form" data-toggle="validator">

									<!-- First row -->
									<div class="form-group">
										<div class="row">
											<div class="form-group col-md-offset-4 col-md-4">
												<label for="inputName" class="control-label">Inicio ejecución</label>
												<input type="text" class="form-control datepicker"
													data-format="dd/MM/yyyy" name="ini" id="ini"
													placeholder="dd/mm/aaaa" required>
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="row">
											<button type="submit" class="btn btn-primary center-block">
												Cambiar estado
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
		
		
		<!-- Modal: Change state to Terminate
    	================================================== -->
		<div class="modal fade" id="changeToTerm" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Cambio de estado</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-9">
								<h4>
									Nuevo estado: <em>Terminado</em><br> 
									<small>
										Para poder concluir el proyecto todas sus tareas deben haberlo 
									</small>
									<small>
										hecho tambien. Realizado el cambio de estado, se calcularán
									</small>
									<small>
										 automáticamente los datos reales.
									</small>
								</h4>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-12">
								<form:form class="form" method="post" id="updateManagerForm"
								action='/projects/${idProject}/states/TERM'
								modelAttribute="hproject" role="form" data-toggle="validator">

									<!-- First row -->
									<div class="form-group">
										<div class="row">
											<div class="form-group col-md-offset-4 col-md-4">
												<label for="inputName" class="control-label">Fecha de finalización</label>
												<input type="text" class="form-control datepicker"
													data-format="dd/MM/yyyy" name="ini" id="ini"
													placeholder="dd/mm/aaaa" required>
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="row">
											<button type="submit" class="btn btn-primary center-block">
												Cambiar estado
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
		
		<!-- Modal: Feedback modal
    	================================================== -->
		<div class="modal fade" id="feedbackModal" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Error</h4>
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
	
	<!-- Bootstrap Datapicker initialation
    ================================================== -->	
	<script>
		$(document).ready(function() {
			$('.datepicker').datepicker({
				language : 'es'
			});
		})
	</script>
	
	<!-- Script that fires when the window is loaded
    ================================================== -->
	<script type="text/javascript">
		$(window).load(function() {
			
			if ('${currentIdState}' == "PLAN") {
				$('#changeStateButton').attr('data-target','#changeToEjec');
			}
			
			if ('${currentIdState}' == "EJEC") {
				$('#changeStateButton').attr('data-target','#changeToTerm');
			}
			
			if ('${currentIdState}' == "CANC" || '${task.idState}' == "TERM") {
				document.getElementById("changeStateButton").disabled = true;
			}
			
			if ('${feedback}' == "active") {
				$('#feedbackModal').modal('show');
			}
		});
	</script>
	
	<!-- Fires when the edit button is clicked
    ================================================== -->	
	<script type='text/javascript'>
		$(function() {
			$('#updateHP').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				var b = event.relatedTarget;
				
				// Only set the modal data if the event comes from the main edit botton
				if (b != null)
			    {		     	
					
					// Extract info from data attributes
					var id = button.data('id') 
					var state = button.data('idstate') 
					var name = button.data('name')
					var ini = button.data('ini')
					var end = button.data('end')
					var comment = button.data('cmt')		
					var url = "/projects/" + ${idProject} + "/states/" + id + "/update"
							
					// Set the values at the modal components
					document.getElementById('stateSelected').innerHTML = name
					document.getElementById('editFormHP').action = url
					document.getElementById('id').value = id
					document.getElementById('idState').value = state
					document.getElementById('ini').value = ini
					document.getElementById('end').value = end
					document.getElementById('comment').value = comment
					
					// If the state is not the current, then we only can update the comment
					if (end != "Actualidad") {
						document.getElementById('ini').readOnly = true
						document.getElementById('end').readOnly = true
					}
			    }
			})
		});
	</script>
	
	
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#updateHP').change(function() { 
		        document.getElementById("updateButton").disabled = false;
		        
		   }); 
		});
	</script>

</body>
</html>
