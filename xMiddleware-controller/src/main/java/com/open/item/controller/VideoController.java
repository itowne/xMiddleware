package com.open.item.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
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
import com.open.item.entity.Page;
import com.open.item.entity.Vdo;
import com.open.item.entity.enumObject.StatEnum;
import com.open.item.service.VdoService;
import com.open.item.utils.CommonJson;
import com.open.item.utils.IdWorkerUtils;

/**
 * 
 * @author towne
 * @date Nov 18, 2018
 */
@Controller
@RequestMapping("/vdo")
public class VideoController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ArtApplyController.class);

    private static String VDO_PRIFIX = "/vdo/";

    @Resource(name = "vdoService")
    private VdoService vdoService;

    @Value("${upload.path}")
    private String uploadPath;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("vdo/index");
        return mav;
    }

    @RequestMapping("/vdoShow/{fileName}")
    public ModelAndView vdoShow(HttpServletRequest request, @PathVariable String fileName) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("vdo/vdo");
        mav.addObject("fileName", fileName);
        return mav;
    }

    @ResponseBody
    @RequestMapping("/vdoList")
    public DataTableBean<Vdo> list(HttpServletRequest request, int start, int length) {
        Page<Vdo> page = vdoService.findVdoPage(start, length);
        DataTableBean<Vdo> dtb = new DataTableBean<Vdo>(page);
        return dtb;
    }

    @RequestMapping("/toAddVdo")
    public ModelAndView toAdd(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("vdo/add");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/doAddVdo")
    public String doAdd(HttpServletRequest req) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(req.getSession().getServletContext());
        String fileName = "";
        if (!multipartResolver.isMultipart(req)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "文件类型不正确");
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
        String vdoName = multiRequest.getParameter("vdoName");
        MultipartFile uf = multiRequest.getFile("file");
        if (uf != null) {
            try {
                Vdo vdo = findFileByMd5(uf.getBytes());
                if (StringUtils.isBlank(vdo.getVdoId())) {
                    String suffix = UUID.randomUUID().toString().replace("-", "");
                    String ext = uf.getOriginalFilename().substring(uf.getOriginalFilename().lastIndexOf(".") + 1);
                    fileName = "VDO_" + suffix + "." + ext;
                    addVdo(uf, fileName, vdoName, sessionUser().getUserId(), vdo.getFileMd5());
                    String path = uploadPath + VDO_PRIFIX + fileName;
                    uf.transferTo(new File(path));
                } else {
                    fileName = vdo.getSysFileName();
                }
            } catch (Exception e) {
                logger.info("vdo新增失败！错误原因:{}", e.getMessage());
                return CommonJson.dataResponse(CommonJson.ERROR, e.getMessage());
            }
        }
        // String ctx = req.getScheme() + "://" + req.getServerName() + ":" +
        // req.getServerPort() + req.getContextPath();
        return CommonJson.dataResponse(CommonJson.SUCC, null);
    }

    private void addVdo(MultipartFile file, String sysFileName, String vdoName, String createId, String md5) {
        Vdo vdo = new Vdo();
        vdo.setVdoId(IdWorkerUtils.vdoIdWorker());
        vdo.setVdoName(vdoName);
        vdo.setVdoUrl(uploadPath + VDO_PRIFIX);
        vdo.setStat(StatEnum.VALID);
        vdo.setCreateTime(new Date());
        vdo.setUpdTime(new Date());
        vdo.setCreateId(createId);
        vdo.setFileMd5(md5);
        vdo.setOriFileName(file.getOriginalFilename());
        vdo.setSysFileName(sysFileName);
        vdoService.save(vdo);
    }

    private Vdo findFileByMd5(byte[] b) {
        String md5Hex = DigestUtils.md5Hex(b);
        List<Vdo> md5Vdos = vdoService.findByMd5(md5Hex);
        if (md5Vdos == null) {
            Vdo vdo = new Vdo();
            vdo.setFileMd5(md5Hex);
            return vdo;
        } else {
            return md5Vdos.get(0);
        }
    }

    @RequestMapping("/toEditVdo/{vdoId}")
    public ModelAndView toEdit(HttpServletRequest request, @PathVariable String vdoId) {
        ModelAndView mav = new ModelAndView();
        if (StringUtils.isBlank(vdoId)) {
            mav.setViewName("vdo/index");
            return mav;
        }
        Vdo exVdo = vdoService.findById(vdoId);
        if (exVdo == null) {
            mav.setViewName("vdo/index");
            return mav;
        }
        mav.addObject("vdo", exVdo);
        mav.setViewName("vdo/edit");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/doEditVdo")
    public String doEdit(HttpServletRequest req) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(req.getSession().getServletContext());
        String fileName = "";
        if (!multipartResolver.isMultipart(req)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "文件类型不正确");
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
        String vdoName = multiRequest.getParameter("vdoName");
        String vdoId = multiRequest.getParameter("vdoId");
        if (StringUtils.isBlank(vdoId)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "修改的记录不存在");
        }
        Vdo exVdo = vdoService.findById(vdoId);
        if (exVdo == null) {
            return CommonJson.dataResponse(CommonJson.ERROR, "修改的记录不存在");
        }
        MultipartFile uf = multiRequest.getFile("file");
        if (uf != null) {
            try {
                Vdo md5Vdo = findFileByMd5(uf.getBytes());
                if (StringUtils.isBlank(md5Vdo.getVdoId())) {
                    String suffix = UUID.randomUUID().toString().replace("-", "");
                    String ext = uf.getOriginalFilename().substring(uf.getOriginalFilename().lastIndexOf(".") + 1);
                    fileName = "VDO_" + suffix + "." + ext;
                    updateVdo(uf, fileName, vdoName, exVdo, md5Vdo.getFileMd5());
                    String path = uploadPath + VDO_PRIFIX + fileName;
                    uf.transferTo(new File(path));
                } else {
                    exVdo.setVdoName(vdoName);
                    exVdo.setFileMd5(md5Vdo.getFileMd5());
                    exVdo.setSysFileName(md5Vdo.getSysFileName());
                    exVdo.setUpdTime(new Date());
                    fileName = md5Vdo.getSysFileName();
                }
            } catch (Exception e) {
                logger.info("vdo修改失败！错误原因:{}", e.getMessage());
                return CommonJson.dataResponse(CommonJson.ERROR, e.getMessage());
            }
        } else {
            exVdo.setUpdTime(new Date());
            exVdo.setVdoName(vdoName);
            fileName = exVdo.getSysFileName();
        }
        vdoService.update(exVdo);
        // String ctx = req.getScheme() + "://" + req.getServerName() + ":" +
        // req.getServerPort() + req.getContextPath();
        return CommonJson.dataResponse(CommonJson.SUCC, null);
    }

    private void updateVdo(MultipartFile file, String sysFileName, String vdoName, Vdo exVdo, String md5) {
        exVdo.setUpdTime(new Date());
        exVdo.setFileMd5(md5);
        exVdo.setOriFileName(file.getOriginalFilename());
        exVdo.setSysFileName(sysFileName);
    }

    @ResponseBody
    @RequestMapping("/delVdo/{vdoId}")
    public String delImg(HttpServletRequest request, @PathVariable String vdoId) {
        if (StringUtils.isBlank(vdoId)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "删除记录ID为空");
        }
        Vdo exVdo = vdoService.findById(vdoId);
        if (exVdo == null) {
            return CommonJson.dataResponse(CommonJson.ERROR, "删除记录不存在");
        }
        try {
            vdoService.del(exVdo);
            return CommonJson.dataResponse(CommonJson.SUCC, null);
        } catch (Exception e) {
            logger.info("删除vdo失败!错误原因:{}", e.getMessage());
            return CommonJson.dataResponse(CommonJson.ERROR, e.getMessage());
        }
    }
}
