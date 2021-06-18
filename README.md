# TuxDB

TuxDB is a DBMS that is developed with No-SQL logic. If you interested in the design of the TuxDB-Core structure, you can review the design document from [here](https://github.com/burakpadr/tuxdb/blob/main/docs/design/tuxdb-design.md)

# Run
  
 - **Prerequisites**
 
	 - Java 11,  you can see the [instruction documents](https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A) to install
	 - Python 3.8.x or greater, you can see the [instruction documents](https://www.python.org/)

 - **Run**

	 1- **Run the server**

	- Firstly, you should go to the **/bin/core** directory
	- Secondly, you should run the server with the
			`$ java -cp tuxdb-core.jar com.padr.tuxdb.App` command
		
	2- **Run the shell**

	If you want to operate on the database, you should run the shell after the server starts running.

	- Firstly, you should go to the **/bin/shell** directory
	- Secondly, you should run the shell with the 
			`$ python3 main.py` command
	  

