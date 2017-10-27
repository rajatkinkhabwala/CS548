CREATE TABLE Patient (ID BIGINT NOT NULL, AGE INTEGER, BIRTHDATE DATE, NAME VARCHAR(255), PATIENTID BIGINT, PRIMARY KEY (ID))
CREATE TABLE Treatment (TREATMENTID BIGINT NOT NULL, TREATMENT_TYPE VARCHAR(31), DIAGNOSIS VARCHAR(255), patient_fk BIGINT, providers_fk BIGINT, PRIMARY KEY (TREATMENTID))
CREATE TABLE DrugTreatment (TREATMENTID BIGINT NOT NULL, DOSAGE FLOAT, DRUG VARCHAR(255), PRIMARY KEY (TREATMENTID))
CREATE TABLE Surgery (TREATMENTID BIGINT NOT NULL, DATE DATE, PRIMARY KEY (TREATMENTID))
CREATE TABLE Radiology (TREATMENTID BIGINT NOT NULL, PRIMARY KEY (TREATMENTID))
CREATE TABLE Provider (ID BIGINT NOT NULL, NAME VARCHAR(255), NPI BIGINT, SPECIALIZATION VARCHAR(255), PRIMARY KEY (ID))
CREATE TABLE Radiology_date (DATE VARCHAR(255), RadiologyTreatmentid_fk BIGINT)
ALTER TABLE Treatment ADD CONSTRAINT FK_Treatment_patient_fk FOREIGN KEY (patient_fk) REFERENCES Patient (ID)
ALTER TABLE Treatment ADD CONSTRAINT FK_Treatment_providers_fk FOREIGN KEY (providers_fk) REFERENCES Provider (ID)
ALTER TABLE DrugTreatment ADD CONSTRAINT FK_DrugTreatment_TREATMENTID FOREIGN KEY (TREATMENTID) REFERENCES Treatment (TREATMENTID)
ALTER TABLE Surgery ADD CONSTRAINT FK_Surgery_TREATMENTID FOREIGN KEY (TREATMENTID) REFERENCES Treatment (TREATMENTID)
ALTER TABLE Radiology ADD CONSTRAINT FK_Radiology_TREATMENTID FOREIGN KEY (TREATMENTID) REFERENCES Treatment (TREATMENTID)
ALTER TABLE Radiology_date ADD CONSTRAINT FK_Radiology_date_RadiologyTreatmentid_fk FOREIGN KEY (RadiologyTreatmentid_fk) REFERENCES Radiology (TREATMENTID)
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(38), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)
