<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  request.setAttribute("sellerActiveMenu", "settlement");
  request.setAttribute("sellerPageTitle", "정산 · 매출");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>정산 · 매출 | 온담 판매자센터</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-settlement.css" />
</head>
<body class="seller-app" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-layout">
    <jsp:include page="/WEB-INF/views/seller/layout/seller-sidebar.jsp" />

    <div class="seller-main-area">
      <jsp:include page="/WEB-INF/views/seller/layout/seller-header.jsp" />

      <main class="seller-content seller-settlement-page" aria-label="정산 및 매출 관리">
        <header class="seller-settlement-head">
          <div>
            <h2 class="seller-settlement-title">정산 · 매출</h2>
            <p class="seller-settlement-sub">기간별 매출과 정산 현황을 확인할 수 있어요</p>
          </div>
          <div class="seller-settlement-head-actions">
            <button type="button" class="seller-settlement-btn" id="settlementDownloadBtn">
              <span class="material-icons-outlined" aria-hidden="true">download</span>
              정산 내역 다운로드
            </button>
          </div>
        </header>

        <!-- 필터 -->
        <section class="seller-card seller-settlement-toolbar" aria-label="조회 필터">
          <div class="seller-settlement-filters">
            <div class="seller-settlement-field">
              <label class="seller-settlement-label" for="periodPreset">기간</label>
              <select id="periodPreset" class="seller-settlement-select">
                <option value="today">오늘</option>
                <option value="7d">최근 7일</option>
                <option value="30d">최근 30일</option>
                <option value="month">이번 달</option>
                <option value="custom">직접 선택</option>
              </select>
            </div>

            <div class="seller-settlement-field">
              <label class="seller-settlement-label" for="startDate">시작일</label>
              <input id="startDate" class="seller-settlement-input" type="date" />
            </div>

            <div class="seller-settlement-field">
              <label class="seller-settlement-label" for="endDate">종료일</label>
              <input id="endDate" class="seller-settlement-input" type="date" />
            </div>

            <div class="seller-settlement-field">
              <label class="seller-settlement-label" for="settleStatus">정산 상태</label>
              <select id="settleStatus" class="seller-settlement-select">
                <option value="all">전체 상태</option>
                <option value="pending">정산 예정</option>
                <option value="done">정산 완료</option>
                <option value="refund">환불 포함</option>
                <option value="cancel">취소 포함</option>
              </select>
            </div>

            <div class="seller-settlement-field">
              <label class="seller-settlement-label" for="payMethod">결제수단</label>
              <select id="payMethod" class="seller-settlement-select">
                <option value="all">전체 결제수단</option>
                <option value="card">카드 결제</option>
                <option value="bank">계좌이체</option>
                <option value="wallet">함께지갑 결제</option>
              </select>
            </div>

            <div class="seller-settlement-field seller-settlement-field--btn">
              <button type="button" class="seller-settlement-btn seller-settlement-btn--primary" id="settlementSearchBtn">
                조회
              </button>
            </div>
          </div>
          <p class="seller-settlement-note">정산 계산/다운로드는 더미 동작이며 실제 연동되지 않았어요.</p>
        </section>

        <!-- 요약 카드 -->
        <section class="seller-settlement-summary" aria-label="요약 수치">
          <div class="seller-settlement-summary-grid">
            <div class="seller-settlement-kpi">
              <div class="label">총 매출</div>
              <div class="value">1,820,000<span class="unit">원</span></div>
            </div>
            <div class="seller-settlement-kpi">
              <div class="label">정산 예정</div>
              <div class="value">420,000<span class="unit">원</span></div>
            </div>
            <div class="seller-settlement-kpi">
              <div class="label">정산 완료</div>
              <div class="value">1,180,000<span class="unit">원</span></div>
            </div>
            <div class="seller-settlement-kpi kpi--danger">
              <div class="label">환불/취소</div>
              <div class="value">220,000<span class="unit">원</span></div>
            </div>
          </div>
        </section>

        <div class="seller-settlement-grid">
          <!-- 결제수단 비중 -->
          <section class="seller-card seller-settlement-methods" aria-label="결제수단 비중">
            <header class="seller-settlement-section-head">
              <div>
                <h3 class="seller-settlement-section-title">결제수단 비중</h3>
                <p class="seller-settlement-section-sub">어떤 방식으로 결제가 이루어졌는지 확인해 보세요</p>
              </div>
            </header>

            <div class="seller-settlement-method-list">
              <div class="seller-settlement-method">
                <div class="top">
                  <div class="name">카드 결제</div>
                  <div class="amount">980,000원 <span class="pct">(54%)</span></div>
                </div>
                <div class="bar" aria-hidden="true"><span class="fill" style="width:54%"></span></div>
              </div>

              <div class="seller-settlement-method">
                <div class="top">
                  <div class="name">계좌이체</div>
                  <div class="amount">240,000원 <span class="pct">(13%)</span></div>
                </div>
                <div class="bar" aria-hidden="true"><span class="fill fill--bank" style="width:13%"></span></div>
              </div>

              <div class="seller-settlement-method method--wallet">
                <div class="top">
                  <div class="name">
                    함께지갑 결제 <span class="tag">온담 포인트</span>
                  </div>
                  <div class="amount">600,000원 <span class="pct">(33%)</span></div>
                </div>
                <div class="bar" aria-hidden="true"><span class="fill fill--wallet" style="width:33%"></span></div>
                <p class="hint">함께지갑 결제 비중은 온담의 핵심 지표예요.</p>
              </div>
            </div>
          </section>

          <!-- 환불/취소 -->
          <section class="seller-card seller-settlement-refund" aria-label="환불 및 취소 현황">
            <header class="seller-settlement-section-head">
              <div>
                <h3 class="seller-settlement-section-title">환불 · 취소 현황</h3>
                <p class="seller-settlement-section-sub">최근 처리 내역을 빠르게 확인해 보세요</p>
              </div>
            </header>

            <div class="seller-settlement-refund-kpis">
              <div class="mini">
                <div class="k">환불</div>
                <div class="v">3건</div>
              </div>
              <div class="mini">
                <div class="k">취소</div>
                <div class="v">2건</div>
              </div>
              <div class="mini mini--sum">
                <div class="k">합계</div>
                <div class="v">220,000원</div>
              </div>
            </div>

            <div class="seller-settlement-mini-table-wrap">
              <table class="seller-settlement-mini-table">
                <thead>
                <tr>
                  <th>주문번호</th>
                  <th>유형</th>
                  <th>금액</th>
                  <th>처리상태</th>
                  <th>날짜</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                  <td>20260408-0004</td>
                  <td><span class="seller-settlement-pill pill--refund">환불</span></td>
                  <td>39,000원</td>
                  <td>완료</td>
                  <td>2026.04.08</td>
                </tr>
                <tr>
                  <td>20260407-0011</td>
                  <td><span class="seller-settlement-pill pill--cancel">취소</span></td>
                  <td>29,000원</td>
                  <td>완료</td>
                  <td>2026.04.07</td>
                </tr>
                <tr>
                  <td>20260406-0021</td>
                  <td><span class="seller-settlement-pill pill--refund">환불</span></td>
                  <td>152,000원</td>
                  <td>완료</td>
                  <td>2026.04.06</td>
                </tr>
                </tbody>
              </table>
            </div>
          </section>
        </div>

        <!-- 정산 내역 테이블 -->
        <section class="seller-card seller-settlement-table-wrap" aria-label="정산 내역">
          <header class="seller-settlement-table-head">
            <div>
              <h3 class="seller-settlement-section-title">정산 내역</h3>
              <p class="seller-settlement-section-sub">기간별 정산 대상 금액과 상태를 확인할 수 있어요</p>
            </div>
          </header>

          <div class="seller-settlement-table-scroll">
            <table class="seller-settlement-table">
              <thead>
              <tr>
                <th>정산번호</th>
                <th>기간</th>
                <th>총 매출</th>
                <th>환불/취소</th>
                <th>정산 대상</th>
                <th>결제수단 비중</th>
                <th>상태</th>
                <th>정산일</th>
                <th>보기</th>
              </tr>
              </thead>
              <tbody>
              <tr data-settle-id="ST202604-01"
                  data-period="2026.04.01 ~ 2026.04.07"
                  data-gross="820,000"
                  data-refund="-39,000"
                  data-target="781,000"
                  data-card="451,000"
                  data-bank="0"
                  data-wallet="369,000"
                  data-status="done"
                  data-date="2026.04.08">
                <td class="mono">ST202604-01</td>
                <td>2026.04.01 ~ 2026.04.07</td>
                <td>820,000원</td>
                <td class="neg">-39,000원</td>
                <td><strong>781,000원</strong></td>
                <td class="mix">카드 55% / 함께지갑 45%</td>
                <td><span class="seller-settlement-badge badge--done">정산 완료</span></td>
                <td>2026.04.08</td>
                <td><button type="button" class="seller-settlement-mini-btn" data-action="view">보기</button></td>
              </tr>

              <tr data-settle-id="ST202604-02"
                  data-period="2026.04.08 ~ 2026.04.14"
                  data-gross="1,000,000"
                  data-refund="-181,000"
                  data-target="819,000"
                  data-card="530,000"
                  data-bank="140,000"
                  data-wallet="330,000"
                  data-status="pending"
                  data-date="-">
                <td class="mono">ST202604-02</td>
                <td>2026.04.08 ~ 2026.04.14</td>
                <td>1,000,000원</td>
                <td class="neg">-181,000원</td>
                <td><strong>819,000원</strong></td>
                <td class="mix">카드 53% / 계좌 14% / 함께지갑 33%</td>
                <td><span class="seller-settlement-badge badge--pending">정산 예정</span></td>
                <td>-</td>
                <td><button type="button" class="seller-settlement-mini-btn" data-action="view">보기</button></td>
              </tr>

              <tr data-settle-id="ST202603-04"
                  data-period="2026.03.22 ~ 2026.03.31"
                  data-gross="640,000"
                  data-refund="-0"
                  data-target="640,000"
                  data-card="420,000"
                  data-bank="50,000"
                  data-wallet="170,000"
                  data-status="done"
                  data-date="2026.04.01">
                <td class="mono">ST202603-04</td>
                <td>2026.03.22 ~ 2026.03.31</td>
                <td>640,000원</td>
                <td>0원</td>
                <td><strong>640,000원</strong></td>
                <td class="mix">카드 66% / 계좌 8% / 함께지갑 26%</td>
                <td><span class="seller-settlement-badge badge--done">정산 완료</span></td>
                <td>2026.04.01</td>
                <td><button type="button" class="seller-settlement-mini-btn" data-action="view">보기</button></td>
              </tr>

              <tr data-settle-id="ST202603-03"
                  data-period="2026.03.15 ~ 2026.03.21"
                  data-gross="390,000"
                  data-refund="-58,000"
                  data-target="332,000"
                  data-card="210,000"
                  data-bank="0"
                  data-wallet="180,000"
                  data-status="refund"
                  data-date="2026.03.22">
                <td class="mono">ST202603-03</td>
                <td>2026.03.15 ~ 2026.03.21</td>
                <td>390,000원</td>
                <td class="neg">-58,000원</td>
                <td><strong>332,000원</strong></td>
                <td class="mix">카드 54% / 함께지갑 46%</td>
                <td><span class="seller-settlement-badge badge--warn">환불 포함</span></td>
                <td>2026.03.22</td>
                <td><button type="button" class="seller-settlement-mini-btn" data-action="view">보기</button></td>
              </tr>
              </tbody>
            </table>
          </div>

          <div class="seller-settlement-pagination" aria-label="페이지네이션(더미)">
            <button type="button" class="seller-settlement-page-btn" data-page="prev">이전</button>
            <button type="button" class="seller-settlement-page-btn active" data-page="1">1</button>
            <button type="button" class="seller-settlement-page-btn" data-page="2">2</button>
            <button type="button" class="seller-settlement-page-btn" data-page="3">3</button>
            <button type="button" class="seller-settlement-page-btn" data-page="next">다음</button>
          </div>
        </section>

        <section class="seller-card seller-settlement-empty" aria-label="빈 상태(더미)" hidden>
          <div class="seller-settlement-empty-inner">
            <div class="seller-settlement-empty-icon" aria-hidden="true">
              <span class="material-icons-outlined">bar_chart</span>
            </div>
            <h3 class="seller-settlement-empty-title">아직 정산 내역이 없어요</h3>
            <p class="seller-settlement-empty-desc">주문이 발생하면 여기에 정산 정보가 표시됩니다</p>
            <button type="button" class="seller-settlement-btn seller-settlement-btn--primary" id="goOrderBtn">주문 관리 보기</button>
          </div>
        </section>
      </main>

      <!-- 상세 모달 -->
      <div class="seller-settlement-modal hidden" id="settleModal" aria-hidden="true">
        <div class="seller-settlement-modal__dim" id="settleModalDim" aria-hidden="true"></div>
        <div class="seller-settlement-modal__dialog" role="dialog" aria-modal="true" aria-labelledby="settleModalTitle">
          <header class="seller-settlement-modal__head">
            <div>
              <h3 class="seller-settlement-modal__title" id="settleModalTitle">정산 상세</h3>
              <p class="seller-settlement-modal__sub" id="settleModalSub">-</p>
            </div>
            <button type="button" class="seller-settlement-modal__close" id="settleModalClose" aria-label="닫기">
              <span class="material-icons-outlined" aria-hidden="true">close</span>
            </button>
          </header>
          <div class="seller-settlement-modal__body">
            <div class="seller-settlement-detail-grid">
              <div class="box">
                <div class="k">기간</div>
                <div class="v" id="dPeriod">-</div>
              </div>
              <div class="box">
                <div class="k">상태</div>
                <div class="v" id="dStatus">-</div>
              </div>
              <div class="box">
                <div class="k">총 매출</div>
                <div class="v" id="dGross">-</div>
              </div>
              <div class="box">
                <div class="k">환불/취소</div>
                <div class="v neg" id="dRefund">-</div>
              </div>
              <div class="box box--strong">
                <div class="k">최종 정산 금액</div>
                <div class="v" id="dTarget">-</div>
              </div>
              <div class="box">
                <div class="k">정산일</div>
                <div class="v" id="dDate">-</div>
              </div>
            </div>

            <section class="seller-settlement-detail-pay" aria-label="결제수단 상세">
              <h4 class="h4">결제수단 금액</h4>
              <div class="pay-row">
                <span class="label">카드</span>
                <span class="value" id="dCard">-</span>
              </div>
              <div class="pay-row">
                <span class="label">계좌이체</span>
                <span class="value" id="dBank">-</span>
              </div>
              <div class="pay-row wallet">
                <span class="label">함께지갑</span>
                <span class="value" id="dWallet">-</span>
              </div>
              <p class="hint">상세 모달은 더미 UI이며 실제 정산 계산은 연동되지 않았어요.</p>
            </section>
          </div>
        </div>
      </div>

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
  <script src="${pageContext.request.contextPath}/js/seller/settlement-list.js"></script>
</body>
</html>

