const { nanoid } = require('nanoid');
const db = require('./db_config');

const getTestServerHandler = () => new Promise((resolve) => {
  console.log('Berhasil dipanggil');
  resolve({ message: 'Bisa nih aman!' });
});

const dbTable = 'lapak_lapakin';
// Retrieve all data in database
const getAllDataHandler = () => (
  new Promise((resolve, reject) => {
    const sql = `SELECT * FROM ${dbTable}`;
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
    label = null, prior1 = null, prior2 = null, prior3 = null,
    value1 = null, value2 = null, value3 = null,
  } = request.query;
  return new Promise((resolve, reject) => {
    if (prior1 === 'harga') {
      const valueHarga = parseInt(value1, 10);
      const sql = `
      WITH fourth_important as (
        SELECT kota, harga, kecamatan, id, label, alamat, url_thumbnail
          FROM ${dbTable}
          WHERE label = ${label} 
          ORDER BY harga
      ),third_important AS (
          SELECT kota, harga, kecamatan, id, label, alamat, url_thumbnail
          FROM ${dbTable}
          WHERE CASE 
            WHEN ${prior3} IS NULL THEN  ${prior2} = ${value2} AND label = ${label}
            ELSE ${prior2} = ${value2} AND ${prior3} = ${value3} AND label = ${label} 
          END
          ORDER BY harga
      ), second_important AS(
        SELECT kota, harga, kecamatan, id, label, alamat, url_thumbnail
          FROM ${dbTable}
          WHERE CASE 
            WHEN ${prior3} IS NULL THEN ${prior2} = ${value2} AND label = ${label}
            ELSE ${prior2} = ${value2} AND ${prior3} != ${value3} AND label = ${label} AND ${prior1} <= ${valueHarga}
          END
          ORDER BY harga
      ), first_important AS (
          SELECT kota, harga, kecamatan, id, label, alamat, url_thumbnail
          FROM ${dbTable}
          WHERE CASE 
            WHEN ${prior3} IS NULL THEN ${prior2} = ${value2} AND label = ${label} AND ${prior1} <= ${valueHarga}
            ELSE ${prior2} = ${value2} AND ${prior3} != ${value3} AND label = ${label} AND ${prior1} <= ${valueHarga}
          END
          ORDER BY harga
      )
      SELECT DISTINCT * from(
      SELECT * FROM first_important
      UNION ALL
      SELECT * FROM second_important
      UNION ALL
      SELECT * FROM third_important
      UNION ALL
      SELECT * FROM fourth_important
      ) as combined_result;`;
      db.query(sql, (err, res) => {
        if (err) {
          reject(err);
        }
        resolve(res);
      });
    }
    if (prior1 === 'kota' || prior1 === 'kecamatan') {
      const sql = `
      WITH third_important AS (
        SELECT kota, harga, kecamatan, id, label, alamat, url_thumbnail
        FROM ${dbTable}
        WHERE label = ${label} 
        ORDER BY harga
    ), second_important AS(
      SELECT kota, harga, kecamatan, id, label, alamat, url_thumbnail
        FROM ${dbTable}
        WHERE CASE 
          WHEN ${prior2} IS NULL THEN label = ${label}
          ELSE ${prior1} = ${value1} AND ${prior2} != ${value2} AND label = ${label}
        END
        ORDER BY harga
    ), first_important AS (
        SELECT kota, harga, kecamatan, id, label, alamat, url_thumbnail
        FROM ${dbTable}
        WHERE CASE 
          WHEN ${prior2} IS NULL THEN ${prior1} = ${value1} AND label = ${label}
          ELSE ${prior1} = ${value1} AND ${prior2} = ${value2} AND label = ${label} 
        END
        ORDER BY harga
    )
    SELECT DISTINCT * from(
    SELECT * FROM first_important
    UNION ALL
    SELECT * FROM second_important
    UNION ALL
    SELECT * FROM third_important
    ) as combined_result;`;
      db.query(sql, (err, res) => {
        if (err) {
          reject(err);
        }
        resolve(res);
      });
    }
  });
};

const getDataByIdHandler = (request) => {
  const { id } = request.params;
  return new Promise((resolve, reject) => {
    const sql = `SELECT * FROM ${dbTable} WHERE id ='${id}'`;
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
  const r = request;
  const data = r.payload;
  return new Promise((resolve, reject) => {
    for (let i = 0; i < data.length; i += 1) {
      const obj = data[i];
      const {
        maps, namaLapak, deskripsi, namaPenjual, kontakPenjual,
        harga, luasBangunan, alamat, kota, kecamatan, urlThumbnail, label,
      } = obj;
      const id = nanoid(16);
      const sql = `
      INSERT INTO lapak_lapakin (
        id, maps, nama_lapak, deskripsi, nama_penjual, kontak_penjual,
        harga, luas_bangunan, alamat, kota, kecamatan, url_thumbnail, label
      ) 
      VALUES (
        '${id}', '${maps}', '${namaLapak}', '${deskripsi}', '${namaPenjual}', '${kontakPenjual}',
        '${harga}', '${luasBangunan}', '${alamat}', '${kota}', '${kecamatan}', '${urlThumbnail}', '${label}')`;
      db.query(sql, (error) => {
        if (error) {
          reject(error);
          console.log(error);
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
  getDataByIdHandler,
  addDataHandler,
  getTestServerHandler,
};
