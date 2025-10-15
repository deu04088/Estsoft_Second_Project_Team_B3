document.addEventListener("DOMContentLoaded", async () => {
    const tableBody = document.getElementById("statTableBody");
    const pagination = document.getElementById("pagination");
    const chartFilter = document.getElementById("chartFilter");

    let stats = [];
    const rowsPerPage = 15;
    let currentPage = 1;
    let barChart, doughnutChart, horizontalChart;
    let map, mapLayerGroup;

    /** 데이터 로드 */
    async function loadStats() {
        try {
            const res = await fetch("/api/admin/stats/regions");
            const data = await res.json();
            console.log("📊 샘플 데이터:", data.data[0]);
            stats = data.data;

            updateView("sido"); // 기본 시도 기준으로 초기화
        } catch (err) {
            console.error("통계 데이터를 불러오지 못했습니다:", err);
        }
    }

    /** 기준 변경 시 전체 갱신 */
    function updateView(criterion) {
        const groupedData = aggregateData(stats, criterion);

        renderTable(groupedData, 1, criterion);
        renderCharts(groupedData, criterion);
        renderMap(stats); // 지도는 항상 시도 기준
    }

    /** 기준별 집계 (차트+표 공용) */
    function aggregateData(data, criterion) {
        const grouped = {};

        data.forEach(s => {
            // ✅ 다양한 키 이름 대응 (siDo, sido, SIDO 등)
            const sido = s.siDo || s.sido || s.SIDO || s.sidoNm;
            const gugun = s.guGun || s.gugun || s.GUGUN || s.gugunNm;
            const dong = s.dongEupMyeon || s.dong || s.dongeupmyeon || s.DONGEUPMYEON;
            const count = s.count || s.totalCount || 1;

            const key =
                criterion === "sido" ? (sido || "기타")
                    : criterion === "gugun" ? (gugun || "기타")
                        : (dong || "기타");

            grouped[key] = (grouped[key] || 0) + count;
        });

        // 객체를 배열로 변환 + 내림차순 정렬
        return Object.entries(grouped)
            .map(([region, count]) => ({ region, count }))
            .sort((a, b) => b.count - a.count);
    }

    /** 표 렌더링 */
    function renderTable(groupedData, page, criterion) {
        const start = (page - 1) * rowsPerPage;
        const end = start + rowsPerPage;
        const sliced = groupedData.slice(start, end);

        // 헤더명 결정
        const headerName =
            criterion === "sido" ? "시/도"
                : criterion === "gugun" ? "구/군"
                    : "동/읍/면";

        // 표 전체 렌더링
        const tableHtml = `
            <table class="table align-middle text-center">
              <thead class="table-light">
                <tr>
                  <th>#</th>
                  <th>${headerName}</th>
                  <th>식당 수</th>
                </tr>
              </thead>
              <tbody>
                ${sliced.map((s, idx) => `
                    <tr ${idx === 0 ? 'class="table-warning fw-semibold"' : ""}>
                      <td>${start + idx + 1}</td>
                      <td>${s.region}</td>
                      <td>${s.count}</td>
                    </tr>`
        ).join("")}
              </tbody>
            </table>
          `;

        document.querySelector(".table-responsive").innerHTML = tableHtml;
        renderPagination(groupedData, criterion);
    }

    /** 페이지네이션 */
    function renderPagination(groupedData, criterion) {
        const totalPages = Math.ceil(groupedData.length / rowsPerPage);
        pagination.innerHTML = Array.from({ length: totalPages }, (_, i) => `
            <li class="page-item ${i + 1 === currentPage ? 'active' : ''}">
              <a class="page-link" href="#">${i + 1}</a>
            </li>
        `).join("");

        pagination.querySelectorAll(".page-link").forEach((btn, i) => {
            btn.addEventListener("click", e => {
                e.preventDefault();
                currentPage = i + 1;
                renderTable(groupedData, currentPage, criterion);
            });
        });
    }

    /** 차트 렌더링 */
    function renderCharts(groupedData, criterion) {
        const labels = groupedData.map(g => g.region);
        const values = groupedData.map(g => g.count);

        if (barChart) barChart.destroy();
        if (doughnutChart) doughnutChart.destroy();
        if (horizontalChart) horizontalChart.destroy();

        const barCtx = document.getElementById("regionBarChart");
        const doughnutCtx = document.getElementById("regionDoughnutChart");
        const horizontalCtx = document.getElementById("regionHorizontalChart");

        const colors = [
            "#0d6efd", "#6610f2", "#6f42c1", "#d63384", "#dc3545",
            "#fd7e14", "#ffc107", "#198754", "#20c997", "#0dcaf0",
            "#17a2b8", "#adb5bd"
        ];

        // 세로 막대
        barChart = new Chart(barCtx, {
            type: "bar",
            data: { labels, datasets: [{ label: "식당 수", data: values, backgroundColor: "rgba(13,110,253,0.5)" }] },
            options: {
                responsive: true,
                plugins: {
                    legend: { display: false },
                    title: { display: true, text: `${headerText(criterion)}별 등록 식당 수` }
                },
                scales: { y: { beginAtZero: true } }
            }
        });

        // 도넛
        doughnutChart = new Chart(doughnutCtx, {
            type: "doughnut",
            data: { labels, datasets: [{ data: values, backgroundColor: colors.slice(0, labels.length) }] },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                cutout: "55%",
                plugins: {
                    legend: { display: false },
                    datalabels: {
                        color: "#fff",
                        font: { weight: "bold" },
                        formatter: (v, ctx) => `${((v / ctx.chart._metasets[0].total) * 100).toFixed(1)}%`
                    }
                }
            },
            plugins: [ChartDataLabels]
        });

        // 가로 막대
        horizontalChart = new Chart(horizontalCtx, {
            type: "bar",
            data: { labels, datasets: [{ data: values, backgroundColor: colors.slice(0, labels.length) }] },
            options: {
                indexAxis: "y",
                responsive: true,
                plugins: {
                    legend: { display: false },
                    datalabels: {
                        color: "#000",
                        anchor: "end",
                        align: "right",
                        formatter: v => v
                    }
                },
                scales: { x: { beginAtZero: true } }
            },
            plugins: [ChartDataLabels]
        });
    }

    /** 기준명 텍스트 변환 */
    function headerText(criterion) {
        return criterion === "sido" ? "시/도"
            : criterion === "gugun" ? "구/군"
                : "동/읍/면";
    }

    /** 지도 (항상 시도 기준 고정) */
    function renderMap(data) {
        if (mapLayerGroup) mapLayerGroup.clearLayers();
        if (!map) {
            map = L.map("regionMap").setView([36.5, 127.9], 7);
            L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
                attribution: '&copy; OpenStreetMap contributors'
            }).addTo(map);
            mapLayerGroup = L.layerGroup().addTo(map);
        }

        const coords = {
            "서울특별시": [37.5665, 126.9780],
            "경기도": [37.4138, 127.5183],
            "부산광역시": [35.1796, 129.0756],
            "대구광역시": [35.8714, 128.6014],
            "인천광역시": [37.4563, 126.7052],
            "광주광역시": [35.1595, 126.8526],
            "대전광역시": [36.3504, 127.3845],
            "울산광역시": [35.5384, 129.3114],
            "강원도": [37.8228, 128.1555],
            "충청북도": [36.8, 127.7],
            "충청남도": [36.5184, 126.8],
            "전라북도": [35.7175, 127.153],
            "전라남도": [34.8679, 126.991],
            "경상북도": [36.4919, 128.8889],
            "경상남도": [35.4606, 128.2132],
            "제주특별자치도": [33.4996, 126.5312]
        };

        const grouped = aggregateData(data, "sido");
        grouped.forEach(r => {
            const loc = coords[r.region];
            if (!loc) return;
            const radius = Math.sqrt(r.count) * 4;
            L.circleMarker(loc, {
                radius,
                color: "#0d6efd",
                fillColor: "#0d6efd",
                fillOpacity: 0.5
            })
                .bindPopup(`<b>${r.region}</b><br>등록 식당: ${r.count}개`)
                .addTo(mapLayerGroup);
        });
    }

    /** 드롭다운 변경 시 */
    chartFilter.addEventListener("change", () => {
        const criterion = chartFilter.value;
        updateView(criterion);
    });

    await loadStats();
});
