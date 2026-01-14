package com.vegemil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vegemil.mapper.BizProposalMapper;

@Service
public class FileServiceImpl implements FileService{
	
	@Autowired
	BizProposalMapper bizProposalMapper;

	@Override
	public String getOriginFileName(String dir, String pathName) {

		String result = pathName;
		pathName = "/web/upload/biz/"+pathName;
		
		try {				
			result = bizProposalMapper.selectOriginFileName(pathName);				
		} catch (Exception e) {
			System.out.println(e.toString());
		}
			
		return result;
			
	}

}
