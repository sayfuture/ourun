package com.mxcx.erp.we.dao.entity;

import com.mxcx.ec.base.commons.dao.entity.TransparentPo;

import javax.persistence.*;
import java.util.Set;

/**
 * wx_menu
 * Fri Mar 09 16:27:50 CST 2018 hmy
 */

@Entity
@Table(name = "WX_MENU")
public class WxMenu implements TransparentPo {
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "NAME")
    private String name;
    @ManyToOne
    @JoinColumn(name = "P_ID")
    private WxMenu superWxMenu;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "KEY")
    private String key;
    @Column(name = "URL")
    private String url;
    @Column(name = "MEDIA_ID")
    private String media_id;
    @Column(name = "APPID")
    private String appid;
    @Column(name = "PAGEPATH")
    private String pagepath;
    @OneToMany(fetch=FetchType.EAGER,mappedBy="superWxMenu")
    private Set<WxMenu> wxMenuset;
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }

    public String getPagepath() {
        return pagepath;
    }

    public WxMenu getSuperWxMenu() {
        return superWxMenu;
    }

    public void setSuperWxMenu(WxMenu superWxMenu) {
        this.superWxMenu = superWxMenu;
    }

    public Set<WxMenu> getWxMenuset() {
        return wxMenuset;
    }

    public void setWxMenuset(Set<WxMenu> wxMenuset) {
        this.wxMenuset = wxMenuset;
    }
}

