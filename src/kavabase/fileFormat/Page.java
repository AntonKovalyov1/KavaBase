package kavabase.fileFormat;

import java.util.TreeMap;

/**
 *
 * @author axk176431
 */
public class Page {
    
    private final int pageNumber;
    private final PageHeader pageHeader;
    private final TreeMap<Integer, Cell> cells;
    
    public Page(final int pageNumber,
                final PageHeader pageHeader, 
                final TreeMap<Integer, Cell> cells) {
        this.pageNumber = pageNumber;
        this.pageHeader = pageHeader;
        this.cells = cells;
    }

    /**
     * @return the pageHeader
     */
    public PageHeader getPageHeader() {
        return pageHeader;
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
    
    public boolean isLeafPage() {
        return pageHeader.getPageType() == Helper.LEAF_TABLE_PAGE_TYPE;
    }
}
