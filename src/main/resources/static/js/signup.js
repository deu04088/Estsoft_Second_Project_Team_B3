document.addEventListener("DOMContentLoaded", () => {
    const userNameInput = document.getElementById("userName");
    const userNameIcon = document.getElementById("userNameIcon");
    const userNameHint = document.getElementById("userNameHint");

    const nickNameInput = document.getElementById("nickName");
    const nickNameIcon = document.getElementById("nickNameIcon");
    const nickNameHint = document.getElementById("nickNameHint");

    const passwordInput = document.getElementById("password");
    const passwordIcon = document.getElementById("passwordIcon");
    const passwordHint = document.getElementById("passwordHint");
    const passwordStrength = document.getElementById("passwordStrength");

    const confirmPasswordInput = document.getElementById("confirmPassword");
    const confirmIcon = document.getElementById("confirmIcon");
    const confirmHint = document.getElementById("confirmHint");

    const errorBox = document.getElementById("errorBox");
    const successBox = document.getElementById("successBox");

    // 공통 함수
    function setValid(icon, hint, message) {
        icon.classList.add("visible");
        icon.classList.remove("fa-circle-xmark");
        icon.classList.add("fa-circle-check");
        icon.style.color = "#28a745";
        hint.textContent = message;
        hint.classList.add("visible", "valid");
        hint.classList.remove("invalid");
    }

    function setInvalid(icon, hint, message) {
        icon.classList.add("visible");
        icon.classList.remove("fa-circle-check");
        icon.classList.add("fa-circle-xmark");
        icon.style.color = "#d9534f";
        hint.textContent = message;
        hint.classList.add("visible", "invalid");
        hint.classList.remove("valid");
    }

    // 아이디 검사
    userNameInput.addEventListener("input", () => {
        const value = userNameInput.value;
        if (/^[A-Za-z0-9]{4,}$/.test(value)) {
            setValid(userNameIcon, userNameHint, "사용 가능한 아이디입니다.");
        } else if (value.length > 0) {
            setInvalid(userNameIcon, userNameHint, "영문/숫자 조합, 4자 이상 입력해주세요.");
        } else {
            userNameIcon.classList.remove("visible");
            userNameHint.classList.remove("visible");
        }
    });

    // 닉네임 검사
    nickNameInput.addEventListener("input", () => {
        const value = nickNameInput.value;
        if (/^[A-Za-z0-9가-힣]{2,12}$/.test(value)) {
            setValid(nickNameIcon, nickNameHint, "사용 가능한 닉네임입니다.");
        } else if (value.length > 0) {
            setInvalid(nickNameIcon, nickNameHint, "닉네임은 2~12자의 한글/영문/숫자만 가능합니다.");
        } else {
            nickNameIcon.classList.remove("visible");
            nickNameHint.classList.remove("visible");
        }
    });

    // 비밀번호 검사
    passwordInput.addEventListener("input", () => {
        const value = passwordInput.value;
        const hasLetter = /[A-Za-z]/.test(value);
        const hasNumber = /\d/.test(value);
        const longEnough = value.length >= 8;

        if (value.length === 0) {
            passwordIcon.classList.remove("visible");
            passwordHint.classList.remove("visible");
            passwordStrength.classList.remove("visible");
            return;
        }

        passwordStrength.classList.add("visible");

        if (hasLetter && hasNumber && longEnough) {
            setValid(passwordIcon, passwordHint, "사용 가능한 비밀번호입니다.");
        } else {
            setInvalid(passwordIcon, passwordHint, "영문과 숫자를 모두 포함해 8자 이상 입력해주세요.");
        }

        // 안전도
        if (!longEnough || (!hasLetter && !hasNumber)) {
            passwordStrength.textContent = "사용불가";
            passwordStrength.className = "password-strength-badge weak visible";
        } else if (hasLetter && hasNumber && value.length < 12) {
            passwordStrength.textContent = "보통";
            passwordStrength.className = "password-strength-badge medium visible";
        } else if (hasLetter && hasNumber && value.length >= 12) {
            passwordStrength.textContent = "안전";
            passwordStrength.className = "password-strength-badge safe visible";
        }

        if (confirmPasswordInput.value) {
            confirmPasswordInput.dispatchEvent(new Event("input"));
        }
    });

    // 비밀번호 확인 검사
    confirmPasswordInput.addEventListener("input", () => {
        const value = confirmPasswordInput.value;
        if (value.length === 0) {
            confirmIcon.classList.remove("visible");
            confirmHint.classList.remove("visible");
            return;
        }

        if (passwordInput.value === value) {
            setValid(confirmIcon, confirmHint, "비밀번호가 일치합니다.");
        } else {
            setInvalid(confirmIcon, confirmHint, "동일한 비밀번호를 다시 입력해주세요.");
        }
    });

    // 비밀번호 보기 토글
    document.querySelectorAll(".toggle-password").forEach(btn => {
        btn.addEventListener("click", () => {
            const targetId = btn.getAttribute("data-target");
            const input = document.getElementById(targetId);
            if (input.type === "password") {
                input.type = "text";
                btn.classList.remove("fa-eye");
                btn.classList.add("fa-eye-slash");
            } else {
                input.type = "password";
                btn.classList.remove("fa-eye-slash");
                btn.classList.add("fa-eye");
            }
        });
    });

    // 회원가입 API 호출
    signupForm.addEventListener("submit", (e) => {
        e.preventDefault();

        const userName = userNameInput.value;
        const nickName = nickNameInput.value;
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput.value;

        // 비밀번호 일치 확인
        if (password !== confirmPassword) {
            errorBox.style.display = "block";
            errorBox.innerText = "비밀번호가 일치하지 않습니다.";
            return;
        }

        const data = { userName, nickName, password };

        fetch("/api/bosses/signup", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        })
            .then(res => res.json())
            .then(result => {
                if (result.status >= 200 && result.status < 300) {
                    errorBox.style.display = "none";
                    successBox.style.display = "block";
                    successBox.innerText = result.message || "회원가입이 완료되었습니다!";
                    setTimeout(() => window.location.href = "/login", 1500);
                } else {
                    successBox.style.display = "none";
                    errorBox.style.display = "block";
                    errorBox.innerText = result.message || "회원가입에 실패하였습니다.";
                }
            })
            .catch(err => {
                successBox.style.display = "none";
                errorBox.style.display = "block";
                errorBox.innerText = "서버 오류가 발생했습니다.";
                console.error(err);
            });
    });
});
