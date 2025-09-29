document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");
    const errorBox = document.getElementById("errorBox");

    // 비밀번호 보기 토글
    document.querySelectorAll(".toggle-password").forEach(btn => {
        btn.addEventListener("click", () => {
            const targetId = btn.getAttribute("data-target");
            const input = document.getElementById(targetId);
            if (input.type === "password") {
                input.type = "text";
                btn.classList.remove("fa-eye");
                btn.classList.add("fa-eye-slash");
            } else {
                input.type = "password";
                btn.classList.remove("fa-eye-slash");
                btn.classList.add("fa-eye");
            }
        });
    });

    // 로그인 처리
    loginForm.addEventListener("submit", event => {
        event.preventDefault();

        const userName = document.getElementById("userName").value;
        const password = document.getElementById("password").value;

        const data = {userName, password};

        fetch("/api/bosses/login", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(result => {
                if (result.status >= 200 && result.status < 300) {
                    if (result.data.address) {
                        window.location.href = "/restaurants";
                    } else {
                        window.location.href = "/address-certify";
                    }
                } else {
                    errorBox.style.display = "block";
                    errorBox.innerText = result.message || "로그인 실패";
                }
            })
            .catch(err => {
                errorBox.style.display = "block";
                errorBox.innerText = "서버 오류가 발생했습니다.";
                console.error(err);
            });
    });
});
