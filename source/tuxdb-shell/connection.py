import socket
import json

from env import Env


class Connection:

    __HOST = "192.168.1.27"
    __PORT = 6060

    def __init__(self):
        self.__client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    def connect(self):
        self.__client.connect((self.__HOST, self.__PORT))

    def send(self, head: list, body: list):
        data = {
            "env": Env.getEnvAsDictionary(),
            "head": head,
            "body": body
        }

        self.__client.sendall(json.dumps(data).encode("utf-8"))

    def read(self):
        return self.__client.recv(1024).decode("utf-8")


connection = Connection()

connection.connect()
