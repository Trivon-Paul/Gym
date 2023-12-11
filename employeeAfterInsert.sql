DELIMITER //

CREATE TRIGGER employee_after_insert
AFTER INSERT ON employees
FOR EACH ROW
BEGIN
    -- Your trigger logic here.
    -- You can access the values being inserted using NEW.column_name
    
    -- For example, you might log the insertion in an audit table:
    INSERT INTO employeeaudit
    (Timestamp, ChangeType, Employee_ID, FirstName, LastName, HireDate, Members, Supervisor)
    VALUES
    (CURRENT_TIMESTAMP, 'INSERT', NEW.Employee_ID, NEW.First_name, NEW.Last_name, STR_TO_DATE(New.Hire_Date, '%m/%d/%Y'), NEW.Members_Representing, NEW.Supervisor);

END;
//

DELIMITER ;
