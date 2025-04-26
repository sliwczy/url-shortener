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
     - assuming users are likely to link local content (like articles in their language), which is also an argument behind locality 
 - there will likely be a lot of users with likely no interactions with each other and not much info stored as well (key-value pairs)
 - the app does not store large amounts of data and is supposed to be MVP, so using in-memory database 
 - if there is some routing to a most geo-local app, all instances in the region need to have the same db (separate from the app)
   or have some replication mechanism...

### tech stack

Given all above assumptions here is what I propose to use:
 - Java + Spring framework (contains modules for app container, security, privilege handling)
 - H2 in memory database (lightweight, just for the sake of MVP)
   - *(NOTE: normally persistent DB is better because you don't want to loose urls when app crashes)*
 - JJWT - for setting JWT token auth. Reasons - simple to setup, no session handling code, no centralized process for authentication, easy for scaling,
 - Testing with JUnit + Citrus (?) - there is hardly any logic performed in requests handling (like adding key-value to the DB) but there is some logic in network interactions. 
   it will be good to have some end to end tests to check authentication and url creation

### data design

   main entity has:
   - id
   - url 
   - user email 
   - hashcode 
    
   the reason for this modelling is that we don't want duplicate url in the db; Also, the same url is tracked per user, because I saw bitly offers some statistics on user traffic on their links; So to enable 
   user to track results of their e.g. marketing campaign and not collide with others we keep it "per user";

   sticking with long id as primary key, it has better performance and long urls are not very well suited for indexing, should we need some; instead
   I'm going to perform a check before adding a potential duplicate url to the database;
   
   
### ideas beyond mvp:
- there could be multiple regional instances of the app; figure out, if there is one url endpoint, how to redirect to most geo-local instance ?
- how does db persistance looks like when vm is killed ? persistance in docker/kubernetes ?
- could have a scheduled service scanning urls in the database for existing malicious url to disalow redirects there;
  also this check could happen when trying to add a url, but above case describes when at a later time the site is proven malicious; 
