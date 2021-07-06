DROP DATABASE IF EXISTS Map;
CREATE DATABASE Map;
CREATE USER Student IDENTIFIED BY 'map';
GRANT SELECT ON Map.* TO Student;
USE Map;
CREATE TABLE MAP.playtennisTarget(
       outlook varchar(10),
       temperature float(5,2),
       umidity varchar(10),
       wind varchar(10),
       play varchar(10)
);
CREATE TABLE MAP.playtennisBackground(
       outlook varchar(10),
       temperature float(5,2),
       umidity varchar(10),
       wind varchar(10),
       play varchar(10)
);

insert into Map.playtennisTarget values('sunny',30.3,'high','weak','no');
insert into Map.playtennisTarget values('sunny',30.3,'high','strong','no');
insert into Map.playtennisTarget values('overcast',30.0,'high','weak','yes');
insert into Map.playtennisTarget values('rain',13.0,'high','weak','yes');
insert into Map.playtennisTarget values('rain',0.0,'normal','weak','yes');
insert into Map.playtennisTarget values('rain',0.0,'normal','strong','no');
insert into Map.playtennisTarget values('overcast',0.1,'normal','strong','yes');
insert into Map.playtennisTarget values('sunny',13.0,'high','weak','no');
insert into Map.playtennisBackground values('sunny',0.1,'normal','weak','yes');
insert into Map.playtennisBackground values('rain',12.0,'normal','weak','yes');
insert into Map.playtennisBackground values('sunny',12.5,'normal','strong','yes');
insert into Map.playtennisBackground values('overcast',12.5,'high','strong','yes');
insert into Map.playtennisBackground values('overcast',29.21,'normal','weak','yes');
insert into Map.playtennisBackground values('rain',12.5,'high','strong','no');
