import os
import requests
import io
from io import BytesIO
from json import loads, dumps
from google.cloud import storage

import numpy as np
import pandas as pd
import joblib
from keras.models import load_model
import warnings
warnings.filterwarnings("ignore")

"""Back-end Endpoint to upload the predicted blob data to database"""
base_backend_url = 'http://localhost:8080'

def get_test_url():
   r = requests.get(base_backend_url)
   return r.content

def get_predicted_label(y_result):
  """Part of the Predict Function"""
  rows = y_result.shape[0]
  y_pred = np.zeros(rows)
  for row in range(rows):
      y_pred[row] = np.argmax(y_result[row])
  return y_pred

def convert_to_json(df):
  """Output Predict Process"""
  result = df.to_json(orient="index")
  parsed = loads(result)
  list = []
  for k in parsed:
    v=parsed[k]
    list.append(v)
  return dumps(list, indent=4) 

def predict_blob(data):
   """Main Predict Function"""
   model = load_model('./lapakin_model.h5')
   scaler = joblib.load('./lapakin_scaler.pkl')
   label_encoder = joblib.load('./lapakin_encoder.pkl')

   df_stall = pd.read_csv(io.BytesIO(data), encoding= 'unicode_escape')
   X_stall = df_stall[['jumlah mall terdekat', 'jumlah kantor terdekat', 'jumlah sekolah terdekat']]
   X_stall= scaler.transform(X_stall)
   y_stall = model.predict(X_stall)
   df_stall['label'] = label_encoder.inverse_transform(get_predicted_label(y_stall).astype(int))

   classified_stalls_json = convert_to_json(df_stall)
   print(classified_stalls_json)

   return classified_stalls_json

def upload_to_db(data):
    """Upload Stalls to database through Back-end server endpoint"""

    payload = data 
    r = requests.post(base_backend_url, data=payload) 
    print(r.json)
    
    print("Request sent to the Back-end endpoint")

def delete_blob(data):
    """Delete the blob in the bucket."""

    file_data = data
    bucket_name = file_data["bucket"]
    blob_name = file_data["name"]

    storage_client = storage.Client()

    bucket = storage_client.bucket(bucket_name)
    blob = bucket.blob(blob_name)
    blob.delete()

    print("Blob {blob_name} deleted.")

def process_data(data):
    """Process predict, upload and delete the blob"""

    file_data = data
    bucket_name = file_data["bucket"]
    source_blob_name = file_data["name"]

    storage_client = storage.Client()
    bucket = storage_client.get_bucket(bucket_name)
    blob = bucket.blob(source_blob_name)

    # Download blob as a string
    data_blob = blob.download_as_string()

    # Predict the downloaded blob strings
    json_data = predict_blob(data_blob)

    # Upload the blob 

    return upload_to_db(json_data)



