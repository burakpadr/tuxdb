from src.controller.builtinController import BuiltinController
from src.controller.databaseController import DatabaseController
from src.controller.collectionController import CollectionController


class Controller:

    @staticmethod
    def handler(block: str) -> None:
        splitBlock = block.split()

        # Catch the illegal block length. The length of the block must be 1 or 2

        if len(splitBlock) == 0 or len(splitBlock) > 2:
            BuiltinController.help()

            return

        # Select the controller to run

        command = splitBlock.__getitem__(0)

        if len(splitBlock) == 1:
            if command == "help":
                BuiltinController.help()
            elif command == "use":
                BuiltinController.use()
            elif command == "name":
                BuiltinController.name()
            elif command == "clear":
                BuiltinController.clear()
            elif command == "exit":
                BuiltinController.exit()
            else:
                print(f"No builtin command named {command} found!")
        else:
            subcommand = splitBlock.__getitem__(1)

            if command == "database":
                if subcommand == "getDatabaseNames":
                    DatabaseController.getDatabaseNames()
                elif subcommand == "getDatabaseSize":
                    DatabaseController.getDatabaseSize()
                elif subcommand == "createDatabase":
                    DatabaseController.createDatabase()
                elif subcommand == "getCollectionNames":
                    DatabaseController.getCollectionNames()
                elif subcommand == "getCollectionSize":
                    DatabaseController.getCollectionSize()
                elif subcommand == "setDatabaseName":
                    DatabaseController.setDatabaseName()
                elif subcommand == "drop":
                    DatabaseController.drop()
                else:
                    print(f"No database command named {subcommand} found!")
            elif command == "collection":
                if subcommand == "createCollection":
                    CollectionController.createCollection()
                elif subcommand == "setCollectionName":
                    CollectionController.setCollectionName()
                elif subcommand == "getAllObjects":
                    CollectionController.getAllObjects()
                elif subcommand == "findFromObjectId":
                    CollectionController.findFromObjectId()
                elif subcommand == "find":
                    CollectionController.find()
                elif subcommand == "insert":
                    CollectionController.insert()
                elif subcommand == "updateFromObjectId":
                    CollectionController.updateFromObjectId()
                elif subcommand == "update":
                    CollectionController.update()
                elif subcommand == "deleteFromObjectId":
                    CollectionController.deleteFromObjectId()
                elif subcommand == "delete":
                    CollectionController.delete()
                elif subcommand == "drop":
                    CollectionController.drop()
                else:
                    print(
                        f"No collection command named {subcommand} found!")
            else:
                print(f"No command named {command} found!")
