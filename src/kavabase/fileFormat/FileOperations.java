package kavabase.fileFormat;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import kavabase.DataFormat.DataType;
import kavabase.DataFormat.SerialType;
import kavabase.fileFormat.Cell.LeafCell;
import kavabase.fileFormat.Cell.InteriorCell;

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
    
    public static void insertLeafCell(LeafCell leafCell,
            final RandomAccessFile raf) throws IOException {
        raf.writeShort(leafCell.getPayloadSize());
        raf.writeInt(leafCell.getKey());
        raf.writeByte(leafCell.getNumberOfRecords());
        for (int i = 0; i < leafCell.getRecords().size(); i++) {
            DataType dataType = leafCell.getRecords().get(i);
            raf.writeByte(dataType.getSerialTypeCode());
            raf.write(dataType.toByteArray());            
        }
    }
    
    public static void createMetaData() {
        try {
            createTablesFile();
            createColumnsFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void createTablesFile() throws IOException {
        File file = new File(TABLES_PATH);
        //Create tables leaf cell
        ArrayList<DataType> tables = new ArrayList<>();
        tables.add(new DataType.Int(1));
        tables.add(new DataType.CustomText("davisbase_tables"));
        LeafCell tablesLeafCell = new LeafCell(tables, Helper.PAGE_SIZE);

        //Create columns leaf cell
        ArrayList<DataType> columns = new ArrayList<>();
        columns.add(new DataType.Int(2));
        columns.add(new DataType.CustomText("davisbase_columns"));
        LeafCell columnsLeafCell = new LeafCell(columns,
                (short)(Helper.PAGE_SIZE - tablesLeafCell.getSize()));

        //Create page header
        byte numberOfCells = 2;
        short cellsStartContentArea = (short) (Helper.PAGE_SIZE
                - (tablesLeafCell.getSize() + columnsLeafCell.getSize()));
        int pagePointer = -1;
        PageHeader header = new PageHeader(Helper.LEAF_TABLE_PAGE_TYPE,
                numberOfCells,
                cellsStartContentArea,
                pagePointer);

        //Create page
        TreeMap<Integer, Cell> cellsMap = new TreeMap<>();
        cellsMap.put(tablesLeafCell.getKey(), tablesLeafCell);
        cellsMap.put(columnsLeafCell.getKey(), columnsLeafCell);
        
        Page tablesPage = new Page(0, header, cellsMap);
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(Helper.PAGE_SIZE);
        raf.seek(0);
        writePage(raf, tablesPage);
        raf.close();
    }
    
    public static void createColumnsFile() {
        //TODO
    }
    
    public static void writePage(final RandomAccessFile raf, final Page page)
            throws IOException {
        //Write header
        raf.seek(page.getPageOffset());
        PageHeader header = page.getPageHeader();
        raf.writeByte(header.getPageType());
        raf.writeByte(header.getNumberOfCells());
        raf.writeShort(header.getCellsStartContentArea());
        raf.writeInt(header.getPagePointer());

        //Write content pointer
        TreeMap<Integer, Cell> cells = page.getCells();
        for (Map.Entry<Integer, Cell> entry : cells.entrySet()) {
            raf.writeShort(entry.getValue().getOffset());
        }

        //Write cells
        if (header.getPageType() == Helper.LEAF_TABLE_PAGE_TYPE) {
            for (Map.Entry<Integer, Cell> entry : cells.entrySet()) {
                writeLeafCell(raf,
                        page.getPageNumber() + entry.getValue().getOffset(),
                        (LeafCell)entry.getValue());
            }
        } 
        else {
            for (Map.Entry<Integer, Cell> entry : cells.entrySet()) {
                writeInteriorCell(raf,
                        page.getPageNumber() + entry.getValue().getOffset(),
                        (InteriorCell) entry.getValue());
            }
        }
    }
    
    public static void writeLeafCell(final RandomAccessFile raf,
            final int offset,
            final LeafCell cell) throws IOException {
        raf.seek(offset);
        raf.writeShort(cell.getPayloadSize());
        raf.writeInt(cell.getKey());
        ArrayList<DataType> records = cell.getRecords();
        raf.writeByte(records.size());
        for (DataType record : records) {
            raf.writeByte(record.getSerialTypeCode());
        }
        for (DataType record : records) {
            raf.write(record.toByteArray());
        }
    }
    
    public static void writeInteriorCell(final RandomAccessFile raf,
            final int offset,
            final InteriorCell cell)
            throws IOException {
        raf.seek(offset);
        raf.writeInt(cell.getLeftChildPointer());
        raf.write(cell.getKey());
    }
    
    public static Page readPage(final RandomAccessFile raf, int pageNumber)
            throws IOException {
        raf.seek(pageNumber);
        
        //read page header
        byte pageType = raf.readByte();
        byte numberOfCells = raf.readByte();
        short cellsStartContentArea = raf.readShort();
        int pagePointer = raf.readInt();
        PageHeader header = new PageHeader(pageType,
                numberOfCells,
                cellsStartContentArea,
                pagePointer);
        //read cells pointers
        ArrayList<Short> cellPointers = new ArrayList<>();
        for (int i = 0; i < numberOfCells; i++) {
            cellPointers.add(raf.readShort());
        }

        //read cells
        if (pageType == Helper.INTERIOR_TABLE_PAGE_TYPE) {
            TreeMap<Integer, Cell> cells = new TreeMap<>();
            for (int i = 0; i < numberOfCells; i++) {
                raf.seek(getOffset(pageNumber, cellPointers.get(i)));
                int leftChildPointer = raf.readInt();
                int key = raf.readInt();
                InteriorCell cell = new InteriorCell(leftChildPointer,
                        key,
                        cellPointers.get(i));
                cells.put(key, cell);
            }
            raf.close();
            return new Page(pageNumber, header, cells);
        }
        TreeMap<Integer, Cell> cells = new TreeMap<>();
        for (int i = 0; i < numberOfCells; i++) {
            raf.seek(getOffset(pageNumber, cellPointers.get(i)));
            raf.readShort();
            int cellKey = raf.readInt();
            byte numberOfColumns = raf.readByte();
            ArrayList<Byte> serialTypeCodes = new ArrayList<>();
            for (int j = 0; j < numberOfColumns; j++) {
                serialTypeCodes.add(raf.readByte());
            }
            ArrayList<DataType> records = new ArrayList<>();
            for (int j = 0; j < numberOfColumns; j++) {
                records.add(getRecord(raf, serialTypeCodes.get(j)));               
            }
            LeafCell cell = new LeafCell(records, cellPointers.get(i));
            cells.put(cellKey, cell);
        }
        return new Page(pageNumber, header, cells);
    }
    
    public static long getOffset(final long pageNumber, final short pageOffset) {
        return Helper.PAGE_SIZE * pageNumber + pageOffset;
    }
    
    public static DataType getRecord(final RandomAccessFile raf, 
                                     final byte serialType)
            throws IOException {
        if (serialType == SerialType.NULL_1.getCode()) {
            raf.readByte();
            return new DataType.TinyInt();
        }
        if (serialType == SerialType.NULL_2.getCode()) {
            raf.readShort();
            return new DataType.SmallInt();
        }
        if (serialType == SerialType.NULL_4.getCode()) {
            raf.readInt();
            return new DataType.Int();
        }
        if (serialType == SerialType.NULL_8.getCode()) {
            raf.readLong();
            return new DataType.BigInt();
        }        
        if (serialType == SerialType.TINYINT.getCode()) {
            return new DataType.TinyInt(raf.readByte());
        }
        if (serialType == SerialType.SMALLINT.getCode()) {
            return new DataType.SmallInt(raf.readShort());
        }        
        if (serialType == SerialType.INT.getCode()) {
            return new DataType.Int(raf.readInt());
        }
        if (serialType == SerialType.BIGINT.getCode()) {
            return new DataType.BigInt(raf.readLong());
        }
        if (serialType == SerialType.REAL.getCode()) {
            return new DataType.Real(raf.readFloat());
        }
        if (serialType == SerialType.DOUBLE.getCode()) {
            return new DataType.CustomDouble(raf.readDouble());
        }
        if (serialType == SerialType.DATETIME.getCode()) {
            return new DataType.CustomDateTime(raf.readLong());
        }
        if (serialType == SerialType.DATE.getCode()) {
            return new DataType.CustomDate(raf.readLong());
        }
        int textSize = serialType & 0xff - SerialType.TEXT.getCode();
        byte[] bytes = new byte[textSize];
        raf.readFully(bytes);
        return new DataType.CustomText(new String(bytes));
    }
}
