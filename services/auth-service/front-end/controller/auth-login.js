// Matches your backend: POST /api/auth/login  ->  { token: "..." }
const AUTH_LOGIN_URL = "http://localhost:8082/api/auth/login";

const form = document.getElementById("login-form");
const statusBar = document.getElementById("statusBar");
const statusInner = document.getElementById("statusInner");
const submitBtn = document.getElementById("submitBtn");

const usernameInput = document.getElementById("username");
const rememberMeInput = document.getElementById("rememberMe");

// Preview elements
const previewName = document.getElementById("preview-name");
const previewEmail = document.getElementById("preview-email");
const previewRole = document.getElementById("preview-role");
const previewRemember = document.getElementById("preview-remember");
const previewStatus = document.getElementById("preview-status");
const previewAvatar = document.getElementById("preview-avatar");
const previewIssued = document.getElementById("preview-issued");
const previewLastLogin = document.getElementById("preview-last-login");

const fakeForgotLink = document.getElementById("fakeForgotLink");

/* Helpers */

function initialsFromUsername(username) {
    if (!username) return "AU";
    const ch = username.trim()[0] || "";
    const up = ch.toUpperCase();
    return up || "AU";
}

// decode JWT payload to get the role if you put it there (nice for preview)
function parseJwt(token) {
    try {
        const parts = token.split(".");
        if (parts.length < 2) return null;
        const payload = atob(parts[1].replace(/-/g, "+").replace(/_/g, "/"));
        return JSON.parse(payload);
    } catch {
        return null;
    }
}

function maskToken(token) {
    if (!token) return "Token: —";
    if (token.length <= 10) return "Token: " + token;
    const head = token.slice(0, 4);
    const tail = token.slice(-4);
    return `Token: ${head}••••••${tail}`;
}

function showStatus(type, message, extra) {
    statusInner.className = "status-inner " + type;
    statusInner.textContent = message;
    if (extra) {
        statusInner.innerHTML += `<code>${extra}</code>`;
    }
    statusBar.style.display = "block";
}

function clearStatus() {
    statusBar.style.display = "none";
    statusInner.textContent = "";
    statusInner.className = "status-inner";
}

function syncPreview() {
    const username = usernameInput.value.trim();
    const remember = rememberMeInput.checked;

    previewEmail.textContent = username || "—";
    previewName.textContent = username ? `Ready to sign in as ${username}` : "Not signed in";
    previewAvatar.textContent = initialsFromUsername(username);
    previewRemember.textContent = "Remember me: " + (remember ? "Yes" : "No");
}

/* Live preview */

usernameInput.addEventListener("input", syncPreview);
usernameInput.addEventListener("change", syncPreview);
rememberMeInput.addEventListener("change", syncPreview);

/* Fake forgot password */

fakeForgotLink.addEventListener("click", (e) => {
    e.preventDefault();
    showStatus(
        "error",
        "Forgot password flow is not implemented in this demo.",
        "Implement a dedicated /reset-password endpoint or page in your backend."
    );
});

/* Validation */

function validateForm() {
    const username = usernameInput.value.trim();
    const password = form.password.value;

    if (!username) {
        throw new Error("Username is required.");
    }
    if (!password) {
        throw new Error("Password is required.");
    }
}

/* Submit */

async function handleSubmit(e) {
    e.preventDefault();
    clearStatus();

    try {
        validateForm();
    } catch (validationError) {
        showStatus("error", validationError.message);
        return;
    }

    const username = usernameInput.value.trim();
    const password = form.password.value;
    const rememberMe = rememberMeInput.checked;

    const payload = { username, password };

    submitBtn.disabled = true;
    submitBtn.innerHTML = "Signing in…";

    try {
        const response = await fetch(AUTH_LOGIN_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const text = await response.text();
        let data = null;
        try {
            data = text ? JSON.parse(text) : null;
        } catch {
            // non-JSON
        }

        if (!response.ok) {
            throw new Error(data?.message || text || ("HTTP " + response.status));
        }

        const token = data?.token;
        const claims = token ? parseJwt(token) : null;
        const role = claims?.role || claims?.roles || "—";

        // update preview
        previewStatus.textContent = "Authenticated";
        previewStatus.classList.remove("inactive");
        previewRole.textContent = "Role " + (role || "—");

        const now = new Date();
        previewLastLogin.textContent = "Last login: " + now.toLocaleString();
        previewIssued.textContent = maskToken(token);

        if (rememberMe && token) {
            try {
                localStorage.setItem("authToken", token);
            } catch {
                // ignore storage errors
            }
        }

        showStatus(
            "success",
            "✅ Logged in successfully.",
            JSON.stringify(data, null, 2)
        );
        window.location.href = "./../../student-service/f'ront-end/dashbord.html";
    } catch (err) {
        previewStatus.textContent = "Offline";
        previewStatus.classList.add("inactive");
        previewRole.textContent = "Role —";
        previewIssued.textContent = "Token: —";
        showStatus("error", "Login failed: " + err.message);
        console.error("Login error:", err);
    } finally {
        submitBtn.disabled = false;
        submitBtn.innerHTML = '<span>🔐</span> Sign in';
    }
}

form.addEventListener("submit", handleSubmit);

/* Initial state */
document.getElementById("switchToSignUp").addEventListener("click", () => {
    window.location.href = "auth-create.html"; // your registration page
});

syncPreview();
previewRole.textContent = "Role —";
previewIssued.textContent = "Token: —";
previewLastLogin.textContent = "Last login: —";
previewStatus.textContent = "Offline";
previewStatus.classList.add("inactive");
