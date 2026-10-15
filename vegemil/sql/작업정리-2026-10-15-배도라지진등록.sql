-- ============================================================
-- 신제품 배도라지 진 등록
-- 작업일: 2026-10-15
-- ============================================================

-- ============================================================
-- 0. m_product 테이블 조회 (참고용)
-- ============================================================
SELECT p_idx, category_code, category_name, m_category_code, sub_category_code, sub_category_name, sub_category_ico,
       p_code, thumbnail1, thumbnail2, thumbnail3, p_name, count, display_priority,
       new_product, best_product, new_product_all, rep_product_all, new_product_cate, rep_product_cate,
       p_active, p_onactive, p_onuri,
       capacity1, capacity2, capacity3, kcal1, kcal2, kcal3,
       release_ym, description, expiration_month, recommender, tip,
       tag1, tag2, tag3, insert_date,
       brand_display, bottle_img, packaging16_img, packaging24_img, packaging15_img,
       swiper_class, eday_link_bottle, eday_link_16, eday_link_24, eday_link_15,
       brand_dis_priority, brand_pre_section, brand_post_section, brand_entire, thumb_text_field
  FROM m_product
-- WHERE sub_category_code = 'CDT'
  WHERE m_category_code = 'CDT'
 ORDER BY p_idx;

select * from m_product order by p_idx desc;



-- ============================================================
-- 1. m_product 테이블 INSERT
-- ============================================================


-- 1-2. 배도라지 진 (CDR07)
INSERT INTO m_product (
    category_code, category_name, m_category_code, sub_category_code, sub_category_name,
    sub_category_ico, p_code, thumbnail1, thumbnail2, thumbnail3,
    p_name, count, display_priority, new_product, best_product,
    new_product_all, rep_product_all, new_product_cate, rep_product_cate,
    p_active, p_onactive, p_onuri,
    capacity1, capacity2, capacity3, kcal1, kcal2, kcal3,
    release_ym, description, expiration_month, recommender, tip,
    tag1, tag2, tag3, insert_date,
    brand_display, bottle_img, packaging16_img, packaging24_img, packaging15_img,
    swiper_class, eday_link_bottle, eday_link_16, eday_link_24, eday_link_15,
    brand_dis_priority, brand_pre_section, brand_post_section, brand_entire, thumb_text_field
) VALUES (
    'S', '음료/생수', 'CDT', 'CDR', '차&전통음료',
    '', 'CDR07', 'CDR07_600_01.png', 'CDR07_860_01.png', 'CDR07_860_02.png',
    '배도라지 진', 0, NULL, 0, 0,
    1, 0, 1, 0,
    1, 0, '',
    '', '', '', '68kcal', '', '',
    '2026.01', '쌍화 함량을 33% 증가시켜 더욱 건강하게 즐길 수 있는 제품', '12month', '커피, 탄산 대신 건강을 생각한 음료를 드시고 싶으신 분', '따뜻하게도, 차갑게도 음용 가능합니다.',
    '#국산 도라지, 배, 생강', '#천연 아카시아벌꿀', '#환절기 한방음료', NOW(),
    0, '', '', '', '',
    '', '', '', '', '',
    0, 0, 0, 0, 'tip'
);

-- 에브릿 삭제
-- UPDATE m_product
-- SET p_active = 0,
--     p_onactive = 0
-- WHERE p_idx = 80;
--
-- -- 배도라지 진 비공개
-- UPDATE m_product
-- SET p_active = 0,
--     p_onactive = 0
-- WHERE p_idx = 132;
--
-- -- 배도라지 진 공개
--UPDATE m_product
--SET p_active = 1,
--    p_onactive = 1
--WHERE p_idx = 132;


select p_idx from m_product order by 1 desc;
-- ============================================================
-- 4. 등록 확인용 조회
-- ============================================================
SELECT p_idx,   category_code, category_name, m_category_code, sub_category_code, sub_category_name, p_code, p_name, category_code, sub_category_code, p_active, p_onactive, insert_date
  FROM m_product
 WHERE p_code IN ('CDR07')
 ORDER BY p_idx;

