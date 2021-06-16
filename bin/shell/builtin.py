import os

from env import Env
from connection import connection


def help() -> str:
    helpString = ""

    helpString += "\n-- Built-in functions --\n\n"
    helpString += "use(<databaseName : String>) : This process set the database to operate on\n"
    helpString += "showdbs() : This process shows all the database names\n"
    helpString += "clear() : This process clears the terminal\n"
    helpString += "exit() : Exit\n\n"

    helpString += "-- Other help functions --\n\n"
    helpString += "db.help() : This process shows all usable database processes\n"

    return helpString


def use(databaseName: str) -> str:
    if "/" in databaseName:
        return "Database name doesn't contain this symbol '/'"

    Env.database = databaseName

    return "Database was switched"


def showdbs() -> str:
    head = ["database", "getDatabaseNames"]

    connection.send(head, [])

    return connection.read()


def clear():
    os.system("clear || cls")
