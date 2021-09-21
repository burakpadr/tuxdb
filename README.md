
# TuxDB

TuxDB is a DBMS that is developed with No-SQL logic. If you interested in the design of the TuxDB-Core structure, you can review the design document from [here](https://github.com/burakpadr/tuxdb/blob/main/docs/design/tuxdb-design.md)

# Run
  
 1. **Prerequisites**
 
	 - Java 11,  you can see the [instruction documents](https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A) to install
	 - Python 3.8.x or greater, you can see the [instruction documents](https://www.python.org/)

 2. **Run**

	 1- **Run the server**

	- Firstly, you should go to the **/bin/core** directory
	- Secondly, you should run the server with `$ java -cp tuxdb.jar com.padr.tuxdb.App` command
		
	2- **Run the shell**

	If you want to operate on the tuxdb, you should run the shell after the server starts running.

	- Firstly, you should go to **/bin/shell** directory
	- Secondly, you should run the shell with `$ python3 main.py` command
	 
		 **Note**: Shell connects to "host_address: 127.0.0.1, port: 6060" by default. If tuxdb's server runs in different address, you should change shell's connection point (host_address and port) from **/bin/shell/settings.json**

	  
