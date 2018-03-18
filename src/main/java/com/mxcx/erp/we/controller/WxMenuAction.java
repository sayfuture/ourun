package com.mxcx.erp.we.controller;

import com.mxcx.erp.base.commons.controller.BaseController;
import com.mxcx.erp.we.dao.entity.TreeWxMenuVo;
import com.mxcx.erp.we.dao.entity.WxMenu;
import com.mxcx.erp.we.service.WxMenuService;
import com.mxcx.erp.wechat.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * WxMenuAction
 * Fri Mar 09 16:27:50 CST 2018 hmy
 */


@Controller

public class WxMenuAction extends BaseController {

    @Autowired
    private WxMenuService wxMenuService;
    @Autowired
    private WeChatService weChatService;

    @RequestMapping(value = "/manager/erp/wx/wxMenu.do")
    public String index() {
        return "/ftl/manager/we/wxMenu";
    }

    @RequestMapping("/manager/erp/wx/findWxMenuList.do")
    @ResponseBody
    public List<TreeWxMenuVo> findWxMenuList(HttpServletRequest request) {
        String parentId=request.getParameter("id");
        List<TreeWxMenuVo> TreeWxMenuVos = wxMenuService.findTree(parentId);
        return TreeWxMenuVos;
    }

    @RequestMapping(value = "/manager/erp/wx/addWxMenu.do", method = RequestMethod.POST)
    @ResponseBody
    public String addWxMenu(WxMenu wxMenu, HttpServletRequest request) {
        if(wxMenu.getType().equals("view")){
            wxMenu.setPagepath(null);
            wxMenu.setKey(null);
            wxMenu.setAppid(null);
        }
        if(wxMenu.getType().equals("click")){
            wxMenu.setPagepath(null);
            wxMenu.setUrl(null);
            wxMenu.setAppid(null);
        }
        if(wxMenu.getType().equals("miniprogram")){
            wxMenu.setKey(null);
        }
        return wxMenuService.addWxMenu(wxMenu, this.getLoginUser(request));
    }

    @RequestMapping(value = "/manager/erp/wx/modifyWxMenu.do", method = RequestMethod.POST)
    @ResponseBody
    public Boolean modifyWxMenu(WxMenu wxMenu, HttpServletRequest request) {
        if(wxMenu.getType().equals("view")){
            wxMenu.setPagepath(null);
            wxMenu.setKey(null);
            wxMenu.setAppid(null);
        }
        if(wxMenu.getType().equals("click")){
            wxMenu.setPagepath(null);
            wxMenu.setUrl(null);
            wxMenu.setAppid(null);
        }
        if(wxMenu.getType().equals("miniprogram")){
            wxMenu.setKey(null);
        }
        return wxMenuService.modifyWxMenu(wxMenu, this.getLoginUser(request));
    }

    @RequestMapping(value = "/manager/erp/wx/deleteWxMenu.do")
    @ResponseBody
    public Boolean deleteWxMenu(String ids, HttpServletRequest request) {
        String teamIds[] = ids != null ? ids.split(",") : null;
        Boolean flag = true;
        try {
            if (null != teamIds) {
                for (String id : teamIds) {
                    wxMenuService.deleteWxMenu(id, this.getLoginUser(request));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @RequestMapping(value = "/manager/erp/wx/goToModifyWxMenu.do")
    @ResponseBody
    public WxMenu goToModifyWxMenu(String id) {
        WxMenu wxMenu = wxMenuService.findWxMenuByID(id);
        if(wxMenu.getSuperWxMenu()!=null){
            wxMenu.getSuperWxMenu().setSub_button(null);
        }
        if(wxMenu.getSub_button().size()>0){
            for(WxMenu subwxMenu:wxMenu.getSub_button())
                subwxMenu.setSuperWxMenu(null);
        }
        return wxMenu;
    }


    @RequestMapping(value = "/manager/erp/wx/createWxMenu.do")
    @ResponseBody
    public Boolean createWxMenu(String id) {
        Boolean flag=true;
        try {
            weChatService.wxCreateMenu();
        }catch(Exception e){
            flag=false;
            e.printStackTrace();
        }
        return flag;
    }
    @RequestMapping(value = "/manager/erp/wx/delWXMenu.do")
    @ResponseBody
    public Boolean delWXMenu() {
        Boolean flag=true;
        try {
            weChatService.wxDelMenu();
        }catch(Exception e){
            flag=false;
            e.printStackTrace();
        }
        return flag;
    }
}

