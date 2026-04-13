<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  if (request.getAttribute("sellerActiveMenu") == null) {
    request.setAttribute("sellerActiveMenu", "setting");
  }
  if (request.getAttribute("sellerPageTitle") == null) {
    request.setAttribute("sellerPageTitle", "설정");
  }
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>설정 | 온담 파트너</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-settings.css" />
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body class="seller-app" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-layout">
    <jsp:include page="/WEB-INF/views/seller/layout/seller-sidebar.jsp" />

    <div class="seller-main-area">
      <jsp:include page="/WEB-INF/views/seller/layout/seller-header.jsp" />

      <main class="seller-content seller-settings-page" aria-label="설정">
        <header class="seller-settings-head">
          <div>
            <h2 class="seller-settings-title">설정</h2>
            <p class="seller-settings-sub">스토어 정보와 배송 정책을 관리할 수 있어요</p>
          </div>
        </header>

        <c:if test="${vendorLoadError}">
          <p class="seller-settings-profile-msg seller-settings-profile-msg--err" role="alert">업체 정보를 불러올 수 없습니다. 잠시 후 다시 시도해 주세요.</p>
        </c:if>
        <c:if test="${param.save == 'fail'}">
          <p class="seller-settings-profile-msg seller-settings-profile-msg--err" role="alert">저장에 실패했습니다. 필수 항목을 확인한 뒤 다시 시도해 주세요.</p>
        </c:if>

        <c:if test="${not empty vendor}">
        <form id="sellerLogoUploadForm" action="${pageContext.request.contextPath}/seller/settings/logo" method="post" enctype="multipart/form-data" class="seller-settings-logo-form-hidden" aria-hidden="true"></form>

        <form id="sellerSettingsForm" class="seller-settings-form" autocomplete="off" method="post" action="${pageContext.request.contextPath}/seller/settings/save">
          <!-- 스토어 정보 -->
          <section class="seller-card seller-settings-section" aria-label="스토어 정보">
            <header class="seller-settings-section-head">
              <div>
                <h3 class="seller-settings-section-title">스토어 정보</h3>
                <p class="seller-settings-section-sub seller-settings-section-sub--gap-below">파트너와 주문 화면에 연결되는 기본 정보를 설정해 주세요</p>
              </div>
            </header>

            <div class="seller-settings-profile-panel" aria-label="스토어 프로필 사진">
              <div class="seller-settings-profile-avatar-wrap">
                <img id="sellerProfilePreview"
                  class="seller-settings-profile-avatar<c:if test="${not empty vendorLogoImgSrc}"> seller-settings-profile-avatar--visible</c:if>"
                  src="<c:choose><c:when test="${not empty vendorLogoImgSrc}"><c:out value="${vendorLogoImgSrc}" /></c:when><c:otherwise>data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7</c:otherwise></c:choose>"
                  alt="스토어 프로필"
                  width="96"
                  height="96"
                  decoding="async" />
                <div id="sellerProfilePlaceholder" class="seller-settings-profile-placeholder<c:if test="${not empty vendorLogoImgSrc}"> is-hidden</c:if>" aria-hidden="true">
                  <span class="material-icons-outlined" aria-hidden="true">storefront</span>
                </div>
              </div>
              <div class="seller-settings-profile-actions">
                <div class="seller-settings-profile-toolbar" role="group" aria-label="스토어 사진 업로드">
                  <label for="sellerLogoFile" class="seller-settings-btn seller-settings-btn--secondary seller-settings-profile-toolbar__pick">사진 선택</label>
                  <input class="seller-settings-file-input-hidden" type="file" id="sellerLogoFile" name="logoFile" form="sellerLogoUploadForm" accept="image/jpeg,image/png,image/gif,image/webp,.jpg,.jpeg,.png,.gif,.webp" />
                  <input type="text" class="seller-settings-input seller-settings-profile-filename" id="sellerLogoFileName" readonly="readonly"
                    value="<c:out value="${vendorLogoFile}" />"
                    placeholder="선택한 파일명이 여기에 표시됩니다" autocomplete="off" aria-label="선택된 파일명" />
                  <button type="submit" form="sellerLogoUploadForm" class="seller-settings-btn seller-settings-btn--primary seller-settings-profile-toolbar__save" id="sellerLogoSaveBtn">변경 저장</button>
                </div>
                <p class="seller-settings-profile-hint">JPG, PNG, GIF, WEBP · 권장 400×400px 이상</p>
                <c:if test="${param.logo == 'ok'}">
                  <p class="seller-settings-profile-msg seller-settings-profile-msg--ok" role="status">프로필 사진이 저장되었습니다.</p>
                </c:if>
                <c:if test="${param.logo == 'fail'}">
                  <p class="seller-settings-profile-msg seller-settings-profile-msg--err" role="alert">프로필 사진 저장에 실패했습니다. 잠시 후 다시 시도해 주세요.</p>
                </c:if>
                <c:if test="${param.logo == 'invalid'}">
                  <p class="seller-settings-profile-msg seller-settings-profile-msg--err" role="alert">JPG, PNG, GIF, WEBP 이미지 파일만 업로드할 수 있어요.</p>
                </c:if>
                <c:if test="${param.logo == 'empty'}">
                  <p class="seller-settings-profile-msg seller-settings-profile-msg--err" role="alert">파일을 선택한 뒤 저장해 주세요.</p>
                </c:if>
              </div>
            </div>

            <div class="seller-settings-grid">
              <div class="seller-settings-field">
                <label class="seller-settings-label" for="storeName">스토어명 <span class="req">*</span></label>
                <input class="seller-settings-input" id="storeName" name="storeName" type="text" value="<c:out value='${vendor.vendorName}' />" placeholder="스토어명을 입력해 주세요" />
                <p class="seller-settings-error" id="errStoreName" hidden>스토어명을 입력해 주세요.</p>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="managerName">담당자명 <span class="req">*</span></label>
                <input class="seller-settings-input" id="managerName" name="managerName" type="text" value="<c:out value='${managerName}' />" placeholder="담당자명을 입력해 주세요" />
                <p class="seller-settings-error" id="errManagerName" hidden>담당자명을 입력해 주세요.</p>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="csPhone">고객센터 연락처 <span class="req">*</span></label>
                <input class="seller-settings-input" id="csPhone" name="csPhone" type="text" value="<c:out value='${vendor.bizTel}' />" placeholder="고객센터 연락처를 입력해 주세요" />
                <p class="seller-settings-error" id="errCsPhone" hidden>고객센터 연락처를 입력해 주세요.</p>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="csEmail">고객센터 이메일 <span class="req">*</span></label>
                <input class="seller-settings-input" id="csEmail" name="csEmail" type="email" value="<c:out value='${vendor.contactEmail}' />" placeholder="고객센터 이메일을 입력해 주세요" />
                <p class="seller-settings-error" id="errCsEmail" hidden>이메일 형식을 확인해 주세요.</p>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="bizNo">사업자등록번호</label>
                <input class="seller-settings-input" id="bizNo" name="bizNo" type="text" value="<c:out value='${vendor.bizRegNo}' />" placeholder="사업자등록번호를 입력해 주세요" />
              </div>

              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="storeIntro">스토어 소개</label>
                <textarea class="seller-settings-textarea" id="storeIntro" name="storeIntro" rows="3" placeholder="스토어 소개를 입력해 주세요"><c:out value="${vendor.bizDescription}" /></textarea>
              </div>

              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="returnGuide">반품 / 교환 안내 문구</label>
                <textarea class="seller-settings-textarea" id="returnGuide" name="returnGuide" rows="3" placeholder="반품/교환 안내 문구를 입력해 주세요"><c:out value="${vendor.returnExchangeGuide}" /></textarea>
              </div>
            </div>
          </section>

          <!-- 출고/반품 주소 -->
          <section class="seller-card seller-settings-section" aria-label="출고 및 반품 주소">
            <header class="seller-settings-section-head">
              <div>
                <h3 class="seller-settings-section-title">출고 / 반품 주소</h3>
                <p class="seller-settings-section-sub">상품 발송과 반품에 사용할 주소를 관리해 주세요</p>
              </div>
              <div class="seller-settings-check-row">
                <label class="seller-settings-check">
                  <input type="checkbox" id="sameReturnAddr" name="sameReturnAddr" value="1" <c:if test="${sameReturnAddr}">checked="checked"</c:if> />
                  <span>반품지는 출고지와 같아요</span>
                </label>
              </div>
            </header>

            <div class="seller-settings-grid seller-settings-grid--addr">
              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="shipZip">출고지 우편번호</label>
                <div class="seller-settings-zip-row">
                  <input class="seller-settings-input seller-settings-input--zip" id="shipZip" name="shipZip" type="text" value="<c:out value='${shipZip}' />" placeholder="우편번호" inputmode="numeric" autocomplete="postal-code" readonly />
                  <button type="button" class="seller-settings-mini-btn" id="shipZipBtn">우편번호 찾기</button>
                </div>
              </div>
              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="shipAddr1">출고지 주소</label>
                <input class="seller-settings-input" id="shipAddr1" name="shipAddr1" type="text" value="<c:out value='${shipAddr1}' />" placeholder="출고지 주소를 입력해 주세요" autocomplete="street-address" readonly />
              </div>
              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="shipAddr2">출고지 상세주소</label>
                <input class="seller-settings-input" id="shipAddr2" name="shipAddr2" type="text" value="<c:out value='${shipAddr2}' />" placeholder="출고지 상세주소를 입력해 주세요" />
              </div>

              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="returnZip">반품지 우편번호</label>
                <div class="seller-settings-zip-row">
                  <input class="seller-settings-input seller-settings-input--zip" id="returnZip" name="returnZip" type="text" value="<c:out value='${returnZip}' />" placeholder="우편번호" inputmode="numeric" autocomplete="postal-code" readonly />
                  <button type="button" class="seller-settings-mini-btn" id="returnZipBtn">우편번호 찾기</button>
                </div>
              </div>
              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="returnAddr1">반품지 주소</label>
                <input class="seller-settings-input" id="returnAddr1" name="returnAddr1" type="text" value="<c:out value='${returnAddr1}' />" placeholder="반품지 주소를 입력해 주세요" autocomplete="street-address" readonly />
              </div>
              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="returnAddr2">반품지 상세주소</label>
                <input class="seller-settings-input" id="returnAddr2" name="returnAddr2" type="text" value="<c:out value='${returnAddr2}' />" placeholder="반품지 상세주소를 입력해 주세요" />
              </div>
            </div>
          </section>

          <!-- 배송 정책 -->
          <section class="seller-card seller-settings-section" aria-label="배송 정책">
            <header class="seller-settings-section-head">
              <div>
                <h3 class="seller-settings-section-title">배송 정책</h3>
                <p class="seller-settings-section-sub">주문 화면과 상품 상세에 보여질 배송 기준을 설정해 주세요</p>
              </div>
            </header>

            <div class="seller-settings-grid">
              <div class="seller-settings-field">
                <label class="seller-settings-label" for="shipFee">기본 배송비 <span class="req">*</span></label>
                <div class="seller-settings-unit-row">
                  <input class="seller-settings-input" id="shipFee" name="shipFee" type="number" min="0" value="<c:choose><c:when test="${empty vendor.shipFee}">0</c:when><c:otherwise><c:out value="${vendor.shipFee}" /></c:otherwise></c:choose>" placeholder="기본 배송비를 입력해 주세요" />
                  <span class="seller-settings-unit">원</span>
                </div>
                <p class="seller-settings-error" id="errShipFee" hidden>기본 배송비를 입력해 주세요.</p>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="freeOver">무료배송 기준 금액 <span class="req">*</span></label>
                <div class="seller-settings-unit-row">
                  <input class="seller-settings-input" id="freeOver" name="freeOver" type="number" min="0" value="<c:choose><c:when test="${empty vendor.freeShipMin}">0</c:when><c:otherwise><c:out value="${vendor.freeShipMin}" /></c:otherwise></c:choose>" placeholder="무료배송 기준 금액을 입력해 주세요" />
                  <span class="seller-settings-unit">원</span>
                </div>
                <p class="seller-settings-error" id="errFreeOver" hidden>무료배송 기준 금액을 입력해 주세요.</p>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="prepDays">평균 배송 준비 기간</label>
                <input class="seller-settings-input" id="prepDays" name="prepDays" type="text" value="<c:out value='${vendor.prepDays}' />" placeholder="평균 배송 준비 기간을 입력해 주세요" />
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="courier">택배사 기본값</label>
                <select class="seller-settings-select" id="courier" name="courier">
                  <option value="CJ대한통운" <c:if test="${vendor.defaultCourier eq 'CJ대한통운'}">selected="selected"</c:if>>CJ대한통운</option>
                  <option value="롯데택배" <c:if test="${vendor.defaultCourier eq '롯데택배'}">selected="selected"</c:if>>롯데택배</option>
                  <option value="한진택배" <c:if test="${vendor.defaultCourier eq '한진택배'}">selected="selected"</c:if>>한진택배</option>
                  <option value="우체국택배" <c:if test="${vendor.defaultCourier eq '우체국택배'}">selected="selected"</c:if>>우체국택배</option>
                </select>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="islandExtra">도서산간 추가 배송비</label>
                <div class="seller-settings-unit-row">
                  <input class="seller-settings-input" id="islandExtra" name="islandExtra" type="number" min="0" value="<c:choose><c:when test="${empty vendor.islandExtra}">0</c:when><c:otherwise><c:out value="${vendor.islandExtra}" /></c:otherwise></c:choose>" placeholder="도서산간 추가 배송비를 입력해 주세요" />
                  <span class="seller-settings-unit">원</span>
                </div>
              </div>

              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="shipNotice">배송 안내 문구</label>
                <textarea class="seller-settings-textarea" id="shipNotice" name="shipNotice" rows="3" placeholder="주문 후 1~2일 내 출고됩니다"><c:out value="${vendor.shipNotice}" /></textarea>
              </div>
            </div>
          </section>

          <!-- 안내 문구 -->
          <section class="seller-card seller-settings-section" aria-label="안내 문구">
            <header class="seller-settings-section-head">
              <div>
                <h3 class="seller-settings-section-title">안내 문구</h3>
                <p class="seller-settings-section-sub">고객에게 보여줄 기본 안내 문구를 설정해 주세요</p>
              </div>
            </header>

            <div class="seller-settings-grid seller-settings-grid--msg">
              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="delayMsg">배송 지연 안내 문구</label>
                <textarea class="seller-settings-textarea seller-settings-textarea--lg" id="delayMsg" name="delayMsg" rows="4"
                  placeholder="배송이 지연될 경우 안내할 문구를 입력해 주세요"><c:out value="${vendor.delayNotice}" /></textarea>
              </div>

              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="giftMsg">선물 주문 안내 문구</label>
                <textarea class="seller-settings-textarea seller-settings-textarea--lg" id="giftMsg" name="giftMsg" rows="4"
                  placeholder="선물 주문 시 보여줄 안내 문구를 입력해 주세요"><c:out value="${vendor.giftNotice}" /></textarea>
              </div>

              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="exchangeMsg">교환/반품 안내 문구</label>
                <textarea class="seller-settings-textarea seller-settings-textarea--lg" id="exchangeMsg" name="exchangeMsg" rows="4"
                  placeholder="교환/반품 시 보여줄 안내 문구를 입력해 주세요"><c:out value="${vendor.exchangeNotice}" /></textarea>
              </div>
            </div>
          </section>

          <!-- 하단 액션 -->
          <div class="seller-settings-form-actions">
            <div class="seller-settings-form-actions__left">
              <c:if test="${param.save == 'ok'}">
                <p class="seller-settings-profile-msg seller-settings-profile-msg--ok seller-settings-form-actions__msg" role="status">설정이 저장되었습니다.</p>
              </c:if>
            </div>
            <div class="seller-settings-form-actions__btns">
              <button type="button" class="seller-settings-btn" id="settingsResetBtn">초기화</button>
              <button type="submit" class="seller-settings-btn seller-settings-btn--primary" id="settingsSaveBtn">저장하기</button>
            </div>
          </div>
        </form>
        </c:if>
      </main>

      <jsp:include page="/WEB-INF/views/seller/layout/seller-footer.jsp" />
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/seller/settings.js"></script>
</body>
</html>

