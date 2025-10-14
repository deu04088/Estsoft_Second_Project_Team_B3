document.addEventListener("DOMContentLoaded", () => {
    const adminBtn = document.getElementById("adminAccessBtn");
    const confirmBtn = document.getElementById("adminConfirmBtn");
    const passwordInput = document.getElementById("adminPassword");
    const errorBox = document.getElementById("adminErrorBox");

    // 관리자 페이지 버튼 클릭 시 모달 표시
    if (adminBtn) {
        adminBtn.addEventListener("click", () => {
            const modalEl = document.getElementById("adminAuthModal");

            if (!modalEl) {
                return;
            }

            const modal = new bootstrap.Modal(modalEl);
            modal.show();
        });
    }

    // 비밀번호 입력 후 확인 버튼 클릭 시
    if (confirmBtn) {

        confirmBtn.addEventListener("click", async () => {

            const password = passwordInput.value.trim();

            if (!password) {
                return;
            }

            try {
                const res = await fetch("/api/admin/verify", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ password })
                });

                const data = await res.json();

                if (res.ok && data.status === 200) {

                    const currentPath = window.location.pathname;

                    //  관리자 페이지 안이라면 새로고침만
                    if (currentPath.startsWith("/admin")) {
                        location.reload();
                    }

                    //  일반 페이지라면 관리자 대시보드로 이동
                    else {
                        setTimeout(() => window.location.href = "/admin/dashboard", 300);
                    }

                } else {
                    errorBox.style.display = "block";
                    errorBox.textContent = data.message || "인증 실패";
                }

            } catch (err) {
                errorBox.style.display = "block";
                errorBox.textContent = "서버 오류가 발생했습니다.";
                console.error(err);
            }
        });
    }
});
