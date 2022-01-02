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

        print(connection.send(CollectionController.__SERVICE_NAME, "createCollection", {
              "databaseName": EnvironmentVariable.databaseName, "collectionName": collectionName}))

    @staticmethod
    def setCollectionName():
        oldCollectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Old Collection Name: ")

        newCollectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}New Collection Name: ")

        print(connection.send(CollectionController.__SERVICE_NAME, "setCollectionName", {
              "databaseName": EnvironmentVariable.databaseName, "collectionName": oldCollectionName, "newCollectionName": newCollectionName}))

    @staticmethod
    def getAllObjects():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        print(connection.send(CollectionController.__SERVICE_NAME, "getAllObjects", {
              "databaseName": EnvironmentVariable.databaseName, "collectionName": collectionName}))

    @staticmethod
    def findFromObjectId():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        objectId = input(f"{Constant.COMMAND_QUESTION_PREFIX}Object ID: ")

        print(connection.send(CollectionController.__SERVICE_NAME, "findFromObjectId", {
              "databaseName": EnvironmentVariable.databaseName, "collectionName": collectionName, "objectId": objectId}))
    
    @staticmethod
    def findOne():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        query = json.loads(input(Constant.COMMAND_QUESTION_PREFIX +
                                 "Query (JSON Format like {'db': 'sample_db'}): "))

        print(connection.send(CollectionController.__SERVICE_NAME, "findOne", {
              "databaseName": EnvironmentVariable.databaseName, "collectionName": collectionName, "query": query}))

    @staticmethod
    def find():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        query = json.loads(input(Constant.COMMAND_QUESTION_PREFIX +
                                 "Query (JSON Format like {'db': 'sample_db'}): "))

        print(connection.send(CollectionController.__SERVICE_NAME, "find", {
              "databaseName": EnvironmentVariable.databaseName, "collectionName": collectionName, "query": query}))

    @staticmethod
    def insert():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        data = json.loads(input(Constant.COMMAND_QUESTION_PREFIX +
                                "Data (JSON Format like {'db': 'sample_db'}): "))

        print(connection.send(CollectionController.__SERVICE_NAME, "insert", {
              "databaseName": EnvironmentVariable.databaseName, "collectionName": collectionName, "data": data}))

    @staticmethod
    def updateFromObjectId():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        objectId = input(f"{Constant.COMMAND_QUESTION_PREFIX}Object ID: ")

        updateData = json.loads(input(Constant.COMMAND_QUESTION_PREFIX +
                                      "Update Data (JSON Format like {'db': 'sample_db'}): "))

        print(connection.send(CollectionController.__SERVICE_NAME, "updateFromObjectId", {
              "databaseName": EnvironmentVariable.databaseName, "collectionName": collectionName, "objectId": objectId, "updateData": updateData}))

    @staticmethod
    def update():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        query = json.loads(input(Constant.COMMAND_QUESTION_PREFIX +
                                 "Query (JSON Format like {'db': 'sample_db'}): "))

        updateData = json.loads(input(Constant.COMMAND_QUESTION_PREFIX +
                                      "Update Data (JSON Format like {'db': 'sample_db'}): "))

        print(connection.send(CollectionController.__SERVICE_NAME, "update", {
              "databaseName": EnvironmentVariable.databaseName, "collectionName": collectionName, "query": query, "updateData": updateData}))

    @staticmethod
    def deleteFromObjectId():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        objectId = input(f"{Constant.COMMAND_QUESTION_PREFIX}Object ID: ")

        print(connection.send(CollectionController.__SERVICE_NAME, "deleteFromObjectId", {
              "databaseName": EnvironmentVariable.databaseName, "collectionName": collectionName, "objectId": objectId}))

    @staticmethod
    def delete():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        query = json.loads(input(Constant.COMMAND_QUESTION_PREFIX +
                                 "Query (JSON Format like {'db': 'sample_db'}): "))

        print(connection.send(CollectionController.__SERVICE_NAME, "delete", {
              "databaseName": EnvironmentVariable.databaseName, "collectionName": collectionName, "query": query}))

    @staticmethod
    def drop():
        collectionName = input(
            f"{Constant.COMMAND_QUESTION_PREFIX}Collection Name: ")

        print(connection.send(CollectionController.__SERVICE_NAME,
              "drop", {"databaseName": EnvironmentVariable.databaseName, "collectionName": collectionName}))
