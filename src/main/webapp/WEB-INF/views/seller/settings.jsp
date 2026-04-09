<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  request.setAttribute("sellerActiveMenu", "setting");
  request.setAttribute("sellerPageTitle", "설정");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>설정 | 온담 판매자센터</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-settings.css" />
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

        <form id="sellerSettingsForm" class="seller-settings-form" autocomplete="off">
          <!-- 스토어 정보 -->
          <section class="seller-card seller-settings-section" aria-label="스토어 정보">
            <header class="seller-settings-section-head">
              <div>
                <h3 class="seller-settings-section-title">스토어 정보</h3>
                <p class="seller-settings-section-sub">판매자센터와 주문 화면에 연결되는 기본 정보를 설정해 주세요</p>
              </div>
            </header>

            <div class="seller-settings-grid">
              <div class="seller-settings-field">
                <label class="seller-settings-label" for="storeName">스토어명 <span class="req">*</span></label>
                <input class="seller-settings-input" id="storeName" name="storeName" type="text" value="온담스토어" placeholder="스토어명을 입력해 주세요" />
                <p class="seller-settings-error" id="errStoreName" hidden>스토어명을 입력해 주세요.</p>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="managerName">담당자명 <span class="req">*</span></label>
                <input class="seller-settings-input" id="managerName" name="managerName" type="text" value="김지현" placeholder="담당자명을 입력해 주세요" />
                <p class="seller-settings-error" id="errManagerName" hidden>담당자명을 입력해 주세요.</p>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="csPhone">고객센터 연락처 <span class="req">*</span></label>
                <input class="seller-settings-input" id="csPhone" name="csPhone" type="text" value="051-123-4567" placeholder="고객센터 연락처를 입력해 주세요" />
                <p class="seller-settings-error" id="errCsPhone" hidden>고객센터 연락처를 입력해 주세요.</p>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="csEmail">고객센터 이메일 <span class="req">*</span></label>
                <input class="seller-settings-input" id="csEmail" name="csEmail" type="email" value="seller@ondam.com" placeholder="고객센터 이메일을 입력해 주세요" />
                <p class="seller-settings-error" id="errCsEmail" hidden>이메일 형식을 확인해 주세요.</p>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="bizNo">사업자등록번호</label>
                <input class="seller-settings-input" id="bizNo" name="bizNo" type="text" value="123-45-67890" placeholder="사업자등록번호를 입력해 주세요" />
              </div>

              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="storeIntro">스토어 소개</label>
                <textarea class="seller-settings-textarea" id="storeIntro" name="storeIntro" rows="3" placeholder="스토어 소개를 입력해 주세요">고령층 고객이 편하게 입을 수 있는 옷을 제안하는 온담스토어입니다.</textarea>
              </div>

              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="returnGuide">반품 / 교환 안내 문구</label>
                <textarea class="seller-settings-textarea" id="returnGuide" name="returnGuide" rows="3" placeholder="반품/교환 안내 문구를 입력해 주세요">상품 수령 후 7일 이내 교환/반품이 가능하며, 상품 훼손 시 제한될 수 있습니다.</textarea>
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
                  <input type="checkbox" id="sameReturnAddr" />
                  <span>반품지는 출고지와 같아요</span>
                </label>
              </div>
            </header>

            <div class="seller-settings-grid seller-settings-grid--addr">
              <div class="seller-settings-field">
                <label class="seller-settings-label" for="shipZip">출고지 우편번호</label>
                <input class="seller-settings-input" id="shipZip" name="shipZip" type="text" value="47290" placeholder="출고지 우편번호" />
              </div>
              <div class="seller-settings-field seller-settings-field--span2">
                <label class="seller-settings-label" for="shipAddr1">출고지 주소</label>
                <input class="seller-settings-input" id="shipAddr1" name="shipAddr1" type="text" value="부산광역시 부산진구 가야대로 123" placeholder="출고지 주소를 입력해 주세요" />
              </div>
              <div class="seller-settings-field seller-settings-field--span2">
                <label class="seller-settings-label" for="shipAddr2">출고지 상세주소</label>
                <input class="seller-settings-input" id="shipAddr2" name="shipAddr2" type="text" value="101호" placeholder="출고지 상세주소를 입력해 주세요" />
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="returnZip">반품지 우편번호</label>
                <input class="seller-settings-input" id="returnZip" name="returnZip" type="text" value="47290" placeholder="반품지 우편번호" />
              </div>
              <div class="seller-settings-field seller-settings-field--span2">
                <label class="seller-settings-label" for="returnAddr1">반품지 주소</label>
                <input class="seller-settings-input" id="returnAddr1" name="returnAddr1" type="text" value="부산광역시 부산진구 가야대로 123" placeholder="반품지 주소를 입력해 주세요" />
              </div>
              <div class="seller-settings-field seller-settings-field--span2">
                <label class="seller-settings-label" for="returnAddr2">반품지 상세주소</label>
                <input class="seller-settings-input" id="returnAddr2" name="returnAddr2" type="text" value="101호" placeholder="반품지 상세주소를 입력해 주세요" />
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
                  <input class="seller-settings-input" id="shipFee" name="shipFee" type="number" min="0" value="3000" placeholder="기본 배송비를 입력해 주세요" />
                  <span class="seller-settings-unit">원</span>
                </div>
                <p class="seller-settings-error" id="errShipFee" hidden>기본 배송비를 입력해 주세요.</p>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="freeOver">무료배송 기준 금액 <span class="req">*</span></label>
                <div class="seller-settings-unit-row">
                  <input class="seller-settings-input" id="freeOver" name="freeOver" type="number" min="0" value="50000" placeholder="무료배송 기준 금액을 입력해 주세요" />
                  <span class="seller-settings-unit">원</span>
                </div>
                <p class="seller-settings-error" id="errFreeOver" hidden>무료배송 기준 금액을 입력해 주세요.</p>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="prepDays">평균 배송 준비 기간</label>
                <input class="seller-settings-input" id="prepDays" name="prepDays" type="text" value="1~2일" placeholder="평균 배송 준비 기간을 입력해 주세요" />
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="courier">택배사 기본값</label>
                <select class="seller-settings-select" id="courier" name="courier">
                  <option>CJ대한통운</option>
                  <option>롯데택배</option>
                  <option>한진택배</option>
                  <option>우체국택배</option>
                </select>
              </div>

              <div class="seller-settings-field">
                <label class="seller-settings-label" for="islandExtra">도서산간 추가 배송비</label>
                <div class="seller-settings-unit-row">
                  <input class="seller-settings-input" id="islandExtra" name="islandExtra" type="number" min="0" value="3000" placeholder="도서산간 추가 배송비를 입력해 주세요" />
                  <span class="seller-settings-unit">원</span>
                </div>
              </div>

              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="shipNotice">배송 안내 문구</label>
                <textarea class="seller-settings-textarea" id="shipNotice" name="shipNotice" rows="3" placeholder="주문 후 1~2일 내 출고됩니다">주문 후 1~2일 내 출고됩니다.</textarea>
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
                  placeholder="배송이 지연될 경우 안내할 문구를 입력해 주세요">물량 증가로 배송이 지연될 수 있어요. 최대한 빠르게 준비해 발송하겠습니다.</textarea>
              </div>

              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="giftMsg">선물 주문 안내 문구</label>
                <textarea class="seller-settings-textarea seller-settings-textarea--lg" id="giftMsg" name="giftMsg" rows="4"
                  placeholder="선물 주문 시 보여줄 안내 문구를 입력해 주세요">선물 주문은 수령인 배송지 기준으로 안전하게 포장해 발송됩니다.</textarea>
              </div>

              <div class="seller-settings-field seller-settings-field--full">
                <label class="seller-settings-label" for="exchangeMsg">교환/반품 안내 문구</label>
                <textarea class="seller-settings-textarea seller-settings-textarea--lg" id="exchangeMsg" name="exchangeMsg" rows="4"
                  placeholder="교환/반품 시 보여줄 안내 문구를 입력해 주세요">교환/반품 접수 후 기사님 방문 수거로 진행되며, 상품 상태 확인 후 처리됩니다.</textarea>
              </div>
            </div>
          </section>

          <!-- 하단 액션 -->
          <div class="seller-settings-form-actions">
            <button type="button" class="seller-settings-btn" id="settingsResetBtn">초기화</button>
            <button type="submit" class="seller-settings-btn seller-settings-btn--primary" id="settingsSaveBtn">저장하기</button>
          </div>
        </form>
      </main>

      <jsp:include page="/WEB-INF/views/seller/layout/seller-footer.jsp" />
    </div>
  </div>

  <script>
    (function () {
      var notifyBtn = document.getElementById('sellerHeaderNotifyBtn');
      var logoutBtn = document.getElementById('sellerHeaderLogoutBtn');
      if (notifyBtn) notifyBtn.addEventListener('click', function () { alert('알림 기능은 아직 준비 중이에요.'); });
      if (logoutBtn) logoutBtn.addEventListener('click', function () { alert('로그아웃은 아직 연동되지 않았어요. (더미)'); });
    })();
  </script>
  <script src="${pageContext.request.contextPath}/js/seller/settings.js"></script>
</body>
</html>

