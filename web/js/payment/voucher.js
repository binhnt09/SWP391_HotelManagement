/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', function () {
    const searchTabs = document.querySelectorAll('.search-tab');

    searchTabs.forEach(tab => {
        tab.addEventListener('click', function () {
            // Remove active class from all tabs
            searchTabs.forEach(t => t.classList.remove('active'));

            // Add active class to clicked tab
            this.classList.add('active');

            // You can add more functionality here to switch tab content
        });
    });

    // Smooth scrolling for anchor links
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

    // Add hover effect to tour cards
    const tourCards = document.querySelectorAll('.tour-card');
    tourCards.forEach(card => {
        card.addEventListener('mouseenter', function () {
            this.style.transform = 'translateY(-5px)';
        });

        card.addEventListener('mouseleave', function () {
            this.style.transform = 'translateY(0)';
        });
    });

    // Form validation
    const searchForm = document.querySelector('.search-form');
    if (searchForm) {
        const searchButton = searchForm.querySelector('.btn-search');
        searchButton.addEventListener('click', function (e) {
            e.preventDefault();

            // Basic form validation
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
                alert('Tìm kiếm thành công! (Demo)');
            } else {
                alert('Vui lòng điền đầy đủ thông tin!');
            }
        });
    }
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

//memeber ship check box
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
        document.getElementById('memberShipId').value = selectedValues.join(", ");
    }

    checkboxes.forEach(cb => {
        cb.addEventListener('change', updateSelectedLevels);
    });

    updateSelectedLevels();
});

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
        document.getElementById('memberShipIdUd').value = selectedValues.join(", ");
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
                    document.getElementById('memberShipIdUd').value = selectedValues.join(", ");
                }

                checkboxes.forEach(cb => {
                    cb.addEventListener('change', updateSelectedLevelsUd);
                });

                // Gọi cập nhật ngay lần đầu
                updateSelectedLevelsUd();
            }).catch(err => console.error("FETCH ERROR:", err));
}
