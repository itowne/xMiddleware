package com.open.item.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.open.item.entity.Page;

/**
 * 表格数据载体
 * 
 * @author towne
 * @param <T>
 * @time Jun 3, 2016 3:39:24 PM
 */
public class DataTableBean<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5183737364687173536L;
    private static long DEFAULT_INIT = 0;

    public DataTableBean() {
        this.setStart(DEFAULT_INIT);
        this.setLength(DEFAULT_INIT);
        this.setRecordsTotal(DEFAULT_INIT);
        this.setRecordsFiltered(DEFAULT_INIT);
        this.setAaData(new ArrayList<T>());
    }

    public DataTableBean(Page<T> page) {
        this.setStart(page.getStartIndex());
        this.setLength(page.getPageSize());
        this.setRecordsTotal(page.getTotalCount());
        this.setRecordsFiltered(page.getTotalCount());
        this.setAaData(page.getItems());
    }

    /**
     * 分页查询起始
     */
    private long start;

    /**
     * 每页展示条数
     */
    private long length;
    /**
     * 记录数
     */
    private long recordsTotal;

    /**
     * 过滤数
     */
    private long recordsFiltered;
    /**
     * 数据集
     */
    private List<T> aaData;

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }

}
