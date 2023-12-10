-- Create the BEFORE DELETE trigger for the members table
DELIMITER //
CREATE TRIGGER member_before_delete
BEFORE DELETE ON members
FOR EACH ROW
BEGIN
    -- Your trigger logic here.
    -- You can access the values being deleted using OLD.column_name

    -- For example, you might log the deletion in the memberaudit table:
    INSERT INTO memberaudit (Timestamp, Member_ID, First_Name, Last_Name, ChangeType)
    VALUES (CURRENT_TIMESTAMP, OLD.Member_ID, OLD.First_Name, OLD.Last_Name, 'DELETE');
END;

CREATE TRIGGER member_after_insert
AFTER INSERT ON members
FOR EACH ROW
BEGIN
    -- Your trigger logic here.
    -- You can access the values being inserted using NEW.column_name

    -- Increment Members_Representing for the associated employee
    UPDATE employees
    SET Members_Representing = Members_Representing + 1
    WHERE Employee_ID = NEW.Employee_Sponsor;

    -- Log the insertion in the memberaudit table:
    INSERT INTO memberaudit (Timestamp, Member_ID, First_Name, Last_Name, ChangeType)
    VALUES (CURRENT_TIMESTAMP, NEW.Member_ID, NEW.First_Name, NEW.Last_Name, 'INSERT');
END;
//
DELIMITER ;