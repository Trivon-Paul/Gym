
CREATE TABLE IF NOT EXISTS EquipmentAudit (
    Audit_ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Timestamp TIMESTAMP,
    Equipment_ID INTEGER,
    Cost TEXT,
    Action TEXT,
    FOREIGN KEY (Equipment_ID) REFERENCES Equipment(Equipment_ID)
);
