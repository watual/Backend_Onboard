document.addEventListener("DOMContentLoaded", () => {
    // #알림창
    const customAlert = document.getElementById('customAlert');
    const alertMessage = document.getElementById('alertMessage');
    const alertClose = document.getElementById('alertClose');
    // 알림창 표시 함수
    function showAlert(message) {
        alertMessage.innerHTML = message; // 메시지를 HTML로 렌더링
        customAlert.classList.remove('hidden'); // 알림창 표시
    }
    // 알림창 닫기
    alertClose.addEventListener('click', () => {
        customAlert.classList.add('hidden'); // 알림창 숨기기
    });

    const modal = document.getElementById("chasingModal");
    let chaseCount = 0; // 사용자가 모달 근처로 접근한 횟수
    const baseThreshold = 5; // 몇 번 이상 쫓아야 랜덤으로 느려지기 시작
    const randomOffset = () => Math.floor(Math.random() * 3) + 1; // 1~3 추가 랜덤 횟수
    let currentThreshold = baseThreshold + randomOffset(); // 느려지는 랜덤 조건
    let isSlowedDown = false; // 현재 느려진 상태 여부

    function getRandomPosition(maxWidth, maxHeight) {
        const left = Math.random() * maxWidth;
        const top = Math.random() * maxHeight;
        return { left, top };
    }

    function clamp(value, min, max) {
        // 값을 화면 범위 안에 제한
        return Math.min(Math.max(value, min), max);
    }

    document.addEventListener("mousemove", (event) => {
        const modalRect = modal.getBoundingClientRect();
        const mouseX = event.clientX;
        const mouseY = event.clientY;

        const distanceX = Math.abs(mouseX - (modalRect.left + modalRect.width / 2));
        const distanceY = Math.abs(mouseY - (modalRect.top + modalRect.height / 2));

        // 모달 근처에 마우스가 있을 경우
        if (distanceX < 150 && distanceY < 150) {
            chaseCount++;

            const maxWidth = window.innerWidth - modal.offsetWidth;
            const maxHeight = window.innerHeight - modal.offsetHeight;

            if (chaseCount >= currentThreshold) {
                isSlowedDown = !isSlowedDown; // 상태 전환
                currentThreshold = baseThreshold + randomOffset(); // 다음 랜덤 임계값 재설정
                chaseCount = 0; // 쫓은 횟수 초기화
            }

            if (isSlowedDown) {
                // 느려진 상태: 작은 범위로 이동
                const newLeft = clamp(modalRect.left + (Math.random() * 50 - 25), 0, maxWidth);
                const newTop = clamp(modalRect.top + (Math.random() * 50 - 25), 0, maxHeight);
                modal.style.left = `${newLeft}px`;
                modal.style.top = `${newTop}px`;
            } else {
                // 빠른 상태: 화면 전체를 랜덤하게 이동
                const { left, top } = getRandomPosition(maxWidth, maxHeight);
                modal.style.left = `${left}px`;
                modal.style.top = `${top}px`;
            }
        }
    });

    modal.querySelector("button").addEventListener("click", () => {
        showAlert("You caught me!"); // 버튼 클릭 시 동작
    });
});