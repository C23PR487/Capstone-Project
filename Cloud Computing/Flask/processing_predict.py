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

os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = r'lapakin-ad94199da830.json'

storage_client = storage.Client()

def download_string_blob(data):
  file_name = data
  source_blob_name = file_name["name"]
  bucket_name = file_name["bucket"]

  bucket = storage_client.get_bucket(bucket_name)
  blob = bucket.blob(source_blob_name)
  data = blob.download_as_string()
  return data

""" df = pd.read_csv(io.BytesIO(data), encoding='utf-8', sep=',')
print(df.head())
print(df.columns) """

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

json_data = predict_blob()
print(json_data) 