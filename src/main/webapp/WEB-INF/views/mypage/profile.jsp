<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("bottomNav", "mypage");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>내 정보 수정</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<div class="app-shell">
    <div class="top-header-cluster">
        <jsp:include page="../layout/header.jsp" />
    </div>

    <main class="profile-page">
        <!-- 상단 안내 -->
        <section class="profile-intro-card">
            <div class="profile-intro-top">
                <a href="${pageContext.request.contextPath}/mypage" class="back-btn">
                    <span class="material-icons">chevron_left</span>
                </a>
                <div class="intro-text">
                    <h1>내 정보 수정하기</h1>
                    <p>수정할 항목을 골라 바꿔보세요</p>
                </div>
            </div>
            <div class="step-tab-wrap">
				<a href="${pageContext.request.contextPath}/profile" class="step-tab active">기본 정보</a>
				<a href="${pageContext.request.contextPath}/profile-address" class="step-tab">배송지 관리</a>
				<a href="${pageContext.request.contextPath}/preference" class="step-tab">취향 정보</a>
            </div>
        </section>

        <!-- 기본 정보 수정 -->
        <section class="edit-card">
            <div class="card-title-row">
                <h2>기본 정보</h2>
                <p>이름, 생년월일, 성별, 연락처를 수정할 수 있어요</p>
            </div>

            <form action="${pageContext.request.contextPath}/profile/update" method="post" enctype="multipart/form-data" class="edit-form">
                
                <!-- 프로필 이미지 -->
                <div class="form-block">
                    <label class="block-label">프로필 이미지</label>

                    <div class="profile-image-edit">
                        <div class="profile-preview-box">
							<img src="${pageContext.request.contextPath}/images/profile/${loginUser.userProfileImg != null ? loginUser.userProfileImg : 'default-profile.png'}" 
							     alt="프로필 이미지" class="profile-preview"
							     onerror="this.src='${pageContext.request.contextPath}/images/profile/default-profile.png'">
                        </div>

                        <div class="profile-image-actions">
                            <label for="profileImage" class="sub-action-btn upload-btn">새 이미지 올리기</label>
                            <input type="file" id="profileImage" name="profileImage" accept="image/*" hidden>

                            <button type="button" class="sub-action-btn reset-btn">기본 이미지로 변경</button>
                        </div>
                    </div>
                </div>

                <!-- 이름 -->
                <div class="form-block">
                    <label for="userName" class="block-label">이름</label>
                    <input type="text" id="userName" name="userName" class="input-box" value="${loginUser.userName}" placeholder="이름을 입력하세요">
                </div>

                <!-- 생년월일 -->
                <div class="form-block">
                    <label for="birthDate" class="block-label">생년월일</label>
                    <input type="date" id="birthDate" name="birthDate" class="input-box" value="${loginUser.userBirth}">
                </div>

                <!-- 성별 -->
                <div class="form-block">
				    <label class="block-label">성별</label>
				    <div class="radio-group">
				        <label class="radio-chip">
				            <input type="radio" name="gender" value="1" ${loginUser.userGender == 1 ? 'checked' : ''}>
				            <span>여성</span>
				        </label>
				        <label class="radio-chip">
				            <input type="radio" name="gender" value="2" ${loginUser.userGender == 2 ? 'checked' : ''}>
				            <span>남성</span>
				        </label>
				    </div>
				</div>

                <!-- 연락처 -->
                <div class="form-block">
                    <label for="phone" class="block-label">연락처</label>
                    <input type="text" id="phone" name="phone" class="input-box" value="${loginUser.userPhoneNumber}" placeholder="연락처를 입력하세요">
                </div>

                <!-- 수정 불가 정보 -->
                <div class="form-block readonly-block">
                    <label class="block-label">로그인 정보</label>
                    <div class="readonly-box">
                        <div class="readonly-row">
                            <span>가입 방식</span>
                            <strong>카카오 로그인</strong>
                        </div>
                        <div class="readonly-row">
                            <span>계정 정보</span>
                            <strong>변경할 수 없어요</strong>
                        </div>
                    </div>
                </div>

                <button type="submit" class="save-btn">변경사항 저장</button>
            </form>
        </section>
    </main>

    <jsp:include page="../layout/bottomNav.jsp" />
</div>
<script>
    const profileInput = document.getElementById('profileImage');
    const profilePreview = document.querySelector('.profile-preview');

    profileInput.addEventListener('change', function(event) {
        const file = event.target.files[0];
        
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                profilePreview.src = e.target.result;
            }
            reader.readAsDataURL(file);
        }
    });
    document.querySelector('.reset-btn').addEventListener('click', function() {
        profileInput.value = '';
        profilePreview.src = '${pageContext.request.contextPath}/images/profile/default-profile.png';
    });
</script>
<script src="${pageContext.request.contextPath}/js/ondam-nav.js"></script>
</body>
</html>