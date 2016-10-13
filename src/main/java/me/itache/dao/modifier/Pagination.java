package me.itache.dao.modifier;

/**
 * Contains info that needs to provide pagination.
 */
public class Pagination {
    private static final int DEFAULT_ITEMS_ON_PAGE = 5;
    private int itemsOnPage = DEFAULT_ITEMS_ON_PAGE;
    private int currentPageNumber;
    private int itemsCount;

    public int getItemsOnPageCount() {
        return itemsOnPage;
    }

    public void setItemsOnPageCount(int itemsOnPage) {
        this.itemsOnPage = itemsOnPage;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int pageNumber) {
        this.currentPageNumber = pageNumber;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public int getPageCount(){
        return (int) Math.ceil((double) itemsCount/itemsOnPage);
    }
}
