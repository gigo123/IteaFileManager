package konsoleFm;

import java.io.Console;
import java.io.File;

import static java.lang.System.exit;

public class App {
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.err.print(" no conlose found");
            exit(1);
        }
        File file = new File(".");
        String directoryPath = file.getAbsolutePath();
        directoryPath += ">";
        System.out.println( " File meneger for console ver 1");
        System.out.println( " base command");
        System.out.println( " cd");
        System.out.println( " dir");
        System.out.println( " exit");
        System.out.println(directoryPath + " Enter command");


        boolean exitB =true;
        while(exitB) {
            System.out.print(directoryPath);
            String fileComand = console.readLine();
            String[] parseComand = fileComand.split(" ");
            String parseCom = parseComand[0];


            switch (parseCom) {
                case "cd":
                    if(parseComand.length<2){
                        System.out.print(directoryPath);
                        System.out.println(" no directory specifed");
                        break;
                    }
                    file = FileManegerConsole.myCD(file, new File(parseComand[1]));
                    directoryPath = file.getAbsolutePath();
                    directoryPath += ">";
                    break;
                case "cd..":
                    System.out.println(file);
                    file = FileManegerConsole.myCDreturn(file);
                    directoryPath = file.getAbsolutePath();
                    directoryPath += ">";
                    break;
                case "dir":
                    File[] directoryList = FileManegerConsole.myDIRall(file);
                    if (directoryList == null) {
                        break;
                    }
                    for (File dfile : directoryList) {
                        System.out.println(dfile.getName());
                    }
                    break;
                case "exit":
                    exitB = false;
                    break;
                default:
                    System.out.print(directoryPath);
                    System.out.println("invalid command");;
            }


        }
        System.out.println("exit from FM");
    }
}

