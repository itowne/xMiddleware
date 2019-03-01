package com.open.item.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.open.item.beans.DataTableBean;
import com.open.item.entity.Page;
import com.open.item.entity.Vote;
import com.open.item.entity.enumObject.StatEnum;
import com.open.item.entity.relation.vote.VoteItemRelation;
import com.open.item.service.VoteItemRelationService;
import com.open.item.service.VoteService;
import com.open.item.utils.CommonJson;
import com.open.item.utils.IdWorkerUtils;

/**
 * 投票控制类
 * 
 * @author towne
 * @date Oct 3, 2018
 */
@Controller
@RequestMapping("/vote")
public class VoteController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "voteService")
    private VoteService voteService;

    @Resource(name = "voteItemRelationService")
    private VoteItemRelationService voteItemRelationService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("vote/index");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/voteList")
    public DataTableBean<Vote> list(HttpServletRequest request, int start, int length) {
        Page<Vote> page = voteService.findVotePage(start, length);
        DataTableBean<Vote> dtb = new DataTableBean<Vote>(page);
        return dtb;
    }

    @RequestMapping("/toAddVote")
    public ModelAndView toAdd(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("vote/add");
        return mav;
    }

    @RequestMapping("doAddVote")
    @ResponseBody
    public String doAdd(HttpServletRequest request) {
        String voteTitle = request.getParameter("voteTitle");
        String[] subVoteTitle = request.getParameterValues("subVote");
        String[] subVoteIdx = request.getParameterValues("subVoteIdx");
        if (StringUtils.isBlank(voteTitle)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "投票组名称不能为空");
        }
        String voteId = createVote(voteTitle);
        if (StringUtils.isBlank(voteId)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "新增投票组失败");
        }

        if (subVoteTitle == null || subVoteIdx == null) {
            logger.info(voteTitle + "子投票项为空");
            return CommonJson.dataResponse(CommonJson.SUCC, null);
        }
        createSubVote(subVoteTitle, subVoteIdx, voteId);
        return CommonJson.dataResponse(CommonJson.SUCC, null);
    }

    private String createVote(String voteTitle) {
        try {
            Vote v = new Vote();
            v.setVoteId(IdWorkerUtils.votIdWorker());
            v.setVoteTitle(voteTitle);
            v.setStat(StatEnum.VALID);
            v.setCreateId(sessionUser().getUserId());
            v.setCreateTime(new Date());
            v.setUpdTime(new Date());
            voteService.save(v);
            return v.getVoteId();
        } catch (Exception e) {
            logger.info("投票组写入失败!错误原因:{}", e.getMessage());
        }
        return null;
    }

    private void createSubVote(String[] subVoteTitle, String[] subVoteIdx, String voteId) {
        try {
            for (int i = 0; i < subVoteTitle.length; i++) {
                if (StringUtils.isNotBlank(subVoteTitle[i]) && StringUtils.isNotBlank(subVoteIdx[i])) {
                    VoteItemRelation vir = createVirObj(voteId, subVoteTitle[i], subVoteIdx[i]);
                    voteItemRelationService.save(vir);
                }
            }
        } catch (Exception e) {
            logger.info("添加投票子项目失败！原因:{}", e.getMessage());
        }

    }

    private VoteItemRelation createVirObj(String voteId, String title, String idx) {
        VoteItemRelation vir = new VoteItemRelation();
        vir.setVoteId(voteId);
        vir.setVoteItemRelationId(IdWorkerUtils.virIdWorker());
        vir.setVoteName(title);
        vir.setIdx(Integer.parseInt(idx));
        return vir;
    }

    @RequestMapping("/toEditVote/{voteId}")
    public ModelAndView toEdit(HttpServletRequest request, @PathVariable String voteId) {
        ModelAndView mav = new ModelAndView();
        Vote vote = voteService.findById(voteId);
        if (vote == null) {
            logger.info("修改记录不存在.voteId:{}", voteId);
            mav.setViewName("vote/index");
            return mav;
        }
        mav.setViewName("vote/edit");
        mav.addObject("vote", vote);
        List<VoteItemRelation> virs = voteItemRelationService.findByParentId(voteId);
        if (CollectionUtils.isEmpty(virs)) {
            mav.addObject("voteSub", "");
            return mav;
        }
        mav.addObject("voteSub", JSONObject.toJSONString(virs));
        return mav;
    }

    @ResponseBody
    @RequestMapping("/doEditVote")
    public String doEdit(HttpServletRequest request) {
        String voteTitle = request.getParameter("voteTitle");
        String[] subVoteTitle = request.getParameterValues("subVote");
        String[] subVoteIdx = request.getParameterValues("subVoteIdx");
        String[] subVoteId = request.getParameterValues("subVoteId");
        String voteId = request.getParameter("voteId");
        if (StringUtils.isBlank(voteTitle)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "投票组名称不能为空");
        }
        Vote exVote = voteService.findById(voteId);
        if (exVote == null) {
            return CommonJson.dataResponse(CommonJson.ERROR, "修改的记录不存在");
        }
        exVote.setVoteTitle(voteTitle);
        voteService.update(exVote);
        List<VoteItemRelation> virs = voteItemRelationService.findByParentId(voteId);
        // 删除sub
        if (subVoteId == null && CollectionUtils.isNotEmpty(virs)) {
            for (VoteItemRelation voteItemRelation : virs) {
                voteItemRelationService.delVir(voteItemRelation);
            }
            return CommonJson.dataResponse(CommonJson.SUCC, null);
        }
        // 更新或删除或新增sub
        if (subVoteId != null && CollectionUtils.isNotEmpty(virs)) {
            // 不包含的说明被删除了
            for (VoteItemRelation vir : virs) {
                if (!ArrayUtils.contains(subVoteId, vir.getVoteItemRelationId())) {
                    voteItemRelationService.delVir(vir);
                }
            }
            for (int i = 0; i < subVoteId.length; i++) {
                VoteItemRelation exVir = voteItemRelationService.findById(subVoteId[i]);
                if (exVir == null) {
                    exVir = createVirObj(voteId, subVoteTitle[i], subVoteIdx[i]);
                    voteItemRelationService.save(exVir);
                } else {
                    exVir.setVoteName(subVoteTitle[i]);
                    exVir.setIdx(Integer.parseInt(subVoteIdx[i]));
                    voteItemRelationService.update(exVir);
                }
            }
            return CommonJson.dataResponse(CommonJson.SUCC, null);
        }
        // 新增sub
        if (subVoteId != null && CollectionUtils.isEmpty(virs)) {
            createSubVote(subVoteTitle, subVoteIdx, voteId);
        }
        return CommonJson.dataResponse(CommonJson.SUCC, null);
    }

    @ResponseBody
    @RequestMapping("delVote/{voteId}")
    public String delVote(HttpServletRequest request, @PathVariable String voteId) {
        if (StringUtils.isBlank(voteId)) {
            return CommonJson.dataResponse(CommonJson.ERROR, "删除的记录Id不存在");
        }
        Vote exVote = voteService.findById(voteId);
        if (exVote == null) {
            return CommonJson.dataResponse(CommonJson.ERROR, "删除的记录对象不存在");
        }
        voteService.delVote(exVote);
        List<VoteItemRelation> virs = voteItemRelationService.findByParentId(voteId);
        if (CollectionUtils.isNotEmpty(virs)) {
            for (VoteItemRelation voteItemRelation : virs) {
                voteItemRelationService.delVir(voteItemRelation);
            }
        }
        return CommonJson.dataResponse(CommonJson.SUCC, "删除投票记录成功");
    }
}
