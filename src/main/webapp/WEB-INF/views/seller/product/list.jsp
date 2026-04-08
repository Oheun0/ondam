<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  request.setAttribute("sellerActiveMenu", "product");
  request.setAttribute("sellerPageTitle", "상품 관리");
  String sellerName = (String) request.getAttribute("sellerName");
  if (sellerName == null) sellerName = "온담스토어";
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>상품 관리 | 온담 판매자센터</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-product.css" />
</head>
<body class="seller-app" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-layout">
    <jsp:include page="/WEB-INF/views/seller/layout/seller-sidebar.jsp" />

    <div class="seller-main-area">
      <jsp:include page="/WEB-INF/views/seller/layout/seller-header.jsp" />

      <main class="seller-content seller-product-page" aria-label="상품 관리">
        <header class="seller-product-head">
          <div>
            <h2 class="seller-product-title">상품 관리</h2>
            <p class="seller-product-sub">등록된 상품을 확인하고 수정할 수 있어요</p>
          </div>
          <div class="seller-product-head-actions">
            <button type="button" class="seller-primary-btn" id="sellerNewProductBtn">
              <span class="material-icons-outlined" aria-hidden="true">add</span>
              새 상품 등록
            </button>
          </div>
        </header>

        <section class="seller-product-summary" aria-label="요약">
          <div class="seller-product-summary-grid">
            <div class="seller-product-summary-card">
              <div class="seller-product-summary-label">전체 상품</div>
              <div class="seller-product-summary-value">24<span class="seller-product-summary-unit">개</span></div>
            </div>
            <div class="seller-product-summary-card">
              <div class="seller-product-summary-label">판매중</div>
              <div class="seller-product-summary-value">18<span class="seller-product-summary-unit">개</span></div>
            </div>
            <div class="seller-product-summary-card">
              <div class="seller-product-summary-label">품절 임박</div>
              <div class="seller-product-summary-value">3<span class="seller-product-summary-unit">개</span></div>
            </div>
            <div class="seller-product-summary-card">
              <div class="seller-product-summary-label">숨김</div>
              <div class="seller-product-summary-value">2<span class="seller-product-summary-unit">개</span></div>
            </div>
          </div>
        </section>

        <section class="seller-product-toolbar seller-card" aria-label="검색 및 필터">
          <div class="seller-product-filters">
            <div class="seller-product-filter">
              <label class="seller-product-filter-label" for="sellerProductQuery">검색</label>
              <div class="seller-product-input-wrap">
                <span class="material-icons-outlined" aria-hidden="true">search</span>
                <input id="sellerProductQuery" class="seller-product-input" type="text" placeholder="상품명으로 검색해 주세요" />
              </div>
            </div>

            <div class="seller-product-filter">
              <label class="seller-product-filter-label" for="sellerProductCategory">종류 카테고리</label>
              <select id="sellerProductCategory" class="seller-product-select">
                <option value="all">종류 카테고리</option>
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

            <div class="seller-product-filter">
              <label class="seller-product-filter-label" for="sellerProductSale">판매 상태</label>
              <select id="sellerProductSale" class="seller-product-select">
                <option value="all">전체 상태</option>
                <option value="selling">판매중</option>
                <option value="soldout">품절</option>
                <option value="hidden">숨김</option>
              </select>
            </div>

            <div class="seller-product-filter">
              <label class="seller-product-filter-label" for="sellerProductStock">재고 상태</label>
              <select id="sellerProductStock" class="seller-product-select">
                <option value="all">전체 재고</option>
                <option value="in">재고 있음</option>
                <option value="low">품절 임박</option>
                <option value="out">품절</option>
              </select>
            </div>

            <div class="seller-product-filter seller-product-filter--btn">
              <button type="button" class="seller-secondary-btn" id="sellerProductApplyBtn">필터 적용</button>
            </div>
          </div>
        </section>

        <section class="seller-card seller-product-table-wrap" aria-label="상품 목록">
          <div class="seller-product-table-head">
            <div class="seller-product-table-title">
              <h3 class="seller-product-h3">상품 목록</h3>
              <p class="seller-product-h3-sub">썸네일/가격/재고/쇼츠 연결 상태를 빠르게 확인하세요</p>
            </div>
            <div class="seller-product-table-meta">
              <span class="seller-product-meta-pill">총 5건 (더미)</span>
            </div>
          </div>

          <div class="seller-product-table-scroll">
            <table class="seller-product-table">
              <thead>
                <tr>
                  <th scope="col">대표 이미지</th>
                  <th scope="col">상품명</th>
                  <th scope="col">카테고리</th>
                  <th scope="col">가격</th>
                  <th scope="col">할인</th>
                  <th scope="col">재고</th>
                  <th scope="col">판매 상태</th>
                  <th scope="col">쇼츠 연결</th>
                  <th scope="col">관리</th>
                </tr>
              </thead>
              <tbody>
                <tr data-product-id="P-1001">
                  <td>
                    <img class="seller-product-thumb" src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="상품 썸네일" />
                  </td>
                  <td class="seller-product-name-cell">
                    <div class="seller-product-name">부드러운 라운드 니트 가디건</div>
                    <div class="seller-product-subline">상품ID: P-1001 · 노출: 사용자 상품목록/상세</div>
                  </td>
                  <td>아우터</td>
                  <td><strong>39,000원</strong></td>
                  <td>10%</td>
                  <td>24개</td>
                  <td><span class="seller-product-badge seller-product-badge--selling">판매중</span></td>
                  <td>1개</td>
                  <td class="seller-product-row-actions">
                    <button type="button" class="seller-mini-btn" data-action="edit">수정</button>
                    <button type="button" class="seller-mini-btn" data-action="hide">숨김</button>
                  </td>
                </tr>

                <tr data-product-id="P-1002">
                  <td>
                    <img class="seller-product-thumb" src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="상품 썸네일" />
                  </td>
                  <td class="seller-product-name-cell">
                    <div class="seller-product-name">편안한 봄 니트 조끼</div>
                    <div class="seller-product-subline">상품ID: P-1002 · 추천/검색 노출 최적화: 태그 점검</div>
                  </td>
                  <td>상의</td>
                  <td><strong>29,000원</strong></td>
                  <td>없음</td>
                  <td>3개</td>
                  <td><span class="seller-product-badge seller-product-badge--low">품절 임박</span></td>
                  <td>0개</td>
                  <td class="seller-product-row-actions">
                    <button type="button" class="seller-mini-btn" data-action="edit">수정</button>
                    <button type="button" class="seller-mini-btn seller-mini-btn--warn" data-action="soldout">품절</button>
                  </td>
                </tr>

                <tr data-product-id="P-1003">
                  <td>
                    <img class="seller-product-thumb" src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="상품 썸네일" />
                  </td>
                  <td class="seller-product-name-cell">
                    <div class="seller-product-name">가벼운 데일리 셔츠</div>
                    <div class="seller-product-subline">상품ID: P-1003 · 쇼츠 2개 연결(노출 증가)</div>
                  </td>
                  <td>상의</td>
                  <td><strong>35,000원</strong></td>
                  <td>15%</td>
                  <td>0개</td>
                  <td><span class="seller-product-badge seller-product-badge--soldout">품절</span></td>
                  <td>2개</td>
                  <td class="seller-product-row-actions">
                    <button type="button" class="seller-mini-btn" data-action="edit">수정</button>
                    <button type="button" class="seller-mini-btn seller-mini-btn--primary" data-action="reopen">재등록</button>
                  </td>
                </tr>

                <tr data-product-id="P-1004">
                  <td>
                    <img class="seller-product-thumb" src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="상품 썸네일" />
                  </td>
                  <td class="seller-product-name-cell">
                    <div class="seller-product-name">가볍게 입는 데일리 팬츠</div>
                    <div class="seller-product-subline">상품ID: P-1004 · 옵션/사이즈 재고 동기화 필요(더미)</div>
                  </td>
                  <td>하의</td>
                  <td><strong>42,000원</strong></td>
                  <td>없음</td>
                  <td>18개</td>
                  <td><span class="seller-product-badge seller-product-badge--hidden">숨김</span></td>
                  <td>0개</td>
                  <td class="seller-product-row-actions">
                    <button type="button" class="seller-mini-btn" data-action="edit">수정</button>
                    <button type="button" class="seller-mini-btn seller-mini-btn--primary" data-action="show">판매중</button>
                  </td>
                </tr>

                <tr data-product-id="P-1005">
                  <td>
                    <img class="seller-product-thumb" src="${pageContext.request.contextPath}/images/category/type-top-knit.jpg" alt="상품 썸네일" />
                  </td>
                  <td class="seller-product-name-cell">
                    <div class="seller-product-name">봄날 산책 원피스 세트</div>
                    <div class="seller-product-subline">상품ID: P-1005 · 선물 주문 비중 높음(더미)</div>
                  </td>
                  <td>원피스/세트</td>
                  <td><strong>59,000원</strong></td>
                  <td>5%</td>
                  <td>9개</td>
                  <td><span class="seller-product-badge seller-product-badge--selling">판매중</span></td>
                  <td>1개</td>
                  <td class="seller-product-row-actions">
                    <button type="button" class="seller-mini-btn" data-action="edit">수정</button>
                    <button type="button" class="seller-mini-btn" data-action="hide">숨김</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <div class="seller-product-pagination" aria-label="페이지네이션(더미)">
            <button type="button" class="seller-page-btn" data-page="prev">이전</button>
            <button type="button" class="seller-page-btn active" data-page="1">1</button>
            <button type="button" class="seller-page-btn" data-page="2">2</button>
            <button type="button" class="seller-page-btn" data-page="3">3</button>
            <button type="button" class="seller-page-btn" data-page="next">다음</button>
          </div>
        </section>

        <section class="seller-card seller-product-empty" aria-label="빈 상태(더미)" hidden>
          <div class="seller-product-empty-inner">
            <div class="seller-product-empty-icon" aria-hidden="true">
              <span class="material-icons-outlined">inventory_2</span>
            </div>
            <h3 class="seller-product-empty-title">아직 등록된 상품이 없어요</h3>
            <p class="seller-product-empty-desc">새 상품을 등록해 판매를 시작해보세요</p>
            <button type="button" class="seller-primary-btn" data-action="new-product">새 상품 등록</button>
          </div>
        </section>
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
  <script src="${pageContext.request.contextPath}/js/seller/product-list.js"></script>
</body>
</html>

