ALTER TABLE Treatment DROP CONSTRAINT FK_Treatment_patient_fk
ALTER TABLE Treatment DROP CONSTRAINT FK_Treatment_providers_fk
ALTER TABLE DrugTreatment DROP CONSTRAINT FK_DrugTreatment_TREATMENTID
ALTER TABLE Surgery DROP CONSTRAINT FK_Surgery_TREATMENTID
ALTER TABLE Radiology DROP CONSTRAINT FK_Radiology_TREATMENTID
ALTER TABLE Radiology_date DROP CONSTRAINT FK_Radiology_date_RadiologyTreatmentid_fk
DROP TABLE Patient CASCADE
DROP TABLE Treatment CASCADE
DROP TABLE DrugTreatment CASCADE
DROP TABLE Surgery CASCADE
DROP TABLE Radiology CASCADE
DROP TABLE Provider CASCADE
DROP TABLE Radiology_date CASCADE
DELETE FROM SEQUENCE WHERE SEQ_NAME = 'SEQ_GEN'
