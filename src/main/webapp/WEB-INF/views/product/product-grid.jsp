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
            <article class="product-card" data-product-no="${p.productNo}">

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
              </div>

              <div class="product-body">
                <p class="product-brand">${p.productBrand}</p>
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