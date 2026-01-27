package com.vegemil.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vegemil.domain.BizProposalDTO;
import com.vegemil.domain.ClaimDTO;
import com.vegemil.domain.EventDTO;
import com.vegemil.domain.MailDTO;
import com.vegemil.domain.SearchDTO;
import com.vegemil.mapper.BizProposalMapper;
import com.vegemil.mapper.CommunicationMapper;

@Service
public class CommunicationServiceImpl implements CommunicationService{
	@Autowired
	CommunicationMapper communicationMapper;
	@Autowired
	BizProposalMapper bizProposalMapper;
	
	@Autowired
	MailService mailService;
	
	@Override
	public BizProposalDTO getBizProposal(Long id) {
		// BizProposalDTO bizProposalDTO
		return bizProposalMapper.selectProposalById(id);
	}

	@Override
	public List<BizProposalDTO> getBizProposals(SearchDTO searchDTO) {
		// TODO Auto-generated method stub
		return bizProposalMapper.selectProposalsWithPaging(null);
	}

	@Override
	public List<BizProposalDTO> getBizProposals(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return bizProposalMapper.selectProposalsWithPaging(map);
	}
	@Override
	public Long enrollBizProposal(BizProposalDTO bizProposalDTO) {
		// TODO Auto-generated method stub		
		Long result = bizProposalMapper.insertBizProposal(bizProposalDTO);
		
		if(result>0) {
			MailDTO mail = new MailDTO();

			mail.setAddress("hypark023@osse.co.kr");
			mail.setTitle("[vegemilcokr]사업제휴문의글 등록");
			mail.setMessage("\n\n사업제휴문의글이 등록되었습니다. 관리자 페이지에서 확인 바랍니다.\n\n");
			
			mailService.mailSendOriginReturnBoolean(mail);
		}
		
		return result;
	}

	@Override
	public BizProposalDTO changeCheckStatus(Long id, int check) {
		// TODO Auto-generated method stub
		// 이거 성공하면 1 뱉나? 확인해서 체크 코드 넣을 것 
		bizProposalMapper.updateIsCheck(id, check);
		return bizProposalMapper.selectProposalById(id);
	}

	@Override
	public BizProposalDTO changeCheckStatus(BizProposalDTO bizProposalDTO) {
		// TODO Auto-generated method stub
		
		if(!StringUtils.isEmpty(bizProposalDTO.getId())) {
			bizProposalMapper.updateIsCheck(bizProposalDTO.getId(), bizProposalDTO.getIsCheck());	
		}
		
		return bizProposalMapper.selectProposalById(bizProposalDTO.getId());
	}
	
	
	
	

	@Override
	public int checkCompId(String cId) {
		return communicationMapper.selectOneMemberCP(cId);
	}

	@Override
	public int insertMclaim(ClaimDTO claimDTO) {
		
		claimDTO.setCTel(claimDTO.getTel01().trim() + "-" + claimDTO.getTel02().trim() + "-" + claimDTO.getTel03().trim());
		claimDTO.setCHp(claimDTO.getHp01().trim() + "-" + claimDTO.getHp02().trim() + "-" + claimDTO.getHp03().trim());
		claimDTO.setCEmail(claimDTO.getEmail01().trim() + claimDTO.getEmail02());
		claimDTO.setCAnswer("N");
		return communicationMapper.insertMclaim(claimDTO);
	}
	
	@Override
	public List<EventDTO> getEnevetList() {
		
		List<EventDTO> eventList = Collections.emptyList();
		eventList = communicationMapper.selectEventList();
		
        return eventList;
		
	}
	
	@Override
	public EventDTO getEvent(String eIdx) {
		
		EventDTO event = communicationMapper.selectEvent(eIdx);
		
		return event;
	}

	@Override
	public String getRecentClaimIp() {
		return communicationMapper.selectClaimIPRecent();
	}



	
}
