
CREATE TABLE IF NOT EXISTS SupervisorAudit (
    Audit_ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Timestamp TIMESTAMP,
    Supervisor_ID INTEGER,
    First_Name TEXT,
    Last_Name TEXT,
    Action TEXT,
    FOREIGN KEY (Supervisor_ID) REFERENCES Supervisor(Supervisor_ID)
);
