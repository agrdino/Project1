create database whatEver;
use whatEver;
drop database whatever;
create table user
(
userID int primary key auto_increment,
name varchar(50),
mobileNumber int unique,
admin bool
);

create table food
(
foodID int primary key auto_increment,
name varchar(50) unique,
price int
);

create table restaurant
(
resID int primary key auto_increment,
name varchar(50),
address varchar(255),
openHour long,
closeHour long
);

create table menu
(
nio int auto_increment primary key,
foodID int,
resID int
);
alter table menu add constraint fk_menu_foodID foreign key (foodID) references food(foodID);
alter table menu add constraint fk_menu_resID foreign key (resID) references restaurant(resID);

create table suggestion
(
sgID int primary key auto_increment,
userID int,
foodID int,
createTime long
);
alter table suggestion add constraint fk_suggestion_foodID foreign key (foodID) references food(foodID);
alter table suggestion add constraint fk_suggestion_userID foreign key (userID) references user(userID);

create table review
(
reviewID int primary key auto_increment,
sgID int,
userID int,
foodID int, 
resID int,
createTime long,
rate int,
recommend boolean,
revieww varchar(255)
);
alter table review add constraint fk_review_userID foreign key (userID) references user(userID);
alter table review add constraint fk_review_resID foreign key (resID) references restaurant(resID);
alter table review add constraint fk_review_foodID foreign key (foodID) references food(foodID);
alter table review add constraint fk_review_sgID foreign key (sgID) references suggestion(sgID);

create table reviewCheck
(
rcID int primary key auto_increment,
sgID int,
userID int,
foodID int, 
resID int,
createTime long,
rate int,
recommend boolean,
revieww varchar(255)
);
alter table reviewCheck add constraint fk_reviewCheck_userID foreign key (userID) references user(userID);
alter table reviewCheck add constraint fk_reviewCheck_resID foreign key (resID) references restaurant(resID);
alter table reviewCheck add constraint fk_reviewCheck_foodID foreign key (foodID) references food(foodID);
alter table reviewCheck add constraint fk_reviewCheck_sgID foreign key (sgID) references suggestion(sgID);

create table suggestionOffical
(
sgo int primary key auto_increment,
sgID int,
resID int
);
alter table suggestionOffical add constraint fk_sgo_sgID foreign key (sgID) references suggestion(sgID);
alter table suggestionOffical add constraint fk_sgo_resID foreign key (resID) references restaurant(resID);

-- food
insert into food(name, price) values ('Com suon', 30000), ('Bun thit nuong', 30000);
insert into food(name, price) values ('Banh xeo', 5000), ('Bun dau mam tom', 50000);

-- user
insert into user(name, mobileNumber, admin) values ('admin', 1, true), ('user', 0, false);
select * from user;

-- suggestion
insert into suggestion(userID, foodID, createTime) value(1,1,1);
insert into suggestionOffical(sgID, resID) value (1,1);

-- restaurant
insert into restaurant(name, address, openHour, closeHour) value ("test res", "test address", 1,2);
insert into restaurant(name, address, openHour, closeHour) value ("test res1", "test address1",1,2);

-- menu
insert into menu(foodID, resID) values (1,1), (2,1), (1,2), (4,2);

select * from food;
select * from reviewCheck;
select * from suggestion;
select * from restaurant;
select * from menu;

delete from menu where nio = 15;

-- test
select name, address, openHour, closeHour from restaurant inner join menu using (resID) where foodID = 1;
select resID, name, address from restaurant where name = "test res1";
select hour(now());
select * from restaurant inner join menu using (resID) where menu.foodID = 1 and restaurant.openHour <= hour(now()) and restaurant.closeHour >= hour(now());

select u.name, rw.revieww, rw.rate, rw.recommend, rw.createTime from user u, reviewCheck rw 
where u.userID = 2 and rw.resID= 1 and rw.foodID  = 1;
select rw.rcID, u.name, f.name, rw.resID, res.name as resname, rw.revieww, rw.rate, rw.recommend, rw.createTime
                        from user u inner join reviewCheck rw inner join food f inner join restaurant res 
                         where u.userID = rw.userID
                        and rw.foodID = f.foodID
                        and rw.resID = res.resID;
                        
select * from reviewCheck;
select * from reviewCheck;
insert into reviewCheck (sgID, userID, foodID, resID, revieww) value (2,2,4,2,"test again2");
select * from review;
select * from reviewCheck;

select * from suggestionOffical;

-- back up
-- add food
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddFood`(in foodName varchar(50), in foodPrice int)
BEGIN
	declare result boolean default false;
	if((select name from food where food.name = `foodName`) is null)
    then
		insert into food(name, price) value (`foodName`, `foodPrice`);
    end if;
END

-- add menu
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddMenu`(in foodID int, in resID int)
BEGIN
	if ((select mn.resID from menu mn where mn.foodID = `foodID`) is not null
    and (select mn.resID from menu mn where mn.foodID = `foodID`) = `resID`)
    then
		select * from menu where menu.foodID = `foodID`;
	else
		insert into menu(resID, foodID) value(`resID`, `foodID`);
	end if;
END

-- add restaurant
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddRestaurant`(in name varchar(50), in address varchar(255), in openHour long, in closeHour long)
BEGIN
	if ((select res.address from restaurant res where res.name = `name`) is not null)
    and ((select res.address from restaurant res where res.name = `name`) = `address`)
    then
		select * from restaurant where restaurant.name = `name`;
    else
		insert into restaurant(name, address, openHour, closeHour) value (`name`, `address`, `openHour`, `closeHour`);
	end if;
END


-- add sg official
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddSuggestionOffical`(in sgID int, in resID int)
BEGIN
	insert into suggestionOffical(sgID, resID) value(`sgID`, `resID`);
END


-- create review
CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateReview`(in sgID int, in review varchar(255), in createTime long, in rate int, in recommend boolean)
BEGIN
	insert into ReviewCheck(sgID, userID, resID, foodID, createTime, rate, recommend, revieww)
    value
    (
    `sgID`,
    (select userID from suggestion where suggestion.sgID = `sgID`),
    (select resID from suggestionOffical where suggestionOffical.sgID = `sgID`),
    (select foodID from suggestion where suggestion.sgID = `sgID`),
    `createTime`,
    `rate`,
    `recommend`,
    `review`
    );
END


-- food count
CREATE DEFINER=`root`@`localhost` PROCEDURE `FoodCount`(out total int)
BEGIN
	set total = (select count(*) from food);
END


-- get food
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetFood`(in ID int, out foodName varchar(50), out foodPrice int)
BEGIN
	set `foodName` = (select name from food where food.foodID = `ID`);
    set `foodPrice` = (select price from food where food.foodID = `ID`);
END


-- get menu
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetMenu`(in foodID int, in resID int, out foodName varchar(50), out resName varchar(50))
BEGIN
	set `foodName` = (select name from food where food.foodID = `foodID`);
    set `resName` = (select name from restaurant where restaurant.resID = `resID`);
END


-- get restaurant
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetRestaurant`(in name varchar(50), out resID int, out resName varchar(50), out address varchar(255), out openHour long, out closeHour long)
BEGIN
	set `resID` = (select res.resID from restaurant res where res.name = `name` and res.openHour <= hour(now()) and res.closeHour >= hour(now()));
	set `resName` = (select res.name from restaurant res where res.name = `name` and res.openHour <= hour(now()) and res.closeHour >= hour(now()));
	set `address` = (select res.address from restaurant res where res.name = `name` and res.openHour <= hour(now()) and res.closeHour >= hour(now()));
	set `openHour` = (select res.openHour from restaurant res where res.name = `name` and res.openHour <= hour(now()) and res.closeHour >= hour(now()));
	set `closeHour` = (select res.closeHour from restaurant res where res.name = `name` and res.openHour <= hour(now()) and res.closeHour >= hour(now()));
END


-- get review
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetReview`(in rcID int)
BEGIN
	select * from reviewCheck where reviewCheck.rcID = `rcID`;
END