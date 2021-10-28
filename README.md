# blogapp

### Admin User name : admin
### Admin User password : 12345


### url : http://localhost:8000/
### Server port : 8000

##### Admin user and (ROLE_ADMIN, ROLE_BLOGGER) will be created at the startup of the application. 


## Features
1. There will be two types of users. Admin & Blogger.
2. One Admin type user’s account will be system generated when the application bootstrap
    for the first time (No Admin type user exists in the app).
3. Admin will have following functionalities:
  * Log in with username/password
  * Create other Admin account
  * Approve / Deactivate Blogger type user’s account
  * Approve and publish Blog post
  * Delete any blog post
4. Blogger will have following functionality before his/her account is approved by Admin:
  * Create an account and send for Admin’s approval.
5. Blogger will have following functionalities after his/her account is approved by Admin:
  * Log in with username / password
  * Create blog post and send for admin’s approval
  * Delete own blog post
  * View other blogger’s approved blog post
  * Comment on other blogger’s approved blog post
  * Like / Dislike other blogger’s approved blog
    post

##### All features have been implemented.



#### Requirement:
* Java 8,
* Maven 3.3.9
