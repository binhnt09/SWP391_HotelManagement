/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
//js moi


//js cu
function selectPayment(element) {
    const allMethods = document.querySelectorAll('.payment-method');
    const qrinfo = document.querySelectorAll('.qr-info');

    allMethods.forEach(method => {
        method.classList.remove('selected');
        const radioBtn = method.querySelector('.radio-btn');
        if (radioBtn) radioBtn.classList.remove('checked');
    });

    qrinfo.forEach(detail => {
        detail.classList.remove('active');
    });

    // Thêm class selected
    element.classList.add('selected');
    const radioBtn = element.querySelector('.radio-btn');
    if (radioBtn) radioBtn.classList.add('checked');

    const method = element.getAttribute('data-method');
    if (method) {
        const detail = document.getElementById(`${method}-details`);
        if (detail) detail.classList.add('active');
        // Gán vào input hidden
        const hiddenInput = document.getElementById("paymentMethod");
        if (hiddenInput) hiddenInput.value = method;
    }
}

function processPayment(event) {
    event.preventDefault(); // Ngăn submit mặc định

    const form = document.getElementById("paymentForm");
    const selectedMethod = document.querySelector('.payment-method.selected');
    const methodInput = document.getElementById("paymentMethod");

    if (!selectedMethod || !methodInput || !methodInput.value) {
        alert('Vui lòng chọn phương thức thanh toán!');
        return;
    }

    const methodValue = methodInput.value;
    const methodLabel = selectedMethod.querySelector('[data-method-label]');
    const methodName = methodLabel ? methodLabel.textContent.trim() : methodValue;

    alert(`Đang xử lý thanh toán bằng: ${methodName}`);

    // Gán thêm bankCode nếu có
    if (methodValue === "wallettransfer") {
        const walletRadio = document.querySelector('input[name="wallet"]:checked');
        const bankInput = document.getElementById("bankCode");
        if (walletRadio && bankInput) {
            bankInput.value = walletRadio.value.toUpperCase();
        } else {
            alert("Vui lòng chọn loại ví điện tử!");
            return;
        }
    }

    setTimeout(() => {
        // Chuyển hướng thực sự
        form.submit();
    }, 500);
}



// Countdown timer simulation
let timeLeft = 1 * 60; // 54 minutes 29 seconds

function updateTimer() {
    const minutes = Math.floor(timeLeft / 60);
    const seconds = timeLeft % 60;
    const timerElement = document.querySelector('.header span[style*="color: #FFD700"]');
    if (timerElement) {
        timerElement.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
    }

    if (timeLeft > 0) {
        timeLeft--;
        setTimeout(updateTimer, 1000);
    } else {
        alert('Thời gian giữ giá đã hết! Vui lòng thực hiện lại giao dịch.');
    }
}

// Start timer when page loads
document.addEventListener('DOMContentLoaded', function () {
    updateTimer();
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


