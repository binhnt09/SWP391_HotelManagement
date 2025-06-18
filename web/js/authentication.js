//captcha
let captchaWidgets = {};
function onloadCallback() {
    captchaWidgets["formRegister"] = grecaptcha.render("register-captcha", {
        sitekey: "6LfzjVorAAAAAAJT5bnnmi2MrHjf7KjKK2sAwlxF"
    });
    captchaWidgets["formVerifyEmail"] = grecaptcha.render("verifyEmail-captcha", {
        sitekey: "6LfzjVorAAAAAAJT5bnnmi2MrHjf7KjKK2sAwlxF"
    });
    captchaWidgets["formVerifyEmailForgotPassword"] = grecaptcha.render("forgot-captcha", {
        sitekey: "6LfzjVorAAAAAAJT5bnnmi2MrHjf7KjKK2sAwlxF"
    });
}

window.onload = function () {
    setupFormValidation("formRegister", "errorRegister", "repass-error", true);
    setupFormValidation("formVerifyEmail", "errorVerifyEmail", null, false);
    setupFormValidation("formVerifyEmailForgotPassword", "errorForgot", null, false);
    setupFormValidation("formCompleteForgotPassword", null, "repassfg-error", true);
    setupFormValidation("formChange-password", null, "repassch-error", true);
};
function setupFormValidation(formId, captchaErrorId, repassErrorId, hasRepass) {
    const form = document.getElementById(formId);
    const errorCaptcha = captchaErrorId ? document.getElementById(captchaErrorId) : null;
    const errorRepass = repassErrorId ? document.getElementById(repassErrorId) : null;
    form.addEventListener("submit", function (e) {
        let hasError = false;
        // Re-password check
        if (hasRepass) {
            const pass = form.querySelector('input[name="pass"]').value;
            const repass = form.querySelector('input[name="repass"]').value;
            if (pass !== repass) {
                errorRepass.style.display = "block";
                hasError = true;
            } else {
                errorRepass.style.display = "none";
            }
        }
        // Captcha check
        if (errorCaptcha) {
            const widgetId = captchaWidgets?.[formId];
            const captchaResponse = grecaptcha?.getResponse(widgetId);
            if (!captchaResponse) {
                errorCaptcha.innerText = "Bạn cần xác minh captcha.";
                hasError = true;
            } else {
                errorCaptcha.innerText = "";
            }
        }
        if (hasError) {
            e.preventDefault();
        }
    });
}

//popup login register
$(document).ready(function () {
// Khởi tạo popup cho nút mở login/register
    $('.login-modal-btn, .register-modal-btn').magnificPopup({
        type: 'inline',
        midClick: true
    });
    // Xử lý chuyển đổi giữa login và register
    $(document).on('click', '.switch-modal', function (e) {
        e.preventDefault();
        const target = $(this).attr('href');
        // Cập nhật URL hash mà không reload
        history.pushState(null, '', target);
        // Đóng popup hiện tại rồi mở cái mới ngay lập tức
        $.magnificPopup.close();
        // Mở popup mới ngay lập tức 
        $.magnificPopup.open({
            items: {
                src: target,
                type: 'inline'
            },
            midClick: true
        });
    });
    // Khi người dùng nhấn nút back (quay lại)
    window.addEventListener('popstate', function () {
        const hash = window.location.hash;
        $.magnificPopup.close();
        if ($(hash).length) {
            $.magnificPopup.open({
                items: {
                    src: hash,
                    type: 'inline'
                },
                midClick: true
            });
        }
    });
    // Khi người dùng load trang kèm theo #login-modal hoặc #register-modal
    const initialHash = window.location.hash;
    if ($(initialHash).length) {
        $.magnificPopup.open({
            items: {
                src: initialHash,
                type: 'inline'
            },
            midClick: true
        });
    }
});
//hiện ẩn password
document.querySelectorAll('.toggle-password').forEach(function (eyeIcon) {
    eyeIcon.addEventListener('click', function () {
        const input = document.querySelector(this.getAttribute('toggle'));
        const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
        input.setAttribute('type', type);
        this.textContent = type === 'password' ? 'remove_red_eye' : 'visibility_off';
    });
});
//        Ajax hiện lỗi password
$(document).ready(function () {
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).{6,}$/;
    function validatePasswordInput(input) {
        const val = input.val();
        const errorMsg = input.siblings(".error-message");
        if (val.length === 0) {
            input.removeClass("valid invalid");
            errorMsg.fadeOut(150);
        } else if (!passwordPattern.test(val)) {
            input.removeClass("valid").addClass("invalid");
            errorMsg.text("Mật khẩu phải có ít nhất 6 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.").fadeIn(150);
        } else {
            input.removeClass("invalid").addClass("valid");
            errorMsg.fadeOut(150);
        }
    }

// Apply cho mọi ô có class password-check
    $(".password-check").on("input", function () {
        validatePasswordInput($(this));
    });
});


$(document).ready(function () {
    let isNewPassValid = true;
    const newpassError = $("#newpass-error");
    // Kiểm tra new password khác current password
    function validateChangePassword() {
        const currentPass = $("#passCurrent").val();
        const newpass = $("#newpassChange").val();

        if (!newpass) {
            $("#newpassChange").removeClass("valid invalid");
            newpassError.fadeOut(150);
            return;
        }
        if (newpass === currentPass && newpass.length > 0) {
            $("#newpassChange").removeClass("valid").addClass("invalid");
            newpassError.html("⚠️ Mật khẩu mới không được trùng với mật khẩu hiện tại.").fadeIn(150);
            isNewPassValid = false;
        } else {
            $("#newpassChange").removeClass("invalid").addClass("valid");
            newpassError.fadeOut(150);
            isNewPassValid = true;
        }
    }
    $("#newpassChange, #passCurrent").on("input", function () {
        validateChangePassword();
    });

    $("#formChange-password").on("submit", function (e) {
        validateChangePassword(); // đảm bảo kiểm tra lại trước khi submit
        if (!isNewPassValid) {
            e.preventDefault(); // chặn submit
        }
    });
});

