package com.git.byron.sitecount;

import com.git.byron.sitecount.site.SiteVisitFacade;
import com.git.byron.sitecount.site.model.VisitReqDTO;
import com.git.byron.sitecount.site.vo.SiteVisitDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.sitecount
 * @ClassName: VisitController
 * @Description:
 * @Date: 2019/8/6 14:46
 * @Version: 1.0
 */
@RestController
public class VisitController {

    @Resource
    private SiteVisitFacade siteVisitFacade;

    @RequestMapping(value = "visit")
    public SiteVisitDTO visit(VisitReqDTO visitDTO){
        return siteVisitFacade.visit(visitDTO);
    }
}
