// src/controllers/studentController.js
const Student = require('../models/Student');

// Create new student
const createStudent = async (req, res, next) => {
  try {
    const student = await Student.create(req.body);
    return res.status(201).json(student);
  } catch (error) {
    next(error);
  }
};

// Get all students (with optional filters / pagination)
const getStudents = async (req, res, next) => {
  try {
    const { page = 1, limit = 10, department } = req.query;

    const query = {};
    if (department) query.department = department;

    const students = await Student.find(query)
      .skip((page - 1) * limit)
      .limit(Number(limit));

    const total = await Student.countDocuments(query);

    return res.json({
      data: students,
      page: Number(page),
      total,
    });
  } catch (error) {
    next(error);
  }
};

// Get single student by id
const getStudentById = async (req, res, next) => {
  try {
    const student = await Student.findById(req.params.id);
    if (!student) {
      return res.status(404).json({ message: 'Student not found' });
    }
    return res.json(student);
  } catch (error) {
    next(error);
  }
};

// Update student
const updateStudent = async (req, res, next) => {
  try {
    const student = await Student.findByIdAndUpdate(
      req.params.id,
      req.body,
      { new: true, runValidators: true }
    );
    if (!student) {
      return res.status(404).json({ message: 'Student not found' });
    }
    return res.json(student);
  } catch (error) {
    next(error);
  }
};

// Delete student
const deleteStudent = async (req, res, next) => {
  try {
    const student = await Student.findByIdAndDelete(req.params.id);
    if (!student) {
      return res.status(404).json({ message: 'Student not found' });
    }
    return res.json({ message: 'Student deleted successfully' });
  } catch (error) {
    next(error);
  }
};

module.exports = {
  createStudent,
  getStudents,
  getStudentById,
  updateStudent,
  deleteStudent,
};
