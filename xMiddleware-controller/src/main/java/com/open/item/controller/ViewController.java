package com.open.item.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.open.item.entity.Access;
import com.open.item.entity.Article;
import com.open.item.entity.Img;
import com.open.item.entity.Page;
import com.open.item.entity.enumObject.BooleanEnum;
import com.open.item.entity.enumObject.ViewTypeEnum;
import com.open.item.entity.pojo.H5View;
import com.open.item.entity.relation.vote.VoteCountRelation;
import com.open.item.entity.relation.vote.VoteItemRelation;
import com.open.item.service.AccessService;
import com.open.item.service.ArticleService;
import com.open.item.service.ImgService;
import com.open.item.service.VoteCountRelationService;
import com.open.item.service.VoteItemRelationService;
import com.open.item.utils.CommonJson;
import com.open.item.utils.IdWorkerUtils;

/**
 * h5展示
 * 
 * @author towne
 * @date Oct 29, 2018
 */
@Controller
@RequestMapping("/view")
public class ViewController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ViewController.class);

    private static String DEFAULT_COUNT = "10";

    @Resource(name = "articleService")
    private ArticleService articleService;

    @Resource(name = "voteItemRelationService")
    private VoteItemRelationService voteItemRelationService;

    @Resource(name = "voteCountRelationService")
    private VoteCountRelationService voteCountRelationService;

    @Resource(name = "accessService")
    private AccessService accessService;

    @Resource(name = "imgService")
    private ImgService imgService;

    @RequestMapping("/nf")
    public ModelAndView toNoFound() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("404");
        return mav;
    }

    // @RequestMapping("/main/index")
    // public ModelAndView h5viewIndex(HttpServletRequest request) {
    // ModelAndView mav = new ModelAndView();
    // mav.setViewName("h5view/index");
    // return mav;
    // }

    @RequestMapping("/{vte}/{articleId}")
    public ModelAndView toCurrentView(HttpServletRequest request, @PathVariable String articleId,
            @PathVariable String vte) {
        ModelAndView mav = new ModelAndView();
        if (StringUtils.isBlank(articleId)) {
            mav.setViewName("404");
            return mav;
        }
        if (StringUtils.isBlank(vte)) {
            mav.setViewName("404");
            return mav;
        }
        Article art = articleService.findById(articleId);
        if (art == null) {
            mav.setViewName("404");
            return mav;
        }
        // 预告变更为展示报名
        if (ViewTypeEnum.getByCode(vte) == ViewTypeEnum.NOTICE) {
            mav.addObject("article", art);
            mav.setViewName("h5view/" + vte + "/detail");
            return mav;
        }
        List<VoteItemRelation> vir = voteItemRelationService.findByParentId(art.getVoteId());
        if (CollectionUtils.isEmpty(vir)) {
            mav.addObject("vir", "");
        } else {
            Collections.sort(vir, Comparator.comparing(VoteItemRelation::getIdx));
            fetchCountByVir(vir, art.getArticleId());
            mav.addObject("vir", JSON.toJSONString(vir));
        }
        mav.addObject("article", art);
        String ipAddr = request.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isBlank(ipAddr)) {
            ipAddr = request.getRemoteAddr();
        }
        mav.addObject("accessCount", accessCount(articleId, ipAddr));
        mav.setViewName("h5view/" + vte + "/detail");
        return mav;
    }

    private int accessCount(String artId, String ip) {
        try {
            Date current = new Date();
            Access access = new Access();
            access.setAccessId(IdWorkerUtils.acsIdWorker());
            access.setAccessIp(ip);
            access.setArticleId(artId);
            access.setCreateTime(current);
            accessService.save(access);
            return accessService.findAccessCountByArtId(artId);
        } catch (Exception e) {
            logger.error("统计访问出错！错误原因:{}", e.getMessage());
            return 0;
        }

    }

    @RequestMapping("/main/{viewType}")
    public ModelAndView toViewMain(HttpServletRequest request, @PathVariable String viewType) {
        ModelAndView mav = new ModelAndView();
        ViewTypeEnum vte = validateReqParam(viewType);
        if (vte == null) {
            mav.setViewName("404");
            return mav;
        }
        // List<Article> arts = articleService.findByType(vte);
        // List<H5View> h5vList = art2H5v(arts);
        // mav.addObject("h5vs", h5vList);
        if (vte == ViewTypeEnum.CURRENT) {
            List<Article> topArts = articleService.findByIsTop(BooleanEnum.YES);
            List<H5View> h5vList = art2H5v(topArts);
            mav.addObject("topH5vs", h5vList);
        }
        mav.addObject("vte", vte);
        mav.setViewName("h5view/main");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/main/load")
    public String ajaxViewMain(HttpServletRequest request, int page, int pageSize, String viewType) {
        ViewTypeEnum vte = validateReqParam(viewType);
        if (vte == null) {
            return CommonJson.dataResponse(CommonJson.ERROR, "没有更多数据了");
        }
        Page<Article> artsPage = articleService.findArtPage4View(page * pageSize, pageSize, vte);
        if (artsPage == null) {
            return CommonJson.dataResponse(CommonJson.ERROR, "没有更多数据了");
        }
        List<Article> arts = artsPage.getItems();
        if (CollectionUtils.isEmpty(arts)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "没有更多数据了");
        }
        List<H5View> h5vList = art2H5v(arts);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("total", artsPage.getTotalCount());
        resultMap.put("data", JSON.toJSONString(h5vList));
        return CommonJson.dataResponse(CommonJson.SUCC, JSON.toJSONString(resultMap));
    }

    private List<H5View> art2H5v(List<Article> arts) {
        List<H5View> h5vs = new ArrayList<H5View>();
        if (CollectionUtils.isEmpty(arts)) {
            return h5vs;
        }
        for (Article art : arts) {
            H5View h5v = new H5View();
            h5v.setViewId(art.getArticleId());
            h5v.setTitle(art.getTitle());
            Img img = findImgByArt(art.getImgId());
            if (img == null) {
                h5v.setPicUrl("resources/images/dfi.jpeg");
            } else {
                h5v.setPicUrl("/upload/" + img.getSysFileName());
            }
            // h5v.setDescr("活动时间:" + art.getStartTimeLabel());
            h5v.setStartTime(art.getStartDateLabel());
            // h5v.setEndTime(art.getEndTimeLabel());
            h5vs.add(h5v);
        }
        return h5vs;
    }

    private Img findImgByArt(String imgId) {
        try {
            return imgService.findById(imgId);
        } catch (Exception e) {
            logger.info("view通过art查找img失败!错误原因:{}", e.getMessage());
            return null;
        }
    }

    private ViewTypeEnum validateReqParam(String viewType) {
        if (StringUtils.isBlank(viewType)) {
            logger.info("view列表请求参数为空");
            return null;
        }
        try {
            return ViewTypeEnum.valueOf(viewType.toUpperCase());
        } catch (Exception e) {
            logger.error("view列表枚举转换出错!vte:{}", viewType);
            return null;
        }
    }

    private void fetchCountByVir(List<VoteItemRelation> vir, String articleId) {
        for (VoteItemRelation voteItemRelation : vir) {
            VoteCountRelation vcr = voteCountRelationService.findByTripleId(articleId, voteItemRelation.getVoteId(),
                    voteItemRelation.getVoteItemRelationId());
            if (vcr != null) {
                voteItemRelation.setVcr(vcr);
                voteItemRelation.setProcess(compareCount(vcr.getCounter()));
            } else {
                vcr = createVcr(articleId, voteItemRelation.getVoteId(), voteItemRelation.getVoteItemRelationId());
                voteCountRelationService.save(vcr);
                voteItemRelation.setVcr(vcr);
                voteItemRelation.setProcess(compareCount(vcr.getCounter()));
            }
        }
    }

    private VoteCountRelation createVcr(String articleId, String voteId, String voteItemId) {
        VoteCountRelation vcr = new VoteCountRelation();
        vcr.setArticleId(articleId);
        vcr.setCounter(0);
        vcr.setVoteCountRelationId(IdWorkerUtils.vcrIdWorker());
        vcr.setVoteItemRelationId(voteItemId);
        vcr.setVoteId(voteId);
        return vcr;
    }

    private BigDecimal compareCount(long sourceCount) {
        if (sourceCount > 0) {
            BigDecimal result = new BigDecimal(sourceCount).divide(new BigDecimal(DEFAULT_COUNT)).setScale(0,
                    BigDecimal.ROUND_HALF_UP);
            if (result.compareTo(BigDecimal.ZERO) == 0) {
                return new BigDecimal("0.5");
            }
            if (result.compareTo(new BigDecimal("100")) == 0) {
                return new BigDecimal("99.5");
            }
            return result;
        }
        return BigDecimal.ZERO;
    }

    @ResponseBody
    @RequestMapping("/doVote")
    public synchronized String doVote(HttpServletRequest request) {
        String articleId = request.getParameter("articleId");
        String[] vcrs = request.getParameterValues("vcrs");
        if (StringUtils.isBlank(articleId)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "投票失败，请稍后再试.");
        }
        if (vcrs == null || vcrs.length == 0) {
            return CommonJson.dataResponse(CommonJson.ERROR, "投票选项不能为空.");
        }
        Article article = articleService.findById(articleId);
        if (article == null) {
            return CommonJson.dataResponse(CommonJson.ERROR, "投票异常，请稍后再试.");
        }
        // 判断是否过期
        if (checkVoteTime(article.getEndTime())) {
            return CommonJson.dataResponse(CommonJson.ERROR, "投票时间已经过期咯!");
        }
        for (String vrc : vcrs) {
            VoteCountRelation exVcr = voteCountRelationService.findById(vrc);
            if (exVcr != null) {
                long sumCount = Long.sum(exVcr.getCounter(), 1);
                exVcr.setCounter(sumCount);
                voteCountRelationService.update(exVcr);
            }
        }
        return CommonJson.dataResponse(CommonJson.SUCC, null);
    }

    private boolean checkVoteTime(Date et) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String current = sdf.format(new Date());
            String endTime = sdf.format(et);
            if (sdf.parse(current).compareTo(sdf.parse(endTime)) > 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error("比较投票时间出错!错误原因:{}", e.getMessage());
        }
        return false;
    }
}
