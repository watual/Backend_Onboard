document.addEventListener("DOMContentLoaded", () => {
    const contentBox = document.getElementById("mainContentBox");

    // Apply animation on load
    setTimeout(() => {
        contentBox.classList.add("show");
    }, 100);

    // Smooth scrolling for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            document.querySelector(this.getAttribute('href')).scrollIntoView({
                behavior: 'smooth'
            });
        });
    });
});
