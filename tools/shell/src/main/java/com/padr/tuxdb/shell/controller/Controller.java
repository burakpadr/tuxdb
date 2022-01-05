package com.padr.tuxdb.shell.controller;

import com.google.gson.JsonSyntaxException;

public class Controller {

    public static void handler(String block) throws JsonSyntaxException, Exception {
        String[] splitBlock = block.split("\\s++");

        if ((splitBlock.length == 1 && splitBlock[0].isEmpty())) {
            BuiltinController.help();

            return;
        }

        String command = splitBlock[0];

        if (splitBlock.length == 1) {
            switch (command) {
                case "help":
                    BuiltinController.help();

                    break;
                case "useDatabase":
                    BuiltinController.useDatabase();

                    break;
                case "useCollection":
                    BuiltinController.useCollection();

                    break;
                case "clear":
                    BuiltinController.clear();

                    break;
                case "exit":
                    BuiltinController.exit();

                    break;
                default:
                    System.out.println(String.format("No builtin command named %s found!", command));

                    break;
            }
        } else {
            String subcommand = splitBlock[1];

            if (command.equals("database")) {
                switch (subcommand) {
                    case "getDatabaseNames":
                        DatabaseController.getDatabaseNames();

                        break;
                    case "getName":
                        DatabaseController.getName();

                        break;
                    case "setName":
                        DatabaseController.setName();

                        break;
                    case "getCollectionNames":
                        DatabaseController.getCollectionNames();

                        break;
                    case "drop":
                        DatabaseController.drop();

                        break;
                    default:
                        System.out.println(String.format("No database command named %s found!", subcommand));

                        break;
                }
            } else if (command.equals("collection")) {
                switch (subcommand) {
                    case "getName":
                        CollectionController.getName();
                        
                        break;
                    case "setName":
                        CollectionController.setName();

                        break;
                    case "getAllObjects":
                        CollectionController.getAllObjects();

                        break;
                    case "findFromObjectId":
                        CollectionController.findFromObjectId();

                        break;
                    case "findOne":
                        CollectionController.findOne();

                        break;
                    case "find":
                        CollectionController.find();

                        break;
                    case "insert":
                        CollectionController.insert();

                        break;
                    case "updateFromObjectId":
                        CollectionController.updateFromObjectId();

                        break;
                    case "update":
                        CollectionController.update();

                        break;
                    case "deleteFromObjectId":
                        CollectionController.deleteFromObjectId();

                        break;
                    case "delete":
                        CollectionController.delete();

                        break;
                    case "drop":
                        CollectionController.drop();

                        break;
                    default:
                        System.out.println(String.format("No collection command named %s found!", subcommand));

                        break;
                }
            }
            else 
                System.out.println(String.format("No command named %s found!", command));
        }
    }
}
