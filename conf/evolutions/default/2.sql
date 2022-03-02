# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

insert into difficulty (name, value, when_created, when_modified, version) 
values 
  ('Muy fácil', 1, NOW(), NOW(), 1),
  ('Fácil', 2, NOW(), NOW(), 1),
  ('Medio', 3, NOW(), NOW(), 1),
  ('Difícil', 4, NOW(), NOW(), 1),
  ('Muy difícil', 5, NOW(), NOW(), 1);
  

# --- !Downs

delete from difficulty;

