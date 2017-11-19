package kavabase.fileFormat;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author axk176431
 */
public class PageHeader {
    
    private final byte pageType;
    private final byte numberOfCells;
    private final short cellsStartContentArea;
    private int pagePointer;
    
    public PageHeader(final byte pageType, 
                      final byte numberOfCells, 
                      final short cellsStartContentArea, 
                      final int pagePointer) {
        
        this.pageType = pageType;
        this.numberOfCells = numberOfCells;
        this.cellsStartContentArea = cellsStartContentArea;
        this.pagePointer = pagePointer;
    }

    /**
     * @return the pageType
     */
    public byte getPageType() {
        return pageType;
    }

    /**
     * @return the numberOfCells
     */
    public byte getNumberOfCells() {
        return numberOfCells;
    }

    /**
     * @return the cellsStartContentArea
     */
    public short getCellsStartContentArea() {
        return cellsStartContentArea;
    }

    /**
     * @return the pagePointer
     */
    public int getPagePointer() {
        return pagePointer;
    }   
}
