package kavabase;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import kavabase.DataFormat.DataType;
import kavabase.fileFormat.Cell;
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
        FileOperations.createMetaData();
        RandomAccessFile raf = new RandomAccessFile(FileOperations.TABLES_PATH, "rw");
        Page page = FileOperations.readPage(raf, 0);
        Cell.LeafCell cell = (Cell.LeafCell)page.getCells().get(1);
        ArrayList<DataType> list = cell.getRecords();
        for(DataType d : list) {
            System.out.println(d.getData());
        }
        Cell.LeafCell cell2 = (Cell.LeafCell)page.getCells().get(2);
        ArrayList<DataType> list2 = cell2.getRecords();
        for(DataType d : list2) {
            System.out.println(d.getData());
        }
    }    
}
