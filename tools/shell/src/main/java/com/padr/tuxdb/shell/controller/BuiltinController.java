package com.padr.tuxdb.shell.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.padr.tuxdb.shell.Constant;
import com.padr.tuxdb.shell.EnviormentVariable;

public class BuiltinController {

    public static void help() {
        String boldStyle = "\033[1m";
        String defaultStyle = "\033[0m";

        String help = "";

        help += String.format("\n%sBUILTIN COMMANDS%s\n\n", boldStyle, defaultStyle);

        help += "useDatabase : Select the database to operate on\n";
        help += "useCollection : Select the collection to operate on\n";
        help += "clear : Clear the terminal\n";
        help += "exit : Exit from the shell\n";

        help += String.format("\n%sDATABASE COMMANDS%s\n\n", boldStyle, defaultStyle);

        help += "database getDatabaseNames : Get the all database names available in tuxdb\n";
        help += "database getName : Get the name of database\n";
        help += "database setName : Rename the database\n";
        help += "database getCollectionNames : Get the collection names contained in the database used\n";
        help += "database drop : Delete used database\n";

        help += String.format("\n%sCOLLECTON COMMANDS%s\n\n", boldStyle, defaultStyle);

        help += "collection getName : Get the name of collection\n";
        help += "collection setName : Rename the collection\n";
        help += "collection getAllObjects : Get the all objects in the collection\n";
        help += "collection findFromObjectId : Find the object associated with object id\n";
        help += "collection findOne : Find an object associated with json query\n";
        help += "collection find : Find the objects associated with json query\n";
        help += "collection insert : Insert an object to the collection\n";
        help += "collection updateFromObjectId : Update the content of an object associated with object id\n";
        help += "collection update : Update the contents of the objects associated with json query\n";
        help += "collection deleteFromObjectId : Delete an object associated with object id\n";
        help += "collection delete :  Delete the objects associated json query\n";
        help += "collection drop : Delete the collection\n";

        System.out.println(help);
    }

    public static void useDatabase() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 

        System.out.print(String.format("%s Database Name: ", Constant.COMMAND_ANSWER_PREFIX));
        String databaseName = input.readLine();

        Pattern databaseNamePattern = Pattern.compile(".*[^-_.A-Za-z0-9].*");
        Matcher databaseNamePatternMatcher = databaseNamePattern.matcher(databaseName);

        if (databaseName.isEmpty() || databaseNamePatternMatcher.matches()) {
            System.out.println("The database name contains only a-Z, 0-9, -_.");

            return;
        }

        EnviormentVariable.databaseName = databaseName;

        System.out.println(String.format("Database name was changed to %s", databaseName));
    }

    public static void useCollection() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 

        System.out.print(String.format("%s Collection Name: ", Constant.COMMAND_ANSWER_PREFIX));
        String collectionName = input.readLine();

        Pattern collectionNamePattern = Pattern.compile(".*[^-_.A-Za-z0-9].*");
        Matcher collectionNamePatternMatcher = collectionNamePattern.matcher(collectionName);

        if (collectionName.isEmpty() || collectionNamePatternMatcher.matches()) {
            System.out.println("The collection name contains only a-Z, 0-9, -_.");

            return;
        }

        EnviormentVariable.collectionName = collectionName;

        System.out.println(String.format("Collection name was changed to %s", collectionName));
    }

    public static void clear() throws IOException {
        if (System.getProperty("os.name").contains("Windows"))
            Runtime.getRuntime().exec("cls");
        else
            System.out.print("\033\143");
    }

    public static void exit() {
        System.exit(0);
    }
}
