(function () {
    var confirmBtn = document.getElementById('confirmGiftBtn');

    if (confirmBtn) {
        confirmBtn.addEventListener('click', function () {
            var selectedBtn = document.querySelector('.gift-person-btn.active');
			
			console.log('selectedBtn:', selectedBtn);
			    console.log('hiddenProductNo:', document.getElementById('hiddenProductNo') ? document.getElementById('hiddenProductNo').value : 'null');
			    console.log('hiddenOptionNo:', document.getElementById('hiddenOptionNo') ? document.getElementById('hiddenOptionNo').value : 'null');
			    console.log('hiddenQuantity:', document.getElementById('hiddenQuantity') ? document.getElementById('hiddenQuantity').value : 'null');
				
            if (!selectedBtn) {
                alert('선물 받을 사람을 선택해주세요.');
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
                alert('상품 정보를 불러올 수 없어요. 다시 시도해주세요.');
                return;
            }

            // optionNo가 없으면 옵션 미선택 상태
            if (!optionNo || optionNo === '0') {
                alert('색상과 사이즈를 먼저 선택해주세요.');
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