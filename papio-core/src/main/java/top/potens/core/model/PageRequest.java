package top.potens.core.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className PageRequest
 * @projectName web-api
 * @date 2019/9/14 11:21
 */
public class PageRequest {
    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页条数", example = "20")
    private Integer pageSize = 20;

    @ApiModelProperty(value = "分页字段", example = "")
    private String orderBy;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }


}
