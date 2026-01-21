const API_BASE = "http://localhost:4001/students";

const form = document.getElementById("student-form");
const statusBar = document.getElementById("statusBar");
const statusInner = document.getElementById("statusInner");
const submitBtn = document.getElementById("submitBtn");
const resetBtn = document.getElementById("resetBtn");
const loadBtn = document.getElementById("loadBtn");
const idInput = document.getElementById("studentIdInput");

const statusToggle = document.getElementById("statusToggle");
const statusToggleText = document.getElementById("statusToggleText");
const isActiveInput = document.getElementById("isActive");

// form fields
const studentNumberInput = document.getElementById("studentNumber");
const emailInput = document.getElementById("email");
const firstNameInput = document.getElementById("firstName");
const lastNameInput = document.getElementById("lastName");
const dateOfBirthInput = document.getElementById("dateOfBirth");
const departmentSelect = document.getElementById("department");
const levelSelect = document.getElementById("level");

// Preview elements
const previewName = document.getElementById("preview-name");
const previewEmail = document.getElementById("preview-email");
const previewNumber = document.getElementById("preview-number");
const previewDept = document.getElementById("preview-dept");
const previewLevel = document.getElementById("preview-level");
const previewDob = document.getElementById("preview-dob");
const previewStatus = document.getElementById("preview-status");
const previewAvatar = document.getElementById("preview-avatar");
const previewId = document.getElementById("preview-id");

let currentStudentId = null;

/* AGE / DOB HELPERS – 18+ RULE */

function setDobMax18() {
    if (!dateOfBirthInput) return;
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const max = new Date(today.getFullYear() - 18, today.getMonth(), today.getDate());
    dateOfBirthInput.max = max.toISOString().slice(0, 10);
}

function getAge(dateStr) {
    const dob = new Date(dateStr);
    if (isNaN(dob)) return null;
    const today = new Date();
    let age = today.getFullYear() - dob.getFullYear();
    const m = today.getMonth() - dob.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < dob.getDate())) {
        age--;
    }
    return age;
}

setDobMax18();

/* UI HELPERS */

function initialsFromName(first, last) {
    const f = (first || "").trim()[0] || "";
    const l = (last || "").trim()[0] || "";
    const base = (f + l).toUpperCase();
    return base || "ST";
}

function formatDateForPreview(value) {
    if (!value) return "Birth date: —";
    const d = new Date(value);
    if (isNaN(d)) return "Birth date: —";
    return "Birth date: " + d.toLocaleDateString();
}

function setFormEnabled(enabled) {
    [
        studentNumberInput,
        emailInput,
        firstNameInput,
        lastNameInput,
        dateOfBirthInput,
        departmentSelect,
        levelSelect,
        isActiveInput
    ].forEach(el => {
        el.disabled = !enabled;
    });
    submitBtn.disabled = !enabled;
}

function showStatus(type, message, extraCode) {
    statusInner.className = "status-inner " + type;
    statusInner.textContent = message;
    if (extraCode) {
        statusInner.innerHTML += `<code>${extraCode}</code>`;
    }
    statusBar.style.display = "block";
}

/* PREVIEW SYNC */

function syncPreview() {
    const firstName = firstNameInput.value.trim();
    const lastName = lastNameInput.value.trim();
    const fullName = (firstName + " " + lastName).trim();

    previewName.textContent = fullName || "No student loaded";
    previewEmail.textContent = emailInput.value.trim() || "—";
    previewNumber.textContent = "# " + (studentNumberInput.value.trim() || "—");

    const dept = departmentSelect.value || "—";
    const level = levelSelect.value || "—";
    previewDept.textContent = "Dept " + dept;
    previewLevel.textContent = "Level " + level;
    previewDob.textContent = formatDateForPreview(dateOfBirthInput.value);
    previewAvatar.textContent = initialsFromName(firstName, lastName);

    const active = isActiveInput.checked;
    previewStatus.textContent = active ? "Active" : "Inactive";
    previewStatus.classList.toggle("inactive", !active);

    previewId.textContent = "ID: " + (currentStudentId || "—");
}

/* LOAD STUDENT */

async function loadStudent() {
    const id = idInput.value.trim();
    if (!id) {
        showStatus("error", "Please enter a student ID to load.");
        return;
    }

    statusBar.style.display = "none";
    statusInner.textContent = "";
    loadBtn.disabled = true;
    loadBtn.textContent = "Loading…";
    setFormEnabled(false);

    try {
        const response = await fetch(`${API_BASE}/${id}`, { cache: "no-store" });
        const text = await response.text();
        let payload = null;
        try {
            payload = text ? JSON.parse(text) : null;
        } catch {
            throw new Error("Unexpected response format.");
        }

        if (!response.ok) {
            throw new Error(payload?.message || text || ("HTTP " + response.status));
        }

        const student = payload && payload.data && !Array.isArray(payload.data)
            ? payload.data
            : payload;

        currentStudentId = student._id || id;

        studentNumberInput.value = student.studentNumber || "";
        firstNameInput.value = student.firstName || "";
        lastNameInput.value = student.lastName || "";
        emailInput.value = student.email || "";

        // dateOfBirth
        if (student.dateOfBirth) {
            const d = new Date(student.dateOfBirth);
            if (!isNaN(d)) {
                dateOfBirthInput.value = d.toISOString().slice(0, 10);
            } else {
                dateOfBirthInput.value = "";
            }
        } else {
            dateOfBirthInput.value = "";
        }

        departmentSelect.value = student.department || "";
        levelSelect.value = student.level || "";

        // status
        const activeFromBackend = student.isActive ?? student.active ?? student.status;
        const activeBool = typeof activeFromBackend === "boolean"
            ? activeFromBackend
            : !!activeFromBackend;
        isActiveInput.checked = activeBool;

        statusToggle.classList.toggle("active", isActiveInput.checked);
        statusToggleText.textContent = isActiveInput.checked ? "Active" : "Inactive";

        setFormEnabled(true);
        syncPreview();
        showStatus("success", "Student loaded successfully.");
    } catch (err) {
        currentStudentId = null;
        setFormEnabled(false);
        // reset preview
        previewName.textContent = "No student loaded";
        previewEmail.textContent = "—";
        previewNumber.textContent = "# —";
        previewDept.textContent = "Dept —";
        previewLevel.textContent = "Level —";
        previewDob.textContent = "Birth date: —";
        previewAvatar.textContent = "ST";
        previewStatus.textContent = "Unknown";
        previewStatus.classList.add("inactive");
        previewId.textContent = "ID: —";

        showStatus("error", "Failed to load student: " + err.message);
        console.error("Load student error:", err);
    } finally {
        loadBtn.disabled = false;
        loadBtn.innerHTML = '<span>🔍</span> Load student';
    }
}

loadBtn.addEventListener("click", loadStudent);

idInput.addEventListener("keypress", (e) => {
    if (e.key === "Enter") {
        e.preventDefault();
        loadStudent();
    }
});

/* LIVE PREVIEW EVENTS */

[
    studentNumberInput,
    emailInput,
    firstNameInput,
    lastNameInput,
    dateOfBirthInput,
    departmentSelect,
    levelSelect
].forEach(el => {
    el.addEventListener("input", syncPreview);
    el.addEventListener("change", syncPreview);
});

/* TOGGLE – SINGLE SOURCE OF TRUTH = CHECKBOX CHANGE */

isActiveInput.addEventListener("change", () => {
    statusToggle.classList.toggle("active", isActiveInput.checked);
    statusToggleText.textContent = isActiveInput.checked ? "Active" : "Inactive";
    syncPreview();
});

// Optional: make label click work only through browser default (no manual toggling)
// but avoid running when disabled
statusToggle.addEventListener("click", (e) => {
    if (isActiveInput.disabled) {
        e.preventDefault();
    }
});

/* RESET FORM */

resetBtn.addEventListener("click", () => {
    form.reset();
    setFormEnabled(false);
    currentStudentId = null;
    isActiveInput.checked = false;
    statusToggle.classList.remove("active");
    statusToggleText.textContent = "Inactive";

    previewName.textContent = "No student loaded";
    previewEmail.textContent = "—";
    previewNumber.textContent = "# —";
    previewDept.textContent = "Dept —";
    previewLevel.textContent = "Level —";
    previewDob.textContent = "Birth date: —";
    previewAvatar.textContent = "ST";
    previewStatus.textContent = "Unknown";
    previewStatus.classList.add("inactive");
    previewId.textContent = "ID: —";

    statusBar.style.display = "none";
});

/* SUBMIT – UPDATE STUDENT WITH 18+ RULE */

async function handleSubmit(e) {
    e.preventDefault();

    if (!currentStudentId) {
        showStatus("error", "No student loaded. Load a student before updating.");
        return;
    }

    // Validate 18+ years
    const dobVal = dateOfBirthInput.value;
    const age = getAge(dobVal);
    if (!dobVal || age === null || age < 18) {
        showStatus("error", "Student must be at least 18 years old.");
        dateOfBirthInput.focus();
        return;
    }

    statusBar.style.display = "none";
    statusInner.textContent = "";
    submitBtn.disabled = true;
    submitBtn.innerHTML = "Saving…";

    const payload = {
        studentNumber: studentNumberInput.value.trim(),
        firstName: firstNameInput.value.trim(),
        lastName: lastNameInput.value.trim(),
        email: emailInput.value.trim(),
        dateOfBirth: dateOfBirthInput.value,
        department: departmentSelect.value,
        level: levelSelect.value,
        isActive: isActiveInput.checked
    };

    try {
        const response = await fetch(`${API_BASE}/${currentStudentId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const text = await response.text();
        let data = null;
        try {
            data = text ? JSON.parse(text) : null;
        } catch {
            // ignore non-JSON
        }

        if (!response.ok) {
            throw new Error(data?.message || text || ("HTTP " + response.status));
        }

        const extra = data ? JSON.stringify(data, null, 2) : JSON.stringify(payload, null, 2);
        showStatus("success", "Student updated successfully.", extra);
    } catch (err) {
        showStatus("error", "Failed to update student: " + err.message);
        console.error("Update student error:", err);
    } finally {
        submitBtn.disabled = false;
        submitBtn.innerHTML = '<span>💾</span> Save changes';
    }
}

form.addEventListener("submit", handleSubmit);

// Initial preview
syncPreview();
