
Running the project example
-----------------------------

+ Running the app service with Maven/Jetty.

	cd rs-app/rs-app-service
	mvn jetty:run

+ Running the app client application

	Configure rs-app/rs-app-client/src/main/resources/ConfigurationParameters.properties
	for specifying the client project service implementation (XML or JSON) and 
	the port number of the web server in the endpoint address (7070 for Jetty, 8080
	for Tomcat)

	* AddXX
		mvn exec:java -Dexec.mainClass="es.udc.rs.app.client.ui.XXServiceClient" -Dexec.args="-a '...'"


