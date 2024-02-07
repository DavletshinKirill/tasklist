
insert into users (name, username, password)
values ('John Doe', 'johndoe@gmail.com', '$2a$10$H0YUJXfiPTmjvQ8hLawbPu87p3rJzTyk6oAkU6Ek9kOK4A9Q356vG'),
       ('Mike Smith', 'mikesmith@yahoo.com', '$2a$10$D/1fyxfjJxBrImrThPbDVuF/.jL1ybNSEA76lRJwXkzONGvl1E1zm');

insert into tasks (title, description, status, expirationData)
values ('Buy cheese', null, 'TODO', '2023-01-29 12:00:00'),
       ('Do homework', 'Math, Physics, Literature', 'IN_PROGRESS', '2023-01-31 00:00:00'),
       ('Clean rooms', null, 'DONE', null),
       ('Call Mike', 'Ask about meeting', 'TODO', '2023-02-01 00:00:00');


insert into user_tasks (task_id, user_id)
values (1, 2),
       (2, 2),
       (3, 2),
       (4, 1);


insert into users_roles (user_id, role)
values (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');