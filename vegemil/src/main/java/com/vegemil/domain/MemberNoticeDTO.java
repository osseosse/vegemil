package com.vegemil.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberNoticeDTO {
    private Long idx;
    private String mId;
    private NoticeType noticeType;
    private LocalDateTime lastSentAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String updatedBy;

    public MemberNoticeDTO(String mId, NoticeType noticeType) {
        this.mId = mId;
        this.noticeType = noticeType;
    }

    // Enum defined within the DTO
    public enum NoticeType {
        BIENNIAL_MARKETING_CONSENT_NOTICE, //2년주기 정식품 광고성 정보 수신동의 안내 메일
        IMMEDIATE_MARKETING_CONSENT_CHANGE, //즉시 광고성 수신 동의 여부에 대한 변경 처리
        ANNUAL_PRIVACY_NOTICE //연1회 개인정보 이용제공내역 수집 출처 안내
    }
}
