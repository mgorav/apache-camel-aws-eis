# Router Pattern In Data/Software Engineering

What is common between Navigation System & Data Engineering - "movement from A to B with location transparency". Location transparency means that "A" is unaware of "B". A navigation system handles "location transparency" by defining "routes" i.e. how to go from "A" to "B" by picking up the correct highway and as a driver "I don't want to know how"

Can we apply same principle to "Data Engineering". The answer is  "Yes". Location transparency is achieved by implementation of "Router Pattern". The Router pattern has been recognized as an excellent way to accomplish Enterprise Application Integration (EAI). A router is a component  that connects its consumer  to one of multiple output strategies (as enunciated in the strategy design pattern). This pattern is also one powerful design pattern in the "micro-services architecture" as it can transform an application that is monolithic, non modular, non configurable among other bad things into a thing of beauty/piece of art. 

Check out my Github project which shows:
1. Moving data to "Data Lake - AWS S3"
2. Moving data to "Data Pipeline - AWS Kinesis & Apache Kafka"
3. Moving API data to "Data Pipeline - AWS Kinesis" 

It also demonstrates opinionated way of AWS based development without AWS account  but the same software will run on AWS cloud with "no fuss".

## EIA Pattern demonstrated

Following EIA are implemented using Spring Boot & Apache Camel:
1. Route to Spring bean
2. Route to AWS S3
3. Route to AWS Kinesis from REST end point
4. Route to Apache Kafka

To run this project you can setup/emulate AWS locally on you laptop by following below steps. It also comes with docker image of Apache Kafka + Zookeeper

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
