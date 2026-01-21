// =========================
// API GATEWAY CONFIG
// =========================
const API_BASE_URL = 'http://localhost:9191/api'; 
// If later you host behind nginx or another domain, just change this one.

// Simple in-memory list of schedules for the currently edited course
let scheduleEntries = [];
let currentCourseId = null; // set this if you load an existing course

// Helper: update the schedule table in the UI
function renderScheduleTable() {
  const tbody = document.querySelector('.table-scroll .billing-table tbody');
  if (!tbody) return;

  // Clear existing rows
  tbody.innerHTML = '';

  scheduleEntries.forEach((s, index) => {
    const tr = document.createElement('tr');

    tr.innerHTML = `
      <td>${s.dayOfWeek}</td>
      <td>${s.startTime}</td>
      <td>${s.endTime}</td>
      <td>${s.room}</td>
      <td>${s.building}</td>
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

// Attach handlers after DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
  const courseForm   = document.getElementById('courseForm');
  const scheduleForm = document.getElementById('scheduleForm');

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
        dayOfWeek,
        startTime,
        endTime,
        room,
        building
      };

      scheduleEntries.push(schedule);
      renderScheduleTable();
      scheduleForm.reset();
    });
  }

  // ====== COURSE FORM SUBMIT ======
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

      // Build the Course object – matches your CourseDto in the API gateway
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
        enrolledStudents: null,      // backend can fill this
        schedules: scheduleEntries
      };

      console.log('Sending course payload to API gateway:', coursePayload);

      // ====== IMPORTANT PART: choose URL + method depending on create / update ======
      let url = `${API_BASE_URL}/courses`;
      let method = 'POST';

      if (currentCourseId != null) {
        // For update, call PUT /api/courses/{id}
        url = `${API_BASE_URL}/courses/${currentCourseId}`;
        method = 'PUT';
      }

      try {
        const response = await fetch(url, {
          method,
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(coursePayload)
        });

        if (!response.ok) {
          const errorText = await response.text();
          throw new Error('Server error: ' + errorText);
        }

        const savedCourse = await response.json();
        console.log('Saved course from backend:', savedCourse);
        alert('Course saved successfully!');

        // If backend returns the course with ID, keep it for future updates
        if (savedCourse.id != null) {
          currentCourseId = savedCourse.id;
        }

      } catch (err) {
        console.error(err);
        alert('Failed to save course: ' + err.message);
      }
    });
  }

  // (Optional) reset button clears schedules too
  const resetButtons = document.querySelectorAll('#courseForm button[type="reset"]');
  resetButtons.forEach(btn => {
    btn.addEventListener('click', () => {
      currentCourseId = null;
      scheduleEntries = [];
      renderScheduleTable();
    });
  });
});
