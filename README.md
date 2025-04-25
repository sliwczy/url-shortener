# URL SHORTENER
A service that lets users create short URLs (like Bitly, Rebrandly, etc.) for their original links.

// copied over from task description
#### Functional requirements
- Service should let users register a new account and authenticate themselves.
- Service should let authenticated users create shortened URLs.
- Service should let any user use shortened URLs (e.g., follow redirects to original URLs).
 
#### Technical requirements

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

### tech stack

Given all above assumptions here is what I propose to use:
 - Java + Spring framework (contains modules for app container, security, privilege handling)
 - H2 in memory database (lightweight, just for the sake of MVP)
   - *(NOTE: normally persistent DB is better because you don't want to loose urls when app crashes)*
 - JJWT
 - Testing with JUnit + Citrus (?) - there is hardly any logic performed in requests handling (like adding key-value to the DB) but there is some logic in network interactions. 
   it will be good to have some end to end tests to check authentication and url creation


#### beyond mvp:
- there is one url endpoint, how do you get redirected to the local instance ?
- how does db persistance looks like when vm is killed ? persistance in docker/kubernetes ?

