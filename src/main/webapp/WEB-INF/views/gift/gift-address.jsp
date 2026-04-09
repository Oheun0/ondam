<%-- gift-address.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="op-modal hidden" id="opAddressModal" role="dialog" aria-modal="true" aria-labelledby="opAddressModalTitle">
    <div class="op-modal-dim" id="opAddressModalDim"></div>
    <div class="op-modal-card op-modal-card--address">
        <h2 class="op-modal-title" id="opAddressModalTitle">배송지 선택</h2>

        <div class="op-address-list">
            <c:choose>
                <c:when test="${empty addressList}">
                    <div class="op-address-empty">
                        <p>등록된 배송지가 없습니다.</p>
                        <a href="${pageContext.request.contextPath}/mypage/profile-address" class="op-modal-btn op-modal-btn--primary">배송지 추가하러 가기</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="addr" items="${addressList}">
                        <button type="button" 
                                class="op-address-item"
                                data-no="${addr.userAddressNo}"
                                data-receiver-name="${addr.receiverName}"
                                data-receiver-tel="${addr.receiverTel}"
                                data-address="${addr.userAddress}"
                                data-detail="${addr.userDetailAddress}"
                                data-zipcode="${addr.userZipcode}">
                            <span class="op-address-item__name">
                                ${addr.addressName}
                                <c:if test="${addr.isDefault == 1}">
                                    <span class="op-badge op-badge--muted">기본</span>
                                </c:if>
                            </span>
                            <span class="op-address-item__receiver">${addr.receiverName} | ${addr.receiverTel}</span>
                            <span class="op-address-item__addr">(${addr.userZipcode}) ${addr.userAddress}
                                <c:if test="${not empty addr.userDetailAddress}">, ${addr.userDetailAddress}</c:if>
                            </span>
                        </button>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="op-modal-actions">
            <button type="button" class="op-modal-btn op-modal-btn--ghost" id="opAddressModalCloseBtn">닫기</button>
        </div>
    </div>
</div>