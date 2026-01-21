const API_BASE = "http://localhost:4001/students";

const idInput = document.getElementById("studentIdInput");
const loadBtn = document.getElementById("loadBtn");
const card = document.getElementById("studentCard");

const avatar = document.getElementById("avatar");
const cardName = document.getElementById("cardName");
const cardEmail = document.getElementById("cardEmail");
const cardStatus = document.getElementById("cardStatus");
const cardNumber = document.getElementById("cardNumber");
const cardDept = document.getElementById("cardDept");
const cardLevel = document.getElementById("cardLevel");
const cardDob = document.getElementById("cardDob");
const cardId = document.getElementById("cardId");
const cardCreated = document.getElementById("cardCreated");

const confirmInput = document.getElementById("confirmInput");
const deleteBtn = document.getElementById("deleteBtn");

const statusBar = document.getElementById("statusBar");
const statusInner = document.getElementById("statusInner");

// modal elements
const deleteModal = document.getElementById("deleteModal");
const modalStudentName = document.getElementById("modalStudentName");
const modalCancelBtn = document.getElementById("modalCancelBtn");
const modalConfirmBtn = document.getElementById("modalConfirmBtn");

let currentStudentId = null;

/* HELPERS */

function initialsFromName(first, last) {
    const f = (first || "").trim()[0] || "";
    const l = (last || "").trim()[0] || "";
    const base = (f + l).toUpperCase();
    return base || "ST";
}

function formatDate(value) {
    if (!value) return "—";
    const d = new Date(value);
    if (isNaN(d)) return "—";
    return d.toLocaleDateString();
}

function isStudentActive(student) {
    const v = student.isActive ?? student.active ?? student.status;

    if (typeof v === "boolean") return v;
    if (typeof v === "number") return v !== 0;

    if (typeof v === "string") {
        const norm = v.trim().toLowerCase();
        return norm === "true" || norm === "1" || norm === "active" || norm === "yes";
    }

    return false;
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

/* MODAL CONTROL */

function openDeleteModal() {
    if (!currentStudentId) return;
    modalStudentName.textContent = cardName.textContent || "this student";
    deleteModal.classList.add("open");
}

function closeDeleteModal() {
    deleteModal.classList.remove("open");
}

/* LOAD STUDENT */

async function loadStudent() {
    const id = idInput.value.trim();
    if (!id) {
        showStatus("error", "Please enter a student ID.");
        return;
    }

    clearStatus();
    deleteBtn.disabled = true;
    confirmInput.value = "";
    card.style.display = "none";
    loadBtn.disabled = true;
    loadBtn.textContent = "Loading…";
    currentStudentId = null;

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

        const firstName = student.firstName || "";
        const lastName = student.lastName || "";
        const fullname = (firstName + " " + lastName).trim() || "Unnamed student";

        avatar.textContent = initialsFromName(firstName, lastName);
        cardName.textContent = fullname;
        cardEmail.textContent = student.email || "—";
        cardNumber.textContent = student.studentNumber || "—";
        cardDept.textContent = student.department || "—";
        cardLevel.textContent = student.level || "—";
        cardDob.textContent = formatDate(student.dateOfBirth);
        cardId.textContent = "ID: " + (student._id || id);
        cardCreated.textContent = "Created: " + formatDate(student.createdAt);

        const active = isStudentActive(student);
        cardStatus.textContent = active ? "Active" : "Inactive";
        cardStatus.classList.toggle("active", active);

        card.style.display = "block";
        showStatus("success", "Student loaded. Confirm deletion below.");
    } catch (err) {
        currentStudentId = null;
        card.style.display = "none";
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

/* CONFIRM INPUT (type DELETE) */

confirmInput.addEventListener("input", () => {
    const ok = confirmInput.value.trim().toUpperCase() === "DELETE" && !!currentStudentId;
    deleteBtn.disabled = !ok;
});

/* DELETE STUDENT (called only from modal confirm) */

async function deleteStudent() {
    if (!currentStudentId) {
        showStatus("error", "No student loaded.");
        return;
    }

    clearStatus();
    deleteBtn.disabled = true;
    modalConfirmBtn.disabled = true;
    modalConfirmBtn.textContent = "Deleting…";

    try {
        const response = await fetch(`${API_BASE}/${currentStudentId}`, {
            method: "DELETE"
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

        showStatus(
            "success",
            "Student deleted successfully.",
            data ? JSON.stringify(data, null, 2) : null
        );

        card.style.display = "none";
        currentStudentId = null;
        idInput.value = "";
        confirmInput.value = "";
    } catch (err) {
        showStatus("error", "Failed to delete student: " + err.message);
        console.error("Delete student error:", err);
    } finally {
        deleteBtn.disabled = true; // must type DELETE again for next one
        modalConfirmBtn.disabled = false;
        modalConfirmBtn.innerHTML = '<span>🗑️</span> Yes, delete';
    }
}

/* BUTTON HANDLERS */

// Click on main delete button → open modal
deleteBtn.addEventListener("click", () => {
    if (!currentStudentId) {
        showStatus("error", "No student loaded.");
        return;
    }
    openDeleteModal();
});

// Confirm in modal → perform delete
modalConfirmBtn.addEventListener("click", async () => {
    closeDeleteModal();
    await deleteStudent();
});

// Cancel in modal
modalCancelBtn.addEventListener("click", () => {
    closeDeleteModal();
});

// Click outside modal to close
deleteModal.addEventListener("click", (e) => {
    if (e.target === deleteModal) {
        closeDeleteModal();
    }
});
