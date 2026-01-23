package com.vegemil.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vegemil.domain.BizProposalDTO;
import com.vegemil.domain.DataTableDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.service.AdminCustomerService;
import com.vegemil.util.UiUtils;

@Controller
public class AdminSCMController extends UiUtils {
	
	@Autowired
	private AdminCustomerService adminCustomerService;
	
	@RequestMapping(value = "/admin/manage/scm/{viewName}")
    public String adminMoveCustomer(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		return "admin/scm/"+viewName;
    }
	
	/**
	 * 사업 제안 글 리스트 조회 
	 * @param searchDTO
	 * @param model
	 * @param commandMap
	 * @return
	 */
	@RequestMapping(value = "/admin/manage/scm/bizProposals")
	public @ResponseBody DataTableDTO getBizProposalList(@ModelAttribute("params") SearchDTO searchDTO, Model model,  @RequestParam Map<String, Object> commandMap) {
		DataTableDTO bizProposals = adminCustomerService.getBizProposalList(commandMap);
		return bizProposals;
	 }
	
	/**
	 * 사업 제안 글 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/manage/scm/bizProposeContent/{id}")
	public @ResponseBody BizProposalDTO getBizProposalContent(@PathVariable Long id) {
		BizProposalDTO bizProposal = adminCustomerService.getBizProposalData(id);
		return bizProposal;
	 }
	
	@RequestMapping(value = "/admin/manage/scm/bizProposeCheck/{id}/{status}")
	public @ResponseBody int changeBizProposalCheckStatus(@PathVariable Long id, @PathVariable int status) {
		
		System.out.println(id +"/" + status);
		return adminCustomerService.changeIsCheckStatus(id, status);		
	 }
}
