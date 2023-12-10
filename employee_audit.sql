CREATE TABLE `employeeaudit` (
  `Audit_ID` int NOT NULL AUTO_INCREMENT,
  `Timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `ChangeType` varchar(10) NOT NULL,
  `Employee_ID` int DEFAULT NULL,
  `FirstName` varchar(255) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `HireDate` date DEFAULT NULL,
  `Members` int DEFAULT NULL,
  `Supervisor` int DEFAULT NULL,
  PRIMARY KEY (`Audit_ID`),
  KEY `Employee_ID` (`Employee_ID`),
  CONSTRAINT `employeeaudit_ibfk_1` FOREIGN KEY (`Employee_ID`) REFERENCES `employees` (`Employee_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
