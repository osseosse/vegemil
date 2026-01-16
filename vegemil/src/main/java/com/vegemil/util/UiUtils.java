package com.vegemil.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegemil.constant.Method;
import com.vegemil.paging.Criteria;

@Controller
public class UiUtils {

	public String showMessageWithRedirect(@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "redirectUri", required = false) String redirectUri,
			@RequestParam(value = "method", required = false) Method method,
			@RequestParam(value = "params", required = false) Map<String, Object> params, Model model) {

		model.addAttribute("message", message);
		model.addAttribute("redirectUri", redirectUri);
		model.addAttribute("method", method);
		model.addAttribute("params", params);

		return "utils/message-redirect";
	}

	public Map<String, Object> getPagingParams(Criteria criteria) {

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("currentPageNo", criteria.getCurrentPageNo());
		params.put("recordsPerPage", criteria.getRecordsPerPage());
		params.put("pageSize", criteria.getPageSize());
		params.put("searchType", criteria.getSearchType());
		params.put("searchKeyword", criteria.getSearchKeyword());

		return params;
	}

	// 클라이언트 ip주소 가져오기
	public String getClientIp(HttpServletRequest req) {
		String ip = "";
		try {
			byte[] bytes = Inet4Address.getLocalHost().getAddress();
			ip = new String(bytes);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	public String getClientIpVer2(HttpServletRequest request) {

		String ip = null;

		ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-RealIP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	public String getDeviceType(HttpServletRequest req) {
		String userAgent = req.getHeader("User-Agent");
		if (userAgent == null) {
			userAgent = "";
		}
		return userAgent;
	}

	public static String generateCaptcha() {
	    String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
	    StringBuilder sb = new StringBuilder(5);
	    Random random = new Random();

	    for (int i = 0; i < 5; i++) {
	        sb.append(chars.charAt(random.nextInt(chars.length())));
	    }
	    return sb.toString();
	}
	
	protected BufferedImage generateCaptchaImage(String text) {
		final int W = 160;
	    final int H = 50;

	    BufferedImage i = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = i.createGraphics();

	    Random r = new Random(System.nanoTime());

	    // 배경
	    g.setColor(new Color(
	            200 + r.nextInt(30),
	            200 + r.nextInt(30),
	            200 + r.nextInt(30)
	    ));
	    g.fillRect(0, 0, W, H);

	    // 왜곡
	    g.shear(
	        (r.nextDouble() - 0.5) * 0.8,
	        (r.nextDouble() - 0.5) * 0.3
	    );

	    g.setFont(new Font("Arial", Font.ITALIC, 32));

	    FontMetrics m = g.getFontMetrics();
	    int bx = (W - m.stringWidth(text)) / 2;
	    int by = (H - m.getHeight()) / 2 + m.getAscent();

	    char[] c = text.toCharArray();

	    for (int k = 0; k < c.length; k++) {

	        double θ = (r.nextDouble() - 0.5) * 0.6;
	        g.rotate(θ, bx + k * 24, by);

	        g.setColor(new Color(
	                r.nextInt(120),
	                r.nextInt(120),
	                r.nextInt(120)
	        ));

	        g.drawString(
	            String.valueOf(c[k]),
	            bx + k * 24 + r.nextInt(5) - 2,
	            by + r.nextInt(5) - 2
	        );

	        g.rotate(-θ, bx + k * 24, by);
	    }

	    // 선 노이즈
	    for (int n = 0; n < 8; n++) {
	        g.setColor(new Color(
	                r.nextInt(255),
	                r.nextInt(255),
	                r.nextInt(255)
	        ));
	        g.drawLine(
	                r.nextInt(W), r.nextInt(H),
	                r.nextInt(W), r.nextInt(H)
	        );
	    }

	    // 점 노이즈
	    for (int p = 0; p < 80; p++) {
	        i.setRGB(
	                r.nextInt(W),
	                r.nextInt(H),
	                r.nextInt(0xFFFFFF)
	        );
	    }

	    g.dispose();
	    return i;
	}

	

}
