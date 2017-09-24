/*
 * TestObj.java       1.0    2014-1-20
 *
 * Copyright (c) 2011, 2013 LeSaaS Technology Co., Ltd.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * LeSaaS Technology Co., Ltd. ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use 
 * it only in accordance with the terms of the license agreement you 
 * entered into with LeSaaS.
 */
package com.mxcx.erp.au.dao.entity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * 
 * @author Administrator 2014-1-20 下午3:01:26
 * @version 1.0
 */
@Entity 
@Table(name = "test")
public class TestObj implements Serializable {

    private static final long serialVersionUID = -7758801742643210953L;

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "NAME", nullable = false)
    private String name;
    
    @Transient
    private String loginame;

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id; 
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

	public String getLoginame() {
		return loginame;
	}

	public void setLoginame(String loginame) {
		this.loginame = loginame;
	}
    
}
