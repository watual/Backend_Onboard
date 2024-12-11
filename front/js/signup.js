document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('signupForm');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const nicknameInput = document.getElementById('nickname');
    const firstnameInput = document.getElementById('firstname');
    const lastnameInput = document.getElementById('lastname');
    const emailInput = document.getElementById('email');

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

    // 회원가입 폼 제출 이벤트
    form.addEventListener('submit', (event) => {
        event.preventDefault(); // 기본 폼 제출 방지

        const username = usernameInput.value.trim();
        const password = passwordInput.value.trim();
        const nickname = nicknameInput.value.trim();
        const firstname = firstnameInput.value.trim();
        const lastname = lastnameInput.value.trim();
        const email = emailInput.value.trim();

        // 필수 입력값 체크
        if (!username) {
            showAlert('오잉? 이름이 없네요! 이름을 입력해주세요!');
            return;
        }

        if (!password) {
            showAlert('앗! 비밀번호를 잊으셨나요? 빨리 입력해주세요!');
            return;
        }

        if (!nickname) {
            showAlert('닉네임이 없다니! 멋진 별명을 지어주세요!');
            return;
        }

        if (!firstname) {
            showAlert('첫 번째 이름을 꼭 입력해주세요. 이건 필수랍니다!');
            return;
        }

        if (!lastname) {
            showAlert('성을 잊으셨나요? 성도 함께 입력해주세요!');
            return;
        }

        if (!email) {
            showAlert('이메일을 입력하지 않으셨어요! 확인 메일을 보내드릴 수 없습니다!');
            return;
        }

        // API 요청 데이터
        const data = {
            username: username,
            password: password,
            nickname: nickname,
            firstname: firstname,
            lastname: lastname,
            email: email
        };

        console.log('Signup Request Data:', data);

        // API 요청 (POST /api/signup)
        apiModule.POST('/api/signup', data,
            (response) => {
                // 회원가입 성공 처리
                console.log('Signup Success:', response);
                showAlert('"가입 성공, 물론입니다."<br>이건 제가 준비한 작은 선물이죠. 🌹<br>하지만 기대하세요. 여기서부터 더 멋진 일이 기다리고 있으니까요. 😌');
                setTimeout(() => {
                    window.location.href = './login.html'; // 로그인 페이지로 리다이렉트
                }, 3000);
            },
            (xhr) => {
                // 회원가입 실패 처리
                if (xhr.status === 409) {
                    showAlert('앗! 이미 등록된 사용자네요!<br>다른 멋진 닉네임이나 이메일을 생각해보세요! 😄');
                } else {
                    showAlert('오잉! 뭔가 잘못된 것 같아요!<br>잠시 후 다시 시도해 주세요. 저희가 바로 고쳐볼게요! 🤔');
                }
            }
        );
    });
});