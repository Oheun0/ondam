<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  request.setAttribute("sellerActiveMenu", "coupon");
  request.setAttribute("sellerPageTitle", "쿠폰 관리");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>쿠폰 관리 | 온담 파트너</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-coupon.css" />
</head>
<body class="seller-app" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-layout">
    <jsp:include page="/WEB-INF/views/seller/layout/seller-sidebar.jsp" />

    <div class="seller-main-area">
      <jsp:include page="/WEB-INF/views/seller/layout/seller-header.jsp" />

      <main class="seller-content seller-coupon-page" aria-label="쿠폰 관리">
        <header class="seller-coupon-head">
          <div>
            <h2 class="seller-coupon-title">쿠폰 관리</h2>
            <p class="seller-coupon-sub">할인 쿠폰을 생성하고 사용 현황을 확인할 수 있어요</p>
          </div>
        </header>

        <section class="seller-coupon-summary" aria-label="요약">
          <div class="seller-coupon-summary-grid">
            <div class="seller-coupon-summary-card">
              <div class="seller-coupon-summary-label">전체 쿠폰</div>
              <div class="seller-coupon-summary-value">12<span class="seller-coupon-summary-unit">개</span></div>
            </div>
            <div class="seller-coupon-summary-card">
              <div class="seller-coupon-summary-label">진행 중</div>
              <div class="seller-coupon-summary-value">5<span class="seller-coupon-summary-unit">개</span></div>
            </div>
            <div class="seller-coupon-summary-card">
              <div class="seller-coupon-summary-label">사용 완료</div>
              <div class="seller-coupon-summary-value">3<span class="seller-coupon-summary-unit">개</span></div>
            </div>
            <div class="seller-coupon-summary-card">
              <div class="seller-coupon-summary-label">만료</div>
              <div class="seller-coupon-summary-value">4<span class="seller-coupon-summary-unit">개</span></div>
            </div>
          </div>
        </section>

        <section class="seller-card seller-coupon-create" aria-label="새 쿠폰 생성">
          <div class="seller-coupon-create__head">
            <div>
              <h3 class="seller-coupon-section-title">새 쿠폰 생성</h3>
              <p class="seller-coupon-section-sub">할인 방식과 기간을 설정해 쿠폰을 등록해 주세요</p>
            </div>
          </div>

          <form class="seller-coupon-form" id="couponCreateForm" autocomplete="off">
            <div class="seller-coupon-grid">
              <div class="seller-coupon-field">
                <label class="seller-coupon-label" for="couponName">쿠폰명 <span class="req">*</span></label>
                <input class="seller-coupon-input" id="couponName" name="couponName" type="text"
                  placeholder="예: 봄맞이 기획전 할인 쿠폰" />
                <p class="seller-coupon-error" id="errName" hidden>쿠폰명을 입력해 주세요.</p>
              </div>

              <div class="seller-coupon-field">
                <label class="seller-coupon-label" for="discountType">할인 방식 <span class="req">*</span></label>
                <select class="seller-coupon-select" id="discountType" name="discountType">
                  <option value="rate">할인율 (%)</option>
                  <option value="amount">정액 할인 (원)</option>
                </select>
              </div>

              <div class="seller-coupon-field">
                <label class="seller-coupon-label" for="discountValue">할인값 <span class="req">*</span></label>
                <div class="seller-coupon-unit-row">
                  <input class="seller-coupon-input" id="discountValue" name="discountValue" type="number" min="0"
                    placeholder="할인율을 입력해 주세요" />
                  <span class="seller-coupon-unit" id="discountUnit">%</span>
                </div>
                <p class="seller-coupon-error" id="errDiscount" hidden>할인값을 입력해 주세요.</p>
              </div>

              <div class="seller-coupon-field">
                <label class="seller-coupon-label" for="minOrderAmount">최소 주문 금액 <span class="req">*</span></label>
                <div class="seller-coupon-unit-row">
                  <input class="seller-coupon-input" id="minOrderAmount" name="minOrderAmount" type="number" min="0" placeholder="예: 20000" />
                  <span class="seller-coupon-unit">원</span>
                </div>
                <p class="seller-coupon-help">주문 금액이 이 금액 이상일 때 사용 가능</p>
              </div>

              <div class="seller-coupon-field">
                <label class="seller-coupon-label" for="startDate">유효 시작일 <span class="req">*</span></label>
                <input class="seller-coupon-input" id="startDate" name="startDate" type="date" />
              </div>

              <div class="seller-coupon-field">
                <label class="seller-coupon-label" for="endDate">유효 종료일 <span class="req">*</span></label>
                <input class="seller-coupon-input" id="endDate" name="endDate" type="date" />
                <p class="seller-coupon-error" id="errDate" hidden>종료일을 다시 확인해 주세요.</p>
              </div>

              <div class="seller-coupon-field">
                <label class="seller-coupon-label" for="targetType">적용 대상 (선택)</label>
                <select class="seller-coupon-select" id="targetType" name="targetType">
                  <option value="all">전체 상품</option>
                  <option value="top">상의</option>
                  <option value="bottom">하의</option>
                  <option value="outer">아우터</option>
                  <option value="shorts">쇼츠 연결 상품</option>
                  <option value="event">특정 기획전 상품</option>
                </select>
                <p class="seller-coupon-help">현재는 더미 UI로만 동작해요</p>
              </div>

              <div class="seller-coupon-field seller-coupon-field--full">
                <label class="seller-coupon-label" for="couponDesc">쿠폰 설명 (선택)</label>
                <textarea class="seller-coupon-textarea" id="couponDesc" name="couponDesc" rows="3"
                  placeholder="예: 봄맞이 기획전 참여 브랜드 상품에 적용 가능"></textarea>
              </div>
            </div>

            <div class="seller-coupon-form-actions">
              <button type="button" class="seller-coupon-btn" id="couponResetBtn">초기화</button>
              <button type="submit" class="seller-coupon-btn seller-coupon-btn--primary" id="couponCreateBtn">쿠폰 생성</button>
            </div>
            <p class="seller-coupon-note">쿠폰 생성/발급/삭제는 더미 동작이며 실제 저장은 아직 연동되지 않았어요.</p>
          </form>
        </section>

        <section class="seller-card seller-coupon-table-wrap" aria-label="쿠폰 목록">
          <div class="seller-coupon-table-head">
            <div>
              <h3 class="seller-coupon-section-title">등록된 쿠폰</h3>
              <p class="seller-coupon-section-sub">사용 현황과 만료 상태를 확인할 수 있어요</p>
            </div>
          </div>

          <div class="seller-coupon-table-scroll">
            <table class="seller-coupon-table">
              <thead>
              <tr>
                <th>쿠폰명</th>
                <th>할인</th>
                <th>최소 주문 금액</th>
                <th>적용 대상</th>
                <th>유효 기간</th>
                <th>사용 현황</th>
                <th>상태</th>
                <th>관리</th>
              </tr>
              </thead>
              <tbody>
              <tr data-coupon-id="C-0001" data-status="active">
                <td class="name">
                  <div class="seller-coupon-name">봄맞이 기획전 할인 쿠폰</div>
                  <div class="seller-coupon-desc">기획전 참여 상품에 적용 (더미)</div>
                </td>
                <td>할인율 <strong>10%</strong></td>
                <td>20,000원</td>
                <td>전체 상품</td>
                <td>2026.04.01 ~ 2026.04.30</td>
                <td>
                  <div class="seller-coupon-usage">
                    <div class="seller-coupon-usage-text">사용 14 / 발급 50</div>
                    <div class="seller-coupon-bar" aria-hidden="true">
                      <span class="seller-coupon-bar__fill" style="width:28%"></span>
                    </div>
                  </div>
                </td>
                <td><span class="seller-coupon-badge seller-coupon-badge--active">진행 중</span></td>
                <td class="actions">
                  <button type="button" class="seller-coupon-mini" data-action="edit">수정</button>
                  <button type="button" class="seller-coupon-mini seller-coupon-mini--danger" data-action="end">종료</button>
                </td>
              </tr>

              <tr data-coupon-id="C-0002" data-status="done">
                <td class="name">
                  <div class="seller-coupon-name">신규 회원 전용 쿠폰</div>
                  <div class="seller-coupon-desc">첫 구매 고객 대상 (더미)</div>
                </td>
                <td>정액 <strong>3,000원</strong></td>
                <td>15,000원</td>
                <td>전체 상품</td>
                <td>2026.03.01 ~ 2026.03.31</td>
                <td>
                  <div class="seller-coupon-usage">
                    <div class="seller-coupon-usage-text">사용 50 / 발급 50</div>
                    <div class="seller-coupon-bar" aria-hidden="true">
                      <span class="seller-coupon-bar__fill" style="width:100%"></span>
                    </div>
                  </div>
                </td>
                <td><span class="seller-coupon-badge seller-coupon-badge--done">사용 완료</span></td>
                <td class="actions">
                  <button type="button" class="seller-coupon-mini" data-action="view">보기</button>
                </td>
              </tr>

              <tr data-coupon-id="C-0003" data-status="expired">
                <td class="name">
                  <div class="seller-coupon-name">가을 특가 쿠폰</div>
                  <div class="seller-coupon-desc">아우터 카테고리 적용 (더미)</div>
                </td>
                <td>할인율 <strong>15%</strong></td>
                <td>30,000원</td>
                <td>아우터</td>
                <td>2026.02.01 ~ 2026.02.28</td>
                <td>
                  <div class="seller-coupon-usage">
                    <div class="seller-coupon-usage-text">사용 5 / 발급 20</div>
                    <div class="seller-coupon-bar" aria-hidden="true">
                      <span class="seller-coupon-bar__fill" style="width:25%"></span>
                    </div>
                  </div>
                </td>
                <td><span class="seller-coupon-badge seller-coupon-badge--expired">만료</span></td>
                <td class="actions">
                  <button type="button" class="seller-coupon-mini" data-action="view">보기</button>
                </td>
              </tr>

              <tr data-coupon-id="C-0004" data-status="ended">
                <td class="name">
                  <div class="seller-coupon-name">쇼츠 연결 상품 할인 쿠폰</div>
                  <div class="seller-coupon-desc">쇼츠 링크 상품에만 적용 (더미)</div>
                </td>
                <td>정액 <strong>2,000원</strong></td>
                <td>10,000원</td>
                <td>쇼츠 연결 상품</td>
                <td>2026.04.05 ~ 2026.04.20</td>
                <td>
                  <div class="seller-coupon-usage">
                    <div class="seller-coupon-usage-text">사용 2 / 발급 30</div>
                    <div class="seller-coupon-bar" aria-hidden="true">
                      <span class="seller-coupon-bar__fill" style="width:7%"></span>
                    </div>
                  </div>
                </td>
                <td><span class="seller-coupon-badge seller-coupon-badge--ended">종료</span></td>
                <td class="actions">
                  <button type="button" class="seller-coupon-mini" data-action="view">보기</button>
                </td>
              </tr>
              </tbody>
            </table>
          </div>

          <div class="seller-coupon-pagination" aria-label="페이지네이션(더미)">
            <button type="button" class="seller-coupon-page-btn" data-page="prev">이전</button>
            <button type="button" class="seller-coupon-page-btn active" data-page="1">1</button>
            <button type="button" class="seller-coupon-page-btn" data-page="2">2</button>
            <button type="button" class="seller-coupon-page-btn" data-page="3">3</button>
            <button type="button" class="seller-coupon-page-btn" data-page="next">다음</button>
          </div>
        </section>

        <section class="seller-card seller-coupon-empty" aria-label="빈 상태(더미)" hidden>
          <div class="seller-coupon-empty-inner">
            <div class="seller-coupon-empty-icon" aria-hidden="true">
              <span class="material-icons-outlined">local_activity</span>
            </div>
            <h3 class="seller-coupon-empty-title">아직 등록된 쿠폰이 없어요</h3>
            <p class="seller-coupon-empty-desc">새 쿠폰을 생성해 혜택을 제공해 보세요</p>
            <button type="button" class="seller-coupon-btn seller-coupon-btn--primary" id="couponEmptyCreateBtn">쿠폰 생성하기</button>
          </div>
        </section>
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
  <script src="${pageContext.request.contextPath}/js/seller/coupon-list.js"></script>
</body>
</html>

