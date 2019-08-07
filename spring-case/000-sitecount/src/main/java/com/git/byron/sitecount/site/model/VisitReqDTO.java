package com.git.byron.sitecount.site.model;

import lombok.Data;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.sitecount.site.model
 * @ClassName: VisitReqDTO
 * @Description:
 * @Date: 2019/8/7 9:20
 * @Version: 1.0
 */

@Data
public class VisitReqDTO {

    /**
     * 应用区分
     */
    private String app;

    /**
     * 访问者ip
     */
    private String ip;

    /**
     * 访问的URI
     */
    private String uri;

}
