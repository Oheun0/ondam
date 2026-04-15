<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
  request.setAttribute("sellerActiveMenu", "product");
  request.setAttribute("sellerPageTitle", "상품 등록/수정");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title><c:out value="${editMode ? '상품 수정' : '새 상품 등록'}" /> | 온담 파트너</title>

  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Outlined&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-layout.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seller/seller-product.css" />
</head>
<body class="seller-app" data-context-path="${pageContext.request.contextPath}">
  <div class="seller-layout">
    <jsp:include page="/WEB-INF/views/seller/layout/seller-sidebar.jsp" />

    <div class="seller-main-area">
      <jsp:include page="/WEB-INF/views/seller/layout/seller-header.jsp" />

      <main class="seller-content seller-product-form-page" aria-label="상품 등록/수정">
        <header class="seller-product-form-head">
          <div>
            <h2 class="seller-product-title"><c:out value="${editMode ? '상품 수정' : '새 상품 등록'}" /></h2>
            <p class="seller-product-sub">상품 정보, 옵션, 이미지, 추천 태그를 입력해 주세요</p>
          </div>
          <div class="seller-product-form-head-actions">
            <a class="seller-secondary-btn seller-product-link-btn" href="${pageContext.request.contextPath}/seller/product">
              <span class="material-icons-outlined" aria-hidden="true">arrow_back</span>
              목록으로
            </a>
          </div>
        </header>

        <form id="sellerProductForm" class="seller-product-form" action="${pageContext.request.contextPath}${editMode ? '/seller/product/update' : '/seller/product/save'}" method="post" enctype="multipart/form-data" novalidate>
          <input type="hidden" id="saveMode" name="saveMode" value="submit" />
          <c:if test="${editMode}">
            <input type="hidden" name="productNo" value="${editProductNo}" />
            <input type="hidden" id="editSituationNo" value="${editProduct.situationNo}" />
            <input type="hidden" id="editCategoryNo" value="${editProduct.categoryNo}" />
          </c:if>
          <section class="seller-card seller-product-section" aria-label="기본정보">
            <header class="seller-product-section-head">
              <div>
                <h3 class="seller-product-section-title">기본정보</h3>
                <p class="seller-product-section-sub">상품의 기본 정보를 입력해 주세요</p>
              </div>
            </header>

            <div class="seller-product-grid seller-product-grid--2">
              <div class="seller-product-field seller-product-field--brand">
                <label class="seller-product-label" for="brandName">브랜드명 <span class="seller-product-required">*</span></label>
                <input id="brandName" name="brandName" class="seller-product-control" type="text"
                  value="<c:out value='${sessionScope.vendorName}' />"
                  readonly="readonly"
                  placeholder="브랜드명을 입력해 주세요" />
                <p class="seller-product-error hidden" id="brandNameError" aria-live="polite"></p>
              </div>

              <div class="seller-product-field">
                <label class="seller-product-label" for="situationCategory">카테고리 <span class="seller-product-required">*</span></label>
                <div class="seller-product-split">
                  <select id="situationCategory" name="situationCategory" class="seller-product-control">
                    <option value="">상황 카테고리를 선택해 주세요</option>
                    <optgroup label="일상 생활">
                      <option value="14">집에서 편하게 입는 옷</option>
                      <option value="15">잠잘 때 입는 옷</option>
                      <option value="16">집안일 할 때 입는 옷</option>
                      <option value="17">가볍게 입는 옷</option>
                      <option value="18">가볍게 나갈 때 입는 옷</option>
                      <option value="19">사람 만날 때 입는 옷</option>
                      <option value="20">날씨에 맞는 옷</option>
                      <option value="21">오래 걸어도 편한 옷</option>
                      <option value="22">입고 벗기 쉬운 옷</option>
                      <option value="26">신축성 좋은 옷</option>
                      <option value="27">부드러운 옷</option>
                      <option value="28">넉넉한 옷</option>
                      <option value="29">피부에 자극 없는 옷</option>
                    </optgroup>
                    <optgroup label="특별한 날">
                      <option value="30">결혼식 갈 때</option>
                      <option value="31">장례식 갈 때</option>
                      <option value="32">동창회 갈 때</option>
                      <option value="33">모임/행사 갈 때</option>
                      <option value="34">입학식 / 졸업식</option>
                      <option value="35">가족 행사</option>
                      <option value="36">기념일</option>
                    </optgroup>
                    <optgroup label="취미·여가">
                      <option value="37">등산할 때</option>
                      <option value="38">골프 칠 때</option>
                      <option value="39">수영할 때</option>
                      <option value="40">자전거 탈 때</option>
                      <option value="41">여행 갈 때</option>
                      <option value="42">낚시할 때</option>
                      <option value="43">텃밭/원예 할 때</option>
                      <option value="44">편하게 놀러 갈 때</option>
                    </optgroup>
                    <optgroup label="선물하기">
                      <option value="45">부모님께 드리기 좋은 옷</option>
                      <option value="46">할머니·할아버지 옷</option>
                      <option value="47">손주 옷</option>
                      <option value="48">사이즈 넉넉한 옷</option>
                      <option value="49">누구나 입기 좋은 옷</option>
                      <option value="50">인기 많은 옷</option>
                    </optgroup>
                  </select>
                  <select id="typeCategory" name="typeCategory" class="seller-product-control">
                    <option value="">종류 카테고리를 선택해 주세요</option>
                    <optgroup label="윗옷">
                      <option value="5">반팔</option>
                      <option value="6">긴팔</option>
                      <option value="7">니트</option>
                      <option value="8">셔츠</option>
                      <option value="9">조끼</option>
                    </optgroup>
                    <optgroup label="아랫옷">
                      <option value="10">긴바지</option>
                      <option value="11">반바지</option>
                      <option value="12">치마</option>
                    </optgroup>
                    <optgroup label="겉옷">
                      <option value="13">가디건</option>
                      <option value="14">점퍼</option>
                      <option value="15">코트</option>
                      <option value="16">바람막이</option>
                    </optgroup>
                    <optgroup label="한 벌 옷">
                      <option value="17">원피스</option>
                      <option value="18">세트 옷</option>
                    </optgroup>
                  </select>
                </div>
                <p class="seller-product-error hidden" id="categoryError" aria-live="polite"></p>
              </div>

              <div class="seller-product-field">
                <label class="seller-product-label" for="productName">상품명 <span class="seller-product-required">*</span></label>
                <input id="productName" name="productName" class="seller-product-control" type="text" value="${editMode ? editProduct.productName : ''}" placeholder="상품명을 입력해 주세요" />
                <p class="seller-product-error hidden" id="productNameError" aria-live="polite"></p>
              </div>

              <div class="seller-product-field">
                <label class="seller-product-label" for="saleStatus">판매상태 / 성별</label>
                <div class="seller-product-split">
                  <select id="saleStatus" name="saleStatus" class="seller-product-control">
                    <option value="selling" <c:if test="${editProduct.productState == 1}">selected</c:if>>판매중</option>
                    <option value="hidden" <c:if test="${editProduct.productState == 0}">selected</c:if>>숨김</option>
                    <option value="soldout" <c:if test="${editProduct.productState == 2}">selected</c:if>>품절</option>
                  </select>
                  <div class="chip-wrap" aria-label="성별 선택">
                    <label class="chip chip--plain">
                      <input type="radio" name="productGender" value="0" <c:if test="${!editMode || editProduct.productGender == 0}">checked</c:if>>
                      <span>남녀공용</span>
                    </label>
                    <label class="chip chip--plain">
                      <input type="radio" name="productGender" value="1" <c:if test="${editMode && editProduct.productGender == 1}">checked</c:if>>
                      <span>남성용</span>
                    </label>
                    <label class="chip chip--plain">
                      <input type="radio" name="productGender" value="2" <c:if test="${editMode && editProduct.productGender == 2}">checked</c:if>>
                      <span>여성용</span>
                    </label>
                  </div>
                </div>
              </div>

              <div class="seller-product-field" style="grid-column: 1 / -1;">
                <label class="seller-product-label" for="productEx">상품 설명</label>
                <textarea id="productEx" name="productEx" class="seller-product-textarea" rows="4" placeholder="상품 설명을 입력해 주세요">${editMode ? editProduct.productEx : ''}</textarea>
              </div>
            </div>
          </section>

          <section class="seller-card seller-product-section" aria-label="가격 정보">
            <header class="seller-product-section-head">
              <div>
                <h3 class="seller-product-section-title">가격 정보</h3>
                <p class="seller-product-section-sub">사용자 화면에 보여질 가격과 할인 정보를 설정해 주세요</p>
              </div>
              <div class="seller-product-price-preview" aria-label="가격 미리보기">
                <span class="seller-product-price-before" id="priceBefore">-</span>
                <span class="seller-product-price-arrow">→</span>
                <span class="seller-product-price-after" id="priceAfter">-</span>
              </div>
            </header>

            <div class="seller-product-grid seller-product-grid--3">
              <div class="seller-product-field">
                <label class="seller-product-label" for="price">판매가 <span class="seller-product-required">*</span></label>
                <input id="price" name="price" class="seller-product-control" type="text" inputmode="numeric" value="${editMode ? editProduct.productOriginPrice : ''}" placeholder="판매가를 입력해 주세요" />
                <p class="seller-product-error hidden" id="priceError" aria-live="polite"></p>
              </div>

              <div class="seller-product-field">
                <label class="seller-product-label" for="discountRate">할인율</label>
                <div class="seller-product-inline">
                  <input id="discountRate" name="discountRate" class="seller-product-control" type="text" inputmode="numeric" placeholder="할인율(%)" />
                  <span class="seller-product-suffix">%</span>
                </div>
              </div>

              <div class="seller-product-field">
                <label class="seller-product-label" for="salePrice">할인가</label>
                <input id="salePrice" name="salePrice" class="seller-product-control" type="text" inputmode="numeric" value="${editMode ? editProduct.productPrice : ''}" placeholder="할인가를 입력해 주세요" />
              </div>
            </div>
            <p class="seller-product-hint">판매가와 할인율을 입력하면 할인가가 자동 계산돼요.</p>
          </section>

          <section class="seller-card seller-product-section" aria-label="이미지 등록">
            <header class="seller-product-section-head">
              <div>
                <h3 class="seller-product-section-title">이미지 등록</h3>
                <p class="seller-product-section-sub">대표 이미지 1장과 상세 이미지를 등록해 주세요</p>
              </div>
            </header>

            <div class="seller-product-upload-grid">
              <div class="seller-product-upload-block">
                <div class="seller-product-upload-head">
                  <strong>대표 이미지</strong>
                  <span class="seller-product-upload-desc">상품 목록과 상세 상단에 사용돼요</span>
                </div>
                <div class="seller-product-upload-box" id="thumbBox" tabindex="0" role="button" aria-label="대표 이미지 업로드">
                  <div class="seller-product-upload-icon" aria-hidden="true">
                    <span class="material-icons-outlined">image</span>
                  </div>
                  <div class="seller-product-upload-text">
                    <div class="seller-product-upload-title">대표 이미지 추가</div>
                    <div class="seller-product-upload-sub">클릭해서 대표 이미지를 선택해 주세요</div>
                  </div>
                  <button type="button" class="seller-mini-btn seller-mini-btn--primary" id="thumbAddBtn">이미지 추가</button>
                </div>
                <input type="file" id="thumbImageInput" name="thumbImage" accept="image/*" style="display:none;" />
                <c:set var="editThumbUrl" value="" />
                <c:if test="${editMode}">
                  <c:forEach var="img" items="${editImages}">
                    <c:if test="${img.imgType == 0 and empty editThumbUrl}">
                      <c:set var="editThumbUrl" value="${pageContext.request.contextPath}/uploads/products/${img.imgFile}" />
                    </c:if>
                  </c:forEach>
                </c:if>
                <div class="seller-product-thumb-preview <c:if test='${empty editThumbUrl}'>hidden</c:if>" id="thumbPreview">
                  <img id="thumbPreviewImg" alt="대표 이미지 미리보기" <c:if test="${not empty editThumbUrl}">src="${editThumbUrl}"</c:if> />
                  <div class="seller-product-thumb-preview-meta">
                    <div class="seller-product-thumb-preview-title">대표 이미지</div>
                    <div class="seller-product-thumb-preview-sub">상품 목록과 상세 상단에 먼저 보여줘요</div>
                  </div>
                  <button type="button" class="seller-mini-btn" id="thumbRemoveBtn">삭제</button>
                </div>
                <p class="seller-product-error hidden" id="thumbError" aria-live="polite"></p>
              </div>

              <div class="seller-product-upload-block">
                <div class="seller-product-upload-head">
                  <strong>상세 이미지</strong>
                  <span class="seller-product-upload-desc">상품 설명 아래에 노출돼요</span>
                </div>
                <div class="seller-product-upload-box seller-product-upload-box--multi" id="detailBox" tabindex="0" role="button" aria-label="상세 이미지 업로드">
                  <div class="seller-product-upload-icon" aria-hidden="true">
                    <span class="material-icons-outlined">collections</span>
                  </div>
                  <div class="seller-product-upload-text">
                    <div class="seller-product-upload-title">상세 이미지 추가 (최대 5장)</div>
                    <div class="seller-product-upload-sub">여러 장 선택하면 순서대로 저장돼요</div>
                  </div>
                  <button type="button" class="seller-mini-btn seller-mini-btn--primary" id="detailAddBtn">이미지 추가</button>
                </div>
                <input type="file" id="detailImageInput" name="detailImages" accept="image/*" multiple="multiple" style="display:none;" />

                <div class="seller-product-detail-thumbs" id="detailThumbs" aria-label="상세 이미지 미리보기">
                  <c:if test="${editMode}">
                    <c:forEach var="img" items="${editImages}" varStatus="st">
                      <c:if test="${img.imgType == 1}">
                        <div class="seller-product-detail-thumb" data-existing-image="true">
                          <img alt="상세 이미지 미리보기" src="${pageContext.request.contextPath}/uploads/products/${img.imgFile}">
                          <div class="seller-product-detail-thumb-meta">
                            <div class="seller-product-detail-thumb-title">기존 상세 이미지 ${st.index + 1}</div>
                            <div class="seller-product-detail-thumb-sub">새 이미지 업로드 시 교체돼요</div>
                          </div>
                        </div>
                      </c:if>
                    </c:forEach>
                  </c:if>
                </div>
                <p class="seller-product-hint">“위/아래” 버튼으로 상세 이미지 순서를 조정할 수 있어요.</p>
              </div>
            </div>
          </section>

          <section class="seller-card seller-product-section" aria-label="옵션 및 재고">
            <header class="seller-product-section-head">
              <div>
                <h3 class="seller-product-section-title">옵션 / 재고</h3>
                <p class="seller-product-section-sub">색상, 사이즈, 재고를 입력해 주세요</p>
              </div>
            </header>

            <div class="seller-product-grid seller-product-grid--2">
              <div class="seller-product-field">
                <label class="seller-product-label" for="colors">색상</label>
                <div class="chip-wrap seller-color-chips" id="sellerColorChips" aria-label="색상 선택(다중 선택 가능)">
                  <label class="chip"><input type="checkbox" name="productColor" value="검정색"><span><i class="color-dot" style="background:#111111;"></i>검정색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="흰색"><span><i class="color-dot" style="background:#ffffff;"></i>흰색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="회색"><span><i class="color-dot" style="background:#8b8f94;"></i>회색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="고동색"><span><i class="color-dot" style="background:#5a3b2e;"></i>고동색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="연갈색"><span><i class="color-dot" style="background:#b88a60;"></i>연갈색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="자주색"><span><i class="color-dot" style="background:#7b1f52;"></i>자주색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="빨강색"><span><i class="color-dot" style="background:#d73333;"></i>빨강색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="연분홍색"><span><i class="color-dot" style="background:#f5c7d3;"></i>연분홍색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="노란색"><span><i class="color-dot" style="background:#f2d348;"></i>노란색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="남색"><span><i class="color-dot" style="background:#203864;"></i>남색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="하늘색"><span><i class="color-dot" style="background:#86c8f2;"></i>하늘색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="국방색"><span><i class="color-dot" style="background:#556b2f;"></i>국방색</span></label>
                  <label class="chip"><input type="checkbox" name="productColor" value="기타"><span><i class="color-dot rainbow"></i>기타색</span></label>
                </div>
              </div>
              <div class="seller-product-field">
                <label class="seller-product-label" for="sizes">사이즈</label>
                <div class="seller-size-groups" aria-label="사이즈 선택(다중 선택 가능)">
                  <div class="seller-size-group">
                    <div class="seller-size-group-title">상의 사이즈</div>
                    <div class="chip-wrap seller-size-chips">
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="85 (작음)"><span>85 (작음)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="90 (보통)"><span>90 (보통)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="95 (보통)"><span>95 (보통)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="100 (여유)"><span>100 (여유)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="105 (넉넉)"><span>105 (넉넉)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="110 (크게)"><span>110 (크게)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="상의 한 가지 사이즈 (FREE)"><span>상의 한 가지 사이즈 (FREE)</span></label>
                    </div>
                  </div>

                  <div class="seller-size-group">
                    <div class="seller-size-group-title">하의 사이즈</div>
                    <div class="chip-wrap seller-size-chips">
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="26 (여성 S)"><span>26 (여성 S)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="28 (여성 M)"><span>28 (여성 M)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="30 (남성 M)"><span>30 (남성 M)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="32 (남성 L)"><span>32 (남성 L)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="34 (넉넉)"><span>34 (넉넉)</span></label>
                      <label class="chip chip--plain"><input type="checkbox" name="productSize" value="하의 한 가지 사이즈 (FREE)"><span>하의 한 가지 사이즈 (FREE)</span></label>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="seller-product-option-actions">
              <button type="button" class="seller-secondary-btn" id="buildOptionsBtn">
                <span class="material-icons-outlined" aria-hidden="true">table_rows</span>
                옵션 조합 생성
              </button>
            </div>

            <div class="seller-product-option-table-wrap">
              <table class="seller-product-option-table" aria-label="옵션 조합 테이블">
                <thead>
                  <tr>
                    <th scope="col">색상</th>
                    <th scope="col">사이즈</th>
                    <th scope="col">재고</th>
                    <th scope="col">품절 처리</th>
                    <th scope="col">행</th>
                  </tr>
                </thead>
                <tbody id="optionBody">
                  <c:choose>
                    <c:when test="${editMode and not empty editOptions}">
                      <c:forEach var="opt" items="${editOptions}">
                        <tr>
                          <td><c:out value="${opt.optionColor}" /></td>
                          <td><c:out value="${opt.optionSize}" /></td>
                          <td><input class="seller-product-option-input" type="text" inputmode="numeric" value="${opt.optionStock}" data-opt="stock"></td>
                          <td>
                            <label class="seller-product-check">
                              <input type="checkbox" data-opt="soldout" <c:if test="${opt.optionStock == 0}">checked</c:if>>
                              <span>품절</span>
                            </label>
                          </td>
                          <td><button type="button" class="seller-mini-btn seller-mini-btn--warn" data-opt-action="remove">삭제</button></td>
                        </tr>
                      </c:forEach>
                    </c:when>
                    <c:otherwise>
                      <tr class="seller-product-option-empty">
                        <td colspan="5">옵션 조합을 생성하면 여기에 표시돼요.</td>
                      </tr>
                    </c:otherwise>
                  </c:choose>
                </tbody>
              </table>
              <p class="seller-product-error hidden" id="optionError" aria-live="polite"></p>
            </div>
          </section>

          <section class="seller-card seller-product-section" aria-label="필터 태그">
            <header class="seller-product-section-head">
              <div>
                <h3 class="seller-product-section-title">필터 태그</h3>
                <p class="seller-product-section-sub">검색 시 필터에 활용되는 태그를 선택해 주세요</p>
              </div>
            </header>

            <div class="seller-filter-grid">
              <div class="seller-product-tag-block seller-product-tag-block--filter">
                <div class="seller-product-tag-title">계절감 (단일 선택)</div>
                <div class="chip-wrap seller-filter-chips" role="radiogroup" aria-label="계절">
                  <label class="season-pill"><input type="radio" name="productSeason" value="따뜻해요" <c:if test="${editSeasonUi == '따뜻해요'}">checked</c:if>><span>따뜻해요</span></label>
                  <label class="season-pill"><input type="radio" name="productSeason" value="시원해요" <c:if test="${editSeasonUi == '시원해요'}">checked</c:if>><span>시원해요</span></label>
                  <label class="season-pill"><input type="radio" name="productSeason" value="사계절 입어요" <c:if test="${editSeasonUi == '사계절 입어요'}">checked</c:if>><span>사계절 입어요</span></label>
                </div>
              </div>

              <div class="seller-product-tag-block seller-product-tag-block--filter">
                <div class="seller-product-tag-title">옷 특징 (최대 3개)</div>
                <div class="chip-wrap seller-filter-chips" id="featureChipWrap" aria-label="옷 특징(복수 선택 가능)">
                  <label class="chip chip--plain"><input type="checkbox" name="clothesFeature" value="주머니 있어요" <c:if test="${editFeatures.contains('주머니 있어요')}">checked</c:if>><span>주머니 있어요</span></label>
                  <label class="chip chip--plain"><input type="checkbox" name="clothesFeature" value="단추 있는 옷이에요" <c:if test="${editFeatures.contains('단추 있는 옷이에요')}">checked</c:if>><span>단추 있는 옷이에요</span></label>
                  <label class="chip chip--plain"><input type="checkbox" name="clothesFeature" value="지퍼 있는 옷이에요" <c:if test="${editFeatures.contains('지퍼 있는 옷이에요')}">checked</c:if>><span>지퍼 있는 옷이에요</span></label>
                  <label class="chip chip--plain"><input type="checkbox" name="clothesFeature" value="허리가 편해요" <c:if test="${editFeatures.contains('허리가 편해요')}">checked</c:if>><span>허리가 편해요</span></label>
                  <label class="chip chip--plain"><input type="checkbox" name="clothesFeature" value="세탁하기 쉬워요" <c:if test="${editFeatures.contains('세탁하기 쉬워요')}">checked</c:if>><span>세탁하기 쉬워요</span></label>
                  <label class="chip chip--plain"><input type="checkbox" name="clothesFeature" value="바람을 막아줘요" <c:if test="${editFeatures.contains('바람을 막아줘요')}">checked</c:if>><span>바람을 막아줘요</span></label>
                </div>
                <p class="seller-product-hint seller-product-hint--tight" id="featureLimitHint">0/3 선택</p>
              </div>
            </div>
          </section>

          <section class="seller-card seller-product-section" aria-label="추가 상품 정보">
            <header class="seller-product-section-head">
              <div>
                <h3 class="seller-product-section-title">추가 상품 정보</h3>
                <p class="seller-product-section-sub">소재, 패턴, 핏, 두께감을 입력해 주세요</p>
              </div>
            </header>
            <div class="seller-product-grid seller-product-grid--2">
              <div class="seller-product-field">
                <label class="seller-product-label" for="productMaterial">소재</label>
                <input id="productMaterial" name="productMaterial" class="seller-product-control" type="text" value="${editMode ? editProduct.productMaterial : ''}" placeholder="예: 면 100%" />
              </div>
              <div class="seller-product-field">
                <label class="seller-product-label" for="productPattern">패턴</label>
                <input id="productPattern" name="productPattern" class="seller-product-control" type="text" value="${editMode ? editProduct.productPattern : ''}" placeholder="예: 무지" />
              </div>
              <div class="seller-product-field">
                <label class="seller-product-label" for="productFit">핏</label>
                <select id="productFit" name="productFit" class="seller-product-control">
                  <option value="">핏을 선택해 주세요</option>
                  <option value="레귤러핏" <c:if test="${editProduct.productFit == '레귤러핏'}">selected</c:if>>레귤러핏</option>
                  <option value="오버핏" <c:if test="${editProduct.productFit == '오버핏'}">selected</c:if>>오버핏</option>
                  <option value="슬림핏" <c:if test="${editProduct.productFit == '슬림핏'}">selected</c:if>>슬림핏</option>
                  <option value="루즈핏" <c:if test="${editProduct.productFit == '루즈핏'}">selected</c:if>>루즈핏</option>
                  <option value="와이드핏" <c:if test="${editProduct.productFit == '와이드핏'}">selected</c:if>>와이드핏</option>
                </select>
              </div>
              <div class="seller-product-field">
                <label class="seller-product-label" for="productThickness">두께감</label>
                <select id="productThickness" name="productThickness" class="seller-product-control">
                  <option value="">두께감을 선택해 주세요</option>
                  <option value="보통" <c:if test="${editProduct.productThickness == '보통'}">selected</c:if>>보통</option>
                  <option value="얇음" <c:if test="${editProduct.productThickness == '얇음'}">selected</c:if>>얇음</option>
                  <option value="두꺼움" <c:if test="${editProduct.productThickness == '두꺼움'}">selected</c:if>>두꺼움</option>
                </select>
              </div>
            </div>
          </section>

          <section class="seller-card seller-product-section" aria-label="쉽게 읽는 상품 설명">
            <header class="seller-product-section-head">
              <div>
                <h3 class="seller-product-section-title">쉽게 읽는 상품 설명</h3>
                <p class="seller-product-section-sub">고령층 사용자도 쉽게 이해할 수 있게 상품을 소개해 주세요</p>
              </div>
              <button type="button" class="seller-secondary-btn" id="generateEasyDescBtn">
                <span class="material-icons-outlined" aria-hidden="true">auto_awesome</span>
                설명 자동 생성
              </button>
            </header>
            <div class="seller-product-grid seller-product-grid--1">
              <div class="seller-product-field">
                <label class="seller-product-label" for="easyOneLine">한 줄 요약</label>
                <textarea id="easyOneLine" name="easyOneLine" class="seller-product-textarea" rows="2" placeholder="예: 부드럽고 가볍게 입기 좋은 가디건이에요">${editMode ? editProduct.easyOneLine : ''}</textarea>
              </div>
              <div class="seller-product-field">
                <label class="seller-product-label" for="easyFor">이런 분께 좋아요</label>
                <textarea id="easyFor" name="easyFor" class="seller-product-textarea" rows="2" placeholder="예: 가볍게 외출할 때 입기 좋아요">${editMode ? editProduct.easyFor : ''}</textarea>
              </div>
              <div class="seller-product-field">
                <label class="seller-product-label" for="easyComfort">입기 편한 점</label>
                <textarea id="easyComfort" name="easyComfort" class="seller-product-textarea" rows="2" placeholder="예: 신축성이 있어 입고 벗기 편해요">${editMode ? editProduct.easyComfort : ''}</textarea>
              </div>
            </div>
          </section>

          <div class="seller-product-actions">
            <button type="button" class="seller-secondary-btn" id="tempSaveBtn">임시 저장</button>
            <button type="submit" class="seller-primary-btn" id="submitBtn"><c:out value="${editMode ? '수정하기' : '등록하기'}" /></button>
          </div>

          <p class="seller-product-form-error hidden" id="formError" aria-live="assertive"></p>
        </form>
      </main>

      <jsp:include page="/WEB-INF/views/seller/layout/seller-footer.jsp" />
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/seller/product-form.js"></script>
</body>
</html>

