<html>
<head>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<table class="table table-striped">
			<caption>Person Information</caption>

			<thead>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Nif</th>
					<th>Email</th>
				</tr>
			</thead>

			<tbody>
				<tr>
					<td>${id}</td>
					<td>${name}</td>
					<td>${nif}</td>
					<td>${email}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>