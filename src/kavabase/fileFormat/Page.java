package kavabase.fileFormat;

import java.util.Map;

/**
 *
 * @author axk176431
 */
public class Page {
    
    private final PageHeader pageHeader;
    private final Map<Short, Cell> cells;
    
    public Page(PageHeader pageHeader, Map<Short, Cell> cells) {
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
    public Map<Short, Cell> getCells() {
        return cells;
    }
}
