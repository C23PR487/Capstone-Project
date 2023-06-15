const mysql = require('mysql');

const db = mysql.createConnection({
  host: '34.101.151.214',
  user: 'cloud-run',
  password: '?g+5E1JB3Gj>=5%O',
  database: 'db_lapakin',
});

module.exports = db;
