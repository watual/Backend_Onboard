document.addEventListener("DOMContentLoaded", () => {
    const numStars = 900; // 생성할 별의 개수
    const body = document.body;

    for (let i = 0; i < numStars; i++) {
        const star = document.createElement("div");
        star.classList.add("star");

        // 랜덤 위치와 크기
        star.style.left = Math.random() * 100 + "vw";
        star.style.top = Math.random() * 100 + "vh";
        star.style.width = Math.random() * 3 + 2 + "px";
        star.style.height = star.style.width;

        // 랜덤 애니메이션 속도와 딜레이
        star.style.animationDelay = Math.random() * 5 + "s";
        star.style.animationDuration = 3 + Math.random() * 5 + "s";

        body.appendChild(star);
    }
});

