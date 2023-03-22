drop table task;

create TABLE task (
    id int AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) NOT NULL,
    assigned_points int NOT NULL,
    for_date date NOT NULL,
    created_time timestamp NOT NULL,
    update_time timestamp,
    status varchar(30) NOT NULL,
    evaluated_points int,
    email_id varchar(60) NOT NULL
);


