# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table difficulty (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  value                         integer,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  version                       bigint not null,
  constraint pk_difficulty primary key (id)
);

create table ingredient (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  version                       bigint not null,
  constraint pk_ingredient primary key (id)
);

create table ingredient_quantity (
  id                            bigint auto_increment not null,
  ingredient_id                 bigint,
  quantity                      double,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  version                       bigint not null,
  constraint pk_ingredient_quantity primary key (id)
);

create table rating (
  id                            bigint auto_increment not null,
  value                         double,
  comment                       varchar(255),
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  version                       bigint not null,
  constraint pk_rating primary key (id)
);

create table recipe (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  steps_description             varchar(255),
  rating_id                     bigint,
  difficulty_id                 bigint,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  version                       bigint not null,
  constraint uq_recipe_rating_id unique (rating_id),
  constraint pk_recipe primary key (id)
);

create table recipe_ingredient_quantity (
  recipe_id                     bigint not null,
  ingredient_quantity_id        bigint not null,
  constraint pk_recipe_ingredient_quantity primary key (recipe_id,ingredient_quantity_id)
);

create index ix_ingredient_quantity_ingredient_id on ingredient_quantity (ingredient_id);
alter table ingredient_quantity add constraint fk_ingredient_quantity_ingredient_id foreign key (ingredient_id) references ingredient (id) on delete restrict on update restrict;

alter table recipe add constraint fk_recipe_rating_id foreign key (rating_id) references rating (id) on delete restrict on update restrict;

create index ix_recipe_difficulty_id on recipe (difficulty_id);
alter table recipe add constraint fk_recipe_difficulty_id foreign key (difficulty_id) references difficulty (id) on delete restrict on update restrict;

create index ix_recipe_ingredient_quantity_recipe on recipe_ingredient_quantity (recipe_id);
alter table recipe_ingredient_quantity add constraint fk_recipe_ingredient_quantity_recipe foreign key (recipe_id) references recipe (id) on delete restrict on update restrict;

create index ix_recipe_ingredient_quantity_ingredient_quantity on recipe_ingredient_quantity (ingredient_quantity_id);
alter table recipe_ingredient_quantity add constraint fk_recipe_ingredient_quantity_ingredient_quantity foreign key (ingredient_quantity_id) references ingredient_quantity (id) on delete restrict on update restrict;


# --- !Downs

alter table ingredient_quantity drop foreign key fk_ingredient_quantity_ingredient_id;
drop index ix_ingredient_quantity_ingredient_id on ingredient_quantity;

alter table recipe drop foreign key fk_recipe_rating_id;

alter table recipe drop foreign key fk_recipe_difficulty_id;
drop index ix_recipe_difficulty_id on recipe;

alter table recipe_ingredient_quantity drop foreign key fk_recipe_ingredient_quantity_recipe;
drop index ix_recipe_ingredient_quantity_recipe on recipe_ingredient_quantity;

alter table recipe_ingredient_quantity drop foreign key fk_recipe_ingredient_quantity_ingredient_quantity;
drop index ix_recipe_ingredient_quantity_ingredient_quantity on recipe_ingredient_quantity;

drop table if exists difficulty;

drop table if exists ingredient;

drop table if exists ingredient_quantity;

drop table if exists rating;

drop table if exists recipe;

drop table if exists recipe_ingredient_quantity;

