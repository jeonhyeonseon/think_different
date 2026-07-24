document.addEventListener("DOMContentLoaded", function () {
    const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const PASSWORD_REGEX = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).{8,}$/;

    let isLoginIdChecked = false;
    let isEmailChecked = false;

    const nameInput = document.querySelector("input[name='name']");

    const loginIdInput = document.getElementById("loginId");
    const loginIdGroup = document.getElementById("loginId-group");
    const loginIdCheckedInput = document.getElementById("loginIdChecked");
    const loginIdMsg = document.getElementById("loginId-msg");
    const checkIdBtn = document.getElementById("check-id");

    const passwordInput = document.getElementById("password");
    const passwordFormatMsg = document.getElementById("password-format-msg");
    const passwordConfirmInput = document.getElementById("passwordConfirm");
    const passwordMsg = document.getElementById("password-msg");

    const phoneInput = document.getElementById("phone");

    const emailInput = document.getElementById("email");
    const emailGroup = document.getElementById("email-group");
    const emailCheckedInput = document.getElementById("emailChecked");
    const emailMsg = document.getElementById("email-msg");
    const checkEmailBtn = document.getElementById("check-email");

    const joinForm = document.querySelector(".join-form");

    function setMsg(el, text, isError) {
        el.textContent = text;
        el.classList.toggle("error-msg", !!isError);
        el.classList.toggle("success-msg", !isError && !!text);
    }

    // 아이디 중복 체크
    checkIdBtn.addEventListener("click", function () {
        const loginId = loginIdInput.value.trim();
        if (!loginId) {
            alert("아이디를 입력해주세요.");
            return;
        }

        fetch(`/members/exists/loginId?loginId=${encodeURIComponent(loginId)}`)
            .then(function (response) {
                return response.json();
            })
            .then(function (duplicated) {
                const isDuplicated = duplicated === true;
                setMsg(loginIdMsg, isDuplicated ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.", isDuplicated);
                loginIdGroup.classList.toggle("checked", !isDuplicated);
                isLoginIdChecked = !isDuplicated;
                loginIdCheckedInput.value = isLoginIdChecked ? "true" : "false";
            });
    });

    loginIdInput.addEventListener("input", function () {
        isLoginIdChecked = false;
        loginIdCheckedInput.value = "false";
        loginIdGroup.classList.remove("checked");

        const loginId = loginIdInput.value.trim();
        if (!loginId) {
            setMsg(loginIdMsg, "", false);
        } else {
            setMsg(loginIdMsg, "아이디 중복 체크를 해주세요.", true);
        }
    });

    // 비밀번호 형식 체크
    passwordInput.addEventListener("input", function () {
        const pw = passwordInput.value;
        if (!pw) {
            setMsg(passwordFormatMsg, "", false);
        } else if (!PASSWORD_REGEX.test(pw)) {
            setMsg(passwordFormatMsg, "비밀번호는 8자 이상이며 영문, 숫자, 특수문자를 모두 포함해야 합니다.", true);
        } else {
            setMsg(passwordFormatMsg, "사용 가능한 비밀번호입니다.", false);
        }
        checkPasswordMatch();
    });

    // 비밀번호 확인 일치 여부
    passwordConfirmInput.addEventListener("input", checkPasswordMatch);

    function checkPasswordMatch() {
        const pw = passwordInput.value;
        const pwConfirm = passwordConfirmInput.value;

        if (pwConfirm && pw !== pwConfirm) {
            setMsg(passwordMsg, "비밀번호가 일치하지 않습니다.", true);
        } else if (pwConfirm) {
            setMsg(passwordMsg, "비밀번호가 일치합니다.", false);
        } else {
            setMsg(passwordMsg, "", false);
        }
    }

    // 전화번호 자동 하이픈
    phoneInput.addEventListener("input", function () {
        let number = phoneInput.value;

        number = number.replace(/[^0-9]/g, "");

        if (number.length < 4) {
        } else if (number.length < 8) {
            number = number.replace(/(\d{3})(\d+)/, "$1-$2");
        } else if (number.length < 11) {
            number = number.replace(/(\d{3})(\d{4})(\d+)/, "$1-$2-$3");
        } else {
            number = number.replace(/(\d{3})(\d{4})(\d{4}).*/, "$1-$2-$3");
        }

        phoneInput.value = number;
    });

    // 이메일 형식 + 중복 체크
    checkEmailBtn.addEventListener("click", function () {
        const email = emailInput.value.trim();

        if (!email) {
            alert("이메일을 입력해주세요.");
            return;
        }

        if (!EMAIL_REGEX.test(email)) {
            setMsg(emailMsg, "이메일 형식이 올바르지 않습니다.", true);
            emailGroup.classList.remove("checked");
            isEmailChecked = false;
            emailCheckedInput.value = "false";
            return;
        }

        fetch(`/members/exists/email?email=${encodeURIComponent(email)}`)
            .then(function (response) {
                return response.json();
            })
            .then(function (duplicated) {
                const isDuplicated = duplicated === true;
                setMsg(emailMsg, isDuplicated ? "이미 사용 중인 이메일입니다." : "사용 가능한 이메일입니다.", isDuplicated);
                emailGroup.classList.toggle("checked", !isDuplicated);
                isEmailChecked = !isDuplicated;
                emailCheckedInput.value = isEmailChecked ? "true" : "false";
            });
    });

    emailInput.addEventListener("input", function () {
        isEmailChecked = false;
        emailCheckedInput.value = "false";
        emailGroup.classList.remove("checked");

        const email = emailInput.value.trim();
        if (!email) {
            setMsg(emailMsg, "", false);
        } else if (!EMAIL_REGEX.test(email)) {
            setMsg(emailMsg, "이메일 형식이 올바르지 않습니다.", true);
        } else {
            setMsg(emailMsg, "이메일 중복 체크를 해주세요.", true);
        }
    });

    joinForm.addEventListener("submit", function (e) {
        const name = nameInput.value.trim();
        const loginId = loginIdInput.value.trim();
        const pw = passwordInput.value;
        const pwConfirm = passwordConfirmInput.value;
        const phone = phoneInput.value.trim();
        const email = emailInput.value.trim();

        if (!name || !loginId || !pw || !pwConfirm || !phone || !email) {
            e.preventDefault();
            alert("모든 항목을 입력해주세요.");
            return;
        }

        let hasError = false;

        if (!PASSWORD_REGEX.test(pw)) {
            setMsg(passwordFormatMsg, "비밀번호는 8자 이상이며 영문, 숫자, 특수문자를 모두 포함해야 합니다.", true);
            hasError = true;
        }

        if (pw !== pwConfirm) {
            setMsg(passwordMsg, "비밀번호가 일치하지 않습니다.", true);
            hasError = true;
        }

        if (!EMAIL_REGEX.test(email)) {
            setMsg(emailMsg, "이메일 형식이 올바르지 않습니다.", true);
            hasError = true;
        }

        if (!isLoginIdChecked) {
            setMsg(loginIdMsg, "아이디 중복 체크를 완료해주세요.", true);
            hasError = true;
        }

        if (!isEmailChecked) {
            setMsg(emailMsg, "이메일 중복 체크를 완료해주세요.", true);
            hasError = true;
        }

        if (hasError) {
            e.preventDefault();
        }
    });
});
