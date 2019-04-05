#Spring Boot Camel EIS

Following EIP are implemented using Spring Boot & Apache Camel:
1. Route to Spring bean
2. Route to AWS S3
3. Route to AWS Kinesis from REST end point
4. Route to Apache Kafka


### Step 1: Create virtual environment
```bash
python3 -m virtualenv localstackenv
```

### Step 2: Activate virtual environment
```bash
source localstackenv/bin/activate   
```

### Step 3: Install AWS Local stack
```bash
pip install localstack    
```
### Step4: Start localstack

```bash
localstack start --docker
```

### Step 4: Start kafka with zookeeper
```bash
docker-compose up
```
