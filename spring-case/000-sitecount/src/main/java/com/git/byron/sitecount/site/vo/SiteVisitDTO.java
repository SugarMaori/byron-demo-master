package com.git.byron.sitecount.site.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: BYRON
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.sitecount.site.vo
 * @ClassName: SiteVisitDTO
 * @Description:
 * @Date: 2019/8/7 9:27
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
public class SiteVisitDTO {

    /**
     * 站点访问统计
     */
    private VisitVO siteVO;

    /**
     * 页面访问统计
     */
    private VisitVO uriVO;
}
