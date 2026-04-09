<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("bottomNav", "mypage");%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>내 정보 수정 - 배송지 관리</title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/inquiry-write.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell">
    <div class="top-header-cluster">
        <jsp:include page="../layout/header.jsp" />
    </div>

    <main class="profile-page">
        <section class="profile-intro-card">
            <div class="profile-intro-top">
                <c:choose>
				  <c:when test="${isHelperMode}">
				    <a href="${pageContext.request.contextPath}/group?action=manage" class="back-btn">
				  </c:when>
				  <c:otherwise>
				    <a href="${pageContext.request.contextPath}/mypage" class="back-btn">
				  </c:otherwise>
				</c:choose>
                    <span class="material-icons">chevron_left</span>
                </a>
                <div class="intro-text">
                    <h1>내 정보 수정하기</h1>
                    <p>배송지를 저장하고 관리할 수 있어요</p>
                </div>
            </div>
            <div class="step-tab-wrap">
			  <c:choose>
			    <c:when test="${isHelperMode}">
			      <span class="step-tab"
			            style="color:#ccc; pointer-events:none; cursor:not-allowed;">기본 정보</span>
			      <a href="${pageContext.request.contextPath}/profile-address?targetUserNo=${targetUserNo}"
			         class="step-tab active">배송지 관리</a>
			      <span class="step-tab"
			            style="color:#ccc; pointer-events:none; cursor:not-allowed;">취향 정보</span>
			    </c:when>
			    <c:otherwise>
			      <a href="${pageContext.request.contextPath}/profile" class="step-tab">기본 정보</a>
			      <a href="${pageContext.request.contextPath}/profile-address" class="step-tab active">배송지 관리</a>
			      <a href="${pageContext.request.contextPath}/preference" class="step-tab">취향 정보</a>
			    </c:otherwise>
			  </c:choose>
			</div>
			
			<%-- 도움 모드 안내 배너 --%>
			<c:if test="${isHelperMode}">
			  <div style="display:flex; align-items:center; gap:8px;
			              background:#D8EDDA; border-left:4px solid #4CAF50;
			              padding:10px 14px; border-radius:8px;
			              margin: 12px 0; font-size:14px; color:#111; font-weight: 700;">
			    <span class="material-icons" style="font-size:18px;">volunteer_activism</span>
			    <p style="margin:0;">내 사람의 배송지를 대신 수정 중이에요</p>
			  </div>
			</c:if>
        </section>
	
        <c:forEach var="addr" items="${addressList}">
            <section class="edit-card">
                <div class="card-head">
                    <div>
                        <h2>${addr.addressName}</h2>
                        <c:if test="${addr.isDefault == 1}">
                            <p>기본으로 저장된 배송지예요</p>
                        </c:if>
                    </div>
                    <c:if test="${addr.isDefault == 1}">
                        <span class="status-badge active">기본</span>
                    </c:if>
                </div>

                <div class="address-info-list">
                    <div class="info-line">
                        <span>받는 분</span>
                        <strong>${addr.receiverName}</strong>
                    </div>
                    <div class="info-line">
                        <span>연락처</span>
                        <strong>${addr.receiverTel}</strong>
                    </div>
                    <div class="info-line address-line">
                        <span>주소</span>
                        <strong>(${addr.userZipcode}) ${addr.userAddress} ${addr.userDetailAddress}</strong>
                    </div>
                </div>

            <div class="address-button-row">

                    <c:choose>
					  <c:when test="${isHelperMode}">
					    <button type="button" class="sub-action-btn upload-btn"
					        onclick="location.href='${pageContext.request.contextPath}/address/form?mode=edit&addressId=${addr.userAddressNo}&targetUserNo=${targetUserNo}'">
					        변경하기
					    </button>
					  </c:when>
					  <c:otherwise>
					    <button type="button" class="sub-action-btn upload-btn"
					        onclick="location.href='${pageContext.request.contextPath}/address/form?mode=edit&addressId=${addr.userAddressNo}'">
					        변경하기
					    </button>
					  </c:otherwise>
					</c:choose>
                    <c:if test="${addr.isDefault == 0}">
					    <button type="button" class="sub-action-btn reset-btn"
					        onclick="openDeleteModal('${pageContext.request.contextPath}/address/delete?addressId=${addr.userAddressNo}<c:if test="${isHelperMode}">&targetUserNo=${targetUserNo}</c:if>')">
					        삭제하기
					    </button>
					</c:if>
                </div>
            </section>
        </c:forEach>

        <c:choose>
            <c:when test="${addressList.size() < 3}">
                <section class="edit-card">
                    <div class="empty-state-box">
                        <span class="material-icons">add_location_alt</span>
                        <h2>새 배송지 추가</h2>
                        <p>배송지는 최대 3개까지 저장할 수 있어요</p>
                        <c:choose>
						  <c:when test="${isHelperMode}">
						    <button type="button" class="save-btn"
						        onclick="location.href='${pageContext.request.contextPath}/address/form?mode=add&targetUserNo=${targetUserNo}'">
						        배송지 추가하기
						    </button>
						  </c:when>
						  <c:otherwise>
						    <button type="button" class="save-btn"
						        onclick="location.href='${pageContext.request.contextPath}/address/form?mode=add'">
						        배송지 추가하기
						    </button>
						  </c:otherwise>
						</c:choose>
                    </div>
                </section>
            </c:when>
            <c:otherwise>
                <section class="edit-card">
                    <div class="empty-state-box">
                        <span class="material-icons">inventory_2</span>
                        <h2>배송지가 모두 등록되었어요</h2>
                        <p>새 배송지는 최대 3개까지만 저장할 수 있어요</p>
                    </div>
                </section>
            </c:otherwise>
        </c:choose>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />
</div>
<div id="customDeleteModal" class="inquiry-write-modal hidden">
    <div class="inquiry-write-dim" onclick="closeDeleteModal()"></div>
    
    <div class="inquiry-write-modal-card">
        <p class="inquiry-write-modal-message">정말 삭제하시겠습니까?</p>
        <p class="inquiry-write-modal-sub">삭제된 배송지는 복구할 수 없어요.</p>
        
        <div class="inquiry-write-modal-actions inquiry-write-modal-actions--double">
    <button type="button" class="inquiry-write-modal-btn inquiry-write-modal-btn--ghost" onclick="closeDeleteModal()">취소</button>
    <button type="button" class="inquiry-write-modal-btn" id="modalConfirmBtn" style="background: #D84C33; color: #fff;">삭제하기</button>
</div>
    </div>
</div>

<script>
    let deleteUrl = '';
    function openDeleteModal(url) {
        deleteUrl = url;
        document.getElementById('customDeleteModal').classList.remove('hidden');
    }
    function closeDeleteModal() {
        document.getElementById('customDeleteModal').classList.add('hidden');
        deleteUrl = '';
    }
    document.addEventListener("DOMContentLoaded", function() {
        const confirmBtn = document.getElementById('modalConfirmBtn');
        if (confirmBtn) {
            confirmBtn.addEventListener('click', function() {
                if (deleteUrl) {
                    window.location.href = deleteUrl; // 진짜 삭제 경로로 이동
                }
            });
        }
    });
</script>

<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>