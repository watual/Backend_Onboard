document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('loginForm');
    const usernameInput = document.querySelector('input[type="text"]');
    const passwordInput = document.querySelector('input[type="password"]');

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


    // 로그인 폼 제출 이벤트
    form.addEventListener('submit', (event) => {
        event.preventDefault(); // 기본 폼 제출 방지

        const username = usernameInput.value.trim();
        const password = passwordInput.value.trim();

        if (!username || !password) {
            showAlert('Username and Password are required.');
            return;
        }

        // API 요청 (POST /api/login)
        let data = {
            username: username,
            password: password
        };
        console.log(data);
        apiModule.POST('/api/login', data,
            (response) => {
                // 로그인 성공 처리
                console.log(response);
                showAlert(`Welcome, ${response.username || username}!`);
                window.location.href = './main.html'; // 홈 페이지로 리다이렉트
            },
            (xhr) => {
                // 로그인 실패 처리
                if (xhr.status === 401) {
                    showAlert('Invalid username or password. Please try again.');
                } else {
                    showAlert('An error occurred. Please try again later.');
                }
            }
        );
    });
});