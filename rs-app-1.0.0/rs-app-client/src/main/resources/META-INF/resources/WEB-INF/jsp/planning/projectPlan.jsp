
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<title>Dashboard Template for Bootstrap</title>

<!-- Bootstrap core CSS -->
<link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap Datepicker -->
<link href="/webjars/bootstrap-datepicker/1.6.1/css/bootstrap-datepicker.min.css" rel="stylesheet">

<!-- Custom colors for the navigation bar -->
<link href="/css/navbarColors.css" rel="stylesheet">

<!-- Gantt chart  -->
<script src="/dhtmlxGantt/codebase/dhtmlxgantt.js"></script>
<link href="/dhtmlxGantt/codebase/dhtmlxgantt.css" rel="stylesheet">

<!-- Custom tabs for the add operationes -->
<link href="/css/customTabs.css" rel="stylesheet">

<style>
body {
	padding-top: 50px;
}

.weekend{ background: #f4f7f4 !important;}

.nested_task .gantt_add{
   display: none;
}

</style>

</head>

<body>
	<!-- Main navigation bar 
    ================================================== -->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#"><span class="glyphicon glyphicon-home"></span></a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#"><font size="3">Planificación</font></a></li>
				<li><a href="/projects/${idProject}"><font size="3">Proyecto</font></a></li>
				<li><a href="#"><font size="3">Tarea</font></a></li>
				<li><a href="#"><font size="3">Hito</font></a></li>
				<li><a href="#"><font size="3">Personas</font></a></li>
				<li><a href="/materials"><font size="3">Materiales</font></a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</nav>
	
	<!-- Content of the interface
    ================================================== -->
	<div class="container-fluid">
		<div class="row">
			<div id="gantt_here" style='width: 1366px; height: 580px;'></div>
		</div>


		<!-- Modal: Project element creation
    	================================================== -->
		<div id="creationModal" class="modal fade" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
				
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Creación de un nuevo elemento</h4>
					</div>

					<div class="modal-body">
						<div class="container">
							<div class="col-md-1">
								<ul class="nav nav-tabs tabs-left sideways">
									<li class="active"><a data-toggle="tab" href="#createPhase">Fase</a></li>
									<li><a data-toggle="tab" href="#createTask">Tarea</a></li>
									<li><a data-toggle="tab" href="#createMilestone">Hito</a></li>
								</ul>
							</div>
							<div class="col-md-10">
								<div class="tab-content">
								
									<!-- Phase creation form
    								================================================== -->
									<div id="createPhase" class="tab-pane fade in active">
										<h4>
											Fase<br> 
											<small>
												Etapa del proyecto que permite aglutinar una serie de tareas
											</small>
										</h4>
										<hr align="left" width="48%">
										<form:form class="form-horizontal" method="post" modelAttribute="phase"
												   action='/project/${idProject}/phase' role="form"
												   data-toggle="validator">
											<div class="form-group">
												<div class="row">
													<label class="col-md-2 control-label">Nombre</label>
													<div class="form-group col-md-3">
														<input class="form-control" name="name" id="namePhase" 
															   type="text" required>
													</div>
												</div>
											</div>
											<div class="form-group">
												<div class="row">
													<label class="col-md-2 control-label">Inicio previsto</label>
													<div class="form-group col-md-3">
														<input type="text" class="form-control datepicker"
														data-format="dd/MM/yyyy" name="ini" id="iniPhase"
														placeholder="dd/mm/aaaa" required>
													</div>
												</div>
											</div>
											<div class="form-group">
												<div class="row"><div class="col-md-6 center-block">
													<button type="submit" class="btn btn-primary center-block">
														Crear fase
													</button>
												</div></div>
											</div>
										</form:form>
									</div>
									
									<!-- Task creation form
    								================================================== -->
									<div id="createTask" class="tab-pane fade">
										<h4>
											Tarea<br> 
											<small>
												Unidad de trabajo realizada en un tiempo limitado
											</small>
										</h4>
										<hr align="left" width="48%">

										<form:form class="form-horizontal" method="post"
											modelAttribute="phase" action='/project/${idProject}/task'
											role="form" data-toggle="validator">
											
											<div class="form-group">
												<div class="row">
													<label class="col-md-2 control-label">Nombre</label>
													<div class="form-group col-md-3">
														<input class="form-control" name="name" id="nameNewTask"
															type="text" required>
													</div>
												</div>
											</div>

											<div class="form-group">
												<div class="row">
													<label class="col-md-2 control-label">Fase</label>
													<input type="hidden" name="idPhase" id="idPhaseT"
														   value="${phases[0].id}">
													<div class="form-group col-md-3">
														<select class="form-control" name="namePhase" 
																id="namePhaseNewTask">
															<c:forEach var="phase" items="${phases}">
																<option id="${phase.id}">${phase.name}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<div class="form-group">
												<div class="row">
													<label class="col-md-2 control-label">Duración</label>
													<div class="form-group has-feedback col-md-3">
														<input type="text" class="form-control" 
															   name="daysPlan" id="daysPlanNewTask"
															   pattern="^[1-9]\d*$" maxlength="3"
															   placeholder="Entre 1 y 999 días" required> <span
															   class="glyphicon form-control-feedback"></span>
													</div>
												</div>
											</div>
											
											<div class="form-group">
												<div class="row">
													<label class="col-md-2 control-label">Inicio previsto</label>
													<div class="form-group col-md-3">
														<input type="text" class="form-control datepicker"
														data-format="dd/MM/yyyy" name="iniPlan" id="iniPlanNewTask"
														placeholder="dd/mm/aaaa" required>
													</div>
												</div>
											</div>
											
											<div class="form-group">
												<div class="row">
													<label  class="col-md-2 control-label">Responsable</label> 
													<input type="hidden" name="idResponsible" id="idResponsible"
														   value="${persons[0].id}">
													<div class="form-group col-md-3">
														<select class="form-control" name="nameManager" 
																id="nameManagerNewTask">
															<c:forEach var="person" items="${persons}">
																<option id="${person.id}">${person.namePerson}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											
											<div class="form-group">
												<div class="row">
													<label  class="col-md-2 control-label">Prioridad</label> 
													<input type="hidden" name="idPriority" id="idPriorityNewTask"
														   value="B">
													<div class="form-group col-md-3">
														<select class="form-control" name="priority" id="priority">
															<option id="B">Baja</option>
															<option id="M">Media</option>
															<option id="A">Alta</option>
															<option id="MA">Muy alta</option>
														</select>
													</div>
												</div>
											</div>
											
											<div class="form-group">
												<div class="row">
													<div class="col-md-6 center-block">
														<button type="submit" class="btn btn-primary center-block">
															Crear tarea</button>
													</div>
												</div>
											</div>
										</form:form>
									</div>
									
									<!-- Milestone creation form
    								================================================== -->
									<div id="createMilestone" class="tab-pane fade">
										<h4>
											Hito<br> 
											<small> 
												Punto de referencia para marcar eventos importantes
												del proyecto
											</small>
										</h4>
										<hr align="left" width="48%">
										<form:form class="form-horizontal" method="post"
											modelAttribute="phase" action='/project/${idProject}/milestone'
											role="form" data-toggle="validator">
											<div class="form-group">
												<div class="row">
													<label class="col-md-2 control-label">Nombre</label>
													<div class="form-group col-md-3">
														<input class="form-control" name="name" id="nameNewMS"
															type="text" required>
													</div>
												</div>
											</div>
											<div class="form-group">
												<div class="row">
													<label class="col-md-2 control-label">Fase</label> <input
														type="hidden" name="idPhase" id="idPhaseMS"
														value="${phases[0].id}">
													<div class="form-group col-md-3">
														<select class="form-control" name="namePhase"
															id="namePhaseNewMS">
															<c:forEach var="phase" items="${phases}">
																<option id="${phase.id}">${phase.name}</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<div class="form-group">
												<div class="row">
													<label class="col-md-2 control-label">Fecha prevista</label>
													<div class="form-group col-md-3">
														<input type="text" class="form-control datepicker"
															data-format="dd/MM/yyyy" name="datePlan" id="datePlanNewMS"
															placeholder="dd/mm/aaaa" required>
													</div>
												</div>
											</div>
											<div class="form-group">
												<div class="row">
													<div class="col-md-6 center-block">
														<button type="submit" class="btn btn-primary center-block">
															Crear hito</button>
													</div>
												</div>
											</div>
										</form:form>
									</div>
								</div>
							</div>
						</div>

					</div>
					
				</div> <!-- /.modal-content -->
			</div> <!-- /.modal-dialog -->
		</div>	<!-- /.modal -->


	<!-- Modal: Select task
    ================================================== -->
		<div class="modal fade" id="selectTaskModal" role="dialog">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualizar tarea</h4>
					</div>
					<div class="modal-body">
						<form:form id="formSelectTask" class="form" method="post" action='url'
							modelAttribute="task" role="form" data-toggle="validator">

							<!-- First row -->
							<div class="form-group">
								<div class="row">
									<div class="form-group col-md-offset-1 col-md-4">
										<label for="inputName" class="control-label">Nombre</label> <input
											type="text" class="form-control" name="name" id="nameSelectTask"
											required>
									</div>

									<div class="form-group col-md-2">
										<label for="inputSurname1" class="control-label">Estado
										</label> <input type="text" class="form-control" name="state"
											id="stateSelectTask" required readOnly>
									</div>

									<div class="form-group col-md-2">
										<label for="inputSurname2" class="control-label">Progreso
										</label> <input type="text" class="form-control" name="progress"
											id="progressSelectTask" required>
									</div>
									
									<div class="form-group col-md-2">
										<label for="inputSurname2" class="control-label">Prioridad </label>
										<input type="hidden" name="idPriority" id="idPrioritySelectTask" value="B">
										<select class="form-control" name="priority" id="prioritySelectTask">
															<option id="B">Baja</option>
															<option id="M">Media</option>
															<option id="A">Alta</option>
															<option id="MA">Muy alta</option>
										</select>
									</div>
									<div class="form-group col-md-1">
									</div>
								</div>
							</div>

							<!-- Second row -->
							<div class="form-group">
								<div class="row">
									<div class="form-group col-md-offset-1 col-md-5">
										<h4>
											Previsto
										</h4>
										<div class="well">
											<div class="row">
												<div class="form-group col-md-6">
													<label for="inputName" class="control-label">Inicio</label>
													<input type="text" class="form-control datepicker"
															data-format="dd/MM/yyyy" name="iniPlan" 
															id="iniPlanSelectTask" placeholder="dd/mm/aaaa" required>
												</div>

												<div class="form-group col-md-6">
													<label for="inputSurname1" class="control-label">Duración
													</label> <input type="text" class="form-control" name="daysPlan"
														id="daysPlanSelectTask" required>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group col-md-5">
										<h4>
											Real
										</h4>
										<div class="well">
											<div class="row">
												<div class="form-group col-md-6">
													<label for="inputName" class="control-label">Inicio</label>
													<input type="text" class="form-control" name="iniReal"
														id="iniRealSelectTask">
												</div>

												<div class="form-group col-md-6">
													<label for="inputSurname1" class="control-label">Duración
													</label> <input type="text" class="form-control" name="daysReal"
														id="daysRealSelectTask">
												</div>
											</div>
										</div>
									</div>
									<div class="form-group col-md-1">
									</div>
								</div>
							</div>

							<div class="row">
								<button type="submit" class="btn btn-primary center-block">Guardar cambios</button>
							</div>
						</form:form>

						<div class="row">
							<div class="form-group col-md-offset-1">
								<h4>
									Información completa<br> 
									<small>
										Pulsa el botón "Información" si deseas obtener todos los datos relativos a
										esta tarea y acceder a operaciones avanzadas.  
									</small>
								</h4>
								<hr align="left" width="70%">
							</div>
							<div class="row">
								<form id="taskInformationForm" action="" method="get">
									<button type="submit" class="btn btn-info center-block">Información</button>
								</form>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>


		<!-- Modal: Select milestone
    	================================================== -->
		<div class="modal fade" id="selectMilestoneModal" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualizar hito</h4>
					</div>
					<div class="modal-body">
						<form:form id="formSelectMS" class="form" method="post" action='url'
							modelAttribute="milestone" role="form" data-toggle="validator">
							
							<div class="form-group">
								<div class="row">
									<div class="form-group col-md-offset-2 col-md-8">
										<label for="inputName" class="control-label">Nombre</label> <input
											type="text" class="form-control" name="name" id="nameSelectMS"
											required>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<div class="form-group col-md-offset-2 col-md-4">
										<label for="inputName" class="control-label">Fecha prevista</label> 
										<input type="text" class="form-control datepicker"
											   data-format="dd/MM/yyyy" name="datePlan" 
											   id="datePlanSelectMS" placeholder="dd/mm/aaaa" required>
															
									</div>
									<div class="form-group col-md-4">
										<label for="inputName" class="control-label">Fecha prevista</label> 
										<input type="text" class="form-control datepicker"
											   data-format="dd/MM/yyyy" name="dateReal" 
											   id="dateRealSelectMS" placeholder="dd/mm/aaaa" required>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<div class="form-group col-md-offset-2 col-md-8">
										<label>Cometarios</label>
										<textarea class="form-control" rows="3" id="commentSelectMS"
											name="comment" placeholder="Detalles a destacar..."></textarea>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<button type="submit" class="btn btn-primary center-block">Guardar cambios</button>
								</div>
							</div>
						</form:form>
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
	
	<!-- Bootstrap Datapicker instialation
    ================================================== -->	
	<script>
		$(document).ready(function() {
			$('.datepicker').datepicker({
				language : 'es'
			});
		})
	</script>
	
	<!-- Feedback modal activation
    ================================================== -->
	<script type="text/javascript">
		$(window).load(function() {
			if ("active" == '${feedback}') {
				$('#feedbackModal').modal('show');
			}
		});
	</script>


	<!-- Scripts neccesaries to get the id in selects
    ================================================== -->
    <script type="text/javascript">
		$("#namePhaseNewTask").change(function() {
			  var id = $(this).children(":selected").attr("id");
			  document.getElementById("idPhaseT").value = id;
			  
		});
	</script>
	
	<script type="text/javascript">
		$("#namePhaseNewMS").change(function() {
			  var id = $(this).children(":selected").attr("id");
			  document.getElementById("idPhaseMS").value = id;
			  
		});
	</script>
	
	<script type="text/javascript">
		$("#nameManager").change(function() {
			  var id = $(this).children(":selected").attr("id");
			  document.getElementById("idResponsible").value = id;
			  
		});
	</script>
	
	<script type="text/javascript">
		$("#priority").change(function() {
			  var id = $(this).children(":selected").attr("id");
			  document.getElementById("idPriority").value = id;
			  
		});
	</script>
	
	<script type="text/javascript">
		$("#prioritySelectTask").change(function() {
			  var id = $(this).children(":selected").attr("id");
			  document.getElementById("idPrioritySelectTask").value = id;
			  
		});
	</script>



	<!-- Gantt chart configuration
    ================================================== -->
	<script type="text/javascript">
	
		/* Main configurations */
		//gantt.config.readonly = true;
		gantt.config.date_grid = "%d/%m/%Y";
		gantt.config.grid_width = 520;
		gantt.config.scale_height = 55;
		gantt.config.drag_progress = false;
		
		// We disable all
		gantt.config.drag_move = false; //disables the possibility to move tasks by dnd
		gantt.config.drag_links = false; //disables the possibility to create links by dnd
		gantt.config.drag_progress = false; //disables the possibility to change the task //progress by dragging the progress knob
		gantt.config.drag_resize = false; //disables the possibility to resize tasks by dnd
				
		
		/* Set the start and end date of the diagrma 
		var opened_task = gantt.getState().min_date;
		var opened_task = gantt.getState().max_date;
		gantt.config.start_date = new Date(2016, 04, 30);
		gantt.config.end_date = new Date(2016, 06, 11);*/
		
		gantt.config.scale_unit = "day"; 
		gantt.config.date_scale = "%D %d";
		gantt.config.min_column_width = 50;
		
		var weekScaleTemplate = function(date){
			  var dateToStr = gantt.date.date_to_str("%d %M");
			  var endDate = gantt.date.add(gantt.date.add(date, 1, "week"), -1, "day");
			  var year = gantt.date.date_to_str("%Y");
			  return dateToStr(date) + " - " + dateToStr(endDate) + ", " + year(date) ;
		};
			
		gantt.config.subscales = [
			{unit:"week", step:1, template:weekScaleTemplate}
		];
 		
 		gantt.templates.scale_cell_class = function(date){
 		    if(date.getDay()==0||date.getDay()==6||date=="02-06-2016"){
 		        return "weekend";
 		    }
 		};
 		
 		gantt.templates.task_cell_class = function(item,date){
 		    if(date.getDay()==0||date.getDay()==6||date=="02-06-2016"){ 
 		        return "weekend" ;
 		    }
 		};
 		

		/* Specifying Columns for the grid */
		gantt.config.columns =  [
			{name:"text",       label:"Nombre",  tree:true, width: '*'},
			{name:"start_date", label:"Inicio",   align: "center", width : 90},
			{name:"duration",   label:"Duración",   align: "center", width : 50},
			{name:"end",   label:"Fin",        align: "center", width : 90},
		    {name:"add",        label:"",           width:40 }

		];
		
		/* ocultacion add */
		gantt.templates.grid_row_class = function(start, end, task){
		   ///if(task.$level > 0){
		      return "nested_task"
		   /*}
		   return "";*/
		};		
		
		/* Initialize the Gantt chart */
		gantt.config.order_branch = true;
		
		gantt.init("gantt_here");
		
		/* Load the data (phases, tasks and milestones) that coming from the controller  */
		gantt.parse(${dataProject});
	</script>


	<!-- Select item in Gantt chart
    ================================================== -->
	<script type="text/javascript">	
		gantt.attachEvent("onTaskDblClick", function(id,item){
			var type = id.substring(0, 1);
			var idTask = id.substring(1);
			
			// Check the type of the object
			if (type == "p") {
				alert("Es una fase");
			} 
			else if (type == "t") {
				
				var state = ${taskDetails}[id].state;
				
				if (state=="Planificacion") {
					document.getElementById("progressSelectTask").readOnly = true;
					document.getElementById("iniRealSelectTask").readOnly = true;
					document.getElementById("daysRealSelectTask").readOnly = true;
				}
				
				var idPhase = ${taskDetails}[id].idPhase;
				var urlGetInfo = "/projects/" + ${idProject} + "/phases/" + idPhase + "/tasks/" + idTask;
				var urlUpdate = "/project/" + ${idProject} + "/task/" + idTask + "/update";
				
				document.getElementById("formSelectTask").action = urlUpdate;
				document.getElementById("nameSelectTask").value = ${taskDetails}[id].name;
				document.getElementById("stateSelectTask").value = state;
				document.getElementById("progressSelectTask").value = ${taskDetails}[id].progress;
				document.getElementById("idPrioritySelectTask").value = ${taskDetails}[id].idPriority;
				document.getElementById("prioritySelectTask").value = ${taskDetails}[id].priority;
				document.getElementById("iniPlanSelectTask").value = ${taskDetails}[id].iniPlan;
				document.getElementById("daysPlanSelectTask").value = ${taskDetails}[id].daysPlan;
				document.getElementById("iniRealSelectTask").value = ${taskDetails}[id].iniReal;
				document.getElementById("daysRealSelectTask").value = ${taskDetails}[id].daysReal;
				document.getElementById("daysRealSelectTask").value = ${taskDetails}[id].daysReal;
				document.getElementById("taskInformationForm").action = urlGetInfo;
				
				$('#selectTaskModal').modal('show');
			} 
			else {
				
				var urlUpdate = "/project/" + ${idProject} + "/task/" + idTask + "/update";
				
				document.getElementById("formSelectMS").action = urlUpdate;
				document.getElementById("nameSelectMS").value = ${msDetails}[id].name;
				document.getElementById("datePlanSelectMS").value = ${msDetails}[id].datePlan;
				document.getElementById("dateRealSelectMS").value = ${msDetails}[id].dateReal;
				document.getElementById("commentSelectMS").value = ${msDetails}[id].comment;
				
				$('#selectMilestoneModal').modal('show');
			}
			
			/*var ids = id.split("-");
			
			if (ids.length > 1) {
				
				var type = ids[1].substring(0, 1);
				
				if (type == "t") {
					alert("Es una tarea");
				}
				else {
					alert("Es un hito");
				}
				alert(ids[1]);
				/*document.getElementById("json").value = ${dataProject}['links'][0].source;
				$('#selectTaskModal').modal('show');
			}*/
			
			/*alert("ID tarea doblemente clickada: " + id);*/
			/* window.location.href = "http://localhost:8080/persons"; */
		});
	</script>
	
	<script type="text/javascript">
		gantt.attachEvent("onTaskCreated", function(id,item){
			$('#creationModal').modal('show');
		});
	</script>
	


</body>
</html>
