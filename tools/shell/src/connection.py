import json
import requests

class Connection:

    def __init__(self):
        settingsFile = open("./settings.json", "r")

        connectionSettings = dict(
            dict(json.load(settingsFile)).get("connection"))

        self.__host = str(connectionSettings.get("host"))
        self.__port = int(connectionSettings.get("port"))

    def getHost(self):
        return self.__host

    def getPort(self):
        return self.__port

    def send(self, service: str, function: str, parameters: dict):
        return requests.post(f"http://{self.__host}:{self.__port}/{service}/{function}/", json=parameters).json()


connection = Connection()
