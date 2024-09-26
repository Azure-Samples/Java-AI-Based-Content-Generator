# Dependencies Summary

### Refer: [pom.xml](./pom.xml)

## Spring Boot Starters

| Artifact ID                                                 | Version         | Scope   | Description                                                             |
|-------------------------------------------------------------|-----------------|---------|-------------------------------------------------------------------------|
| `org.springframework.boot:spring-boot-starter-data-mongodb` | Managed by Boot | Default | Starter for using MongoDB in Spring Boot applications.                  |
| `org.springframework.boot:spring-boot-starter-web`          | Managed by Boot | Default | Starter for building web, including RESTful, applications using Spring. |
| `org.springframework.boot:spring-boot-starter-webflux`      | Managed by Boot | Default | Supports reactive programming model in Spring Boot.                     |
| `org.springframework.boot:spring-boot-starter-tomcat`       | Managed by Boot | Default | Tomcat web server for deploying Spring Boot WAR files.                  |
| `org.springframework.boot:spring-boot-starter-test`         | Managed by Boot | Test    | Provides testing utilities for Spring Boot applications.                |

---

## Dependency Management
### `spring-cloud-azure.version : 5.16.0`

| Artifact ID                                        | Version                         | Scope  | Type | Description                           |
|----------------------------------------------------|---------------------------------|--------|------|---------------------------------------|
| `com.azure.spring:spring-cloud-azure-dependencies` | `${spring-cloud-azure.version}` | Import | pom  | Azure Storage support in Spring Cloud |


## Spring Cloud Azure

| Artifact ID                                                    | Version                       | Scope   | Description                                     |
|----------------------------------------------------------------|-------------------------------|---------|-------------------------------------------------|
| `com.azure.spring:spring-cloud-azure-starter-storage`          | Managed by Spring Cloud Azure | Default | Azure Storage support in Spring Cloud           |
| `com.azure.spring:spring-cloud-azure-starter-keyvault-secrets` | Managed by Spring Cloud Azure | Default | Azure Key Vault Secrets support in Spring Cloud |

---
