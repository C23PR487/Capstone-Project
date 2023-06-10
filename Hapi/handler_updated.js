const json = [
  {
    'id' : 'hgyebbnamebtg',
    'url' : 'https://www.google.com/maps/place/Fakultas+Kopi+Jakarta/data=!4m7!3m6!1s0x2e69f404f476b09d:0xad1e2ce92f97ab00!8m2!3d-6.2118378!4d106.828466!16s%2Fg%2F11df3mdnv0!19sChIJnbB29AT0aS4RAKuXL-ksHq0?authuser=0&hl=en&rclk=1',
    'nama_lapak' : 'Lapak Jakarta',
    'deskripsi' : 'asdos',
    'penjual' : 'Budi',
    'harga' : 950000,
    'luas_tanah' : 48,
    'luas_bangunan' : 40,
    'alamat' : 'Jl. Karet Karya VII No.7, RW.7',
    'kota' : 'Jakarta Selatan',
    'label' : 'Toko_Kopi'
  },
  {
    'id' : 'hadfiahjghgaf',
    'url' : 'https://www.google.com/maps/place/Fakultas+Kopi+Jakarta/data=!4m7!3m6!1s0x2e69f404f476b09d:0xad1e2ce92f97ab00!8m2!3d-6.2118378!4d106.828466!16s%2Fg%2F11df3mdnv0!19sChIJnbB29AT0aS4RAKuXL-ksHq0?authuser=0&hl=en&rclk=1',
    'nama_lapak' : 'Lapak Jakarta',
    'deskripsi' : 'asdos',
    'penjual' : 'Budi',
    'harga' : 950000,
    'luas_tanah' : 48,
    'luas_bangunan' : 40,
    'alamat' : 'Jl. Karet Karya VII No.7, RW.7',
    'kota' : 'Jakarta Selatan',
    'label' : 'Toko_Kopi'
  },
];

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
      const sql = `
      INSERT INTO tabel_lapak (
        id, maps, nama_lapak, deskripsi, nama_penjual, kontak_penjual,
        harga, luas_bangunan, alamat, kota, kecamatan, url_thumbnail, label
      ) 
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

const addDataHandler = (request) => {
  const r = request;
  const data = r.payload;
  return new Promise((resolve, reject) => {
    for (let i = 0; i < data.length; i += 1) {
      const obj = data[i];
      const { maps, nama_lapak } = obj;
      console.log(maps);
      console.log(nama_lapak);
    }
  });
};
