WITH not_important AS (
    SELECT 
        id, 
        nama_toko,
        harga,
        luas_tanah,
        luas_bangunan,
        kota,
        label
    FROM lapakin
    WHERE kota != 'Jakarta Selatan' AND label = 'usaha_baju'
    order by harga
), important AS (
    SELECT 
        id, 
        nama_toko,
        harga,
        luas_tanah,
        luas_bangunan,
        kota,
        label
    FROM lapakin
    WHERE kota = 'Jakarta Selatan' AND label = 'usaha_baju' AND harga < 1000000
    order by harga
)
SELECT * FROM important
UNION all
SELECT * FROM not_important;