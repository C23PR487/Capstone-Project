const mysql = require('mysql');

const db = mysql.createConnection({
  host: '34.134.166.13',
  user: 'root',
  password: '2Q?n#GSc1oq,>c;a',
  database: 'database-lapak',
});

db.connect((err) => {
  if (err) throw err;
  console.log('Connected!');
});

module.exports = db;