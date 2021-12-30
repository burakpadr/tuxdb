# TuxDB

TuxDB is a DBMS that is developed with No-SQL logic. If you interested in the design of the TuxDB-Core structure, you can review the design documents from [here](https://github.com/burakpadr/tuxdb/blob/main/docs/design/tuxdb-design.md)

# Run
  
 - **Prerequisites**
 
	 - Java 11,  you can see the [instruction documents](https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A) to install
	 - Python3,  you can see the [instruction documents](https://www.python.org/) to install

```bash

# download with git tool
$ git clone https://github.com/burakpadr/tuxdb.git

# or download with gh tool
$ gh repo clone burakpadr/tuxdb

# run the server
$ java -cp core/bin/tuxdb.jar com.padr.tuxdb.App

#run the shell
$ cd tools/shell && python main.py

```
# API

- **tuxpy**
 
	 You can review the tuxpy API developed for the Python Programming Language from [here](https://github.com/burakpadr/tuxdb/api/tuxpy)
	 ![](https://raw.githubusercontent.com/burakpadr/tuxdb/main/docs/media/tuxpy.png)
	 
- **tuxjava**

	You can review the tuxpy API developed for the Python Programming Language from [https://github.com/burakpadr/tuxdb/api/tuxpy]

# Benchmark

![](https://raw.githubusercontent.com/burakpadr/tuxdb/main/docs/media/tuxdb-benchmark.png)
