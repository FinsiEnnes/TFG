
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
<head>

<title>Dashboard Template for Bootstrap</title>

<!-- Bootstrap core CSS -->
<link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">

<style>
body {
	padding-top: 50px;
}

.navbar-default {
  background-color: #9a9a9a;
  border-color: #337ab7;
}
.navbar-default .navbar-brand {
  color: #ecf0f1;
}
.navbar-default .navbar-brand:hover,
.navbar-default .navbar-brand:focus {
  color: #ecdbff;
}
.navbar-default .navbar-text {
  color: #ecf0f1;
}
.navbar-default .navbar-nav > li > a {
  color: #ecf0f1;
}
.navbar-default .navbar-nav > li > a:hover,
.navbar-default .navbar-nav > li > a:focus {
  color: #ecdbff;
}
.navbar-default .navbar-nav > .active > a,
.navbar-default .navbar-nav > .active > a:hover,
.navbar-default .navbar-nav > .active > a:focus {
  color: #ecdbff;
  background-color: #337ab7;
}
.navbar-default .navbar-nav > .open > a,
.navbar-default .navbar-nav > .open > a:hover,
.navbar-default .navbar-nav > .open > a:focus {
  color: #ecdbff;
  background-color: #337ab7;
}
.navbar-default .navbar-toggle {
  border-color: #337ab7;
}
.navbar-default .navbar-toggle:hover,
.navbar-default .navbar-toggle:focus {
  background-color: #337ab7;
}
.navbar-default .navbar-toggle .icon-bar {
  background-color: #ecf0f1;
}
.navbar-default .navbar-collapse,
.navbar-default .navbar-form {
  border-color: #ecf0f1;
}
.navbar-default .navbar-link {
  color: #ecf0f1;
}
.navbar-default .navbar-link:hover {
  color: #ecdbff;
}

@media (max-width: 767px) {
  .navbar-default .navbar-nav .open .dropdown-menu > li > a {
    color: #ecf0f1;
  }
  .navbar-default .navbar-nav .open .dropdown-menu > li > a:hover,
  .navbar-default .navbar-nav .open .dropdown-menu > li > a:focus {
    color: #ecdbff;
  }
  .navbar-default .navbar-nav .open .dropdown-menu > .active > a,
  .navbar-default .navbar-nav .open .dropdown-menu > .active > a:hover,
  .navbar-default .navbar-nav .open .dropdown-menu > .active > a:focus {
    color: #ecdbff;
    background-color: #337ab7;
  }
}

.well{
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
</style>
</head>

<body>

	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Brand</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Link</a></li>
				<li><a href="#">Link</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Dropdown <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Action</a></li>
						<li><a href="#">Another action</a></li>
						<li><a href="#">Something else here</a></li>
						<li class="divider"></li>
						<li><a href="#">Separated link</a></li>
						<li class="divider"></li>
						<li><a href="#">One more separated link</a></li>
					</ul></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li><a href="#">Link</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Dropdown <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Action</a></li>
						<li><a href="#">Another action</a></li>
						<li><a href="#">Something else here</a></li>
						<li class="divider"></li>
						<li><a href="#">Separated link</a></li>
					</ul></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</nav>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3 ">
				<div class="sidebar-nav affix" style="height:100%;">
					<div id="sidebar" class="well nav-stacked affix" style="width: 300px;  ">
					<h4>
						<i class="glyphicon glyphicon-home"></i> <small><b> GENERAL</b></small>
					</h4>
					<ul class="nav nav-pills nav-stacked">
						<li class="active"><a href="#">Información</a></li>
						<li><a href="#">Estado</a></li>
						<li><a href="#">Estadísticas</a></li>
						<li><a href="#">Cliente</a></li>
					</ul>
					<br>
					<h4>
						<i class="glyphicon glyphicon-user"></i> <small><b> DIRECCIÓN</b></small>
					</h4>
					<ul class="nav nav-pills nav-stacked">
						<li><a href="#">Jefes de proyecto</a></li>
					</ul>
					<br>
					<h4>
						<i class="glyphicon glyphicon-equalizer"></i> <small><b> OTROS</b></small>
					</h4>
					<ul class="nav nav-pills nav-stacked">
						<li><a href="#">Calendario</a></li>
					</ul>
				</div>
				</div>
			</div>

			<div class="col-md-9">
				<h3>
					Información <br> <small>Cambia la información básica
						de esta persona</small>
				</h3>
				<hr>
				<form:form id="personForm" class="form-horizontal" method="post"
					action="/persons/2/update" modelAttribute="person" role="form"
					data-toggle="validator">

					<!-- First row -->
					<div class="row">
						<label class="col-md-2 control-label">Nombre</label>
						<div class="form-group col-md-4">
							<input class="form-control" name="name" id="name" type="text"
								required>
						</div>

						<label class="col-md-2 control-label">DNI</label>
						<div class="form-group col-md-4">
							<input class="form-control" readonly name="nif" id="nif"
								type="text" required>
						</div>
					</div>
					<br>

					<!-- Second row -->
					<div class="row">
						<label class="col-md-2 control-label">Apellido 1</label>
						<div class="form-group col-md-4">
							<input class="form-control" name="surname1" id="surname1"
								type="text" required>
						</div>

						<label class="col-md-2 control-label">Email</label>
						<div class="form-group col-md-4">
							<input class="form-control" name="email" id="email" type="text"
								required>
						</div>
					</div>
					<br>

					<!-- Third row -->
					<div class="row">
						<label class="col-md-2 control-label">Apellido 2</label>
						<div class="form-group col-md-4">
							<input class="form-control" name="surname2" id="surname2"
								type="text" required>
						</div>

						<label class="col-md-2 control-label">Fecha de alta</label>
						<div class="form-group col-md-4 date">
							<input type="text" class="form-control datepicker"
								data-format="dd/MM/yyyy" name="hiredate" id="hiredate"
								placeholder="dd/mm/aaaa" required>
						</div>
					</div>
					<br>

					

					<!-- Fourth row -->
					<div class="row">
						<label class="col-md-2 control-label" for="comment">Comentarios</label>
						<div class="form-group col-md-10" style="width: 79.8%">
							<textarea class="form-control pull-right" rows="6" name="comment"
								id="comment">
									</textarea>
						</div>
					</div>
					<br>

					<!-- Submit button -->
					<div class="row">
						<div class="col-md-offset-5 col-md-2">
							<button id="saveChangesButton" type="submit" disabled='true'
								class="btn btn-primary">Guardar cambios</button>
						</div>
					</div>
					<br>
					<br>
				</form:form>
			</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
