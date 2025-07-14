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

