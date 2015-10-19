# Rainbow


 ## Version 0.1

 - Spring Boot Application Enablement
 - Integration with H2 in-memory Data Store, DB configured @ "/console"
 - Data Store table creation through Schema and JPA(ORM)
 - HATEOAS Support for simple Pagination, Search and Sort.
 - Event Handlers for Pre & Post Processing Enabled


 ### Bugs
 - Error handling when wrong parameters are sent as query params or path params or post params.
 - Systems attempts to close DB second time while shutting the embedded webserver component.