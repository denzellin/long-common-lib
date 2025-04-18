package com.isylph.basis.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BaseListQuery extends BaseCmd {

    public static final String INVALID_PERMISSION_CODE = "-";

    private final static int MAX_CNT = 10000;

    private String permissionCode;

    /**
     时间戳, 在第一次查询时由服务器下发, 需要客户端回传
     */
    private Long timestamp;

    /**
     * 时间戳, 服务器端解析结果
     * */
    private LocalDateTime refreshTimestamp;


    /**
     * 客户端查询时记录偏移, 常用于APP
     */
    private Integer offset;

    /**
     * 客户端一次查询时记录数
     */
    private Integer cnt;


    private Integer current;

    private Integer pageSize;

    public BaseListQuery() {
        this.offset = 0;
        this.current = 1;
        this.pageSize = MAX_CNT;
        this.cnt = MAX_CNT;
    }


    public void setCurrent(Integer current) {
        this.current = current;
        int size = pageSize!=null?pageSize:20;
        this.offset =(current -1)*size;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        this.cnt = pageSize;
        this.offset = current != null?(current -1)*cnt:0;
    }


    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;

        if (null != timestamp){
            this.refreshTimestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        }
    }


    public BaseListQuery setCnt(Integer cnt) {
        this.cnt = (cnt == null || cnt > 10000) ? 10000:cnt;
        return this;
    }
}
