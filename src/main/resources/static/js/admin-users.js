document.addEventListener("DOMContentLoaded", async () => {
    const tableBody = document.getElementById("userTableBody");
    const pagination = document.getElementById("pagination");
    const userCount = document.getElementById("userCount");
    const stateFilter = document.getElementById("stateFilter");

    const rowsPerPage = 15;
    let users = [];
    let filteredUsers = [];
    let currentPage = 1;

    async function loadUsers() {
        try {
            const res = await fetch("/api/admin/users");
            const data = await res.json();

            if (!res.ok) {
                alert("유저 데이터를 불러올 수 없습니다.");
                return;
            }

            users = data.data;
            applyFilter();
        } catch (err) {
            console.error("유저 목록 로드 실패:", err);
        }
    }

    // 필터 적용
    function applyFilter() {
        const filter = stateFilter.value;
        if (filter === "active") {
            filteredUsers = users.filter(u => u.state === 1);
        } else if (filter === "deleted") {
            filteredUsers = users.filter(u => u.state === 0);
        } else {
            filteredUsers = [...users];
        }

        userCount.textContent = filteredUsers.length;
        currentPage = 1;
        renderTable();
        renderPagination();
    }

    // 테이블 렌더링
    function renderTable() {
        const start = (currentPage - 1) * rowsPerPage;
        const end = start + rowsPerPage;
        const pageUsers = filteredUsers.slice(start, end);

        tableBody.innerHTML = pageUsers
            .map(
                (user, idx) => `
                <tr>
                    <td>${start + idx + 1}</td>
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
    }

    // 페이지네이션 렌더링
    function renderPagination() {
        const totalPages = Math.ceil(filteredUsers.length / rowsPerPage);
        pagination.innerHTML = "";

        if (totalPages <= 1) return; // 1페이지면 숨김

        pagination.innerHTML += `
            <li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage - 1}">&laquo;</a>
            </li>`;

        for (let i = 1; i <= totalPages; i++) {
            pagination.innerHTML += `
                <li class="page-item ${i === currentPage ? 'active' : ''}">
                    <a class="page-link" href="#" data-page="${i}">${i}</a>
                </li>`;
        }

        pagination.innerHTML += `
            <li class="page-item ${currentPage === totalPages ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage + 1}">&raquo;</a>
            </li>`;

        pagination.querySelectorAll(".page-link").forEach(link => {
            link.addEventListener("click", (e) => {
                e.preventDefault();
                const page = parseInt(e.target.dataset.page);
                if (!isNaN(page) && page >= 1 && page <= totalPages) {
                    currentPage = page;
                    renderTable();
                    renderPagination();
                    window.scrollTo({ top: 0, behavior: "smooth" });
                }
            });
        });
    }

    // 상태 토글 버튼 이벤트
    async function bindToggleEvents() {
        document.querySelectorAll(".toggle-btn").forEach(btn => {
            btn.addEventListener("click", async (e) => {
                const id = e.target.dataset.id;
                try {
                    const res = await fetch(`/api/admin/users/${id}/toggle`, { method: "PUT" });
                    const data = await res.json();

                    if (res.ok) {
                        alert(data.message);
                        await loadUsers();
                    } else {
                        alert(data.message || "처리 실패");
                    }
                } catch (err) {
                    alert("서버 오류로 처리할 수 없습니다.");
                }
            });
        });
    }

    // 필터 변경 감지
    stateFilter.addEventListener("change", applyFilter);

    await loadUsers();
});
