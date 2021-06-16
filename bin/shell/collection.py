from connection import connection
from env import Env


class Collection:

    __CORE_COMMAND = "collection"

    @staticmethod
    def help():
        helpString = ""

        helpString += "\n-- Collection functions --\n\n"
        helpString += "db.get(<collection name : String>).setCollectionName(<new collection name : String>) : This process sets the collection name\n"
        helpString += "db.get(<collection name : String>).getAllObjects() : This process shows all the objects in the collection\n"
        helpString += "db.get(<collection name : String>).findFromObjectId(<object id : String>) : This process finds a object associated with the object id in the collection\n"
        helpString += "db.get(<collection name : String>).find(<query : JSON>) : This process find the objects associated with the query in the collection \n"
        helpString += "db.get(<collection name : String>).insert(<data : JSON>) : This process inserts a object into the collection\n"
        helpString += "db.get(<collection name : String>).updateFromObjectId(<object id : String>, <update data : JSON>) : This process updates a object associated with the object id in the collection\n"
        helpString += "db.get(<collection name : String>).update(<query : JSON>, <update data : JSON>) : This process updates the object associated with the query in the collection\n"
        helpString += "db.get(<collection name : String>).deleteFromObjectId(<object id : String>) : This process deletes a object associated with the object id in the collection\n"
        helpString += "db.get(<collection name : String>).delete(<query : JSON>) : This process deletes a object associated with the query in the collection\n"
        helpString += "db.get(<collection name : String>).drop() : This process delete the collection from the currently used database\n"
        
        return helpString

    def __init__(self, collectionName: str):
        self.__collectionName = collectionName

    def setCollectionName(self, newCollectionName: str):
        if "/" in newCollectionName:
            return "New collection name doesn't contain this symbol '/'"

        head = [Collection.__CORE_COMMAND, "setCollectionName"]
        body = [self.__collectionName, newCollectionName]

        connection.send(head, body)

        return connection.read()

    def getAllObjects(self):
        head = [Collection.__CORE_COMMAND, "getAllObjects"]
        body = [self.__collectionName]

        connection.send(head, body)

        return connection.read()

    def findFromObjectId(self, objectId: str):
        head = [Collection.__CORE_COMMAND, "findFromObjectId"]
        body = [self.__collectionName, objectId]

        connection.send(head, body)

        return connection.read()

    def find(self, query: dict):
        head = [Collection.__CORE_COMMAND, "find"]
        body = [self.__collectionName, query]

        connection.send(head, body)

        return connection.read()

    def insert(self, data: dict):
        head = [Collection.__CORE_COMMAND, "insert"]
        body = [self.__collectionName, data]

        connection.send(head, body)

        return connection.read()

    def updateFromObjectId(self, objectId: str, updateData: dict):
        head = [Collection.__CORE_COMMAND, "updateFromObjectId"]
        body = [self.__collectionName, objectId, updateData]

        connection.send(head, body)

        return connection.read()

    def update(self, query: dict, updateData: dict):
        head = [Collection.__CORE_COMMAND, "update"]
        body = [self.__collectionName, query, updateData]

        connection.send(head, body)

        return connection.read()

    def deleteFromObjectId(self, objectId: str):
        head = [Collection.__CORE_COMMAND, "deleteFromObjectId"]
        body = [self.__collectionName, objectId]

        connection.send(head, body)

        return connection.read()

    def delete(self, query: dict):
        head = [Collection.__CORE_COMMAND, "delete"]
        body = [self.__collectionName, query]

        connection.send(head, body)

        return connection.read()

    def drop(self):
        head = [Collection.__CORE_COMMAND, "dropCollection"]
        body = [self.__collectionName]

        connection.send(head, body)

        return connection.read()

    def __str__(self) -> str:
        return f"{Env.database}.{self.__collectionName}"
