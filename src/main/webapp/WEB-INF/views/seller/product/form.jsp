<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  request.setAttribute("sellerActiveMenu", "product");
  request.setAttribute("sellerPageTitle", "새 상품 등록");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>새 상품 등록 | 온담 파트너</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-product.css" />
</head>
<body class="seller-app" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-layout">
    <jsp:include page="/WEB-INF/views/seller/layout/seller-sidebar.jsp" />

    <div class="seller-main-area">
      <jsp:include page="/WEB-INF/views/seller/layout/seller-header.jsp" />

      <main class="seller-content seller-product-form-page" aria-label="상품 등록/수정">
        <header class="seller-product-form-head">
          <div>
            <h2 class="seller-product-title">새 상품 등록</h2>
            <p class="seller-product-sub">상품 정보, 옵션, 이미지, 추천 태그를 입력해 주세요</p>
          </div>
          <div class="seller-product-form-head-actions">
            <a class="seller-secondary-btn seller-product-link-btn" href="${pageContext.request.contextPath}/preview?page=seller/product/list">
              <span class="material-icons-outlined" aria-hidden="true">arrow_back</span>
              목록으로
            </a>
          </div>
        </header>

        <form id="sellerProductForm" class="seller-product-form" action="#" method="post" novalidate>
          <section class="seller-card seller-product-section" aria-label="기본정보">
            <header class="seller-product-section-head">
              <div>
                <h3 class="seller-product-section-title">기본정보</h3>
                <p class="seller-product-section-sub">상품의 기본 정보를 입력해 주세요</p>
              </div>
            </header>

            <div class="seller-product-grid seller-product-grid--2">
              <div class="seller-product-field">
                <label class="seller-product-label" for="brandName">브랜드명 <span class="seller-product-required">*</span></label>
                <input id="brandName" name="brandName" class="seller-product-control" type="text" placeholder="브랜드명을 입력해 주세요" />
                <p class="seller-product-error hidden" id="brandNameError" aria-live="polite"></p>
              </div>

              <div class="seller-product-field">
                <label class="seller-product-label" for="productName">상품명 <span class="seller-product-required">*</span></label>
                <input id="productName" name="productName" class="seller-product-control" type="text" placeholder="상품명을 입력해 주세요" />
                <p class="seller-product-error hidden" id="productNameError" aria-live="polite"></p>
              </div>

              <div class="seller-product-field">
                <label class="seller-product-label" for="situationCategory">카테고리 <span class="seller-product-required">*</span></label>
                <div class="seller-product-split">
                  <select id="situationCategory" name="situationCategory" class="seller-product-control">
                    <option value="">상황 카테고리를 선택해 주세요</option>
                    <optgroup label="일상 생활">
                      <option value="14">집에서 편하게 입는 옷</option>
                      <option value="15">잠잘 때 입는 옷</option>
                      <option value="16">집안일 할 때 입는 옷</option>
                      <option value="17">가볍게 입는 옷</option>
                      <option value="18">가볍게 나갈 때 입는 옷</option>
                      <option value="19">사람 만날 때 입는 옷</option>
                      <option value="20">날씨에 맞는 옷</option>
                      <option value="21">오래 걸어도 편한 옷</option>
                      <option value="22">입고 벗기 쉬운 옷</option>
                      <option value="26">신축성 좋은 옷</option>
                      <option value="27">부드러운 옷</option>
                      <option value="28">넉넉한 옷</option>
                      <option value="29">피부에 자극 없는 옷</option>
                    </optgroup>
                    <optgroup label="특별한 날">
                      <option value="30">결혼식 갈 때</option>
                      <option value="31">장례식 갈 때</option>
                      <option value="32">동창회 갈 때</option>
                      <option value="33">모임/행사 갈 때</option>
                      <option value="34">입학식 / 졸업식</option>
                      <option value="35">가족 행사</option>
                      <option value="36">기념일</option>
                    </optgroup>
                    <optgroup label="취미·여가">
                      <option value="37">등산할 때</option>
                      <option value="38">골프 칠 때</option>
                      <option value="39">수영할 때</option>
                      <option value="40">자전거 탈 때</option>
                      <option value="41">여행 갈 때</option>
                      <option value="42">낚시할 때</option>
                      <option value="43">텃밭/원예 할 때</option>
                      <option value="44">편하게 놀러 갈 때</option>
                    </optgroup>
                    <optgroup label="선물하기">
                      <option value="45">부모님께 드리기 좋은 옷</option>
                      <option value="46">할머니·할아버지 옷</option>
                      <option value="47">손주 옷</option>
                      <option value="48">사이즈 넉넉한 옷</option>
                      <option value="49">누구나 입기 좋은 옷</option>
                      <option value="50">인기 많은 옷</option>
                    </optgroup>
                  </select>
                  <select id="typeCategory" name="typeCategory" class="seller-product-control">
                    <option value="">종류 카테고리를 선택해 주세요</option>
                    <optgroup label="윗옷">
                      <option value="5">반팔</option>
                      <option value="6">긴팔</option>
                      <option value="7">니트</option>
                      <option value="8">셔츠</option>
                      <option value="9">조끼</option>
                    </optgroup>
                    <optgroup label="아랫옷">
                      <option value="10">긴바지</option>
                      <option value="11">반바지</option>
                      <option value="12">치마</option>
                    </optgroup>
                    <optgroup label="겉옷">
                      <option value="13">가디건</option>
                      <option value="14">점퍼</option>
                      <option value="15">코트</option>
                      <option value="16">바람막이</option>
                    </optgroup>
                    <optgroup label="한 벌 옷">
                      <option value="17">원피스</option>
                      <option value="18">세트 옷</option>
                    </optgroup>
                  </select>
                </div>
                <p class="seller-product-error hidden" id="categoryError" aria-live="polite"></p>
              </div>

              <div class="seller-product-field">
                <label class="seller-product-label" for="saleStatus">판매상태</label>
                <select id="saleStatus" name="saleStatus" class="seller-product-control">
                  <option value="selling">판매중</option>
                  <option value="hidden">숨김</option>
                  <option value="soldout">품절</option>
                </select>
              </div>
            </div>
          </section>

          <section class="seller-card seller-product-section" aria-label="가격 정보">
            <header class="seller-product-section-head">
              <div>
                <h3 class="seller-product-section-title">가격 정보</h3>
                <p class="seller-product-section-sub">사용자 화면에 보여질 가격과 할인 정보를 설정해 주세요</p>
              </div>
              <div class="seller-product-price-preview" aria-label="가격 미리보기(더미)">
                <span class="seller-product-price-before" id="priceBefore">-</span>
                <span class="seller-product-price-arrow">→</span>
                <span class="seller-product-price-after" id="priceAfter">-</span>
              </div>
            </header>

            <div class="seller-product-grid seller-product-grid--3">
              <div class="seller-product-field">
                <label class="seller-product-label" for="price">판매가 <span class="seller-product-required">*</span></label>
                <input id="price" name="price" class="seller-product-control" type="text" inputmode="numeric" placeholder="판매가를 입력해 주세요" />
                <p class="seller-product-error hidden" id="priceError" aria-live="polite"></p>
              </div>

              <div class="seller-product-field">
                <label class="seller-product-label" for="discountRate">할인율</label>
                <div class="seller-product-inline">
                  <input id="discountRate" name="discountRate" class="seller-product-control" type="text" inputmode="numeric" placeholder="할인율(%)" />
                  <span class="seller-product-suffix">%</span>
                </div>
              </div>

              <div class="seller-product-field">
                <label class="seller-product-label" for="salePrice">할인가</label>
                <input id="salePrice" name="salePrice" class="seller-product-control" type="text" inputmode="numeric" placeholder="할인가를 입력해 주세요" />
              </div>
            </div>
            <p class="seller-product-hint">판매가와 할인율을 입력하면 할인가가 자동 계산돼요. (더미)</p>
          </section>

          <section class="seller-card seller-product-section" aria-label="이미지 등록">
            <header class="seller-product-section-head">
              <div>
                <h3 class="seller-product-section-title">이미지 등록</h3>
                <p class="seller-product-section-sub">대표 이미지 1장과 상세 이미지를 등록해 주세요</p>
              </div>
            </header>

            <div class="seller-product-upload-grid">
              <div class="seller-product-upload-block">
                <div class="seller-product-upload-head">
                  <strong>대표 이미지</strong>
                  <span class="seller-product-upload-desc">상품 목록과 상세 상단에 사용돼요</span>
                </div>
                <div class="seller-product-upload-box" id="thumbBox" tabindex="0" role="button" aria-label="대표 이미지 업로드(더미)">
                  <div class="seller-product-upload-icon" aria-hidden="true">
                    <span class="material-icons-outlined">image</span>
                  </div>
                  <div class="seller-product-upload-text">
                    <div class="seller-product-upload-title">대표 이미지 추가</div>
                    <div class="seller-product-upload-sub">클릭해서 더미 이미지로 채워볼 수 있어요</div>
                  </div>
                  <button type="button" class="seller-mini-btn seller-mini-btn--primary" id="thumbAddBtn">이미지 추가</button>
                </div>
                <div class="seller-product-thumb-preview hidden" id="thumbPreview">
                  <img id="thumbPreviewImg" alt="대표 이미지 미리보기" />
                  <button type="button" class="seller-mini-btn" id="thumbRemoveBtn">삭제</button>
                </div>
                <p class="seller-product-error hidden" id="thumbError" aria-live="polite"></p>
              </div>

              <div class="seller-product-upload-block">
                <div class="seller-product-upload-head">
                  <strong>상세 이미지</strong>
                  <span class="seller-product-upload-desc">상품 설명 아래에 노출돼요</span>
                </div>
                <div class="seller-product-upload-box seller-product-upload-box--multi" id="detailBox" tabindex="0" role="button" aria-label="상세 이미지 업로드(더미)">
                  <div class="seller-product-upload-icon" aria-hidden="true">
                    <span class="material-icons-outlined">collections</span>
                  </div>
                  <div class="seller-product-upload-text">
                    <div class="seller-product-upload-title">상세 이미지 추가</div>
                    <div class="seller-product-upload-sub">“이미지 추가”로 더미 썸네일을 만들어요</div>
                  </div>
                  <button type="button" class="seller-mini-btn seller-mini-btn--primary" id="detailAddBtn">이미지 추가</button>
                </div>

                <div class="seller-product-detail-thumbs" id="detailThumbs" aria-label="상세 이미지 미리보기(더미)"></div>
                <p class="seller-product-hint">정렬은 드래그 대신 “위/아래” 버튼으로 더미 구현했어요.</p>
              </div>
            </div>
          </section>

          <section class="seller-card seller-product-section" aria-label="옵션 및 재고">
            <header class="seller-product-section-head">
              <div>
                <h3 class="seller-product-section-title">옵션 / 재고</h3>
                <p class="seller-product-section-sub">색상, 사이즈, 재고를 입력해 주세요</p>
              </div>
            </header>

            <div class="seller-product-grid seller-product-grid--2">
              <div class="seller-product-field">
                <label class="seller-product-label" for="colors">색상</label>
                <div class="chip-wrap seller-color-chips" id="sellerColorChips" aria-label="색상 선택(다중 선택 가능)">
                  <label class="chip"><input type="checkbox" name="productColor" value="검정색"><span><i class="color-dot" style="background:#111111;"></i>검정색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="흰색"><span><i class="color-dot" style="background:#ffffff;"></i>흰색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="회색"><span><i class="color-dot" style="background:#8b8f94;"></i>회색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="고동색"><span><i class="color-dot" style="background:#5a3b2e;"></i>고동색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="연갈색"><span><i class="color-dot" style="background:#b88a60;"></i>연갈색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="자주색"><span><i class="color-dot" style="background:#7b1f52;"></i>자주색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="빨강색"><span><i class="color-dot" style="background:#d73333;"></i>빨강색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="연분홍색"><span><i class="color-dot" style="background:#f5c7d3;"></i>연분홍색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="노란색"><span><i class="color-dot" style="background:#f2d348;"></i>노란색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="남색"><span><i class="color-dot" style="background:#203864;"></i>남색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="하늘색"><span><i class="color-dot" style="background:#86c8f2;"></i>하늘색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="국방색"><span><i class="color-dot" style="background:#556b2f;"></i>국방색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="기타"><span><i class="color-dot rainbow"></i>기타색</span></label>
                </div>
              </div>
              <div class="seller-product-field">
                <label class="seller-product-label" for="sizes">사이즈</label>
                <div class="seller-size-groups" aria-label="사이즈 선택(다중 선택 가능)">
                  <div class="seller-size-group">
                    <div class="seller-size-group-title">상의 사이즈</div>
                    <div class="chip-wrap seller-size-chips">
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="85"><span>85 (작음)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="90"><span>90 (보통)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="95"><span>95 (보통)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="100"><span>100 (여유)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="105"><span>105 (넉넉)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="110"><span>110 (크게)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="FREE_TOP"><span>한 가지 사이즈 (FREE)</span></label>
                    </div>
                  </div>

                  <div class="seller-size-group">
                    <div class="seller-size-group-title">하의 사이즈</div>
                    <div class="chip-wrap seller-size-chips">
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="26"><span>26 (여성 S)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="28"><span>28 (여성 M)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="30"><span>30 (남성 M)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="32"><span>32 (남성 L)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="34"><span>34 (넉넉)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="FREE_BOTTOM"><span>한 가지 사이즈 (FREE)</span></label>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="seller-product-option-actions">
              <button type="button" class="seller-secondary-btn" id="buildOptionsBtn">
                <span class="material-icons-outlined" aria-hidden="true">table_rows</span>
                옵션 조합 생성
              </button>
            </div>

            <div class="seller-product-option-table-wrap">
              <table class="seller-product-option-table" aria-label="옵션 조합 테이블(더미)">
                <thead>
                  <tr>
                    <th scope="col">색상</th>
                    <th scope="col">사이즈</th>
                    <th scope="col">재고</th>
                    <th scope="col">품절 처리</th>
                    <th scope="col">행</th>
                  </tr>
                </thead>
                <tbody id="optionBody">
                  <tr class="seller-product-option-empty">
                    <td colspan="5">옵션 조합을 생성하면 여기에 표시돼요.</td>
                  </tr>
                </tbody>
              </table>
              <p class="seller-product-error hidden" id="optionError" aria-live="polite"></p>
            </div>
          </section>

          <section class="seller-card seller-product-section" aria-label="쉽게 읽는 상품 설명">
            <header class="seller-product-section-head">
              <div>
                <h3 class="seller-product-section-title">쉽게 읽는 상품 설명</h3>
                <p class="seller-product-section-sub">고령층 사용자도 쉽게 이해할 수 있게 상품을 소개해 주세요</p>
              </div>
            </header>

            <div class="seller-product-grid seller-product-grid--1">
              <div class="seller-product-field">
                <label class="seller-product-label" for="easyOneLine">한 줄 요약</label>
                <textarea id="easyOneLine" class="seller-product-textarea" rows="2" placeholder="예: 부드럽고 가볍게 입기 좋은 가디건이에요"></textarea>
              </div>
              <div class="seller-product-field">
                <label class="seller-product-label" for="easyFor">이런 분께 좋아요</label>
                <textarea id="easyFor" class="seller-product-textarea" rows="2" placeholder="예: 가볍게 외출할 때 입기 좋아요"></textarea>
              </div>
              <div class="seller-product-field">
                <label class="seller-product-label" for="easyComfort">입기 편한 점</label>
                <textarea id="easyComfort" class="seller-product-textarea" rows="2" placeholder="예: 신축성이 있어 입고 벗기 편해요"></textarea>
              </div>
            </div>
          </section>

          <section class="seller-card seller-product-section" aria-label="필터 태그">
            <header class="seller-product-section-head">
              <div>
                <h3 class="seller-product-section-title">필터 태그</h3>
                <p class="seller-product-section-sub">검색 시 필터에 활용되는 태그를 선택해 주세요</p>
              </div>
            </header>

            <div class="seller-filter-grid">
              <div class="seller-product-tag-block seller-product-tag-block--filter">
                <div class="seller-product-tag-title">계절 (단일 선택)</div>
                <div class="chip-wrap seller-filter-chips" role="radiogroup" aria-label="계절">
                  <label class="season-pill"><input type="radio" name="productSeason" value="따뜻해요"><span>따뜻해요</span></label>
                  <label class="season-pill"><input type="radio" name="productSeason" value="시원해요"><span>시원해요</span></label>
                  <label class="season-pill"><input type="radio" name="productSeason" value="사계절 입어요"><span>사계절 입어요</span></label>
                </div>
              </div>

              <div class="seller-product-tag-block seller-product-tag-block--filter">
                <div class="seller-product-tag-title">옷 특징 (최대 3개)</div>
                <div class="chip-wrap seller-filter-chips" id="featureChipWrap" aria-label="옷 특징(복수 선택 가능)">
                  <label class="chip chip--plain"><input type="checkbox" name="clothesFeature" value="주머니 있어요"><span>주머니 있어요</span></label>
                  <label class="chip chip--plain"><input type="checkbox" name="clothesFeature" value="단추 있는 옷이에요"><span>단추 있는 옷이에요</span></label>
                  <label class="chip chip--plain"><input type="checkbox" name="clothesFeature" value="지퍼 있는 옷이에요"><span>지퍼 있는 옷이에요</span></label>
                  <label class="chip chip--plain"><input type="checkbox" name="clothesFeature" value="허리가 편해요"><span>허리가 편해요</span></label>
                  <label class="chip chip--plain"><input type="checkbox" name="clothesFeature" value="세탁하기 쉬워요"><span>세탁하기 쉬워요</span></label>
                  <label class="chip chip--plain"><input type="checkbox" name="clothesFeature" value="바람을 막아줘요"><span>바람을 막아줘요</span></label>
                </div>
                <p class="seller-product-hint seller-product-hint--tight" id="featureLimitHint">0/3 선택</p>
              </div>
            </div>
          </section>

          <div class="seller-product-actions">
            <button type="button" class="seller-secondary-btn" id="tempSaveBtn">임시 저장</button>
            <button type="submit" class="seller-primary-btn" id="submitBtn">등록하기</button>
          </div>

          <p class="seller-product-form-error hidden" id="formError" aria-live="assertive"></p>
        </form>
      </main>

      <jsp:include page="/WEB-INF/views/seller/layout/seller-footer.jsp" />
    </div>
  </div>

  <script>
    // 레이아웃 공통(더미) 동작: 헤더 버튼들
    (function () {
      var notifyBtn = document.getElementById('sellerHeaderNotifyBtn');
      var logoutBtn = document.getElementById('sellerHeaderLogoutBtn');
      if (notifyBtn) {
        notifyBtn.addEventListener('click', function () {
          alert('알림 기능은 아직 준비 중이에요.');
        });
      }
      if (logoutBtn) {
        logoutBtn.addEventListener('click', function () {
          alert('로그아웃은 아직 연동되지 않았어요. (더미)');
        });
      }
    })();
  </script>
  <script src="${pageContext.request.contextPath}/js/seller/product-form.js"></script>
</body>
</html>

