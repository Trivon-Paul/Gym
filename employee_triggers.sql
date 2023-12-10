DELIMITER //

CREATE TRIGGER employee_before_delete
BEFORE DELETE ON employees
FOR EACH ROW
BEGIN
    -- Your trigger logic here.
    -- You can access the values being deleted using OLD.column_name

    -- Find the employee with the lowest number of Members_Representing
    DECLARE min_representing INT;
    DECLARE new_sponsor_id INT;

    SELECT MIN(Members_Representing) INTO min_representing
    FROM Employees
    WHERE Employee_ID <> OLD.Employee_ID
    LIMIT 1;

    SELECT Employee_ID INTO new_sponsor_id
    FROM Employees
    WHERE Employee_ID <> OLD.Employee_ID AND Members_Representing = min_representing
    LIMIT 1;
    
	UPDATE equipment
    SET Employee_Owner = NULL
    WHERE Employee_Owner = OLD.Employee_ID;
    
    -- Update Employee_Sponsor in members table
	UPDATE members
	SET Employee_Sponsor = NULL
	WHERE Employee_Sponsor = OLD.Employee_ID;

    -- Set variables to be used in AFTER DELETE trigger
    SET @new_sponsor_id := new_sponsor_id;
    SET @old_employee_id := OLD.Employee_ID;
END;
//

CREATE TRIGGER employee_after_delete
AFTER DELETE ON employees
FOR EACH ROW
BEGIN
    -- Update Members table with the new employee sponsor
    UPDATE Members
    SET Employee_Sponsor = @new_sponsor_id
    WHERE Employee_Sponsor = @old_employee_id;

    -- Increment Members_Representing for the new employee sponsor
	-- UPDATE Employees
    -- SET Members_Representing = Members_Representing + 1
    -- WHERE Employee_ID = @new_sponsor_id;

     -- Update Equipments table to reassign Employee_Owner
     UPDATE equipment
     SET Employee_Owner = @new_sponsor_id
     WHERE Employee_Owner = NULL;

    -- For example, you might log the deletion in an audit table:
    INSERT INTO employeeaudit
    (Timestamp, ChangeType, Employee_ID, FirstName, LastName, HireDate, Members, Supervisor)
    VALUES
    (CURRENT_TIMESTAMP, 'DELETE', NULL, OLD.First_name, OLD.Last_name, STR_TO_DATE(OLD.Hire_date, '%m/%d/%Y'), OLD.Members_Representing, OLD.Supervisor);

END;
//

DELIMITER ;
