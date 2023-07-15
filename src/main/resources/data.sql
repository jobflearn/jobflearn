INSERT INTO locations (latitude, longitude, province, city, street, country_code)
VALUES (37.5665, 126.9780, '서울특별시', '중구', '세종대로 110', 'KR'),
       (37.7749, -122.4194, '캘리포니아', '샌프란시스코', '마운틴뷰', 'US'),
       (51.5074, -0.1278, '잉글랜드', '런던', '카나리 워프', 'GB'),
       (35.6895, 139.6917, '도쿄도', '치요다구', '마루노우치', 'JP'),
       (34.0522, -118.2437, '캘리포니아', '로스앤젤레스', '할리우드', 'US'),
       (40.7128, -74.0060, '뉴욕', '뉴욕', '월 스트리트', 'US'),
       (48.8566, 2.3522, '프랑스', '파리', '샹젤리제', 'FR'),
       (41.9028, 12.4964, '이탈리아', '로마', '콜로세움', 'IT'),
       (55.7558, 37.6176, '러시아', '모스크바', '크렘린', 'RU'),
       (39.9042, 116.4074, '중국', '베이징', '왕징', 'CN'),
       (28.6139, 77.2090, '인도', '뉴델리', '코나우트 플레이스', 'IN'),
       (31.5546, 74.3572, '파키스탄', '라호르', '몰 로드', 'PK'),
       (40.4168, -3.7038, '스페인', '마드리드', '그란 비아', 'ES'),
       (52.5200, 13.4050, '독일', '베를린', '프란츠프서퍼 플라츠', 'DE'),
       (59.3293, 18.0686, '스웨덴', '스톡홀름', '가메라 스탄', 'SE'),
       (46.2044, 6.1432, '스위스', '제네바', '세인트 피에르 대성당', 'CH'),
       (-33.8688, 151.2093, '호주', '시드니', '시드니 하버 브릿지', 'AU'),
       (1.3521, 103.8198, '싱가포르', '싱가포르', '마리나 베이 샌즈', 'SG'),
       (-22.9068, -43.1729, '브라질', '리우데자네이로', '코파카바나', 'BR'),
       (30.0444, 31.2357, '이집트', '카이로', '피라미드', 'EG'),
       (43.6532, -79.3832, '캐나다', '토론토', '이튼 센터', 'CA'),
       (-1.286389, 36.817223, '케냐', '나이로비', '킴아티 스트리트', 'KE'),
       (-34.6037, -58.3816, '아르헨티나', '부에노스아이레스', '플로리다 스트리트', 'AR'),
       (24.8615, 67.0099, '파키스탄', '카라치', '자마 스트리트', 'PK'),
       (33.6844, 73.0479, '파키스탄', '이슬라마바드', '페사와르 모랄 로드', 'PK'),
       (25.2048, 55.2708, '아랍에미리트', '두바이', '부르즈 할리파', 'AE'),
       (-33.9249, 18.4241, '남아프리카공화국', '케이프타운', '쇼핑 센터', 'ZA'),
       (-6.2088, 106.8456, '인도네시아', '자카르타', '부나란 히라타우워', 'ID'),
       (9.0820, 8.6753, '나이지리아', '아부자', '국회 건물', 'NG'),
       (13.6934, -89.2182, '엘살바도르', '산살바도르', '리베르타드 애비뉴', 'SV');

INSERT INTO companies (name, is_verified, description, location_id)
VALUES ('OpenAI', true, 'OpenAI는 인공 지능 연구소입니다.', 1),
       ('Google', true, 'Google LLC는 미국의 다국적 기술 회사입니다.', 2),
       ('HSBC', false, 'HSBC Holdings plc는 영국의 다국적 투자 은행 및 금융 서비스 홀딩 회사입니다.', 3),
       ('Sony', true, 'Sony Group Corporation는 일본의 다국적 종합기업입니다.', 4),
       ('Microsoft', true, 'Microsoft Corporation은 미국의 다국적 기술 회사입니다.', 5),
       ('Apple', true, 'Apple Inc.는 미국의 다국적 기술 회사입니다.', 6),
       ('Louis Vuitton', true, 'Louis Vuitton은 프랑스의 다국적 패션 회사입니다.', 7),
       ('Ferrari', true, 'Ferrari는 이탈리아의 자동차 제조 회사입니다.', 8),
       ('Gazprom', true, 'Gazprom은 러시아의 천연가스 회사입니다.', 9),
       ('Alibaba', true, 'Alibaba Group은 중국의 다국적 합동 기업입니다.', 10),
       ('Tata Group', true, 'Tata Group은 인도의 다국적 합동 기업입니다.', 11),
       ('Unilever', true, 'Unilever는 영국과 네덜란드의 다국적 합동 기업입니다.', 12),
       ('Santander', true, 'Banco Santander는 스페인의 다국적 은행입니다.', 13),
       ('Siemens', true, 'Siemens AG는 독일의 다국적 합동 기업입니다.', 14),
       ('IKEA', true, 'IKEA는 스웨덴의 가구 및 가정용품 소매 회사입니다.', 15),
       ('Nestlé', true, 'Nestlé S.A.는 스위스의 다국적 식품 및 음료 회사입니다.', 16),
       ('BHP', true, 'BHP Group는 호주의 다국적 광업, 금속 회사입니다.', 17),
       ('DBS Bank', true, 'DBS Bank는 싱가포르의 다국적 은행입니다.', 18),
       ('Petrobras', true, 'Petrobras는 브라질의 다국적 석유 회사입니다.', 19),
       ('Orascom Construction', true, 'Orascom Construction PLC는 이집트의 건설 및 엔지니어링 회사입니다.', 20),
       ('Shopify', true, 'Shopify Inc.는 캐나다의 다국적 이커머스 회사입니다.', 21),
       ('Safaricom', true, 'Safaricom PLC는 케냐의 통신 회사입니다.', 22),
       ('MercadoLibre', true, 'MercadoLibre, Inc.는 아르헨티나의 이커머스 회사입니다.', 23),
       ('Pak Suzuki Motors', true, 'Pak Suzuki Motor Company는 파키스탄의 다국적 자동차 제조 회사입니다.', 24),
       ('Hashoo Group', true, 'Hashoo Group는 파키스탄의 다국적 기업 그룹입니다.', 25),
       ('Emirates Group', true, 'Emirates Group는 아랍에미리트의 항공 및 여행 회사입니다.', 26),
       ('Naspers', true, 'Naspers는 남아프리카공화국의 다국적 인터넷 및 미디어 그룹입니다.', 27),
       ('Indofood', true, 'PT Indofood Sukses Makmur Tbk는 인도네시아의 식품 제조 회사입니다.', 28),
       ('Dangote Group', true, 'Dangote Group는 나이지리아의 다국적 산업 회사입니다.', 29),
       ('Super Selectos', false, 'Super Selectos는 엘살바도르의 슈퍼마켓 체인입니다.', 30);

-- 비밀번호 죄다 1임
INSERT INTO accounts (email, password, name, account_type) VALUES ('free@test.com', '{bcrypt}$2a$10$HlVJohlGeJPOmW.pi231Ke/dTztPmv8s7GS7DZS8fcWHVIP4Dez7m', 'Test User 1', 'JOBSEEKER');
INSERT INTO accounts (email, password, name, account_type)
VALUES ('emp@test.com', '{bcrypt}$2a$10$HlVJohlGeJPOmW.pi231Ke/dTztPmv8s7GS7DZS8fcWHVIP4Dez7m', 'Test User 2', 'EMPLOYEE');
INSERT INTO accounts (email, password, name, account_type)
VALUES ('admin@test.com', '{bcrypt}$2a$10$HlVJohlGeJPOmW.pi231Ke/dTztPmv8s7GS7DZS8fcWHVIP4Dez7m', 'Test User 3', 'PERSONNEL');

INSERT INTO job (name, job_type, start_salary, end_salary, description, category, company_id, created_date, modified_date)
VALUES
    ('소프트웨어 엔지니어', 'FULLTIME', 6000, 7000, '소프트웨어 애플리케이션 개발 및 유지보수', 'WEB_SOFT', 1, '2022-01-01 09:00:00', '2022-01-01 09:00:00'),
    ('데이터 분석가', 'PARTTIME', 4000, 5000, '복잡한 데이터 세트의 분석 및 해석', 'DATA_SCI', 2, '2022-01-02 10:00:00', '2022-01-02 10:00:00'),
    ('마케팅 전문가', 'FULLTIME', 5500, 6500, '마케팅 캠페인 기획 및 실행', 'SALE_MAR', 3, '2022-01-03 11:00:00', '2022-01-03 11:00:00'),
    ('그래픽 디자이너', 'PARTTIME', 3000, 4000, '마케팅 자료를 위한 시각 디자인 제작', 'GRAP_DES', 4, '2022-01-04 12:00:00', '2022-01-04 12:00:00'),
    ('교육 조정자', 'FULLTIME', 3500, 4500, '교육 프로그램 조직 및 제공', 'EDU_TRAI', 5, '2022-01-05 13:00:00', '2022-01-05 13:00:00'),
    ('프론트엔드 개발자', 'FULLTIME', 5500, 6500, '웹 애플리케이션을 위한 사용자 인터페이스 개발', 'WEB_SOFT', 6, '2022-01-06 14:00:00', '2022-01-06 14:00:00'),
    ('데이터 과학자', 'FULLTIME', 7000, 8000, '고급 분석을 통한 데이터 인사이트 추출', 'DATA_SCI', 7, '2022-01-07 15:00:00', '2022-01-07 15:00:00'),
    ('영업 대표', 'PARTTIME', 2500, 3500, '제품 또는 서비스 홍보 및 판매', 'SALE_MAR', 8, '2022-01-08 16:00:00', '2022-01-08 16:00:00'),
    ('UI/UX 디자이너', 'FULLTIME', 4000, 5000, '디지털 제품을 위한 사용자 인터페이스 디자인', 'GRAP_DES', 9, '2022-01-09 17:00:00', '2022-01-09 17:00:00'),
    ('교육 전문가', 'PARTTIME', 3000, 4000, '교육 프로그램 개발 및 제공', 'EDU_TRAI', 10, '2022-01-10 18:00:00', '2022-01-10 18:00:00'),
    ('백엔드 개발자', 'FULLTIME', 6000, 7000, '서버 사이드 애플리케이션 및 API 개발', 'WEB_SOFT', 11, '2022-01-11 19:00:00', '2022-01-11 19:00:00'),
    ('데이터 엔지니어', 'FULLTIME', 7000, 8000, '데이터 파이프라인 설계 및 관리', 'DATA_SCI', 12, '2022-01-12 20:00:00', '2022-01-12 20:00:00'),
    ('마케팅 매니저', 'FULLTIME', 8000, 9000, '마케팅 전략 및 캠페인 관리', 'SALE_MAR', 13, '2022-01-13 21:00:00', '2022-01-13 21:00:00'),
    ('모션 그래픽 디자이너', 'PARTTIME', 4000, 5000, '애니메이션 비주얼 컨텐츠 제작', 'GRAP_DES', 14, '2022-01-14 22:00:00', '2022-01-14 22:00:00'),
    ('기업 교육 트레이너', 'FULLTIME', 4500, 5500, '직원을 대상으로 교육 프로그램 제공', 'EDU_TRAI', 15, '2022-01-15 23:00:00', '2022-01-15 23:00:00'),
    ('모바일 앱 개발자', 'FULLTIME', 6500, 7500, '모바일 애플리케이션 개발', 'WEB_SOFT', 16, '2022-01-16 09:00:00', '2022-01-16 09:00:00'),
    ('데이터 분석 인턴', 'INTERNSHIP', 2000, 3000, '인턴으로서 데이터 분석 프로젝트 지원', 'DATA_SCI', 17, '2022-01-17 10:00:00', '2022-01-17 10:00:00'),
    ('영업 매니저', 'FULLTIME', 9000, 10000, '영업 팀의 리더십 및 관리', 'SALE_MAR', 18, '2022-01-18 11:00:00', '2022-01-18 11:00:00'),
    ('사용자 인터페이스 디자이너', 'PARTTIME', 3500, 4500, '시각적으로 매력적인 UI 디자인', 'GRAP_DES', 19, '2022-01-19 12:00:00', '2022-01-19 12:00:00'),
    ('E-러닝 전문가', 'FULLTIME', 5000, 6000, 'E-러닝 프로그램 개발 및 구현', 'EDU_TRAI', 20, '2022-01-20 13:00:00', '2022-01-20 13:00:00'),
    ('DevOps 엔지니어', 'FULLTIME', 7000, 8000, 'IT 인프라 관리 및 최적화', 'WEB_SOFT', 21, '2022-01-21 14:00:00', '2022-01-21 14:00:00'),
    ('머신 러닝 엔지니어', 'FULLTIME', 8000, 9000, '머신 러닝 모델 개발 및 배포', 'DATA_SCI', 22, '2022-01-22 15:00:00', '2022-01-22 15:00:00'),
    ('디지털 마케팅 전문가', 'FULLTIME', 6000, 7000, '디지털 마케팅 캠페인 기획 및 실행', 'SALE_MAR', 23, '2022-01-23 16:00:00', '2022-01-23 16:00:00'),
    ('일러스트레이터', 'PARTTIME', 3000, 4000, '다양한 미디어를 위한 일러스트레이션 제작', 'GRAP_DES', 24, '2022-01-24 17:00:00', '2022-01-24 17:00:00'),
    ('기업 교육 트레이너', 'PARTTIME', 3000, 4000, '전문가들을 위한 교육 세션 진행', 'EDU_TRAI', 25, '2022-01-25 18:00:00', '2022-01-25 18:00:00'),
    ('클라우드 솔루션 아키텍트', 'FULLTIME', 9000, 10000, '클라우드 기반 솔루션 설계 및 구현', 'WEB_SOFT', 26, '2022-01-26 19:00:00', '2022-01-26 19:00:00'),
    ('데이터 프라이버시 분석가', 'FULLTIME', 7000, 8000, '데이터 보호 규정 준수 및 분석', 'DATA_SCI', 27, '2022-01-27 20:00:00', '2022-01-27 20:00:00'),
    ('소셜 미디어 매니저', 'FULLTIME', 6000, 7000, '소셜 미디어 프로모션 및 관리', 'SALE_MAR', 28, '2022-01-28 21:00:00', '2022-01-28 21:00:00'),
    ('웹 디자이너', 'PARTTIME', 3500, 4500, '시각적으로 매력적인 웹 디자인', 'GRAP_DES', 29, '2022-01-29 22:00:00', '2022-01-29 22:00:00'),
    ('교육 및 개발 매니저', 'FULLTIME', 8000, 9000, '교육 및 개발 프로젝트 리더십', 'EDU_TRAI', 30, '2022-01-30 23:00:00', '2022-01-30 23:00:00');

INSERT INTO reviews (title, content, rating, account_id, company_id, created_date, modified_date)
WITH generator AS (
    SELECT ROW_NUMBER() OVER () AS rn
    FROM (
             SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL
             SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9
         ) a
             CROSS JOIN (
        SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL
        SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9
    ) b
)
SELECT
    CONCAT('Review ', g1.rn, ' for Company ', c.company_id),
    CONCAT('This is review ', g1.rn, ' for Company ', c.company_id, '.'),
    FLOOR(1 + RAND() * 5),
    m.account_id,
    c.company_id,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM
    generator g1
        CROSS JOIN companies c
        CROSS JOIN accounts m
WHERE g1.rn <= 30;

