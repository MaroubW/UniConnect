// src/routes/studentRoutes.js
const express = require('express');
const {
  createStudent,
  getStudents,
  getStudentById,
  updateStudent,
  deleteStudent,
} = require('../controllers/studentController');

const { authMiddleware, isAdmin } = require('../middleware/auth');

const router = express.Router();

// All student routes require authentication
// router.use(authMiddleware);

// Only admin can create / update / delete
// router.post('/', isAdmin, createStudent);
router.post('/',createStudent);
router.get('/', getStudents); // students themselves can read list (optional rule)
router.get('/:id', getStudentById);
router.put('/:id', updateStudent);
router.delete('/:id', deleteStudent);

module.exports = router;
