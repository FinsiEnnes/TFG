
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

/* Left padding for subtitles */
.subtitle {
    padding-left: 12px;
}

/* Left padding in the small attribute */
small {
    margin-left: 15px;
}

/* poner color a los hr */
hr { border-color: grey }


/* Increase size icons */
.gi-8x{font-size: 8em;}

/* Table on modal */
.table-responsive {
    max-height:154px;
    padding: 0px;
}

/* Add scrolls in the popup */
.modal .modal-body {
    max-height: 420px;
    overflow-y: auto;
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
				<li><a href="/persons"><font size="3">Personas</font></a></li>
				<li><a href="/materials"><font size="3">Materiales</font></a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</nav>
	
	
	<div class="container-fluid">
		<div class="row">
			
				<br><br>
				
				<!-- Warning of that project doesnt have customer 
    			================================================== -->
				<div class="row">
					<div class="form-group col-md-offset-2 col-md-8">
						<div class="well">
							<div class="row">
								<div class="form-group col-md-3">
									<span class="glyphicon glyphicon-exclamation-sign gi-8x" aria-hidden="true"></span>
								</div>

								<div class="form-group col-md-9">
									<h3>
										Atención!<br>
										<small>
											Este proyecto todavía no cuenta con ninguna tarea establecida. Por favor vuelve al 
										</small>
										<small>
										 	apartado <em>Planificación</em> para añadir actividades a tu proyecto.
										</small>
									</h3>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row">
				<div class="col-md-offset-5 col-md-2">
					<a href="/project/${idProject}/planning" class="btn btn-primary center-block">
					<span class="glyphicon glyphicon-arrow-left"></span>
							Volver a Planificación
					</a>
				</div>
				</div>
				</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	
	<!-- Bootstrap Validator
    ================================================== -->
	<script src="/webjars/bootstrap-validator/0.11.5/dist/validator.js"></script>
	<script src="/webjars/bootstrap-validator/0.11.5/dist/validator.min.js"></script>
	
	<!-- Bootstrap Table
    ================================================== -->
	<script src="/webjars/bootstrap-table/1.11.0/src/bootstrap-table.js"></script>
	
	
	<!-- Enable the add button when a customer is selected 
    ================================================== -->	
	<script type="text/javascript">
		$(document).ready(function() { 
		   $('#customerTable').change(function() { 
			   
			   // Get info of the table
			   var json = JSON.parse(JSON.stringify($('#customerTable').bootstrapTable('getSelections')));
			   var size = json.length;
			   
			   // If a option is selected then we can add a Person as manager
			   if (size > 0) {
				   var idPerson = json['0']['1'];
				   document.getElementById("idCustomer").value = idPerson;
				   document.getElementById("addButton").disabled = false;
			   } 
			   else {
				   document.getElementById("addButton").disabled = true;
			   }		        
		   }); 
		});
	</script>

</body>
</html>
