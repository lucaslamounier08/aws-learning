# Spring Boot AWS Study Project

This is a study project designed to explore and test the core functionalities of AWS (Amazon Web Services) using a Spring Boot application with Java 21. The project will serve as a sandbox environment to learn and experiment with AWS services and integrations.

---

## Features

This project will cover:
- **Spring Boot Basics**
    - REST APIs
    - Dependency Injection
    - Application Configuration

- **AWS Integrations**
    - **S3**: File storage and retrieval
    - **DynamoDB**: NoSQL database interactions
    - **SNS**: Notifications and messaging
    - **SQS**: Queue-based messaging system
    - **IAM**: User and role authentication for AWS services
    - **CloudWatch**: Logging and monitoring
    - **Lambda**: Serverless function invocation
    - **RDS**: Relational database (MySQL/PostgreSQL)

---

## Prerequisites

### Tools and Environment
- **Java 21**
- **Maven 3.8+** or Gradle
- **Spring Boot 3.x**
- **AWS CLI** installed and configured
- **Docker** (optional, for local testing with DynamoDB or S3)

### AWS Setup
- An active **AWS account**.
- IAM credentials with programmatic access.
- Necessary permissions for S3, DynamoDB, SNS, SQS, etc.

### Local Setup
1. Create an `.env` file to manage environment variables:
   ```plaintext
   AWS_ACCESS_KEY_ID=your-access-key-id
   AWS_SECRET_ACCESS_KEY=your-secret-access-key
   AWS_REGION=your-aws-region
   S3_BUCKET_NAME=your-s3-bucket-name
   DYNAMODB_TABLE_NAME=your-table-name
   SQS_QUEUE_NAME=your-queue-name
   SNS_TOPIC_ARN=your-sns-topic-arn
   RDS_DATABASE_NAME=your-database-name
   RDS_USERNAME=your-db-username
   RDS_PASSWORD=your-db-password
   ```

2. Configure the AWS CLI on your machine:
   ```bash
   aws configure
   ```

---

## Project Structure

```
.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.aws
│   │   │       ├── config        # Configuration classes
│   │   │       ├── controller    # REST controllers
│   │   │       ├── service       # Service layer
│   │   │       ├── repository    # AWS SDK and database access
│   │   │       └── model         # Entity and DTO classes
│   │   └── resources
│   │       ├── application.yml   # Application properties
│   │       └── static
├── test                          # Unit and integration tests
└── README.md
```

---

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/aws-study-project.git
   cd aws-study-project
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application at:
   ```
   http://localhost:8080
   ```

---

## Endpoints

### Example Endpoints
- **S3 Integration**:
    - `POST /s3/upload`: Upload a file to S3.
    - `GET /s3/files`: List all files in the bucket.

- **DynamoDB Integration**:
    - `POST /dynamodb/item`: Add a new item to DynamoDB.
    - `GET /dynamodb/item/{id}`: Retrieve an item by ID.

- **SNS and SQS**:
    - `POST /sns/publish`: Publish a message to an SNS topic.
    - `GET /sqs/messages`: Read messages from an SQS queue.

- **RDS**:
    - `GET /rds/users`: Retrieve a list of users from the RDS database.
    - `POST /rds/users`: Add a new user to the database.

---

## Testing AWS Locally

### DynamoDB Local
- Use the DynamoDB local container for testing:
  ```bash
  docker run -p 8000:8000 amazon/dynamodb-local
  ```

- Update your `application.yml` for local testing:
  ```yaml
  dynamodb:
    endpoint: http://localhost:8000
  ```

### S3 Local (Optional)
- Use **MinIO** for S3 emulation:
  ```bash
  docker run -p 9000:9000 -p 9001:9001 --name minio \
    -e "MINIO_ROOT_USER=admin" \
    -e "MINIO_ROOT_PASSWORD=admin123" \
    quay.io/minio/minio server /data --console-address ":9001"
  ```

---

## Useful AWS SDK for Java Links
- [AWS SDK for Java Documentation](https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/welcome.html)
- [AWS Examples for Java](https://github.com/awsdocs/aws-doc-sdk-examples)

---

## Next Steps
- Explore advanced AWS services such as Step Functions, EventBridge, and CloudFormation.
- Implement automated deployment using AWS CodePipeline or GitHub Actions.

---

## License
This project is for personal study and experimentation purposes. Feel free to fork and modify.

