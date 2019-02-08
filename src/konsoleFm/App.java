package konsoleFm;

import java.io.Console;
import java.io.File;
import java.nio.file.Path;

import static java.lang.System.exit;

public class App {
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.err.print(" no conlose found");
            exit(1);
        }
        File file = new File("");
        file= file.getAbsoluteFile();
        Path directoryPath = file.toPath();

        System.out.println( " File meneger for console ver 1");
        System.out.println( " base command");
        System.out.println( " cd");
        System.out.println( " dir");
        System.out.println( " exit");
        System.out.println(directoryPath + "> Enter command");


       boolean exitB =true;
        while(exitB) {
            System.out.print(directoryPath + "\\>");
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
                    if(parseComand[1].equals("..")){
                        directoryPath = NioFileComands.myCDreturn(directoryPath);
                        break;
                    }
                   // System.out.println((new File(parseComand[1])).toPath());
                    directoryPath = NioFileComands.myCd(directoryPath, (new File(parseComand[1])).getAbsoluteFile().toPath());
                    //directoryPath = file.getAbsolutePath();
                    break;
                case "cd..":
                   // System.out.println(file);
                    directoryPath = NioFileComands.myCDreturn(directoryPath);
                    break;
                case "dir":
                    Path[] directoryList = NioFileComands.myDIRallPath(directoryPath,true);
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

