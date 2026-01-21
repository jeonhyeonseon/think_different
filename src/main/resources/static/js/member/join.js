$(function () {
    $("#check-id").on('click', function () {
        const loginId = $("#loginId").val().trim();
        if(!loginId) {
            alert("아이디를 입력해주세요.")
            return;
        }

        // 아이디 중복 체크
        $.ajax({
            url: "/members/exists/loginId",
            type: "GET",
            data: { loginId },
            success: function (duplicated) {
                const isDuplicated = (duplicated === true) || (duplicated === "true");
                $("#loginId-msg").text(
                    isDuplicated ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다."
                );
            }
        });
    });

    // 비밀번호 일치 여부
    $("#password-confirm").on("input", function (e) {
        const pw = $("input[name='password']").val();
        const pwConfirm = $("#password-confirm").val();

        if (pw !== pwConfirm) {
            e.preventDefault(); // 제출 막기
            $("#password-msg").text("비밀번호가 일치하지 않습니다.");
            $("#password-confirm").focus();
            return;
        }
    })

    // 전화번호
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
    })

    $("#check-email").on('click', function () {
        const email = $("#email").val().trim();
        if(!email) {
            alert("이메일을 입력해주세요.");
            return;
        }

        // 이메일 중복 체크
        $.ajax({
            url: "/members/exists/email",
            type: "GET",
            data: { email },
            success: function (duplicated) {
                const isDuplicated = (duplicated === true) || (duplicated === "true");
                $("#email-msg").text(
                    isDuplicated ? "이미 사용 중인 이메일입니다." : "사용 가능한 이메일입니다."
                );
            }
        });
    });
});