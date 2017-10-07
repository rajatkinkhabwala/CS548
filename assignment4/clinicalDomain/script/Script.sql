--<ScriptOptions statementTerminator=";"/>

CREATE TABLE patient (
		id INT8 NOT NULL,
		birthdate DATE,
		name VARCHAR(255),
		patientid INT8
	);

CREATE TABLE surgery (
		treatmentid INT8 NOT NULL,
		date DATE
	);

CREATE TABLE radiology_date (
		radiology_treatmentid INT8,
		date VARCHAR(255)
	);

CREATE TABLE treatment (
		treatmentid INT8 NOT NULL,
		treatment_type VARCHAR(31),
		diagnosis VARCHAR(255),
		patient_id INT8,
		providers_id INT8
	);

CREATE TABLE drugtreatment (
		treatmentid INT8 NOT NULL,
		dosage FLOAT8,
		drug VARCHAR(255),
		drugtreatmentid INT8
	);

CREATE TABLE radiology (
		treatmentid INT8 NOT NULL,
		rid INT8
	);

CREATE TABLE provider (
		id INT8 NOT NULL,
		name VARCHAR(255),
		npi INT8
	);

CREATE TABLE sequence (
		seq_name VARCHAR(50) NOT NULL,
		seq_count NUMERIC(38 , 0)
	);

CREATE UNIQUE INDEX radiology_pkey ON radiology (treatmentid ASC);

CREATE UNIQUE INDEX patient_pkey ON patient (id ASC);

CREATE UNIQUE INDEX treatment_pkey ON treatment (treatmentid ASC);

CREATE UNIQUE INDEX drugtreatment_pkey ON drugtreatment (treatmentid ASC);

CREATE UNIQUE INDEX sequence_pkey ON sequence (seq_name ASC);

CREATE UNIQUE INDEX surgery_pkey ON surgery (treatmentid ASC);

CREATE UNIQUE INDEX provider_pkey ON provider (id ASC);

ALTER TABLE surgery ADD CONSTRAINT surgery_pkey PRIMARY KEY (treatmentid);

ALTER TABLE radiology ADD CONSTRAINT radiology_pkey PRIMARY KEY (treatmentid);

ALTER TABLE drugtreatment ADD CONSTRAINT drugtreatment_pkey PRIMARY KEY (treatmentid);

ALTER TABLE treatment ADD CONSTRAINT treatment_pkey PRIMARY KEY (treatmentid);

ALTER TABLE provider ADD CONSTRAINT provider_pkey PRIMARY KEY (id);

ALTER TABLE patient ADD CONSTRAINT patient_pkey PRIMARY KEY (id);

ALTER TABLE sequence ADD CONSTRAINT sequence_pkey PRIMARY KEY (seq_name);

ALTER TABLE radiology ADD CONSTRAINT fk_radiology_treatmentid FOREIGN KEY (treatmentid)
	REFERENCES treatment (treatmentid);

ALTER TABLE drugtreatment ADD CONSTRAINT fk_drugtreatment_treatmentid FOREIGN KEY (treatmentid)
	REFERENCES treatment (treatmentid);

ALTER TABLE treatment ADD CONSTRAINT fk_treatment_patient_id FOREIGN KEY (patient_id)
	REFERENCES patient (id);

ALTER TABLE radiology_date ADD CONSTRAINT fk_radiology_date_radiology_treatmentid FOREIGN KEY (radiology_treatmentid)
	REFERENCES treatment (treatmentid);

ALTER TABLE treatment ADD CONSTRAINT fk_treatment_providers_id FOREIGN KEY (providers_id)
	REFERENCES provider (id);

ALTER TABLE surgery ADD CONSTRAINT fk_surgery_treatmentid FOREIGN KEY (treatmentid)
	REFERENCES treatment (treatmentid);

