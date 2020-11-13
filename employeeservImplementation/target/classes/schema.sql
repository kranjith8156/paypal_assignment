CREATE TABLE address 
( 
addressId INT PRIMARY KEY AUTO_INCREMENT,
line1 VARCHAR(255), 
line2 VARCHAR(255), 
city VARCHAR(255),
state VARCHAR(255), 
country VARCHAR(255),
zip_code INT
);

CREATE TABLE employee 
( id INT PRIMARY KEY AUTO_INCREMENT, 
first_name VARCHAR(255), 
last_name VARCHAR(255),
dob VARCHAR(50),
addressId INT,
FOREIGN KEY (addressId) REFERENCES address(addressId)
);