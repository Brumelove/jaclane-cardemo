create table car
(
    id bigint auto_increment primary key,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description varchar(255) not null,
    engineNo varchar(255) not null,
    lastUpdatedAt TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name varchar(255) not null,
    yearOfMake bigint not null
);


create table  category
(
    id bigint auto_increment
        primary key,
    categoryId varchar(255) not null,
    categoryName varchar(255) not null,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


create table image
(
    id bigint auto_increment
        primary key,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    imageLocation varchar(255) not null,
    imageName varchar(255) not null
);

create table  tag
(
    id bigint auto_increment
        primary key,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tagId varchar(255) not null,
    tagName varchar(255) not null
);


create table  car_category
(
    car_id bigint not null references car(id),
    categoryEntityList_id bigint not null references category(id)
);


create table  car_image
(
    car_id bigint not null references car(id),
    imageEntityList_id bigint not null references image(id)
);



create table  car_tag
(
    car_id bigint not null references car(id),
    tagEntityList_id bigint not null references tag(id)
);






