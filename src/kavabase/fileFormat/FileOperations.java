package kavabase.fileFormat;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import kavabase.DataFormat.Comparison;
import kavabase.DataFormat.DataType;
import kavabase.DataFormat.SerialType;
import kavabase.fileFormat.Cell.LeafCell;

/**
 *
 * @author Anton
 */
public class FileOperations {
    
    public final static String TABLES_PATH = "data/catalog/davisbase_tables.tbl";
    public final static String COLUMNS_PATH = "data/catalog/davisbase_columns.tbl";
    
    public static void createEmptyPage(String file, byte pageType) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.write(pageType);
        raf.writeByte(0);
        raf.writeShort(512);
        raf.writeInt(0);
        raf.close();
    }
    
    public static void insertRecord(final ArrayList<Column> record, 
                                    final RandomAccessFile raf) throws IOException {
        LeafCell leafCell = new LeafCell(record);
        raf.writeShort(leafCell.getPayloadSize());
        raf.writeInt(leafCell.getKey());
        raf.writeByte(leafCell.getNumberOfColumns());
        for (int i = 0; i < leafCell.getRecord().size(); i++) {
            DataType dataType = leafCell.getRecord().get(i).getDataType();
            raf.writeByte(dataType.getSerialTypeCode());
            raf.write(dataType.toByteArray());           
        }
    }
    
    public static void createMetaData() {
        createTablesFile();
        createColumnsFile();
    }
    
    public static void createTablesFile() {
        //Create tables leaf cell
        ArrayList<Column> tables = new ArrayList<>();
        tables.add(new Column("rowid", true, false, 0, new DataType.Int(1)));
        tables.add(new Column("tables_name", true, true, 1, 
                new DataType.CustomText("davisbase_tables")));
        LeafCell tablesLeafCell = new LeafCell(tables);
        
        //Create columns leaf cell
        ArrayList<Column> columns = new ArrayList<>();
        tables.add(new Column("rowid", true, false, 0, new DataType.Int(2)));
        tables.add(new Column("tables_name", true, true, 1, 
                new DataType.CustomText("davisbase_columns")));
        LeafCell columnsLeafCell = new LeafCell(columns);
        
        //Create page header
        byte numberOfCells = 2;
        short cellsStartContentArea = (short)(Helper.PAGE_SIZE - 
                (tablesLeafCell.getSize() + columnsLeafCell.getSize()));
        int pagePointer = 0;
        ArrayList<Short> cellsPointers = new ArrayList<>();
        cellsPointers.add(Helper.PAGE_SIZE);
        cellsPointers.add((short)(Helper.PAGE_SIZE - tablesLeafCell.getSize()));
        PageHeader header = new PageHeader(Helper.LEAF_TABLE_PAGE_TYPE, 
                                           numberOfCells, 
                                           cellsStartContentArea, 
                                           pagePointer, 
                                           cellsPointers);
        
        //Create page
        Map<Short, Cell> cellsMap = new HashMap<>();
        cellsMap.put(cellsPointers.get(0), tablesLeafCell);
        cellsMap.put(cellsPointers.get(1), columnsLeafCell);
        
        Page tablesPage = new Page(header, cellsMap);
    }
    
    public static void createColumnsFile() {
        
    }
    
    public static void WritePage(RandomAccessFile raf, int offset, Page page) 
            throws IOException {
        //Write header
        raf.seek(offset);
        PageHeader header = page.getPageHeader();
        raf.write(header.getPageType());
        raf.write(header.getNumberOfCells());
        raf.writeShort(header.getCellsStartContentArea());
        raf.writeInt(header.getPagePointer());
        ArrayList<Short> cellsPointers = header.getCellsPointers();
        for(Short cellPointer : cellsPointers)
            raf.writeShort(cellPointer);
        
        //Write content
        Map<Short, Cell> cells = page.getCells();

    }
}
