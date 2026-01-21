const API_URL = "http://localhost:4001/students";

const loader = document.getElementById("loader");
const errorBox = document.getElementById("errorBox");
const listContainer = document.getElementById("listContainer");
const listBody = document.getElementById("listBody");
const emptyMessage = document.getElementById("emptyMessage");
const subtitle = document.getElementById("subtitle");
const pagination = document.getElementById("pagination");
const prevBtn = document.getElementById("prevBtn");
const nextBtn = document.getElementById("nextBtn");
const shownCount = document.getElementById("shownCount");
const totalCount = document.getElementById("totalCount");
const filterNote = document.getElementById("filterNote");

const statTotal = document.getElementById("statTotal");
const statActive = document.getElementById("statActive");
const statFiltered = document.getElementById("statFiltered");
const statPage = document.getElementById("statPage");

const searchInput = document.getElementById("searchInput");
const deptFilter = document.getElementById("deptFilter");
const levelFilter = document.getElementById("levelFilter");
const activeToggle = document.getElementById("activeToggle");
const activeOnly = document.getElementById("activeOnly");
const refreshBtn = document.getElementById("refreshBtn");

let allStudents = [];
let filteredStudents = [];
let pageFromServer = 1;
let totalFromServer = 0;

const PAGE_SIZE = 10;
let currentPageIndex = 0;

/* HELPERS */

function setLoading(isLoading) {
    loader.style.display = isLoading ? "block" : "none";
}

function showError(message) {
    errorBox.textContent = message;
    errorBox.style.display = "block";
}

function clearError() {
    errorBox.textContent = "";
    errorBox.style.display = "none";
}

function initialsFromStudent(student) {
    const f = (student.firstName || "").trim()[0] || "";
    const l = (student.lastName || "").trim()[0] || "";
    const base = (f + l).toUpperCase();
    return base || "ST";
}

// robust active detection
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

/* FILTER + RENDER */

function applyFilters() {
    const q = searchInput.value.trim().toLowerCase();
    const dept = deptFilter.value;
    const level = levelFilter.value;
    const activeOnlyChecked = activeOnly.checked;

    filteredStudents = allStudents.filter(s => {
        const name = ((s.firstName || "") + " " + (s.lastName || "")).toLowerCase();
        const email = (s.email || "").toLowerCase();
        const active = isStudentActive(s);

        if (q && !name.includes(q) && !email.includes(q)) return false;
        if (dept && s.department !== dept) return false;
        if (level && s.level !== level) return false;
        if (activeOnlyChecked && !active) return false;

        return true;
    });

    currentPageIndex = 0;
    renderList();
    updateStats();
}

function renderList() {
    listBody.innerHTML = "";

    if (filteredStudents.length === 0) {
        listContainer.style.display = "none";
        emptyMessage.style.display = "block";
        pagination.style.display = "none";
        subtitle.textContent = "No students match your filters.";
        shownCount.textContent = "0";
        totalCount.textContent = (totalFromServer || allStudents.length || 0).toString();
        statFiltered.textContent = "0";
        return;
    }

    const start = currentPageIndex * PAGE_SIZE;
    const end = start + PAGE_SIZE;
    const pageItems = filteredStudents.slice(start, end);

    pageItems.forEach(student => {
        const row = document.createElement("div");
        row.className = "list-row";

        const fullName = `${student.firstName || ""} ${student.lastName || ""}`.trim() || "Unnamed student";
        const email = student.email || "—";
        const number = student.studentNumber || "—";
        const dept = student.department || "—";
        const level = student.level || "—";
        const active = isStudentActive(student);

        let dobText = "—";
        if (student.dateOfBirth) {
            const d = new Date(student.dateOfBirth);
            if (!isNaN(d)) dobText = d.toLocaleDateString();
        }

        row.innerHTML = `
            <div>
                <div class="list-row-main">${number}</div>
                <div class="sub-text">${student._id || ""}</div>
            </div>
            <div>
                <div class="list-row-main">${fullName}</div>
                <div class="sub-text">DOB: ${dobText}</div>
            </div>
            <div>
                <div>${email}</div>
                <div class="sub-text">Initials: ${initialsFromStudent(student)}</div>
            </div>
            <div>
                <div>${dept}</div>
                <div class="sub-text">${level}</div>
            </div>
            <div>
                <span class="badge-status ${active ? "active" : "inactive"}">
                    <span class="badge-status-dot"></span>
                    ${active ? "Active" : "Inactive"}
                </span>
            </div>
        `;

        listBody.appendChild(row);
    });

    listContainer.style.display = "block";
    emptyMessage.style.display = "none";

    const shown = pageItems.length;
    shownCount.textContent = shown.toString();
    totalCount.textContent = (totalFromServer || allStudents.length).toString();

    if (filteredStudents.length !== allStudents.length) {
        filterNote.textContent = ` (filtered from ${allStudents.length})`;
    } else {
        filterNote.textContent = "";
    }

    pagination.style.display = filteredStudents.length > PAGE_SIZE ? "flex" : "none";

    prevBtn.disabled = currentPageIndex === 0;
    nextBtn.disabled = end >= filteredStudents.length;

    subtitle.textContent = `Showing ${shown} student${shown !== 1 ? "s" : ""} (page ${currentPageIndex + 1})`;
}

function updateStats() {
    const total = totalFromServer || allStudents.length;
    const activeCount = allStudents.filter(isStudentActive).length;
    const filteredCount = filteredStudents.length;

    statTotal.textContent = total ? total.toString() : "0";
    statActive.textContent = activeCount.toString();
    statFiltered.textContent = filteredCount.toString();
    statPage.textContent = pageFromServer.toString();
}

/* FETCH */

async function loadStudents() {
    setLoading(true);
    clearError();
    subtitle.textContent = "Loading students…";
    listContainer.style.display = "none";
    emptyMessage.style.display = "none";
    pagination.style.display = "none";

    try {
        const response = await fetch(API_URL, { cache: "no-store" });
        const text = await response.text();
        let payload;
        try {
            payload = text ? JSON.parse(text) : null;
        } catch {
            throw new Error("Unexpected response format from /students");
        }

        if (!response.ok) {
            throw new Error(payload?.message || text || ("HTTP " + response.status));
        }

        const data = Array.isArray(payload) ? payload : (payload.data || []);
        allStudents = data;
        pageFromServer = payload.page || 1;
        totalFromServer = payload.total || data.length || 0;

        applyFilters();
        subtitle.textContent = `Loaded ${data.length} student${data.length !== 1 ? "s" : ""} from server.`;
    } catch (err) {
        allStudents = [];
        filteredStudents = [];
        setLoading(false);
        showError("Failed to load students: " + err.message);
        subtitle.textContent = "Unable to load students.";
        console.error("Error fetching students:", err);
    } finally {
        setLoading(false);
    }
}

/* EVENTS */

searchInput.addEventListener("input", applyFilters);
deptFilter.addEventListener("change", applyFilters);
levelFilter.addEventListener("change", applyFilters);

// ✅ The ONLY source of truth for toggle state
activeOnly.addEventListener("change", () => {
    activeToggle.classList.toggle("active", activeOnly.checked);
    applyFilters();
});

// Refresh
refreshBtn.addEventListener("click", loadStudents);

// Pagination
prevBtn.addEventListener("click", () => {
    if (currentPageIndex > 0) {
        currentPageIndex--;
        renderList();
    }
});

nextBtn.addEventListener("click", () => {
    if ((currentPageIndex + 1) * PAGE_SIZE < filteredStudents.length) {
        currentPageIndex++;
        renderList();
    }
});

/* INITIAL LOAD */

loadStudents();
