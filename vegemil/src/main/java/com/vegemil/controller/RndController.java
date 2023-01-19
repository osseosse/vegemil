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
import java.time.LocalDate;
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
			int applyCnt = rndService.getApplyCount(date);
			if(applyCnt > 1) {
				return showMessageWithRedirect("해당 날짜는 견학신청이 마감되었습니다.", "/rnd/factoryTour", Method.GET, null, model);
			}
			
		} else {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/rnd/factory", Method.GET, null, model);
		}
		if(authentication != null) {
	        member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
	        model.addAttribute("member",member);	//유저 정보
		}
		
		return "rnd/tourApply";
    }

	@GetMapping("/rnd/factoryTour")
	public String getFactoryVisit(Model model, Authentication authentication) {
		
		List<VisitDTO> visitList;
		MemberDTO member = new MemberDTO();
		String vEmail = "";
		
		// 현재 날짜 구하기 (시스템 시계, 시스템 타임존)
        LocalDate now = LocalDate.now();
        int year = now.getYear();
		
		if(authentication != null) {
	        member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
	        vEmail = member.getMEmail();
	        
		}
		visitList = rndService.getVisitList(vEmail);
		int listCount = 0;
		
		if(visitList != null) {
			listCount = visitList.size();
		}
        
		model.addAttribute("yyyy", year);
		model.addAttribute("visitList", visitList);
		model.addAttribute("listCount", listCount);
		
		return "rnd/factoryTour";
	}
	
	@PostMapping(value="/rnd/factoryTour")
	public String postVisitForm(VisitDTO visitDto, Model model, Authentication authentication)  {
		
		MemberDTO member = new MemberDTO();
		
		try {
		
			if(authentication == null) {
				return showMessageWithRedirect("로그인후 이용바랍니다.", "/rnd/factoryTour", Method.GET, null, model);
			} else {
			
				member = (MemberDTO) authentication.getPrincipal();
				VisitDTO tempDto = new VisitDTO();
				
				tempDto.setVAppdate(visitDto.getVAppdate().substring(0, 10));
				tempDto.setVApptime(visitDto.getVApptime());
				int applyTimeCnt = rndService.getApplyTimeCount(tempDto);
				if(applyTimeCnt > 0) {
					return showMessageWithRedirect("해당 시간대는 마감되었습니다.", "/rnd/factoryTour", Method.GET, null, model);
				}
				
				tempDto.setVEmail(visitDto.getVEmail());
				tempDto.setVAppdate(visitDto.getVAppdate().substring(0, 7));
				/*
				 * 홍보팀 황소연씨 요청 
				 * 월 1회 신청 제한 해제
				int applyCnt = rndService.getApplyCount(tempDto);
				if(applyCnt > 0) {
					return showMessageWithRedirect("해당 월에는 이미 신청하신 이력이 있습니다.", "/rnd/factoryTour", Method.GET, null, model);
				}
				*/
				
				int result = rndService.insertMvisit(visitDto); 
				if(result > 0) {
					return showMessageWithRedirect("견학 신청이 정상적으로 접수되었습니다.", "/rnd/factoryTour", Method.GET, null, model);
				}
			}
		
		} catch(Exception e) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("견학 신청이 실패했습니다.", "/rnd/factoryTour", Method.GET, null, model);

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
				jsonObj.addProperty("message", "올바르지 않은 접근입니다.");
			} else {
				if(authentication != null) {
					MemberDTO member = new MemberDTO();
					member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
			        review.setSId(member.getMId());
			        review.setSName(member.getMName());
			        review.setSHp(member.getMHp());
			        review.setSEmail(member.getMEmail());
			        review.setSAddr(member.getMAddr1()+member.getMAddr2());
					int result = rndService.saveReview(review);
					if(result == 0) {
						jsonObj.addProperty("message", "저장에 실패했습니다.");
					}
					jsonObj.addProperty("success", "1");
				}
			}
		
		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
    }
	
	//정적 이미지 불러오기
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
			//파일정보
			String sFileInfo = "";
			//파일명을 받는다 - 일반 원본파일명
			String sFilename = request.getHeader("file-name");
			//파일 확장자
			String sFilenameExt = sFilename.substring(sFilename.lastIndexOf(".")+1);
			//확장자를소문자로 변경
			sFilenameExt = sFilenameExt.toLowerCase();
				
			//이미지 검증 배열변수
			String[] allowFileArr = {"jpg","png","bmp","gif"};

			//확장자 체크
			int nCnt = 0;
			for(int i=0; i<allowFileArr.length; i++) {
				if(sFilenameExt.equals(allowFileArr[i])){
					nCnt++;
				}
			}

			//이미지가 아니라면
			if(nCnt == 0) {
				PrintWriter print = response.getWriter();
				print.print("NOTALLOW_"+sFilename);
				print.flush();
				print.close();
			} else {
				//디렉토리 설정 및 업로드	
				
				//파일경로
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
				
				///////////////// 서버에 파일쓰기 ///////////////// 
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
				
				///////////////// 이미지 /////////////////
				// 정보 출력
				sFileInfo += "&bNewLine=true";
				// img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
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
