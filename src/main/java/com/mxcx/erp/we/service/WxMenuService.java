package com.mxcx.erp.we.service;


import com.mxcx.ec.base.commons.dao.entity.PageParameter;
import com.mxcx.ec.base.commons.util.DataGrid;
import com.mxcx.erp.au.dao.entity.AuEmployee;
import com.mxcx.erp.we.dao.entity.TreeWxMenuVo;
import com.mxcx.erp.we.dao.entity.WxMenu;

import java.util.List;

/**
 * WxMenuService
 * Fri Mar 09 16:27:50 CST 2018 hmy
 */


public interface WxMenuService {

 public Boolean addWxMenu(WxMenu wxMenu, AuEmployee auEmployee);
 public Boolean deleteWxMenu(String id, AuEmployee auEmployee);
 public Boolean modifyWxMenu(WxMenu wxMenu, AuEmployee auEmployee);
 public List<TreeWxMenuVo> findTree(String parentId);
 public WxMenu findWxMenuByID(String id);
 public List<WxMenu> findWxMenuTree(String parentId);
}

