/* global document, alert, console, window, fetch, URLSearchParams */
(function () {
  var panel = document.getElementById('sellerNotificationPanel');
  if (!panel) return;

  var closeBtn = document.getElementById('sellerNotificationCloseBtn');
  var dim = document.getElementById('sellerNotificationDim');
  var tabs = panel.querySelectorAll('.seller-notification-tab');
  
  var listView = document.getElementById('sellerNotificationListView');
  var detailView = document.getElementById('sellerNotificationDetailView');
  var listEl = document.getElementById('sellerNotificationList');
  var emptyEl = document.getElementById('sellerNotificationEmpty');
  var detailEl = document.getElementById('sellerNotificationDetail');

  var backBtn = document.getElementById('sellerNotificationBackBtn');
  var kindBadge = document.getElementById('sellerNotificationDetailKind');
  var dateEl = document.getElementById('sellerNotificationDetailDate');

  var activeKind = 'all';
  var selectedId = null;
  var notificationData = []; 

  var ctx = document.body.getAttribute('data-context-path') || '/ondam';
  var apiUrl = ctx + '/seller/notification';

  function fetchNotifications() {
        // 💡 [추가] headers에 'X-Requested-With' 암호 추가
        fetch(apiUrl + '?action=list', {
            headers: { 'X-Requested-With': 'XMLHttpRequest' }
        })
            .then(function(res) { return res.json(); })
            .then(function(json) {
                notificationData = json;
                renderList();
            })
            .catch(function(err) { console.error("알림 로드 실패:", err); });
    }

  function kindText(kind) {
    if (kind === 'inquiry') return '문의';
    if (kind === 'order') return '주문';
    if (kind === 'review') return '리뷰';
    return '전체';
  }

  function escapeHtml(s) {
    return String(s || '').replaceAll('&', '&amp;').replaceAll('<', '&lt;').replaceAll('>', '&gt;').replaceAll('"', '&quot;').replaceAll("'", '&#39;');
  }

  function openPanel() {
    panel.classList.remove('hidden');
    panel.setAttribute('aria-hidden', 'false');
    document.body.classList.add('seller-notification-open');
    document.body.style.overflow = 'hidden';
    showList();
    fetchNotifications(); 
  }

  function closePanel() {
    document.body.classList.remove('seller-notification-open');
    document.body.style.overflow = '';
    window.setTimeout(function () {
      panel.classList.add('hidden');
      panel.setAttribute('aria-hidden', 'true');
    }, 180);
  }

  function togglePanel() {
    if (panel.classList.contains('hidden')) openPanel();
    else closePanel();
  }

  document.addEventListener('click', function (e) {
    var t = e.target;
    if (!t) return;
    var btn = t.closest('#sellerHeaderNotifyBtn');
    if (!btn) return;
    e.preventDefault();
    e.stopImmediatePropagation();
    togglePanel();
  }, true);

  if (closeBtn) closeBtn.addEventListener('click', closePanel);
  if (dim) dim.addEventListener('click', closePanel);
  document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape' && document.body.classList.contains('seller-notification-open')) {
      closePanel();
    }
  });

  function setActiveTab(kind) {
    activeKind = kind;
    tabs.forEach(function (b) {
      b.classList.toggle('active', b.getAttribute('data-kind') === kind);
    });
  }

  // 💡 [버그 1 해결] 탭 클릭 시 리스트 화면으로 강제 전환
  tabs.forEach(function (b) {
    b.addEventListener('click', function () {
      showList(); // 상세화면 빠져나가기
      setActiveTab(b.getAttribute('data-kind') || 'all');
      renderList();
    });
  });

  function filtered() {
    if (activeKind === 'all') return notificationData.slice();
    return notificationData.filter(function (n) { return n.kind === activeKind; });
  }

  function kindClass(kind) {
    if (kind === 'inquiry') return 'kind--inquiry';
    if (kind === 'order') return 'kind--order';
    if (kind === 'review') return 'kind--review';
    return '';
  }
  function statusText(n) {
    if (n.kind === 'inquiry') return n.answered ? '답변 완료' : '답변 대기';
    if (n.kind === 'order') return n.status === 'need' ? '처리 필요' : '미확인';
    if (n.kind === 'review') return '확인 완료';
    return '';
  }
  function statusClass(n) {
    var t = statusText(n);
    if (t.includes('대기')) return 'status--pending';
    if (t.includes('처리')) return 'status--need';
    if (t.includes('완료')) return 'status--done';
    return '';
  }

  function renderList() {
    var items = filtered();
    if (listEl) listEl.innerHTML = '';
    if (emptyEl) emptyEl.classList.toggle('hidden', items.length !== 0);

    items.forEach(function (n) {
      var div = document.createElement('div');
      div.className = 'seller-notification-item';
      div.setAttribute('data-id', n.id);

      var top = '<div class="seller-notification-item__top">' +
                '  <div class="seller-notification-item__badges">' +
                '    <span class="seller-notification-kind ' + kindClass(n.kind) + '">' + kindText(n.kind) + '</span>' +
                '    <span class="seller-notification-status ' + statusClass(n) + '">' + escapeHtml(statusText(n)) + '</span>' +
                '  </div>' +
                '  <span class="seller-notification-date">' + escapeHtml(n.date) + '</span>' +
                '</div>';

      var title = '', sub = '';
      if (n.kind === 'inquiry') {
        title = escapeHtml(n.product);
        sub = '"' + escapeHtml(n.body) + '"';
      } else if (n.kind === 'order') {
        title = escapeHtml(n.orderNo);
        sub = escapeHtml(n.author + ' / ' + n.orderType + ' / ' + n.payMethod) + '<br>' + escapeHtml(n.title);
      } else {
        title = escapeHtml(n.product);
        sub = '"' + escapeHtml(n.body) + '"';
      }

      div.innerHTML = top +
        '<div class="seller-notification-item__title">' + title + '</div>' +
        '<div class="seller-notification-item__sub">' + sub + '</div>' +
        (n.kind === 'review' ? '<div class="seller-notification-stars">' + '★'.repeat(n.rating) + '☆'.repeat(5 - n.rating) + '</div>' : '');

      listEl.appendChild(div);
    });
  }

  function showList() {
    if (detailView) detailView.classList.add('hidden');
    if (listView) listView.classList.remove('hidden');
    selectedId = null;
    if (detailEl) detailEl.innerHTML = '';
  }

  function showDetail(n) {
    selectedId = n.id;
    if (listView) listView.classList.add('hidden');
    if (detailView) detailView.classList.remove('hidden');

    if (kindBadge) kindBadge.textContent = kindText(n.kind);
    if (dateEl) dateEl.textContent = n.date;
    if (detailEl) detailEl.innerHTML = renderDetailHtml(n);
  }

  // 💡 [버그 2 해결] dashboard.js와의 충돌을 막기 위해 모든 버튼의 속성을 data-noti-action으로 변경
  function renderDetailHtml(n) {
    if (n.kind === 'inquiry') return renderInquiryDetail(n);
    if (n.kind === 'order') return renderOrderDetail(n);
    return renderReviewDetail(n);
  }

  function renderInquiryDetail(n) {
    var info =
      '<div class="seller-notification-card">' +
      ' <div class="seller-notification-card__title">' + escapeHtml(n.product) + '</div>' +
      ' <div class="seller-notification-kv">' +
      '  <div class="row"><span class="k">작성자</span><span class="v">' + escapeHtml(n.author) + '</span></div>' +
      '  <div class="row"><span class="k">주문번호</span><span class="v">' + escapeHtml(n.orderNo) + '</span></div>' +
      '  <div class="row"><span class="k">옵션</span><span class="v">' + escapeHtml(n.option) + '</span></div>' +
      ' </div>' +
      ' <div class="seller-notification-quote">"' + escapeHtml(n.body) + '"</div>' +
      '</div>';

    if (!n.answered) {
      return info +
        '<div class="seller-notification-card seller-notification-answer" data-answer-state="pending">' +
        ' <div class="seller-notification-card__title">답변 달기</div>' +
        ' <div class="seller-notification-quote" style="margin-top:10px;">답변 대기 상태예요.</div>' +
        ' <div style="margin-top:10px;"><textarea id="notifAnswerText" placeholder="짧고 친절하게 답변해 주세요"></textarea></div>' +
        ' <div class="seller-notification-templates">' +
        '  <button type="button" class="seller-notification-template" data-template="문의 주셔서 감사합니다. 확인 후 안내드릴게요.">감사/확인</button>' +
        '  <button type="button" class="seller-notification-template" data-template="배송은 곧 시작될 예정입니다.">배송 안내</button>' +
        '  <button type="button" class="seller-notification-template" data-template="현재 정상 판매 중입니다.">판매 안내</button>' +
        '  <button type="button" class="seller-notification-template" data-template="불편을 드려 죄송합니다. 확인 후 안내드릴게요.">사과/확인</button>' +
        ' </div>' +
        ' <div class="seller-notification-actions">' +
        '  <button type="button" class="seller-notification-mini-btn seller-notification-mini-btn--primary" data-noti-action="answer-submit">등록</button>' +
        ' </div>' +
        '</div>';
    }

    return info +
      '<div class="seller-notification-card seller-notification-answer" data-answer-state="done">' +
      ' <div class="seller-notification-card__title">판매자 답변</div>' +
      ' <div class="seller-notification-quote">' + escapeHtml(n.answer) + '</div>' +
      ' <div class="seller-notification-kv" style="margin-top:10px;">' +
      '  <div class="row"><span class="k">답변일</span><span class="v">' + escapeHtml(n.answerDate || '-') + '</span></div>' +
      ' </div>' +
      '</div>';
  }

  function renderOrderDetail(n) {
    return (
      '<div class="seller-notification-card">' +
      ' <div class="seller-notification-card__title">주문 상세</div>' +
      ' <div class="seller-notification-kv">' +
      '  <div class="row"><span class="k">주문번호</span><span class="v">' + escapeHtml(n.orderNo) + '</span></div>' +
      '  <div class="row"><span class="k">주문자</span><span class="v">' + escapeHtml(n.author) + '</span></div>' +
      '  <div class="row"><span class="k">결제수단</span><span class="v">' + escapeHtml(n.payMethod) + '</span></div>' +
      '  <div class="row"><span class="k">상품</span><span class="v">' + escapeHtml(n.product + ' / ' + n.qty + '개') + '</span></div>' +
      '  <div class="row"><span class="k">요청사항</span><span class="v">' + escapeHtml(n.request) + '</span></div>' +
      ' </div>' +
      ' <div class="seller-notification-actions">' +
      '  <button type="button" class="seller-notification-mini-btn" data-noti-action="order-detail">주문 상세 보기</button>' +
      '  <button type="button" class="seller-notification-mini-btn seller-notification-mini-btn--primary" data-noti-action="order-ship">배송 관리로 이동</button>' +
      ' </div>' +
      '</div>'
    );
  }

  function renderReviewDetail(n) {
    var imgHtml = '';
    if (n.images && n.images.length) {
      imgHtml = '<div class="seller-notification-card">' +
        '<div class="seller-notification-card__title">리뷰 이미지</div>' +
        '<div style="display:flex;gap:10px;flex-wrap:wrap;margin-top:10px;">' +
        n.images.map(function (src) {
          return '<img src="' + escapeHtml(src) + '" alt="리뷰 이미지" style="width:120px;height:120px;object-fit:cover;border-radius:16px;border:1px solid rgba(15,23,42,0.10)">';
        }).join('') +
        '</div></div>';
    }

    return (
      '<div class="seller-notification-card">' +
      ' <div class="seller-notification-card__title">' + escapeHtml(n.product) + '</div>' +
      ' <div class="seller-notification-kv">' +
      '  <div class="row"><span class="k">작성자</span><span class="v">' + escapeHtml(n.author) + '</span></div>' +
      '  <div class="row"><span class="k">평점</span><span class="v">' + ('★'.repeat(n.rating) + '☆'.repeat(5 - n.rating)) + '</span></div>' +
      ' </div>' +
      ' <div class="seller-notification-quote">"' + escapeHtml(n.body) + '"</div>' +
      '</div>' + imgHtml
    );
  }

  if (backBtn) backBtn.addEventListener('click', showList);

  document.addEventListener('click', function (e) {
    var item = e.target.closest('.seller-notification-item');
    if (item && panel.contains(item)) {
      var id = item.getAttribute('data-id');
      var n = notificationData.find(function (x) { return x.id === id; });
      if (n) showDetail(n);
      return;
    }

    if (!panel.contains(e.target)) return;
    var tab = e.target.closest('.seller-notification-tab');
    if (tab) return;

    var tpl = e.target.closest('.seller-notification-template');
    if (tpl) {
      var msg = tpl.getAttribute('data-template') || '';
      var ta = document.getElementById('notifAnswerText');
      if (ta) { ta.value = msg; ta.focus(); }
      return;
    }

    // 💡 변경점: data-noti-action 으로 수집 (dashboard.js 간섭 회피)
    var actionBtn = e.target.closest('[data-noti-action]');
    if (!actionBtn) return;
    var act = actionBtn.getAttribute('data-noti-action');
    if (!selectedId) return;

    var cur = notificationData.find(function (x) { return x.id === selectedId; });
    if (!cur) return;

	if (act === 'answer-submit') {
	      var text = (document.getElementById('notifAnswerText') || {}).value || '';
	      text = text.trim();
	      if (!text) { alert('답변 내용을 입력해 주세요.'); return; }
	      
	      actionBtn.disabled = true;
	      var formData = new URLSearchParams();
	      var inqPk = cur.id.replace('INQ-', '');
	      formData.append('inquiryNo', inqPk);
	      formData.append('answerContent', text);

	      // 💡 [추가] headers에 'X-Requested-With' 암호 추가
	      fetch(apiUrl + '?action=answer', {
	          method: 'POST',
	          headers: { 
	              'Content-Type': 'application/x-www-form-urlencoded',
	              'X-Requested-With': 'XMLHttpRequest' 
	          },
	          body: formData.toString()
	      }).then(function(res) { return res.json(); })
	        .then(function(data) {
	            if(data.success) {
	                alert('답변이 등록되었습니다.');
	                fetchNotifications(); 
	                showList(); 
	            } else {
	                alert('오류가 발생했습니다.');
	                actionBtn.disabled = false;
	            }
	        });
	      return;
	    }

    // 💡 [버그 3 해결] 주문 관리 페이지 경로 수정 (/seller/order)
	if (act === 'order-detail' || act === 'order-ship') {
	      // 'ORD-102' 같은 id에서 'ORD-'를 지우고 숫자(PK)만 추출
	      var orderPk = cur.id.replace('ORD-', '');
	      
	      // 원하는 경로로 완벽하게 이동!
	      window.location.href = ctx + '/seller/order?action=detail&orderNo=' + orderPk; 
	      return;
	    }
  });

  // init
  setActiveTab('all');
})();