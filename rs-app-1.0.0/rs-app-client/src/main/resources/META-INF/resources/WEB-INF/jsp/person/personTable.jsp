<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>

<head>
<link 
	href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="/webjars/bootstrap-datepicker/1.6.1/css/bootstrap-datepicker.min.css"
	rel="stylesheet">
<link 
	href="/webjars/bootstrap-table/1.11.0/src/bootstrap-table.css"
	rel="stylesheet">
</head>

<style type='text/css'>
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


<body>
	<div class="container">
		<h2>
			<b>Lista de Personas</b>
		</h2>
		<hr>
		<br>

		<!-- ----------------------------- Search and add buttons  ---------------------------- -->
		<div class="row row-eq-height">
			
			<!-- Search button -->
			<div class="col-md-3">
				<form class="form" role="search" action="/persons" method="get" data-toggle="validator">
					<div class="input-group">
						<input id="searchInput" type="text" class="form-control" placeholder="Búsqueda por ID"
							name="keyword" id="keyword" required>
						<div class="input-group-btn">
							<button class="btn btn-default" id="searchButton" type="submit" name="search-term"
								value="ID">
								<i class="glyphicon glyphicon-search"></i>
							</button>
							<button type="button"
								class="btn btn-default dropdown-toggle dropdown-toggle-split"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#" id="ID">ID</a></li>
								<li><a href="#" id="nombre">Nombre</a></li>
								<li><a href="#" id="DNI">DNI</a></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
			
			<!-- Add button -->
			<div class="col-md-offset-7 col-md-2">
				<button type="button" class="btn btn-success pull-right"
						data-toggle="modal" data-target="#formPersonCreation">
					<span class="glyphicon glyphicon-plus"></span> 
					Añadir nueva persona
				</button>
			</div>

		</div>
		<br>
		
		<!-- ------------------ Table whose rows include Person information  ------------------ -->
		<table id="person_table" class="table table-bordered"  data-toggle="table">
			<thead>
				<tr>
					<th>ID</th>
					<th>Nombre</th>
					<th>Apellido 1</th>
					<th>Apellido 2</th>
					<th>Nif</th>
					<th>Email</th>
					<th>Alta</th>
					<th>Acción</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="person" items="${persons}">
					<tr>
						<td id="idPerson" class="col-md-1 text-info">${person.id}</td>
						<td class="col-md-1">${person.name}</td>
						<td class="col-md-1">${person.surname1}</td>
						<td class="col-md-1">${person.surname2}</td>
						<td class="col-md-1">${person.nif}</td>
						<td class="col-md-2">${person.email}</td>
						<td class="col-md-1">
							${person.hiredate}
						</td>
						<td class="col-md-1">
							<table>
								<tr>
									<td>
									<form action="/persons/${person.id}" method="get">
										<button type="submit"
											class="btn btn-info btn-xs center-block">
											<span class="glyphicon glyphicon-info-sign"></span>
										</button>
									</form>
									</td>
									<td>
										<button id="deletePerson" type="button"
											class="btn btn-danger btn-xs center-block"
											 data-toggle="modal" data-target="#confirmDelete" 
											 data-id="${person.id}"
											 data-name="${person.name}"
											 data-surname1="${person.surname1}"
											 data-surname2="${person.surname2}">
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
		<br>

		<!-- ----------- Pagination information with the previous and next buttons  ----------- -->
		<div class="row">
			
			<!-- Page information -->
			<div class="col-md-2">
				<p class="text-info text-center">
					Página ${pageNumber} de ${totalPage}
				</p>
			</div>
			
			<!-- Previous button -->
			<div class="col-md-offset-7 col-md-1">
				<form action="/persons" method="get">
					<button type="submit" name="page" value="${previousPage}"
						class="btn btn-default ${previousActive}">
						<span class="glyphicon glyphicon-chevron-left"></span> Anterior
					</button>
				</form>
			</div>
			
			<!-- Next button -->
			<div class="col-md-offset-1 col-md-1">
				<form action="/persons" method="get">
					<button type="submit" name="page" value="${nextPage}"
						class="btn btn-default pull-right ${nextActive}" disabled>
						Siguiente <span class="glyphicon glyphicon-chevron-right"></span>
					</button>
				</form>
			</div>
		</div>
		<br>


		<!-- -------------------------- Modal: Form Person creation  -------------------------- -->
		<div class="modal fade" id="formPersonCreation" role="dialog">
			<div class="modal-dialog">

				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Creación de nueva persona</h4>
					</div>
					<div class="modal-body">
						<form:form class="form" method="post" action='/persons'
							modelAttribute="person" role="form" data-toggle="validator">

							<!-- First row -->
							<div class="form-group">
								<div class="row">
									<div class="form-group col-md-4">
										<label for="inputName" class="control-label">Nombre</label> <input
											type="text" class="form-control" name="name" id="name"
											required>
									</div>

									<div class="form-group col-md-4">
										<label for="inputSurname1" class="control-label">Apellido
											1</label> <input type="text" class="form-control" name="surname1"
											id="surname1" required>
									</div>

									<div class="form-group col-md-4">
										<label for="inputSurname2" class="control-label">Apellido
											2</label> <input type="text" class="form-control" name="surname2"
											id="surname2" required>
									</div>
								</div>
							</div>

							<!-- Seconnd row -->
							<div class="form-group">
								<div class="row">
									<div class="form-group has-feedback col-md-4">
										<label for="inputNif" class="control-label">DNI</label>
										<div class="right-inner-addon">
											<input type="text" class="form-control" name="nif" id="nif"
												pattern="^(\d{8})([A-Z]{1})$" maxlength="9"
												placeholder="12345678A" required> <span
												class="glyphicon form-control-feedback"></span>
										</div>
									</div>

									<div class="form-group has-feedback col-md-4">
										<label for="inputEmail" class="control-label">Email </label>
										<div class="right-inner-addon">
											<input type="email" class="form-control" name="email"
												id="email" placeholder="ejemplo@dominio.com" required>
											<span class="glyphicon form-control-feedback"></span>
										</div>
									</div>

									<div class="form-group col-md-4">
										<label for="inputHiredate" class="control-label">Fecha
											de alta </label> <input type="text" class="form-control datepicker"
											data-format="dd/MM/yyyy" name="hiredate" id="hiredate"
											placeholder="dd/mm/aaaa" required>
									</div>
								</div>
							</div>

							<!-- Third row -->
							<div class="form-group">
								<div class="row">
									<div class="col-md-12">
										<label>Cometarios</label>
										<textarea class="form-control" rows="2" id="comment"
											name="comment" placeholder="Detalles a destacar..."></textarea>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="row">
								<button type="submit" class="btn btn-primary center-block">Añadir</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>

			</div>
		</div>

		<!-- ------------------------ Modal: This is used to show feedback  ------------------------- -->
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
				
		<!-- ------------------------ Modal: Confirm Person delete  ------------------------- -->
		<div class="modal fade" id="confirmDelete" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Confirmación de borrado</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<p id="confirmMsg" align="center"></p>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="row center-block">
							<div class="col-md-offset-3 col-md-3">
								<form id="confirmDeleteButton" action="" method="post">
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

	<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script
		src="/webjars/bootstrap-datepicker/1.6.1/js/bootstrap-datepicker.min.js"></script>
	<script
		src="/webjars/bootstrap-datepicker/1.6.1/locales/bootstrap-datepicker.es.min.js"></script>
	<script src="/webjars/bootstrap-validator/0.11.5/dist/validator.js"></script>
	<script src="/webjars/bootstrap-validator/0.11.5/dist/validator.min.js"></script>
	<script src="/webjars/bootstrap-table/1.11.0/src/bootstrap-table.js"></script>

	<!-- ------------------------ Datapicker language: es  ------------------------ -->
	<script>
		$(document).ready(function() {
			$('.datepicker').datepicker({
				language : 'es'
			});
		})
	</script>


	<script type="text/javascript">
		$(window).load(function() {
			if ("correctCreation" == '${action}') {
				$('#feedbackModal').modal('show');
			}
		});
	</script>

	<!-- ------------- Set the search criteria in function of the button clicked  ------------- -->
	<script type='text/javascript'>
		$(window).load(function() {
			$(function() {
				$(".dropdown-menu").on("click", "li", function(event) {
					console.log(event.target.id, event);
					document.getElementById("searchButton").value = event.target.id;
					document.getElementById("searchInput").placeholder = "Búsqueda por " + event.target.id;
				})
			})
		});
	</script>

	<!-- ------------------ Passing the data of the selected row to the modal ----------------- -->
	<script type='text/javascript'>
		$(function() {
			$('#confirmDelete').on('show.bs.modal', function(event) {
				
				// Button that triggered the modal
				var button = $(event.relatedTarget) 
				
				// Extract info from data attributes
				var id = button.data('id') 
				var name = button.data('name') + " " + button.data('surname1') + " " + button.data('surname2')
				
				var modal = $(this)
				var url = "persons/" + id + "/delete"
				var msg = "¿Seguro que desea eliminar los datos de " + name + "?"
						
				// Set the values at the modal components
				document.getElementById('confirmMsg').innerHTML = msg
				document.getElementById('confirmDeleteButton').action = url
			})
		});
	</script>



	<script type='text/javascript'>
		$(function() {            
			$('#delete_button').click(function () {
				var json = JSON.parse(JSON.stringify($('#person_table').bootstrapTable('getSelections')));
				var idPerson = json[0]['0'];
				var url = "/persons/" + idPerson + "/delete";			    
                
				document.getElementById("delete_button").action = url;
            });
		});
	</script>

</body>

</html>