package com.vegemil.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vegemil.adapter.GsonLocalDateTimeAdapter;
import com.vegemil.constant.Method;
import com.vegemil.domain.FactpostDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.ScheduleDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.domain.VisitDTO;
import com.vegemil.service.RndService;
import com.vegemil.util.UiUtils;

@Controller
public class RndController extends UiUtils {

	@Autowired
	RndService rndService;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	@GetMapping(value="/rnd/factory")
	public String getFactory() {
		return "rnd/factory";
	}
	
	
	@GetMapping(value = "/rnd/getTourSchedule")
	 public @ResponseBody JsonObject getTourSchedule() {

		JsonObject jsonObj = new JsonObject();
		List<ScheduleDTO> volunteerList = rndService.getTourScheduleList();
		if (CollectionUtils.isEmpty(volunteerList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(volunteerList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
			
		}

		return jsonObj;
	 }
	
	
	@GetMapping(value = "/rnd/tourApply")
    public String moveTourApply(Model model, Authentication authentication, @RequestParam(value = "date", required = false) String date)throws Exception{
		
		MemberDTO member = new MemberDTO();
		
		if(!date.equals("")) {
			model.addAttribute("date",date);
		} else {
			return showMessageWithRedirect("???????????? ?????? ???????????????.", "/rnd/factory", Method.GET, null, model);
		}
		if(authentication != null) {
	        member = (MemberDTO) authentication.getPrincipal();  //userDetail ????????? ?????????
	        model.addAttribute("member",member);	//?????? ??????
		}
		
		return "rnd/tourApply";
    }

	
	@PostMapping(value="/rnd/factoryTour")
	public String postVisitForm(VisitDTO visitDto, Model model, Authentication authentication)  {
		
		try {
		
			if(authentication == null) {
				return showMessageWithRedirect("???????????? ??????????????????.", "/rnd/factoryTour", Method.GET, null, model);
			} else {
			
				int result = rndService.insertMvisit(visitDto); 
		
				if(result > 0) {
					return showMessageWithRedirect("?????? ????????? ??????????????? ?????????????????????.", "/rnd/factoryTour", Method.GET, null, model);
				}
			}
		
		} catch(Exception e) {
			return showMessageWithRedirect("???????????? ?????? ???????????????.", "/", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("?????? ????????? ??????????????????.", "/rnd/factoryTour", Method.GET, null, model);

	}
	
	@GetMapping("/rnd/tourReview")
	public String getFactoryReviewList(Model model, SearchDTO params) {
		
		List<FactpostDTO> list;
		list = rndService.getFactReviewList(params);
		int listCount = 0;
		
		if(list != null) {
			listCount = list.size();
		}
		
		model.addAttribute("list", list);
		model.addAttribute("listCount", listCount);
		model.addAttribute("params",params);
		
		return "rnd/tourReview";
	}
	
	@GetMapping("/rnd/reviewDetail/{sIdx}")
	public String getFactoryReviewList(Model model, @PathVariable(value = "sIdx", required = false) String sIdx) {
		
		FactpostDTO review = rndService.getTourReview(sIdx);
		model.addAttribute("review",review);
		
		return "rnd/reviewDetail";
	}
	
	@GetMapping("/rnd/reviewWrite")
	public String moveReviewWrite(Model model, SearchDTO params) {
		
		FactpostDTO review = new FactpostDTO();
		model.addAttribute("review", review);
		
		return "rnd/reviewWrite";
	}
	
	@GetMapping(value = "/rnd/saveReview")
    public @ResponseBody JsonObject saveReview(Model model, Authentication authentication, FactpostDTO review)throws Exception{
		
		JsonObject jsonObj = new JsonObject();

		try {
		
			if(review == null) {
				jsonObj.addProperty("message", "???????????? ?????? ???????????????.");
			} else {
				if(authentication != null) {
					MemberDTO member = new MemberDTO();
					member = (MemberDTO) authentication.getPrincipal();  //userDetail ????????? ?????????
			        review.setSId(member.getMId());
			        review.setSName(member.getMName());
			        review.setSHp(member.getMHp());
			        review.setSEmail(member.getMEmail());
			        review.setSAddr(member.getMAddr1()+member.getMAddr2());
					int result = rndService.saveReview(review);
					if(result == 0) {
						jsonObj.addProperty("message", "????????? ??????????????????.");
					}
					jsonObj.addProperty("success", "1");
				}
			}
		
		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "?????????????????? ?????? ????????? ????????? ?????????????????????.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "???????????? ????????? ?????????????????????.");
		}

		return jsonObj;
    }
	
	//?????? ????????? ????????????
	@GetMapping("/web/upload/{viewname}/{filename}")
	public ResponseEntity<Resource> display(@PathVariable(value = "viewname", required = false) String viewname,
											@PathVariable(value = "filename", required = false) String filename) {
		Resource resource = new FileSystemResource(uploadPath + "/upload/tourReview/" + filename);
		if(!resource.exists()) 
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try {
			filePath = Paths.get(uploadPath + "/upload/tourReview/" + filename);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	@RequestMapping(value="/rnd/multiImageUpload")
	public void smarteditorMultiImageUpload(HttpServletRequest request, HttpServletResponse response){
		try {
			//????????????
			String sFileInfo = "";
			//???????????? ????????? - ?????? ???????????????
			String sFilename = request.getHeader("file-name");
			//?????? ?????????
			String sFilenameExt = sFilename.substring(sFilename.lastIndexOf(".")+1);
			//???????????????????????? ??????
			sFilenameExt = sFilenameExt.toLowerCase();
				
			//????????? ?????? ????????????
			String[] allowFileArr = {"jpg","png","bmp","gif"};

			//????????? ??????
			int nCnt = 0;
			for(int i=0; i<allowFileArr.length; i++) {
				if(sFilenameExt.equals(allowFileArr[i])){
					nCnt++;
				}
			}

			//???????????? ????????????
			if(nCnt == 0) {
				PrintWriter print = response.getWriter();
				print.print("NOTALLOW_"+sFilename);
				print.flush();
				print.close();
			} else {
				//???????????? ?????? ??? ?????????	
				
				//????????????
				//String filePath = "D:/upload/";
				String filePath = uploadPath + "/upload/tourReview/";
				File file = new File(filePath);
				
				if(!file.exists()) {
					file.mkdirs();
				}
				
				String sRealFileNm = "";
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				String today= formatter.format(new java.util.Date());
				sRealFileNm = today+UUID.randomUUID().toString() + sFilename.substring(sFilename.lastIndexOf("."));
				String rlFileNm = filePath + sRealFileNm;
				
				///////////////// ????????? ???????????? ///////////////// 
				InputStream inputStream = request.getInputStream();
				OutputStream outputStream=new FileOutputStream(rlFileNm);
				int numRead;
				byte bytes[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
				while((numRead = inputStream.read(bytes,0,bytes.length)) != -1){
					outputStream.write(bytes,0,numRead);
				}
				if(inputStream != null) {
					inputStream.close();
				}
				outputStream.flush();
				outputStream.close();
				
				///////////////// ????????? /////////////////
				// ?????? ??????
				sFileInfo += "&bNewLine=true";
				// img ????????? title ????????? ????????????????????? ?????????????????? ??????
				sFileInfo += "&sFileName="+ sFilename;
				//sFileInfo += "&sFileURL="+"D:/upload/"+sRealFileNm;
				sFileInfo += "&sFileURL="+ uploadPath + "/upload/tourReview/"+sRealFileNm;
				PrintWriter printWriter = response.getWriter();
				printWriter.print(sFileInfo);
				printWriter.flush();
				printWriter.close();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
