
-- 1. 4분기 데이터 추가 (미오픈상태로)
select * from m_webzine order by index_no desc limit 20;

INSERT INTO m_webzine
      (qrt_year, file_no, remark, title, sns_title, category, insert_date, sum_line, is_open)
SELECT
      'Q4_2026'                AS qrt_year,     -- 새 분기
      file_no,
      remark,
      title,
      sns_title,
      category,
      NOW()                   AS insert_date,  -- MSSQL은 GETDATE()
      sum_line,
      '0'                     AS is_open       -- 처음엔 비공개로
FROM  m_webzine
WHERE qrt_year = 'Q3_2026';                    -- 이전 분기



-- -------------------------------------------------------------
-- 2. 콘텐츠 반영 (UPDATE · 실제 실행됨)
--    qrt_year + file_no 조합으로 매칭해 제목/요약 등을 갱신
-- -------------------------------------------------------------
UPDATE m_webzine a
JOIN webzineQ42026 b
  ON a.qrt_year = b.qrt_year
 AND a.file_no  = b.file_no
SET a.remark    = b.remark,
    a.title     = b.sns_title,
    a.sns_title = b.sns_title,
    a.sum_line  = b.sum_line;
-- 주의: title / sns_title 모두 b.sns_title 로 세팅함 (원본에 별도 title 컬럼 없음)


ALTER TABLE m_webzine CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;