CREATE TABLE test_user (
  id varchar(50),
  data jsonb,
  type varchar(10)
);

CREATE TABLE test_names (
  id varchar(50),
  first_name varchar(50),
  middle_name varchar(50),
  last_name varchar(50),
  dob date
);

insert into test_user (id, data, type) values ('abc56384', '{"firstName" : "test_name"}', 'BASIC');

select count(*) from test_user;
--drop table application
--docker run --name concepts-postrgres-docker-v -e POSTGRES_PASSWORD=mysecretpassword postgres -v /Users/jovaughnlockridge1/dockerVolumes/postgresVolumeConcept/:/var/lib/postgresql/data



