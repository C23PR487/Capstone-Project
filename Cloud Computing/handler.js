const { nanoid } = require('nanoid');
const db = require('./db_config');

// Digunakan untuk mengambil seluruh data dalam tabel lapak
const getAllDataHandler = () => (
  new Promise((resolve, reject) => {
    const sql = 'SELECT * FROM tabel_lapak';
    db.query(sql, (error, results) => {
      if (error) {
        reject(error);
      } else {
        resolve(results);
      }
    });
  })
);

// Digunakan untuk mengambil data sesuai prioritas filer
// Masih belum rampung, masih perlu dibuatkan filter untuk database & pengurutan berdasarkan prior
/* Apakah cheking value request.query dan request.payload dibuat di mobile? agar lebih efektif
   supaya server tidak dipanggil kalau value nya = null */
const getAllDataByPriorityHandler = (request) => {
  const { prior1, value1 } = request.query;
  return new Promise((resolve, reject) => {
    const sql = `SELECT * FROM tabel_lapak WHERE ${prior1}='${value1}'`;
    db.query(sql, (error, results) => {
      if (error) {
        reject(error);
      } else {
        resolve(results);
      }
    });
  });
};

const getDataByIdHandler = (request) => {
  const { id } = request.params;
  return new Promise((resolve, reject) => {
    const sql = `SELECT * FROM tabel_lapak WHERE id ='${id}'`;
    db.query(sql, (error, results) => {
      if (error) {
        reject(error);
      } else {
        resolve(results);
      }
    });
  });
};

const addDataHandler = (request) => {
  const {
    harga,
    lokasi,
    label,
  } = request.payload;

  const id = nanoid(16);

  return new Promise((resolve, reject) => {
    const sql = `INSERT INTO tabel_lapak (id, harga, lokasi, label) VALUES ('${id}', '${harga}', '${lokasi}', '${label}')`;
    db.query(sql, (error) => {
      if (error) {
        reject(error);
      } else {
        resolve({
          status: 'success',
          message: 'lokasi berhasil ditambahkan',
          data: {
            id,
          },
        });
      }
    });
  });
};

module.exports = {
  getAllDataHandler,
  getAllDataByPriorityHandler,
  getDataByIdHandler,
  addDataHandler,
};
