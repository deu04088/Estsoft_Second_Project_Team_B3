document.addEventListener("DOMContentLoaded", async () => {
    const tableBody = document.getElementById("postTableBody");

    async function loadPosts() {
        try {
            const res = await fetch("/api/admin/posts");
            const data = await res.json();

            if (!res.ok) {
                alert("게시물 데이터를 불러올 수 없습니다.");
                return;
            }

            const posts = data.data;
            tableBody.innerHTML = posts
                .map(
                    (post, idx) => `
                    <tr>
                        <td>${idx + 1}</td>
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
        } catch (err) {
            console.error("게시물 목록 로드 실패:", err);
        }
    }

    async function bindToggleEvents() {
        document.querySelectorAll(".toggle-btn").forEach(btn => {
            btn.addEventListener("click", async (e) => {
                const id = e.target.dataset.id;
                const currentState = parseInt(e.target.dataset.state, 10);

                const confirmMessage =
                    currentState === 0
                        ? "해당 게시글을 공개 처리 하시겠습니까?"
                        : "해당 게시글을 비공개 처리 하시겠습니까?";

                if (!confirm(confirmMessage)) return;

                try {
                    const res = await fetch(`/api/admin/posts/${id}/toggle`, { method: "PUT" });
                    const data = await res.json();

                    if (res.ok) {
                        alert(data.message);
                        loadPosts();
                    } else {
                        alert(data.message || "처리 실패");
                    }
                } catch (err) {
                    alert("서버 오류로 처리할 수 없습니다.");
                }
            });
        });
    }

    await loadPosts();
});
