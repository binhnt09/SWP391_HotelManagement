/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function isLeapYear(year) {
    return (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0);
}

function getDaysInMonth(month, year) {
    switch (month) {
        case 2:
            return isLeapYear(year) ? 29 : 28;
        case 4: case 6: case 9: case 11:
            return 30;
        default:
            return 31;
    }
}

function populateDays(daySelect, month, year) {
    const daysInMonth = getDaysInMonth(month, year);
    const currentDay = daySelect.value;

    daySelect.innerHTML = '<option value="">Day</option>';
    for (let i = 1; i <= daysInMonth; i++) {
        const option = document.createElement('option');
        option.value = i;
        option.textContent = i;
        daySelect.appendChild(option);
    }

    if (currentDay <= daysInMonth) {
        daySelect.value = currentDay;
    }
}

function initializeDateDropdowns() {
    const daySelect = document.getElementById('birthDay');
    const monthSelect = document.getElementById('birthMonth');
    const yearSelect = document.getElementById('birthYear');

    const months = [
        'Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
        'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'
    ];
    monthSelect.innerHTML = '<option value="">Month</option>';
    months.forEach((months, index) => {
        const option = document.createElement('option');
        option.value = index + 1;
        option.textContent = months;
        monthSelect.appendChild(option);
    });

    const currentYear = new Date().getFullYear();
    yearSelect.innerHTML = '<option value="">Year</option>';
    for (let y = currentYear - 10; y >= currentYear - 100; y--) {
        const option = document.createElement('option');
        option.value = y;
        option.textContent = y;
        yearSelect.appendChild(option);
    }

    populateDays(daySelect, 1, currentYear);

    monthSelect.addEventListener('change', () => {
        const month = parseInt(monthSelect.value);
        const year = parseInt(yearSelect.value) || currentYear;
        populateDays(daySelect, month, year);
    });

    yearSelect.addEventListener('change', () => {
        const month = parseInt(monthSelect.value) || 1;
        const year = parseInt(yearSelect.value);
        populateDays(daySelect, month, year);
    });
}

document.addEventListener('DOMContentLoaded', initializeDateDropdowns);
