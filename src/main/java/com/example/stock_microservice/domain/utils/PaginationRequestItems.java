package com.example.stock_microservice.domain.utils;

import java.util.List;

public class PaginationRequestItems {
    private int page;
    private int size;
    private SortDirection sortDirection;
    private Filter filter;
    private String filterName;
    private List<Long> itemsId;

    public PaginationRequestItems(int page, int size, SortDirection sortDirection, Filter filter, String filterName, List<Long> itemsId) {
        this.page = page;
        this.size = size;
        this.sortDirection = sortDirection;
        this.filter = filter;
        this.filterName = filterName;
        this.itemsId = itemsId;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }


    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public List<Long> getItemsId() {
        return itemsId;
    }

    public Filter getFilter() {
        return filter;
    }

    public String getFilterName() {
        return filterName;
    }
}
