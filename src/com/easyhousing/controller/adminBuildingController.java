package com.easyhousing.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.easyhousing.dao.BuildingDealDao;
import com.easyhousing.dao.BuildingInfoDao;
import com.easyhousing.model.BuildingDeal;
import com.easyhousing.model.BuildingInfo;
import com.easyhousing.model.RentHouse;

@Controller
public class adminBuildingController {
	
	@Autowired
	private BuildingInfoDao buildingInfoDao;

	@RequestMapping(value = "adminAddBuilding.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView adminAddBuilding(@RequestParam(value ="inputAddbuildingTimeHanded") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,HttpServletRequest request, BuildingInfo buildingInfo) throws IllegalStateException, IOException {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();

		buildingInfo.setBuildingTimeHanded(date);
		buildingInfoDao.insertBuildingInfo(buildingInfo);
		
		List<BuildingInfo> buildingInfoList = buildingInfoDao.selectAll();
		session.setAttribute("buildingInfoList", buildingInfoList);
		
		modelAndView.setViewName("SystemUser/managerBuilding");
		return modelAndView;
	}
	
	@RequestMapping(value="adminUpdateBuilding.do", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView adminUpdateBuilding(@RequestParam(value ="inputUpdatebuildingTimeHanded") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,HttpServletRequest request, BuildingInfo buildingInfo) {
		
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		
		buildingInfo.setBuildingTimeHanded(date);
		
		int updateBuildingId = 0;
		for(Cookie iCookie : cookies) {
			String name = iCookie.getName();
			String value = iCookie.getValue();
			if(name.equals("updateBuildingId")) {
				updateBuildingId = Integer.parseInt(value);
			}
		}
		
		buildingInfo.setBuildingId(updateBuildingId);
		buildingInfoDao.updateBuildingInfo(buildingInfo);
		
		List<BuildingInfo> buildingInfoList = buildingInfoDao.selectAll();
		session.setAttribute("buildingInfoList", buildingInfoList);
		
		modelAndView.setViewName("SystemUser/managerBuilding");
		return modelAndView;
	}
	
	@RequestMapping(value="deleteBuildingAjax.do", method={RequestMethod.GET,RequestMethod.POST})
	public void deleteBuildingAjax(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		int deleteBuildingId = 0;
		for(Cookie iCookie : cookies) {
			String name = iCookie.getName();
			String value = iCookie.getValue();
			if(name.equals("deleteBuildingId")) {
				deleteBuildingId = Integer.parseInt(value);
			}
		}
		
		BuildingInfo buildingInfo = new BuildingInfo();
		buildingInfo.setBuildingId(deleteBuildingId);
		buildingInfoDao.deleteBuildingInfo(buildingInfo);
		
		
		List<BuildingInfo> buildingInfoList = buildingInfoDao.selectAll();
		session.setAttribute("buildingInfoList", buildingInfoList);
		
	}
	
	@RequestMapping(value="deleteBuildingPartAjax.do", method={RequestMethod.GET,RequestMethod.POST})
	public void deleteBuildingPartAjax(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		String deleteBuildingPart = "";
		for(Cookie iCookie : cookies) {
			String name = iCookie.getName();
			String value = iCookie.getValue();
			if(name.equals("deleteBuildingPart")) {
				deleteBuildingPart = value;
			}
		}
		System.err.println(deleteBuildingPart);
		String[] ids = deleteBuildingPart.split("\\.");
		
		BuildingInfo buildingInfo = new BuildingInfo();
		
		for(String iString : ids) {
			if(iString == null) continue;
			if(iString == "") continue;
			System.err.println(iString);
			buildingInfo.setBuildingId(Integer.parseInt(iString));
			buildingInfoDao.deleteBuildingInfo(buildingInfo);
		}
	}
	
	@Autowired
	private BuildingDealDao buildingDealDao;
	
	@RequestMapping(value = "adminAddBuildingDeal.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView adminAddBuildingDeal(@RequestParam(value ="addDealtime") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,HttpServletRequest request, BuildingDeal buildingDeal) throws IllegalStateException, IOException {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		
		buildingDeal.setBuildingDealTime(date);
		buildingDealDao.insertBuildingDeal(buildingDeal);
		
		List<BuildingDeal> buildingDealList = buildingDealDao.selectAll();
		session.setAttribute("buildingDealList", buildingDealList);
		
		modelAndView.setViewName("SystemUser/managerBuildingHistory");
		return modelAndView;
	}
	
	@RequestMapping(value="adminUpdateBuildingDeal.do", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView adminUpdateBuildingDeal(@RequestParam(value ="updateDealtime") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,HttpServletRequest request, BuildingDeal buildingDeal) {
		
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		
		buildingDeal.setBuildingDealTime(date);
		
		int updateBuildingDealId = 0;
		for(Cookie iCookie : cookies) {
			String name = iCookie.getName();
			String value = iCookie.getValue();
			if(name.equals("updateBuildingDealId")) {
				updateBuildingDealId = Integer.parseInt(value);
			}
		}
		System.err.println(updateBuildingDealId);
		buildingDeal.setBuildingDealId(updateBuildingDealId);
		buildingDealDao.updateBuildingDeal(buildingDeal);
		
		List<BuildingDeal> buildingDealList = buildingDealDao.selectAll();
		session.setAttribute("buildingDealList", buildingDealList);
		
		modelAndView.setViewName("SystemUser/managerBuildingHistory");
		return modelAndView;
	}
	
	@RequestMapping(value="deleteBuildingDealAjax.do", method={RequestMethod.GET,RequestMethod.POST})
	public void deleteBuildingDealAjax(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		int delBuildingDealId = 0;
		for(Cookie iCookie : cookies) {
			String name = iCookie.getName();
			String value = iCookie.getValue();
			if(name.equals("delBuildingDealId")) {
				delBuildingDealId = Integer.parseInt(value);
			}
		}
		
		BuildingDeal buildingDeal = new BuildingDeal();
		buildingDeal.setBuildingDealId(delBuildingDealId);
		buildingDealDao.deleteBuildingDeal(buildingDeal);
		
		System.err.println(delBuildingDealId);
		
		List<BuildingDeal> buildingDealList = buildingDealDao.selectAll();
		session.setAttribute("buildingDealList", buildingDealList);
		
	}
	
	@RequestMapping(value="deleteBuildingDealPartAjax.do", method={RequestMethod.GET,RequestMethod.POST})
	public void deleteBuildingDealPartAjax(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		String deleteBuildingDealPart = "";
		for(Cookie iCookie : cookies) {
			String name = iCookie.getName();
			String value = iCookie.getValue();
			if(name.equals("deleteBuildingDealPart")) {
				deleteBuildingDealPart = value;
			}
		}
		System.err.println(deleteBuildingDealPart);
		String[] ids = deleteBuildingDealPart.split("\\.");
		
		BuildingDeal buildingDeal = new BuildingDeal();
		
		for(String iString : ids) {
			if(iString == null) continue;
			if(iString == "") continue;
			System.err.println(iString);
			buildingDeal.setBuildingDealId(Integer.parseInt(iString));
			buildingDealDao.deleteBuildingDeal(buildingDeal);
		}
	}
	
}
