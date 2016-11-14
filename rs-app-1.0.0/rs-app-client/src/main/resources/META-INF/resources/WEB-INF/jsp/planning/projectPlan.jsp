
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>

<title>Dashboard Template for Bootstrap</title>

<!-- Bootstrap core CSS -->
<link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom colors for the navigation bar -->
<link href="/css/navbarColors.css" rel="stylesheet">

<!-- Gantt chart  -->
<script src="/dhtmlxGantt/codebase/dhtmlxgantt.js"></script>
<link href="/dhtmlxGantt/codebase/dhtmlxgantt.css" rel="stylesheet">


<style>
body {
	padding-top: 50px;
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
				<li><a href="#"><font size="3">Proyecto</font></a></li>
				<li><a href="#"><font size="3">Tarea</font></a></li>
				<li><a href="#"><font size="3">Hito</font></a></li>
				<li><a href="#"><font size="3">Personas</font></a></li>
				<li><a href="#"><font size="3">Materiales</font></a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</nav>
	
	<!-- Content of the interface
    ================================================== -->
	<div class="container-fluid">
		<div class="row">
			<div id="gantt_here" style='width: 1366px; height: 500px;'></div>
		</div>
	</div>


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>


	<!-- Gantt chart configuration
    ================================================== -->
	<script type="text/javascript">
	
		/* Main configurations */
		gantt.config.readonly = true;
		gantt.config.date_grid = "%d-%m-%Y";
		gantt.config.grid_width = 450;
		
		
		/* Set the start and end date of the diagrma */
		gantt.config.start_date = new Date(2016, 10, 01);
		gantt.config.end_date = new Date(2016, 10, 29);


		/* Specifying Columns for the grid */
		gantt.config.columns =  [
			{name:"text",       label:"Nombre",  tree:true, width:'*' },
			{name:"start_date", label:"Comienzo",   align: "center", width : 100},
			{name:"duration",   label:"Duración",   align: "center", width : 100},
			{name:"end_date",   label:"Fin",        align: "center", width : 100}
		];
		
		/* Initialize the Gantt chart */
		gantt.init("gantt_here");
		
		/* Load the data (phases, tasks and milestones) that coming from the controller  */
		gantt.parse(${tasks});
	</script>

</body>
</html>
