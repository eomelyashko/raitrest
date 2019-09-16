### curl samples (application deployed in application context `Restorant rating`).
> For windows use `Git Bash`

## ADMIN Users
#### get All Users
`curl -v http://localhost:8080/restaurant/rest/admin/users --user admin@gmail.com:admin`

#### get Users 100001
`curl -v http://localhost:8080/restaurant/rest/admin/users/100001 --user admin@gmail.com:admin`

#### get not found
`curl -v http://localhost:8080/restaurant/rest/admin/users/1 --user admin@gmail.com:admin`

#### Get user by email
`curl -v http://localhost:8080/restaurant/rest/admin/users/by?email=user@yandex.ru --user admin@gmail.com:admin`

#### Delete user 100005
`curl -v -X DELETE http://localhost:8080/restaurant/rest/admin/users/100005  --user admin@gmail.com:admin`

#### Delete user, double delete - delete not found
`curl -v -X DELETE http://localhost:8080/restaurant/rest/admin/users/100005  --user admin@gmail.com:admin`

#### Unauthorized
`curl -v http://localhost:8080/restaurant/rest/admin/users`

#### Forbidden
`curl -v http://localhost:8080/restaurant/rest/admin/users --user manager@yandex.ru:manager`

#### Update user
`curl -v -X PUT -d '{ "id":"100000","name":"UpdatedName","email":"user@yandex.ru","enabled":true,"registered":"2019-09-11T11:39","roles":["ROLE_ADMIN"],"password":"password"}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/admin/users/100000 --user admin@gmail.com:admin`

#### Create user
`curl -v -X POST -d '{"name":"New","email":"new@gmail.com","enabled":true,"registered":"2019-09-11T12:01","roles":["ROLE_USER","ROLE_ADMIN"],"password":"newPass"}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/admin/users/ --user admin@gmail.com:admin`

#### Enable-disable user
`curl -v -X PATCH -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/admin/users/100000?enabled=false --user admin@gmail.com:admin`

### USER-MANAGER Profile

`curl -v -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile --user user@yandex.ru:password`


#### Register new user
`curl -v -X POST -d '{"name":"newName","email":"newemail@ya.ru","password":"newPassword","roles":["ROLE_USER"]}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile/register`

#### Register new user with role ADMIN - wrong
`curl -v -X POST -d '{"name":"newName","email":"newemail@ya.ru","password":"newPassword","roles":["ROLE_ADMIN"]}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile/register`

#### Update user
`curl -v -X PUT -d '{"name":"superName","email":"user@yandex.ru","password":"password","roles":["ROLE_USER"]}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile --user user@yandex.ru:password`

#### Delete user
`curl -v -X DELETE http://localhost:8080/restaurant/rest/profile  --user newemail@ya.ru:newPassword`

### RESTAURANT, only MANAGER
#### get restaurants by manager
`curl -v http://localhost:8080/restaurant/rest/manager/restaurant/manager/100002 --user manager@yandex.ru:manager`

#### get restaurants by ID
`curl -v http://localhost:8080/restaurant/rest/manager/restaurant/100009 --user manager@yandex.ru:manager`

#### Create restaurant
`curl -v -X POST -d '{"name":"new Restaurant","address":"New Adress , tel 111-111-111","owner":"New director","registered":"2019-09-12T10:50:19.234+0000","description":"new something","managerID":100002}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/manager/restaurant --user manager@yandex.ru:manager`

#### Update restaurant
`curl -v -X PUT -d '{"name":"Update Restaurant","address":"Update Adress , tel 111-111-111","owner":"Update director","registered":"2019-09-12T10:50:19.234+0000","description":"Update something","managerID":100002}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/manager/restaurant/100021 --user manager@yandex.ru:manager`

#### Delete restaurant by ID
`curl -v -X DELETE http://localhost:8080/restaurant/rest/manager/restaurant/100021 --user manager@yandex.ru:manager`


### RESTAURANT -  ANYBODY AUTHORIZED

#### get All Restaurants
`curl -v http://localhost:8080/restaurant/rest/profile/restaurant --user user@yandex.ru:password`

#### get restaurant by id 
`curl -v http://localhost:8080/restaurant/rest/profile/restaurant/100009 --user user@yandex.ru:password`

### MENU -  only MANAGER
#### get menuDetails by ID 100
`curl -v http://localhost:8080/restaurant/rest/manager/menu/100 --user manager@yandex.ru:manager`
#### Create new menuDetail
`curl -v -X POST -d '{"description":"Bread","dateTime":"2019-09-12T14:32:18.8358915","typeDish":"Five dish","quantity":"1 pec","price":0.10,"restaurantID":100009}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/manager/menu --user manager@yandex.ru:manager`

#### Update new menuDetail
`curl -v -X PUT -d '{"id": 128, "description":"Milk","dateTime":"2019-09-12T14:32:18.8358915","typeDish":"Five dish","quantity":"1 glass","price":0.50,"restaurantID":100009}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/manager/menu --user manager@yandex.ru:manager`

#### Update new menuDetail
`curl -v -X DELETE  http://localhost:8080/restaurant/rest/manager/menu/128 --user manager@yandex.ru:manager`

### MENU -  ANYBODY AUTHORIZED
#### get all menu on today
`curl -v http://localhost:8080/restaurant/rest/profile/menu/ --user user@yandex.ru:password`

#### get menu on today  by Restaurant  
`curl -v http://localhost:8080/restaurant/rest/profile/menu/restaurant/100009 --user user@yandex.ru:password`

### Vote , WARNING!!!! create and update vote may be Only before 11.00. You can change  time in class  ValidationUtil.MAX_TIME
#### get vote by ID 100013
`curl -v http://localhost:8080/restaurant/rest/profile/votes/100013 --user user@yandex.ru:password`

#### Create vote 
`curl -v -X POST -d '{"restaurantID":100009}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile/votes/ --user user_3@yandex.ru:password3`

#### Create vote by restaurant id  100011
`curl -v -X POST   -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile/votes/restaurant/100011 --user user_3@yandex.ru:password3`

#### Update vote by  id  100023
`curl -v -X PUT -d '{"restaurantID":100009}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile/votes/100023 --user user_3@yandex.ru:password3`

####
`curl -v -X DELETE  http://localhost:8080/restaurant/rest/profile/votes/100023 --user user_3@yandex.ru:password3`

### RATING
#### get rating today for restaurants
`curl -v http://localhost:8080/restaurant/rest/profile/rating/ --user user@yandex.ru:password`

####  get rating, filter by date, for restaurants
`curl -v http://localhost:8080/restaurant/rest/profile/rating/all?startDate=2019-09-14\&endDate=2019-09-14 --user user@yandex.ru:password`

####  get rating, all time
`curl -v http://localhost:8080/restaurant/rest/profile/rating/all --user user@yandex.ru:password`






