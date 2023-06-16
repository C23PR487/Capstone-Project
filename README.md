# C23-PR487's Capstone Project : LapakIn

## Table of Contents

### [Our Team](#our-team)

### [Introduction](#introduction) 

### [1. Machine Learning](#machinelearning) 

#### [Installation](#installation-1)

#### [Usage](#usage-1)

#### [Configuration](#configuration-1)

### [2. Mobile Development](#mobiledevelopment) 

#### [Installation](#installation-2)

#### [Usage](#usage-2)

#### [Configuration](#configuration-2)

### [3. Cloud Computing](#cloudcomputing)

#### [Installation for Hapi Server](#hapi)

#### [Installation for Flask Server](#flask)

<br>

## Our Team <a name="our-team"></a>
- C017DSX0837 - Haoking Suryanatmaja - Institut Teknologi Bandung - Cloud Computing - active
- C085DSX0732 - I Kadek Priyana Adi Merta - Sekolah Tinggi Manajemen Informatika dan Komputer STIKOM Indonesia - Cloud Computing - active
- M038DSX0045 - Mohammad Fisal Aly Akbar - Institut Teknologi Sepuluh Nopember - Machine Learning - active
- M181DSY0447 - Febi Imanuela - Universitas Indonesia - Machine Learning - active
- A181DSX1150 - Samuel Raja Partogi Panggabean - Universitas Indonesia - Learning path - active
```diff
- M017DSX0071 - Andhika Reyhan Soebroto - Institut Teknologi Bandung - Machine Learning - inactive
```

## Introduction <a name="introduction"></a>

The Ministry of Finance of the Republic of Indonesia reports that MSMEs employ 97% of the country's workforce, highlighting their immense potential in driving economic growth. However, starting an MSME business can be a complex and overwhelming task, and many entrepreneurs struggle to know where to start. To fully harness the potential of MSMEs and boost economic growth in Indonesia, it is essential to address these challenges and provide entrepreneurs with the necessary support and resources.

Our team recognizes this challenge and is leveraging AI technology to provide location recommendations for MSMEs. By analyzing factors such as access to resources and facilities, we aim to simplify the process of starting an MSME business and increase the chances of success for new enterprises. With these location recommendations, entrepreneurs can make informed decisions about where to establish their businesses, ensuring that they have the necessary support to thrive.

By empowering entrepreneurs to start and grow their MSMEs, we can create more job opportunities and contribute to Indonesia's economic development. Moreover, our approach can help to reduce barriers to entry for MSMEs, allowing entrepreneurs from diverse backgrounds to participate in the country's economy.

Our application aims to facilitate users in starting their own businesses by providing a list of business stalls available for sale or rent that match their desired business type. Currently, we offer options between a coffee shop and a clothing store.

## Machine Learning <a name="machinelearning"></a>

### Installation <a name="installation-1"></a>
1. Git:  
- Install Git on your local machine. You can download it from the official Git website.  
- Clone the repository by running the following command in your terminal:
```shell
git clone https://github.com/C23PR487/Capstone-Project.git
```
- Navigate to the ML folder:
```shell
cd Capstone-Project/ML
```

2. Google Colab:  
- Make sure you have Google Account
- Use Google Colab (https://colab.research.google.com/) to open `LapakIn.ipynb` notebook that you've retrieved from the cloned repository
> The alternative to open the notebook is by using Jupyter Notebook on your local machine (https://jupyter.org/install)

### Usage <a name="usage-1"></a>
You can run the cells in Google Colab by choosing run option in `Runtime` menu or run a single cell by clicking the run logo at desired cell.

There are several sections in the `LapakIn.ipynb` notebook that need to be ran sequentially:
1. Section `Setup` is used for dependencies installation.
2. Section `EDA & Preprocesing` is used for data preparation before feeding it to the model.
3. Section `Modeling` is used for model creation.
4. Section `Training` is used for model training on the dataset.
5. Section `Testing` is used for model testing for evaluation.
6. Section `Predict New Input` is used for prediction of new data. 

### Configuration <a name="configuration-1"></a>
To classify business types of available business stalls in other dataset (still with attributes of 'jumlah mall terdekat', 'jumlah kantor terdekat', 'jumlah sekolah terdekat', and target of 'label'), you can modify this code in section `Predict New Input`:
```shell
df_stall = pd.read_csv('<new_dataset_to_be_predicted>', encoding= 'unicode_escape')
```
<br>

## Mobile Development <a name="mobiledevelopment"></a>

### Installation <a name="installation-2"></a>
1. Make sure you have Android studio. You can install it through this [link](https://developer.android.com/studio).
2. Make sure you have a Maps SDK for Android API Key that you can get through this [link](https://developers.google.com/maps/documentation/android-sdk/get-api-key)
3. Clone the repository by running the following command in your terminal:
```shell
git clone https://github.com/C23PR487/Capstone-Project.git
```
4. Open Android Studio
5. Click the Open Project button and select MD folder.
6. After gradle build has finished, look for local.properties file and add this in a new line
```shell
MAPS_API_KEY={your api key from step 2 without the braces '{}'}
```

### Usage <a name="usage-2"></a>
- To run the application in debug mode in your android studio emulator, simply press the run button
- To get the apk itself, go to Build -> Build Bundle(s) / APK(s) -> Build APK(s)
- You can then install the apk in your android device

### Configuration <a name="configuration-2"></a>
- You can go to MD/app/src/main/java/io/github/c23pr487/lapakin/repository/ApiConfig.kt and replace the news api token with your own
- You can also replace the MD/app/google-services.json file with your own to connect to your own firebase project, however, please note to change 
MD/app/src/main/res/values/strings.xml <database_url> and MD/app/src/main/java/io/github/c23pr487/lapakin/repository/ProfileRepository.kt url to your firebase realtime db url.

<br>

## Cloud Computing <a name="cloudcomputing"></a>

### Installation for Hapi Server<a name="hapi"></a>

#### Prerequisites
Git: Install Git on your local machine. You can download it from the official Git website.

Node.js and npm: Make sure you have Node.js and npm (Node Package Manager) installed. You can download them from the official Node.js website.

Google Cloud SDK: Install the Google Cloud SDK to interact with Google Cloud Platform services. Follow the instructions provided in the Google Cloud SDK documentation.

#### Installation
To get started with the Capstone Project, perform the following steps:
1. Clone the repository by running the following command in your terminal:
```shell
git clone https://github.com/C23PR487/Capstone-Project.git
```

2. Navigate to the CC/Hapi folder:
```shell
cd Capstone-Project/CC/Hapi
```

3. Install the project dependencies using npm:
```shell
npm install
```

4. To build the Docker image for the Hapi server, run the following command:
```shell
gcloud builds submit --tag gcr.io/$GOOGLE_CLOUD_PROJECT/hapi-server
```

5. To deploy the Hapi backend on Google Cloud Run, execute the following command:
```shell
gcloud run deploy hapi-server \
  --image gcr.io/$GOOGLE_CLOUD_PROJECT/hapi-server \
  --platform managed \
  --region asia-southeast2 \
  --memory=2Gi \
  --allow-unauthenticated \
  --max-instances=1
```

6. To obtain the URL for the deployed Hapi backend service, run the following commands:
```shell
SERVICE_URL=$(gcloud beta run services describe hapi-server --platform managed --region asia-southeast2 --format="value(status.url)")
echo $SERVICE_URL
```

7. You can test the deployed Hapi backend service using cURL:
```shell
curl -X GET $SERVICE_URL
```
Make sure to replace $SERVICE_URL with the actual URL obtained from the previous step.

### Usage <a name="usage-3"></a>
To use the API, refer to Postman documentation https://documenter.getpostman.com/view/21912069/2s93sdYrVe

<br>

### Installation for Flask Server<a name="flask"></a>

#### Prerequisites

Git: Install Git on your local machine. You can download it from the official Git website.

Google Cloud SDK: Install the Google Cloud SDK to interact with Google Cloud Platform services. Follow the instructions provided in the Google Cloud SDK documentation.

#### Installation
To get started with the Flask server, perform the following steps:
1. Clone the repository by running the following command in your terminal:
```shell
git clone https://github.com/C23PR487/Capstone-Project.git
```

2. Navigate to the CC/Flask folder:
```shell
cd Capstone-Project/CC/Flask
```

3. Install the project dependencies using pip:
```shell
pip install -r requirements.txt
```

4. To build the Docker image for the Flask server, run the following command:
```shell
gcloud builds submit --tag gcr.io/$GOOGLE_CLOUD_PROJECT/flask-server
```

5. To deploy the Flask server on Google Cloud Run, execute the following command:
```shell
gcloud run deploy flask-server \
  --image gcr.io/$GOOGLE_CLOUD_PROJECT/flask-server \
  --platform managed \
  --region asia-southeast2 \
  --memory=2Gi \
  --no-allow-unauthenticated \
  --max-instances=1
```

6. Create a Cloud Storage bucket:
```shell
gsutil mb gs://csv-bucket-test2
```

7. Create a Cloud Pub/Sub notification:
```shell
gsutil notification create -t new-doc -f json -e OBJECT_FINALIZE gs://csv-bucket-test2
```

8. Create a service account for Pub/Sub Cloud Run invoker:
```shell
gcloud iam service-accounts create pubsub-cloud-run-invoker --display-name "PubSub Cloud Run Invoker"
```

9. Assign necessary permissions to the service account:
```shell
gcloud beta run services add-iam-policy-binding flask-server --member=serviceAccount:pubsub-cloud-run-invoker@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com --role=roles/run.invoker --platform managed --region asia-southeast2
```

```shell
gcloud projects list
PROJECT_NUMBER=[project number]
```

```shell
gcloud projects add-iam-policy-binding $GOOGLE_CLOUD_PROJECT --member=serviceAccount:service-$PROJECT_NUMBER@gcp-sa-pubsub.iam.gserviceaccount.com --role=roles/iam.serviceAccountTokenCreator
```

10. Create a Cloud Pub/Sub subscription:
```shell
gcloud beta pubsub subscriptions create predict-flask-sub --topic new-doc --push-endpoint=$SERVICE_URL --push-auth-service-account=pubsub-cloud-run-invoker@$GOOGLE_CLOUD_PROJECT.iam.gserviceaccount.com
```

Make sure to replace $SERVICE_URL with the actual URL obtained from the deployment step.
