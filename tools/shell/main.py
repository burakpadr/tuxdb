import readline

from src.constant import Constant
from src.controller.controller import Controller
from src.connection import connection


if __name__ == "__main__":
    print("\n——— Tuxdb Shell ———\n")
    print(f"Connection to -> http://{connection.getHost()}:{connection.getPort()}\n")
    print("\033[1mNote:\033[0m If you want to see usable commands, you can say help\n")

    while 1:
        block = input(Constant.COMMAND_PREFIX)

        Controller.handler(block)
