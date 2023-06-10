const { nanoid } = require('nanoid');
const db = require('./db_config');

// Retrieve all data in database
const getAllDataHandler = () => (
  new Promise((resolve, reject) => {
    const sql = 'SELECT * FROM lapak_lapakin';
    db.query(sql, (error, results) => {
      if (error) {
        reject(error);
      } else {
        resolve(results);
      }
    });
  })
);

const getFilteredDataHandler = (request) => {
  const {
    label, prior1, value1, prior2, value2,
  } = request.query;
  return new Promise((resolve, reject) => {
    if (prior1 === 'harga') {
      const sql = `WITH not_important AS (
        SELECT id, maps, nama_lapak, deskripsi, nama_penjual, kontak_penjual, 
        harga, luas_bangunan, alamat, kota, kecamatan, url_thumbnail, label 
        FROM lapak_lapakin
        WHERE ${prior2} != ${value2} AND label = ${label}
        order by ${prior1}
    ), important AS (
      SELECT id, maps, nama_lapak, deskripsi, nama_penjual, kontak_penjual, 
      harga, luas_bangunan, alamat, kota, kecamatan, url_thumbnail, label 
        FROM lapak_lapakin
        WHERE ${prior2} = ${value2} AND label = ${label} AND ${prior1} < ${value1}
        order by ${prior1}
    )
    SELECT * FROM important
    UNION all
    SELECT * FROM not_important;`;

      db.query(sql, (error, results) => {
        if (error) {
          reject(error);
        } else {
          resolve(results);
        }
      });
    } else {
      const sql = `WITH not_important AS (
        SELECT id, maps, nama_lapak, deskripsi, nama_penjual, kontak_penjual, 
        harga, luas_bangunan, alamat, kota, kecamatan, url_thumbnail, label 
        FROM lapak_lapakin
        WHERE ${prior1} = ${value1} AND label = ${label}
        order by ${prior2}
    ), important AS (
      SELECT id, maps, nama_lapak, deskripsi, nama_penjual, kontak_penjual, 
      harga, luas_bangunan, alamat, kota, kecamatan, url_thumbnail, label 
        FROM lapak_lapakin
        WHERE ${prior1} = ${value1} AND label = ${label} AND ${prior2} < ${value2}
        order by ${prior2}
    )
    select DISTINCT * from (
    SELECT * FROM important
    UNION all
    SELECT * FROM not_important
    ) as combined_results;`;

      db.query(sql, (error, results) => {
        if (error) {
          reject(error);
        } else {
          resolve(results);
        }
      });
    }
  });
};

/* const getDataByIdHandler = (request) => {
  const { id } = request.params;
  return new Promise((resolve, reject) => {
    const sql = `SELECT * FROM lapak_lapakin WHERE id ='${id}'`;
    db.query(sql, (error, results) => {
      if (error) {
        reject(error);
      } else {
        resolve(results);
      }
    });
  });
}; */

const addDataHandler = (request) => {
  const r = request;
  const data = r.payload;
  return new Promise((resolve, reject) => {
    for (let i = 0; i < data.length; i += 1) {
      const obj = data[i];
      const id = nanoid(16);
      const {
        maps, namaLapak, deskripsi, namaPenjual, kontakPenjual,
        harga, luasBangunan, alamat, kota, kecamatan, urlThumbnail, label,
      } = obj;

      const sql = `INSERT INTO lapak_lapakin (
        id, maps, nama_lapak, deskripsi, nama_penjual, kontak_penjual, 
        harga, luas_bangunan, alamat, kota, kecamatan, url_thumbnail, label) 
        VALUES (
          '${id}', '${maps}', '${namaLapak}', '${deskripsi}', '${namaPenjual}', '${kontakPenjual}', 
          '${harga}', '${luasBangunan}', '${alamat}', '${kota}', '${kecamatan}', '${urlThumbnail}', '${label}')`;
      db.query(sql, (error) => {
        if (error) {
          reject(error);
        } else {
          resolve({
            status: 'success',
            message: 'lokasi berhasil ditambahkan',
          });
        }
      });
    }
  });
};

module.exports = {
  getAllDataHandler,
  getFilteredDataHandler,
  // getDataByIdHandler,
  addDataHandler,
};
