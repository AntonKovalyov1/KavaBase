package kavabase.fileFormat;

/**
 *
 * @author axk176431
 */
public class Page {
    
    public static final short PAGE_SIZE = 512;
    private PageHeader pageHeader;
    
    public Page(PageHeader pageHeader) {
        this.pageHeader = pageHeader;
    }
}
