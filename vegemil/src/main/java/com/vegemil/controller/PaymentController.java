package com.vegemil.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.PaymentDTO;
import com.vegemil.service.MemberService;
import com.vegemil.service.PaymentService;
import com.vegemil.util.UiUtils;

@Controller
public class PaymentController extends UiUtils {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping(value = "/payment/login")
	public String movePayLogin(Model model, Authentication authentication, HttpServletResponse response) {
		
		response.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			if(authentication != null) {
				out.println("<script>alert('이미 로그인 상태입니다.'); history.go(-1);</script>");
				out.flush();
		    	return showMessageWithRedirect("이미 로그인 상태입니다.", "/home", Method.GET, null, model);
			}
		}catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/home", Method.GET, null, model);
		}
		return "member/payLogin";
	}
	
	@GetMapping(value = "/payment/join")
	public String movePayJoin(Model model) {
		
		MemberDTO member = new MemberDTO();
		
		model.addAttribute("member", member);
		
		return "payment/join";
	}
	
	@GetMapping(value = "/comp/payment/list")
	public String openPaymentList(Model model, HttpServletResponse response, Authentication authentication) {
	    
		response.setContentType("text/html; charset=UTF-8");
		MemberDTO member = new MemberDTO();
		PaymentDTO pay = new PaymentDTO();
		try {
			
			PrintWriter out = response.getWriter();
			//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
			if(authentication != null) {
				member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
				if (member == null) {
					out.println("<script>alert('로그인 후 이용바랍니다.'); history.go(-1);</script>");
					out.flush();
					return showMessageWithRedirect("로그인 후 이용바랍니다.", "member/payLogin", Method.GET, null, model);
				}
				
				pay.setLgdBuyerid(member.getMId());
				List<PaymentDTO> payList = paymentService.getPaymentList(pay);
				model.addAttribute("payList", payList);
				model.addAttribute("member", member);

				
			} else {
				out.println("<script>alert('로그인 후 이용바랍니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("로그인 후 이용바랍니다.", "member/payLogin", Method.GET, null, model);
			}
			
		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/home", Method.GET, null, model);
		}
		
		return "payment/list";
	}
	
	@GetMapping(value = "/comp/payment/pay")
	public String movePay(Model model, HttpServletResponse response, Authentication authentication) {
		
		response.setContentType("text/html; charset=UTF-8");
		try {
			MemberDTO member = new MemberDTO();
			PrintWriter out = response.getWriter();
			//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
			if(authentication != null) {
				member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
				if (member == null) {
					out.println("<script>alert('로그인 후 이용바랍니다.'); history.go(-1);</script>");
					out.flush();
					return showMessageWithRedirect("로그인 후 이용바랍니다.", "member/payLogin", Method.GET, null, model);
				}
			}
			model.addAttribute("member", member);
			
		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/home", Method.GET, null, model);
		}
		
		return "payment/pay";
	}
	
	@GetMapping(value = "/payment/joinOk")
	public String movePayJoinOk(Model model) {
		
		MemberDTO member = new MemberDTO();
		
		model.addAttribute("member", member);
		
		return "payment/joinOk";
	}
	
	@PostMapping(value = "/payment/register")
	public String registerPayMember(
			@ModelAttribute("member") final @Valid MemberDTO member,
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			boolean isRegistered = memberService.registerMember(member);
			if (isRegistered == false) {
				out.println("<script>alert('이미 가입된 회원입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/home", Method.GET, null, model);
			}
			model.addAttribute("member", member);
			
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/home", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/home", Method.GET, null, model);
		}

		return "payment/joinOk";
	}
	
	/**
	 * 토스 결제승인 요청 2022.11 김명환
	 * @param request
	 * @param response
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/payment/success")
	public String  tossPaySuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication,
			Model model,
			@RequestParam(value="paymentKey", defaultValue="") String paymentKey,
			@RequestParam(value="orderId", defaultValue="") String orderId,
			@RequestParam(value="amount", defaultValue="") String amount) throws Exception{

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		//request 데이터
        //String secretKey	= "test_sk_OAQ92ymxN34d7vzQwZP3ajRKXvdk:";//테스트
        String secretKey	= "live_sk_jkYG57Eba3GjLkbKXQ58pWDOxmA1:";//라이브
        /* base64 encoding */
		byte[] encodedBytes = Base64.encodeBase64(secretKey.getBytes());
        String base64Credentials = new String(encodedBytes);
		int responseCode = 0;

		try {

			if(authentication != null) {
				MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
				if (member != null) {
					
					URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
		
					StringBuffer paramData = new StringBuffer();
					// json data 담기
					JSONObject jsonObject = new JSONObject();
		            jsonObject.put("paymentKey", paymentKey);
		            jsonObject.put("amount", amount);
		            jsonObject.put("orderId", orderId);
		
			        //연결
			        HttpURLConnection conn = (HttpURLConnection)url.openConnection();//URL 연결
			        conn.setDoOutput(true);                                          //Server 통신에서 출력 가능한 상태로 만듦
			        conn.setRequestMethod("POST");                                   //요청 방식
			        conn.setRequestProperty("Authorization", "Basic " + base64Credentials); //시크릿코드
			        conn.setRequestProperty("Content-Type", "application/json");     //타입 설정
			        Map<String, List<String>> map = conn.getRequestProperties();
		
			        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			            System.out.println("Key : " + entry.getKey() + " ,Value : " + entry.getValue());
			        }
		
		            //전송, 한글 깨짐 방지
		            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
		
		            osw.write(jsonObject.toJSONString());
		            osw.flush();
		
		            //응답
		            BufferedReader br = null;
		            responseCode = conn.getResponseCode();
		
		        	if(responseCode != 200){
		        		model.addAttribute("err", true);
		        		osw.close();
		        		return showMessageWithRedirect("결제 실패했습니다. 다시시도해주세요", "/payment/pay", Method.GET, null, model);
		
		        	} else {
		
		        		br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		
		                String line = null;
		
		                while((line = br.readLine()) != null) {
		                    System.out.println(line);
		                    paramData.append(line);
		                }
		
		                //response 값 받기
		                JSONObject result = (JSONObject)JSONValue.parse(paramData.toString());
		                JSONObject card = null;
		
		                osw.close();
		                br.close();
		
		        		// 액세스 토큰 저장후 리턴
		        		model.addAttribute("payStatus", "response");
		        		
		        		PaymentDTO pay = new PaymentDTO();
		        		pay.setLgdIp(getClientIp(request));
		        		
		        		if(result != null) {
		        			pay.setLgdMid(result.get("mId").toString());
		        			pay.setLgdOid(result.get("orderId").toString());
		        			pay.setLgdTid(result.get("paymentKey").toString());
		        			pay.setLgdPaytype(result.get("method").toString());
		        			pay.setLgdRespcode(result.get("status").toString());
		        			pay.setLgdRespmsg("결제성공");
		        			pay.setLgdPaydate(result.get("requestedAt").toString());
		        			pay.setLgdBuyer(member.getMCompName());
		        			pay.setLgdBuyerid(member.getMId());
		        			
		        			if(result.get("card") != null) {
		        				card = (JSONObject) result.get("card");
		            			pay.setLgdFinancename(card.get("company").toString());
		            			pay.setLgdCardnum(card.get("number").toString());
		            			pay.setLgdCardinstallmonth(card.get("installmentPlanMonths").toString());
		            			pay.setLgdFinanceauthnum(card.get("approveNo").toString());
		            			pay.setLgdReceiptUrl(card.get("receiptUrl").toString());
		            			pay.setLgdAmount(Double.parseDouble(card.get("amount").toString()));
		            		}
		        			
		        			//결제내역 저장
		        			boolean isRegistered = paymentService.registerPayment(pay);
			                if(!isRegistered) {
			                	//저장실패시 취소처리
			                	boolean resultCancelApi = tossPayCancelApi(request, response, paymentKey, "단순 변심");
			                	if(!resultCancelApi)
		                		return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/home", Method.GET, null, model);
			                }
		        			
		                }
		
		        	}
        	
				}
			} else {
				out.println("<script>alert('로그인 후 이용바랍니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("로그인 후 이용바랍니다.", "member/payLogin", Method.GET, null, model);
			}
        	
		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/home", Method.GET, null, model);
		}

		return showMessageWithRedirect("pay success", "/payment/list", Method.GET, null, model);
	}
	
	@SuppressWarnings("unchecked")
	public Boolean tossPayCancelApi(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="paymentKey", defaultValue="") String paymentKey,
			@RequestParam(value="cancelReason", defaultValue="고객 변심") String cancelReason) throws Exception{

		Boolean result = false;

		//request 데이터
		//String secretKey	= "test_sk_OAQ92ymxN34d7vzQwZP3ajRKXvdk:";//테스트
        String secretKey	= "live_sk_jkYG57Eba3GjLkbKXQ58pWDOxmA1:";//라이브
		/* base64 encoding */
		byte[] encodedBytes = Base64.encodeBase64(secretKey.getBytes());
        String base64Credentials = new String(encodedBytes);

		int responseCode = 0;

		try {

			URL url = new URL("https://api.tosspayments.com/v1/payments/"+ paymentKey +"/cancel");

			// json data 담기
			JSONObject jsonObject = new JSONObject();
            jsonObject.put("cancelReason", cancelReason);

	        //연결
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();//URL 연결
	        conn.setDoOutput(true);                                          //Server 통신에서 출력 가능한 상태로 만듦
	        conn.setRequestMethod("POST");                                   //요청 방식
	        conn.setRequestProperty("Authorization", "Basic " + base64Credentials); //시크릿코드
	        conn.setRequestProperty("Content-Type", "application/json");     //타입 설정
	        Map<String, List<String>> map = conn.getRequestProperties();

	        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
	            System.out.println("Key : " + entry.getKey() + " ,Value : " + entry.getValue());
	        }

            //전송, 한글 깨짐 방지
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

            osw.write(jsonObject.toJSONString());
            osw.flush();

            //응답
            responseCode = conn.getResponseCode();

        	if(responseCode != 200){
        		osw.close();
        		return result;

        	} else {
        		//취소성공
        		result = true;
        	}
		} catch (Exception e) {
		}

		return result;
	}
	
	/**
	 * 토스 커넥트페이 결제 취소 요청 2021.11 김명환
	 * @param request
	 * @param response
	 * @param commandMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/payment/cancel/{paymentKey}")
	public String  tossPayCancelSave(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication,
			Model model,
			@PathVariable("paymentKey") String paymentKey) throws Exception{

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		//request 데이터
		//String secretKey	= "test_sk_OAQ92ymxN34d7vzQwZP3ajRKXvdk:";//테스트
        String secretKey	= "live_sk_jkYG57Eba3GjLkbKXQ58pWDOxmA1:";//라이브
		/* base64 encoding */
		byte[] encodedBytes = Base64.encodeBase64(secretKey.getBytes());
        String base64Credentials = new String(encodedBytes);

		int responseCode = 0;

		try {
			
			if(authentication != null) {
				MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
				if (member != null) {

					URL url = new URL("https://api.tosspayments.com/v1/payments/"+ paymentKey +"/cancel");
		
					StringBuffer paramData = new StringBuffer();
					// json data 담기
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("cancelReason", "고객 환불");
		
			        //연결
			        HttpURLConnection conn = (HttpURLConnection)url.openConnection();//URL 연결
			        conn.setDoOutput(true);                                          //Server 통신에서 출력 가능한 상태로 만듦
			        conn.setRequestMethod("POST");                                   //요청 방식
			        conn.setRequestProperty("Authorization", "Basic " + base64Credentials); //시크릿코드
			        conn.setRequestProperty("Content-Type", "application/json");     //타입 설정
			        Map<String, List<String>> map = conn.getRequestProperties();
		
			        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			            System.out.println("Key : " + entry.getKey() + " ,Value : " + entry.getValue());
			        }
		
		            //전송, 한글 깨짐 방지
		            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
		
		            osw.write(jsonObject.toJSONString());
		            osw.flush();
		
		            //응답
		            BufferedReader br = null;
		            responseCode = conn.getResponseCode();
		
		        	if(responseCode != 200){
		        		model.addAttribute("err", true);
		        		osw.close();
		        		return showMessageWithRedirect("취소 실패했습니다. 다시시도해주세요", "/payment/pay", Method.GET, null, model);
		
		        	} else {
		
		        		br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		
		                String line = null;
		
		                while((line = br.readLine()) != null) {
		                    System.out.println(line);
		                    paramData.append(line);
		                }
		
		                //response 값 받기
		                JSONObject result = (JSONObject)JSONValue.parse(paramData.toString());
		                JSONObject card = null;
		                JSONArray cancels = null;
		                JSONObject cancel = null;
		
		                osw.close();
		                br.close();
		                
		                PaymentDTO pay = new PaymentDTO();
		
		        		// 액세스 토큰 저장후 리턴
		        		if(result != null) {
		            		pay.setLgdMid(result.get("mId").toString());
		            		pay.setLgdOid(result.get("orderId").toString());
		            		pay.setLgdTid(result.get("paymentKey").toString());
		            		pay.setLgdPaytype(result.get("method").toString());
		            		pay.setLgdRespcode(result.get("status").toString());
		            		pay.setLgdRespmsg("취소성공");
		            		pay.setLgdPaydate(result.get("requestedAt").toString());
		            		pay.setLgdBuyer(member.getMCompName());
		        			pay.setLgdBuyerid(member.getMId());
		            		
			        		if(result.get("card") != null) {
			                	card = (JSONObject) result.get("card");
			                	pay.setLgdFinancename(card.get("company").toString());
		            			pay.setLgdCardnum(card.get("number").toString());
		            			pay.setLgdCardinstallmonth(card.get("installmentPlanMonths").toString());
		            			pay.setLgdFinanceauthnum(card.get("approveNo").toString());
		            			pay.setLgdReceiptUrl(card.get("receiptUrl").toString());
		            			pay.setLgdAmount(Double.parseDouble(card.get("amount").toString()));
			                }
			                if(result.get("cancels") != null) {
			                	cancels = (JSONArray) result.get("cancels");
			                	if(cancels.get(0) != null) {
			                		cancel = (JSONObject) cancels.get(0);
				                	pay.setLgdCancelDate(cancel.get("canceledAt").toString());
			                	}
			                }
			                
			                //취소내역 저장
			                boolean isRegistered = paymentService.registerPayment(pay);
			                if(!isRegistered)
		            		return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/home", Method.GET, null, model);
		        		}
		        	}
				}
			} else {
				out.println("<script>alert('로그인 후 이용바랍니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("로그인 후 이용바랍니다.", "member/payLogin", Method.GET, null, model);
			}
		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/home", Method.GET, null, model);
		}

		return showMessageWithRedirect("cancel success", "/payment/list", Method.GET, null, model);
	}
	
}