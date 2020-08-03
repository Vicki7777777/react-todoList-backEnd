create table company(
    company_id INT AUTO_INCREMENT,
    company_name VARCHAR(255),
    PRIMARY KEY (company_id)
);

create table employee(
    id INT AUTO_INCREMENT,
    name VARCHAR(255),
    gender VARCHAR(255),
    age INT,
    salary INT,
    company_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (company_id) REFERENCES company(company_id)
);