# This is one part of a massive project called "data-microservice".
# This part of the microservice, called "data_store_microservice", is responsible for storing data and creating statistics from the overlying data.

After all stages of data generation in "data_generator_microservice" and data processing and structuring in "data_analyser_microservice", the processed data come to this microservice where detailed statistics will be made from them using Debezium and Redis (code example): updateMinValue, updateMaxValue, updateSumAndAverageValue, updateSumValue.

Full list of technologies used for the microservice "data_ana_microservice" :

- Java 17
- Spring 3.2.2
- Jedis 5.1.0
- MapStruct 1.5.5Final
- MapStruct Processor 1.5.5Final
- Spring Kafka 3.1.1
- JUnit5 5.0.0 ALPHA
- Mockito 5.9.0