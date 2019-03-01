package com.open.item.controller;

import java.io.File;
import java.util.Date;
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

import com.open.item.beans.DataTableBean;
import com.open.item.entity.Img;
import com.open.item.entity.Page;
import com.open.item.entity.enumObject.ImgEnum;
import com.open.item.entity.enumObject.StatEnum;
import com.open.item.service.ImgService;
import com.open.item.utils.CommonJson;
import com.open.item.utils.IdWorkerUtils;

/**
 * 
 * @author towne
 * @date Oct 30, 2018
 */
@Controller
@RequestMapping("/img")
public class ImgController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ImgController.class);

    @Resource(name = "imgService")
    private ImgService imgService;

    @Value("${upload.path}")
    private String uploadPath;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("img/index");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/imgList")
    public DataTableBean<Img> list(HttpServletRequest request, int start, int length) {
        Page<Img> page = imgService.findImgPage(start, length);
        DataTableBean<Img> dtb = new DataTableBean<Img>(page);
        return dtb;
    }

    @RequestMapping("/toAddImg")
    public ModelAndView toAdd(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("img/add");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/doAddImg")
    public String doAdd(HttpServletRequest req) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(req.getSession().getServletContext());
        String fileName = "";
        if (!multipartResolver.isMultipart(req)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "文件类型不正确");
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
        String imgName = multiRequest.getParameter("imgName");
        MultipartFile uf = multiRequest.getFile("file");
        if (uf != null) {
            try {
                Img img = findFileByMd5(uf.getBytes());
                if (img == null) {
                    String suffix = UUID.randomUUID().toString().replace("-", "");
                    String ext = uf.getOriginalFilename().substring(uf.getOriginalFilename().lastIndexOf(".") + 1);
                    fileName = "IMG_" + suffix + "." + ext;
                    addImg(uf, fileName, imgName, sessionUser().getUserId());
                    String path = uploadPath + fileName;
                    uf.transferTo(new File(path));
                } else {
                    fileName = img.getSysFileName();
                }
            } catch (Exception e) {
                logger.info("IMG新增图片失败！错误原因:{}", e.getMessage());
                return CommonJson.dataResponse(CommonJson.ERROR, e.getMessage());
            }
        }
        String ctx = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        return CommonJson.dataResponse(CommonJson.SUCC, ctx + "/upload/" + fileName);
    }

    private void addImg(MultipartFile file, String sysFileName, String imgName, String createId) {
        Img img = new Img();
        img.setImgId(IdWorkerUtils.imgIdWorker());
        img.setImgName(imgName);
        img.setImgUrl(uploadPath);
        img.setStat(StatEnum.VALID);
        img.setCreateTime(new Date());
        img.setUpdTime(new Date());
        img.setCreateId(createId);
        img.setImgType(ImgEnum.IMG_IMG);
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

    @RequestMapping("/toEditImg/{imgId}")
    public ModelAndView toEdit(HttpServletRequest request, @PathVariable String imgId) {
        ModelAndView mav = new ModelAndView();
        if (StringUtils.isBlank(imgId)) {
            mav.setViewName("img/index");
            return mav;
        }
        Img exImg = imgService.findById(imgId);
        if (exImg == null) {
            mav.setViewName("img/index");
            return mav;
        }
        mav.addObject("img", exImg);
        mav.setViewName("img/edit");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/doEditImg")
    public String doEdit(HttpServletRequest req) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(req.getSession().getServletContext());
        String fileName = "";
        if (!multipartResolver.isMultipart(req)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "文件类型不正确");
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
        String imgName = multiRequest.getParameter("imgName");
        String imgId = multiRequest.getParameter("imgId");
        if (StringUtils.isBlank(imgId)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "修改的记录不存在");
        }
        Img exImg = imgService.findById(imgId);
        if (exImg == null) {
            return CommonJson.dataResponse(CommonJson.ERROR, "修改的记录不存在");
        }
        MultipartFile uf = multiRequest.getFile("file");
        if (uf != null) {
            try {
                Img md5Img = findFileByMd5(uf.getBytes());
                if (md5Img == null) {
                    String suffix = UUID.randomUUID().toString().replace("-", "");
                    String ext = uf.getOriginalFilename().substring(uf.getOriginalFilename().lastIndexOf(".") + 1);
                    fileName = "IMG_" + suffix + "." + ext;
                    updateImg(uf, fileName, imgName, exImg);
                    String path = uploadPath + fileName;
                    uf.transferTo(new File(path));
                } else {
                    exImg.setImgName(imgName);
                    exImg.setFileMd5(md5Img.getFileMd5());
                    exImg.setSysFileName(md5Img.getSysFileName());
                    exImg.setUpdTime(new Date());
                    fileName = md5Img.getSysFileName();
                }
            } catch (Exception e) {
                logger.info("IMG修改图片失败！错误原因:{}", e.getMessage());
                return CommonJson.dataResponse(CommonJson.ERROR, e.getMessage());
            }
        } else {
            exImg.setUpdTime(new Date());
            exImg.setImgName(imgName);
            fileName = exImg.getSysFileName();
        }
        imgService.update(exImg);
        String ctx = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        return CommonJson.dataResponse(CommonJson.SUCC, ctx + "/upload/" + fileName);
    }

    private void updateImg(MultipartFile file, String sysFileName, String imgName, Img exImg) {
        exImg.setUpdTime(new Date());
        try {
            exImg.setFileMd5(DigestUtils.md5Hex(file.getBytes()));
        } catch (Exception e) {
            logger.info("设置文件MD5失败!错误原因:{}", e.getMessage());
            exImg.setFileMd5("0");
        }
        exImg.setOriFileName(file.getOriginalFilename());
        exImg.setSysFileName(sysFileName);
    }

    @ResponseBody
    @RequestMapping("/delImg/{imgId}")
    public String delImg(HttpServletRequest request, @PathVariable String imgId) {
        if (StringUtils.isBlank(imgId)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "删除记录ID为空");
        }
        Img exImg = imgService.findById(imgId);
        if (exImg == null) {
            return CommonJson.dataResponse(CommonJson.ERROR, "删除记录不存在");
        }
        try {
            imgService.delImg(exImg);
            return CommonJson.dataResponse(CommonJson.SUCC, null);
        } catch (Exception e) {
            logger.info("删除图片失败!错误原因:{}", e.getMessage());
            return CommonJson.dataResponse(CommonJson.ERROR, e.getMessage());
        }
    }

}
