// ================== API CONFIG ==================
const API_BASE = "http://localhost:9191";
const COURSES_ENDPOINT = `${API_BASE}/api/courses`;
const COURSES_LIST_ENDPOINT = `${COURSES_ENDPOINT}/list`; // used for "All courses" table

// Simple in-memory list of schedules for the currently edited course
let scheduleEntries = [];
let currentCourseId = null;       // set when editing an existing course
let loadedCourses = [];           // cache of courses from server

// ================== SCHEDULE TABLE RENDER ==================
function renderScheduleTable() {
  const tbody = document.getElementById('scheduleTableBody') ||
                document.querySelector('.table-scroll .billing-table tbody');
  if (!tbody) return;

  // Clear existing rows
  tbody.innerHTML = '';

  scheduleEntries.forEach((s, index) => {
    const tr = document.createElement('tr');

    tr.innerHTML = `
      <td>${s.dayOfWeek || ''}</td>
      <td>${s.startTime || ''}</td>
      <td>${s.endTime || ''}</td>
      <td>${s.room || ''}</td>
      <td>${s.building || ''}</td>
      <td><button class="btn-table" type="button" data-index="${index}">Remove</button></td>
    `;

    tbody.appendChild(tr);
  });

  // Add click handler for remove buttons
  tbody.querySelectorAll('button[data-index]').forEach(btn => {
    btn.addEventListener('click', (e) => {
      const idx = parseInt(e.target.getAttribute('data-index'), 10);
      scheduleEntries.splice(idx, 1);
      renderScheduleTable();
    });
  });
}

// ================== COURSE LIST TABLE RENDER ==================
function renderCoursesTable(courses) {
  const tbody = document.getElementById('coursesTableBody');
  const totalEl = document.getElementById('courseTotalCount');

  if (!tbody) return;

  tbody.innerHTML = '';
  if (totalEl) totalEl.textContent = courses ? courses.length : 0;

  if (!courses || courses.length === 0) {
    const tr = document.createElement('tr');
    tr.innerHTML = `<td colspan="7" style="text-align:center;">No courses found</td>`;
    tbody.appendChild(tr);
    return;
  }

  courses.forEach(c => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${c.code || ''}</td>
      <td>${c.name || ''}</td>
      <td>${c.credits != null ? c.credits : ''}</td>
      <td>${c.semester || ''}</td>
      <td>${c.professorName || ''}</td>
      <td>${c.capacity != null ? c.capacity : ''}</td>
      <td>${c.enrolledStudents != null ? c.enrolledStudents : ''}</td>
    `;
    tbody.appendChild(tr);
  });
}

// ================== COURSE SELECT DROPDOWN ==================
function populateCourseSelect(courses) {
  const select = document.getElementById('courseSelect');
  if (!select) return;

  // Reset options
  select.innerHTML = '';
  const newOption = document.createElement('option');
  newOption.value = '';
  newOption.textContent = 'New course…';
  select.appendChild(newOption);

  if (!courses) return;

  courses.forEach(c => {
    const opt = document.createElement('option');
    opt.value = c.id != null ? c.id : '';
    opt.textContent = c.code ? `${c.code} – ${c.name}` : c.name;
    select.appendChild(opt);
  });
}

// ================== LOAD ALL COURSES FROM BACKEND ==================
async function loadCoursesFromServer() {
  try {
    console.log('[API] Fetching course list from:', COURSES_LIST_ENDPOINT);

    const response = await fetch(COURSES_LIST_ENDPOINT);
    console.log('[API] Raw response:', response);

    if (!response.ok) {
      const txt = await response.text();
      console.error('[API] Error body:', txt);
      alert('Failed to load courses: ' + txt);
      return;
    }

    const data = await response.json();
    console.log('[API] JSON /api/courses/list:', data);

    loadedCourses = Array.isArray(data) ? data : [];
    populateCourseSelect(loadedCourses);
    renderCoursesTable(loadedCourses);
  } catch (err) {
    console.error('[API] Exception while loading courses:', err);
    alert('Error loading courses: ' + err.message);
  }
}

// ================== LOAD ONE COURSE INTO FORM ==================
function loadCourseIntoForm(course) {
  const courseCodeEl      = document.getElementById('courseCode');
  const courseNameEl      = document.getElementById('courseName');
  const courseCreditsEl   = document.getElementById('courseCredits');
  const courseSemesterEl  = document.getElementById('courseSemester');
  const professorNameEl   = document.getElementById('professorName');
  const courseCapacityEl  = document.getElementById('courseCapacity');
  const enrolledStudentsEl= document.getElementById('enrolledStudents');

  if (!course) {
    // reset form to "new"
    currentCourseId = null;
    if (courseCodeEl)      courseCodeEl.value = '';
    if (courseNameEl)      courseNameEl.value = '';
    if (courseCreditsEl)   courseCreditsEl.value = '';
    if (courseSemesterEl)  courseSemesterEl.value = '';
    if (professorNameEl)   professorNameEl.value = '';
    if (courseCapacityEl)  courseCapacityEl.value = '';
    if (enrolledStudentsEl)enrolledStudentsEl.value = '';
    scheduleEntries = [];
    renderScheduleTable();
    return;
  }

  console.log('[UI] Loading course into form:', course);

  currentCourseId = course.id;

  if (courseCodeEl)      courseCodeEl.value = course.code || '';
  if (courseNameEl)      courseNameEl.value = course.name || '';
  if (courseCreditsEl)   courseCreditsEl.value = course.credits != null ? course.credits : '';
  if (courseSemesterEl)  courseSemesterEl.value = course.semester || '';
  if (professorNameEl)   professorNameEl.value = course.professorName || '';
  if (courseCapacityEl)  courseCapacityEl.value = course.capacity != null ? course.capacity : '';
  if (enrolledStudentsEl)enrolledStudentsEl.value = course.enrolledStudents != null ? course.enrolledStudents : '';

  // load schedules
  scheduleEntries = [];
  if (Array.isArray(course.schedules)) {
    scheduleEntries = course.schedules.map(s => ({
      id: s.id || null,
      courseId: s.courseId || course.id || null,
      dayOfWeek: s.dayOfWeek || '',
      startTime: s.startTime || '',
      endTime: s.endTime || '',
      room: s.room || '',
      building: s.building || ''
    }));
  }
  renderScheduleTable();
}

// ================== DOM READY ==================
document.addEventListener('DOMContentLoaded', () => {
  const courseForm   = document.getElementById('courseForm');
  const scheduleForm = document.getElementById('scheduleForm');
  const courseSelect = document.getElementById('courseSelect');

  // ====== LOAD INITIAL COURSE LIST ======
  loadCoursesFromServer();

  // ====== COURSE SELECT CHANGE (LOAD COURSE INTO FORM) ======
  if (courseSelect) {
    courseSelect.addEventListener('change', () => {
      const selectedId = courseSelect.value;
      console.log('[UI] courseSelect changed:', selectedId);

      if (!selectedId) {
        // "New course…" selected
        loadCourseIntoForm(null);
        return;
      }

      const idNum = parseInt(selectedId, 10);
      const found = loadedCourses.find(c => c.id === idNum);

      if (found) {
        loadCourseIntoForm(found);
      } else {
        console.warn('[UI] Selected course not found in cache, id =', idNum);
        loadCourseIntoForm(null);
      }
    });
  }

  // ====== SCHEDULE FORM SUBMIT ======
  if (scheduleForm) {
    scheduleForm.addEventListener('submit', (e) => {
      e.preventDefault();

      const dayOfWeek = document.getElementById('dayOfWeek').value;
      const startTime = document.getElementById('startTime').value;
      const endTime   = document.getElementById('endTime').value;
      const room      = document.getElementById('room').value.trim();
      const building  = document.getElementById('building').value.trim();

      if (!dayOfWeek || !startTime || !endTime) {
        alert('Day, start time and end time are required.');
        return;
      }

      const schedule = {
        id: null,                         // new schedule, backend will assign ID
        courseId: currentCourseId || null,
        dayOfWeek,
        startTime,
        endTime,
        room,
        building
      };

      console.log('[UI] Adding schedule entry:', schedule);

      scheduleEntries.push(schedule);
      renderScheduleTable();
      scheduleForm.reset();
    });
  }

  // ====== COURSE FORM SUBMIT (CREATE / UPDATE) ======
  if (courseForm) {
    courseForm.addEventListener('submit', async (e) => {
      e.preventDefault();

      const code           = document.getElementById('courseCode').value.trim();
      const name           = document.getElementById('courseName').value.trim();
      const credits        = parseInt(document.getElementById('courseCredits').value, 10) || 0;
      const semester       = document.getElementById('courseSemester').value.trim();
      const professorName  = document.getElementById('professorName').value.trim();
      const capacity       = parseInt(document.getElementById('courseCapacity').value, 10) || 0;

      // Optional description if you add it in HTML later
      const descriptionInput = document.getElementById('courseDescription');
      const description      = descriptionInput ? descriptionInput.value.trim() : null;

      if (!code || !name || !semester) {
        alert('Course code, name and semester are required.');
        return;
      }

      // Build the Course object – matches CourseDto + ScheduleDto
      const coursePayload = {
        id: currentCourseId,          // null for new course; set when editing
        code: code,
        name: name,
        description: description,
        credits: credits,
        semester: semester,
        professorId: null,           // if you later manage numeric professorId
        professorName: professorName,
        capacity: capacity,
        enrolledStudents: null,      // backend will fill this
        schedules: scheduleEntries   // our local array
      };

      console.log('[API] Sending course payload:', coursePayload);

      try {
        const isUpdate = currentCourseId != null;
        const url = isUpdate
          ? `${COURSES_ENDPOINT}/${currentCourseId}`   // PUT /api/courses/{id}
          : COURSES_ENDPOINT;                         // POST /api/courses

        console.log(`[API] ${isUpdate ? 'PUT' : 'POST'} ->`, url);

        const response = await fetch(url, {
          method: isUpdate ? 'PUT' : 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(coursePayload)
        });

        console.log('[API] Raw save response:', response);

        if (!response.ok) {
          const errorText = await response.text();
          console.error('[API] Error response body:', errorText);
          throw new Error('Server error: ' + errorText);
        }

        const savedCourse = await response.json();
        console.log('[API] JSON savedCourse from server:', savedCourse);
        alert('Course saved successfully!');

        // Update currentCourseId & scheduleEntries from server response
        if (savedCourse && savedCourse.id != null) {
          currentCourseId = savedCourse.id;
        } else {
          currentCourseId = null;
        }

        if (savedCourse && Array.isArray(savedCourse.schedules)) {
          scheduleEntries = savedCourse.schedules.map(s => ({
            id: s.id || null,
            courseId: s.courseId || savedCourse.id || null,
            dayOfWeek: s.dayOfWeek || '',
            startTime: s.startTime || '',
            endTime: s.endTime || '',
            room: s.room || '',
            building: s.building || ''
          }));
        } else {
          scheduleEntries = [];
        }
        renderScheduleTable();

        // Reload the full course list so dropdown + table are up to date
        await loadCoursesFromServer();

      } catch (err) {
        console.error('[API] Exception while saving course:', err);
        alert('Failed to save course: ' + err.message);
      }
    });
  }

  // ====== RESET BUTTONS CLEAR COURSE + SCHEDULES ======
  const resetButtons = document.querySelectorAll('#courseForm button[type="reset"]');
  resetButtons.forEach(btn => {
    btn.addEventListener('click', () => {
      console.log('[UI] Reset course form & schedules');
      currentCourseId = null;
      scheduleEntries = [];
      renderScheduleTable();
      // Also reset dropdown to "New course…"
      const courseSelectEl = document.getElementById('courseSelect');
      if (courseSelectEl) courseSelectEl.value = '';
    });
  });
});
// ================== DOM READY ==================