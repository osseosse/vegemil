package com.vegemil.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vegemil.domain.AdminDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.ProductDTO;
import com.vegemil.domain.UrlDTO;
import com.vegemil.domain.UserAgentDTO;

@Mapper
public interface AdminMapper {

	public int insertAdmin(MemberDTO memberDTO);

	public MemberDTO findAdminById(String aId);

	public int updateAdminPwd(MemberDTO memberDTO);

	public List<AdminDTO> selectAdminList(MemberDTO memberDTO);

	public int selectAdminTotalCount(MemberDTO memberDTO);
	
	public int checkMember(MemberDTO memberDTO);
	
	public int selectAdminCount(MemberDTO memberDTO);
	
	public int activeAdmin(MemberDTO memberDTO);
	
	public int updateAgentCount(Map<String, Object> params);
	
	public int selectUrlCount(@Param("url") String url, @Param("table") String table);
	
	public int insertUrl(@Param("url") String url, @Param("title") String title, @Param("table") String table);
	
	public int updateUrl(@Param("url") String url, @Param("title") String title, @Param("table") String table);
	
	public MemberDTO selectMemCount();
	
	public List<MemberDTO> selectSexRate();
	
	public List<MemberDTO> selectMonthlyJoinCount();
	
	public List<UserAgentDTO> selectMonthlyMobileAgent();
	
	public UserAgentDTO selectUserAgent(String yymm);
	
	public List<UrlDTO> selectUrlList();
	
	public List<ProductDTO> selectProductList();

}
