import json

from src.connection import connection
from src.environmentVariable import EnvironmentVariable
from src.constant import Constant


class DatabaseController:

    __SERVICE_NAME = "database"

    @staticmethod
    def getDatabaseNames():
        print(connection.send(
            DatabaseController.__SERVICE_NAME, "getDatabaseNames", {}))

    @staticmethod
    def createDatabase():
        print(connection.send(DatabaseController.__SERVICE_NAME,
              "createDatabase", {"databaseName": EnvironmentVariable.databaseName}))

    @staticmethod
    def getCollectionNames():
        print(connection.send(DatabaseController.__SERVICE_NAME,
              "getCollectionNames", {"databaseName": EnvironmentVariable.databaseName}))

    @staticmethod
    def setDatabaseName():
        newDatabaseName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}New Database Name: ")

        print(connection.send(DatabaseController.__SERVICE_NAME, "setDatabaseName", {
              "databaseName": EnvironmentVariable.databaseName, "newDatabaseName": newDatabaseName}))

    @staticmethod
    def drop():
        print(connection.send(DatabaseController.__SERVICE_NAME,
              "drop", {"databaseName": EnvironmentVariable.databaseName}))
