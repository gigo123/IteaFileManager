package konsoleFm;

import java.io.File;

public class FileManegerConsole {
    public FileManegerConsole() {
    }

    public static File myCDreturn(File file) {
        File retfile = file.getAbsoluteFile();
        retfile = retfile.getParentFile();
        return retfile == null ? file : retfile;
    }

    public static File myCD(File fileCur, File fileNew) {
        return !fileNew.isDirectory() ? fileCur : fileNew;
    }

    public static File[] myDIRall(File file) {
        File[] dir = file.listFiles();
        if (dir == null) {
            return null;
        } else {
            File[] dirSort = new File[dir.length];
            int j = 0;

            for(int i = 0; i < dir.length; ++i) {
                if (dir[i].isDirectory()) {
                    dirSort[j] = dir[i];
                    ++j;
                }
            }

            File[] var8 = dir;
            int var5 = dir.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                File aWinDir = var8[var6];
                if (aWinDir.isFile()) {
                    dirSort[j] = aWinDir;
                    ++j;
                }
            }

            return dirSort;
        }
    }

    public static String[] myDIRallString(File file) {
        File[] dir = file.listFiles();
        if (dir == null) {
            return null;
        } else {
            String[] dirSort = new String[dir.length];
            int j = 0;

            for(int i = 0; i < dir.length; ++i) {
                if (dir[i].isDirectory()) {
                    dirSort[j] = dir[i].getName();
                    ++j;
                }
            }

            File[] var8 = dir;
            int var5 = dir.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                File aWinDir = var8[var6];
                if (aWinDir.isFile()) {
                    dirSort[j] = aWinDir.getName();
                    ++j;
                }
            }

            return dirSort;
        }
    }
}
