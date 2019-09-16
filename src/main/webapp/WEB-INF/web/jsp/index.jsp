<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Graduation Java Enterprise (Topjava) - Restaurant rating</title>
</head>
<body>
<hr>
<h3>Project <a href="https://github.com/andrey-k-qwerty/restorans" target="_blank">Restaurant rating</a></h3>
<hr>
<h4>Task:</h4>
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.<br>
The task is:<br>
Build a voting system for deciding where to have lunch.<br>
2 types of users: admin and regular users<br>
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)<br>
Menu changes each day (admins do the updates)<br>
Users can vote on which restaurant they want to have lunch at<br>
Only one vote counted per user<br>
If user votes again the same day:<br>
If it is before 11:00 we asume that he changed his mind.<br>
If it is after 11:00 then it is too late, vote can't be changed<br>
Each restaurant provides new menu each day.<br>

As a result, provide a link to github repository.<br>

It should contain the code and README.md with API documentation and curl commands to get data for voting and vote.<br>
<hr>
<h3>REST API , examples</h3>
<h4>ADMIN - Users</h4>
<p>get All Users </p>
`curl -v http://localhost:8080/restaurant/rest/admin/users --user admin@gmail.com:admin`<br>

<p>get Users by ID 100001</p>
`curl -v http://localhost:8080/restaurant/rest/admin/users/100001 --user admin@gmail.com:admin`

<p> get not found </p>
`curl -v http://localhost:8080/restaurant/rest/admin/users/1 --user admin@gmail.com:admin`

<p>  Get user by email  </p>
`curl -v http://localhost:8080/restaurant/rest/admin/users/by?email=user@yandex.ru --user admin@gmail.com:admin`

<p> Delete user 100005 </p>
`curl -v -X DELETE http://localhost:8080/restaurant/rest/admin/users/100005  --user admin@gmail.com:admin`

<p> Delete user, double delete - delete not found </p>
`curl -v -X DELETE http://localhost:8080/restaurant/rest/admin/users/100005  --user admin@gmail.com:admin`

<p> Unauthorized </p>
`curl -v http://localhost:8080/restaurant/rest/admin/users`

<p> Forbidden </p>
`curl -v http://localhost:8080/restaurant/rest/admin/users --user manager@yandex.ru:manager`

<p> Update user </p>
`curl -v -X PUT -d '{ "id":"100000","name":"UpdatedName","email":"user@yandex.ru","enabled":true,"registered":"2019-09-11T11:39","roles":["ROLE_ADMIN"],"password":"password"}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/admin/users/100000 --user admin@gmail.com:admin`

<p> Create user </p>
`curl -v -X POST -d '{"name":"New","email":"new@gmail.com","enabled":true,"registered":"2019-09-11T12:01","roles":["ROLE_USER","ROLE_ADMIN"],"password":"newPass"}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/admin/users/ --user admin@gmail.com:admin`

<p> Enable-disable user </p>
`curl -v -X PATCH -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/admin/users/100000?enabled=false --user admin@gmail.com:admin`

<h4> USER-MANAGER Profile</h4>

`curl -v -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile --user user@yandex.ru:password`


<p> Register new user </p>
`curl -v -X POST -d '{"name":"newName","email":"newemail@ya.ru","password":"newPassword","roles":["ROLE_USER"]}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile/register`

<p> Register new user with role ADMIN - wrong </p>
`curl -v -X POST -d '{"name":"newName","email":"newemail@ya.ru","password":"newPassword","roles":["ROLE_ADMIN"]}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile/register`

<p> Update user </p>
`curl -v -X PUT -d '{"name":"superName","email":"user@yandex.ru","password":"password","roles":["ROLE_USER"]}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile --user user@yandex.ru:password`

<p> Delete user </p>
`curl -v -X DELETE http://localhost:8080/restaurant/rest/profile  --user newemail@ya.ru:newPassword`

<h4> RESTAURANT, only manager </h4>
<p> get restaurants by manager </p>
`curl -v http://localhost:8080/restaurant/rest/manager/restaurant/manager/100002 --user manager@yandex.ru:manager`

<p> get restaurants by ID </p>
`curl -v http://localhost:8080/restaurant/rest/manager/restaurant/100009 --user manager@yandex.ru:manager`

<p> Create restaurant </p>
`curl -v -X POST -d '{"name":"new Restaurant","address":"New Adress , tel 111-111-111","owner":"New director","registered":"2019-09-12T10:50:19.234+0000","description":"new something","managerID":100002}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/manager/restaurant --user manager@yandex.ru:manager`

<p> Update restaurant </p>
`curl -v -X PUT -d '{"name":"Update Restaurant","address":"Update Adress , tel 111-111-111","owner":"Update director","registered":"2019-09-12T10:50:19.234+0000","description":"Update something","managerID":100002}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/manager/restaurant/100021 --user manager@yandex.ru:manager`

<p> Delete restaurant by ID </p>
`curl -v -X DELETE http://localhost:8080/restaurant/rest/manager/restaurant/100021 --user manager@yandex.ru:manager`


<h4> RESTAURANT -  anybody authorized</h4>

<p> get All Restaurants </p>
`curl -v http://localhost:8080/restaurant/rest/profile/restaurant --user user@yandex.ru:password`

<p> get restaurant by id </p>
`curl -v http://localhost:8080/restaurant/rest/profile/restaurant/100009 --user user@yandex.ru:password`

<h4> MENU -  only MANAGER </h4>
<p> get menuDetails by ID 100 </p>
`curl -v http://localhost:8080/restaurant/rest/manager/menu/100 --user manager@yandex.ru:manager`
<p> Create new menuDetail </p>
`curl -v -X POST -d '{"description":"Bread","dateTime":"2019-09-12T14:32:18.8358915","typeDish":"Five dish","quantity":"1 pec","price":0.10,"restaurantID":100009}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/manager/menu --user manager@yandex.ru:manager`

<p> Update new menuDetail </p>
`curl -v -X PUT -d '{"id": 128, "description":"Milk","dateTime":"2019-09-12T14:32:18.8358915","typeDish":"Five dish","quantity":"1 glass","price":0.50,"restaurantID":100009}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/manager/menu --user manager@yandex.ru:manager`

<p> Update new menuDetail </p>
`curl -v -X DELETE  http://localhost:8080/restaurant/rest/manager/menu/128 --user manager@yandex.ru:manager`

<h4> MENU -  anybody authorized </h4>
<p> get all menu on today </p>
`curl -v http://localhost:8080/restaurant/rest/profile/menu/ --user user@yandex.ru:password`

<p> get menu on today  by Restaurant </p>
`curl -v http://localhost:8080/restaurant/rest/profile/menu/restaurant/100009 --user user@yandex.ru:password`

<h4> Vote , WARNING!!!! create and update vote may be Only before 11.00. You can change  time in class  ValidationUtil.MAX_TIME</h4>
<p> get vote by ID 100013 </p>
`curl -v http://localhost:8080/restaurant/rest/profile/votes/100013 --user user@yandex.ru:password`

<p> Create vote </p>
`curl -v -X POST -d '{"restaurantID":100009}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile/votes/ --user user_3@yandex.ru:password3`

<p> Create vote by restaurant id  100011 </p>
`curl -v -X POST   -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile/votes/restaurant/100011 --user user_3@yandex.ru:password3`

<p> Update vote by  id  100023 </p>
`curl -v -X PUT -d '{"restaurantID":100009}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile/votes/100023 --user user_3@yandex.ru:password3`

<p>Delete vote by id  </p>
`curl -v -X DELETE  http://localhost:8080/restaurant/rest/profile/votes/100023 --user user_3@yandex.ru:password3`

<h4> RATING</h4>
<p> get rating today for restaurants </p>
`curl -v http://localhost:8080/restaurant/rest/profile/rating/ --user user@yandex.ru:password`

<p>  get rating, filter by date, for restaurants </p>
`curl -v http://localhost:8080/restaurant/rest/profile/rating/all?startDate=2019-09-14\&endDate=2019-09-14 --user user@yandex.ru:password`

<p>  get rating, all time </p>
`curl -v http://localhost:8080/restaurant/rest/profile/rating/all --user user@yandex.ru:password`
</body>
</html>
