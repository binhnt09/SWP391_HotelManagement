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


$(document).ready(function () {
    $(".btn-view-invoice").click(function () {
        const paymentId = $(this).data("paymentid");
        if (!paymentId) {
            alert("Không tìm thấy paymentId hợp lệ.");
            return;
        }
        $.ajax({
            url: "viewinvoicebypayment",
            method: "GET",
            data: {paymentId},
            success: function (invoice) {
                $("#customerName").text(invoice.firstName + " " + invoice.lastName);
                $("#customerEmail").text(invoice.email);
                $("#customerPhone").text(invoice.phone);
                $("#customerAddress").text(invoice.address);

                const payment = invoice.payment || {};
                const booking = payment.booking || {};
                
                $("#bookingId").text(invoice.bookingId);
                $("#bookingDate").text(booking.bookingDate ? formatDateTime(booking.bookingDate) : "N/A");
                $("#checkInDate").text(booking.checkInDate ? formatDateTime(booking.checkInDate) : "N/A");
                $("#checkOutDate").text(booking.checkOutDate ? formatDateTime(booking.checkOutDate) : "N/A");

                $("#roomNumber").text(invoice.roomNumber);
                $("#pricePerNight").text(invoice.roomPrice);
                $("#numNights").text(invoice.nights);
                $("#discountAmountR").text(invoice.discountAmount + '%');
                $("#totalPricePerNight").text(invoice.totalRoomPrice);

                const services = invoice.services || [];
                const serviceTableBody = $("#serviceTableBody");
                serviceTableBody.empty();
                if (services.length > 0) {
                    $("#serviceSection").show();
                    $("#noService").hide();
                    services.forEach(s => {
                        serviceTableBody.append(`
                            <tr>
                                <td>${s.serviceName}</td>
                                <td>${s.price}</td>
                                <td>${s.quantity}</td>
                                <td>${s.priceAtUse}</td>
                                <td>${s.usedAt}</td>
                            </tr>
                        `);
                    });
                } else {
                    $("#serviceSection").hide();
                }

                // Voucher
                if (invoice.voucherCode && invoice.discountAmount > 0) {
                    $("#voucherSection").show();
                    $("#voucherCode").text(invoice.voucherCode);
                    $("#discountAmount").text(invoice.discountAmount);
                    $("#noVoucher").hide();
                } else {
                    $("#voucherSection").hide();
                }

                $("#paymentMethod").text(payment.method || "N/A");
                $("#transactionCode").text(payment.transactionCode || "N/A");
                $("#paymentBank").text(payment.bankCode || "N/A");
                $("#totalAmount").text(payment.amount || "0");
                
                $("#issueDate").text(invoice.issueDate ? formatDateTime(invoice.issueDate) : "N/A");

                $("#invoiceModal").modal("show");
            },
            error: function (xhr, status, error) {
                console.error("Lỗi AJAX:", xhr.responseText, error);
                alert("Không thể tải dữ liệu hóa đơn.");
            }
        });
    });
});

// format datetime
function formatDateTime(dateStr) {
    const date = new Date(dateStr);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();

    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${day}-${month}-${year} ${hours}:${minutes}`;
}

