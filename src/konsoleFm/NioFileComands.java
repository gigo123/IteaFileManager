package konsoleFm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

public class NioFileComands {

    public NioFileComands() {
    }

    public static Path myCd(Path curentPath, Path newPath) {

        return Files.isDirectory(newPath) ? newPath : curentPath;
    }

    public static Path myCDreturn(Path curentPath) {
        Path retPath = curentPath.getParent();
        if (retPath == null) {
            return curentPath;
        }
        return retPath;
    }

    public static Path[] myDIRallPath(Path curentPath) {

        try {
            Stream<Path> list1 = Files.list(curentPath);
            if (list1.count() == 0) {
                return null;
            }
            int j = 0;
            int size = (int) list1.count();
            Path[] dir = new Path[size];

            Iterator<Path> iter1 = list1.iterator();
            while (iter1.hasNext()) {
                Path p1 = iter1.next();
                if (Files.isDirectory(p1)) {
                    dir[j] = p1;
                    j++;
                }
            }
            Iterator<Path> iter2 = list1.iterator();
            while (iter2.hasNext()) {
                while (!iter1.hasNext()) {
                    Path p1 = iter1.next();
                    if (Files.isDirectory(p1)) {
                        dir[j] = p1;
                        j++;
                    }
                }
            }
            return dir;
        } catch (IOException e) {
            System.out.println("error");
            return null;
        }
    }
    public static boolean myRm(Path curentPath){

        try {
            Files.delete(curentPath);
            return  true;
        } catch (IOException e) {
           return  false;
        }
    }
}
