<!DOCTYPE html>
<html>
<head>
<title>Start with dhtmlxGantt</title>

<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<script src="/dhtmlxGantt/codebase/dhtmlxgantt.js"></script>
<link href="/dhtmlxGantt/codebase/dhtmlxgantt.css" rel="stylesheet">
</head>
<body>
	<br>
	<div id="gantt_here" style='width: 1000px; height: 400px;'></div>
	<script type="text/javascript">
		gantt.config.start_date = new Date(2016, 10, 01);
		gantt.config.end_date = new Date(2016, 10, 29);	
		gantt.config.readonly = true;
		gantt.config.date_grid = "%d/%m/%Y";
		
		var tasks = {
				  data:[
				     {id:1, text:"Project #1", start_date:"01-11-2016", duration:18},
				     {id:2, text:"Task #1", start_date:"02-11-2016", duration:8, parent:1},
				     {id:3, text:"Task #2", start_date:"11-11-2016", duration:8, parent:1}
				   ]
				};
		gantt.init("gantt_here");
		gantt.parse(tasks);

	</script>
	
	<script type="text/javascript">
		gantt.attachEvent("onTaskDblClick", function(id,item){
			alert("tarea clickada");
			/* window.location.href = "http://localhost:8080/persons"; */
		});
	</script>

	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>