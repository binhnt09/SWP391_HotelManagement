/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function toggleCustomDate() {
    const form = document.getElementById('customDateForm');
    form.classList.toggle('d-none');
}

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

        document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
        document.getElementById('customDateForm').classList.add('d-none');
    });
});

function toggleCustomDate() {
    document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
    document.getElementById('customDateForm').classList.toggle('d-none');
}

function filterByCustomDate() {
    const fromInput = document.getElementById('fromDate').value;
    const toInput = document.getElementById('toDate').value;

    if (!fromInput || !toInput)
        return;

    const from = new Date(fromInput);
    const to = new Date(toInput);
    to.setHours(23, 59, 59);

    filterRowsByDate(from, to);
}

function filterRowsByDate(from, to) {
    const table = document.getElementById('paymentTable');
    const rows = table.querySelectorAll('tbody tr');
    let hasVisible = false;

    rows.forEach(row => {
        const dateStr = row.getAttribute('data-date');
        const date = new Date(dateStr);
        if (date >= from && date <= to) {
            row.style.display = '';
            hasVisible = true;
        } else {
            row.style.display = 'none';
        }
    });

    table.closest('.table-responsive').style.display = hasVisible ? 'block' : 'none';
    document.getElementById('no-transaction')?.classList.toggle('d-none', hasVisible);
}

