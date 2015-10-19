# Rainbow

## Scope

### Environment
 - Java - Spring Boot Client Server Application (Embedded Web Server)
 - Python - Django Application
 - Ruby on Rails - RoR Client Server App

### Data Stores
 - H2
 - H2 - MongoDB Integration
 - H2 - HSQL Integration
 - H2 - PSQL Integration

### Messaging
 - AMQP
 - MQTT
 - Web Sockets

### Complex Event Processor
 - Event Handlers for Pre & Post Processing
 - No-Thread Blocking using Java Reactors

### Security
 - OAuth 2.*
 - LDAP Integration
 - Active Directory Integration

### Load Balancing & High Availability
 - NGINX Integration for Soft Load Balancing



## Release: Capabilities and Issues


### Version 0.0.1

 - Spring Boot Application Enablement
 - Integration with H2 in-memory Data Store, DB configured @ "/console"
 - Data Store table creation through Schema and JPA(ORM)
 - HATEOAS Support for simple Pagination, Search and Sort.
 - Event Handlers for Pre & Post Processing Enabled


#### Bugs
 - Error handling when wrong parameters are sent as query params or path params or post params.
 - Systems attempts to close DB second time while shutting the embedded webserver component.