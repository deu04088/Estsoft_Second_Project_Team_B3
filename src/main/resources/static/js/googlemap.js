let map;
let marker;
let circle;
const certifyBtn = document.getElementById("certifyBtn");
const hiddenAddress = document.getElementById("hiddenAddress");
// -----------------------------
// 지도 초기화

function initMap() {
  const initialLocation = { lat: 37.5665, lng: 126.9780 };
  map = new google.maps.Map(document.getElementById("map"), {
    center: initialLocation,
    zoom: 15
  });

  marker = new google.maps.Marker({
    position: initialLocation,
    map: map,
    title: "현재 위치"
  });

  circle = new google.maps.Circle({
    map,
    center: initialLocation,
    radius: 1000,
    strokeColor: "#3399FF",
    strokeOpacity: 0.6,
    strokeWeight: 2,
    fillColor: "#3399FF",
    fillOpacity: 0.2
  });
}

// -----------------------------
// 입력 주소에서 동/읍/면 추출
export function extractDongEupMyeon(address) {
  const match = address.match(/(\S+(동|읍|면))/);
  return match ? match[0] : "";
}

// -----------------------------
// 현재 위치에서 동/읍/면 추출
export function getCurrentDongEupMyeon(lat, lng, callback) {
  const geocoder = new google.maps.Geocoder();
  geocoder.geocode({ location: { lat, lng } }, (results, status) => {
    if (status === "OK" && results[0]) {
      let current = "";

      for (let i = 0; i < Math.min(results.length,2); i++) {
        const components = results[i].address_components || [];
        const level1 = components.find(c => c.types.includes("sublocality_level_1"));
        const level2 = components.find(c => c.types.includes("sublocality_level_2"));

        // level_1에 동/읍/면이 있으면 우선 사용
        if (level1 && /(동|읍|면)$/.test(level1.long_name)) {
          current = level1.long_name;
          break;
        } else if (level2 && /(동|읍|면)$/.test(level2.long_name)) {
          // level_1에 없으면 level_2 사용
          current = level2.long_name;
          break;
        }
      }
      callback(current || null);
    } else {
      callback(null);
    }
  });
}

// -----------------------------
// 버튼 이벤트
export function setupButton() {
  const resultBox = document.getElementById("result");
  const currentBox = document.getElementById("ggaddress");
  const certifyBtn = document.getElementById("certifyBtn");
  const hiddenAddress = document.getElementById("hiddenAddress");

  document.querySelectorAll(".addressBtn").forEach(btn => {
    btn.addEventListener("click", async () => {
      const address = document.getElementById("addressInput").value.trim();
      const regex = /^[가-힣]+(시|도)\s([가-힣]+(구|군|시)\s)?[가-힣]+(동|읍|면)$/;
      if (!address) {
        alert("주소를 입력해주세요");
        certifyBtn.disabled = true;
        return;
      }
      if (!regex.test(address)) {
        alert("주소를 예시(서울특별시 종로구 회현동)와 같이 입력해주세요");
        certifyBtn.disabled = true;
        return;
      }
      // 행안부 API 유효성 체크 및 데이터 가져오기
      try {
        const res = await fetch('/api/check-address', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({address})
        });

        const data = await res.json();

        if (!data || !data.juso || data.juso.length === 0) {
          alert("존재하지 않는 주소입니다.");
          certifyBtn.disabled = true;
          return;
        }
        //json의 형태로 가져오고 아래와 같이 데이터 합쳐서 써도되고 따로써도됨
        // 시/도 + 구/군 + 동/읍/면 가져오기
        const juso = data.juso[0];
        const fullAddress = [juso.siNm, juso.sggNm, juso.emdNm]
        .filter(p => p && p.trim() !== "")
        .join(" ");

      // 기기위치로 지도 새로 표시
      const geocoder = new google.maps.Geocoder();
      geocoder.geocode({address : fullAddress}, (results, status) => {
        if (status === "OK" && results[0]) {
          const location = results[0].geometry.location;

          // 지도 중심이동 + 마커 + 원
          map.setCenter(location);
          marker.setPosition(location);
          circle.setCenter(location);

          // 동/읍/면 비교
          const inputDong = extractDongEupMyeon(fullAddress);

          if (!navigator.geolocation) {
            resultBox.textContent = "브라우저에서 위치 정보를 지원하지 않습니다.";
            certifyBtn.disabled = true;
            return;
          }

          navigator.geolocation.getCurrentPosition((pos) => {
            const lat = pos.coords.latitude;
            const lng = pos.coords.longitude;

            getCurrentDongEupMyeon(lat, lng, (currentDong) => {
              if (!inputDong || !currentDong) {
                resultBox.textContent = "위치 정보 가져오기 실패 다시 시도해주세요.";
                certifyBtn.disabled = true;
                return;
              }

              if (inputDong === currentDong) {
                currentBox.textContent = fullAddress;
                resultBox.textContent = "현재 위치가 내 동내 설정과 같습니다.";
                hiddenAddress.value = fullAddress;
                certifyBtn.disabled = false;
              } else {
                currentBox.textContent = fullAddress;
                //원활한 테스트를 위해 다를경우 현재 기기위치의 동을 표시해 줍니다.
                resultBox.textContent = `현재 위치가 내 동네 설정과 다릅니다. 현재 위치 ${currentDong}`;
                certifyBtn.disabled = true;
              }
            });
          }, (err) => {
            let message = "위치 정보 가져오기 실패, 다시 시도해주세요.";
            certifyBtn.disabled = true;
            switch (err.code) {
              case 1:
                message = "브라우저 또는 장치에서 위치 접근이 차단되었습니다. 설정에서 위치 허용 후 다시 시도해주세요.";
                break;
              case 2:
                message = "기기에서 위치 정보를 가져올 수 없습니다. GPS또는 네트워크 위치 서비스를 켜주세요.";
                break;
              case 3:
                message = "위치 정보를 가져오는데 시간이 초과되었습니다. 네트워크 상태를 확인해주세요.";
                break;
            }
            resultBox.textContent = message;
          });
        } else {
          resultBox.textContent = "구글 좌표 변환에 실패했습니다. 관리자에게 문의해주세요";
          certifyBtn.disabled = true;
        }
      });
      } catch (err) {
        console.error(err);
        resultBox.textContent = "주소 검증 중 오류 발생, 다시 시도해주세요.";
        certifyBtn.disabled = true;
      }
    });
  });
}


certifyBtn.addEventListener("click", async () => {
  const address = hiddenAddress.value;

  try {
    const res = await fetch('/api/update-address', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ address }),
      //credentials: 'include' 토큰 전달부 시큐리티 완성후 풀기
    });

    if (res.ok) {
      // POST 성공 후 /restaurants 페이지 이동해야하나 일단 메인으로 이동하게
      window.location.href = '/restaurants';
    } else {
      alert("주소 저장 실패, 다시 시도해주세요.");
    }
  } catch (err) {
    alert("서버 통신 오류");
    console.error(err);
  }
});


// 구글 지도 로드 (HTML에서 API Key 주입)
export function loadGoogleMaps() {
  return new Promise((resolve, reject) => {
    const script = document.createElement("script");
    script.src = `https://maps.googleapis.com/maps/api/js?key=${window.GOOGLE_MAPS_API_KEY}&callback=initMap`;
    script.async = true;
    script.defer = true;
    window.initMap = () => {
      initMap();
      resolve();
    };
    script.onerror = reject;
    document.head.appendChild(script);
  });
}

// -----------------------------
// 초기화
window.addEventListener("DOMContentLoaded", async () => {
  await loadGoogleMaps();
  setupButton();
});