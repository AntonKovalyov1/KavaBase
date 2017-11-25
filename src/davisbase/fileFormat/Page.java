package davisbase.fileFormat;

import davisbase.Commons.Helper;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author axk176431
 */
public class Page {
    
    private int pageNumber;
    private final byte pageType;
    private int pagePointer;
    private final TreeMap<Integer, Cell> cells = new TreeMap<>();
    private final TreeMap<Integer, Short> cellOffsets = new TreeMap<>();
    private short cellsStartArea = Helper.PAGE_SIZE;
    
    public Page(int pageNumber,
                final byte pageType,
                int pagePointer) {
        this.pageNumber = pageNumber;
        this.pageType = pageType;
        this.pagePointer = pagePointer;
    }
    
    public Page(final byte pageType) {
        this.pageNumber = 0;
        this.pageType = pageType;
        this.pagePointer = -1;
    }
    
    public void addCell(final Cell cell) {
        int key = cell.getKey();
        cells.put(key, cell);
        cellsStartArea -= cell.getSize();
        cellOffsets.put(key, cellsStartArea);
    }
    
    public void removeCell(final int key) {
        cells.remove(key);
        TreeMap<Integer, Cell> temp = new TreeMap<>();
        // deep copy
        cells.entrySet().forEach((current) -> {
            temp.put(current.getKey(), current.getValue());
        });
        cellOffsets.clear();
        cells.clear();
        cellsStartArea = Helper.PAGE_SIZE;
        addCells(temp);
    }
    
    public void addCells(final Map<Integer, Cell> cells) {
        cells.entrySet().forEach((current) -> {
            addCell(current.getValue());
        });
    }

    /**
     * @return the cells
     */
    public TreeMap<Integer, Cell> getCells() {
        return cells;
    }
    
    public int getPageNumber() {
        return pageNumber;
    }
    
    public int getPageOffset() {
        return pageNumber * Helper.PAGE_SIZE;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public boolean isLeafPage() {
        return this.pageType == Helper.LEAF_TABLE_PAGE_TYPE;
    }
    
    public boolean isOverFlow() {
        return getSize() > Helper.PAGE_SIZE;
    }
    
    public int getSize() {
        return getHeaderSize() + getContentSize();
    }
    
    public int getContentSize() {
        int contentSize = 0;
        for (Map.Entry<Integer, Cell> cell : getCells().entrySet()) {
            contentSize += cell.getValue().getSize();
        }
        return contentSize;        
    }
    
    public int getHeaderSize() {
        return 8 + getCells().size() * 2;
    }
    
    public int getSplitKey() {
        int contentHalf = getContentSize() / 2;
        int currentSize = 0;
        for (Map.Entry<Integer, Cell> cell : getCells().entrySet()) {
            currentSize += cell.getValue().getSize();
            if (currentSize > contentHalf)
                return cell.getKey();
        }
        return getCells().lastKey();
    }
    
    public short getCellsStartArea() {
        return cellsStartArea;
    }
    
    public byte getNumberOfCells() {
        return (byte)getCells().size();
    }
    
    /**
     * @return the pagePointer
     */
    public int getPagePointer() {
        return pagePointer;
    }

    /**
     * @param pagePointer the pagePointer to set
     */
    public void setPagePointer(int pagePointer) {
        this.pagePointer = pagePointer;
    }

    /**
     * @return the pageType
     */
    public byte getPageType() {
        return pageType;
    }

    /**
     * @return the cellOffsets
     */
    public TreeMap<Integer, Short> getCellOffsets() {
        return cellOffsets;
    }
}
