/* global document, alert, console, window */
(function () {
  var panel = document.getElementById('sellerNotificationPanel');
  if (!panel) return;

  var openBtn = document.getElementById('sellerHeaderNotifyBtn');
  var closeBtn = document.getElementById('sellerNotificationCloseBtn');
  var dim = document.getElementById('sellerNotificationDim');
  var allReadBtn = document.getElementById('sellerNotificationAllReadBtn');

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

  function kindText(kind) {
    if (kind === 'inquiry') return '문의';
    if (kind === 'order') return '주문';
    if (kind === 'review') return '리뷰';
    return '전체';
  }

  function escapeHtml(s) {
    return String(s || '')
      .replaceAll('&', '&amp;')
      .replaceAll('<', '&lt;')
      .replaceAll('>', '&gt;')
      .replaceAll('"', '&quot;')
      .replaceAll("'", '&#39;');
  }

  var data = [
    {
      id: 'N-0001',
      kind: 'inquiry',
      status: 'pending',
      unread: true,
      date: '2026.04.08',
      product: '부드러운 라운드 니트 가디건',
      author: '김지현',
      orderNo: '20260408-0001',
      option: '아이보리 / 95',
      title: '배송은 언제 시작되나요?',
      body: '배송은 언제 시작되나요? 선물이라 일정이 궁금합니다.',
      answered: false,
      answer: '',
      answerDate: '',
    },
    {
      id: 'N-0002',
      kind: 'order',
      status: 'need',
      unread: true,
      date: '2026.04.08',
      orderNo: '20260408-0002',
      author: '성연수',
      orderType: '선물',
      payMethod: '함께지갑 결제',
      product: '편안한 봄 니트 조끼',
      qty: 1,
      request: '문 앞에 놓아주세요',
      title: '배송 준비가 필요해요',
      body: '결제 완료 상태입니다. 배송 준비를 시작해 주세요.',
    },
    {
      id: 'N-0003',
      kind: 'review',
      status: 'pending',
      unread: true,
      date: '2026.04.07',
      product: '부드러운 라운드 니트 가디건',
      author: '성연수',
      rating: 5,
      title: '생각보다 부드럽고 편했어요',
      body: '생각보다 부드럽고 입기 편했어요. 색도 화면이랑 비슷해서 만족합니다.',
      images: [
        '/images/category/comfort-soft.jpg',
      ],
    },
    {
      id: 'N-0004',
      kind: 'inquiry',
      status: 'done',
      unread: false,
      date: '2026.04.06',
      product: '가벼운 데일리 셔츠',
      author: '김가빈',
      orderNo: '20260406-0019',
      option: '남색 / 100',
      title: '사이즈 추천 부탁드려요',
      body: '키 170인데 100 사이즈 괜찮을까요?',
      answered: true,
      answer: '문의 주셔서 감사합니다. 100 사이즈를 추천드려요.',
      answerDate: '2026.04.06',
    },
    {
      id: 'N-0005',
      kind: 'order',
      status: 'need',
      unread: false,
      date: '2026.04.05',
      orderNo: '20260405-0007',
      author: '박민준',
      orderType: '일반',
      payMethod: '카드 결제',
      product: '산뜻한 플라워 블라우스',
      qty: 1,
      request: '경비실에 맡겨주세요',
      title: '출고 처리해주세요',
      body: '배송 준비 중입니다. 출고 처리 후 송장 등록이 필요해요.',
    },
    {
      id: 'N-0006',
      kind: 'review',
      status: 'done',
      unread: false,
      date: '2026.04.02',
      product: '편안한 봄 니트 조끼',
      author: '김지현',
      rating: 4,
      title: '가볍고 좋아요',
      body: '가볍고 편해서 좋아요. 다만 단추가 조금 더 크면 좋겠어요.',
      images: [],
    },
    {
      id: 'N-0007',
      kind: 'inquiry',
      status: 'pending',
      unread: true,
      date: '2026.04.01',
      product: '산뜻한 플라워 블라우스',
      author: '박민준',
      orderNo: '20260401-0003',
      option: '연분홍 / 90',
      title: '세탁 방법이 궁금해요',
      body: '세탁기 사용해도 괜찮나요? 손세탁 권장인가요?',
      answered: false,
      answer: '',
      answerDate: '',
    },
  ];

  function openPanel() {
    panel.classList.remove('hidden');
    panel.setAttribute('aria-hidden', 'false');
    document.body.classList.add('seller-notification-open');
    document.body.style.overflow = 'hidden';
    showList();
    renderList();
  }

  function closePanel() {
    document.body.classList.remove('seller-notification-open');
    document.body.style.overflow = '';
    // 애니메이션 후 숨김
    window.setTimeout(function () {
      panel.classList.add('hidden');
      panel.setAttribute('aria-hidden', 'true');
    }, 180);
  }

  function togglePanel() {
    if (panel.classList.contains('hidden')) openPanel();
    else closePanel();
  }

  // 다른 페이지에 남아있는 "알림 더미 alert" 리스너를 무력화(캡처 단계에서 차단)
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

  tabs.forEach(function (b) {
    b.addEventListener('click', function () {
      setActiveTab(b.getAttribute('data-kind') || 'all');
      renderList();
    });
  });

  function filtered() {
    if (activeKind === 'all') return data.slice();
    return data.filter(function (n) { return n.kind === activeKind; });
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
    if (n.kind === 'review') return n.status === 'done' ? '답변 완료' : '미답변';
    return '';
  }
  function statusClass(n) {
    var t = statusText(n);
    if (t.includes('대기') || t.includes('미답변')) return 'status--pending';
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
      div.className = 'seller-notification-item' + (n.unread ? ' is-unread' : '');
      div.setAttribute('data-id', n.id);

      var top = '';
      top += '<div class="seller-notification-item__top">';
      top += '  <div class="seller-notification-item__badges">';
      top += '    <span class="seller-notification-kind ' + kindClass(n.kind) + '">' + kindText(n.kind) + '</span>';
      top += '    <span class="seller-notification-status ' + statusClass(n) + '">' + escapeHtml(statusText(n)) + '</span>';
      top += '  </div>';
      top += '  <span class="seller-notification-date">' + escapeHtml(n.date) + '</span>';
      top += '</div>';

      var title = '';
      var sub = '';
      if (n.kind === 'inquiry') {
        title = escapeHtml(n.product);
        sub = '"' + escapeHtml(n.title) + '"';
      } else if (n.kind === 'order') {
        title = escapeHtml(n.orderNo);
        sub = escapeHtml(n.author + ' / ' + n.orderType + ' / ' + n.payMethod) + '<br>' + escapeHtml(n.title);
      } else {
        title = escapeHtml(n.product);
        sub = '"' + escapeHtml(n.title) + '"';
      }

      div.innerHTML =
        top +
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

    // unread -> read (dummy)
    n.unread = false;
    renderList();
  }

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
        '  <button type="button" class="seller-notification-mini-btn seller-notification-mini-btn--primary" data-action="answer-submit">등록</button>' +
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
      ' <div class="seller-notification-actions">' +
      '  <button type="button" class="seller-notification-mini-btn" data-action="answer-edit">수정</button>' +
      '  <button type="button" class="seller-notification-mini-btn seller-notification-mini-btn--danger" data-action="answer-delete">삭제</button>' +
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
      '  <button type="button" class="seller-notification-mini-btn" data-action="order-detail">주문 상세 보기</button>' +
      '  <button type="button" class="seller-notification-mini-btn seller-notification-mini-btn--primary" data-action="order-ship">배송 처리하기</button>' +
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
      ' <div class="seller-notification-actions">' +
      '  <button type="button" class="seller-notification-mini-btn seller-notification-mini-btn--primary" data-action="go-review">리뷰 관리로 이동</button>' +
      ' </div>' +
      '</div>' +
      imgHtml
    );
  }

  if (backBtn) backBtn.addEventListener('click', showList);

  document.addEventListener('click', function (e) {
    var item = e.target.closest('.seller-notification-item');
    if (item && panel.contains(item)) {
      var id = item.getAttribute('data-id');
      var n = data.find(function (x) { return x.id === id; });
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

    var actionBtn = e.target.closest('[data-action]');
    if (!actionBtn) return;
    var act = actionBtn.getAttribute('data-action');
    if (!selectedId) return;

    var cur = data.find(function (x) { return x.id === selectedId; });
    if (!cur) return;

    if (act === 'answer-submit') {
      var text = (document.getElementById('notifAnswerText') || {}).value || '';
      text = text.trim();
      if (!text) { alert('답변 내용을 입력해 주세요.'); return; }
      cur.answered = true;
      cur.answer = text;
      cur.answerDate = cur.date;
      cur.status = 'done';
      console.log('[Notification] answer submit (dummy)', cur.id, text);
      showDetail(cur);
      return;
    }
    if (act === 'answer-edit') {
      cur.answered = false;
      console.log('[Notification] answer edit (dummy)', cur.id);
      showDetail(cur);
      return;
    }
    if (act === 'answer-delete') {
      cur.answered = false;
      cur.answer = '';
      cur.answerDate = '';
      console.log('[Notification] answer delete (dummy)', cur.id);
      showDetail(cur);
      return;
    }
    if (act === 'order-detail') {
      console.log('[Notification] order detail (dummy)', cur.orderNo);
      alert('주문 상세 보기는 더미 동작입니다.');
      return;
    }
    if (act === 'order-ship') {
      console.log('[Notification] order ship (dummy)', cur.orderNo);
      alert('배송 처리하기는 더미 동작입니다.');
      return;
    }
    if (act === 'go-review') {
      console.log('[Notification] go review (dummy)');
      alert('리뷰 관리로 이동(더미)\n\npreview?page=seller/review/list');
      var ctx = document.body.getAttribute('data-context-path') || '';
      window.location.href = ctx + '/preview?page=seller/review/list';
      return;
    }
  });

  if (allReadBtn) {
    allReadBtn.addEventListener('click', function () {
      data.forEach(function (n) { n.unread = false; });
      console.log('[Notification] mark all read (dummy)');
      renderList();
      alert('모두 읽음 처리되었습니다. (더미)');
    });
  }

  // init
  setActiveTab('all');
})();

