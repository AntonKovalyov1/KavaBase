package kavabase.fileFormat;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;
import kavabase.DataFormat.DataType;
import kavabase.DataFormat.DataType.CustomText;
import kavabase.DataFormat.SerialType;
import kavabase.Prompt.Column;
import kavabase.Prompt.TableDisplay;
import kavabase.Prompt.TableMetaData;
import kavabase.fileFormat.Cell.LeafCell;
import kavabase.fileFormat.Cell.InteriorCell;

/**
 *
 * @author Anton
 */
public class FileOperations {

    public static final String TABLES_NAME = "davisbase_tables";
    public static final String COLUMNS_NAME = "davisbase_columns";
    public static final String TABLES_PATH = "data/catalog/davisbase_tables.tbl";
    public static final String COLUMNS_PATH = "data/catalog/davisbase_columns.tbl";
    public static final String USER_DATA_PATH = "data/user_data";
    public static final String TABLE_EXTENSION = ".tbl";

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
        LeafCell tablesLeafCell = new LeafCell(tables);

        //Create columns leaf cell
        ArrayList<DataType> columns = new ArrayList<>();
        columns.add(new DataType.Int(2));
        columns.add(new DataType.CustomText("davisbase_columns"));
        LeafCell columnsLeafCell = new LeafCell(columns);

        //Create page
        Page tablesPage = new Page(0, Helper.LEAF_TABLE_PAGE_TYPE, -1);
        tablesPage.addCell(tablesLeafCell);
        tablesPage.addCell(columnsLeafCell);
        
        //Write page
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(Helper.PAGE_SIZE);
        raf.seek(0);
        writePage(raf, tablesPage);
        raf.close();
    }

    public static void createColumnsFile() throws IOException {
        File file = new File(COLUMNS_PATH);
        // Create leaf cells
        ArrayList<DataType> tables1 = new ArrayList<>();
        tables1.add(new DataType.Int(1));
        tables1.add(new DataType.CustomText("davisbase_tables"));
        tables1.add(new DataType.CustomText("rowid"));
        tables1.add(new DataType.CustomText(SerialType.INT.toString()));
        tables1.add(new DataType.TinyInt((byte) 1));
        tables1.add(new DataType.CustomText("NO"));
        LeafCell tables1LeafCell = new LeafCell(tables1);

        ArrayList<DataType> tables2 = new ArrayList<>();
        tables2.add(new DataType.Int(2));
        tables2.add(new DataType.CustomText("davisbase_tables"));
        tables2.add(new DataType.CustomText("table_name"));
        tables2.add(new DataType.CustomText(SerialType.TEXT.toString()));
        tables2.add(new DataType.TinyInt((byte) 2));
        tables2.add(new DataType.CustomText("NO"));
        LeafCell tables2LeafCell = new LeafCell(tables2);

        ArrayList<DataType> columns1 = new ArrayList<>();
        columns1.add(new DataType.Int(3));
        columns1.add(new DataType.CustomText("davisbase_columns"));
        columns1.add(new DataType.CustomText("rowid"));
        columns1.add(new DataType.CustomText(SerialType.INT.toString()));
        columns1.add(new DataType.TinyInt((byte) 1));
        columns1.add(new DataType.CustomText("NO"));
        LeafCell columns1LeafCell = new LeafCell(columns1);

        ArrayList<DataType> columns2 = new ArrayList<>();
        columns2.add(new DataType.Int(4));
        columns2.add(new DataType.CustomText("davisbase_columns"));
        columns2.add(new DataType.CustomText("table_name"));
        columns2.add(new DataType.CustomText(SerialType.TEXT.toString()));
        columns2.add(new DataType.TinyInt((byte) 2));
        columns2.add(new DataType.CustomText("NO"));
        LeafCell columns2LeafCell = new LeafCell(columns2);

        ArrayList<DataType> columns3 = new ArrayList<>();
        columns3.add(new DataType.Int(5));
        columns3.add(new DataType.CustomText("davisbase_columns"));
        columns3.add(new DataType.CustomText("column_name"));
        columns3.add(new DataType.CustomText(SerialType.TEXT.toString()));
        columns3.add(new DataType.TinyInt((byte) 3));
        columns3.add(new DataType.CustomText("NO"));
        LeafCell columns3LeafCell = new LeafCell(columns3);

        ArrayList<DataType> columns4 = new ArrayList<>();
        columns4.add(new DataType.Int(6));
        columns4.add(new DataType.CustomText("davisbase_columns"));
        columns4.add(new DataType.CustomText("datatype"));
        columns4.add(new DataType.CustomText(SerialType.TEXT.toString()));
        columns4.add(new DataType.TinyInt((byte) 4));
        columns4.add(new DataType.CustomText("NO"));
        LeafCell columns4LeafCell = new LeafCell(columns4);

        ArrayList<DataType> columns5 = new ArrayList<>();
        columns5.add(new DataType.Int(7));
        columns5.add(new DataType.CustomText("davisbase_columns"));
        columns5.add(new DataType.CustomText("ordinal_position"));
        columns5.add(new DataType.CustomText(SerialType.TINYINT.toString()));
        columns5.add(new DataType.TinyInt((byte) 5));
        columns5.add(new DataType.CustomText("NO"));
        LeafCell columns5LeafCell = new LeafCell(columns5);

        ArrayList<DataType> columns6 = new ArrayList<>();
        columns6.add(new DataType.Int(8));
        columns6.add(new DataType.CustomText("davisbase_columns"));
        columns6.add(new DataType.CustomText("is_nullable"));
        columns6.add(new DataType.CustomText(SerialType.TEXT.toString()));
        columns6.add(new DataType.TinyInt((byte) 6));
        columns6.add(new DataType.CustomText("NO"));
        LeafCell columns6LeafCell = new LeafCell(columns6);

        //Create page header
        int pagePointer = -1;

        //Create page
        TreeMap<Integer, Cell> cellsMap = new TreeMap<>();
        cellsMap.put(tables1LeafCell.getKey(), tables1LeafCell);
        cellsMap.put(tables2LeafCell.getKey(), tables2LeafCell);
        cellsMap.put(columns1LeafCell.getKey(), columns1LeafCell);
        cellsMap.put(columns2LeafCell.getKey(), columns2LeafCell);
        cellsMap.put(columns3LeafCell.getKey(), columns3LeafCell);
        cellsMap.put(columns4LeafCell.getKey(), columns4LeafCell);
        cellsMap.put(columns5LeafCell.getKey(), columns5LeafCell);
        cellsMap.put(columns6LeafCell.getKey(), columns6LeafCell);

        Page columnsPage = new Page(0, 
                                    Helper.LEAF_TABLE_PAGE_TYPE, 
                                    pagePointer);
        columnsPage.addCells(cellsMap);
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(Helper.PAGE_SIZE);
        raf.seek(0);
        writePage(raf, columnsPage);
        raf.close();
    }

    public static void writePage(final RandomAccessFile raf, final Page page)
            throws IOException {
        //Write header
        raf.seek(page.getPageOffset());
        System.out.println("The page offset is: " + page.getPageOffset());
        raf.writeByte(page.getPageType());
        raf.writeByte(page.getNumberOfCells());
        raf.writeShort(page.getCellsStartArea());
        raf.writeInt(page.getPagePointer());

        //Write content pointers
        TreeMap<Integer, Short> cellOffsets = page.getCellOffsets();
        for (Map.Entry<Integer, Short> entry : cellOffsets.entrySet()) {
            raf.writeShort(entry.getValue());
            System.out.println("key " + entry.getKey() + " offset is " + entry.getValue());
        }

        //Write cells
        if (page.getPageType() == Helper.LEAF_TABLE_PAGE_TYPE) {
            for (Map.Entry<Integer, Short> entry : cellOffsets.entrySet()) {
                writeLeafCell(raf, 
                              getOffset(page.getPageNumber(), entry.getValue()), 
                              (LeafCell) page.getCells().get(entry.getKey()));
            }
        } 
        else {
            for (Map.Entry<Integer, Short> entry : cellOffsets.entrySet()) {
                writeInteriorCell(raf,
                        getOffset(page.getPageNumber(), entry.getValue()),
                        (InteriorCell) page.getCells().get(entry.getKey()));
            }
        }
    }

    public static void writeLeafCell(final RandomAccessFile raf,
                                     final long offset,
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
                                         final long offset,
                                         final InteriorCell cell)
            throws IOException {
        raf.seek(offset);
        System.out.println("The offset of the interior cell is: " + offset);
        raf.writeInt(cell.getLeftChildPointer());
        raf.writeInt(cell.getKey());
    }

    public static Page readPage(final RandomAccessFile raf, int pageNumber)
            throws IOException {
        raf.seek(pageNumber * Helper.PAGE_SIZE);

        //read page header
        byte pageType = raf.readByte();
        byte numberOfCells = raf.readByte();
        short cellsStartContentArea = raf.readShort();
        int pagePointer = raf.readInt();
        Page page = new Page(pageNumber, pageType, pagePointer);

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
                InteriorCell cell = new InteriorCell(leftChildPointer, key);
                page.addCell(cell);
            }
            return page;
        }
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
            LeafCell cell = new LeafCell(records);
            page.addCell(cell);
        }
        return page;
    }

    public static long getOffset(final int pageNumber, final short pageOffset) {
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
        int textSize = serialType - SerialType.TEXT.getCode();
        byte[] bytes = new byte[textSize];
        raf.readFully(bytes);
        return new DataType.CustomText(new String(bytes));
    }

    public static ArrayList<TableMetaData> getMetaData() {
        ArrayList<TableMetaData> metaData = new ArrayList<>();

        try {
            // Get table names
            RandomAccessFile raf = new RandomAccessFile(TABLES_PATH, "rw");
            ArrayList<String> tableNames = new ArrayList<>();
            Page page = getLeftmostLeafPage(raf);
            boolean done = false;
            do {
                for (Map.Entry<Integer, Cell> cell : page.getCells().entrySet()) {
                    LeafCell leafCell = (LeafCell) cell.getValue();
                    CustomText tableName = (CustomText) leafCell.getRecords().get(1);
                    tableNames.add(tableName.getData());
                }
                int nextPage = page.getPagePointer();
                if (nextPage == -1) {
                    done = true;
                }
            } while (!done);
            raf.close();

            //Get table columns
            raf = new RandomAccessFile(COLUMNS_PATH, "r");
            page = getLeftmostLeafPage(raf);
            done = false;
            int currentTableIndex = 0;
            System.out.println("table names size: " + tableNames.size());
            for (String s : tableNames) {
                System.out.println(s + " ");
            }
            TableMetaData tableMetaData = new TableMetaData(
                    tableNames.get(currentTableIndex));
            do {System.out.println("next page");
                for (Map.Entry<Integer, Cell> cell : page.getCells().entrySet()) {
                    LeafCell leafCell = (LeafCell) cell.getValue();
                    ArrayList<DataType> record = leafCell.getRecords();
                    System.out.println("record table name: " + record.get(1).getData() + ", key " + cell.getKey());
                    if (tableMetaData.getTableName().equals(
                            record.get(1).getData())) {
                        tableMetaData.addColumn(createMetaDataColumn(record));
                    } 
                    else {
                        metaData.add(tableMetaData);
                        tableMetaData = new TableMetaData(
                                tableNames.get(++currentTableIndex));
                        tableMetaData.addColumn(createMetaDataColumn(record));
                    }
                }
                int nextPage = page.getPagePointer();
                if (nextPage == -1) {
                    metaData.add(tableMetaData);
                    done = true;
                }
                else {
                    page = readPage(raf, nextPage);
                }
            } while (!done);
            raf.close();
        } 
        catch (IOException ex) {
            System.out.println("Unable to fetch metadata.");
        }

        return metaData;
    }

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists() && !file.isDirectory();
    }
    
    public static void selectAll(final TableMetaData tableMetaData) 
            throws IOException {
        String tableName = tableMetaData.getTableName();
        String path;
        switch (tableName) {
            case TABLES_NAME:
                path = TABLES_PATH;
                break;
            case COLUMNS_NAME:
                path = COLUMNS_PATH;
                break;
            default:
                path = USER_DATA_PATH + tableName + TABLE_EXTENSION;
                break;
        }
        RandomAccessFile raf = new RandomAccessFile(path, "r");
        Page page = getLeftmostLeafPage(raf);
        System.out.println("testhhkjhkhkjhkhkhkhkjhkjhkjh");
        TableDisplay tableDisplay = new TableDisplay(
                tableMetaData.getColumnNames());
        int rightPagePointer;
        do {
            for (Map.Entry<Integer, Cell> cell : page.getCells().entrySet()) {
                LeafCell leafCell = (LeafCell) cell.getValue();
                ArrayList<DataType> row = leafCell.getRecords();
                tableDisplay.addRecord(row);
            }
            rightPagePointer = page.getPagePointer();
            if (rightPagePointer != -1) {
                page = readPage(raf, rightPagePointer);
            }
        }
        while(rightPagePointer != -1);
        tableDisplay.display();
    }
    
    public static Page getLeftmostLeafPage(final RandomAccessFile raf) 
            throws IOException {
        Page page = readPage(raf, 0);
        while (!page.isLeafPage()) {
            InteriorCell leftmostInteriorCell = (InteriorCell)
                    page.getCells().firstEntry().getValue();
            System.out.println("interior key is: " + leftmostInteriorCell.getKey()); // key is messed up
            int childPage = leftmostInteriorCell.getLeftChildPointer();
            page = readPage(raf, childPage); // check this, there is bug
        }
        return page;
    }
    
    private static Column createMetaDataColumn(ArrayList<DataType> record) {
        return new Column((String) record.get(1).getData(),
                          (String) record.get(2).getData(),
                          (String) record.get(3).getData(),
                          (Byte) record.get(4).getData(),
                          (String) record.get(5).getData());
    }
    
    public static Page search(final RandomAccessFile raf, final int key) 
            throws IOException {
        Page page = readPage(raf, 0);
        while (!page.isLeafPage()) {
            if (key > page.getCells().lastKey()) {
                InteriorCell cell = (InteriorCell)
                        page.getCells().lastEntry().getValue();
                page = readPage(raf, cell.getLeftChildPointer());
            }
            else {
                InteriorCell cell = (InteriorCell)
                        page.getCells().ceilingEntry(key);
                page = readPage(raf, cell.getLeftChildPointer());
            }
        }
        return page;
    }
    
    public static ArrayList<DataType> getRecord(final Page page, 
                                                final int key) {
        LeafCell cell = (LeafCell)page.getCells().get(key);
        if (cell == null)
            return null;
        return cell.getRecords();
    }
    
    public static boolean insert(final RandomAccessFile raf, 
                                 final ArrayList<DataType> row) 
            throws IOException {
        int key = getKey(row);
        Page page = readPage(raf, 0);
        Stack<Page> stack = new Stack<>();
        
        //search
        while (!page.isLeafPage()) {
            stack.push(page);
            if (key > page.getCells().lastKey()) {
                page = readPage(raf, page.getPagePointer());
            }
            else {
                InteriorCell cell = (InteriorCell)
                        page.getCells().ceilingEntry(key).getValue();
                page = readPage(raf, cell.getLeftChildPointer());
            }
        }
        if (getRecord(page, key) != null) {
            return false;
        }
        Cell cell = new LeafCell(row);
        page.addCell(cell);
        if (!page.isOverFlow()) {
            System.out.println("test thisuhiuhiuhiiuhihiu");
            writePage(raf, page);
//            raf.close();
            return true;
        }
        //Split page
        int splitKey = page.getSplitKey();
        System.out.println("And the split key is: " + splitKey);
        TreeMap<Integer, Cell> cells = page.getCells();
        int minKey = cells.firstKey();
        int maxKey = cells.lastKey();
        SortedMap<Integer, Cell> leftCells = cells.subMap(minKey, true, splitKey, true);
        SortedMap<Integer, Cell> rightCells = cells.subMap(splitKey, false, maxKey, true);
        Page leftPage = new Page(page.getPageNumber(), 
                                 page.getPageType(), 
                                 (int)(raf.length() / Helper.PAGE_SIZE));
        leftPage.addCells(leftCells);
        Page rightPage = new Page((int)(raf.length() / Helper.PAGE_SIZE), 
                                  page.getPageType(), 
                                  page.getPagePointer());
        rightPage.addCells(rightCells);
        raf.setLength(raf.length() + Helper.PAGE_SIZE);
        boolean done = false;
        while (!done) {
            if (stack.empty()) {
                // No parent node, new root must be created
                raf.setLength(raf.length() + Helper.PAGE_SIZE);
                int leftPageNumber = rightPage.getPageNumber() + 1;
                leftPage.setPageNumber(leftPageNumber);
                Page root = new Page(Helper.INTERIOR_TABLE_PAGE_TYPE);
                root.setPagePointer(rightPage.getPageNumber());
                System.out.println("Root page pointer: " + root.getPagePointer());
                root.addCell(new InteriorCell(leftPage.getPageNumber(), 
                                              leftPage.getCells().lastKey()));
                System.out.println("Root cell pointer: " + leftPage.getPageNumber());
                writePage(raf, root);
                writePage(raf, leftPage);
                writePage(raf, rightPage);
                done = true;
            }
            else {
                page = stack.pop();
                // Change pointer to new right page
                int rightPageMaxKey = rightPage.getCells().lastKey();
                cells = page.getCells();
                if (rightPageMaxKey > cells.lastKey()) {
                    page.setPagePointer(rightPage.getPageNumber());
                }
                else {
                    InteriorCell oldCell = (InteriorCell)
                            cells.ceilingEntry(rightPageMaxKey).getValue();
                    oldCell.setLeftChildPointer(rightPageMaxKey);
                }
                
                Cell newCell = new InteriorCell(leftPage.getPageNumber(),
                                                leftPage.getCells().lastKey());
                page.addCell(newCell);
                writePage(raf, rightPage);
                writePage(raf, leftPage);
                if (!page.isOverFlow()) {
                    writePage(raf, page);
                    done = true;
                }
                else {
                    splitKey = page.getSplitKey();
                    minKey = cells.firstKey();
                    maxKey = cells.lastKey();
                    leftCells = cells.subMap(minKey, true, splitKey, true);
                    rightCells = cells.subMap(splitKey, false, maxKey, true);
                    leftPage = new Page((int) (raf.length() / Helper.PAGE_SIZE),
                                        page.getPageType(),
                                        page.getPageNumber());
                    leftPage.addCells(leftCells);
                    rightPage = new Page(page.getPageNumber(),
                                         page.getPageType(),
                                         page.getPagePointer());
                    rightPage.addCells(rightCells);
                    raf.setLength(raf.length() + Helper.PAGE_SIZE);
                }
            }
        }
//        raf.close();
        return true;
    }
 
    public static ArrayList<Page> splitPage(Page page, long fileSize) {
        int splitKey = page.getSplitKey();
        ArrayList<Page> pages = new ArrayList<>();
        TreeMap<Integer, Cell> leftCells = new TreeMap<>();
        TreeMap<Integer, Cell> rightCells = new TreeMap<>();
        for (Map.Entry<Integer, Cell> current : page.getCells().entrySet()) {
            if (current.getKey() <= splitKey)
                leftCells.put(current.getKey(), current.getValue());
            else
                rightCells.put(current.getKey(), current.getValue());
        }
        int leftPageNumber = page.getPageNumber();
        byte pageType = page.getPageType();
        int newLastPageNumber = (int)fileSize / Helper.PAGE_SIZE;
        Page leftPage = new Page(leftPageNumber, 
                                 pageType, 
                                 newLastPageNumber);
        leftPage.addCells(leftCells);
        Page rightPage = new Page(newLastPageNumber, 
                                  pageType, 
                                  page.getPagePointer());
        rightPage.addCells(rightCells);
        pages.add(leftPage);
        pages.add(rightPage);
        
        return pages;
    }
    
    private static int getKey(ArrayList<DataType> row) {
        return (Integer)row.get(0).getData();
    }
}
