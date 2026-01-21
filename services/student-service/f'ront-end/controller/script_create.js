const form = document.getElementById("student-form");
const statusBar = document.getElementById("statusBar");
const statusInner = document.getElementById("statusInner");
const submitBtn = document.getElementById("submitBtn");
const resetBtn = document.getElementById("resetBtn");

const statusToggle = document.getElementById("statusToggle");
const statusToggleText = document.getElementById("statusToggleText");
const isActiveInput = document.getElementById("isActive");

// Inputs
const dobInput = document.getElementById("dateOfBirth");

// Preview elements
const previewName = document.getElementById("preview-name");
const previewEmail = document.getElementById("preview-email");
const previewNumber = document.getElementById("preview-number");
const previewDept = document.getElementById("preview-dept");
const previewLevel = document.getElementById("preview-level");
const previewDob = document.getElementById("preview-dob");
const previewStatus = document.getElementById("preview-status");
const previewAvatar = document.getElementById("preview-avatar");

/* ---------------------------
   HELPERS
---------------------------- */

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

// Age check: must be >= 18 years
function isAtLeast18(dobValue) {
    if (!dobValue) return false;

    const dob = new Date(dobValue);
    if (isNaN(dob)) return false;

    const today = new Date();
    const cutoff = new Date(
        today.getFullYear() - 18,
        today.getMonth(),
        today.getDate()
    );

    return dob <= cutoff;
}

// Set max date in the picker to "today - 18 years"
function setDobMaxTo18YearsAgo() {
    const today = new Date();
    const cutoff = new Date(
        today.getFullYear() - 18,
        today.getMonth(),
        today.getDate()
    );
    const yyyy = cutoff.getFullYear();
    const mm = String(cutoff.getMonth() + 1).padStart(2, "0");
    const dd = String(cutoff.getDate()).padStart(2, "0");
    dobInput.max = `${yyyy}-${mm}-${dd}`;
}

/* ---------------------------
   PREVIEW + TOGGLE
---------------------------- */

function syncPreview() {
    const firstName = form.firstName.value.trim();
    const lastName = form.lastName.value.trim();
    const fullName = (firstName + " " + lastName).trim();

    previewName.textContent = fullName || "New Student";
    previewEmail.textContent = form.email.value.trim() || "email@domain.tld";
    previewNumber.textContent = "# " + (form.studentNumber.value.trim() || "—");

    const dept = form.department.value || "—";
    const level = form.level.value || "—";
    previewDept.textContent = "Dept " + dept;
    previewLevel.textContent = "Level " + level;
    previewDob.textContent = formatDateForPreview(form.dateOfBirth.value);
    previewAvatar.textContent = initialsFromName(firstName, lastName);

    const active = isActiveInput.checked;
    previewStatus.textContent = active ? "Active" : "Inactive";
    previewStatus.classList.toggle("inactive", !active);
}

// Keep visual toggle in sync with checkbox
function syncToggleFromCheckbox() {
    const active = isActiveInput.checked;
    statusToggle.classList.toggle("active", active);
    statusToggleText.textContent = active ? "Active" : "Inactive";
    syncPreview();
}

/* ---------------------------
   EVENT BINDINGS
---------------------------- */

// change event = fires whenever checkbox changes (label click, keyboard, etc.)
isActiveInput.addEventListener("change", syncToggleFromCheckbox);

// Live preview on input
["studentNumber", "firstName", "lastName", "email", "dateOfBirth", "department", "level"].forEach(id => {
    const el = document.getElementById(id);
    el.addEventListener("input", syncPreview);
    el.addEventListener("change", syncPreview);
});

// Live age validation on DOB change (UX)
dobInput.addEventListener("change", () => {
    if (!dobInput.value) return;

    if (!isAtLeast18(dobInput.value)) {
        dobInput.style.borderColor = "#b91c1c"; // red
        statusInner.className = "status-inner error";
        statusInner.textContent = "The student must be at least 18 years old.";
        statusBar.style.display = "block";
    } else {
        dobInput.style.borderColor = "";
        // only hide bar if it was an age error before
        if (statusInner.textContent.includes("18 years")) {
            statusBar.style.display = "none";
            statusInner.textContent = "";
        }
    }
});

resetBtn.addEventListener("click", () => {
    form.reset();
    // default state = active
    isActiveInput.checked = true;
    syncToggleFromCheckbox();

    dobInput.style.borderColor = "";
    statusBar.style.display = "none";
    statusInner.textContent = "";
});

/* ---------------------------
   SUBMIT LOGIC
---------------------------- */

async function handleSubmit(event) {
    event.preventDefault();

    // Age validation: student must be 18+
    if (!isAtLeast18(form.dateOfBirth.value)) {
        dobInput.style.borderColor = "#b91c1c";
        statusInner.className = "status-inner error";
        statusInner.textContent = "The student must be at least 18 years old.";
        statusBar.style.display = "block";
        return;
    }

    // Reset status styles
    dobInput.style.borderColor = "";
    statusBar.style.display = "none";
    statusInner.textContent = "";
    statusInner.className = "status-inner";

    submitBtn.disabled = true;
    submitBtn.innerHTML = "Saving…";

    const payload = {
        studentNumber: form.studentNumber.value.trim(),
        firstName: form.firstName.value.trim(),
        lastName: form.lastName.value.trim(),
        email: form.email.value.trim(),
        dateOfBirth: form.dateOfBirth.value,
        department: form.department.value,
        level: form.level.value,
        isActive: isActiveInput.checked
    };

    try {
        const response = await fetch("http://localhost:4001/students", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });

        const text = await response.text();
        let data = null;
        try {
            data = text ? JSON.parse(text) : null;
        } catch {
            // non-JSON response, ignore
        }

        if (!response.ok) {
            throw new Error(data?.message || text || ("HTTP " + response.status));
        }

        statusInner.classList.add("success");
        statusInner.innerHTML = "✅ Student created successfully.";
        if (data) {
            statusInner.innerHTML += `<code>${JSON.stringify(data, null, 2)}</code>`;
            const id = data._id || (data.data && data.data._id);
            if (id) {
                document.getElementById("preview-id").textContent = "ID: " + id;
            } else {
                document.getElementById("preview-id").textContent = "Created just now";
            }
        } else {
            statusInner.innerHTML += `<code>${JSON.stringify(payload, null, 2)}</code>`;
            document.getElementById("preview-id").textContent = "Created just now";
        }

        statusBar.style.display = "block";
    } catch (err) {
        statusInner.classList.add("error");
        statusInner.textContent = "Failed to create student: " + err.message;
        statusBar.style.display = "block";
        console.error("Create student error:", err);
    } finally {
        submitBtn.disabled = false;
        submitBtn.innerHTML = '<span>➕</span> Create student';
    }
}

form.addEventListener("submit", handleSubmit);

/* ---------------------------
   INITIAL STATE
---------------------------- */

setDobMaxTo18YearsAgo();   // limit date picker
syncToggleFromCheckbox();  // sync toggle + preview
