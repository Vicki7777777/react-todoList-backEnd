create table company(
    company_Id INT AUTO_INCREMENT,
    company_Name VARCHAR(255),
    PRIMARY KEY (company_Id)
);



--create table employee(
--    id INT AUTO_INCREMENT,
--    name VARCHAR(255),
--    gender VARCHAR(255),
--    age INT,
--    salary INT,
--    companyId INT,
--    PRIMARY KEY (id),
--    FOREIGN KEY (companyId) REFERENCES company(companyId)
--);