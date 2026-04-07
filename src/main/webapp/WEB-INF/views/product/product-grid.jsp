<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<main class="product-content">
  <section class="product-list-section">
    <div class="product-grid" id="productGrid">

      <c:choose>
        <c:when test="${empty productList}">
          <div class="product-empty">
            <p>해당 조건의 상품이 없습니다.</p>
          </div>
        </c:when>

        <c:otherwise>
          <c:forEach var="p" items="${productList}">
            <article class="product-card"
                data-product-no="${p.productNo}"
                data-favorite-popular="${p.saleCount}"
                data-favorite-price="${p.productPrice}"
                onclick="location.href='${pageContext.request.contextPath}/product?action=detail&productNo=${p.productNo}'"
    			style="cursor:pointer;">

              <div class="product-thumb-wrap">
                <c:choose>
                  <c:when test="${not empty thumbnailMap[p.productNo]}">
                    <img class="product-thumb"
                         src="${pageContext.request.contextPath}/uploads/products/${thumbnailMap[p.productNo]}"
                         alt="${p.productName}"
                         loading="lazy" width="300" height="300">
                  </c:when>
                  <c:otherwise>
                    <div class="product-thumb placeholder">이미지 없음</div>
                  </c:otherwise>
                </c:choose>

                <%-- 찜 버튼 --%>
                <c:choose>
                  <c:when test="${wishSet != null && wishSet.contains(p.productNo)}">
                    <button type="button"
                            class="related-wish-btn product-grid-wish-btn is-active"
                            data-product-no="${p.productNo}"
                            aria-pressed="true" aria-label="찜 해제">
                      <span class="material-icons" aria-hidden="true">favorite</span>
                    </button>
                  </c:when>
                  <c:otherwise>
                    <button type="button"
                            class="related-wish-btn product-grid-wish-btn"
                            data-product-no="${p.productNo}"
                            aria-pressed="false" aria-label="찜하기">
                      <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
                    </button>
                  </c:otherwise>
                </c:choose>

                <%-- 뱃지 --%>
                <c:if test="${p.saleCount >= 50 || p.wishCount >= 30}">
                  <div class="product-badge-row">
                    <c:if test="${p.wishCount >= 30}">
                      <span class="product-badge product-badge--recommend">추천</span>
                    </c:if>
                    <c:if test="${p.saleCount >= 50}">
                      <span class="product-badge product-badge--popular">인기</span>
                    </c:if>
                  </div>
                </c:if>
              </div>

              <div class="product-body">
                <div class="brand-meta-row">
                  <p class="product-brand">${p.productBrand}</p>
                </div>
                <h3 class="product-name">${p.productName}</h3>
                <div class="product-price-row">
                  <span class="product-price">
                    <fmt:formatNumber value="${p.productPrice}" pattern="#,###"/>원
                  </span>
                  <c:if test="${p.productOriginPrice > p.productPrice}">
                    <span class="product-discount">
                      <fmt:formatNumber
                        value="${(1 - p.productPrice / p.productOriginPrice) * 100}"
                        pattern="#"/>% 할인
                    </span>
                  </c:if>
                </div>
              </div>

            </article>
          </c:forEach>
        </c:otherwise>
      </c:choose>

    </div>
  </section>
</main>