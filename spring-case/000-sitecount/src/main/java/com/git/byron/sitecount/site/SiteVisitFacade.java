package com.git.byron.sitecount.site;

import com.git.byron.sitecount.site.core.VisitService;
import com.git.byron.sitecount.site.model.VisitReqDTO;
import com.git.byron.sitecount.site.util.DateUtil;
import com.git.byron.sitecount.site.util.URIUtil;
import com.git.byron.sitecount.site.vo.SiteVisitDTO;
import com.git.byron.sitecount.site.vo.VisitVO;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: byron
 * @ProjectName: byron-demo-master
 * @Package: com.git.byron.sitecount.site
 * @ClassName: SiteVisitFacade
 * @Description:
 * @Date: 2019/8/7 9:30
 * @Version: 1.0
 */
@Service
public class SiteVisitFacade {

    @Resource
    private VisitService visitService;

    /**
     * 应用的pv统计计数
     *
     * @param app
     * @return
     */
    private String buildPvKey(String app) {
        return "site_cnt_" + app;
    }

    /**
     * 应用的热度统计计数
     *
     * @param app
     * @return
     */
    private String buildHotKey(String app) {
        return "hot_cnt_" + app;
    }

    /**
     * app+uri 对应的uv
     *
     * @param app
     * @param uri
     * @return
     */
    private String buildUvKey(String app, String uri) {
        return "uri_rank_" + app + "_" + uri;
    }

    /**
     * 每日访问统计
     *
     * @param app
     * @param uri
     * @return
     */
    private String buildUriTagKey(String app, String uri) {
        return "uri_tag_" + DateUtil.getToday() + "_" + app + "_" + uri;
    }

    /**
     * uri 访问统计
     *
     * @param reqDTO
     * @return
     */
    public SiteVisitDTO visit(VisitReqDTO reqDTO){
        //URI 分段
        ImmutablePair<String,String> uri = URIUtil.foramtUri(reqDTO.getUri());

        //获取站点的访问记录
        VisitVO uriVisit = doVisit(reqDTO.getApp(), uri.getRight(), reqDTO.getIp());
        VisitVO siteVisit;
        String left = uri.getLeft();
        String rigth = uri.getRight();
        if (uri.getLeft().equals(uri.getRight())) {
            siteVisit = new VisitVO(uriVisit);
        } else {
            siteVisit = doVisit(reqDTO.getApp(), uri.getLeft(), reqDTO.getIp());
        }

        return new SiteVisitDTO(siteVisit, uriVisit);
    }

    /**
     * 站点访问记录
     * */
    private VisitVO doVisit(String app,String uri,String ip){
        String pvKey = buildPvKey(app);
        String uvKey = buildUvKey(app,uri);
        String hotKey = buildHotKey(app);
        String todayVisitKey = buildUriTagKey(app,uri);

        //热度
        Long hot = visitService.addHot(hotKey,uri);

        //获取 PV 数据
        Long pv = visitService.getPv(pvKey,uri);
        if (pv == null || pv == 0){
            //历史没有访问过，则 pv + 1 ，uv + 1
            visitService.addPv(pvKey,uri);
            visitService.addUv(uvKey,ip,1L);
            visitService.tagVisit(todayVisitKey,ip);
            return new VisitVO(1L , 1L , 1L,hot);
        }

        //判断 IP 今天是否访问过
        boolean visit = visitService.visitToday(todayVisitKey,ip);

        //获取 UV 及 排名
        ImmutablePair</** uv */Long,/** rank */Long> uv = visitService.getUv(uvKey,ip);
        if(visit){
            //今天访问过，则不需要修改 pv / uv；可以直接返回需要的数据
            return new VisitVO(pv,uv.getLeft(),uv.getRight(),hot);
        }

        //今天没有访问
        if(uv.left == 0L){
            //首次有人访问，pv + 1；uv + 1
            visitService.addPv(pvKey,uri);
            visitService.addUv(uvKey,ip,1L);
            //标记 IP 访问过这个 KEY
            visitService.tagVisit(todayVisitKey,ip);
            return new VisitVO(pv + 1,uv.left + 1,uv.right + 1,hot);
        } else if(uv.right == 0L){
            // 这个ip首次访问, pv +1; uv + 1
            visitService.addPv(pvKey, uri);
            visitService.addUv(uvKey, ip, uv.left + 1);
            visitService.tagVisit(todayVisitKey, ip);
            return new VisitVO(pv + 1, uv.left + 1, uv.left + 1, hot);
        }else{
            // 这个ip的今天第一次访问， pv + 1 ; uv 不变
            visitService.addPv(pvKey, uri);
            visitService.tagVisit(todayVisitKey, ip);
            return new VisitVO(pv + 1, uv.left, uv.right, hot);
        }
    }
}
