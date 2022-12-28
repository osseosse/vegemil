package com.vegemil.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vegemil.adapter.GsonLocalDateTimeAdapter;
import com.vegemil.domain.AdminBeanSoupEventDTO;
import com.vegemil.domain.AdminBeanSoupNewsDTO;
import com.vegemil.domain.AdminBeanSoupVideoDTO;
import com.vegemil.service.AdminBeanSoupService;

import lombok.extern.log4j.Log4j2;



@Controller
public class AdminBeanSoupController {
	
	@Autowired
	AdminBeanSoupService adminBeanSoupService;
	
	@RequestMapping(value = "/admin/manage/beanSoup/{viewName}")
    public String adminBeanSoup(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		return "admin/beanSoup/"+viewName;
    }
	
	@RequestMapping(value = "/admin/manage/beanSoup/videoList")
    public @ResponseBody JsonObject getBeanSoupVideo(@ModelAttribute("params") final AdminBeanSoupVideoDTO params, HttpServletRequest req, 
    		Map<String, Object> commandMap)throws Exception{
		
		List<AdminBeanSoupVideoDTO> beanSoupVideoList = adminBeanSoupService.getBeanSoupVideoList(params);
		JsonObject jsonObj = new JsonObject();
		if (CollectionUtils.isEmpty(beanSoupVideoList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(beanSoupVideoList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}
		
		return jsonObj;
    }
	
	@GetMapping(value = "/admin/manage/beanSoup/displayBeanSoupVideo")
	public @ResponseBody boolean displayBeanSoupVideo(@RequestParam(value = "mIdx", required = false) Long mIdx, 
			@RequestParam(value = "mDisplay", required = false) Long mDisplay, HttpServletResponse response) throws Exception {
		boolean isRegistered = true;
		try {
			if (mIdx == null) {
				return false;
			}
			AdminBeanSoupVideoDTO beanSoupVideoDto = adminBeanSoupService.getBeanSoupVideo(mIdx);
			beanSoupVideoDto.setMIdx(mIdx);
			beanSoupVideoDto.setMDisplay(mDisplay);
			isRegistered = adminBeanSoupService.registerBeanSoupVideo(beanSoupVideoDto);
			if (!isRegistered) {
				throw new IOException("저장에 실패하였습니다.");
			}
		}catch (DataAccessException e) {
    		e.printStackTrace();
    		throw new Exception("저장에 실패하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("저장에 실패하였습니다");
		}
		return isRegistered;
	}
	
	@RequestMapping(value = "/admin/manage/saveBeanSoupVideo")
    public @ResponseBody Map<String, Object> saveBeanSoupVideo(@ModelAttribute("params") final AdminBeanSoupVideoDTO params)throws Exception{
		
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		try {
			boolean isResulted = adminBeanSoupService.saveBeanSoupVideo(params);
			rtnMsg.put("result", isResulted);
		}catch (DataAccessException e) {
    		e.printStackTrace();
    		throw new Exception("저장에 실패하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("저장에 실패하였습니다");
		}
		return rtnMsg;
    }
	
	@RequestMapping(value = "/admin/manage/beanSoup/newsList")
    public @ResponseBody JsonObject getBeanSoupNews(@ModelAttribute("params") final AdminBeanSoupNewsDTO params, HttpServletRequest req, 
    		Map<String, Object> commandMap)throws Exception{
		
		List<AdminBeanSoupNewsDTO> beanSoupNewsList = adminBeanSoupService.getBeanSoupNewsList(params);
		JsonObject jsonObj = new JsonObject();
		if (CollectionUtils.isEmpty(beanSoupNewsList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(beanSoupNewsList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}
		
		return jsonObj;
    }
	
	@GetMapping(value = "/admin/manage/beanSoup/displayBeanSoupNews")
	public @ResponseBody boolean displayBeanSoupNews(@RequestParam(value = "mIdx", required = false) Long mIdx, 
			@RequestParam(value = "mDisplay", required = false) Long mDisplay, @RequestParam(value = "mIng", required = false) Long mIng, 
			HttpServletResponse response) throws Exception {
		boolean isRegistered = true;
		try {
			if (mIdx == null) {
				return false;
			}
			AdminBeanSoupNewsDTO beanSoupNewsDto = adminBeanSoupService.getBeanSoupNews(mIdx);
			beanSoupNewsDto.setMIdx(mIdx);
			beanSoupNewsDto.setMDisplay(mDisplay);
			beanSoupNewsDto.setMIng(mIng);
			isRegistered = adminBeanSoupService.registerBeanSoupNews(beanSoupNewsDto);
			if (!isRegistered) {
				throw new IOException("저장에 실패하였습니다.");
			}
		}catch (DataAccessException e) {
    		e.printStackTrace();
    		throw new Exception("저장에 실패하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("저장에 실패하였습니다");
		}
		return isRegistered;
	}
	
	@RequestMapping(value = "/admin/manage/beanSoup/saveBeanSoupNews")
    public @ResponseBody Map<String, Object> saveBeanSoupNews(@ModelAttribute("params") final AdminBeanSoupNewsDTO params)throws Exception{
		
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		try {
			boolean isResulted = adminBeanSoupService.saveBeanSoupNews(params);
			rtnMsg.put("result", isResulted);
		}catch (DataAccessException e) {
    		e.printStackTrace();
    		throw new Exception("저장에 실패하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("저장에 실패하였습니다");
		}
		return rtnMsg;
    }
	
	@RequestMapping(value = "/admin/manage/beanSoup/eventList")
    public @ResponseBody JsonObject getBeanSoupEvent(@ModelAttribute("params") final AdminBeanSoupEventDTO params, HttpServletRequest req, 
    		Map<String, Object> commandMap)throws Exception{
		
		List<AdminBeanSoupEventDTO> beanSoupEventList = adminBeanSoupService.getBeanSoupEventList(params);
		JsonObject jsonObj = new JsonObject();
		if (CollectionUtils.isEmpty(beanSoupEventList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(beanSoupEventList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}
		
		return jsonObj;
    }
	
	@RequestMapping(value = "/admin/manage/beanSoup/saveBeanSoupEvent")
    public @ResponseBody Map<String, Object> saveBeanSoupEvent(@ModelAttribute("params") final AdminBeanSoupEventDTO params)throws Exception{
		
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		try {
			boolean isResulted = adminBeanSoupService.saveBeanSoupEvent(params);
			rtnMsg.put("result", isResulted);
		}catch (DataAccessException e) {
    		e.printStackTrace();
    		throw new Exception("저장에 실패하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("저장에 실패하였습니다");
		}
		return rtnMsg;
    }
}
