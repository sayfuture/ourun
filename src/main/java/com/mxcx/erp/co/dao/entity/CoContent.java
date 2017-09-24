package com.mxcx.erp.co.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.mxcx.ec.base.commons.util.DateUtil;
import com.mxcx.erp.base.commons.controller.BasePo;

/**
 * 内容管理
 * 
 * @author 20140910
 * 
 */
@Entity
@Table(name = "CO_CONTENT")
public class CoContent extends BasePo {
	private static final long serialVersionUID = 5926656027040677715L;

	@Id
	@Column(name = "ID", nullable = false)
	private String id; // 主键Id

	@Column(name = "TITLE")
	private String title; // 内容标题

	@Column(name = "INTRODUCE")
	private String introduce; // 内容短文

	@Column(name = "BODY")
	private String body; // 内容正文

	@Column(name = "TIME")
	private Date time; // 上传时间

	@Column(name = "SOURCE")
	private String source; // 内容源自
	

	@Column(name = "CLICK")
	private Integer click; // 点击量
	
	@Column(name = "FILEURL")
	private String fileUrl;  //封面图片路径
	
	@Column(name = "KEYWORDS")
	private String keywords;
	
	@Column(name="WEIGHT")
	private Integer weight;			//权重
	

	@ManyToOne
	@JoinColumn(name = "CO_TYPE")
	private CoType coType; // 类型

	@Column(name="WEIMG_URL")
	private String weimg_url;

	@Column(name="WETYPE")
	private String wetype;

	@Column(name="MEDIA_ID")
	private String media_id;

	@Column(name="CREATED_AT")
	private Long created_at;
	@Column(name="companyid")
	private String companyid;
	
	@Transient
	private String coTypeId;

	@Transient
	private String time2;

	public String getCoTypeId() {
		return coTypeId;
	}

	public void setCoTypeId(String coTypeId) {
		this.coTypeId = coTypeId;
	}

	public String getTime2() {
		if (time != null) {
			time2 = DateUtil.format(time, "yyyy-MM-dd HH:mm:ss");
		}
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public String getId() {
		return id;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getBody() {
		return body;
	}

	public CoType getCoType() {
		return coType;
	}

	public void setCoType(CoType coType) {
		this.coType = coType;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getClick() {
		return click;
	}

	public void setClick(Integer click) {
		this.click = click;
	}


	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}


	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getWeimg_url() {
		return weimg_url;
	}

	public void setWeimg_url(String weimg_url) {
		this.weimg_url = weimg_url;
	}

	public String getWetype() {
		return wetype;
	}

	public void setWetype(String wetype) {
		this.wetype = wetype;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public Long getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Long created_at) {
		this.created_at = created_at;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
}