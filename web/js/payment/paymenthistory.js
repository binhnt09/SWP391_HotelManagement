/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

//search
const ranges = {
    "90days": [new Date(new Date().setDate(new Date().getDate() - 90)), new Date()],
    "6-2025": [new Date("2025-06-01"), new Date("2025-06-30")],
    "5-2025": [new Date("2025-05-01"), new Date("2025-05-31")]
};

document.querySelectorAll('.filter-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        const rangeKey = btn.getAttribute('data-range');
        const [from, to] = ranges[rangeKey];
        filterRowsByDate(from, to);

        // Active button
        document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');

        // Ẩn custom date
        document.getElementById('customDateForm').classList.add('d-none');
    });
});

const fromInput = document.getElementById("fromDate");
const toInput = document.getElementById("toDate");
const today = new Date().toISOString().split("T")[0]; // yyyy-MM-dd, toISOString(): "2025-07-07T14:00:00.000Z"

// Set max for fromDate = today
fromInput.setAttribute("max", today);
toInput.setAttribute("max", today);

fromInput.addEventListener("change", () => {
    const fromValue = fromInput.value;

    if (fromValue > today) {
        alert("Ngày bắt đầu không được lớn hơn ngày hiện tại.");
        fromInput.value = "";
        return;
    }

    // Set min for toDate based on fromDate, max vẫn là hôm nay
    toInput.setAttribute("min", fromValue);
    toInput.setAttribute("max", today);

    if (!toInput.value) {
        toInput.value = today;
    }

    filterByCustomDate();
});

toInput.addEventListener("change", () => {
    const fromValue = fromInput.value;
    const toValue = toInput.value;

    if (!fromValue) {
        alert("Vui lòng chọn ngày bắt đầu trước.");
        toInput.value = "";
        return;
    }

    if (toValue < fromValue) {
        alert("Ngày kết thúc không được nhỏ hơn ngày bắt đầu.");
        toInput.value = "";
        return;
    }

    if (toValue > today) {
        alert("Ngày kết thúc không được lớn hơn ngày hiện tại.");
        toInput.value = "";
        return;
    }

    filterByCustomDate();
});

function toggleCustomDate() {
    // Hiện custom date form + remove active các preset
    document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));

    const customBtn = document.querySelector('[data-range="custom"]');
    customBtn.classList.add('active');

    document.getElementById('customDateForm').classList.toggle('d-none');

    if (fromInput.value && toInput.value) {
        filterByCustomDate();
    }

    if (!fromInput.value && !toInput.value) {
        document.getElementById('paymentTable').closest('.table-responsive').style.display = 'none';
        document.getElementById('no-transaction')?.classList.remove('d-none');
        return;
    }
}

function filterByCustomDate() {
    const from = new Date(fromInput.value);
    const to = new Date(toInput.value);
    to.setHours(23, 59, 59);

    if (!fromInput.value || !toInput.value)
        return;

    filterRowsByDate(from, to);
}

function filterRowsByDate(from, to) {
    const table = document.getElementById('paymentTable');
    const rows = table.querySelectorAll('tbody tr');

    let anyVisible = false;
    rows.forEach(row => {
        const dateStr = row.getAttribute('data-date');
        const date = new Date(dateStr);
        if (date >= from && date <= to) {
            row.style.display = '';
            anyVisible = true;
        } else {
            row.style.display = 'none';
        }
    });

    // Hiện "Không có giao dịch" nếu tất cả đều ẩn
    const visibleRows = Array.from(rows).filter(r => r.style.display !== 'none');

    table.closest('.table-responsive').style.display = anyVisible ? 'block' : 'none';
    document.getElementById('no-transaction')?.classList.toggle('d-none', anyVisible);
}


document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.btn-view-invoice').forEach(btn => {
        btn.addEventListener('click', function () {
            // Đây là dữ liệu mẫu giả định, bạn nên gọi AJAX theo paymentId
            document.getElementById('customerName').textContent = "Ngô Thanh Bình";
            document.getElementById('customerEmail').textContent = "ngobinh0910ntb@gmail.com";
            document.getElementById('customerPhone').textContent = "0328633491";
            document.getElementById('bookingId').textContent = "224";
            document.getElementById('bookingDate').textContent = "2025-07-06 15:01:38";
            document.getElementById('checkInDate').textContent = "2025-07-07";
            document.getElementById('checkOutDate').textContent = "2025-07-08";

            document.getElementById('roomNumber').textContent = "101A";
            document.getElementById('pricePerNight').textContent = "100000.0";
            document.getElementById('numNights').textContent = "1";

            document.getElementById('paymentId').textContent = btn.dataset.paymentid;
            document.getElementById('paymentMethod').textContent = btn.dataset.method;
            document.getElementById('paymentBank').textContent = btn.dataset.bank;
            document.getElementById('totalAmount').textContent = btn.dataset.amount;

            new bootstrap.Modal(document.getElementById('invoiceModal')).show();
        });
    });
});
