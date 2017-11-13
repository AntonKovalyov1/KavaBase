package kavabase.fileFormat;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author axk176431
 */
public class PageHeader {
    
    public static final byte PAGE_TYPE_OFFSET = 0x00;
    public static final byte NUMBER_OF_CELLS_OFFSET = 0x01;
    public static final byte CELLS_START_OFFSET = 0x02;
    public static final byte PAGE_POINTER_OFFSET = 0x04;
    public static final byte CELLS_POINTERS_OFFSET = 0x08;
    
    public static final byte INTERIOR_INDEX_PAGE_TYPE = 0x02;
    public static final byte INTERIOR_TABLE_PAGE_TYPE = 0x05;
    public static final byte LEAF_INDEX_PAGE_TYPE = 0x0a;
    public static final byte LEAF_TABLE_PAGE_TYPE = 0x0d;
    
    protected byte pageType;
    protected byte numberOfCells;
    protected short cellsStartContentArea;
    protected int pagePointer;
    protected List<Short> cellsPointers = new ArrayList<>();
    
    public PageHeader(final byte pageType) {
        this.pageType = pageType;
    }
}
