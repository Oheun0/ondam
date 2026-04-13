<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
  request.setAttribute("sellerActiveMenu", "settlement");
  request.setAttribute("sellerPageTitle", "정산 · 매출");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>정산 · 매출 | 온담 파트너</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
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
              <input id="startDate" name="startDate" class="seller-settlement-input" type="date" value="${startDate}" />
			</div>

            <div class="seller-settlement-field">
              <label class="seller-settlement-label" for="endDate">종료일</label>
              <input id="endDate" name="endDate" class="seller-settlement-input" type="date" value="${endDate}" />
			</div>

            <div class="seller-settlement-field">
			  <label class="seller-settlement-label" for="settleStatus">정산 상태</label>
			  <select id="settleStatus" name="settleStatus" class="seller-settlement-select">
			    <option value="all" ${settleStatus == 'all' ? 'selected' : ''}>전체 상태</option>
			    <option value="pending" ${settleStatus == 'pending' ? 'selected' : ''}>정산 예정</option>
			    <option value="done" ${settleStatus == 'done' ? 'selected' : ''}>정산 완료</option>
			
			    <option value="refund" ${settleStatus == 'refund' ? 'selected' : ''}>환불 포함</option>
			    <option value="cancel" ${settleStatus == 'cancel' ? 'selected' : ''}>취소 포함</option>
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
              <div class="value"><fmt:formatNumber value="${totalGross}" pattern="#,###"/><span class="unit">원</span></div>
            </div>
            <div class="seller-settlement-kpi">
              <div class="label">정산 예정</div>
              <div class="value"><fmt:formatNumber value="${totalPending}" pattern="#,###"/><span class="unit">원</span></div>
            </div>
            <div class="seller-settlement-kpi">
              <div class="label">정산 완료</div>
              <div class="value"><fmt:formatNumber value="${totalDone}" pattern="#,###"/><span class="unit">원</span></div>
            </div>
		            <div class="seller-settlement-kpi kpi--danger">
		      <div class="label">수수료 및 차감액</div>
		      <div class="value">
			        <fmt:formatNumber value="${totalRefund + totalFee}" pattern="#,###"/>
			        <span class="unit">원</span>
			      </div>
		      </div>
          </div>
        </section>

        <div class="seller-settlement-grid">
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
                  <div class="amount"><fmt:formatNumber value="${payStats.cardAmt}" pattern="#,###"/>원 <span class="pct">(${payStats.cardPct}%)</span></div>
                </div>
                <div class="bar" aria-hidden="true"><span class="fill" style="width:${payStats.cardPct}%"></span></div>
              </div>

              <div class="seller-settlement-method">
                <div class="top">
                  <div class="name">계좌이체</div>
                  <div class="amount"><fmt:formatNumber value="${payStats.bankAmt}" pattern="#,###"/>원 <span class="pct">(${payStats.bankPct}%)</span></div>
                </div>
                <div class="bar" aria-hidden="true"><span class="fill fill--bank" style="width:${payStats.bankPct}%"></span></div>
              </div>

              <div class="seller-settlement-method method--wallet">
                <div class="top">
                  <div class="name">
                    함께지갑 결제 <span class="tag">온담 포인트</span>
                  </div>
                  <div class="amount"><fmt:formatNumber value="${payStats.walletAmt}" pattern="#,###"/>원 <span class="pct">(${payStats.walletPct}%)</span></div>
                </div>
                <div class="bar" aria-hidden="true"><span class="fill fill--wallet" style="width:${payStats.walletPct}%"></span></div>
                <p class="hint">함께지갑 결제 비중은 온담의 핵심 지표예요.</p>
              </div>
            </div>
          </section>

          <section class="seller-card seller-settlement-refund" aria-label="환불 및 취소 현황">
            <header class="seller-settlement-section-head">
              <div>
                <h3 class="seller-settlement-section-title">환불 · 취소 현황</h3>
                <p class="seller-settlement-section-sub">최근 처리 내역을 빠르게 확인해 보세요</p>
              </div>
            </header>

            <div class="seller-settlement-mini-table-wrap" style="margin-top: 16px;">
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
                <c:forEach var="ref" items="${recentRefunds}">
                  <tr>
                    <td>${ref.orderNo}</td>
                    <td>
                      <c:choose>
                        <c:when test="${ref.type == '환불'}"><span class="seller-settlement-pill pill--refund">환불</span></c:when>
                        <c:otherwise><span class="seller-settlement-pill pill--cancel">취소</span></c:otherwise>
                      </c:choose>
                    </td>
                    <td><fmt:formatNumber value="${ref.amount}" pattern="#,###"/>원</td>
                    <td>완료</td>
                    <td>${ref.date}</td>
                  </tr>
                </c:forEach>
                
                <c:if test="${empty recentRefunds}">
                  <tr><td colspan="5" style="text-align:center; padding:20px;">최근 내역이 없습니다.</td></tr>
                </c:if>
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
              <c:forEach var="item" items="${settlementList}">
			    <tr data-settle-id="ST${item.settlementNo}"
			        data-period="${item.createdAt} 기준"
			        data-gross="${item.totalAmount}"
			        <%-- 💡 수수료 + 환불액을 더해서 '차감액'으로 모달에 전달 --%>
			        data-refund="${item.commissionFee + item.refundAmount}"
			        data-target="${item.actualAmount}"
			        <%-- 💡 결제수단별 금액 (DTO에 해당 필드가 있다면 연결, 없다면 우선 0) --%>
			        data-card="${item.cardAmount}" 
			        data-bank="${item.bankAmount}"
			        data-wallet="${item.walletAmount}"
			        data-status="${item.settleState == 1 ? 'done' : 'pending'}"
			        data-date="${item.settleDate}">
			        
			      <td class="mono">ST${item.settlementNo}</td>
			      <td>${item.createdAt}</td>
			      <td><fmt:formatNumber value="${item.totalAmount}" pattern="#,###"/>원</td>
			      <td class="neg">-<fmt:formatNumber value="${item.commissionFee + item.refundAmount}" pattern="#,###"/>원</td>
			      <td><strong><fmt:formatNumber value="${item.actualAmount}" pattern="#,###"/>원</strong></td>
			      <td class="mix">카드/지갑 등</td> 
			      
			      <td>
			        <c:choose>
			          <c:when test="${item.settleState == 1}">
			            <span class="seller-settlement-badge badge--done">정산 완료</span>
			          </c:when>
			          <c:otherwise>
			            <span class="seller-settlement-badge badge--pending">정산 예정</span>
			          </c:otherwise>
			        </c:choose>
			      </td>
			      <td>${item.settleDate}</td>
			      <td><button type="button" class="seller-settlement-mini-btn" data-action="view">보기</button></td>
			    </tr>
			</c:forEach>
              <c:if test="${empty settlementList}">
                <tr><td colspan="9" style="text-align:center; padding: 40px 0;">정산 내역이 없습니다.</td></tr>
              </c:if>
              </tbody>
            </table>
          </div>

          <div class="seller-settlement-pagination">
			  <c:forEach var="i" begin="1" end="${totalPage}">
			    <button type="button" 
			            class="seller-settlement-page-btn ${i == currentPage ? 'active' : ''}" 
			            data-page="${i}">${i}</button>
			  </c:forEach>
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
               <div class="k">수수료 및 차감액</div> 
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
              <p class="hint">위 금액은 결제 시점의 수단별 합계입니다.</p>
			</section>
          </div>
        </div>
      </div>

      <jsp:include page="/WEB-INF/views/seller/layout/seller-footer.jsp" />
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/seller/settlement-list.js"></script>
</body>
</html>

