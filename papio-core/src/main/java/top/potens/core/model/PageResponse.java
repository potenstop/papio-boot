package top.potens.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className PageResponse
 * @projectName web-api
 * @date 2019/9/14 11:23
 */
public class PageResponse<T> {
    private Long total;
    private List<T> list;

    public PageResponse() {
        this.total = (long)0;
        this.list = new ArrayList<>();
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
