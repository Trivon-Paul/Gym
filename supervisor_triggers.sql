
DELIMITER //

-- Supervisor After Insert Trigger
CREATE TRIGGER supervisor_after_insert
AFTER INSERT ON Supervisor
FOR EACH ROW
BEGIN
    -- Inserting into SupervisorAudit table with details of the operation
    INSERT INTO SupervisorAudit (Timestamp, Supervisor_ID, First_Name, Last_Name, Action)
    VALUES (CURRENT_TIMESTAMP, NEW.Supervisor_ID, NEW.First_Name, NEW.Last_Name, 'INSERT');
END; 
//

-- Supervisor After Delete Trigger
CREATE TRIGGER supervisor_after_delete
AFTER DELETE ON Supervisor
FOR EACH ROW
BEGIN
    -- Inserting into SupervisorAudit table with details of the operation
    INSERT INTO SupervisorAudit (Timestamp, Supervisor_ID, First_Name, Last_Name, Action)
    VALUES (CURRENT_TIMESTAMP, OLD.Supervisor_ID, OLD.First_Name, OLD.Last_Name, 'DELETE');
END; 
//
DELIMITER ;
