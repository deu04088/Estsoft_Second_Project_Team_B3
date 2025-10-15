document.addEventListener("DOMContentLoaded", async () => {
    const tableBody = document.getElementById("postTableBody");
    const pagination = document.getElementById("pagination");
    const stateFilter = document.getElementById("stateFilter");
    const postCount = document.getElementById("postCount");

    const rowsPerPage = 15;
    let posts = [];
    let filtered = [];
    let currentPage = 1;

    async function loadPosts() {
        try {
            const res = await fetch("/api/admin/posts");
            const data = await res.json();

            if (!res.ok) {
                alert("게시물 데이터를 불러올 수 없습니다.");
                return;
            }

            posts = data.data;
            applyFilter();
        } catch (err) {
            console.error("게시물 목록 로드 실패:", err);
        }
    }

    function applyFilter() {
        const f = stateFilter.value;
        if (f === "public") {
            filtered = posts.filter(p => p.state === 1);
        } else if (f === "private") {
            filtered = posts.filter(p => p.state === 0);
        } else {
            filtered = [...posts];
        }

        postCount.textContent = filtered.length;
        currentPage = 1;
        renderTable();
        renderPagination();
    }

    function renderTable() {
        const start = (currentPage - 1) * rowsPerPage;
        const pageData = filtered.slice(start, start + rowsPerPage);

        tableBody.innerHTML = pageData
            .map(
                (post, idx) => `
                <tr>
                    <td>${start + idx + 1}</td>
                    <td>${post.restaurantName}</td>
                    <td>${post.menuName}</td>
                    <td>${post.bossNickName}</td>
                    <td>${post.address || '-'}</td>
                    <td>${post.createdAt ? post.createdAt.split('T')[0] : '-'}</td>
                    <td>
                        <button 
                            class="btn btn-sm ${post.state === 1 ? 'btn-outline-danger' : 'btn-outline-secondary'} toggle-btn"
                            data-id="${post.id}"
                            data-state="${post.state}">
                            ${post.state === 1 ? '비공개' : '공개'}
                        </button>
                    </td>
                </tr>
            `
            )
            .join("");

        bindToggleEvents();
    }

    function renderPagination() {
        const totalPages = Math.ceil(filtered.length / rowsPerPage);
        pagination.innerHTML = "";
        if (totalPages <= 1) return;

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
            link.addEventListener("click", e => {
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

    async function bindToggleEvents() {
        document.querySelectorAll(".toggle-btn").forEach(btn => {
            btn.addEventListener("click", async (e) => {
                const id = e.target.dataset.id;
                const currentState = parseInt(e.target.dataset.state, 10);
                const confirmMsg =
                    currentState === 0
                        ? "해당 게시글을 비공개 처리 하시겠습니까?"
                        : "해당 게시글을 공개 처리 하시겠습니까?";

                if (!confirm(confirmMsg)) return;

                try {
                    const res = await fetch(`/api/admin/posts/${id}/toggle`, { method: "PUT" });
                    const data = await res.json();

                    if (res.ok) {
                        alert(data.message);
                        await loadPosts();
                    } else {
                        alert(data.message || "처리 실패");
                    }
                } catch {
                    alert("서버 오류로 처리할 수 없습니다.");
                }
            });
        });
    }

    stateFilter.addEventListener("change", applyFilter);
    await loadPosts();
});
