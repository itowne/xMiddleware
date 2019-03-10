package com.open.item.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.open.item.beans.DataTableBean;
import com.open.item.entity.ArtApply;
import com.open.item.entity.Article;
import com.open.item.entity.Page;
import com.open.item.entity.enumObject.StatEnum;
import com.open.item.service.ArtApplyService;
import com.open.item.service.ArticleService;
import com.open.item.utils.CommonJson;
import com.open.item.utils.IdWorkerUtils;

/**
 * 
 * @author towne
 * @date Nov 17, 2018
 */
@Controller
@RequestMapping("/artApply")
public class ArtApplyController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ArtApplyController.class);

    @Resource(name = "artApplyService")
    private ArtApplyService artApplyService;

    @Resource(name = "articleService")
    private ArticleService articleService;

    @RequestMapping("/index/{articleId}")
    public ModelAndView index(HttpServletRequest request, @PathVariable String articleId) {
        ModelAndView mav = new ModelAndView();
        if (StringUtils.isBlank(articleId)) {
            mav.setViewName("redirect:/article/index");
            return mav;
        }
        Article art = articleService.findById(articleId);
        if (art != null) {
            mav.addObject("artTitle", art.getTitle());
        }
        mav.addObject("articleId", articleId);
        mav.setViewName("artApply/index");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/doAdd")
    public String doAdd(HttpServletRequest request) {
        String applyName = request.getParameter("applyName");
        String mobile = request.getParameter("mobile");
        String articleId = request.getParameter("articleId");
        if (StringUtils.isBlank(applyName)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "姓名不能为空");
        }
        if (StringUtils.isBlank(mobile)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "手机不能空");
        }
        if (StringUtils.isBlank(articleId)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "未找到该报名记录");
        }
        try {
            ArtApply exAa = artApplyService.findByArtcleIdAndMobile(articleId, mobile);
            if (exAa != null) {
                return CommonJson.dataResponse(CommonJson.ERROR, "您已经报名了!");
            }
            ArtApply aa = createApply(applyName, mobile, articleId);
            artApplyService.save(aa);
            return CommonJson.dataResponse(CommonJson.SUCC, null);
        } catch (Exception e) {
            logger.error("报名失败!错误原因:{},articleId:{},applyName:{},mobile:{}", e.getMessage(), articleId, applyName,
                    mobile);
            return CommonJson.dataResponse(CommonJson.ERROR, "报名失败!请稍后再试.");
        }
    }

    private ArtApply createApply(String applyName, String mobile, String articleId) {
        ArtApply aa = new ArtApply();
        aa.setArtApplyId(IdWorkerUtils.aayIdWorker());
        aa.setApplyName(applyName);
        aa.setMobile(mobile);
        aa.setStat(StatEnum.VALID);
        aa.setArticleId(articleId);
        aa.setCreateTime(new Date());
        aa.setUpdTime(new Date());
        return aa;
    }

    @ResponseBody
    @RequestMapping("/artApplyList/{articleId}")
    public DataTableBean<ArtApply> list(HttpServletRequest request, int start, int length,
            @PathVariable String articleId) {
        Page<ArtApply> page = artApplyService.findArtApplyPage(start, length, articleId);
        DataTableBean<ArtApply> dtb = new DataTableBean<ArtApply>(page);
        return dtb;
    }
}
