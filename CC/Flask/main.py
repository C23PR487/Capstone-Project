import base64
import json

from flask import Flask, request

import processing


app = Flask(__name__)


@app.route("/stalls", methods=["POST"])
def index():
    """Receive and parse Pub/Sub messages containing Cloud Storage event data."""
    envelope = request.get_json()
    if not envelope:
        msg = "no Pub/Sub message received"
        print(f"error: {msg}")
        return f"Bad Request: {msg}", 400

    if not isinstance(envelope, dict) or "message" not in envelope:
        msg = "invalid Pub/Sub message format"
        print(f"error: {msg}")
        return f"Bad Request: {msg}", 400

    # Decode the Pub/Sub message.
    pubsub_message = envelope["message"]

    if isinstance(pubsub_message, dict) and "data" in pubsub_message:
        try:
            data = json.loads(base64.b64decode(pubsub_message["data"]).decode())

        except Exception as e:
            msg = (
                "Invalid Pub/Sub message: "
                "data property is not valid base64 encoded JSON"
            )
            print(f"error: {e}")
            return f"Bad Request: {msg}", 400

        # Validate the message is a Cloud Storage event.
        if not data["name"] or not data["bucket"]:
            msg = (
                "Invalid Cloud Storage notification: "
                "expected name and bucket properties"
            )
            print(f"error: {msg}")
            return f"Bad Request: {msg}", 400

        try:
            df_blob = processing.download_blob(data) 
            json_data = processing.predict_blob(df_blob) 
            processing.upload_to_db(json_data) 
            processing.delete_blob(data) 
            return ("Download, Predict, Upload dan Delete berhasil dijalankan.", 204)

        except Exception as e:
            print(f"error: {e}")
            return ("", 500)

    return ("", 500)