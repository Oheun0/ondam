document.addEventListener("DOMContentLoaded", function () {
  bindSelectableCards();
  bindUserIdInputReset();
});

function bindSelectableCards() {
  var optionCards = document.querySelectorAll(".option-card");

  optionCards.forEach(function (card) {
    card.addEventListener("click", function () {
      var input = card.querySelector("input");
      if (!input) return;

      if (input.type === "radio") {
        var groupName = input.name;

        document.querySelectorAll('input[name="' + groupName + '"]').forEach(function (radio) {
          var parentCard = radio.closest(".option-card");
          if (parentCard) {
            parentCard.classList.remove("active");
          }
        });

        input.checked = true;
        card.classList.add("active");
      } else if (input.type === "checkbox") {
        input.checked = !input.checked;

        if (input.checked) {
          card.classList.add("active");
        } else {
          card.classList.remove("active");
        }
      }
    });
  });
}

function checkUserId() {
  var userIdInput = document.getElementById("userId");
  var msg = document.getElementById("idCheckMessage");

  if (!userIdInput || !msg) return;

  var userId = userIdInput.value.trim();

  msg.style.display = "block";
  msg.classList.remove("success", "error");

  if (userId === "") {
    msg.classList.add("error");
    msg.textContent = "아이디를 먼저 입력해주세요.";
    return;
  }

  if (userId.length < 4) {
    msg.classList.add("error");
    msg.textContent = "아이디는 4자 이상 입력해주세요.";
    return;
  }

  /* 예시용 가짜 중복체크 */
  if (userId === "admin" || userId === "test" || userId === "ondam") {
    msg.classList.add("error");
    msg.textContent = "이미 사용 중인 아이디예요.";
  } else {
    msg.classList.add("success");
    msg.textContent = "사용할 수 있는 아이디예요.";
  }
}

function bindUserIdInputReset() {
  var userIdInput = document.getElementById("userId");
  var msg = document.getElementById("idCheckMessage");

  if (!userIdInput || !msg) return;

  userIdInput.addEventListener("input", function () {
    msg.style.display = "none";
    msg.textContent = "";
    msg.classList.remove("success", "error");
  });
}