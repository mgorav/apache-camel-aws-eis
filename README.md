# Router Pattern In Data/Software Engineering

What is common between Navigation System & Data Engineering - "_**movement from A to B with location transparency**_". **Location transparency** means that "A" is unaware of "B". A navigation system handles "location transparency" by defining "routes" i.e. how to go from "A" to "B" by picking up the correct highway and as a driver "_I don't want to know how_"

Can we apply same principle to "Data Engineering". The answer is  "Yes". Location transparency is achieved by implementation of "**Router Pattern**". _The Router pattern has been recognized as an excellent way to accomplish Enterprise Application Integration (EAI)._ A router is a component  that connects its consumer  to one of multiple output strategies (as enunciated in the strategy design pattern). This pattern is also one of the most powerful design pattern for the "micro-services architecture" as it can transform an application that is monolithic, non modular, non configurable among other bad things into a thing of beauty/piece of art. 

![alt text](./navigation.png)

Check out my Github project which shows:
1. Moving data to "Data Lake - AWS S3"
2. Moving data to "Data Pipeline - AWS Kinesis & Apache Kafka"
3. Moving API data to "Data Pipeline - AWS Kinesis" 
4. Content Based Routing (CBR) to "Data Pipeline Apache Kafka"

It also demonstrates opinionated way of AWS based development without AWS account  but the same software will run on AWS cloud with "no fuss".

## EIA Patterns demonstrated

Following EIA are implemented using Spring Boot & Apache Camel:
1. Route to Spring bean
2. Route to AWS S3
3. Route to AWS Kinesis from REST end point
4. Route to Apache Kafka
5. Content based routing

![alt text](./routes.png)

Let's checkout one of routes which moved REST API data to AWS Kinesis:
```java_holder_method_tree
  restConfiguration().host("localhost").port(4001);

        from("timer:hi?period={{timer.period}}")
                .setHeader("id", simple("${random(1,3)}"))
                .to("rest:get:cars/{id}")
                .log("[going to Kinesis]"+"${body}")
                .setHeader(KinesisConstants.PARTITION_KEY,simple("1"))
                .setHeader(KinesisConstants.SHARD_ID, simple("1"))
                .to("aws-kinesis://mykinesisstream?amazonKinesisClient=#amazonKinesisClient")
                .to("log:out?showAll=true")
                .log("Completed Writing to Kinesis");
```
To run this project you can setup/emulate AWS locally on you laptop by following below steps. It also comes with docker image of Apache Kafka + Zookeeper

### Step 1: Create *python* virtual environment
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


## Play time
Just run the Spring Boot application - "**SpringBootCamelApplication**" from IDE or maven & observe the logs.
```bash
2019-04-06 11:50:50.010  INFO 22172 --- [ucer[TestTopic]] route2                                   : 
{
  "orderNumber": 1,
  "country": "US",
  "amount": 100,
  "items": [
    {
      "itemId" : 123,
      "itemCost": 33,
      "itemQty": 12
    }
  ]
}
2019-04-06 11:50:50.013  INFO 22172 --- [umer[TestTopic]] FromKafka                                : consumed message from Kafka
2019-04-06 11:50:50.013  INFO 22172 --- [umer[TestTopic]] FromKafka                                : Hello from Gonnect
2019-04-06 11:50:50.036  INFO 22172 --- [umer[TestTopic]] FromKafka                                : consumed message from Kafka
2019-04-06 11:50:50.036  INFO 22172 --- [umer[TestTopic]] FromKafka                                : 
{
  "orderNumber": 1,
  "country": "NL",
  "amount": 100,
  "items": [
    {
      "itemId" : 123,
      "itemCost": 33,
      "itemQty": 12
    }
  ]
}

.... ...... ........

```

### Route Information

```bash
curl -XGET -s http://localhost:4001/actuator/camelroutes
```
```json
[
  {
    "id": "route1",
    "uptime": "9.780 seconds",
    "uptimeMillis": 9781,
    "properties": {
      "parent": "64beb2b7",
      "rest": "false",
      "description": null,
      "id": "route1"
    },
    "status": "Started"
  },
  {
    "id": "RandomTextGeneratorRoute",
    "uptime": "9.780 seconds",
    "uptimeMillis": 9780,
    "properties": {
      "parent": "4e4b7abd",
      "rest": "false",
      "description": null,
      "id": "RandomTextGeneratorRoute"
    },
    "status": "Started"
  },
  {
    "id": "FromKafka",
    "uptime": "9.736 seconds",
    "uptimeMillis": 9736,
    "properties": {
      "parent": "730cd2d0",
      "rest": "false",
      "description": null,
      "id": "FromKafka"
    },
    "status": "Started"
  },
  {
    "id": "kafkaStartWithPartitioner",
    "group": "kafka-route-group",
    "uptime": "9.735 seconds",
    "uptimeMillis": 9735,
    "properties": {
      "parent": "f10d3e4",
      "rest": "false",
      "description": null,
      "id": "kafkaStartWithPartitioner",
      "group": "kafka-route-group"
    },
    "status": "Started"
  },
  {
    "id": "route2",
    "uptime": "9.734 seconds",
    "uptimeMillis": 9734,
    "properties": {
      "parent": "38e4f7b",
      "rest": "false",
      "description": null,
      "id": "route2"
    },
    "status": "Started"
  },
  {
    "id": "hello",
    "group": "hello-group",
    "uptime": "9.734 seconds",
    "uptimeMillis": 9734,
    "properties": {
      "parent": "1915ce41",
      "rest": "false",
      "description": null,
      "id": "hello",
      "group": "hello-group"
    },
    "status": "Started"
  }
]
```

```bash
curl -XGET -s http://localhost:4001/actuator/camelroutes/{id}/detail
```
**NOTE**:  _id = route1_ 
```json
{
  "id": "route1",
  "uptime": "2 minutes",
  "uptimeMillis": 164554,
  "properties": {
    "parent": "64beb2b7",
    "rest": "false",
    "description": null,
    "id": "route1"
  },
  "status": "Started",
  "details": {
    "deltaProcessingTime": -4,
    "exchangesInflight": 0,
    "exchangesTotal": 82,
    "externalRedeliveries": 0,
    "failuresHandled": 0,
    "firstExchangeCompletedExchangeId": "ID-APMGJGH67551C6-1554502425015-0-3",
    "firstExchangeCompletedTimestamp": "2019-04-05T22:13:49.139+0000",
    "lastExchangeCompletedExchangeId": "ID-APMGJGH67551C6-1554502425015-0-590",
    "lastExchangeCompletedTimestamp": "2019-04-05T22:16:30.879+0000",
    "lastProcessingTime": 10,
    "maxProcessingTime": 408,
    "meanProcessingTime": 21,
    "minProcessingTime": 8,
    "redeliveries": 0,
    "totalProcessingTime": 1769,
    "hasRouteController": false
  }
}
```