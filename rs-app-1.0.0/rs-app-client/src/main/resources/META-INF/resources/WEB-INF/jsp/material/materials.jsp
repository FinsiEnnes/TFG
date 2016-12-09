
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
				<li><a href="/projects/${idProject}/phases/${idPhase}/tasks/${idTask}"><font size="3">Tarea</font></a></li>
				<li><a href="#"><font size="3">Hito</font></a></li>
				<li><a href="#"><font size="3">Personas</font></a></li>
				<li class="active"><a href="#"><font size="3">Materiales</font></a></li>
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
									<a href="#" class="active">
										Materiales
										<span class="glyphicon glyphicon-wrench pull-right"></span>
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
					Listado de materiales<br> 
					<small>
						Lista completa de todos los materiales registrados
					</small>
				</h3>
				<hr width="110%">
				<br>
				
				<div class="row">
					<div class="form-group col-md-2">
						<h4 class="subtitle">
							Materiales<br> 
						</h4>
					</div>
					<div class="col-md-offset-7 col-md-2">
						<button id="addPlanMaterialButton" type="button" class="btn btn-success pull-right"
							data-toggle="modal" data-target="#addMaterial">
							<span class="glyphicon glyphicon-plus"></span> 
							Nuevo material
						</button>
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-md-offset-1 col-md-10">
					<table id="materialTable" class="table table-bordered"	
					data-toggle="table" data-height="162" data-click-to-select="true" data-single-select="true">
						<thead>
							<tr>
								<th class="col-md-1" data-field="state" data-checkbox="true"></th>
								<th class="col-md-1">ID</th>
								<th class="col-md-5">Nombre</th>
								<th class="col-md-2">Coste</th>
								<th class="col-md-1">Tipo</th>
								<th class="col-md-2">Acción</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="material" items="${materials}">
							<tr>
								<td></td>
								<td class="text-info">${material.id}</td>
								<td>${material.name}</td>
								<td>${material.cost}</td>
								<td>${material.type}</td>
								<td>
									<table>
										<tr>
											<td>
												<button type="button" class="btn btn-primary btn-xs center-block"
													data-toggle="modal" data-target="#editMaterial"
													data-id="${material.id}" data-name="${material.name}"
													data-cost="${material.cost}" data-type="${material.type}"
													data-desc="${material.description}">
													<span class="glyphicon glyphicon-edit"></span>
												</button>
											</td>
											<td>
												<button id="deleteMaterial" type="button"
													class="btn btn-danger btn-xs center-block"
													data-toggle="modal" data-target="#confirmDeleteMaterial"
													data-id="${material.id}" data-name="${material.name}">
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
				
				<div class="row">
					<div class="form-group col-md-6">
						<h4 class="subtitle">
							Descripción del material seleccionado<br> 
						</h4>
					</div>
				</div>
				
				<div class="row">
					<div class="form-group col-md-offset-3 col-md-6">
						<textarea class="form-control" rows="6" id="descMaterial">
						</textarea>
					</div>
				</div>

			</div>
		</div>

	
	
	<!-- Modal: Creation of a new Material
	===================================================================================================== -->
		<div class="modal fade modal" id="addMaterial" role="dialog">
			<div class="modal-dialog">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Creación de un nuevo material</h4>
					</div>

					<div class="modal-body">						
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Datos relacionados<br> 
									<small>
										Define el nombre, tipo y coste del nuevo material. También puedes
									</small>
									<small>
										añadir una descripción detallada sobre el producto.
									</small>
								</h4>
							</div>
						</div>
					
						<form:form id="materialForm" class="form-horizontal" method="post" 
						modelAttribute="material" action='/materials' data-toggle="validator">

							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-2 col-md-2 control-label">Nombre</label>
									<div class="form-group col-md-5">
										<input class="form-control" name="name"
										type="text" required>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-2 col-md-2 control-label">Tipo</label>
									<div class="form-group col-md-5">
										<select class="form-control" name="type">
											<option>Propio</option>
											<option>Comprado</option>
										</select>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-2 col-md-2 control-label">Coste</label>
									<div class="form-group has-feedback col-md-5">
										<input class="form-control" name="cost" 
										type="text" pattern="^[1-9]\d*$" required>
										 <span class="glyphicon form-control-feedback"></span>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-2 col-md-2 control-label">Descripción</label>
									<div class="form-group col-md-5">
										<textarea class="form-control" rows="4" name="description">
										</textarea>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
								<button id="addMaterialButton" type="submit"
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
		
		
	<!-- Modal: Creation of a new Material
	===================================================================================================== -->
		<div class="modal fade modal" id="editMaterial" role="dialog">
			<div class="modal-dialog">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Actualización de material</h4>
					</div>

					<div class="modal-body">						
						<div class="row">
							<div class="form-group col-md-offset-1 col-md-10">
								<h4>
									Información del material<br> 
									<small>
										Cambia la información relativa al material seleccionado.
									</small>
								</h4>
							</div>
						</div>
					
						<form:form id="editMaterialForm" class="form-horizontal" method="post" 
						modelAttribute="material" action='/materials' data-toggle="validator">

							<input type="hidden" name="id" id="idEditMaterial">
										
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-2 col-md-2 control-label">Nombre</label>
									<div class="form-group col-md-5">
										<input class="form-control" name="name" id="nameEditMaterial"
										type="text" required>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-2 col-md-2 control-label">Tipo</label>
									<div class="form-group col-md-5">
										<select class="form-control" name="type">
											<option id="ownOption">Propio</option>
											<option id="buyOption">Comprado</option>
										</select>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-2 col-md-2 control-label">Coste</label>
									<div class="form-group has-feedback col-md-5">
										<input class="form-control" name="cost"  id="costEditMaterial"
										type="text" pattern="^[1-9]\d*$" readOnly>
										 <span class="glyphicon form-control-feedback"></span>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
									<label class="col-md-offset-2 col-md-2 control-label">Descripción</label>
									<div class="form-group col-md-5">
										<textarea class="form-control" rows="4" name="description"
										id="descEditMaterial">
										</textarea>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<div class="row">
								<button id="editMaterialButton" type="submit"
								 class="btn btn-primary center-block" disabled>
									Guardar cambios
								</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
		
		
		
	<!-- Modal: Confirmation of delete Material
	===================================================================================================== -->
		<div class="modal fade" id="confirmDeleteMaterial" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Borrado de material</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<p id="msgMaterial" align="center"></p>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="row center-block">
							<div class="col-md-offset-3 col-md-3">
								<form id="confirmDeleteMaterialButton" action='' method="post">
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
	
	<!-- Dynamic load of the material description in the textarea
	===================================================================================================== -->
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#materialTable').change(function() { 
			   
			   // Get info of the table
			   var json = JSON.parse(JSON.stringify($('#materialTable').bootstrapTable('getSelections')));
			   var size = json.length;
			   
			   // If a option is selected then we can add a Person as manager
			   if (size > 0) {
				   var idMaterial = json['0']['1'];
				   var desc = "";
				   
				   // Find the description of this Material
				   for (i = 0; i < ${matDescriptions}.length; i++) { 
    					if (${matDescriptions}[i].id == idMaterial) {
    						desc = ${matDescriptions}[i].desc;
    					}
					}
				   document.getElementById("descMaterial").value = desc;
			   } 
	        
		   }); 
		});
	</script>
	
	
	<!-- Data transfer to the modal editMaterial
	===================================================================================================== -->
	<script type='text/javascript'>
		$(function() {
			$('#editMaterial').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var id = button.data('id');
				var name = button.data('name');
				var cost = button.data('cost');
				var type = button.data('type');
				var desc = button.data('desc');
				  
				// Create the url
				var url = "/materials/" + id + "/update";
						
				// Set the values at the modal components
				document.getElementById('editMaterialForm').action = url;
				document.getElementById('idEditMaterial').value = id;
				document.getElementById('nameEditMaterial').value = name;
				document.getElementById('costEditMaterial').value = cost;
				document.getElementById('descEditMaterial').value = desc;
				
				// Set the select option
				if (type=="Propio") {
					document.getElementById('ownOption').selected = "selected";
				} else {
					document.getElementById('buyOption').selected = "selected";
				}
			})
		});
	</script>
	
	
	<!-- Enable the edit button when the data of the data modal change 
	===================================================================================================== -->
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#editMaterialForm').change(function() { 
				document.getElementById("editMaterialButton").disabled = false;		        
		   }); 
		});
	</script>
	
	
		<!-- Data transfer to the modal confirmDeleteMaterial
	===================================================================================================== -->
	<script type='text/javascript'>
		$(function() {
			$('#confirmDeleteMaterial').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var id = button.data('id');
				var name = button.data('name');
				  
				// Create the url
				var url = "/materials/" + id + "/delete";
				
				// Create the msg
				var msg = "Material seleccionado: " + name.bold() + ". ¿Seguro que desea eliminar este material?";
						
				// Set the values at the modal components
				document.getElementById('msgMaterial').innerHTML = msg;
				$('#confirmDeleteMaterialButton').get(0).setAttribute('action', url);
			})
		});
	</script>

</body>
</html>
