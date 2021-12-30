
# Tuxjava

Tuxjava is an API. It is developed to run processes located in Tuxdb with Java Programming Language.

# API

Tuxjava contains 3 modules. These are client, database and collection.

 1. **client**

	**Import Notation:** `import com.padr.tuxjava.Client` 

	 - `class com.padr.tuxjava.Client()`
	 
		It's a constructor method that connects to the Tuxdb by using default connection point (host="127.0.0.1", port=6060). 
		
	- `class com.padr.tuxjava.Client(String host, int port)`
	
		It's a constructor method that connects to the Tuxdb by using custom connection point.

         | parameters | description |
         |--|--|
         | host: String | This parameter specifies which host address to connect to. Also, this parameter must be the same value as tuxdb's server host value.  |
         |	port: int	| This parameter specifies which port to connect to. Also, this parameter must be the same value as tuxdb's server port value. |

		**Client Class Member Functions**

		- `com.padr.tuxjava.Database getDatabase(String databaseName)`
		
			This function returns a database object contained in Tuxdb. If there is no database associated with the databaseName parameter, the function creates a new database with the name specified by the databaseName parameter.
			
          | parameters | description  |
          |--|--|
          | databaseName: String | The name of the database to be accessed. |

		- `List<String> getDatabaseNames()`

			This function returns a list of all database names contained in the Tuxdb.
	
2. **database**

	**Import Notation:** `import com.padr.tuxjava.Database` 

	- `class tuxpy.database.Database(com.padr.tuxjava.Client client:, String databaseName)`

		This is a constructor method that returns a database object if there is database associated with the name specified by databaseName parameter. If there is no database, the constructor method creates a database with the name specified by databaseName parameter and then returns a database object.
		
      | parameters | description |
      |--|--|
      | client: com.padr.tuxjava.Client | It's a Client object connected to the Tuxdb. 
      |	databaseName: String | The name of the database to be accessed. |

		**Database Class Member Functions**

		 - `com.padr.tuxjava.Colletion getCollection(String collectionName)`

			This function returns a collection object found in the currently used database. If there is no collection associated with the collectionName parameter, the function creates a new collection for the used database with the name specified by the collectionName parameter.
	
            | parameters | description |
            |--|--|
            | collectionName: String | The name of the collection in the used database. |
            
		- `List<String> getCollectionNames()`

			This function returns a list of all collections found in the currently used database. 
            
		- `Map<String, Object> setDatabaseName(String newDatabaseName)`

			This function sets the name of used database. Returns a Map that indicates whether the result of the function was successful.

           | parameters | description |
           |--|--|
           | newDatabaseName: String | The new database name instead of the current name of the used database.|

		- `Map<String, Object> drop()`

			This function delete the used database from Tuxdb. Returns a Map that indicates whether the result of the function was successful.

3. **collection**

	**Import Notation:** `import com.padr.tuxjava.Collection` 

	- `class com.padr.tuxjava.Collection(com.padr.tuxjava.Client client, String databaseName, String collectionName)`

		This constructor method returns a collection object found in the database. If there is no collection associated with the collectionName parameter, the function creates a new collection for the database with the name specified by the collectionName parameter.

	
       | parameters | description |
       |--|--|
       | client: com.padr.tuxjava.Client | It's a Client object connected to the Tuxdb |
	   | databaseName: String | The name of the database to be accessed. |
	   | collectionName: String | The name of the collection in database to be accessed in the database. |

		**Colletion Class Member Functions**

		- `Map<String, Object> setCollectionName(String newCollectionName)`

			This function sets the name of used collection. Returns a Map that indicates whether the result of the function was successful.
			
             | parameters | description |
             |--|--|
             | newCollectionName: String| The new collection name instead of the current name of the used collection. |

		- `List<Map<String, Object>> getAllObjects()`
			
			Returns a list of all objects found in the used collection.

		- `Map<String, Object> findFromObjectId(String objectId)`

			This function returns a Map of the object content found in the used collection. 

            | parameters | description |
            |--|--|
            | objectId: String | The ID of object. |

		- `List<Map<String, Object>> find(Map<String, Object> query)`

			This function returns a list of the objects associated with the query.

           | parameters | description |
           |--|--|
           | query: Map<String, Object> | The query to catch the objects. |

		 - `Map<String, Object> insert(data: Map<String, Object>)`

			This function creates a new object into used collection. Returns a Map that indicates whether the result of the function was successful.

           | parameters | description |
           |--|--|
           | data: Map<String, Object> | Content of  new object |

		- `Map<String, Object> updateFromObjectId(String objectId, Map<String, Object> updateData)`

			This function updates a object content associated with the objectId. Returns a Map that indicates whether the result of the function was successful.
			
           | parameters | description |
           |--|--|
           | objectId: String | The ID of object. |
           | updateData: Map<String, Object> | It contains the names of the elements to be changed and their new values. |

		- `Map<String, Object> update(Map<String, Object> query, Map<String, Object> updateData)`

			This function updates the objects content associated with the query. Returns a Map that indicates whether the result of the function was successful.

           | parameters | description |
           |--|--|
           | query: Map<String, Object> | The query to catch the objects. |
           | updateData: Map<String, Object> | It contains the names of the elements to be changed and their new values. |

		- `Map<String, Object> deleteFromObjectId(String objectId)`

			This object deletes a object associated with the objectId. Returns a Map that indicates whether the result of the function was successful.

          | parameters | description |
          |--|--|
          | objectId: String | The ID of object. |

		- `Map<String, Object> delete(Map<String, Object> query)`

			This object deletes the objects associated with the query. Returns a Map that indicates whether the result of the function was successful.

          | parameters | description |
          |--|--|
          | query: dict | The query to catch the objects. |

		- `Map<String, Object> drop()`
		
			This function deletes the used collection. Returns a Map that indicates whether the result of the function was successful.
