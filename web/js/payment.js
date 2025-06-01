/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
//js moi


//js cu
function selectPayment(element) {
    // Remove selected class from all payment methods
    const allMethods = document.querySelectorAll('.payment-method');
    const qrinfo = document.querySelectorAll('.qr-info');
    allMethods.forEach(method => {
        method.classList.remove('selected');
        const radioBtn = method.querySelector('.radio-btn');
        radioBtn.classList.remove('checked');
    });

    qrinfo.forEach(detail => {
        detail.classList.remove('active');
    });

    // Add selected class to clicked element
    element.classList.add('selected');
    const radioBtn = element.querySelector('.radio-btn');
    radioBtn.classList.add('checked');

    const method = element.getAttribute('data-method'); // ví dụ: 'vietqr', 'banktransfer'
    if (method) {
        const detail = document.getElementById(`${method}-details`);
        if (detail)
            detail.classList.add('active');
    }
}

function processPayment() {
    const selectedMethod = document.querySelector('.payment-method.selected');
    if (selectedMethod) {
        const methodName = selectedMethod.querySelector('div[style*="font-weight: bold"]').textContent;
        alert(`Đang xử lý thanh toán bằng: ${methodName}`);

        // Simulate payment processing
        setTimeout(() => {
            if (methodName === 'VietQR') {
                alert('Mã QR đã được tạo! Vui lòng quét mã để hoàn tất thanh toán.');
            } else {
                alert('Chuyển hướng đến trang thanh toán...');
            }
        }, 1000);
    } else {
        alert('Vui lòng chọn phương thức thanh toán!');
    }
}

// Countdown timer simulation
let timeLeft = 55 * 60; // 54 minutes 29 seconds

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


