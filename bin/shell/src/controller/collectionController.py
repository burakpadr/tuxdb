import json

from src.connection import connection
from src.environmentVariable import EnvironmentVariable
from src.constant import Constant


class CollectionController:

    __SERVICE_NAME = "collection"

    @staticmethod
    def createCollection():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        print(connection.send(CollectionController.__SERVICE_NAME, "createCollection", [
              EnvironmentVariable.databaseName, collectionName]))

    @staticmethod
    def setCollectionName():
        oldCollectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Old Collection Name: ")

        newCollectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}New Collection Name: ")

        print(connection.send(CollectionController.__SERVICE_NAME, "setCollectionName", [
              EnvironmentVariable.databaseName, oldCollectionName, newCollectionName]))

    @staticmethod
    def getAllObjects():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        print(connection.send(CollectionController.__SERVICE_NAME, "getAllObjects", [
              EnvironmentVariable.databaseName, collectionName]))

    @staticmethod
    def findFromObjectId():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        objectId = input(f"{Constant.COMMAND_QUESTION_PREFIX}Object ID: ")

        print(connection.send(CollectionController.__SERVICE_NAME, "findFromObjectId", [
              EnvironmentVariable.databaseName, collectionName, objectId]))

    @staticmethod
    def find():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        query = json.loads(input(Constant.COMMAND_QUESTION_PREFIX +
                                 "Query (JSON Format like {'db': 'sample_db'}): "))

        print(connection.send(CollectionController.__SERVICE_NAME, "find", [
              EnvironmentVariable.databaseName, collectionName, query]))

    @staticmethod
    def insert():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        data = json.loads(input(Constant.COMMAND_QUESTION_PREFIX +
                                "Data (JSON Format like {'db': 'sample_db'}): "))

        print(connection.send(CollectionController.__SERVICE_NAME, "insert", [
              EnvironmentVariable.databaseName, collectionName, data]))

    @staticmethod
    def updateFromObjectId():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        objectId = input(f"{Constant.COMMAND_QUESTION_PREFIX}Object ID: ")

        updateData = json.loads(input(Constant.COMMAND_QUESTION_PREFIX +
                                      "Update Data (JSON Format like {'db': 'sample_db'}): "))

        print(connection.send(CollectionController.__SERVICE_NAME, "updateFromObjectId", [
              EnvironmentVariable.databaseName, collectionName, objectId, updateData]))

    @staticmethod
    def update():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        query = json.loads(input(Constant.COMMAND_QUESTION_PREFIX +
                                 "Query (JSON Format like {'db': 'sample_db'}): "))

        updateData = json.loads(input(Constant.COMMAND_QUESTION_PREFIX +
                                      "Update Data (JSON Format like {'db': 'sample_db'}): "))

        print(connection.send(CollectionController.__SERVICE_NAME, "update", [
              EnvironmentVariable.databaseName, collectionName, query, updateData]))

    @staticmethod
    def deleteFromObjectId():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        objectId = input(f"{Constant.COMMAND_QUESTION_PREFIX}Object ID: ")

        print(connection.send(CollectionController.__SERVICE_NAME, "deleteFromObjectId", [
              EnvironmentVariable.databaseName, collectionName, objectId]))

    @staticmethod
    def delete():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        query = json.loads(input(Constant.COMMAND_QUESTION_PREFIX +
                                 "Query (JSON Format like {'db': 'sample_db'}): "))

        print(connection.send(CollectionController.__SERVICE_NAME, "delete", [
              EnvironmentVariable.databaseName, collectionName, query]))

    @staticmethod
    def drop():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        print(connection.send(CollectionController.__SERVICE_NAME,
              "drop", [EnvironmentVariable.databaseName, collectionName]))
