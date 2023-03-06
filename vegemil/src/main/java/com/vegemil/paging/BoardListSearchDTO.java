package com.vegemil.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardListSearchDTO {

    private int pageNum;                // 이용자 위치 Page 번호
    private int pageSize;               // 한 Page 당 출력될 게시글 개수
    private String searchType;          // 검색 시 입력될 Type
    private String searchContent;       // 검색 시 입력될 검색어

} // class 끝