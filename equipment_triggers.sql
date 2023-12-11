
DELIMITER //

-- Equipment After Insert Trigger
CREATE TRIGGER equipment_after_insert
AFTER INSERT ON Equipment
FOR EACH ROW
BEGIN
    -- Inserting into EquipmentAudit table with details of the operation
    INSERT INTO EquipmentAudit (Timestamp, Equipment_ID, Cost, Action)
    VALUES (CURRENT_TIMESTAMP, NEW.Equipment_ID, NEW.Cost, 'INSERT');
END; 
//

-- Equipment After Delete Trigger
CREATE TRIGGER equipment_after_delete
AFTER DELETE ON Equipment
FOR EACH ROW
BEGIN
    -- Inserting into EquipmentAudit table with details of the operation
    INSERT INTO EquipmentAudit (Timestamp, Equipment_ID, Cost, Action)
    VALUES (CURRENT_TIMESTAMP, OLD.Equipment_ID, OLD.Cost, 'DELETE');
END; 
//
DELIMITER ;
