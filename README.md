# twitter-killer-server
2013\. Study group project. Java server for "Twitter killer" app - very simplified Twitter analog.
## Structure
* `src/main/java/../dataacess` - db util
* `src/main/java/../domain/dto` - server answers
* `src/main/java/../domain/entity` - model
* `src/main/java/../domain/repository` - interfaces 
* `src/main/java/../domain/util` - other utils
* `src/main/java/../rest` - REST api
* `src/main/resources` - Hibernate mappings
## Features
1. User login
2. Posting a tweet
3. Getting all tweets
## Technology
* Java (JAX-RS, Hibernate, Jackson, javax.inject, java.security, Mockito)
* Jetty
* Maven
