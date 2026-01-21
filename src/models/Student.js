// src/models/Student.js
const mongoose = require('mongoose');

const studentSchema = new mongoose.Schema(
  {
    studentNumber: {
      type: String,
      required: true,
      unique: true,  // e.g. “2023-GL-001”
      trim: true,
    },
    firstName: {
      type: String,
      required: true,
      trim: true,
    },
    lastName: {
      type: String,
      required: true,
      trim: true,
    },
    email: {
      type: String,
      required: true,
      unique: true,
      lowercase: true,
      trim: true,
    },
    dateOfBirth: {
      type: Date,
    },
    department: {
      type: String, // e.g. "GL", "SI"
    },
    level: {
      type: String, // e.g. "3ème année"
    },
    isActive: {
      type: Boolean,
      default: true,
    },
  },
  {
    timestamps: true, // createdAt, updatedAt
  }
);

module.exports = mongoose.model('Student', studentSchema);
