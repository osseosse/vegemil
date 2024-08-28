package com.vegemil.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

@Controller
public class TempController {
	
	@GetMapping("/cafe24/authcode/veg")
	public @ResponseBody JsonObject sendPwResetMail (@RequestParam("code") String code, @RequestParam("state") String state) {

		JsonObject jsonObj = new JsonObject();


		jsonObj.addProperty("code", code);
		jsonObj.addProperty("state", state);

		return jsonObj;
	}
}
