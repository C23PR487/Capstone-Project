# C23-PR487's Capstone Project : LapakIn

## Table of Contents

### [Introduction](#introduction) 

#### [1. Machine Learning](#machinelearning) 

###### [Installation](#installation-1)

###### [Usage](#usage-1)

###### [Configuration](#configuration-1)

#### [2. Mobile Development](#mobiledevelopment) 

###### [Installation](#installation-2)

###### [Usage](#usage-2)

###### [Configuration](#configuration-2)

#### [3. Cloud Computing](#cloudcomputing)

###### [Installation](#installation-3)

###### [Usage](#usage-3)

###### [Configuration](#configuration-3)

### [Contributing](#contributing)

### [License](#license)


## Introduction <a name="introduction"></a>

Our application aims to facilitate users in starting their own businesses by providing a list of business stalls available for sale or rent that match their desired business type. Currently, we offer options between a coffee shop and a clothing store.

## Machine Learning <a name="machinelearning"></a>

### Installation <a name="installation-1"></a>
Explain how to install your project. Include any prerequisites, dependencies, or specific setup instructions.

### Usage <a name="usage-1"></a>
Provide examples and instructions on how to use your project. Include code snippets or command-line examples to demonstrate its functionality.

### Configuration <a name="configuration-1"></a>
Explain any configuration options or settings that users can modify. Include relevant file paths or environment variables.


## Mobile Development <a name="mobiledevelopment"></a>

### Installation <a name="installation-2"></a>
Explain how to install your project. Include any prerequisites, dependencies, or specific setup instructions.

### Usage <a name="usage-2"></a>
Provide examples and instructions on how to use your project. Include code snippets or command-line examples to demonstrate its functionality.

### Configuration <a name="configuration-2"></a>
Explain any configuration options or settings that users can modify. Include relevant file paths or environment variables.


## Cloud Computing <a name="cloudcomputing"></a>

### Installation <a name="installation-3"></a>

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
Provide examples and instructions on how to use your project. Include code snippets or command-line examples to demonstrate its functionality.

### Configuration <a name="configuration-3"></a>
Explain any configuration options or settings that users can modify. Include relevant file paths or environment variables.

## Contributing <a name="contributing"></a>
Specify guidelines for contributing to your project. Include information on how to submit bug reports, feature requests, or pull requests.

## License <a name="license"></a>
State the license under which your project is released. Provide a link to the license file, if applicable.
