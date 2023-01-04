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

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vegemil.constant.Method;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.PaymentDTO;
import com.vegemil.service.AdminPaymentService;
import com.vegemil.util.UiUtils;

@Controller
public class AdminPaymentController extends UiUtils {
	
	@Autowired
	private AdminPaymentService adminPaymentService;
	
	@RequestMapping(value = "/admin/manage/payment/list")
    public String openPaymentList(@ModelAttribute("params") final PaymentDTO params, Model model)throws Exception{
		return "admin/payment/list";
    }
	
	@RequestMapping(value = "/admin/manage/payment/paymentList")
	public @ResponseBody DataTableDTO getPaymentList(@ModelAttribute("params") PaymentDTO params, Model model,
			@RequestParam Map<String, Object> commandMap) {
		
		DataTableDTO dataTableDto = adminPaymentService.getPaymentList(commandMap);
		return dataTableDto;
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
	@RequestMapping(value = "/admin/payment/cancel/{paymentKey}")
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
		        		return showMessageWithRedirect("취소 실패했습니다. 다시시도해주세요", "/admin/manage/payment/list", Method.GET, null, model);
		
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
		            		pay.setLgdBuyer("관리자(결제취소)");
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
			                boolean isRegistered = adminPaymentService.registerPayment(pay);
			                if(!isRegistered)
		            		return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/manage/payment/list", Method.GET, null, model);
		        		}
		        	}
				}
			} else {
				out.println("<script>alert('로그인 후 이용바랍니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("로그인 후 이용바랍니다.", "/admin/auth/login", Method.GET, null, model);
			}
		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/manage/payment/list", Method.GET, null, model);
		}

		return showMessageWithRedirect("결제 취소되었습니다.", "/admin/manage/payment/list", Method.GET, null, model);
	}
}
