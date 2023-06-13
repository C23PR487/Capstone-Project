WITH fourth_important as (
	SELECT 
		id,
        harga,
        alamat,
        kota,
        label,
        kecamatan
    FROM lapakin
    WHERE label = 'usaha_baju' 
    ORDER BY harga
),third_important AS (
    SELECT 
    	id,
        harga,
        alamat,
        kota,
        label,
        kecamatan
    FROM lapakin
    WHERE CASE 
    	WHEN kecamatan IS NULL THEN kota = 'Jakarta Utara' AND label = 'usaha_baju'
    	ELSE kota = 'Jakarta Utara' AND label = 'usaha_baju' 
    END
    ORDER BY harga
), second_important AS(
	SELECT 
		id,
        harga,
        alamat,
        kota,
        label,
        kecamatan
    FROM lapakin
    WHERE CASE 
    	WHEN kecamatan IS NULL THEN kota = 'Jakarta Utara' AND label = 'usaha_baju'
    	ELSE kota = 'Jakarta Utara' AND kecamatan != 'Pademangan' AND label = 'usaha_baju' AND harga < 1000000
    END
    ORDER BY harga
), first_important AS (
    SELECT 
    	id,
        harga,
        alamat,
        kota,
        label,
        kecamatan
    FROM lapakin
    WHERE CASE 
    	WHEN kecamatan IS NULL THEN kota = 'Jakarta Utara' AND label = 'usaha_baju' AND harga < 1000000
    	ELSE kota = 'Jakarta Utara' AND kecamatan = 'Pademangan' AND label = 'usaha_baju' AND harga < 1000000
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
) as combined_result;