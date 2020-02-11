Requirements:
JDK Version 1.8
JUnit 4
Added Jackson dependencies in Maven for JSON
Maven 3.6.0
=================================================================

Problem Description:
	ETL Process to find top 3 F1 Driver's with average of lap times

==================================================================

Solution:
	ETLProcess class has implementation of ETL Process. User can pass input (Input CSV File) as maven args
	Process creates OutputFile.json which contains top 3 performers.
	
Code has 92% Junit Coverage	