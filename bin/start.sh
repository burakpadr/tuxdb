#!/bin/bash

java -cp tuxdb-core.jar com.padr.tuxdb.App &

sleep .3s

java -cp tuxdb-shell.jar com.padr.tuxdb.shell.App
