# AWS SQS sample
### Pre-requisite
- AWS EC2 instance
-	Secuirty group of EC2 instance should have following inbound rules
  -	Custom TCP Rule(Type)- TCP (Protocol)- 8080 (Port range)- 0.0.0.0/0 (Source)
- AWS SQS standard queue named "test-input-queue"
-	Create a new IAM Role, with policy "AmazonSQSFullAccess"
- Attach the IAM Role to EC2 instance.
### Installing the code in EC2 instance
-	Download git code
- Move project source code (src folder, pom.xml) to EC2 instance using WinSCP
- Login to EC2 instance usign Putty and go to base project folder
- Create jar file
  - mvn package -Dmaven.test.skip=true
- Run the jar file
	- java -jar spring-boot-jms-sqs-0.0.1-SNAPSHOT.jar
- Following console output should be displayed
  - ID received: 1111
  - Message received: Test SQS Message
### Testing using message from SQS Console
-	Go to AWS SQS console
-	Select queue(test-input-queue)
-	Select Queue Action> Send a message
-	Message Body entry(sample)
	{"ID":"9999","Message":"Check check"}
-	Mesage Attribute entries
-	JMSXGroupID/String/ messageGroup1
-	JMS_SQSMessageType/ String/text
-	documentType/ String/ com.poc.model.input.CombinerInput
-	Click "Send Message"
-	The Consumer should process the message
