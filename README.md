

Retrofit : 
It is the standard library for making API calls
Has 3 components: 
    - POJO Class : (Plain Old Java Object) Maps API response to a List of User defined Java Objects
    - Api Interface : To define the API endpoints
    - Service : Define the factory methods and converters
    
RXJava : 
A third party libraryReactive Asynchronous Programming Paradigm. Used to handle asynchronous code. 
Based on Observer pattern, where one entity creates data and other entity observes it. 

Components: 
    - Observables : Objects that can emit data and be observed.
    - Observers or Subscribers : Subscribes to observables or listens to data emitted by observables.
    - Single : Emits single event (Data or Error) and then it ends. For ex. a network response, which can be either the data of response or error. 
