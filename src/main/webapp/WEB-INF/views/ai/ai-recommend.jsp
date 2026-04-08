<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
  request.setAttribute("bottomNav", "ai");

  String ctx = request.getContextPath();
  String[] phrases = {
      "☀️ 화창한 날씨에 잘 어울리는 옷이에요",
      "🧸 편하게 입기 좋아 보여서 골라봤어요",
      "✨ 요즘 자주 찾으시는 느낌이라 준비해봤어요",
      "🚶‍♂️ 가볍게 외출할 때 입기 좋아요",
      "🎁 오늘 김가빈님께 선물해보는 건 어떠세요?"
  };
  String[] brands = {"온담", "A브랜드", "B브랜드", "C브랜드", "선물하우스"};
  String[] names = {
      "편안한 기모 맨투맨",
      "부드러운 니트 풀오버",
      "데일리 스트라이프 티셔츠",
      "면 소재 와이드 팬츠",
      "프리미엄 양말 선물 세트"
  };
  String[] prices = {"39,000원", "52,000원", "23,200원", "48,500원", "28,000원"};
  String[] wishCounts = {"1.7만", "8,234", "2.1만", "320", "1.2만"};
  String productImg = ctx + "/images/category/type-top-knit.jpg";

  List<Map<String, String>> aiRecDummyProducts = new ArrayList<>();
  for (int i = 0; i < 5; i++) {
    Map<String, String> p = new HashMap<>();
    p.put("phrase", phrases[i]);
    p.put("brand", brands[i]);
    p.put("name", names[i]);
    p.put("img", productImg);
    p.put("href", ctx + "/product?action=detail&productNo=" + (i + 1));
    p.put("brandHref", ctx + "/main");
    p.put("wishCount", wishCounts[i]);
    boolean hasDiscount = (i % 2 == 0);
    p.put("hasDiscount", hasDiscount ? "true" : "false");
    if (hasDiscount) {
      p.put("originalPrice", "78,000원");
      p.put("salePrice", "39,000원");
      p.put("discountLabel", "20% 할인");
    } else {
      p.put("salePrice", prices[i]);
    }
    p.put("wishActive", (i % 2 == 0) ? "true" : "false");
    aiRecDummyProducts.add(p);
  }
  request.setAttribute("aiRecDummyProducts", aiRecDummyProducts);
%>
<!DOCTYPE html>
<html lang="ko" class="ai-rec-page">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <!-- 하루에 처음 해당 페이지 접속 시 ai-recommend-modal.jsp 모달 뜸 / ai api 불러올 동안 바운스 모달 유지
  매일 10개씩? 개수 상관없음 연결하는 분이 정해주길 바람!! 추천 상품마다 카드 위에 추천이유 멘트(1줄?2줄?) 표시됨 -->
  <title>오늘의 추천 상품 | 온담</title>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,1,0" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product-detail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/poke.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/share-modal.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/inquiry-write.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ai-recommend-modal.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ai-recommend.css">
</head>
<body class="ai-rec-page" data-context-path="${pageContext.request.contextPath}">
  <div id="option-toast" class="option-toast hidden" role="alert" aria-live="assertive" aria-hidden="true">
    <span class="material-icons option-toast__icon" aria-hidden="true">error</span>
    <span class="option-toast__text">먼저 색상과 사이즈를 골라주세요</span>
  </div>

  <div class="detail-shell">
  <jsp:include page="ai-recommend-modal.jsp"/>

  <div class="app-shell">
    <div class="top-header-cluster">
      <jsp:include page="../layout/header.jsp"/>
    </div>

    <main class="page-wrap ai-rec-main ai-rec-main--intro-pending" id="aiRecMain">
      <div id="aiRecContent" class="ai-rec-content ai-rec-content--hidden" aria-hidden="true">
      <header class="ai-rec-page-head">
        <h1 class="ai-rec-page-head__title">오늘의 추천 상품</h1>
        <p class="ai-rec-page-head__line">마음에 드는 상품 카드를 클릭하여</p>
        <p class="ai-rec-page-head__line">자세한 정보를 확인해보세요!</p>
        <p class="ai-rec-page-head__line ai-rec-page-head__line--sub">추천 상품은 매일 밤 12시에 초기화돼요</p>
      </header>

      <ul class="ai-rec-list" id="aiRecList">
        <c:forEach var="item" items="${aiRecDummyProducts}">
          <li>
            <article class="ai-rec-card product-card" data-detail-href="${item.href}" role="article" aria-label="${item.name} 상품 카드, 눌러 상세 보기">
              <p class="ai-rec-card__phrase">${item.phrase}</p>
              <div class="product-thumb-wrap ai-rec-thumb-wrap">
                <div class="ai-rec-thumb-link" aria-hidden="true">
                  <img class="ai-rec-thumb-img" src="${item.img}" alt="" width="800" height="800" loading="lazy" decoding="async">
                </div>
                <c:choose>
                  <c:when test="${item.wishActive == 'true'}">
                    <button type="button" class="related-wish-btn product-grid-wish-btn is-active" aria-pressed="true" aria-label="찜 해제">
                      <span class="material-icons" aria-hidden="true">favorite</span>
                    </button>
                  </c:when>
                  <c:otherwise>
                    <button type="button" class="related-wish-btn product-grid-wish-btn" aria-pressed="false" aria-label="찜하기">
                      <span class="material-icons-outlined" aria-hidden="true">favorite_border</span>
                    </button>
                  </c:otherwise>
                </c:choose>
              </div>
              <div class="product-body ai-rec-product-body">
                <div class="brand-meta-row">
                  <a href="${item.brandHref}" class="ai-rec-brand-link">${item.brand}<span class="material-icons" aria-hidden="true">chevron_right</span></a>
                  <div class="brand-wish-stat" aria-label="찜 ${item.wishCount}">
                    <span class="brand-wish-stat__icon" aria-hidden="true"><span class="material-icons">favorite</span></span>
                    <span class="brand-wish-stat__num">${item.wishCount}</span>
                  </div>
                </div>
                <h3 class="product-name">${item.name}</h3>
                <c:choose>
                  <c:when test="${item.hasDiscount == 'true'}">
                    <p class="ai-rec-origin-price">${item.originalPrice}</p>
                    <div class="ai-rec-price-row">
                      <span class="ai-rec-sale-price">${item.salePrice}</span>
                      <span class="ai-rec-discount-rate">${item.discountLabel}</span>
                    </div>
                  </c:when>
                  <c:otherwise>
                    <div class="product-price-row ai-rec-price-row--single">
                      <span class="product-price">${item.salePrice}</span>
                    </div>
                  </c:otherwise>
                </c:choose>
              </div>
              <div class="ai-rec-card__actions">
                <button type="button" class="detail-bottom-btn secondary" data-open-detail-option-sheet>장바구니 담기</button>
                <button type="button" class="detail-bottom-btn primary" data-open-detail-option-sheet>구매하기</button>
              </div>
            </article>
          </li>
        </c:forEach>
      </ul>
      </div>
    </main>

    <jsp:include page="../layout/bottomNav.jsp"/>
  </div>

    <jsp:include page="/WEB-INF/views/product/product-detail-sheet.jsp"/>
    <jsp:include page="/WEB-INF/views/poke/poke-modal.jsp"/>
    <jsp:include page="/WEB-INF/views/gift/gift-modal.jsp"/>
    <jsp:include page="/WEB-INF/views/product/share-modal.jsp"/>
  </div>

  <script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
  <script src="${pageContext.request.contextPath}/js/product-detail.js"></script>
  <script>
    (function () {
      "use strict";
      document.addEventListener("DOMContentLoaded", function () {
        var intro = document.getElementById("aiRecModalIntro");
        var loading = document.getElementById("aiRecModalLoading");
        var introBtn = document.getElementById("aiRecModalIntroBtn");
        var content = document.getElementById("aiRecContent");
        var mainEl = document.getElementById("aiRecMain");
        if (!intro || !loading || !introBtn || !content) return;

        document.body.style.overflow = "hidden";

        introBtn.addEventListener("click", function () {
          intro.classList.add("hidden");
          intro.setAttribute("aria-hidden", "true");
          loading.classList.remove("hidden");
          loading.removeAttribute("aria-hidden");

          setTimeout(function () {
            loading.classList.add("hidden");
            loading.setAttribute("aria-hidden", "true");
            content.classList.remove("ai-rec-content--hidden");
            content.setAttribute("aria-hidden", "false");
            if (mainEl) mainEl.classList.remove("ai-rec-main--intro-pending");
            document.body.style.overflow = "";
          }, 2000);
        });
      });
    })();
  </script>
  <script>
    (function () {
      "use strict";
      var main = document.getElementById("aiRecMain");
      if (!main) return;

      main.addEventListener("click", function (e) {
        var wishBtn = e.target.closest(".related-wish-btn.product-grid-wish-btn");
        if (wishBtn && main.contains(wishBtn)) {
          e.preventDefault();
          e.stopPropagation();

          var on = !wishBtn.classList.contains("is-active");
          wishBtn.classList.toggle("is-active", on);
          wishBtn.setAttribute("aria-pressed", on ? "true" : "false");
          wishBtn.setAttribute("aria-label", on ? "찜 해제" : "찜하기");

          var icon = wishBtn.querySelector("span.material-icons-outlined, span.material-icons");
          if (icon) {
            if (on) {
              icon.className = "material-icons";
              icon.textContent = "favorite";
            } else {
              icon.className = "material-icons-outlined";
              icon.textContent = "favorite_border";
            }
          }
          return;
        }

        if (e.target.closest("a.ai-rec-brand-link")) return;
        if (e.target.closest("button")) return;
        if (e.target.closest("[data-open-detail-option-sheet]")) return;

        var card = e.target.closest(".ai-rec-card[data-detail-href]");
        if (!card || !main.contains(card)) return;
        var href = card.getAttribute("data-detail-href");
        if (href) {
          window.location.href = href;
        }
      });
    })();
  </script>
</body>
</html>
