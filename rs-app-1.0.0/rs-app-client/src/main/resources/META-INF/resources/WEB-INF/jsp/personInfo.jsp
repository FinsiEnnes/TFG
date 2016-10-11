<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link href="/css/customTabs.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<h3>
			Persona <br> <small>Cambia la información de esta
				persona</small>
		</h3>
		<br>

		<div class="col-md-3">
			<!-- required for floating -->
			<!-- Nav tabs -->
			<ul class="nav nav-tabs tabs-left sideways">
				<li class="active"><a href="#home-v" data-toggle="tab">Cuenta</a></li>
				<li><a href="#profile-v" data-toggle="tab">Aptitudes</a></li>
				<li><a href="#messages-v" data-toggle="tab">Bajas</a></li>
			</ul>
		</div>

		<div class="col-xs-9">
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="tab-pane active" id="home-v">
					<form:form method="POST" action="/persons" modelAttribute="person">
						<div class="row form-group">
							<label class="col-md-2 control-label">Nombre</label>
							<div class="col-md-3">
								<input class="form-control" id="focusedInput" type="text"
									value=${person.name}>
							</div>
							<label class="col-md-offset-1 col-md-2 control-label">NIF</label>
							<div class="col-md-3">
								<input class="form-control" id="focusedInput" type="text"
									value=${person.nif}>
							</div>
						</div>
						<div class="row form-group">
							<label class="col-md-2 control-label">Primer apellido</label>
							<div class="col-md-3">
								<input class="form-control" id="focusedInput" type="text"
									value=${person.surname1}>
							</div>
							<label class="col-md-offset-1 col-md-2 control-label">Email</label>
							<div class="col-md-3">
								<input class="form-control" id="focusedInput" type="text"
									value=${person.email}>
							</div>
						</div>
						<div class="row form-group">
							<label class="col-md-2 control-label">Segundo apellido</label>
							<div class="col-md-3">
								<input class="form-control" id="focusedInput" type="text"
									value=${person.surname2}>
							</div>
							<label class="col-md-offset-1 col-md-2 control-label">Alta</label>
							<div class="col-md-3">
								<input class="form-control" id="focusedInput" type="text"
									value=${person.hiredate}>
							</div>
						</div>
						<div class="row form-group">
							<div class="col-md-11">
								<label for="comment">Comentario</label>
								<textarea class="form-control" rows="2" id="comment">${person.comment}</textarea>
							</div>
						</div>
						<button type="submit" class="col-md-offset-9 btn btn-primary">
							Guardar cambios
						</button>
					</form:form>
				</div>
				<div class="tab-pane" id="profile-v">Profile Tab.</div>
				<div class="tab-pane" id="messages-v">Messages Tab.</div>
			</div>
		</div>
	</div>


	<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>