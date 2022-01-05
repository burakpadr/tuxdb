# TuxDB

TuxDB is a DBMS that is developed with No-SQL logic. If you interested in the design of the TuxDB-Core structure, you can review the design documents from [here](https://github.com/burakpadr/tuxdb/tree/main/docs/design)

# Run

```bash

# build
$ docker build -t tuxdb bin/
# and run
$ docker run --rm -it -v ~/.tuxdb:/app/data -p 6060:6060 tuxdb

```
# API

- **tuxpy**
 
	 You can review the tuxpy API developed for the Python Programming Language from [here](https://github.com/burakpadr/tuxdb/tree/main/api/tuxpy)
	 ![](https://raw.githubusercontent.com/burakpadr/tuxdb/main/docs/media/tuxpy.png)
	 
- **tuxjava**

	You can review the tuxjava API developed for the Java Programming Language from [here](https://github.com/burakpadr/tuxdb/tree/main/api/tuxjava)

# Benchmark

![](https://raw.githubusercontent.com/burakpadr/tuxdb/main/docs/media/tuxdb-benchmark.png)
