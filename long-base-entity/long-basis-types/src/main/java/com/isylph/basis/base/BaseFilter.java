package com.isylph.basis.base;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseFilter {

    protected String permissionCode;

    protected LocalDateTime refreshTimestamp;

    protected Integer offset;

    protected Integer cnt;

    public BaseFilter() {
        this.offset = 0;
        this.cnt = 100;
    }

    /**
     * 本系统的时间端查询采用start time << fitler < end time, 统一对查询条件中的结束时间进行调整
     *
     * @param ldt
     * @return
     */
    protected LocalDateTime calibrateEndTime(LocalDateTime ldt){
        if (ldt.getHour() == 0 && ldt.getMinute() == 0 && ldt.getSecond() == 0){
            return ldt.plusDays(1);
        }else{
            return ldt.plusSeconds(1);
        }
    }

    public void setOffset(Integer offset) {
        if(offset == null){
            this.offset = 0;
        }else{
            this.offset = offset;
        }
    }

    public void setCnt(Integer cnt) {

        if (cnt == null){
            this.cnt = 100;
        }else{
            if (cnt > 5000){
                this.cnt = 1000;
            }

            this.cnt = cnt;
        }
    }
}
