/* global document, alert */
(function () {
  function $(id) { return document.getElementById(id); }
  function trim(v) { return (v || '').toString().trim(); }
  function show(el) { if (el) el.hidden = false; }
  function hide(el) { if (el) el.hidden = true; }

  var form = $('sellerSettingsForm');
  var resetBtn = $('settingsResetBtn');

  var ship = {
    zip: $('shipZip'),
    addr1: $('shipAddr1'),
    addr2: $('shipAddr2'),
  };
  var ret = {
    zip: $('returnZip'),
    addr1: $('returnAddr1'),
    addr2: $('returnAddr2'),
  };

  var sameChk = $('sameReturnAddr');
  var returnZipBtn = $('returnZipBtn');

  function setReturnDisabled(disabled) {
    Object.keys(ret).forEach(function (k) {
      if (!ret[k]) return;
      ret[k].disabled = disabled;
    });
    if (returnZipBtn) returnZipBtn.disabled = disabled;
  }

  function openZipSearch(zipEl, addr1El, addr2El) {
    if (typeof daum === 'undefined' || !daum.Postcode) {
      alert('주소 검색을 불러오는 중입니다. 잠시 후 다시 시도해 주세요.');
      return;
    }
    new daum.Postcode({
      oncomplete: function (data) {
        var fullAddr = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
        zipEl.value = data.zonecode;
        addr1El.value = fullAddr;
        if (addr2El) addr2El.focus();
        zipEl.dispatchEvent(new Event('input', { bubbles: true }));
        addr1El.dispatchEvent(new Event('input', { bubbles: true }));
      }
    }).open();
  }

  var shipZipBtn = $('shipZipBtn');
  if (shipZipBtn && ship.zip && ship.addr1) {
    shipZipBtn.addEventListener('click', function () {
      openZipSearch(ship.zip, ship.addr1, ship.addr2);
    });
  }
  if (returnZipBtn && ret.zip && ret.addr1) {
    returnZipBtn.addEventListener('click', function () {
      if (returnZipBtn.disabled) return;
      openZipSearch(ret.zip, ret.addr1, ret.addr2);
    });
  }

  function copyShipToReturn() {
    if (ret.zip) ret.zip.value = ship.zip ? ship.zip.value : '';
    if (ret.addr1) ret.addr1.value = ship.addr1 ? ship.addr1.value : '';
    if (ret.addr2) ret.addr2.value = ship.addr2 ? ship.addr2.value : '';
  }

  if (sameChk) {
    sameChk.addEventListener('change', function () {
      if (sameChk.checked) {
        copyShipToReturn();
        setReturnDisabled(true);
      } else {
        setReturnDisabled(false);
      }
    });
  }

  if (sameChk && sameChk.checked) {
    copyShipToReturn();
    setReturnDisabled(true);
  }

  ['zip', 'addr1', 'addr2'].forEach(function (key) {
    if (!ship[key]) return;
    ship[key].addEventListener('input', function () {
      if (!sameChk || !sameChk.checked) return;
      copyShipToReturn();
    });
  });

  function validate() {
    var ok = true;
    var storeName = $('storeName');
    var managerName = $('managerName');
    var csPhone = $('csPhone');
    var csEmail = $('csEmail');
    var shipFee = $('shipFee');
    var freeOver = $('freeOver');

    var errStore = $('errStoreName');
    var errManager = $('errManagerName');
    var errPhone = $('errCsPhone');
    var errEmail = $('errCsEmail');
    var errShipFee = $('errShipFee');
    var errFreeOver = $('errFreeOver');

    [errStore, errManager, errPhone, errEmail, errShipFee, errFreeOver].forEach(hide);

    if (!storeName || !trim(storeName.value)) { show(errStore); ok = false; }
    if (!managerName || !trim(managerName.value)) { show(errManager); ok = false; }
    if (!csPhone || !trim(csPhone.value)) { show(errPhone); ok = false; }

    var emailVal = csEmail ? trim(csEmail.value) : '';
    var emailOk = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailVal);
    if (!csEmail || !emailVal || !emailOk) { show(errEmail); ok = false; }

    if (!shipFee || trim(shipFee.value) === '') { show(errShipFee); ok = false; }
    if (!freeOver || trim(freeOver.value) === '') { show(errFreeOver); ok = false; }

    return ok;
  }

  if (resetBtn) {
    resetBtn.addEventListener('click', function () {
      if (form) form.reset();
      if (sameChk) sameChk.checked = false;
      setReturnDisabled(false);
      ['errStoreName', 'errManagerName', 'errCsPhone', 'errCsEmail', 'errShipFee', 'errFreeOver']
        .forEach(function (id) { hide($(id)); });
      alert('입력값이 초기화되었습니다.');
    });
  }

  var logoInput = $('sellerLogoFile');
  var logoFileName = $('sellerLogoFileName');
  var profilePreview = $('sellerProfilePreview');
  var profilePlaceholder = $('sellerProfilePlaceholder');

  if (logoInput && profilePreview) {
    logoInput.addEventListener('change', function () {
      var f = logoInput.files && logoInput.files[0];
      if (!f) {
        if (logoFileName) logoFileName.value = '';
        return;
      }
      if (logoFileName) logoFileName.value = f.name;
      if (!f.type || f.type.indexOf('image/') !== 0) return;
      var reader = new FileReader();
      reader.onload = function (ev) {
        profilePreview.src = ev.target.result;
        profilePreview.classList.add('seller-settings-profile-avatar--visible');
        if (profilePlaceholder) profilePlaceholder.classList.add('is-hidden');
      };
      reader.readAsDataURL(f);
    });
  }

  if (form) {
    form.addEventListener('submit', function (e) {
      if (!validate()) {
        e.preventDefault();
        alert('필수 입력값을 확인해 주세요.');
        return false;
      }
      return true;
    });
  }
})();
