/* global document, window, confirm */
document.addEventListener('DOMContentLoaded', function() {
    var contextPath = document.body.getAttribute('data-context-path') || '';

    var orderListSection = document.querySelector('.seller-order-list');
    if (orderListSection) {
        orderListSection.addEventListener('click', function(e) {
            var btn = e.target.closest('.seller-order-btn');
            if (!btn) return;
            
            var action = btn.getAttribute('data-action');
            var card = btn.closest('.seller-order-card');
            var orderNo = card.getAttribute('data-order-no');

            if (action === 'detail') {
                window.location.href = contextPath + "/seller/order?action=detail&orderNo=" + orderNo;
            } 
            else if (action === 'ready') {
                if(confirm(orderNo + '번 주문을 [배송 준비 중] 상태로 변경하시겠습니까?')) {
                    window.location.href = contextPath + "/seller/order?action=updateStatusFromList&orderNo=" + orderNo + "&status=ready";
                }
            } 
            else if (action === 'shipStart') {
                if(confirm(orderNo + '번 주문을 [배송 중] 상태로 변경하시겠습니까?')) {
                    window.location.href = contextPath + "/seller/order?action=updateStatusFromList&orderNo=" + orderNo + "&status=shipping";
                }
            } 
            else if (action === 'shipDone') {
                if(confirm(orderNo + '번 주문을 [배송 완료] 상태로 변경하시겠습니까?')) {
                    window.location.href = contextPath + "/seller/order?action=updateStatusFromList&orderNo=" + orderNo + "&status=done";
                }
            }
        });
    }

    var tabs = document.querySelectorAll('.seller-order-tab');
    var cards = document.querySelectorAll('.seller-order-card');

    if (tabs.length > 0) {
        tabs.forEach(function(tab) {
            tab.addEventListener('click', function() {
                tabs.forEach(function(t) { t.classList.remove('active'); });
                this.classList.add('active');

                var targetStatus = this.getAttribute('data-status');
                cards.forEach(function(card) {
                    if (targetStatus === 'all' || card.getAttribute('data-status') === targetStatus) {
                        card.style.display = '';
                    } else {
                        card.style.display = 'none';
                    }
                });
            });
        });

        var urlParams = new URLSearchParams(window.location.search);
        var statusParam = urlParams.get('status');

        if (statusParam === 'ready') {
            var readyTab = document.querySelector('.seller-order-tab[data-status="ready"]');
            
            if (readyTab) {
                readyTab.click();
            } else {
                tabs.forEach(function(tab) {
                    if (tab.innerText.includes('준비 중')) { 
                        tab.click();
                    }
                });
            }
        }
    }

    var pageBtns = document.querySelectorAll('.seller-order-page-btn');
    if (pageBtns.length > 0) {
        pageBtns.forEach(function(btn) {
            btn.addEventListener('click', function() {
                var page = this.getAttribute('data-page');
                if (page) {
                    window.location.href = contextPath + "/seller/order?action=list&page=" + page;
                }
            });
        });
    }
});