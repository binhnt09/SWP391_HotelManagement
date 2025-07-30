/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', function () {
    const searchTabs = document.querySelectorAll('.search-tab');

    searchTabs.forEach(tab => {
        tab.addEventListener('click', function () {
            // remove active class from all tabs
            searchTabs.forEach(t => t.classList.remove('active'));

            // add active class to clicked tab
            this.classList.add('active');
        });
    });

    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth'
                });
            }
        });
    });

    // add hover effect to tour cards
    const tourCards = document.querySelectorAll('.tour-card');
    tourCards.forEach(card => {
        card.addEventListener('mouseenter', function () {
            this.style.transform = 'translateY(-5px)';
        });

        card.addEventListener('mouseleave', function () {
            this.style.transform = 'translateY(0)';
        });
    });

    // form validation
    const searchForm = document.querySelector('.search-form');
    if (searchForm) {
        const searchButton = searchForm.querySelector('.btn-search');
        searchButton.addEventListener('click', function (e) {
            e.preventDefault();

            // basic form validation
            const inputs = searchForm.querySelectorAll('input[type="text"], input[type="date"], select');
            let isValid = true;

            inputs.forEach(input => {
                if (!input.value.trim()) {
                    input.style.borderColor = '#dc3545';
                    isValid = false;
                } else {
                    input.style.borderColor = '#28a745';
                }
            });

            if (isValid) {
                alert('Tìm kiếm thành công!');
            } else {
                alert('Vui lòng điền đầy đủ thông tin!');
            }
        });
    }
});


//search
const searchInput = document.querySelector("input[name='searchVoucher']");
let timeout;
searchInput.addEventListener("input", function () {
    clearTimeout(timeout);
    timeout = setTimeout(() => {
        document.getElementById("searchForm").submit();
    }, 100); // Gửi sau khi dừng gõ 100ms
});

//search payment
const searchInput1 = document.querySelector("input[name='searchPayment']");
let timeout1;
searchInput1.addEventListener("input", function () {
    clearTimeout(timeout);
    timeout1 = setTimeout(() => {
        document.getElementById("searchFormPayment").submit();
    }, 100); // Gửi sau khi dừng gõ 100ms
});


//constant valid date from and to
const fromInputs = document.querySelectorAll(".validfrom");
const toInputs = document.querySelectorAll(".validto");
const today = new Date().toISOString().split("T")[0]; // yyyy-MM-dd, toISOString(): "2025-07-07T14:00:00.000Z"

fromInputs.forEach((fromInput, index) => {
    const toInput = toInputs[index];
    // Set max for fromDate = today
    fromInput.setAttribute("max", today);

    fromInput.addEventListener("change", () => {
        const fromValue = fromInput.value;

        if (fromValue > today) {
            alert("Ngày bắt đầu không được lớn hơn ngày hiện tại.");
            fromInput.value = "";
            return;
        }
        if (toInput) {
            // Set min for toDate based on fromDate
            toInput.setAttribute("min", fromValue);

            if (!toInput.value) {
                toInput.value = today;
            }
        }
        filterByCustomDate();
    });
});

toInputs.forEach((toInput, index) => {
    const fromInput = fromInputs[index];
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
        filterByCustomDate();
    });
});

//LevelUser check box form add
document.addEventListener("DOMContentLoaded", function () {
    const dropdownButton = document.getElementById('memberDropdown');
    const checkboxes = document.querySelectorAll('.member-checkbox');

    function updateSelectedLevels() {
        const selectedValues = [];
        const selectedLabels = [];

        checkboxes.forEach(checkbox => {
            if (checkbox.checked) {
                selectedValues.push(checkbox.value);
                selectedLabels.push(checkbox.getAttribute('data-name'));
            }
        });
        dropdownButton.innerText = selectedValues.length > 0
                ? selectedLabels.join(", ") : "Select Level";
        document.getElementById('levelUserId').value = selectedValues.join(", ");
    }

    checkboxes.forEach(cb => {
        cb.addEventListener('change', updateSelectedLevels);
    });

    updateSelectedLevels();
});

//levelUser check box form edit
document.addEventListener("DOMContentLoaded", function () {
    const dropdownButton = document.getElementById('memberDropdownUd');
    const checkboxes = document.querySelectorAll('.member-checkboxUd');

    function updateSelectedLevelsUd() {
        const selectedValues = [];
        const selectedLabels = [];

        checkboxes.forEach(checkbox => {
            if (checkbox.checked) {
                selectedValues.push(checkbox.value);
                selectedLabels.push(checkbox.getAttribute('data-name'));
            }
        });
        dropdownButton.innerText = selectedValues.length > 0
                ? selectedLabels.join(", ") : "Select Level";
        document.getElementById('levelUserIdUd').value = selectedValues.join(", ");
    }

    checkboxes.forEach(cb => {
        cb.addEventListener('change', updateSelectedLevelsUd);
    });
    updateSelectedLevelsUd();
});


//load data len form edit
function openEditModal(voucherId, code, discount, from, to) {
    // Gán các giá trị vào input
    document.getElementById("voucherIdUd").value = voucherId;
    document.getElementById("voucherCodeUd").value = code;
    document.getElementById("DiscoutUd").value = discount;
    document.getElementById("validfromUd").value = from;
    document.getElementById("validtoUd").value = to;

    // Gọi Ajax để lấy các level chưa được chọn
    fetch(`${contextPath}/getLevels?voucherId=${voucherId}`)
            .then(res => res.json())
            .then(data => {
                console.log("DATA từ server:", data);
                const container = document.getElementById("levelCheckboxContainer");
                container.innerHTML = "";

                const assignedLevelIds = data.selectedLevelIds;

                data.availableLevels.forEach(level => {
                    const li = document.createElement('li');
                    li.className = 'nav-item submenu dropdown';

                    const label = document.createElement('label');
                    label.className = 'dropdown-item';
                    label.style.cursor = "pointer";

                    const checkbox = document.createElement('input');
                    checkbox.type = "checkbox";
                    checkbox.className = "member-checkboxUd";
                    checkbox.value = level.levelId;
                    checkbox.setAttribute("data-name", level.levelName);
                    checkbox.style = "width: 18px; height: 18px; margin: 5px";

                    if (assignedLevelIds.includes(level.levelId)) {
                        checkbox.checked = true;
                    }

                    label.appendChild(checkbox);
                    label.append(" " + level.levelName);
                    li.appendChild(label);
                    container.appendChild(li);
                });

                // === Sau khi render checkbox, gán lại sự kiện và cập nhật giao diện ===
                const dropdownButton = document.getElementById('memberDropdownUd');
                const checkboxes = document.querySelectorAll('.member-checkboxUd');

                function updateSelectedLevelsUd() {
                    const selectedValues = [];
                    const selectedLabels = [];

                    checkboxes.forEach(checkbox => {
                        if (checkbox.checked) {
                            selectedValues.push(checkbox.value);
                            selectedLabels.push(checkbox.getAttribute('data-name'));
                        }
                    });

                    dropdownButton.innerText = selectedValues.length > 0
                            ? selectedLabels.join(", ") : "Select Level";
                    document.getElementById('levelUserIdUd').value = selectedValues.join(", ");
                }

                checkboxes.forEach(cb => {
                    cb.addEventListener('change', updateSelectedLevelsUd);
                });

                // Gọi cập nhật ngay lần đầu
                updateSelectedLevelsUd();
            }).catch(err => console.error("FETCH ERROR:", err));
}

//delete checkbox
$(document).ready(function () {
    restoreSelection(); // Khôi phục checkbox khi tải trang
    // chọn/Bỏ chọn tất cả
    $("#selectAll").click(function () {
        let isChecked = this.checked;
        $(".select-item").prop("checked", isChecked);

        let selectedStaff = [];
        if (isChecked) {
            // Nếu chọn tất cả, lưu toàn bộ voucher ID vào mảng
            $(".select-item").each(function () {
                selectedStaff.push($(this).val());
            });
        }
        localStorage.setItem("selectedStaff", JSON.stringify(selectedStaff));
    });

    // Xử lý chọn từng checkbox
    $(".select-item").click(function () {
        updateSelection($(this).val(), $(this).prop("checked"));
    });
    // Khi nhấn "Delete" để lấy danh sách voucher ID
    $(".btn-danger[data-toggle='modal']").click(function () {
        let selectedIDs = JSON.parse(localStorage.getItem("selectedStaff")) || [];
        if (selectedIDs.length === 0) {
            alert("Please select at least one staff to delete.");
            return false;
        }
        // Lưu danh sách ID vào thuộc tính data
        $("#deleteVoucherBtn").data("selected-ids", selectedIDs);
    });
    // Khi nhấn "Delete" trong modal thì gửi AJAX
    $("#deleteVoucherBtn").click(function () {
        let bookingID = $(this).data("selected-ids");
        if (bookingID.length > 0) {
            $.ajax({
                type: "POST",
                url: "removevoucher",
                traditional: true,
                data: {voucherId: bookingID},
                success: function () {
                    $("#deleteVoucherModal").modal("hide");
                    alert("Selected Booking deleted successfully!");
                    localStorage.removeItem("selectedStaff"); // Xóa danh sách đã chọn
                    setTimeout(function () {
                        location.reload();
                    }, 500);
                },
                error: function () {
                    alert("Error deleting booking. Please try again.");
                }
            });
        }
    });
});

// Cập nhật trạng thái checkbox vào localStorage (giữ lại status checked khi chuyển trang)
function updateSelection(staffID, isChecked) {
    let selectedStaff = JSON.parse(localStorage.getItem("selectedStaff")) || [];
    if (isChecked) {
        // nếu chọn thì thêm vào danh sách (nếu chưa có)
        if (!selectedStaff.includes(staffID)) {
            selectedStaff.push(staffID);
        }
    } else {
        // Nếu bỏ chọn thì xóa khỏi danh sách
        selectedStaff = selectedStaff.filter(id => id !== staffID);
    }
    localStorage.setItem("selectedStaff", JSON.stringify(selectedStaff));
}

// Khôi phục trạng thái checkbox khi chuyển trang
function restoreSelection() {
    let selectedStaff = JSON.parse(localStorage.getItem("selectedStaff")) || [];

    $(".select-item").each(function () {
        if (selectedStaff.includes($(this).val())) {
            $(this).prop("checked", true);
        }
    });
    // Cập nhật trạng thái "Select All" dựa trên checkbox đã chọn
    $("#selectAll").prop("checked", $(".select-item:checked").length === $(".select-item").length);
}

//delete icon thung rac
var bookingToDelete = ""; // Lưu voucher ID cần xóa
// Khi nhấn vào nút xóa, lấy voucher ID từ `data-id`
$(".delete").click(function () {
    bookingToDelete = $(this).data("id"); // Lưu ID vào biến
});
// Khi nhấn nút Delete trong modal, gửi yêu cầu AJAX
$("#deleteVoucherBtn").click(function () {
    if (bookingToDelete) {
        $.ajax({
            type: "POST",
            url: "removevoucher", // Servlet xử lý xóa
            data: {voucherId: bookingToDelete},
            success: function () {
                $("#deleteVoucherModal").modal("hide"); // ẩn modal sau khi xóa
                alert("Staff deleted successfully!");
                setTimeout(function () {
                    location.reload(); // reload trang sau khi modal đóng
                }, 500); // chờ modal đóng rồi reload
            },
            error: function () {
                alert("Error deleting staff. Please try again.");
            }
        });
    }
});