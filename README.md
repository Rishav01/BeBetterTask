# BeBetterTask

Below are the technical features demonstrated
1. All basic spring boot feature like autowiring and other
2. H2 database usage
3. Proper exception handling: global exception handler, defined the exception response, exception codes and types
4. Data validation: using hibernate validator implementation. @Valid. Usage of @Validated for path variable and request param
5. Liquibase integration
6. Swagger enabled
7. SOLID principles:
  a. S [Single Responsibility]: Have controller, service, repository classes which has single responsibility rather than having everything inside controller.
  b. O [Open to extend while closed for modification]: Used UpdateStatusService interface which is ready to be extended while
  
  
8. Factory design pattern: For Updatestatus scenario, where multiple implementation of UpdateStatus based on scenarios like task openeing, cancellation or completion. Create factory class to achive the usage of the implementation based on the status value received in the request.
