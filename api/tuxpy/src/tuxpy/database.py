from . import client
from . import collection


class Database:

    __SERVICE_NAME = "database"

    def __init__(self, client: client.Client, databaseName: str) -> None:
        self.__client = client
        self.__databaseName = databaseName

        databaseIsExist = bool(client._send(
            self.__SERVICE_NAME, "isExist", {"databaseName": databaseName}))

        if not databaseIsExist:
            client._send(self.__SERVICE_NAME, "createDatabase",
                         {"databaseName": databaseName})

    def getCollection(self, collectionName: str) -> collection.Collection:
        return collection.Collection(
            self.__client, self.__databaseName, collectionName)

    def getCollectionNames(self) -> list:
        return list(self.__client._send(self.__SERVICE_NAME, "getCollectionNames", {"databaseName": self.__databaseName}))

    def setDatabaseName(self, newDatabaseName: str) -> dict:
        response = dict(self.__client._send(self.__SERVICE_NAME, "setDatabaseName", {
                        "databaseName": self.__databaseName, "newDatabaseName": newDatabaseName}))

        if response.get("success"):
            self.__databaseName = newDatabaseName

        return response

    def drop(self) -> dict:
        return dict(self.__client._send(self.__SERVICE_NAME, "drop", {"databaseName": self.__databaseName}))

    def __str__(self) -> str:
        return self.__databaseName
