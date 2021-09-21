import socket
import json


class Connection:

    def __init__(self):
        self.__client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        settingsFile = open("./settings.json", "r")

        connectionSettings = dict(
            dict(json.load(settingsFile)).get("connection"))

        self.__host = connectionSettings.get("host")
        self.__port = connectionSettings.get("port")

    def getHost(self):
        return self.__host

    def getPort(self):
        return self.__port
    def connect(self):
        self.__client.connect((self.__host, self.__port))

    def send(self, service: str, function: str, parameters: list):
        data = {
            "service": service,
            "function": function,
            "parameters": parameters
        }

        self.__client.sendall(json.dumps(data).encode("utf-8"))

        return self.__client.recv(1024).decode("utf-8")


connection = Connection()

connection.connect()
