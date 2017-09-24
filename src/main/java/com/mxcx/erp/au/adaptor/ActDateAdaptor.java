package com.mxcx.erp.au.adaptor;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mxcx.erp.au.dao.entity.AuEmployee;

public class ActDateAdaptor {
	
	public static String[] actDate(ActDateType actDateType){
		String[] dateScope = new String[2];
		
		switch (actDateType) {
		case week:
			dateScope[0] = getMondayOfThisWeek();
			dateScope[1] = getSundayOfThisWeek();
			break;
		case month:
			dateScope[0] = getFirstOfThisMonth();
			dateScope[1] = getLastOfThisMonth();
			break;
		case year:
			dateScope[0] = getFirstDataOfThisYear();
			dateScope[1] = getLastDataOfThisYear();
			break;
		default:
			break;
		}
		
		return dateScope;
	}
	
	public static Map<String, Integer[]> getData(List<Object[]> list,ActDateType actDateType,Integer type,AuEmployee auEmployee){
		Map<String, Integer[]> map = null;
		switch (type) {
		case 1:
			map = getDataByTypeSelf(list, actDateType,auEmployee);
			break;
		case 2:
			map = getDataByTypeDept(list, actDateType,auEmployee);
			break;
		case 3:
			map = getDataByTypeAll(list, actDateType,auEmployee);
			break;
		default:
			break;
		}
		return map;
	}
	
	public static String getSundayOfThisWeek() {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		
		if (day_of_week == 0) day_of_week = 7;
		
		c.add(Calendar.DATE, -day_of_week + 8);
		return sdf.format(c.getTime());
	}
	
	public static String getMondayOfThisWeek() {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		
		if (day_of_week == 0)day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		
		return sdf.format(c.getTime());
	}
	
	public static String getFirstDataOfThisYear(){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar currCal=Calendar.getInstance();    
	    int currentYear = currCal.get(Calendar.YEAR);  
	    
	    Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, currentYear);  
        
	    return sdf.format(calendar.getTime());  
	}
	
	public static String getLastDataOfThisYear(){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar currCal=Calendar.getInstance();    
	    int currentYear = currCal.get(Calendar.YEAR);  
	    
	    Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, currentYear + 1);  
        
	    return sdf.format(calendar.getTime());  
	}
	
	public static String getFirstOfThisMonth() {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal_1=Calendar.getInstance();
		cal_1.set(Calendar.DAY_OF_MONTH,1);
        
		return sdf.format(cal_1.getTime());
	}
	
	public static String getLastOfThisMonth() {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();   
	    c.set(Calendar.DAY_OF_MONTH,1);
	    c.add(Calendar.MONTH,1);
	    
		return sdf.format(c.getTime());
	}
	
	public static Integer getDayCountOfThisMonth(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.DATE,cal.get(Calendar.DATE)-1);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	public static Map<String, Integer[]> getDataByTypeAll(List<Object[]> list,ActDateType actDateType,AuEmployee auEmployee){
		Map<String, Integer[]> map = new HashMap<String, Integer[]>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			switch (actDateType) {
			case week:
				Map<String, String> names = new HashMap<String, String>();
				
				for (Object[] obj : list) {
					if (!names.containsKey(obj[0].toString())) {
						names.put(obj[0].toString(), obj[0].toString());
					}
				}
				
				for (String name : names.keySet()) {
					Integer[] dataNumber = new Integer[7];
					Calendar c = Calendar.getInstance();
					c.setTime(sdf.parse(getMondayOfThisWeek()));
					
					for (int i = 0; i < dataNumber.length; i++) {
						Integer count = 0;
						for (Object[] data : list){
							if (data[0].toString().equals(name)) {
								if((sdf.format(sdf.parse(data[1].toString()))).equals(sdf.format(c.getTime())))
								{
									count = Integer.parseInt(data[2].toString());
								}
							}
						}
						c.add(Calendar.DAY_OF_MONTH, 1);
						dataNumber[i] = count;
					}
					map.put(name, dataNumber);
				}
				break;
			case month:
				Map<String, String> names1 = new HashMap<String, String>();
				
				for (Object[] obj : list) {
					if (!names1.containsKey(obj[0].toString())) {
						names1.put(obj[0].toString(), obj[0].toString());
					}
				}
				
				for (String name : names1.keySet()) {
					Integer[] dataNumber = new Integer[getDayCountOfThisMonth()];
					
					Calendar d = Calendar.getInstance();
					d.setTime(sdf.parse(getFirstOfThisMonth()));
					
					for (int i = 0; i < dataNumber.length; i++) {
						Integer count = 0;
						for (Object[] data : list){
							if (data[0].toString().equals(name)) {
								if((sdf.format(sdf.parse(data[1].toString()))).equals(sdf.format(d.getTime())))
								{
									count = Integer.parseInt(data[2].toString());
								}
							}
						}
						d.add(Calendar.DAY_OF_MONTH, 1);
						dataNumber[i] = count;
					}
					map.put(name, dataNumber);
				}
				break;
			case year:
				Map<String, String> names2 = new HashMap<String, String>();
				
				for (Object[] obj : list) {
					if (!names2.containsKey(obj[0].toString())) {
						names2.put(obj[0].toString(), obj[0].toString());
					}
				}
				
				for (String name : names2.keySet()) {
					Integer[] dataNumber = new Integer[12];
					
					Calendar e = Calendar.getInstance();
					e.setTime(sdf.parse(getFirstDataOfThisYear()));
					
					for (int i = 0; i < dataNumber.length; i++) {
						Integer count = 0;
						for (Object[] data : list){
							if (data[0].toString().equals(name)) {
								
								if(Integer.parseInt(data[1].toString()) == i+1)
								{
									count = Integer.parseInt(data[2].toString());
								}
							}
						}
						e.add(Calendar.DAY_OF_MONTH, 1);
						dataNumber[i] = count;
					}
					map.put(name, dataNumber);
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public static Map<String, Integer[]> getDataByTypeDept(List<Object[]> list,ActDateType actDateType,AuEmployee auEmployee){
		Map<String, Integer[]> map = new HashMap<String, Integer[]>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			switch (actDateType) {
			case week:
				
				Map<String, String> names = new HashMap<String, String>();
				
				for (Object[] obj : list) {
					if (!names.containsKey(obj[0].toString())) {
						names.put(obj[0].toString(), obj[0].toString());
					}
				}
				
				for (String name : names.keySet()) {
					Integer[] dataNumber = new Integer[7];
					Calendar c = Calendar.getInstance();
					c.setTime(sdf.parse(getMondayOfThisWeek()));
					
					for (int i = 0; i < dataNumber.length; i++) {
						Integer count = 0;
						for (Object[] data : list){
							if (data[0].toString().equals(name)) {
								if((sdf.format(sdf.parse(data[1].toString()))).equals(sdf.format(c.getTime())))
								{
									count = Integer.parseInt(data[2].toString());
								}
							}
						}
						c.add(Calendar.DAY_OF_MONTH, 1);
						dataNumber[i] = count;
					}
					map.put(name, dataNumber);
				}
				
				break;
			case month:
				Map<String, String> names1 = new HashMap<String, String>();
				
				for (Object[] obj : list) {
					if (!names1.containsKey(obj[0].toString())) {
						names1.put(obj[0].toString(), obj[0].toString());
					}
				}
				
				for (String name : names1.keySet()) {
					Integer[] dataNumber = new Integer[getDayCountOfThisMonth()];
					
					Calendar d = Calendar.getInstance();
					d.setTime(sdf.parse(getFirstOfThisMonth()));
					
					for (int i = 0; i < dataNumber.length; i++) {
						Integer count = 0;
						for (Object[] data : list){
							if (data[0].toString().equals(name)) {
								if((sdf.format(sdf.parse(data[1].toString()))).equals(sdf.format(d.getTime())))
								{
									count = Integer.parseInt(data[2].toString());
								}
							}
						}
						d.add(Calendar.DAY_OF_MONTH, 1);
						dataNumber[i] = count;
					}
					map.put(name, dataNumber);
				}
				break;
			case year:
				Map<String, String> names2 = new HashMap<String, String>();
				
				for (Object[] obj : list) {
					if (!names2.containsKey(obj[0].toString())) {
						names2.put(obj[0].toString(), obj[0].toString());
					}
				}
				
				for (String name : names2.keySet()) {
					Integer[] dataNumber = new Integer[12];
					
					Calendar e = Calendar.getInstance();
					e.setTime(sdf.parse(getFirstDataOfThisYear()));
					
					for (int i = 0; i < dataNumber.length; i++) {
						Integer count = 0;
						for (Object[] data : list){
							if (data[0].toString().equals(name)) {
								
								if(Integer.parseInt(data[1].toString()) == i+1)
								{
									count = Integer.parseInt(data[2].toString());
								}
							}
						}
						e.add(Calendar.DAY_OF_MONTH, 1);
						dataNumber[i] = count;
					}
					map.put(name, dataNumber);
				}
				
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return map;
	}
	
	public static Map<String, Integer[]> getDataByTypeSelf(List<Object[]> list,ActDateType actDateType,AuEmployee auEmployee){
		Map<String, Integer[]> map = new HashMap<String, Integer[]>();
		Integer[] dataNumber = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			switch (actDateType) {
			case week:
				dataNumber = new Integer[7];
				
				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(getMondayOfThisWeek()));
				
				for (int i = 0; i < dataNumber.length; i++) {
					Integer count = 0;
					
					for (Object[] data : list) {
						
						if((sdf.format(sdf.parse(data[0].toString()))).equals(sdf.format(c.getTime())))
						{
							count = Integer.parseInt(data[1].toString());
						}
					}
					c.add(Calendar.DAY_OF_MONTH, 1);
					dataNumber[i] = count;
				}
				
				break;
			case month:
				dataNumber = new Integer[getDayCountOfThisMonth()];
				
				Calendar d = Calendar.getInstance();
				d.setTime(sdf.parse(getFirstOfThisMonth()));
				
				for (int i = 0; i < dataNumber.length; i++)
				{
					Integer count = 0;
					for (Object[] data : list) {
						
						if((sdf.format(sdf.parse(data[0].toString()))).equals(sdf.format(d.getTime())))
						{
							count = Integer.parseInt(data[1].toString());
						}
					}
					d.add(Calendar.DAY_OF_MONTH, 1);
					dataNumber[i] = count;
				}
				break;
			case year:
				dataNumber = new Integer[12];
				
				Calendar e = Calendar.getInstance();
				e.setTime(sdf.parse(getFirstDataOfThisYear()));
				
				for (int i = 0; i < dataNumber.length; i++)
				{
					Integer count = 0;
					for (Object[] data : list) {
						if(Integer.parseInt(data[0].toString()) == i+1)
						{
							count = Integer.parseInt(data[1].toString());
						}
					}
					e.add(Calendar.DAY_OF_MONTH, 1);
					dataNumber[i] = count;
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		map.put(auEmployee.getRealName(), dataNumber);
		
		return map;
	}
}
