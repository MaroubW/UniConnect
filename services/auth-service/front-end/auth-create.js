// Matches your backend: POST /api/auth/register -> { token: "..." }
const AUTH_API_URL = "http://localhost:8082/api/auth/register";

const form = document.getElementById("user-form");
const statusBar = document.getElementById("statusBar");
const statusInner = document.getElementById("statusInner");
const submitBtn = document.getElementById("submitBtn");
const resetBtn = document.getElementById("resetBtn");

const statusToggle = document.getElementById("statusToggle");
const statusToggleText = document.getElementById("statusToggleText");
const isActiveInput = document.getElementById("isActive");

// Preview elements
const previewName = document.getElementById("preview-name");
const previewEmail = document.getElementById("preview-email");
const previewRole = document.getElementById("preview-role");
const previewUsername = document.getElementById("preview-username");
const previewStatus = document.getElementById("preview-status");
const previewAvatar = document.getElementById("preview-avatar");
const previewCreated = document.getElementById("preview-created");
const previewId = document.getElementById("preview-id");

/* Helpers */

function initialsFromName(first, last, usernameOrEmail) {
    const f = (first || "").trim()[0] || "";
    const l = (last || "").trim()[0] || "";
    const base = (f + l) || (usernameOrEmail || "").trim()[0] || "";
    const up = base.toUpperCase();
    return up || "AU";
}

// decode JWT payload to pick role/username for preview (optional)
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

function formatRoleLabel(roleValue) {
    if (!roleValue) return "";
    const lower = roleValue.toLowerCase();
    return lower.charAt(0).toUpperCase() + lower.slice(1); // STUDENT -> Student
}

function syncPreview() {
    const email = form.email.value.trim();
    const username = form.username.value.trim();
    const firstName = form.firstName.value.trim();
    const lastName = form.lastName.value.trim();
    const roleValue = form.role.value;

    const fullName = (firstName + " " + lastName).trim();
    previewName.textContent = fullName || "New User";
    previewEmail.textContent = email || "user@example.com";

    if (roleValue) {
        previewRole.textContent = "Role — " + formatRoleLabel(roleValue);
    } else {
        previewRole.textContent = "Role —";
    }

    if (username) {
        previewUsername.textContent = "Username — " + username;
    } else if (email && email.includes("@")) {
        previewUsername.textContent =
            "Username — " + email.substring(0, email.indexOf("@")) + " (auto)";
    } else {
        previewUsername.textContent = "Username —";
    }

    const avatarText = initialsFromName(firstName, lastName, username || email);
    previewAvatar.textContent = avatarText;

    const active = isActiveInput.checked;
    previewStatus.textContent = active ? "Active" : "Disabled";
    previewStatus.classList.toggle("inactive", !active);
}

/* Toggle sync */

function syncToggleFromCheckbox() {
    const active = isActiveInput.checked;
    statusToggle.classList.toggle("active", active);
    statusToggle.classList.toggle("inactive", !active);
    statusToggleText.textContent = active ? "Active" : "Disabled";
    syncPreview();
}

// checkbox change (keyboard, direct click if visible)
isActiveInput.addEventListener("change", syncToggleFromCheckbox);

// click anywhere on the toggle pill/label
statusToggle.addEventListener("click", (e) => {
    // If the actual checkbox was clicked, let its own change handler deal with it
    if (e.target === isActiveInput) {
        return;
    }

    // Prevent label default toggle (which would cause double toggle)
    e.preventDefault();

    // Manually flip the checkbox and sync UI
    isActiveInput.checked = !isActiveInput.checked;
    syncToggleFromCheckbox();
});

/* Live preview */

["email", "username", "firstName", "lastName", "role"].forEach(id => {
    const el = document.getElementById(id);
    el.addEventListener("input", syncPreview);
    el.addEventListener("change", syncPreview);
});

/* Status helpers */

function showStatus(type, message, extra) {
    statusInner.className = "status-inner " + type;
    statusInner.textContent = message;
    if (extra) {
        statusInner.innerHTML += `<pre><code>${extra}</code></pre>`;
    }
    statusBar.style.display = "block";
}

function clearStatus() {
    statusBar.style.display = "none";
    statusInner.textContent = "";
    statusInner.className = "status-inner";
}

/* Validation */

function validateForm() {
    const email = form.email.value.trim();
    const password = form.password.value;
    const confirmPassword = form.confirmPassword.value;
    const role = form.role.value;

    if (!email) {
        throw new Error("Email is required.");
    }

    if (!password || password.length < 8) {
        throw new Error("Password must be at least 8 characters long.");
    }

    if (password !== confirmPassword) {
        throw new Error("Passwords do not match.");
    }

    if (!role) {
        throw new Error("Please select a role.");
    }
}

/* Submit */

async function handleSubmit(event) {
    event.preventDefault();

    clearStatus();

    try {
        validateForm();
    } catch (validationError) {
        showStatus("error", validationError.message);
        return;
    }

    let email = form.email.value.trim();
    let username = form.username.value.trim();
    const firstName = form.firstName.value.trim();
    const lastName = form.lastName.value.trim();
    const password = form.password.value;
    const role = form.role.value;
    const enabled = isActiveInput.checked;

    // auto-generate username from email if empty
    if (!username && email && email.includes("@")) {
        username = email.substring(0, email.indexOf("@"));
    }

    if (!username) {
        showStatus("error", "Username is required or must be derivable from email.");
        return;
    }

    submitBtn.disabled = true;
    submitBtn.innerHTML = "Saving…";

    const payload = {
        username: username,
        email: email,
        password: password,
        firstName: firstName,
        lastName: lastName,
        role: role || null,     // STUDENT / MANAGER / ADMIN
        enabled: enabled        // maps to User.enabled in backend
    };

    try {
        const response = await fetch(AUTH_API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const text = await response.text();
        let data = null;
        try {
            data = text ? JSON.parse(text) : null;
        } catch {
            // non-JSON response; ignore
        }

        if (!response.ok) {
            throw new Error(data?.message || text || ("HTTP " + response.status));
        }

        // If backend returns { token: "..." }
        const token = data?.token;
        const claims = token ? parseJwt(token) : null;

        const now = new Date();
        previewCreated.textContent = "Created at " + now.toLocaleString();
        previewId.textContent = data?.id ? ("ID: " + data.id) : "New account (ID from backend)";

        if (claims?.role) {
            previewRole.textContent = "Role — " + formatRoleLabel(claims.role);
        }

        showStatus(
            "success",
            "✅ User registered successfully. Redirecting to login…",
            data ? JSON.stringify(data, null, 2) : null
        );

        // 👉 Store token (optional) and redirect to login page
        if (token) {
            localStorage.setItem("authToken", token);
        }

        // Change "login.html" to whatever your login page actually is
        window.location.href = "./auth-login.html";

    } catch (err) {
        showStatus("error", "Failed to create user: " + err.message);
        console.error("Create user error:", err);
    } finally {
        submitBtn.disabled = false;
        submitBtn.innerHTML = '<span>➕</span> Create user';
    }
}

form.addEventListener("submit", handleSubmit);

/* Reset */

resetBtn.addEventListener("click", () => {
    form.reset();
    isActiveInput.checked = true;
    syncToggleFromCheckbox();
    previewId.textContent = "New account";
    previewCreated.textContent = "Will be created on first save";
    clearStatus();
});

/* Initial state */

syncPreview();
syncToggleFromCheckbox();

