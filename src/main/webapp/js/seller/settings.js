/* global document, alert, console */
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

  function setReturnDisabled(disabled) {
    Object.keys(ret).forEach(function (k) {
      if (!ret[k]) return;
      ret[k].disabled = disabled;
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
      console.log('[SellerSettings] sameReturnAddr change (dummy)', sameChk.checked);
    });
  }

  // If shipping changes while checkbox is on → keep syncing
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
      alert('입력값이 초기화되었습니다. (더미)');
    });
  }

  if (form) {
    form.addEventListener('submit', function (e) {
      e.preventDefault();
      if (!validate()) {
        alert('필수 입력값을 확인해 주세요.');
        return;
      }

      var payload = {
        storeName: trim($('storeName') ? $('storeName').value : ''),
        managerName: trim($('managerName') ? $('managerName').value : ''),
        csPhone: trim($('csPhone') ? $('csPhone').value : ''),
        csEmail: trim($('csEmail') ? $('csEmail').value : ''),
        bizNo: trim($('bizNo') ? $('bizNo').value : ''),
        shipFee: trim($('shipFee') ? $('shipFee').value : ''),
        freeOver: trim($('freeOver') ? $('freeOver').value : ''),
        courier: $('courier') ? $('courier').value : '',
        sameReturnAddr: !!(sameChk && sameChk.checked),
      };
      console.log('[SellerSettings] save (dummy)', payload);
      alert('저장되었습니다. (더미)\n\n실제 저장은 아직 연동되지 않았어요.');
    });
  }
})();

