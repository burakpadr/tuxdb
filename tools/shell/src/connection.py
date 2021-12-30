import json
from urllib.parse import urlencode
from urllib.request import Request, urlopen


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
        request = Request(
            f"http://{self.__host}:{self.__port}/{service}/{function}/", urlencode(parameters).encode())

        return json.loads(urlopen(request).read().decode())


connection = Connection()
