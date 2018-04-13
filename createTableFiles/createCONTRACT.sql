CREATE TABLE CONTRACT (
ContID integer NOT NULL,
CusID integer NOT NULL,
MID integer NOT NULL,
DID integer NOT NULL,
Invoice varchar2(32),
Timestamp varchar2(16),
PRIMARY KEY (ContID)
);
