import pandas as pd
import googlemaps
import time
def get_nearby_malls(latitude, longitude, radius, min_reviews):
    places = gmaps.places_nearby(
        location=(latitude, longitude),
        radius=radius,
        type="shopping_mall"
    )
    mall_names = []
    keywords = ["mall", "plaza", "square"]
    for place in places["results"]:
        place_details = gmaps.place(place["place_id"], fields=["name", "user_ratings_total"])
        # print("places details", place_details)
        # print("=========================================")
        if place_details["status"] == "OK" and place_details.get("result"):
            total_ratings = place_details["result"].get("user_ratings_total", 0)
            name = place_details["result"]["name"]
            if any(keyword in name.lower() for keyword in keywords) or total_ratings > min_reviews:
                mall_names.append(name)
    return mall_names
def get_nearby_office(latitude, longitude, radius, min_reviews):
    places = gmaps.places_nearby(
        location=(latitude, longitude),
        radius=radius,
        type=["accounting", "city_hall", "lawyer","police","post_office"]
    )
    office_name = []
    keywords = ["kantor", "office", "PT"]
    for place in places["results"]:
        place_details = gmaps.place(place["place_id"], fields=["name", "user_ratings_total"])
        # print("places details", place_details)
        # print("=========================================")
        if place_details["status"] == "OK" and place_details.get("result"):
            total_ratings = place_details["result"].get("user_ratings_total", 0)
            name = place_details["result"]["name"]
            if any(keyword in name.lower() for keyword in keywords) or total_ratings > min_reviews:
                office_name.append(name)
    return office_name

def get_nearby_school(latitude, longitude, radius, min_reviews):
    places = gmaps.places_nearby(
        location=(latitude, longitude),
        radius=radius,
        type=["primary_school", "school", "secondary_school","university"]
    )
    school_name = []
    keywords = ["sekolah", "universitas", "institut","SD","MI","SM","MTs","MA","school"]
    for place in places["results"]:
        place_details = gmaps.place(place["place_id"], fields=["name", "user_ratings_total"])
        # print("places details",place_details)
        # print("=========================================")
        if place_details["status"] == "OK" and place_details.get("result"):
            total_ratings = place_details["result"].get("user_ratings_total", 0)
            name = place_details["result"]["name"]
            if any(keyword in name.lower() for keyword in keywords) or total_ratings > min_reviews:
                school_name.append(name)
    return school_name

# Meminta input nama file
file_name = "your_file.xlsx"

# Membaca file Excel
df = pd.read_excel(file_name)

# Membuat kolom baru "jumlah mall terdekat" dengan nilai awal 0
df["jumlah mall terdekat"] = 0
df["jumlah kantor terdekat"] = 0
df["jumlah sekolah terdekat"] = 0
# df["nama_mall"] = ""
# df["nama_kantor"] = ""
# df["nama_sekolah"] = ""


# Inisialisasi client Google Maps
api_key = "Your_Google_Places_API"  # Ganti dengan kunci API Google Maps Anda
gmaps = googlemaps.Client(key=api_key)

# Iterasi melalui setiap baris
start = time.time()
for index, row in df.iterrows():
    # Mendapatkan URL Google Maps dari kolom "URL"
    google_maps_url = row["url"]

    # Mengambil nama toko kopi dari kolom "nama toko"
    nama_toko = row["nama toko"]
    alamat = row["alamat"]
    print(index,nama_toko)
    # Menggunakan Google Places API untuk mendapatkan informasi lokasi berdasarkan nama toko kopi
    places = gmaps.places(nama_toko)

    if places["status"] == "OK" and places.get("results"):
        # Mengambil koordinat latitude dan longitude dari hasil pencarian
        location = places["results"][0]["geometry"]["location"]
        latitude = location["lat"]
        longitude = location["lng"]

        # Mengambil jumlah review dari kolom "jumlah review"
        #jumlah_review = row["jumlah review"]

        # Menghitung jumlah mall terdekat dengan jumlah review lebih dari 500 dan mendapatkan nama mall
        mall_names = get_nearby_malls(latitude, longitude, 1000, 25)
        office_names = get_nearby_office(latitude, longitude, 1000, 25)
        school_names = get_nearby_school(latitude, longitude, 1000, 25)

        # Menyimpan nilai jumlah mall terdekat pada kolom "jumlah mall terdekat"
        df.loc[index, "jumlah mall terdekat"] = len(mall_names)
        df.loc[index, "jumlah kantor terdekat"] = len(office_names)
        df.loc[index, "jumlah sekolah terdekat"] = len(school_names)
        # print("nama mall",mall_names)
        # print("nama kantor", office_names)
        # print("nama sekolah", school_names)
        # Menyimpan nama-nama mall pada kolom "nama_mall"
        # df.loc[index, "nama_mall"] = ", ".join(mall_names)
        # df.loc[index, "nama_kantor"] = ", ".join(office_names)
        # df.loc[index, "nama_sekolah"] = ", ".join(school_names)
end = time.time()
running_time = (end-start)/60
print("running time = ",running_time," menit")
df["waktu running"] = running_time
# Menyimpan DataFrame ke file Excel yang sama
df.to_excel(file_name, index=False)
