<h1>Simple-Vat Application</h1>

<h3>Project coding standards:</h3>


1. Do not display lebels. Use only empty lebels.
2. Use autocomplete as an alter native for dropdowns.
3. Accordian for grouping of atrributes.
4. Form within a panel and tool par outside panel
5. Componenet and managed bean annotation to be used.
6. Multiple pages instead of sinple page module
7. Use of java constants for page path or all path
8. Use of internationalization. Message property file for lebels and messages.
9. One controller per view. 
10. Plural names for listing and single name for create and edit pages.
11. Name of the controller = <View Name>Controller
12. Crud Name
	a. save<Domain>()
	b. delete<Domain>()
	c. get<Domain>ById()
	d. get<Domain>s()
13. Named queries in Domain classes. Named queries should be <Domain>.queryname
14. Package per module in dao, services etc.
15. common package in dao, services etc. for common classes
16. Implement serializable for managedbean, entities or where even getter or setter are
17. implement serialVersionUID in all serializable classes with unique long number
18. Create Model classes per entity in web module
19. <Domain>Model naming for model classes
20. Use One xhtml page for both create/edit functionality. Just render labels based on action.

<h3>Points to be Added/Refactored</h3>

- POM Refactoring. Dependency declaration to parent.
- DriverManager issue
- Add Validators
    - Use lombok as much as possible 
- Spring Security
- SQL Injection Filter
- Implement Cache
- Controller refactor. Use Services
- Criteria queries better than named Queries. (Some refactoring)
    - Paging for all get\<Domain>s() calls.
- Wrappers/DTO/Model for UI object and entity mapper to dto
- Omit the Unused code
- Serializable on dto/model. not on Controller 
- Properties must be injected from Spring
- Don't use Calendar/ util date/ SQL date time/ timestamp. Use java time api
- Manage GIT efficiently. Create PRs
- Use Mockito. Write tests for each method
- Unit tests rollback
  
<h3>Project Milestone & Scope</h3>

1. Alpha Release: 15th June 2017(Release of first version of application.)
2. Beta Release : 15th October 2017(Production ready version of application.)
3. Final product Release: 15th December 2017 (Application live on production mode.)


<h3>Detailed milestone plan</h3>

1. Alpha Release: (Today to 15 June)
	1. Completion of Contact, Invoice and Expense crud operations. (20th March 2017)
	2. Completion of Project, bank account module and User profile module. (30th March 2017) 
	3. Completion of User authentication and roles modules. (dev complete - 30th April 2017) 
	4. Third party testing and bug fixes.(1st May to 31st May)
	5. Accountant testing. (15th May to 15th July)

2. Beta Release: (16th June to 15th October)
	TBD
3. Final Production Release: (16th October to 15th December)   

