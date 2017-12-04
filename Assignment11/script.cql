/* Rajat kinkhabwala */

/* Create Keyspace */
CREATE KEYSPACE cs548
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

use cs548;

drop table IF EXISTS patient;
drop table IF EXISTS provider;
drop table IF EXISTS treatment_patient;
drop table IF EXISTS treatment_provider;
drop type IF EXISTS "drugTreatmentType";
drop type IF EXISTS "radiologyType";
drop type IF EXISTS "surgeryType";

create table patient(id uuid, pid int, name varchar, dob date, primary key(pid));

create table provider(id uuid, npi int, name varchar, specialization varchar, primary key(npi));

create type cs548."drugTreatmentType"(drug varchar, dosage float);

create type cs548."surgeryType"("surgeryForm" varchar, "surgeryDate" date);

create type cs548."radiologyType"("radiologyDate" List<date>);

create table treatment_patient(tid uuid, "dateTreatmentStarted" date, year int, diagnosis varchar,
"patientId" int,
"providerId" int,
"DrugTreatment" frozen <cs548."drugTreatmentType">,
"Surgery" frozen <cs548."surgeryType">,
"Radiology" frozen <cs548."radiologyType">,
name varchar,
dob date,
primary key((tid, "patientId"), "dateTreatmentStarted")
) WITH CLUSTERING ORDER BY ("dateTreatmentStarted" DESC);

create table treatment_provider(tid uuid,
"dateTreatmentStarted" date, year int, diagnosis varchar,
"patientId" int,
"providerId" int,
name varchar,
specialization varchar,
primary key((tid, "providerId", year), "dateTreatmentStarted")
) WITH CLUSTERING ORDER BY ("dateTreatmentStarted" DESC);

insert into cs548.patient(id, pid, name, dob) values (uuid(), 1, 'Alex', '1991-2-6');
insert into cs548.patient(id, pid, name, dob) values (uuid(), 2, 'Mathew', '1993-3-2');
insert into cs548.patient(id, pid, name, dob) values (uuid(), 3, 'Jean', '1994-5-22');

insert into cs548.provider(id, npi, name, specialization) values (uuid(), 1, 'Max', 'Oncology');
insert into cs548.provider(id, npi, name, specialization) values (uuid(), 2, 'Shane&Shaw', 'Radiology');
insert into cs548.provider(id, npi, name, specialization) values (uuid(), 3, 'Hillary&Trump', 'Surgery');

insert into cs548.treatment_patient(tid,"dateTreatmentStarted", year, diagnosis, "patientId", "providerId", "DrugTreatment", name, dob) values (uuid(), '2003-1-26', 2003, 'AIDS', 1, 2, {drug:'Medicine1',dosage:35.0}, 'Alex', '1991-2-6');
insert into cs548.treatment_patient(tid,"dateTreatmentStarted", year, diagnosis, "patientId", "providerId", "Radiology", name, dob) values (uuid(), '2005-4-6', 2005, 'Cancer', 2, 1, {"radiologyDate": ['2004-5-10','2005-5-29','2005-10-08']}, 'Mathew', '1993-3-2');

insert into cs548.treatment_provider(tid,"dateTreatmentStarted", year, diagnosis, "patientId", "providerId", name, specialization) values (uuid(), '2005-4-6', 2005, 'Cancer', 2, 1, 'Max', 'Oncology');
insert into cs548.treatment_provider(tid,"dateTreatmentStarted", year, diagnosis, "patientId", "providerId", name, specialization) values (uuid(), '2003-1-26', 2003, 'AIDS', 1, 2, 'Shane&Shaw', 'Radiology');
insert into cs548.treatment_provider(tid,"dateTreatmentStarted", year, diagnosis, "patientId", "providerId", name, specialization) values (uuid(), '2002-3-2', 2002, 'Cardiac Arrest', 1, 2, 'Hillary&Trump',  'Surgery');

select * from cs548.treatment_provider;