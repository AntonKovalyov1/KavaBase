package kavabase;

import java.io.IOException;
import java.io.RandomAccessFile;
import kavabase.fileFormat.FileOperations;
import kavabase.fileFormat.Page;

/**
 *
 * @author Anton
 */
public class MainApp {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //Prompt.run();
        //FileOperations.createMetaData();
        RandomAccessFile raf = new RandomAccessFile(FileOperations.TABLES_PATH, "rw");
        Page page = FileOperations.readPage(raf, 0);
        //FileOperations.getMetaData();
        System.out.println(FileOperations.getMetaData().
                get("davisbase_columns").getColumns().get(5).getColumnName());
    }    
}
