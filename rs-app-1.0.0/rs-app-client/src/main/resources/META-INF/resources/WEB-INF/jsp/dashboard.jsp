
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>

<title>Dashboard Template for Bootstrap</title>

<!-- Bootstrap core CSS -->
<link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="/webjars/bootstrap-datepicker/1.6.1/css/bootstrap-datepicker.min.css"
	rel="stylesheet">

<style>
body {
	padding-top: 50px;
}

/* Barra superior */
.navbar-default {
  background-color: #408986;
  border-color: #fb9d4b;
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
  background-color: #fb9d4b;
}
.navbar-default .navbar-nav > .open > a,
.navbar-default .navbar-nav > .open > a:hover,
.navbar-default .navbar-nav > .open > a:focus {
  color: #ecdbff;
  background-color: #fb9d4b;
}
.navbar-default .navbar-toggle {
  border-color: #fb9d4b;
}
.navbar-default .navbar-toggle:hover,<link
	href="/webjars/bootstrap-datepicker/1.6.1/css/bootstrap-datepicker.min.css"
	rel="stylesheet">
.navbar-default .navbar-toggle:focus {
  background-color: #fb9d4b;
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
    background-color: #fb9d4b;
  }
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

/* el latoooooooo */
.container {
	width: 800px;
	overflow: hidden;
	display: inline-block;
}

.side-bar {
	background: #74AFAD;
	position: absolute;
	height: 100%;
	width: 200px;
	color: #fff;
	transition: margin-left 0.5s;
}

.side-bar ul {
	list-style: none;
	padding: 0px;
}

.side-bar ul li.menu-head {
	font-family: 'Lato', sans-serif;
	padding: 20px;
}

.side-bar ul li.menu-head a {
	color: #fff;
	text-decoration: none;
	height: 50px;
}

.side-bar ul .menu-head  a {
	color: #fff;
	text-decoration: none;
	height: 50px;
}

.side-bar ul .menu li  a {
	color: #fff;
	text-decoration: none;
	display: inline-table;
	width: 100%;
	padding-left: 20px;
	padding-right: 20px;
	padding-top: 10px;
	padding-bottom: 10px;
}

.side-bar ul .menu li  a:hover {
	border-left: 3px solid #ECECEA;
	padding-left: 17px;
	background: #fb9d4b;
}

.side-bar ul .menu li  a.active {
	padding-left: 17px;
	background: #D9853B;
	border-left: 3px solid #ECECEA;
}

.side-bar ul .menu li  a.active:before {
	content: "";
	position: absolute;
	width: 0;
	height: 0;
	border-top: 20px solid transparent;
	border-bottom: 20px solid transparent;
	border-left: 7px solid #D9853B;
	margin-top: -10px;
	margin-left: 180px;
}

.content {
	padding-left: 200px;
	transition: padding-left 0.5s;
}

.active>.side-bar {
	margin-left: -150px;
	transition: margin-left 0.5s;
}

.active>.content {
	padding-left: 50px;
	transition: padding-left 0.5s;
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
			<div class="col-md-2 nopadding">
				<div class="sidebar-nav affix" style="height: 100%;">
					<div class="side-bar">
						<ul>
							<li class="menu-head"><b>GENERAL</b></li>
							<li class="menu">
								<ul>
								<li><a href="/persons">Información <span
										class="glyphicon glyphicon-info-sign pull-right"></span></a></li>
								<li><a href="#" class="active">Estado <span
										class="glyphicon glyphicon-check pull-right"></span></a></li>
								<li><a href="#">Estadísticas <span
										class="glyphicon glyphicon-stats pull-right"></span>
								</a></li>
								<li><a href="#">Cliente <span
										class="glyphicon glyphicon-user pull-right"></span></a></li>
								</ul>
							</li>
						</ul>

						<br>
						<ul>
							<li class="menu-head"><b>DIRECCIÓN</b></li>
							<li class="menu">
								<ul>
								<li><a href="#">Jefes de proyecto <span
										class="glyphicon glyphicon-briefcase pull-right"></span></a></li>
								</ul>
							</li>
						</ul>
						
						<br>
						<ul>	
							<li class="menu-head"><b>OTROS</b></li>
							<li class="menu">
								<ul>
								<li><a href="#">Calendario <span
										class="glyphicon glyphicon-calendar pull-right"></span></a></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
			</div>

			<div class="col-md-10">
				<div id="date" class="datepicker" onclick="myFunction()"></div>
			</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script src="/webjars/bootstrap-datepicker/1.6.1/js/bootstrap-datepicker.min.js"></script>
	<script src="/webjars/bootstrap-datepicker/1.6.1/locales/bootstrap-datepicker.es.min.js"></script>

	<!-- ------------------------------ Datapicker language: es  ------------------------------ -->
	<script>
		var forbidden=['14-11-2016']

		$(document).ready(function() {
			$('.datepicker').datepicker({
				language : 'es',
				inline: true,
                sideBySide: true,
                autoclose: true,
                datesDisabled: forbidden,
                
                beforeShowDay: function(date) {
                    var hilightedDays = [1,3,8,20,21,16,26,30];
                    if (~hilightedDays.indexOf(date.getDate())) {
                       return {classes: 'highlight', tooltip: 'Title'};
                    }
                 }
      
			}).on('changeDate', function(ev){
	            // do what you want here
	            var date = $("#date").data("datepicker").getDate();
			    formatted = date.getDate()  + "-" + (date.getMonth() + 1) + "-" + date.getFullYear();
	            alert("han clikado en la fecha " + formatted);
	        })
	        ;

		})
	</script>

	<script>
		function myFunction() {
			var date = $("#date").data("datepicker").getDate(),
		    formatted = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours + ":" + date.getMinutes() + ":" + date.getSeconds();
		alert(formatted);
		}
	</script>
</body>
</html>
