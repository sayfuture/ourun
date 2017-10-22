package com.mxcx.erp.area.dao.service;

import com.mxcx.erp.area.dao.entity.Provinces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class SystemCache implements ServletContextAware {

    public static Map<String,Object> cache=new HashMap<String,Object>();
    @Autowired
    AreaService areaService;
    @Override
    public void setServletContext(ServletContext servletContext) {
        List<Provinces> list=areaService.findProvinces();
        cache.put("area",list);
    }
}
