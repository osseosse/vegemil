package com.vegemil.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.util.StringUtil;
import com.vegemil.constant.Method;
import com.vegemil.domain.BizProposalDTO;
import com.vegemil.domain.ClaimDTO;
import com.vegemil.domain.EventDTO;
import com.vegemil.domainEday.EdayVempDTO;
import com.vegemil.service.CommunicationService;
import com.vegemil.service.EdayVempService;
import com.vegemil.service.MailService;
import com.vegemil.util.RedisUtil;
import com.vegemil.util.UiUtils;

import groovy.util.logging.Log4j;

@Controller
@Log4j
public class CommunicationConroller extends UiUtils {

	@Autowired
	private CommunicationService communicationService;

	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	RedisUtil redisUtil;

	@Autowired
	MailService mailService;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;

	@Autowired
	private EdayVempService edayVempService;

	@RequestMapping(value = "/communication/{viewName}")
	public String moveCommunication(@PathVariable(value = "viewName", required = false) String viewName)
			throws Exception {

		return "communication/" + viewName;
	}

	@GetMapping("/communication/cp")
	public String getCpPage() {
		return "communication/cp/cp";
	}

	@GetMapping("/communication/cp/cpHandbook")
	public String getCpManual() {
		return "communication/cp/cpHandbook";
	}

	@GetMapping("/communication/cp/cpProgram")
	public String getCpProgram() {
		return "communication/cp/cpProgram";
	}

	@GetMapping("/communication/cp/cpProgramStatus")
	public String getCpProgramStatus() {
		return "communication/cp/cpProgramStatus";
	}

	@GetMapping("/communication/cp/cpEbookView")
	public String getCpEbookView(Model model, String fileName) {
		model.addAttribute("fileName", fileName);
		return "communication/cp/cpEbookView";
	}

	@RequestMapping(value = "/event/list")
	public String moveEventList(@PathVariable(value = "viewName", required = false) String viewName, Model model)
			throws Exception {

		List<EventDTO> eventList = communicationService.getEnevetList();
		if (eventList != null) {
			model.addAttribute("eventList", eventList);
		}

		return "communication/event/list";
	}

	@RequestMapping(value = "/event/detail/{eIdx}")
	public String moveEventDetail(@PathVariable(value = "eIdx", required = false) String eIdx, Model model)
			throws Exception {

		EventDTO event = new EventDTO();

		event = communicationService.getEvent(eIdx);
		if (event != null) {
			model.addAttribute("event", event);
		}

		return "communication/event/detail";
	}

	@PostMapping("/cp/cIdCheck")
	public String cIdCheck(String cId, String fileName, Model model) {
		model.addAttribute("fileName", fileName);
		if (communicationService.checkCompId(cId) <= 0) {
			model.addAttribute("msg", "사번 조회에 실패했습니다.");
			model.addAttribute("validation", "0");
			return "communication/cp/cpEbookView";
		}

		model.addAttribute("validation", "1");
		model.addAttribute("msg", "");

		return "communication/cp/cpEbookView";
	}

	// 자율준수 편람 파일 다운 코드
	@GetMapping("/cpEbookDown")
	public ResponseEntity<Resource> cpEbookDown(@RequestParam("fileName") String fileName,
			@RequestHeader(name = "user-agent") String userAgent) throws IOException {

		try {

			fileName = fileName + ".pdf";

			Resource resource = resourceLoader.getResource("classpath:static/cp/papers/" + fileName);
			String downName = null;

			// 인터넷 익스플로러 인 경우
			boolean isMSIE = userAgent.indexOf("MSIE") != -1 || userAgent.indexOf("Trident") != -1;

			if (isMSIE) { // 익스플로러 대응
				downName = URLEncoder.encode(fileName, "UTF-8").replaceAll("/+", "%20");
			} else {
				downName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1"); // 크롬
			}

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + downName + "\"")
					.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString()).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 실패 시
	}

	@GetMapping("/communication/cp/cpDeclaration")
	public String cpDeclaration() {
		return "communication/cp/cpDeclaration";
	}

	@PostMapping("/communication/cp/cpDeclaration")
	public String postCpClaim(Model model, ClaimDTO claimDTO, HttpServletRequest req) {

		long timeGap = (new Date().getTime() - claimDTO.getSubmitTime().getTime());

		if (claimDTO.getSubmitTime() == null) {
			return showMessageWithRedirect("잘못된 접근입니다.", "/communication/cp/cpDeclaration", Method.GET, null, model);
		}

		String ip = getClientIpVer2(req);
		if (ip.equals(communicationService.getRecentClaimIp()) || timeGap < 6000) {
			return showMessageWithRedirect("앞에서 같은 아이피 주소로 접수한 기록이 있습니다.", "/communication/cp/cpDeclaration",
					Method.GET, null, model);
		}

		claimDTO.setCIp(ip);
		int result = communicationService.insertMclaim(claimDTO);

		List<String> recipientsId = new ArrayList<>();
		// recipientsId.add("hypark023@osse.co.kr"); 테스트용

		if (result > 0) {
			if (recipientsId.size() > 0) {
				for (String recipientId : recipientsId) {
					EdayVempDTO receiverEmp = edayVempService.getVempInfo(recipientId);

					if (receiverEmp.getExpireYn().equals("1")) {
						// 담당자 확인 요청 메일
						mailService.requestCheckPersonInCharge();
						recipientsId.remove(recipientId);
						break;
					}
				}
			}
			// 관리자에게 알림 메일 발송
			mailService.alertSubmitCpDecl(claimDTO, recipientsId);
			// 신고인에게 확인 메일 발송
			mailService.confirmSubmitCpDecl(claimDTO);
			return showMessageWithRedirect("신고가 정상적으로 접수되었습니다.", "/communication/cp", Method.GET, null, model);
		}

		return showMessageWithRedirect("신고 접수에 실패했습니다.", "/communication/cp/cpDeclaration", Method.GET, null, model);
	}

	// 업체 문의 기능 추가
	
	/**
	 * 사업 제안 포스트 화면 조회
	 * - 처리 내용:
	 *   1. 자동입력방지(CAPTCHA) 생성
	 *   2. 화면 렌더링을 위한 데이터 모델 세팅
	 *
	 * @param model 화면에 전달할 데이터 모델
	 * @return 사업 제안서 화면 뷰 이름
	 */
	@GetMapping("/communication/biz_0105_tmp")
	public String getBisProposalView(Model model) {
		// 자동입력방지검증 
		String capcha = generateCaptcha();

		redisUtil.setHourExpire(capcha, capcha, 1);
		BizProposalDTO bizform = new BizProposalDTO(capcha);
		model.addAttribute("bizform", bizform);
		model.addAttribute("capchaValue", capcha);


		return "communication/biz";
	}
	
	/**
	 * 사업제안서 등록  
	 * - 처리 내용 :
	 * 	1. 자동압룍벙자뮨저 일치 여부 검증
	 *  2. form validation 검증
	 *  3. redis 데이타 삭제
	 *  4. DB insert
	 * @param bizform
	 * @param bindingResult
	 * @param model
	 * @param req
	 * @return
	 */
	@PostMapping("/post/bizProposal")
	public String postBizProposal(@Valid BizProposalDTO bizform, BindingResult bindingResult, Model model,
			HttpServletRequest req) {

		System.out.println("bizProposalDT = " + bizform);

		String capchaKey = bizform.getCapchaKey();
		String capchInput = bizform.getCaptchaInput();
		String capchaValue = redisUtil.getData(capchaKey).toString();
		
		if(!StringUtil.isEmpty(capchaKey)) {			
			if(!capchInput.equals(capchaValue)) {					
				bindingResult.addError(new FieldError("bizform", "capchaKey", "방지문자입력이 틀렸습니다."));
			}			
		}

		// 바인딩 에러 체크
		if (bindingResult.hasErrors()) {		
			bindingResult.getAllErrors().forEach(error -> {
				System.out.println(error);
			});
			model.addAttribute("errors", bindingResult.getFieldErrors());
			model.addAttribute("bizform", bizform);
			model.addAttribute("capchaValue", capchaValue);
			model.addAttribute("captchaInput", capchInput);
			
			return "communication/biz";
		}
		
		// redis 데이터 삭제 
		redisUtil.deleteData(capchaKey);

		communicationService.enrollBizProposal(
				bizform.setDeviceAndIpAddr(getDeviceType(req), getClientIpVer2(req)).combineFilels().setFilePaths());

		return "redirect:/communication/ask";
	}
	
	/**
	 * capcha 입력 값 일치 여부 실시간 검증
	 * @param capchaKey
	 * @param capchaValue
	 * @return
	 */
	@GetMapping("/captcha/check")
	@ResponseBody
	public boolean checkCaptcha(@RequestParam String capchaKey, @RequestParam String capchaValue) {
		
	    String savedValue = redisUtil.getData(capchaKey);	    	   	    	    
	    return savedValue.equals(capchaValue)? true:false;
	}
	
	@GetMapping("/captcha/image")
	public void captcha(@RequestParam("captchaKey") String captchaKey,HttpServletResponse response) throws IOException {
		
		if(StringUtil.isEmpty(redisUtil.getData(captchaKey))) {			
	        throw new IllegalArgumentException("capchaKey가 없습니다.");
	    }
		
	    String captchaFresh = generateCaptcha(); 
	    
	    redisUtil.setHourExpire(captchaKey, captchaFresh, 1);
	    
	    System.out.println("capcha key & refresh = "+ captchaKey + " & "+ captchaFresh);
	
	    BufferedImage image = generateCaptchaImage(captchaFresh);

	    response.setContentType("image/png");
	    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

	    ImageIO.write(image, "png", response.getOutputStream());
	}
	



}
