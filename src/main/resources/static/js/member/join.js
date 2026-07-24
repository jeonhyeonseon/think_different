$(function () {
    const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const PASSWORD_REGEX = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).{8,}$/;

    let isLoginIdChecked = false;
    let isEmailChecked = false;

    function setMsg($el, text, isError) {
        $el.text(text)
            .toggleClass("error-msg", !!isError)
            .toggleClass("success-msg", !isError && !!text);
    }

    // 아이디 중복 체크
    $("#check-id").on('click', function () {
        const loginId = $("#loginId").val().trim();
        if (!loginId) {
            alert("아이디를 입력해주세요.");
            return;
        }

        $.ajax({
            url: "/members/exists/loginId",
            type: "GET",
            data: { loginId },
            success: function (duplicated) {
                const isDuplicated = (duplicated === true) || (duplicated === "true");
                setMsg($("#loginId-msg"), isDuplicated ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.", isDuplicated);
                isLoginIdChecked = !isDuplicated;
            }
        });
    });

    $("#loginId").on("input", function () {
        isLoginIdChecked = false;
    });

    // 비밀번호 형식 체크
    $("#password").on("input", function () {
        const pw = $(this).val();
        if (!pw) {
            setMsg($("#password-format-msg"), "", false);
        } else if (!PASSWORD_REGEX.test(pw)) {
            setMsg($("#password-format-msg"), "비밀번호는 8자 이상이며 영문, 숫자, 특수문자를 모두 포함해야 합니다.", true);
        } else {
            setMsg($("#password-format-msg"), "사용 가능한 비밀번호입니다.", false);
        }
        checkPasswordMatch();
    });

    // 비밀번호 확인 일치 여부
    $("#passwordConfirm").on("input", checkPasswordMatch);

    function checkPasswordMatch() {
        const pw = $("#password").val();
        const pwConfirm = $("#passwordConfirm").val();

        if (pwConfirm && pw !== pwConfirm) {
            setMsg($("#password-msg"), "비밀번호가 일치하지 않습니다.", true);
        } else if (pwConfirm) {
            setMsg($("#password-msg"), "비밀번호가 일치합니다.", false);
        } else {
            setMsg($("#password-msg"), "", false);
        }
    }

    // 전화번호 자동 하이픈
    $("#phone").on("input", function () {
        let number = $(this).val();

        number = number.replace(/[^0-9]/g, "");

        if (number.length < 4) {
        } else if (number.length < 8) {
            number = number.replace(/(\d{3})(\d+)/, "$1-$2");
        } else if (number.length < 11) {
            number = number.replace(/(\d{3})(\d{4})(\d+)/, "$1-$2-$3");
        } else {
            number = number.replace(/(\d{3})(\d{4})(\d{4}).*/, "$1-$2-$3");
        }

        $(this).val(number);
    });

    // 이메일 형식 + 중복 체크
    $("#check-email").on('click', function () {
        const email = $("#email").val().trim();

        if (!email) {
            alert("이메일을 입력해주세요.");
            return;
        }

        if (!EMAIL_REGEX.test(email)) {
            setMsg($("#email-msg"), "이메일 형식이 올바르지 않습니다.", true);
            isEmailChecked = false;
            return;
        }

        $.ajax({
            url: "/members/exists/email",
            type: "GET",
            data: { email },
            success: function (duplicated) {
                const isDuplicated = (duplicated === true) || (duplicated === "true");
                setMsg($("#email-msg"), isDuplicated ? "이미 사용 중인 이메일입니다." : "사용 가능한 이메일입니다.", isDuplicated);
                isEmailChecked = !isDuplicated;
            }
        });
    });

    $("#email").on("input", function () {
        isEmailChecked = false;
        const email = $(this).val().trim();

        if (!email) {
            setMsg($("#email-msg"), "", false);
        } else if (!EMAIL_REGEX.test(email)) {
            setMsg($("#email-msg"), "이메일 형식이 올바르지 않습니다.", true);
        } else {
            setMsg($("#email-msg"), "이메일 중복 체크를 해주세요.", true);
        }
    });

    $(".join-form").on("submit", function (e) {
        const name = $("input[name='name']").val().trim();
        const loginId = $("#loginId").val().trim();
        const pw = $("#password").val();
        const pwConfirm = $("#passwordConfirm").val();
        const phone = $("#phone").val().trim();
        const email = $("#email").val().trim();

        if (!name || !loginId || !pw || !pwConfirm || !phone || !email) {
            e.preventDefault();
            alert("모든 항목을 입력해주세요.");
            return;
        }

        if (!PASSWORD_REGEX.test(pw)) {
            e.preventDefault();
            alert("비밀번호는 8자 이상이며 영문, 숫자, 특수문자를 모두 포함해야 합니다.");
            $("#password").focus();
            return;
        }

        if (pw !== pwConfirm) {
            e.preventDefault();
            alert("비밀번호가 일치하지 않습니다.");
            $("#passwordConfirm").focus();
            return;
        }

        if (!EMAIL_REGEX.test(email)) {
            e.preventDefault();
            alert("이메일 형식이 올바르지 않습니다.");
            $("#email").focus();
            return;
        }

        if (!isLoginIdChecked) {
            e.preventDefault();
            alert("아이디 중복 체크를 해주세요.");
            return;
        }

        if (!isEmailChecked) {
            e.preventDefault();
            alert("이메일 중복 체크를 해주세요.");
        }
    });
});
