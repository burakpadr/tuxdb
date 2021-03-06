## TuxDB Design

  TuxDB consists of two parts, **Shell** and **Core**.

![](https://raw.githubusercontent.com/burakpadr/tuxdb/main/docs/media/tuxdb-general-design.jpeg)

## **Shell**
- The shell decides which service to send the commands entered by the user. After making a decision, it sends the necessary data to the service.  

## **Core.Server**

- The server contains multiple services. It reports the data from the shell to the engine for processing this data.

## **Core.Engine**

- If the engine supports the requested process, it processes the data from the server.
-   The engine is an organization that hosts the components that make up the database
- The engine consist of two parts, **process** and **storage**.

![](https://raw.githubusercontent.com/burakpadr/tuxdb/main/docs/media/core-engine-design.png)

## **Core.Engine.Process**

- The process section contains the processes that works on the database.

 ## **Core.Engine.Storage**

-   The storage is a component that creates the file system needed by the database.
