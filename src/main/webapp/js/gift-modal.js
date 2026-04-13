(function () {
    var confirmBtn = document.getElementById('confirmGiftBtn');
    var toastTimer = null;
    var toastFallbackTimer = null;

    function showErrorToast(message) {
        var el = document.getElementById('option-toast');
        if (!el) return;
        var textEl = el.querySelector('.option-toast__text');
        var iconEl = el.querySelector('.option-toast__icon');
        if (textEl) textEl.textContent = message || '';
        if (iconEl) iconEl.textContent = 'error';
        el.classList.remove('option-toast--success');
        el.classList.add('option-toast--error');
        clearTimeout(toastTimer);
        clearTimeout(toastFallbackTimer);

        el.classList.remove('hidden', 'option-toast--hiding', 'option-toast--show');
        el.setAttribute('aria-hidden', 'false');
        requestAnimationFrame(function () {
            requestAnimationFrame(function () {
                el.classList.add('option-toast--show');
            });
        });

        toastTimer = setTimeout(function () {
            el.classList.remove('option-toast--show');
            el.classList.add('option-toast--hiding');

            var done = false;
            function cleanup() {
                if (done) return;
                done = true;
                el.removeEventListener('transitionend', onEnd);
                clearTimeout(toastFallbackTimer);
                el.classList.add('hidden');
                el.classList.remove('option-toast--hiding');
                el.setAttribute('aria-hidden', 'true');
            }
            function onEnd(e) {
                if (e.target !== el) return;
                if (e.propertyName !== 'opacity' && e.propertyName !== 'transform') return;
                cleanup();
            }
            el.addEventListener('transitionend', onEnd);
            toastFallbackTimer = setTimeout(cleanup, 400);
        }, 1800);
    }

    if (confirmBtn) {
        confirmBtn.addEventListener('click', function () {
            var selectedBtn = document.querySelector('.gift-person-btn.active');
			
			console.log('selectedBtn:', selectedBtn);
			    console.log('hiddenProductNo:', document.getElementById('hiddenProductNo') ? document.getElementById('hiddenProductNo').value : 'null');
			    console.log('hiddenOptionNo:', document.getElementById('hiddenOptionNo') ? document.getElementById('hiddenOptionNo').value : 'null');
			    console.log('hiddenQuantity:', document.getElementById('hiddenQuantity') ? document.getElementById('hiddenQuantity').value : 'null');
				
            if (!selectedBtn) {
                showErrorToast('선물 받을 사람을 선택해주세요.');
                return;
            }

            var receiverNo  = selectedBtn.getAttribute('data-user-no');
            var contextPath = document.body.getAttribute('data-context-path') || '';

            var productNoEl = document.getElementById('hiddenProductNo');
            var optionNoEl  = document.getElementById('hiddenOptionNo');
            var quantityEl  = document.getElementById('hiddenQuantity');

            var productNo = productNoEl ? productNoEl.value : '';
            var optionNo  = optionNoEl  ? optionNoEl.value  : '0';
            var quantity  = quantityEl  ? quantityEl.value  : '1';

            if (!productNo) {
                showErrorToast('상품 정보를 불러올 수 없어요. 다시 시도해주세요.');
                return;
            }

            // optionNo가 없으면 옵션 미선택 상태
            if (!optionNo || optionNo === '0') {
                showErrorToast('색상과 사이즈를 먼저 선택해주세요.');
                return;
            }

            var form = document.createElement('form');
            form.method = 'GET';
            form.action = contextPath + '/payment';

            var params = [
                ['productNo',       productNo],
                ['productOptionNo', optionNo],
                ['quantity',        quantity],
                ['isGift',          'true'],
                ['receiverNo',      receiverNo]
            ];

            params.forEach(function (pair) {
                var input = document.createElement('input');
                input.type  = 'hidden';
                input.name  = pair[0];
                input.value = pair[1];
                form.appendChild(input);
            });

            document.body.appendChild(form);
            form.submit();
        });
    }
})();