package top.potens.core.model;

import java.util.Date;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className DateScope
 * @projectName web-api
 * @date 2019/10/22 15:39
 */
public class DateScope {
    private Date startDate;
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
