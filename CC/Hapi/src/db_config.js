const mysql = require('mysql');

const db = mysql.createConnection({
  host: '34.101.204.176',
  user: 'root',
  password: '$^tYr~/:0L[GXe?,',
  database: 'db_lapakin',
});

db.connect((err) => {
  if (err) throw err;
  console.log('Connected!');
});

module.exports = db;
