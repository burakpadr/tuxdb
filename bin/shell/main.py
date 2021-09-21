import readline

from src.constant import Constant
from src.controller.controller import Controller
from src.connection import connection

def version():
    return "v1.1.0"


if __name__ == "__main__":
    print(f"\n——— Tuxdb Shell {version()} ———\n")
    print(f"Connection to -> {connection.getHost()}:{connection.getPort()}\n")

    while 1:
        block = input(Constant.COMMAND_PREFIX)

        Controller.handler(block)
