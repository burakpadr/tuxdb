import os
import re

from src.constant import Constant
from src.environmentVariable import EnvironmentVariable


class BuiltinController:

    @staticmethod
    def help():
        boldStyle = "\033[1m"
        defaultStyle = "\033[0m"
        
        space = 2

        help = f"{boldStyle}\nBUILTIN COMMANDS{defaultStyle}\n"

        help += space * ' ' + "use : Select the database to operate on\n"
        help += space * ' ' + "name : Get currently used database name\n"
        help += space * ' ' + "clear : Clear the terminal\n"
        help += space * ' ' + "exit : Exit from the shell\n"

        help += f"{boldStyle}\nDATABASE COMMANDS{defaultStyle}\n"

        help += space * ' ' + "database getDatabaseNames : Get the all database names available in tuxdb\n"
        help += space * ' ' + "database createDatabase : Create a new database\n"
        help += space * ' ' + "database getCollectionNames : Get the collection names contained in the database used\n"
        help += space * ' ' + "database setDatabaseName : Rename currently used database\n"
        help += space * ' ' + "database drop : Delete currently used database\n"

        help += f"{boldStyle}\nCOLLECTION COMMANDS{defaultStyle}\n"

        help += space * ' ' + "collection createCollection : Create a collection \n"
        help += space * ' ' + "collection setCollectionName : Set the collection name\n"
        help += space * ' ' + "collection getAllObjects : Get the all objects in the collection\n"
        help += space * ' ' + "collection findFromObjectId : Find the object from object id\n"
        help += space * ' ' + "collection find : Find the objects from json query\n"
        help += space * ' ' + "collection insert : Insert an object to the collection\n"
        help += space * ' ' + "collection updateFromObjectId : Update the content of object associated with an object id\n"
        help += space * ' ' + "collection update : Update the content of an object associated with a json query\n"
        help += space * ' ' + "collection deleteFromObjectId : Delete an object associated with an object id\n"
        help += space * ' ' + "collection delete : Delete the objects associated with a json query\n"
        help += space * ' ' + "collection drop : Delete the collection\n"
        
        print(help)

    @staticmethod
    def use():
        databaseNameRegex = ".*[^-_.A-Za-z0-9].*"
        
        databaseName = input(f"{Constant.COMMAND_QUESTION_PREFIX}Database Name: ")

        if re.search(databaseNameRegex, databaseName):
           print("The database name contains only a-Z, 0-9, -_.")

           return

        EnvironmentVariable.databaseName = databaseName 

        print(f"Database name was changed to {databaseName}")
    
    @staticmethod
    def name():
        print(EnvironmentVariable.databaseName)

    @staticmethod
    def clear():
        os.system("cls" if os.name == "nt" else "clear")

    @staticmethod
    def exit():
        exit(0)
