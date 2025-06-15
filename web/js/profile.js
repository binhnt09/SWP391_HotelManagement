/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function populateDays(daySelect, month, year) {
    const prevSelected = daySelect.value;
    // Reset
    daySelect.innerHTML = "<option value=''>Ngày</option>";

    const daysInMonth = getDaysInMonth(month, year);
    for (let i = 1; i <= daysInMonth; i++) {
        const option = document.createElement("option");

        option.value = i;
        option.textContent = i;

        daySelect.appendChild(option);
    }

    if (prevSelected && prevSelected <= daysInMonth) {
        daySelect.value = prevSelected;
    }
}

function isLeapYear(year) {
    return (year % 4 === 0 && year % 100 !== 0) ||
            (year % 400 === 0);
}

function getDaysInMonth(month, year) {
    switch (month) {
        case 2:
            return isLeapYear(year) ? 29 : 28;
        case 4:
        case 6:
        case 9:
        case 11:
            return 30;
        default:
            return 31;
    }
}

function initializeDateDropdowns() {
    const daySelect = document.getElementById("birthDay");

    const monthSelect = document.getElementById("birthMonth");

    const yearSelect = document.getElementById("birthYear");

    yearSelect.innerHTML = "<option value=''>Năm</option>";
    const currentYear = new Date().getFullYear();

    for (let y = currentYear - 100; y <= currentYear; y++) {
        const option = document.createElement("option");

        option.value = y;
        option.textContent = y;

        yearSelect.appendChild(option);
    }

    const months = [
        "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
        "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8",
        "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
    ];

    monthSelect.innerHTML = "<option value=''>Tháng</option>";

    months.forEach((item, index) => {
        const option = document.createElement("option");

        option.value = index + 1;
        option.textContent = item;

        monthSelect.appendChild(option);
    });

    // Gán giá trị từ phía server
    if (birthYear) {
        yearSelect.value = birthYear;
    }

    if (birthMonth) {
        birthMonth = parseInt(birthMonth);
        monthSelect.value = birthMonth;

        populateDays(daySelect, birthMonth, birthYear);
    } else {
        populateDays(daySelect, 1, currentYear);
    }

    if (birthDay) {
        daySelect.value = birthDay;
    }

    // Cập nhật khi thay đổi
    yearSelect.addEventListener("change", () => {
        const year = parseInt(yearSelect.value);
        const month = parseInt(monthSelect.value) ||
                1;

        populateDays(daySelect, month, year);
    });

    monthSelect.addEventListener("change", () => {
        const year = parseInt(yearSelect.value) ||
                currentYear;

        const month = parseInt(monthSelect.value);
        populateDays(daySelect, month, year);
    });
}

document.addEventListener("DOMContentLoaded", initializeDateDropdowns);


document.addEventListener("DOMContentLoaded", function () {
    var form = document.getElementById("formChange-password-profile");

    console.log("form =", form);

    if (form) {
        var newPass = document.getElementById("newpassChange");

        var rePass = document.getElementById("repassChange");

        var errorMsg = document.getElementById("repassch-error-profile");

        console.log("newPass =", newPass);
        console.log("rePass =", rePass);
        console.log("errorMsg =", errorMsg);

        form.addEventListener("submit", function (e) {
            if (newPass.value.trim() !== rePass.value.trim()) {
                e.preventDefault();
                errorMsg.style.display = "block";
                errorMsg.textContent = "Mật khẩu nhập lại không khớp.";
            } else {
                errorMsg.style.display = "none";
            }
        });
    }
});

$(document).ready(function () {
    $('#formChange-password-profile').on("submit", function (e) {
        var currentPass = $("#passCurrent").val().trim();
        var newPass = $("#newpassChange").val().trim();

        if (newPass === currentPass) {
            $("#newpass-error").text("⚡ Mật khẩu mới KHÔNG được trùng với mật khẩu hiện tại").fadeIn(150);
            e.preventDefault();
            return false;
        } else {
            $("#newpass-error").fadeOut(150);
        }
    });
});

//sau khi co thay doi nut submit moi hien
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('profileForm');
    const submitBtn = document.getElementById('submitProfile');
    const cancelBtn = document.getElementById('cancelProfile');

    // Lưu các giá trị ban đầu
    const initialValues = {};

    for (const element of form.elements) {
        if (element.name) {
            initialValues[element.name] = element.value.trim();
        }
    }

    function isFormModified() {
        for (const element of form.elements) {
            if (element.name &&
                    element.value.trim() !== initialValues[element.name]) {
                return true;
            }
        }
        return false;
    }

    form.addEventListener('input', () => {
        if (isFormModified()) {
            submitBtn.disabled = false;
            cancelBtn.disabled = false;
        } else {
            submitBtn.disabled = true;
            cancelBtn.disabled = true;
        }
    });

    cancelBtn.addEventListener('click', (e) => {
        e.preventDefault();

        for (const element of form.elements) {
            if (element.name) {
                element.value = initialValues[element.name];
            }
        }

        document.getElementById("birthYear").value = birthYear;
        document.getElementById("birthMonth").value = birthMonth;
        document.getElementById("birthDay").value = birthDay;

        submitBtn.disabled = true;
        cancelBtn.disabled = true;
    });
});
