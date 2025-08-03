/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function selectPayment(element) {
    const allMethods = document.querySelectorAll('.payment-method');
    const qrinfo = document.querySelectorAll('.qr-info');

    allMethods.forEach(method => {
        method.classList.remove('selected');
        const radioBtn = method.querySelector('.radio-btn');
        if (radioBtn)
            radioBtn.classList.remove('checked');
    });

    qrinfo.forEach(detail => {
        detail.classList.remove('active');
    });

    // Thêm class selected
    element.classList.add('selected');
    const radioBtn = element.querySelector('.radio-btn');
    if (radioBtn)
        radioBtn.classList.add('checked');

    const method = element.getAttribute('data-method');
    if (method) {
        const detail = document.getElementById(`${method}-details`);
        if (detail)
            detail.classList.add('active');
        // Gán vào input hidden
        const hiddenInput = document.getElementById("paymentMethod");
        if (hiddenInput)
            hiddenInput.value = method;
    }
}

function processPayment(event) {
    event.preventDefault();

    const form = document.getElementById("paymentForm");
    const selectedMethod = document.querySelector('.payment-method.selected');
    const methodInput = document.getElementById("paymentMethod");

    if (!selectedMethod || !methodInput || !methodInput.value) {
        Swal.fire({
            icon: "warning",
            title: "Thiếu thông tin!",
            text: "Vui lòng chọn phương thức thanh toán!",
            confirmButtonText: "OK"
        });
        return;
    }

    const methodValue = methodInput.value;
    const methodLabel = selectedMethod.querySelector('[data-method-label]');
    const methodName = methodLabel ? methodLabel.textContent.trim() : methodValue;

    if (methodValue === "wallettransfer") {
        const walletRadio = document.querySelector('input[name="wallet"]:checked');
        const bankInput = document.getElementById("bankCode");
        if (walletRadio && bankInput) {
            bankInput.value = walletRadio.value.toUpperCase();
        } else {
            Swal.fire({
                icon: "warning",
                title: "Thiếu thông tin!",
                text: "Vui lòng chọn loại ví điện tử!",
                confirmButtonText: "OK"
            });
            return;
        }
    }

    Swal.fire({
        icon: "info",
        title: "Xác nhận thanh toán",
        html: `Phương thức: <b>${methodName}</b>`,
        confirmButtonText: "Tiếp tục",
        cancelButtonText: "Hủy",
        showCancelButton: true,
        timer: 10000,
        timerProgressBar: true,
    }).then((result) => {
        if (result.isConfirmed) {
            form.submit(); // chỉ submit nếu bấm "Tiếp tục"
        }
    });

//    Swal.fire({
//        icon: "info",
//        title: "Đang xử lý...",
//        html: `Phương thức: <b>${methodName}</b><br>Vui lòng chờ trong giây lát...`,
//        timer: 3000,
//        timerProgressBar: true,
//        showConfirmButton: false,
//        didOpen: () => {
//            Swal.showLoading();
//        },
//        willClose: () => {
//            form.submit(); // Submit sau khi countdown xong
//        }
//    });
}



// Countdown timer simulation
const countdown = document.getElementById("countdownPayment");

if (countdown) {
    let timeLeft = 55 * 60;

    const timer = setInterval(() => {
        const minutes = String(Math.floor(timeLeft / 60)).padStart(2, '0');
        const seconds = String(timeLeft % 60).padStart(2, '0');

        countdown.textContent = `${minutes}:${seconds}`;
        timeLeft--;

        if (timeLeft < 0) {
            clearInterval(timer);
            Swal.fire({
                icon: "error",
                title: "Giao dịch hết hạn!",
                text: "Bạn chưa thanh toán trong thời gian quy định. Đặt phòng đã bị hủy.",
                confirmButtonText: "Quay lại trang thanh toán"
            }).then(() => {
                window.location.href = contextPath + "/booking.jsp"; // hoặc loadtohome
            });
        }
    }, 1000);
}

//discount after app voucher
document.addEventListener("DOMContentLoaded", function () {
    const originalPrice = parseFloat(document.getElementById("total-price-data").value);

    // bắt sự kiện chọn radio voucher
    document.querySelectorAll('input[name="voucherId"]').forEach(radio => {
        radio.addEventListener("change", function () {
            const discount = parseFloat(this.getAttribute("data-discount")) || 0;
            const discountAmount = originalPrice * discount / 100;
            const newPrice = originalPrice - discountAmount;

            // cập nhật vào HTML
            document.getElementById("total-price-text").innerText = newPrice.toLocaleString("vi-VN") + " VND";
            document.getElementById("totalbill-hidden").value = newPrice.toFixed(0); // gửi về backend
        });
    });

    // nhập mã tay rồi click nút "Áp dụng"
    document.getElementById("apply-coupon").addEventListener("click", function (e) {
        e.preventDefault();
        const inputCode = document.getElementById("coupon-input").value.trim().toUpperCase();
        const radios = document.querySelectorAll('input[name="voucherId"]');

        let matched = false;
        radios.forEach(radio => {
            const code = radio.getAttribute("data-code").toUpperCase();
            if (code === inputCode) {
                radio.checked = true;
                radio.dispatchEvent(new Event("change")); // kích hoạt lại event
                matched = true;
            }
        });

        if (!matched) {
            alert("Mã giảm giá không hợp lệ hoặc không tồn tại.");
        }
    });
});


// Add coupon functionality
document.getElementById('coupon-input').addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
        const couponCode = this.value.trim();
        if (couponCode) {
            alert(`Đang áp dụng mã giảm giá: ${couponCode}`);
            // Simulate coupon application
            setTimeout(() => {
                alert('Mã giảm giá không hợp lệ hoặc đã hết hạn!');
            }, 1000);
        }
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const toggleCoupon = document.getElementById('toggle-coupon');
    const couponCheckbox = document.getElementById('coupon-checkbox');
    const couponContainer = document.getElementById('coupon-container');

    toggleCoupon.addEventListener('click', function () {
        const isVisible = couponCheckbox.checked = !couponCheckbox.checked;
        couponContainer.style.display = isVisible ? 'flex' : 'none';
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const walletRadios = document.querySelectorAll('input[name="wallet"]');
    const qrDisplay = document.getElementById('qr-display');
    const qrImage = document.getElementById('qr-image');

    const qrSources = {
        vnpay: 'path/to/vnpay-qr.png',
        momo: 'path/to/momo-qr.png'
    };

    walletRadios.forEach(radio => {
        radio.addEventListener('change', function () {
            const selectedWallet = this.value;
            if (qrSources[selectedWallet]) {
                qrImage.src = qrSources[selectedWallet];
                qrDisplay.style.display = 'block';
            }
        });
    });
});


