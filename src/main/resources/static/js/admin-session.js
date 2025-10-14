// admin-session.js
document.addEventListener("DOMContentLoaded", async () => {
    const timerEl = document.getElementById("session-timer");
    const modalEl = document.getElementById("adminAuthModal");
    const timerTextEl = document.getElementById("timer-text");

    // 모달 객체 준비
    const modal = modalEl ? new bootstrap.Modal(modalEl) : null;

    // -------------------------
    // [1] 남은 시간 가져오기
    // -------------------------
    async function fetchRemainingTime() {
        try {

            const res = await fetch("/api/admin/remaining-time", {
                headers: {"X-Requested-With": "XMLHttpRequest"}
            });

            if (!res.ok) {
                throw new Error("세션 만료");
            }

            const data = await res.json();

            return data.data; // 남은 시간(초)
        } catch (err) {

            console.warn("남은 시간 조회 실패:", err);

            return 0;
        }
    }

    // -------------------------
    // [2] 세션 상태 체크 (1분마다)
    // -------------------------
    async function checkAdminSession() {
        try {
            const res = await fetch("/api/admin/check", {

                headers: {"X-Requested-With": "XMLHttpRequest"}
            });

            if (res.status === 401 && modal) {
                modal.show();

                if (timerEl) {
                    timerEl.textContent = "세션이 만료되었습니다. 다시 인증해주세요.";
                }
            }
        } catch (err) {

            console.error("세션 확인 중 오류:", err);
        }
    }

    // -------------------------
    // [3] 타이머 초기화 및 실행
    // -------------------------
    async function initTimer() {
        let remaining = await fetchRemainingTime();

        if (!timerEl) {
            return;
        } // 타이머 표시 요소 없으면 종료

        if (remaining <= 0) {
            timerEl.textContent = "세션이 만료되었습니다. 다시 인증해주세요.";

            if (modal) {
                modal.show();
            }

            return;
        }

        // 1초 단위로 감소
        const timer = setInterval(() => {
            remaining--;

            const min = Math.floor(remaining / 60);
            const sec = remaining % 60;

            if (timerTextEl) {
                timerTextEl.textContent = `${min}분 ${sec < 10 ? "0" + sec : sec}초 남음`;
            }


            if (remaining <= 0) {
                clearInterval(timer);
                timerEl.textContent = "세션이 만료되었습니다. 다시 인증해주세요.";

                if (modal) {
                    modal.show();
                }
            }
        }, 1000);
    }

    // -------------------------
    // [4] 주기적 세션 체크 시작
    // -------------------------
    setInterval(checkAdminSession, 60000); // 1분마다 백엔드 유효성 체크
    await initTimer();


    // -------------------------
    // [5] 세션 연장 기능
    // -------------------------
    document.getElementById("extendSessionBtn")?.addEventListener("click", async () => {
        try {

            const res = await fetch("/api/admin/verify/extend", {
                method: "POST",
                headers: {"X-Requested-With": "XMLHttpRequest"}
            });

            const data = await res.json();

            if (res.ok && data.status === 200) {
                alert("관리자 세션이 10분 연장되었습니다.");

                location.reload(); // 타이머 갱신
            } else {

                alert(data.message || "연장 실패");
            }
        } catch (err) {

            alert("서버 오류로 연장할 수 없습니다.");
        }
    });

});
