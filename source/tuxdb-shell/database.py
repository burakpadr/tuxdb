import json

from connection import connection
from env import Env
from collection import Collection


class Database:

    __CORE_COMMAND = "database"

    @staticmethod
    def help() -> str:
        helpString = ""

        helpString += "\n-- Database functions --\n\n"
        helpString += "db.createDatabase() : This process creates a new database with switched database name\n"
        helpString += "db.getName() : This process shows name of the currently used database\n"
        helpString += "db.createCollection(<collection name : String>) : This process creates a collection into the currently used database\n"
        helpString += "db.getCollectionNames() : This process shows all the collection names that the database contains\n"
        helpString += "db.get(<collection name : String>) : This process gets the collection that the database contains\n"
        helpString += "db.getCollectionSize() : This process shows the total collection size that the database contains\n"
        helpString += "db.setDatabaseName(<new database name: String>) : This process sets the database name of the currently used database\n"
        helpString += "db.drop() : This process deletes the currently used database\n"

        helpString += Collection.help()

        return helpString

    @staticmethod
    def createDatabase() -> str:
        head = [Database.__CORE_COMMAND, "createDatabase"]

        connection.send(head, [])

        return connection.read()

    @staticmethod
    def getName() -> str:
        head = [Database.__CORE_COMMAND, "getDatabaseName"]

        connection.send(head, [])

        return connection.read()

    @staticmethod
    def createCollection(collectionName: str) -> str:
        head = [Database.__CORE_COMMAND, "createCollection"]
        body = [collectionName]

        connection.send(head, body)

        return connection.read()

    @staticmethod
    def getCollectionNames() -> str:
        head = [Database.__CORE_COMMAND, "getCollectionNames"]

        connection.send(head, [])

        return connection.read()

    @staticmethod
    def get(collectionName: str):
        return Collection(collectionName)

    @staticmethod
    def getCollectionSize() -> int:
        head = [Database.__CORE_COMMAND, "getCollectionSize"]

        connection.send(head, [])

        return connection.read()

    @staticmethod
    def setDatabaseName(newDatabaseName: str):
        head = [Database.__CORE_COMMAND, "setDatabaseName"]
        body = [newDatabaseName]

        connection.send(head, body)

        response = dict(json.loads(connection.read()))
        
        if response.get("success") == 1:
            Env.database = newDatabaseName

        return response

    @staticmethod
    def drop():
        head = [Database.__CORE_COMMAND, "dropDatabase"]

        connection.send(head, [])

        return connection.read()

    def __str__(self):
        return Env.database
