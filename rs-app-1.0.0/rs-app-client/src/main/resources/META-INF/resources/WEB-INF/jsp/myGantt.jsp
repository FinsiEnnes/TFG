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
	<div class="container">
		<div id="gantt_here" style='width: 1200px; height: 150px;'></div>
	</div>
	
	<script type="text/javascript">
		gantt.config.start_date = new Date(2016, 10, 01);
		gantt.config.end_date = new Date(2016, 10, 29);	
		gantt.config.readonly = true;
		gantt.config.date_grid = "%d-%m-%Y";
		gantt.config.grid_width = 450;
		
		
		gantt.config.columns =  [
		                         {name:"text",       label:"Task name",  tree:true, width:'*'},
		                         {name:"start_date", label:"Start time", align: "center", width:100 },
		                         {name:"end_date",   label:"End date",   align: "center", width:100 },
		                         {name:"progress",   label:"Progress",   align: "center", width:80 },
		                     ];
		
		var tasks = {
				 data:[
				        {id:2, text:"Phase #1", color:"#2eb82e"},
				        {id:3, text:"Task #1", start_date:"02-11-2016", duration:3, parent:2, progress:"0.2"},
				        {id:4, text:"Alpha release", duration:0, parent:2, start_date:"06-11-2016", color:"#ff1ac6"},
				        {id:5, text:"Task #2", start_date:"07-11-2016", duration:3, parent:2}],
				links:[
				               {id:1, source:4, target:5, type:"0"},
				               {id:2, source:3, target:4, type:"0"}
				            ]
		};
		gantt.init("gantt_here");
		gantt.parse(tasks);

	</script>
	
	<script type="text/javascript">
		gantt.attachEvent("onTaskDblClick", function(id,item){
			alert("ID tarea clickada: " + id);
			/* window.location.href = "http://localhost:8080/persons"; */
		});
	</script>

	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>