package com.vegemil.mapper;

import com.vegemil.domain.MemberNoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.*;

@Mapper
public interface MemberNoticeMapper {

    // 범용적인 메서드
    int save(MemberNoticeDTO memberNoticeDTO);

    int update(MemberNoticeDTO memberNoticeDTO);

    int delete(Map<String, Object> params);

    // 특정 도메인에 특화된 메서드
    MemberNoticeDTO findByMid(String mId);

    MemberNoticeDTO findByMidAndNoticeType(MemberNoticeDTO memberNoticeDTO);

}
