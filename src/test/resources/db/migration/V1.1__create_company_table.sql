--create table company(
--    companyId INT AUTO_INCREMENT,
--    companyName VARCHAR(255),
--    PRIMARY KEY (companyId)
--);

create table employee(
    id INT AUTO_INCREMENT,
    name VARCHAR(255),
    gender VARCHAR(255),
    age INT,
    salary INT,
    company_Id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (company_Id) REFERENCES company(company_Id)
);