package kavabase.fileFormat;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import kavabase.DataFormat.DataType;

/**
 *
 * @author Anton
 */
public class FileOperations {
    
    public static void createEmptyPage(String file, byte pageType) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.write(pageType);
        raf.writeByte(0);
        raf.writeShort(512);
        raf.writeInt(0);
        raf.close();
    }
    
    public static boolean insert(final String file, 
                              final ArrayList<Column> record) throws IOException {
        LeafCell leafCell = new LeafCell(record);
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.seek(PageHeader.CELLS_START_OFFSET);
        short cellsStartContentArea = raf.readShort();
        if (cellsStartContentArea == Page.PAGE_SIZE) {
            //The table is empty, insert record directly
            raf.seek(Page.PAGE_SIZE - leafCell.getPayloadSize() - 
                    LeafCell.LEAF_CELL_HEADER_SIZE);
            insertRecord(leafCell, raf);
            return true;
        }
        
        return true;
    }
    
    public static void insertRecord(final LeafCell leafCell, 
                                    final RandomAccessFile raf) throws IOException {
        raf.writeShort(leafCell.getPayloadSize());
        raf.writeInt(leafCell.getKey());
        raf.writeByte(leafCell.getNumberOfColumns());
        for (int i = 0; i < leafCell.getRecord().size(); i++) {
            DataType dataType = leafCell.getRecord().get(i).getDataType();
            raf.writeByte(dataType.getSerialTypeCode());
            raf.write(dataType.toByteArray());           
        }
    }
}
