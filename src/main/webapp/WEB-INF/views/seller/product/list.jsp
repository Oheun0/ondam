<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setAttribute("sellerActiveMenu", "product");
  request.setAttribute("sellerPageTitle", "상품 관리");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>상품 관리 | 온담 파트너</title>

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

        <c:if test="${param.save == 'ok'}">
          <p class="seller-settings-profile-msg seller-settings-profile-msg--ok" role="status">상품이 등록되었습니다.</p>
        </c:if>
        <c:if test="${param.save == 'temp'}">
          <p class="seller-settings-profile-msg seller-settings-profile-msg--ok" role="status">상품이 임시 저장되었습니다.</p>
        </c:if>
        <c:if test="${param.save == 'fail'}">
          <p class="seller-settings-profile-msg seller-settings-profile-msg--err" role="alert">상품 등록에 실패했습니다.</p>
        </c:if>
        <c:if test="${param.save == 'updated'}">
          <p class="seller-settings-profile-msg seller-settings-profile-msg--ok" role="status">상품 정보가 수정되었습니다.</p>
        </c:if>

        <section class="seller-product-summary" aria-label="요약">
		  <div class="seller-product-summary-grid">
		    <div class="seller-product-summary-card">
		      <div class="seller-product-summary-label">전체 상품</div>
		      <div class="seller-product-summary-value">
		        <c:out value="${empty productTotal or productTotal == 0 ? '0' : productTotal}" /><span class="seller-product-summary-unit">개</span>
		      </div>
		    </div>
		    <div class="seller-product-summary-card">
		      <div class="seller-product-summary-label">판매중</div>
		      <div class="seller-product-summary-value">
		        <c:out value="${empty productSelling or productSelling == 0 ? '0' : productSelling}" /><span class="seller-product-summary-unit">개</span>
		      </div>
		    </div>
		    <div class="seller-product-summary-card">
		      <div class="seller-product-summary-label">품절 임박</div>
		      <div class="seller-product-summary-value">
		        <c:out value="${empty productLowStock or productLowStock == 0 ? '0' : productLowStock}" /><span class="seller-product-summary-unit">개</span>
		      </div>
		    </div>
		    <div class="seller-product-summary-card">
		      <div class="seller-product-summary-label">숨김</div>
		      <div class="seller-product-summary-value">
		        <c:out value="${empty productHidden or productHidden == 0 ? '0' : productHidden}" /><span class="seller-product-summary-unit">개</span>
		      </div>
		    </div>
		  </div>
		</section>

        <section class="seller-product-toolbar seller-card" aria-label="검색 및 필터">
          <form id="sellerProductFilterForm" method="get" action="${pageContext.request.contextPath}/seller/product">
          <div class="seller-product-filters">
            <div class="seller-product-filter">
              <label class="seller-product-filter-label" for="sellerProductQuery">검색</label>
              <div class="seller-product-input-wrap">
                <span class="material-icons-outlined" aria-hidden="true">search</span>
                <input id="sellerProductQuery" name="query" class="seller-product-input" type="text" value="${filterQuery}" placeholder="상품명으로 검색해 주세요" />
              </div>
            </div>

            <div class="seller-product-filter">
              <label class="seller-product-filter-label" for="sellerProductCategory">종류 카테고리</label>
              <select id="sellerProductCategory" name="category" class="seller-product-select">
                <option value="all" <c:if test="${filterCategory == 'all'}">selected</c:if>>종류 카테고리</option>
                <optgroup label="윗옷">
                  <option value="5" <c:if test="${filterCategory == '5'}">selected</c:if>>반팔</option>
                  <option value="6" <c:if test="${filterCategory == '6'}">selected</c:if>>긴팔</option>
                  <option value="7" <c:if test="${filterCategory == '7'}">selected</c:if>>니트</option>
                  <option value="8" <c:if test="${filterCategory == '8'}">selected</c:if>>셔츠</option>
                  <option value="9" <c:if test="${filterCategory == '9'}">selected</c:if>>조끼</option>
                </optgroup>
                <optgroup label="아랫옷">
                  <option value="10" <c:if test="${filterCategory == '10'}">selected</c:if>>긴바지</option>
                  <option value="11" <c:if test="${filterCategory == '11'}">selected</c:if>>반바지</option>
                  <option value="12" <c:if test="${filterCategory == '12'}">selected</c:if>>치마</option>
                </optgroup>
                <optgroup label="겉옷">
                  <option value="13" <c:if test="${filterCategory == '13'}">selected</c:if>>가디건</option>
                  <option value="14" <c:if test="${filterCategory == '14'}">selected</c:if>>점퍼</option>
                  <option value="15" <c:if test="${filterCategory == '15'}">selected</c:if>>코트</option>
                  <option value="16" <c:if test="${filterCategory == '16'}">selected</c:if>>바람막이</option>
                </optgroup>
                <optgroup label="한 벌 옷">
                  <option value="17" <c:if test="${filterCategory == '17'}">selected</c:if>>원피스</option>
                  <option value="18" <c:if test="${filterCategory == '18'}">selected</c:if>>세트 옷</option>
                </optgroup>
              </select>
            </div>

            <div class="seller-product-filter">
              <label class="seller-product-filter-label" for="sellerProductSale">판매 상태</label>
              <select id="sellerProductSale" name="sale" class="seller-product-select">
                <option value="all" <c:if test="${filterSale == 'all'}">selected</c:if>>전체 상태</option>
                <option value="selling" <c:if test="${filterSale == 'selling'}">selected</c:if>>판매중</option>
                <option value="soldout" <c:if test="${filterSale == 'soldout'}">selected</c:if>>품절</option>
                <option value="hidden" <c:if test="${filterSale == 'hidden'}">selected</c:if>>숨김</option>
              </select>
            </div>

            <div class="seller-product-filter">
              <label class="seller-product-filter-label" for="sellerProductStock">재고 상태</label>
              <select id="sellerProductStock" name="stock" class="seller-product-select">
                <option value="all" <c:if test="${filterStock == 'all'}">selected</c:if>>전체 재고</option>
                <option value="in" <c:if test="${filterStock == 'in'}">selected</c:if>>재고 있음</option>
                <option value="low" <c:if test="${filterStock == 'low'}">selected</c:if>>품절 임박</option>
                <option value="out" <c:if test="${filterStock == 'out'}">selected</c:if>>품절</option>
              </select>
            </div>

            <div class="seller-product-filter seller-product-filter--btn">
              <button type="submit" class="seller-secondary-btn" id="sellerProductApplyBtn">필터 적용</button>
            </div>
          </div>
          </form>
        </section>

        <section class="seller-card seller-product-table-wrap" aria-label="상품 목록" <c:if test="${productTotal == 0}">hidden</c:if>>
          <div class="seller-product-table-head">
            <div class="seller-product-table-title">
              <h3 class="seller-product-h3">상품 목록</h3>
              <p class="seller-product-h3-sub">썸네일/가격/재고/쇼츠 연결 상태를 빠르게 확인하세요</p>
            </div>
            <div class="seller-product-table-meta">
              <span class="seller-product-meta-pill">총 <c:out value="${productTotal}" />건</span>
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
                  <th scope="col">할인가</th>
                  <th scope="col">재고</th>
                  <th scope="col">판매 상태</th>
                  <th scope="col">쇼츠 연결</th>
                  <th scope="col">관리</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="row" items="${productRows}">
                  <c:set var="p" value="${row.product}" />
                  <c:set var="thumbUrl" value="${pageContext.request.contextPath}/images/common/no-image.png" />
                  <c:if test="${not empty row.thumb}">
                    <c:set var="thumbUrl" value="${pageContext.request.contextPath}/uploads/products/${row.thumb}" />
                  </c:if>
                  <tr data-product-id="P-${p.productNo}" data-product-no="${p.productNo}">
                    <td>
                      <img class="seller-product-thumb"
                        src="${thumbUrl}"
                        alt="상품 썸네일" />
                    </td>
                    <td class="seller-product-name-cell">
                      <div class="seller-product-name"><c:out value="${p.productName}" /></div>
                      <div class="seller-product-subline">상품No: <c:out value="${p.productNo}" /></div>
                    </td>
                    <td><c:out value="${row.categoryName}" /></td>
                    <td>
					  <c:choose>
					    <c:when test="${p.productOriginPrice > p.productPrice}">
					      <strong><c:out value="${p.productOriginPrice}" />원</strong>
					    </c:when>
					    <c:otherwise>
					      <strong><c:out value="${p.productPrice}" />원</strong>
					    </c:otherwise>
					  </c:choose>
					</td>
					<td>
					  <c:choose>
					    <c:when test="${p.productOriginPrice > p.productPrice}">
					      <c:out value="${p.productPrice}" />원
					    </c:when>
					    <c:otherwise>-</c:otherwise>
					  </c:choose>
					</td>
                    <td><c:out value="${row.stock}" />개</td>
                    <td>
                      <c:choose>
                        <c:when test="${p.productState == 1}"><span class="seller-product-badge seller-product-badge--selling">판매중</span></c:when>
                        <c:when test="${p.productState == 0}"><span class="seller-product-badge seller-product-badge--soldout">품절</span></c:when>
                        <c:otherwise><span class="seller-product-badge seller-product-badge--hidden">숨김</span></c:otherwise>
                      </c:choose>
                    </td>
                    <td>
					  <c:choose>
					    <c:when test="${row.shortsCount > 0}">
					      <span class="seller-product-badge seller-product-badge--linked"><c:out value="${row.shortsCount}"/>개 연결됨</span>
					    </c:when>
					    <c:otherwise>
					      <span class="seller-product-txt--muted">미연결</span>
					    </c:otherwise>
					  </c:choose>
					</td>
                    <td class="seller-product-row-actions">
                      <button type="button" class="seller-mini-btn" data-action="edit" data-product-no="${p.productNo}">수정</button>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>

          <div class="seller-product-pagination" aria-label="페이지네이션">
			  <c:if test="${prev}">
			    <a href="?pageNum=${startPage - 1}&query=${filterQuery}&category=${filterCategory}&sale=${filterSale}&stock=${filterStock}" 
			       class="seller-product-page-btn">&lt;</a>
			  </c:if>
			  <c:forEach var="num" begin="${startPage}" end="${endPage}">
			    <a href="?pageNum=${num}&query=${filterQuery}&category=${filterCategory}&sale=${filterSale}&stock=${filterStock}" 
			       class="seller-product-page-btn ${pageNum == num ? 'active' : ''}">
			      ${num}
			    </a>
			  </c:forEach>
			  <c:if test="${next}">
			    <a href="?pageNum=${endPage + 1}&query=${filterQuery}&category=${filterCategory}&sale=${filterSale}&stock=${filterStock}" 
			       class="seller-product-page-btn">&gt;</a>
			  </c:if>
			</div>
        </section>

        <section class="seller-card seller-product-empty" aria-label="등록된 상품 없음" <c:if test="${productTotal != 0}">hidden</c:if>>
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

  <script src="${pageContext.request.contextPath}/js/seller/product-list.js"></script>
</body>
</html>

