package com.vegemil.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vegemil.domain.AdminAdEctDTO;
import com.vegemil.domain.AdminAviCFDTO;
import com.vegemil.domain.AdminVideoContestDTO;

@Mapper
public interface AdminAviCFMapper {
	
	public List<AdminAviCFDTO> selectAdminAviCFList(AdminAviCFDTO params);
	public int insertAdminAviCF(AdminAviCFDTO params);
	public int deleteAdminAviCFData(String tIdx);
	public AdminAviCFDTO selectAdminAviCFData(String tIdx);
	public int updatetOnairStatus(AdminAviCFDTO params);
	public int updateAdminAviCFData(AdminAviCFDTO params);
}
