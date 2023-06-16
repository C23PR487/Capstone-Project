const mysql = require('mysql');

const db = mysql.createConnection({
  host: '34.101.39.104',
  user: 'cloud-run',
  password: 'H-TjH[&dREl@t4])',
  database: 'db_lapakin',
});

module.exports = db;
