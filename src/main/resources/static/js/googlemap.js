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
      const components = results[0].address_components;
      let current = "";
      components.forEach(comp => {
        if (comp.types.includes("sublocality_level_2")) {
          current = comp.long_name;
        }
      });
      callback(current);
    } else {
      callback(null);
    }
  });
}

// -----------------------------
// 버튼 이벤트
export function setupButton() {
  const btn = document.getElementById("addressSearchBtn");
  const resultBox = document.getElementById("result");
  const currentBox = document.getElementById("ggaddress");

  btn.addEventListener("click", async () => {
    const address = document.getElementById("addressInput").value.trim();
    const regex = /^[가-힣]+(시|도)\s[가-힣]+(구|군|시)\s[가-힣]+(동|읍|면)$/;
    if (!regex.test(address)) {
      alert("주소를 동/읍/면까지 정확히 입력해주세요");
      return;
    }
    if (!address) {
      resultBox.textContent = "주소를 입력해주세요.";
      certifyBtn.disabled = true;
      return;
    }

    // 행안부 API 유효성 체크
    try {
      const res = await fetch(`/api/check-address?address=${encodeURIComponent(address)}`);
      const status = await res.text();
      if (status !== "VALID") {
        resultBox.textContent = "존재하지 않는 주소입니다.";
        certifyBtn.disabled = true;
        return;
      }
    } catch {
      resultBox.textContent = "주소 검증 중 오류 발생";
      certifyBtn.disabled = true;
      return;
    }

    // 기기위치로 지도 새로 표시
    const geocoder = new google.maps.Geocoder();
    geocoder.geocode({ address }, (results, status) => {
      if (status === "OK" && results[0]) {
        const location = results[0].geometry.location;

        // 지도 중심이동 + 마커 + 원
        map.setCenter(location);
        marker.setPosition(location);
        circle.setCenter(location);

        // 동/읍/면 비교
        const inputDong = extractDongEupMyeon(address);

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
              currentBox.textContent = `${address}`;
              resultBox.textContent = '현재 위치가 내 동내 설정과 같습니다.';
              hiddenAddress.value = address;
              certifyBtn.disabled = false;
            } else {
              currentBox.textContent = `${address}`;
              //원활한 테스트를 위해 다를경우 현재 기기위치의 동을 표시해 줍니다.
              resultBox.textContent = '현재 위치가 내 동내 설정과 다릅니다. 기기위치'+ `${currentDong}`;
              certifyBtn.disabled = true;
            }
          });

        }, () => {
          resultBox.textContent = "위치 정보 가져오기 실패 다시 시도해주세요.";
          certifyBtn.disabled = true;
        });

      } else {
        resultBox.textContent = "위치 정보 가져오기 실패 다시 시도해주세요.";
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
      credentials: 'include'
    });

    if (res.ok) {
      // POST 성공 후 /restaurants 페이지 이동해야하나 일단 메인으로 이동하게
      window.location.href = '/';
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