package com.vegemil.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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



@Controller
public class AdminBeanSoupController {
	
	@Autowired
	AdminBeanSoupService adminBeanSoupService;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
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
    public @ResponseBody Map<String, Object> saveBeanSoupNews(@ModelAttribute("params") final AdminBeanSoupNewsDTO beanSoupNews)throws Exception{
		
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		try {
			
			if("I".equals(beanSoupNews.getAction())) {
				String uuid = UUID.randomUUID().toString();
				String originalName = beanSoupNews.getFileName().getOriginalFilename();
				if(!"".equals(originalName)) {
					String file1 = originalName.substring(originalName.lastIndexOf("\\") + 1);
					
					String savefileName1 = uuid + "_" + file1;
					Path savePath = Paths.get(uploadPath + "/main/BeanSoup/assets/news/" + savefileName1);
					beanSoupNews.getFileName().transferTo(savePath);
					beanSoupNews.setMThum(savefileName1);
				}
			}
			
			boolean isResulted = adminBeanSoupService.saveBeanSoupNews(beanSoupNews);
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
    public @ResponseBody Map<String, Object> saveBeanSoupEvent(@ModelAttribute("params") final AdminBeanSoupEventDTO beanSoupEvent)throws Exception{
		
		Map<String, Object> rtnMsg = new HashMap<String, Object>();
		try {
			
//			if("I".equals(beanSoupEvent.getAction())) {
//				if(beanSoupEvent.getMIdx() == null) {
//					System.out.println("이미지 등록로직 시작");
//					String uuid = UUID.randomUUID().toString();
//					String originalName = beanSoupEvent.getFileName().getOriginalFilename();
//					System.out.println(beanSoupEvent.getFileName());
//					
//					if(!"".equals(originalName)) {
//						String file1 = originalName.substring(originalName.lastIndexOf("\\") + 1);
//						
//						String savefileName1 = uuid + "_" + file1;
//						//저장 - 실제경로
//						//Path savePath = Paths.get(uploadPath + "/main/BeanSoup/assets/event/" + savefileName1);
//						//저장 - Test로컬경로
//						Path savePath = Paths.get("D:/upload/admin/beanSoup" + savefileName1);		
//						beanSoupEvent.getFileName().transferTo(savePath);
//						beanSoupEvent.setMThum(savefileName1);
//					}
//					
//				}
//			}
			
			System.out.println("beanSoupEvent정보: " +beanSoupEvent.toString());
			boolean isResulted = adminBeanSoupService.saveBeanSoupEvent(beanSoupEvent);
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
	
	
	//쎔네일 수정
	@RequestMapping(value = "/admin/manage/beanSoup/editImg", method = { RequestMethod.GET, RequestMethod.POST })
	public boolean updateBeanSoupEventImg(@ModelAttribute("params") AdminBeanSoupEventDTO params, Model model,
			HttpServletResponse response, HttpServletRequest request) {
		try {


			boolean isEdited = adminBeanSoupService.updateBeanSoupEventImg(params);
			if (!isEdited) {
				return false;
			}

		} catch (DataAccessException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
	
}
