# URL SHORTENER
A service that lets users create short URLs (like Bitly, Rebrandly, etc.) for their original links.


### Functional requirements *(copied over from task description)*
- Service should let users register a new account and authenticate themselves.
- Service should let authenticated users create shortened URLs.
- Service should let any user use shortened URLs (e.g., follow redirects to original URLs).
 
### Technical requirements

- Service has to serve requests over HTTP API.
- We don't expect UI for this task.
- User registration could be very simple with just a login(email) and password.
- You can use any tech solution for storing data but consider that your project has to be easy to launch. For example, if you use any DB consider including deploy scripts or containerization scripts.
- The same rule for dependencies and libraries. If you use packages that other people are unlikely to have, consider including deploy or containerization scripts.

### design
 - assuming the service is global like internet itself - therefore there will be multiple applications because:  
     - much like CDN you want redirects to happen locally because its faster
 - there will likely be no interactions between  users and not much info stored in the db (mostly key-value pairs)
 - since the app does not store large amounts of data and is supposed to be MVP, so using in-memory database 
 - using jwt token for session handling as it is easy to setup and supports scaling of the app (whatever one app can do others can to because token is checked every time)

### tech stack

Given all above assumptions here is what I propose to use:
 - Java + Spring framework (contains modules for app container, security, privilege handling)
 - Spring boot is a container for this app - can be run with `java -jar UrlShortener-1.0.jar`
 - H2 in memory database (lightweight, just for the sake of MVP)
   - *(NOTE: normally persistent DB is better because you don't want to loose urls when app crashes)*
 - JJWT - for setting JWT token auth. Reasons - simple to setup, no session handling code, no centralized process for authentication, easy for scaling,
 - Testing with standard JUnit for unit tests - there is hardly any logic performed in requests handling (like adding key-value to the DB)
 - Integration Testing with Rest Assured - most of the logic is in network interactions. We will need some good end to end tests to check authentication and url creation

### data design

   UserAccount:
   - email
   - password 

   just like in the description of the task, nothing to add


   UrlMapping: 
   - id
   - url 
   - user email 
   - hashcode (shortened url) 
    
   the reason for this modelling is that we don't want duplicate url in the db; 
   
   Also, the same url is tracked per user, because I saw bitly offers some statistics on user traffic on their links; So to enable 
   user to track results of their e.g. marketing campaign and not collide with others we keep it "per user";

   creating uuid based on email + url combo - hashing it so that it's faster when e.g. indexing (future possibility) 
   I'm going to perform a check before adding a potential duplicate url to the database;
   