window.initMap = function() {
  map = new google.maps.Map(document.getElementById("map"), {
    zoom: 15,
    center: { lat: 37.5665, lng: 126.9780 } // 초기 위치 서울
  });
}

function getDongFromAddress(address, callback) {
  const geocoder = new google.maps.Geocoder();
  geocoder.geocode({ address: address }, (results, status) => {
    if (status === "OK" && results[0]) {
      const components = results[0].address_components;
      const dong = components.find(c =>
          c.types.includes("sublocality_level_1") ||
          c.types.includes("sublocality") ||
          c.types.includes("neighborhood")
      )?.long_name || "";

      const location = results[0].geometry.location;

      // 지도 중심 이동 및 마커 추가
      map.setCenter(location);
      new google.maps.Marker({
        position: location,
        map: map,
        title: address
      });

      //범위추가
      new google.maps.Circle({
        strokeColor: "#3399FF",    // 테두리 색상
        strokeOpacity: 0.6,        // 테두리 투명도
        strokeWeight: 2,           // 테두리 두께
        fillColor: "#3399FF",      // 내부 색상
        fillOpacity: 0.2,          // 내부 투명도
        map: map,
        center: location,
        radius: 1000                // 반경 500m
      });

      callback(dong);
    } else {
      callback(null);
    }
  });
}





function getCurrentDong(callback) {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(position => {
      const latlng = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };
      const geocoder = new google.maps.Geocoder();
      geocoder.geocode({location: latlng}, (results, status) => {
        if (status === "OK" && results[0]) {
          const components = results[0].address_components;
          const dong = components.find(c =>
              c.types.includes("sublocality_level_1") ||
              c.types.includes("sublocality") ||
              c.types.includes("neighborhood")
          )?.long_name || "";
          callback(dong);
        } else {
          callback(null);
        }
      });
    }, () => callback(null));
  } else {
    callback(null);
  }
}

document.getElementById("addressSearchBtn").addEventListener("click", () => {
  const inputAddress = document.getElementById("addressInput").value;
  getDongFromAddress(inputAddress, inputDong => {
    getCurrentDong(currentDong => {
      if (!inputDong || !currentDong) {
        document.getElementById("result").textContent = "동 정보를 가져올 수 없습니다.";
      } else if (inputDong === currentDong) {
        document.getElementById("result").textContent = "현재 위치와 입력 주소의 동이 일치합니다.";
      } else {
        document.getElementById("result").textContent = `불일치: 현재 ${currentDong}, 입력 ${inputDong}`;
      }
    });
  });
});

function loadGoogleMapsApi() {
  const script = document.createElement("script");
  script.src = `https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=initMap`;
  script.async = true;
  script.defer = true;
  document.head.appendChild(script);
}

window.onload = loadGoogleMapsApi;