package com.open.item.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.open.item.beans.DataTableBean;
import com.open.item.entity.Article;
import com.open.item.entity.Img;
import com.open.item.entity.Page;
import com.open.item.entity.Vdo;
import com.open.item.entity.Vote;
import com.open.item.entity.enumObject.BooleanEnum;
import com.open.item.entity.enumObject.ImgEnum;
import com.open.item.entity.enumObject.StatEnum;
import com.open.item.entity.relation.vote.VoteCountRelation;
import com.open.item.entity.relation.vote.VoteItemRelation;
import com.open.item.service.ArticleService;
import com.open.item.service.ImgService;
import com.open.item.service.VdoService;
import com.open.item.service.VoteCountRelationService;
import com.open.item.service.VoteItemRelationService;
import com.open.item.service.VoteService;
import com.open.item.utils.CommonJson;
import com.open.item.utils.IdWorkerUtils;

@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Resource(name = "articleService")
    private ArticleService articleService;

    @Resource(name = "voteService")
    private VoteService voteService;

    @Resource(name = "voteItemRelationService")
    private VoteItemRelationService voteItemRelationService;

    @Resource(name = "voteCountRelationService")
    private VoteCountRelationService voteCountRelationService;

    @Resource(name = "vdoService")
    private VdoService vdoService;

    @Resource(name = "imgService")
    private ImgService imgService;

    @Value("${upload.path}")
    private String uploadPath;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("article/index");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/articleList")
    public DataTableBean<Article> list(HttpServletRequest request, int start, int length) {
        Page<Article> page = articleService.findArtPage(start, length);
        DataTableBean<Article> dtb = new DataTableBean<Article>(page);
        return dtb;
    }

    @RequestMapping("/toAddArticle")
    public ModelAndView toAdd(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("votes", JSONObject.toJSONString(voteSelectList()));
        mav.addObject("imgs", JSONObject.toJSONString(imgSelectList()));
        mav.addObject("vdos", JSONObject.toJSONString(vdoSelectList()));
        mav.setViewName("article/add");
        return mav;
    }

    private List<Vdo> vdoSelectList() {
        List<Vdo> exVdos = vdoService.findVdoList();
        if (CollectionUtils.isEmpty(exVdos)) {
            return new ArrayList<Vdo>();
        }
        for (Vdo vdo : exVdos) {
            vdo.setVdoUrl("");
        }
        return exVdos;
    }

    private List<Vote> voteSelectList() {
        List<Vote> votes = voteService.findVoteList();
        if (CollectionUtils.isEmpty(votes)) {
            return new ArrayList<Vote>();
        }
        return votes;
    }

    private List<Img> imgSelectList() {
        List<Img> exImgs = imgService.findImgList();
        if (CollectionUtils.isEmpty(exImgs)) {
            return new ArrayList<Img>();
        }
        for (Img img : exImgs) {
            img.setImgUrl("");
        }
        return exImgs;
    }

    @ResponseBody
    @RequestMapping("/doAddArticle")
    public String doAdd(HttpServletRequest request) {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String endTime = request.getParameter("endTime");
        String voteId = request.getParameter("voteId");
        String content = request.getParameter("content");
        String imgId = request.getParameter("imgId");
        String startTime = request.getParameter("startTime");
        String isTop = request.getParameter("isTop");
        if (StringUtils.isBlank(title) || StringUtils.isBlank(endTime) || StringUtils.isBlank(startTime)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "标题或开始时间或结束时间不能空");
        }
        if (StringUtils.isBlank(isTop)) {
            isTop = "NO";
        }
        try {
            Article art = new Article();
            String articleId = IdWorkerUtils.artIdWorker();
            art.setTitle(title);
            art.setArticleId(articleId);
            art.setAuthor(author);
            art.setCreateId(sessionUser().getUserId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            art.setEndTime(sdf.parse(endTime));
            art.setStartTime(sdf.parse(startTime));
            art.setFooter("");
            art.setStat(StatEnum.VALID);
            art.setContent(content);
            art.setCreateTime(new Date());
            art.setUpdTime(new Date());
            art.setVoteId(voteId);
            art.setImgId(imgId);
            art.setIsTop(BooleanEnum.valueOf(isTop));
            articleService.save(art);
            addInitVoteCount(articleId, voteId);
            return CommonJson.dataResponse(CommonJson.SUCC, null);
        } catch (Exception e) {
            logger.error("新增内容失败!错误原因:{}", e.getMessage());
            return CommonJson.dataResponse(CommonJson.ERROR, e.getMessage());
        }
    }

    private void addInitVoteCount(String articleId, String voteId) {
        List<VoteCountRelation> vcrList = voteCountRelationService.findByVoteIdAndArticleId(articleId, voteId);
        if (CollectionUtils.isNotEmpty(vcrList)) {
            return;
        }
        List<VoteItemRelation> virList = voteItemRelationService.findByParentId(voteId);
        if (CollectionUtils.isEmpty(virList)) {
            return;
        }
        for (VoteItemRelation voteItemRelation : virList) {
            VoteCountRelation vcr = createVcr(articleId, voteId, voteItemRelation.getVoteItemRelationId());
            voteCountRelationService.save(vcr);
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

    @RequestMapping("/toEditArticle/{articleId}")
    public ModelAndView toEdit(HttpServletRequest request, @PathVariable String articleId) {
        ModelAndView mav = new ModelAndView();
        if (StringUtils.isBlank(articleId)) {
            logger.error("articleId为空");
            mav.setViewName("article/index");
            return mav;
        }
        Article art = articleService.findById(articleId);
        if (art == null) {
            logger.error("内容对象不存在");
            mav.setViewName("article/index");
            return mav;
        }
        mav.addObject("article", art);
        mav.addObject("votes", JSONObject.toJSONString(voteSelectList()));
        mav.addObject("imgs", JSONObject.toJSONString(imgSelectList()));
        mav.addObject("vdos", JSONObject.toJSONString(vdoSelectList()));
        mav.setViewName("article/edit");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/doEditArticle")
    public String doEdit(HttpServletRequest request) {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String endTime = request.getParameter("endTime");
        String voteId = request.getParameter("voteId");
        String content = request.getParameter("content");
        String articleId = request.getParameter("articleId");
        String imgId = request.getParameter("imgId");
        String startTime = request.getParameter("startTime");
        String isTop = request.getParameter("isTop");
        if (StringUtils.isBlank(articleId)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "修改内容id为空");
        }
        if (StringUtils.isBlank(title)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "标题不能空");
        }
        if (StringUtils.isBlank(endTime)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "结束时间不能空");
        }
        if (StringUtils.isBlank(startTime)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "开始时间不能空");
        }
        if (StringUtils.isBlank(isTop)) {
            isTop = "NO";
        }
        Article exArt = articleService.findById(articleId);
        if (exArt == null) {
            return CommonJson.dataResponse(CommonJson.ERROR, "修改记录不存在");
        }
        if (!exArt.getIsTop().name().equals(isTop)) {
            exArt.setIsTop(BooleanEnum.valueOf(isTop));
        }
        try {
            exArt.setAuthor(author);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            exArt.setTitle(title);
            exArt.setEndTime(sdf.parse(endTime));
            exArt.setStartTime(sdf.parse(startTime));
            exArt.setContent(content);
            exArt.setUpdTime(new Date());
            exArt.setVoteId(voteId);
            exArt.setImgId(imgId);
            articleService.update(exArt);
            addInitVoteCount(exArt.getArticleId(), voteId);
            return CommonJson.dataResponse(CommonJson.SUCC, null);
        } catch (Exception e) {
            logger.error("修改内容失败!错误原因:{}", e.getMessage());
            return CommonJson.dataResponse(CommonJson.ERROR, e.getMessage());
        }

    }

    @ResponseBody
    @RequestMapping("delArticle/{articleId}")
    public String delArticle(HttpServletRequest request, @PathVariable String articleId) {
        if (StringUtils.isBlank(articleId)) {
            logger.info("删除内容id为空");
            return CommonJson.dataResponse(CommonJson.ERROR, "删除内容id为空");
        }
        Article art = articleService.findById(articleId);
        if (art == null) {
            logger.info("删除内容不存在");
            return CommonJson.dataResponse(CommonJson.ERROR, "删除内容不存在");
        }
        articleService.delArticle(art);
        return CommonJson.dataResponse(CommonJson.SUCC, null);
    }

    @ResponseBody
    @RequestMapping("doTop/{articleId}/{isTop}")
    public String doTop(HttpServletRequest request, @PathVariable String articleId, @PathVariable String isTop) {
        if (StringUtils.isBlank(articleId)) {
            logger.info("删除内容id为空");
            return CommonJson.dataResponse(CommonJson.ERROR, "置顶才做内容id为空");
        }
        Article art = articleService.findById(articleId);
        if (art == null) {
            logger.info("置顶内容不存在");
            return CommonJson.dataResponse(CommonJson.ERROR, "置顶内容不存在");
        }
        if (StringUtils.isBlank(isTop)) {
            isTop = "NO";
        }
        art.setIsTop(BooleanEnum.valueOf(isTop));
        articleService.update(art);
        return CommonJson.dataResponse(CommonJson.SUCC, null);
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(HttpServletRequest req) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(req.getSession().getServletContext());
        String fileName = "";
        if (multipartResolver.isMultipart(req)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    // 上传
                    try {
                        Img img = findFileByMd5(file.getBytes());
                        if (img == null) {
                            String suffix = UUID.randomUUID().toString().replace("-", "");
                            String ext = file.getOriginalFilename()
                                    .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                            fileName = "ART_" + suffix + "." + ext;
                            addImg(file, fileName, sessionUser().getUserId());
                            String path = uploadPath + fileName;
                            file.transferTo(new File(path));
                        } else {
                            fileName = img.getSysFileName();
                        }
                    } catch (Exception e) {
                        logger.error("插入图片失败！错误原因:{}", e.getMessage());
                        return CommonJson.dataResponse(CommonJson.ERROR, e.getMessage());
                    }
                }
            }
        }
        String ctx = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        return CommonJson.dataResponse(CommonJson.SUCC, ctx + "/upload" + "/" + fileName);
    }

    private void addImg(MultipartFile file, String sysFileName, String createId) {
        Img img = new Img();
        img.setImgId(IdWorkerUtils.imgIdWorker());
        img.setImgName("未命名");
        img.setImgUrl(uploadPath);
        img.setStat(StatEnum.VALID);
        img.setCreateTime(new Date());
        img.setUpdTime(new Date());
        img.setCreateId(createId);
        img.setImgType(ImgEnum.ART_IMG);
        try {
            img.setFileMd5(DigestUtils.md5Hex(file.getBytes()));
        } catch (Exception e) {
            logger.info("设置文件MD5失败!错误原因:{}", e.getMessage());
            img.setFileMd5("0");
        }
        img.setOriFileName(file.getOriginalFilename());
        img.setSysFileName(sysFileName);
        imgService.save(img);
    }

    private Img findFileByMd5(byte[] b) {
        String md5Hex = DigestUtils.md5Hex(b);
        List<Img> md5Imgs = imgService.findByMd5(md5Hex);
        return CollectionUtils.isNotEmpty(md5Imgs) ? md5Imgs.get(0) : null;
    }
}
