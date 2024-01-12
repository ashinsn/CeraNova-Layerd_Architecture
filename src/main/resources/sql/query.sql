create database ceranova;
use ceranova;

CREATE TABLE salary (
                        salaryID varchar(10) primary key,
                        dateOfIssue varchar(20),
                        amount varchar(30)
);

create table item(
                     itemID varchar(10) primary key,
                     itemType varchar(20),
                     itemSize varchar(20),
                     quantityOfItem int(100)
);


create table material(
                         materialID varchar(10) primary key,
                         materialName varchar(20),
                         materialType varchar(30),
                         materialQuantity varchar(30)
);


create table delivery(
                         deliveryID varchar(10) primary key,
                         deliveryDate varchar(20),
                         deliveryDescription varchar(50),
                         deliveryCost varchar(30)
);


CREATE TABLE employee (
                          employeeID            varchar(10) primary key,
                          employeeName          varchar(20),
                          employeeAddress       varchar(30),
                          employeeContactNumber varchar(30),
                          salaryID              varchar(10),
                          CONSTRAINT fk_employeeSalary FOREIGN KEY (salaryID) REFERENCES salary(salaryID)
);


CREATE TABLE user (
                      userID varchar(10) PRIMARY KEY,
                      userName varchar(20),
                      employeeID varchar(10),
                      FOREIGN KEY (employeeID) REFERENCES employee(employeeID)
);

CREATE TABLE orders (
                        orderID varchar(10) PRIMARY KEY,
                        orderDate varchar(20),
                        orderDescription varchar(50),
                        deliveryID varchar(10),
                        FOREIGN KEY (deliveryID) REFERENCES delivery(deliveryID)
);


CREATE TABLE customer (
                          customerID            varchar(10) PRIMARY KEY,
                          customerName          varchar(20),
                          customerAddress       varchar(30),
                          customerContactNumber varchar(30),
                          userID                varchar(10),
                          FOREIGN KEY (userID) REFERENCES user (userID)
);


CREATE TABLE customer_order (
                                orderStatus varchar(50),
                                customerID varchar(10),
                                orderID varchar(10),
                                FOREIGN KEY (customerID) REFERENCES customer(customerID),
                                FOREIGN KEY (orderID) REFERENCES orders(orderID)
);


CREATE TABLE supplier (
                          supplierID varchar(10) PRIMARY KEY,
                          supplierName varchar(20),
                          supplierAddress varchar(30),
                          supplierContactNumber varchar(30),
                          userID varchar(10),
                          FOREIGN KEY (userID) REFERENCES `user`(userID)
);



CREATE TABLE payment (
                         paymentID varchar(10) primary key,
                         paymentDate varchar(20),
                         paymentMethod varchar(20),
                         paymentCost varchar(30),
                         paymentTime varchar(20),
                         orderID varchar(10),
                         itemID varchar(10),
                         customerID varchar(10),
                         FOREIGN KEY (orderID) REFERENCES orders(orderID),
                         FOREIGN KEY (itemID) REFERENCES item(itemID),
                         FOREIGN KEY (customerID) REFERENCES customer(customerID)
);

CREATE TABLE order_item (
                            availability varchar(50),
                            orderID varchar(10),
                            itemID varchar(10),
                            FOREIGN KEY (orderID) REFERENCES orders(orderID),
                            FOREIGN KEY (itemID) REFERENCES item(itemID)
);

CREATE TABLE supplier_material (
                                   stockDetails varchar(50),
                                   supplierID varchar(10),
                                   materialID varchar(10),
                                   FOREIGN KEY (supplierID) REFERENCES supplier(supplierID),
                                   FOREIGN KEY (materialID) REFERENCES material(materialID)
);

ALTER TABLE delivery
    ADD COLUMN orderID varchar(10),
ADD COLUMN customerID varchar(10);

ALTER TABLE delivery
    ADD CONSTRAINT fk_orderID
        FOREIGN KEY (orderID) REFERENCES orders(orderID);

ALTER TABLE delivery
    ADD CONSTRAINT fk_customerID
        FOREIGN KEY (customerID) REFERENCES customer(customerID);

ALTER TABLE salary
    ADD CONSTRAINT fk_employeeID
        FOREIGN KEY (employeeID) REFERENCES employee(employeeID);

