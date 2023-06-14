import os
import requests
import numpy as np
import pandas as pd
import joblib
import io
from io import BytesIO
from keras.models import load_model
from json import loads, dumps

from google.cloud import storage

os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = r'lapakin-af53b1042c90.json'

# bucket_name, source_blob_name, destination_file_name
def download_blob(data):
    """Downloads a blob from the bucket."""

    file_data = data
    # The ID of GCS bucket
    bucket_name = file_data["bucket"]
    # bucket_name = 'csv-bucket-lapakin'

    # The ID of GCS object
    source_blob_name = file_data["name"]
    # source_blob_name = 'available_stalls.csv'

    storage_client = storage.Client()

    # Get the bucket
    bucket = storage_client.get_bucket(bucket_name)

    # Get the blob source 
    blob = bucket.blob(source_blob_name)

    #Get the blob string
    data = blob.download_as_string()
    return data

# df_blob = download_blob() Keperluan Test local

def get_predicted_label(y_result):
  rows = y_result.shape[0]
  y_pred = np.zeros(rows)
  for row in range(rows):
      y_pred[row] = np.argmax(y_result[row])
  return y_pred

def convert_to_json(df):
  result = df.to_json(orient="index")
  parsed = loads(result)
  list = []
  for k in parsed:
    v=parsed[k]
    list.append(v)
  return dumps(list, indent=4) 

def predict_blob(data):
   model = load_model('./lapakin_model.h5')
   scaler = joblib.load('lapakin_scaler.pkl') 
   label_encoder = joblib.load('lapakin_encoder.pkl') 

   df_test = pd.read_csv(io.BytesIO(data), encoding= 'unicode_escape')
   X_test = df_test[['jumlah mall terdekat', 'jumlah kantor terdekat', 'jumlah sekolah terdekat']]
   X_test= scaler.transform(X_test)
   y_test = model.predict(X_test)
   df_test['label'] = label_encoder.inverse_transform(get_predicted_label(y_test).astype(int))

   classified_stalls_json = convert_to_json(df_test)

   return classified_stalls_json 

# json_data = predict_blob(df_blob) Keperluan Test Local

def upload_to_db(data):
    """Upload Spaces to database through Back-end server endpoint"""
    payload = data # (data) JSON 
    r = requests.post('http://localhost:9000/stalls', data=payload) #url diganti dengan url cloud run
    print(r.json)
    print(
        "Request sent to the Back-end endpoint"
    )

# upload_to_db(json_data) Keperluan Test Local

def delete_blob(data):
    """Delete a blob in the bucket."""

    file_data = data
    # The ID of your GCS bucket
    bucket_name = file_data["bucket"]
    # bucket_name = 'csv-bucket-lapakin'

    # The ID of your GCS object
    blob_name = file_data["name"]
    # blob_name = 'available_stalls.csv'


    storage_client = storage.Client()

    bucket = storage_client.bucket(bucket_name)
    blob = bucket.blob(blob_name)
    # generation_match_precondition = None

    # Optional: set a generation-match precondition to avoid potential race conditions
    # and data corruptions. The request to delete is aborted if the object's
    # generation number does not match your precondition.
    # blob.reload()  # Fetch blob metadata to use in generation_match_precondition.
    # generation_match_precondition = blob.generation
    # if_generation_match=generation_match_precondition
    blob.delete()

    print(f"Blob {blob_name} deleted.")

# delete_blob() Keperluan test