<a href="https://www.entelect.co.za">
    <img src="https://avatars.githubusercontent.com/u/8133921?s=200&v=4" alt="Entelect logo" title="Entelect" align="right" height="60" />
</a>

# Entelect Spring Incubator

The Entelect Spring incubator aims to familiarise developers with popular and useful Spring projects used in modern Java-based applications.

Please refer to the official documentation for usage guidelines: https://entelectza.sharepoint.com/spring-incubator-2021

# Running the Service

## Databases

All services that make use of a database have been configured to use in memory DBs.
This means that you don't need anything other than the code in this repository to start the services.
It also means that all data that had been persisted will be lost when you restart a service.

## Access and Security

Basic authentication has been implemented in some parts of the system and you will need credentials to access the endpoints.
Check the `SecurityConfig.java` classes for credentials.

## Some notes

- Always <b>build</b> your project before pushing. There is no build server to check whether your code works
