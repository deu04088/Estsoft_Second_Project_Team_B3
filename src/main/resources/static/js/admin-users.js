document.addEventListener("DOMContentLoaded", async () => {
    const tableBody = document.getElementById("userTableBody");

    async function loadUsers() {
        try {
            const res = await fetch("/api/admin/users");
            const data = await res.json();

            if (!res.ok) {
                alert("유저 데이터를 불러올 수 없습니다.");
                return;
            }

            const users = data.data;
            tableBody.innerHTML = users
                .map(
                    (user, idx) => `
                    <tr>
                        <td>${idx + 1}</td>
                        <td>${user.userName}</td>
                        <td>${user.nickName}</td>
                        <td>${user.createdAt ? user.createdAt.split('T')[0] : '-'}</td>
                        <td>${user.userAgent || '-'}</td>
                        <td>
                            <button 
                                class="btn btn-sm ${user.state === 1 ? 'btn-outline-danger' : 'btn-outline-secondary'} toggle-btn"
                                data-id="${user.id}"
                                data-state="${user.state}">
                                ${user.state === 1 ? '탈퇴' : '복구'}
                            </button>
                        </td>
                    </tr>
                `
                )
                .join("");

            bindToggleEvents();
        } catch (err) {
            console.error("유저 목록 로드 실패:", err);
        }
    }

    async function bindToggleEvents() {
        document.querySelectorAll(".toggle-btn").forEach(btn => {
            btn.addEventListener("click", async (e) => {
                const id = e.target.dataset.id;
                try {
                    const res = await fetch(`/api/admin/users/${id}/toggle`, { method: "PUT" });
                    const data = await res.json();

                    if (res.ok) {
                        alert(data.message);
                        loadUsers(); // 새로고침 없이 갱신
                    } else {
                        alert(data.message || "처리 실패");
                    }
                } catch (err) {
                    alert("서버 오류로 처리할 수 없습니다.");
                }
            });
        });
    }

    await loadUsers();
});
