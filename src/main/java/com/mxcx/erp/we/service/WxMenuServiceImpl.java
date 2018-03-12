package com.mxcx.erp.we.service;


import com.mxcx.ec.base.commons.dao.IBaseDao;
import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.ec.base.commons.util.StringCheck;
import com.mxcx.ec.base.commons.util.ToolUtils;
import com.mxcx.ec.base.commons.util.UuidDitch;

import com.mxcx.erp.au.dao.entity.AuDept;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.au.dao.entity.TreeAuDeptVo;
import com.mxcx.erp.base.adaptor.LogFunction;
import com.mxcx.erp.base.adaptor.LogModule;
import com.mxcx.erp.base.commons.service.BaseService;
import com.mxcx.erp.lo.service.LogManagementService;
import com.mxcx.erp.we.dao.entity.TreeWxMenuVo;
import com.mxcx.erp.we.dao.entity.WxMenu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WxMenuServiceImpl
 * Fri Mar 09 16:27:50 CST 2018 hmy
 */

@Service
public class WxMenuServiceImpl extends BaseService<WxMenu> implements WxMenuService {

    @Autowired

    private IBaseDao<WxMenu> wxMenuDao;
    @Autowired
    private LogManagementService logManagementService;

    @Override
    public Boolean addWxMenu(WxMenu wxMenu, AuEmployee auEmployee) {
        boolean flag = false;
        try {
            wxMenu.setId(UuidDitch.getId(LogModule.WXMENU.getModuleNo()));
            flag = addPo(wxMenu);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.WXMENU.toString(), LogFunction.WXMENU_CREATE.toString(), wxMenu.toString());
        }
        return flag;
    }

    @Override
    public Boolean deleteWxMenu(String id, AuEmployee auEmployee) {
        WxMenu wxMenu = null;
        Boolean flag = true;
        try {
//            wxMenu = (WxMenu) this.getOne(id, WxMenu.class);
//            removeByState(wxMenu);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.WXMENU.toString(), LogFunction.WXMENU_DELETE.toString(), wxMenu.toString());
        }
        return flag;
    }

    @Override
    public Boolean modifyWxMenu(WxMenu wxMenu, AuEmployee auEmployee) {
        Boolean flag = true;
        WxMenu wxMenuTemp = new WxMenu();
        try {
            wxMenuTemp = (WxMenu) this.wxMenuDao.getById(WxMenu.class, wxMenu.getId());
            wxMenuTemp.setName(wxMenu.getName());
            this.modify(wxMenuTemp);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            logManagementService.addLogManagement(auEmployee, ToolUtils.getHostAddress(), flag, LogModule.WXMENU.toString(), LogFunction.WXMENU_UPDATE.toString(), wxMenu.toString());
        }
        return flag;
    }


    @Override
    public WxMenu findWxMenuByID(String id) {
        return (WxMenu) getOne(id, WxMenu.class);
    }


    private String ischildren(WxMenu wxMenu){
        Boolean flag=true;
        for(WxMenu wx:wxMenu.getWxMenuset()){
                flag=false;
        }
        return flag== true ? "open" : "closed";
    }

    @Override
    public List<TreeWxMenuVo> findTree(String parentId) {
        String hql="from WxMenu x where  1=1 ";
        hql+= StringUtils.isEmpty(parentId) ? " and x.superWxMenu is null  " : " and x.superWxMenu.id='"+parentId+"'";
        hql=hql+" order by x.id asc";
        List<TreeWxMenuVo> treeWxMenus=new ArrayList<TreeWxMenuVo>();
        List<WxMenu> lists=wxMenuDao.find(hql);
        for(WxMenu wxMenu:lists){
            TreeWxMenuVo vo=new TreeWxMenuVo();
            vo.setId(wxMenu.getId());
            vo.setName(wxMenu.getName());
            if(wxMenu.getType().equals("view"))
                vo.setType("链接");
            if(wxMenu.getType().equals("click"))
                vo.setType("点击事件");
            if(wxMenu.getType().equals("miniprogram")) {
                vo.setType("小程序");
                vo.setPagepath(wxMenu.getPagepath());
            }
            vo.setUrl(wxMenu.getUrl());
            vo.setState(this.ischildren(wxMenu));
            treeWxMenus.add(vo);
        }
        return treeWxMenus;
    }


}

