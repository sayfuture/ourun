package com.mxcx.erp.gs.dao.entity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.mxcx.erp.base.commons.controller.BasePo;

/**
 * 商品分类实体类
 * 
 * 
 */
@Entity
@Table(name = "GS_GOODS_TYPE")
public class GsGoodsType extends BasePo{
	private static final long serialVersionUID = 7027656289068189944L;

	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	
	@Column(name = "NAME", nullable = false)
	private String name; // 级别名称
	
	@Column(name = "CAT_LEVEL", nullable = false)
	private Integer level; // 类别层级
	
	@Column(name="HOT_GS_GOODS_TYPE")
	private Integer hotGsGoodsType;   //0 -非热门， 1 热门
	
	@Column(name="GS_NUM")
	private int gsNum;   //商品类型点击量
	
	@Column(name="SEQ_NO")
	private Integer seq_no ;
	@Column(name="IS_TOP")
	private Integer is_top;
	@Column(name="TOP_USER")
	private String top_user;
	@Column(name="IMG_URL")
	private String img_url;
	
	
	
	@Transient
	private String level2; // 等级
	
	@ManyToOne
	@JoinColumn(name = "PARID")
	private GsGoodsType gsGoodsType;
	
	@OneToMany(mappedBy="gsGoodsType")
	@JsonIgnore
	private List<GsGoodsType> gsGoodsTypes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getHotGsGoodsType() {
		return hotGsGoodsType;
	}

	public void setHotGsGoodsType(Integer hotGsGoodsType) {
		this.hotGsGoodsType = hotGsGoodsType;
	}

	public String getLevel2() {
		if(null != this.level){
			level2 = level.toString();
		}
		return level2;
	}

	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public int getGsNum() {
		return gsNum;
	}

	public void setGsNum(int gsNum) {
		this.gsNum = gsNum;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public GsGoodsType getGsGoodsType() {
		return gsGoodsType;
	}

	public void setGsGoodsType(GsGoodsType gsGoodsType) {
		this.gsGoodsType = gsGoodsType;
	}

	public List<GsGoodsType> getGsGoodsTypes() {
		return gsGoodsTypes;
	}

	public void setGsGoodsTypes(List<GsGoodsType> gsGoodsTypes) {
		this.gsGoodsTypes = gsGoodsTypes;
	}

	public Integer getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(Integer seq_no) {
		this.seq_no = seq_no;
	}

	public Integer getIs_top() {
		return is_top;
	}

	public void setIs_top(Integer is_top) {
		this.is_top = is_top;
	}

	public String getTop_user() {
		return top_user;
	}

	public void setTop_user(String top_user) {
		this.top_user = top_user;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	} 
	
	
}
