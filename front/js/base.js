document.addEventListener('DOMContentLoaded', () => {
    // 알림창 준비
    const bodyElement = document.body; // body 끝에 삽입

    // customAlert.html 파일 동적 로드
    fetch('../html/customAlert.html')
        .then(response => response.text())
        .then(data => {
            // HTML 내용을 body 끝에 추가
            bodyElement.insertAdjacentHTML('beforeend', data);

            // 알림창 닫기 버튼 이벤트 리스너 등록
            const alertClose = document.getElementById('alertClose');
            const customAlert = document.getElementById('customAlert');

            if (alertClose && customAlert) {
                alertClose.addEventListener('click', () => {
                    customAlert.classList.add('hidden');
                });
            }
        })
        .catch(error => console.error('Error loading customAlert:', error));


    const links = document.querySelectorAll('.transition-link');
    const contentBox = document.getElementById('contentBox');

    // 등장 애니메이션 추가
    setTimeout(() => {
        contentBox.classList.add('show'); // "뽀잉~" 애니메이션 활성화
    }, 100); // 약간의 지연 시간 후 실행

    // 페이지 전환 이벤트
    links.forEach(link => {
        link.addEventListener('click', (event) => {
            event.preventDefault();

            // 페이드 아웃 애니메이션
            contentBox.classList.add('fade-out');

            // 페이드 아웃 후 페이지 이동
            setTimeout(() => {
                window.location.href = link.href;
            }, 500); // 페이드 아웃 지속 시간
        });
    });
});

// api 요청 자동화

// 테스트와 배포 환경에 따라 API 주소를 설정
const apiBaseUrl = window.location.hostname === 'localhost'
    ? 'https://api.watual.store/:8080' // 테스트 환경
    : ''; // 배포 환경(상대 경로)

// const imageUrl = 'https://image.tmdb.org/t/p/w600_and_h900_bestv2';
// const imageUrl = 'https://image.tmdb.org/t/p/w220_and_h330_face';

let apiModule = (function() {
    // 기본 설정
    let settings = {
        baseUrl: apiBaseUrl,
    };

    // 내부 함수: Ajax 요청을 보내는 함수
    function ajaxRequest(method, url, data, successCallback, errorCallback, options = {}) {
        let fullUrl = settings.baseUrl + url;
        let ajaxOptions = {
            url: fullUrl,
            type: method,
            headers: settings.headers,
            xhrFields: {
                withCredentials: true
            },
            success: function(result, status, xhr) {
                console.log(`요청 성공: ${method} ${fullUrl}\n`, result);
                if (successCallback) successCallback(result);
            },
            error: function(xhr, status, error) {
                console.error(`요청 실패:  ${method} ${fullUrl}\n`
                    + `상태 코드 : ${xhr.status}\n`
                    + `응답 데이터 : ${JSON.stringify(xhr, null , 2)}\n`
                );
                if (xhr.responseJSON.code === 401) {
                    alert(`${xhr.responseJSON.message}\n로그인을 해주세요.`);
                    location.href = '../html/login.html';
                    return;
                }
                if (xhr.responseJSON.code === 403) {
                    alert(`${xhr.responseJSON.message}\n로그인을 해주세요.`);
                    location.href = '../html/login.html';
                    return;
                }
                if (errorCallback) {}errorCallback(xhr, status, error);
            }
        };

        if (options.processData === false) {
            ajaxOptions.data = data;
            ajaxOptions.contentType = options.contentType;
            ajaxOptions.processData = options.processData;
        } else {
            ajaxOptions.data = data ? JSON.stringify(data) : null;
            ajaxOptions.contentType = 'application/json';
            ajaxOptions.processData = true;
            console.log(ajaxOptions.data);
        }

        $.ajax(ajaxOptions);
    }

    // GET 요청
    function GET(url, successCallback, errorCallback) {
        ajaxRequest('GET', url, null, successCallback, errorCallback);
    }

    // POST 요청
    function POST(url, data, successCallback, errorCallback, options) {
        ajaxRequest('POST', url, data, successCallback, errorCallback, options);
    }

    // PATCH 요청
    function PATCH(url, data, successCallback, errorCallback, options) {
        ajaxRequest('PATCH', url, data, successCallback, errorCallback, options);
    }

    // PUT 요청
    function PUT(url, data, successCallback, errorCallback, options) {
        ajaxRequest('PUT', url, data, successCallback, errorCallback, options);
    }

    // DELETE 요청
    function DELETE(url, data = null, successCallback, errorCallback, options) {
        ajaxRequest('DELETE', url, data, successCallback, errorCallback, options);
    }

    // 외부에 공개할 API
    return {
        GET: GET,
        POST: POST,
        PATCH: PATCH,
        PUT: PUT,
        DELETE: DELETE,
        settings: settings // 설정을 외부에서 수정 가능
    };
})();