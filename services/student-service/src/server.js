// server.js
require('dotenv').config();
const express = require('express');
const cors = require('cors');
const morgan = require('morgan');

// Correct paths (your structure has src/... inside project/)
const connectDB = require('./config/db');
const studentRoutes = require('./routes/studentRoutes');
const errorHandler = require('./middleware/errorHandler');

const app = express();

// Connect to MongoDB
connectDB();

// Middleware
app.use(cors());
app.use(express.json());
app.use(morgan('dev'));

// Health Check
app.get('/health', (req, res) => {
  res.json({ status: 'Student Service is running' });
});

// Routes
app.use('/students', studentRoutes);

// Error Handler (after routes)
app.use(errorHandler);

// Start server
const PORT = process.env.PORT || 4001;
app.listen(PORT, () => {
  console.log(`Student Service listening on port ${PORT}`);
});
