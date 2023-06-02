WITH not_important AS (
    SELECT 
        id, 
        "nama toko",
        Harga,
        "Luas Tanah",
        "Luas Bangunan",
        kota
    FROM lapakin
    WHERE kota != 'Jakarta Selatan'
    order by harga
), important AS (
    SELECT 
        id,
        "nama toko",
        Harga,
        "Luas Tanah",
        "Luas Bangunan",
        kota
    FROM lapakin
    WHERE kota = 'Jakarta Selatan' AND harga < 1000000
    order by harga
)
SELECT * FROM important
UNION all
SELECT * FROM not_important;