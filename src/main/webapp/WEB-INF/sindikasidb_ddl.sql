CREATE SEQUENCE MUSERGROUP_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE MUSERGROUPMENU_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE MUSER_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE MDECLINECODE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE MUNIT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE MSECTOR_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE MDEBITUR_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE MRMGROUP_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE MRM_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE MCURRENCY_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE TTARGETRMGROUP_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE TTARGETRM_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE TPIPELINE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE TPIPELINEPART_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE TMEMO_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE TPORTO_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE SEQUENCE TPORTOPART_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE MSYSPARAM  (
	MSYSPARAMPK INTEGER NOT NULL PRIMARY KEY, 
	PARAMNAME CHAR(50) NOT NULL , 
	PARAMVALUE CHAR(100) DEFAULT '',
	PARAMDESC CHAR(100) DEFAULT '', 
	ISMASKED CHARACTER(1) DEFAULT '',
	PARAMGROUP CHAR(50) DEFAULT '', 	
	ORDERNO INTEGER DEFAULT 0,
	UPDATEDBY CHAR(50) DEFAULT '',
	LASTUPDATED TIMESTAMP
);

CREATE TABLE MMENU (
	MMENUPK INTEGER NOT NULL PRIMARY KEY, 
	MENUORDERNO INTEGER DEFAULT 0 , 
	MENUGROUP CHAR(40) DEFAULT '' ,
	MENUGROUPICON CHAR(50) DEFAULT '',
	MENUSUBGROUP CHAR(40) DEFAULT '' ,
	MENUSUBGROUPICON CHAR(50) DEFAULT '',
	MENUNAME CHAR(100) DEFAULT '' ,
	MENUICON CHAR(50) DEFAULT '',
	MENUPATH CHAR(100) DEFAULT '' , 
	MENUPARAMNAME CHAR(10) DEFAULT '' , 
	MENUPARAMVALUE CHAR(30) DEFAULT '' 
);

CREATE TABLE MUSERGROUP  (
	MUSERGROUPPK INTEGER NOT NULL PRIMARY KEY , 
	USERGROUPCODE CHAR(20) DEFAULT '' , 
	USERGROUPNAME CHAR(40) DEFAULT '' , 
	USERGROUPDESC CHAR(100) DEFAULT '' , 
	UPDATEDBY CHAR(15) DEFAULT '',
	LASTUPDATED TIMESTAMP
);

CREATE TABLE MUSERGROUPMENU (
	MUSERGROUPMENUPK INTEGER NOT NULL PRIMARY KEY , 
	MUSERGROUPFK INTEGER NOT NULL , 
	MMENUFK INTEGER NOT NULL ); 
ALTER TABLE MUSERGROUPMENU
	ADD CONSTRAINT MUSERGROUPMENU_FK1 FOREIGN KEY
		(MUSERGROUPFK)
	REFERENCES MUSERGROUP
		(MUSERGROUPPK)
	ON DELETE CASCADE
	ON UPDATE NO ACTION;
ALTER TABLE MUSERGROUPMENU
	ADD CONSTRAINT MUSERGROUPMENU_FK2 FOREIGN KEY
		(MMENUFK)
	REFERENCES MMENU
		(MMENUPK)
	ON DELETE CASCADE
	ON UPDATE NO ACTION;
		 
CREATE TABLE MUSER (
	MUSERPK INTEGER NOT NULL PRIMARY KEY , 
	MUSERGROUPFK INTEGER NOT NULL, 
	USERID CHAR(15) DEFAULT '', 
	USERNAME CHAR(40) DEFAULT '', 
	PASSWORD CHAR(70) DEFAULT '', 	
	EMAIL CHAR(100) DEFAULT '', 
	UPDATEDBY CHAR(15) DEFAULT '',
	LASTUPDATED TIMESTAMP
);
ALTER TABLE MUSER
	ADD CONSTRAINT MUSER_FK1 FOREIGN KEY
		(MUSERGROUPFK)
	REFERENCES MUSERGROUP
		(MUSERGROUPPK)
	ON DELETE NO ACTION
	ON UPDATE NO ACTION;

CREATE TABLE TSCHEDULER  (
	TSCHEDULERPK INTEGER NOT NULL PRIMARY KEY, 
	SCHEDULERNAME CHAR(30) DEFAULT '' , 
	SCHEDULERGROUP CHAR(30) DEFAULT '' , 
	SCHEDULERDESC VARCHAR(200) DEFAULT '' , 
	SCHEDULERSTATUS CHAR(3) DEFAULT '' , 
	SCHEDULERREPEATTYPE CHAR(10) DEFAULT '' , 
	REPEATINTERVAL INTEGER DEFAULT 0 , 
	JOBCLASS CHAR(70) DEFAULT '', 
	UPDATEDBY CHAR(30) DEFAULT '',
	LASTUPDATED TIMESTAMP
); 

CREATE TABLE TCOUNTERENGINE  (
	COUNTERNAME CHAR(30) DEFAULT '' , 
	LASTCOUNTER INTEGER DEFAULT 0 ); 

CREATE TABLE MDECLINECODE  (
	MDECLINECODEPK INTEGER NOT NULL PRIMARY KEY , 
	DECLINECODE CHAR(3) DEFAULT '' , 
	DECLINEDESC CHAR(70) DEFAULT '' , 	
	LASTUPDATED TIMESTAMP, 
	UPDATEDBY CHAR(15) DEFAULT ''
);
	
CREATE TABLE MUNIT (
	MUNITPK INTEGER NOT NULL PRIMARY KEY, 
	UNITCODE CHAR(5) DEFAULT '',
	UNITNAME CHAR(50) DEFAULT '',
	UNITCOSTCENTER CHAR(10) DEFAULT '',
	UPDATEDBY CHAR(30) DEFAULT '',
	LASTUPDATED TIMESTAMP
);

CREATE TABLE MSECTOR (
	MSECTORPK INTEGER NOT NULL PRIMARY KEY, 
	SECTORNAME CHAR(50) DEFAULT '',
	UPDATEDBY CHAR(30) DEFAULT '',
	LASTUPDATED TIMESTAMP
);

CREATE TABLE MDEBITUR (
	MDEBITURPK INTEGER NOT NULL PRIMARY KEY , 
	DEBITUR CHAR(100) DEFAULT '',
	OFFICEPHONE CHAR(40) DEFAULT '',
	OFFICEFAX CHAR(40) DEFAULT '',
	OFFICEEMAIL CHAR(100) DEFAULT '',
	OFFICEADDRESS CHAR(200) DEFAULT '',
	DIRNAME CHAR(40) DEFAULT '',
	DIRPHONE CHAR(40) DEFAULT '',
	DIREMAIL CHAR(40) DEFAULT '',
	UPDATEDBY CHAR(30) DEFAULT '',
	LASTUPDATED TIMESTAMP
);

CREATE TABLE MRMGROUP (
	MRMGROUPPK INTEGER NOT NULL PRIMARY KEY , 
	RMGROUPCODE CHAR(10) DEFAULT '',
	RMGROUPNAME CHAR(40) DEFAULT '',
	UPDATEDBY CHAR(30) DEFAULT '',
	LASTUPDATED TIMESTAMP
);

CREATE TABLE MRM (
	MRMPK INTEGER NOT NULL PRIMARY KEY, 
	MRMGROUPFK INTEGER NOT NULL,
	RMID CHAR(10) DEFAULT '',
	RMNAME CHAR(40) DEFAULT '',
	UPDATEDBY CHAR(30) DEFAULT '',
	LASTUPDATED TIMESTAMP
);
ALTER TABLE MRM
	ADD CONSTRAINT MRM_FK1 FOREIGN KEY
		(MRMGROUPFK)
	REFERENCES MRMGROUP
		(MRMGROUPPK)
	ON DELETE RESTRICT
	ON UPDATE NO ACTION;

CREATE TABLE MCURRENCY (
	MCURRENCYPK INTEGER NOT NULL PRIMARY KEY , 
	CURRENCYCODE CHAR(5) DEFAULT '',
	COUNTRY CHAR(40) DEFAULT '',
	UPDATEDBY CHAR(30) DEFAULT '',
	LASTUPDATED TIMESTAMP
);

CREATE TABLE TTARGETRMGROUP  (
	TTARGETRMGROUPPK INTEGER NOT NULL PRIMARY KEY , 
	MRMGROUPFK INTEGER NOT NULL,
	TARGETYEAR CHAR(4) DEFAULT '',
	TARGETAMOUNT DECIMAL(17,0) DEFAULT 0,
	UPDATEDBY CHAR(30) DEFAULT '',
	LASTUPDATED TIMESTAMP
);
ALTER TABLE TTARGETRMGROUP
	ADD CONSTRAINT TTARGETRMGROUP_FK1 FOREIGN KEY
		(MRMGROUPFK)
	REFERENCES MRMGROUP
		(MRMGROUPPK)
	ON DELETE RESTRICT
	ON UPDATE NO ACTION;
	
CREATE TABLE TTARGETRM  (
	TTARGETRMPK INTEGER NOT NULL PRIMARY KEY , 
	MRMGROUPFK INTEGER NOT NULL,
	MRMFK INTEGER NOT NULL,
	TARGETYEAR CHAR(4) DEFAULT '',
	TARGETAMOUNT DECIMAL(17,0) DEFAULT 0,
	UPDATEDBY CHAR(30) DEFAULT '',
	LASTUPDATED TIMESTAMP
);
ALTER TABLE TTARGETRM
	ADD CONSTRAINT TTARGETRM_FK1 FOREIGN KEY
		(MRMGROUPFK)
	REFERENCES MRMGROUP
		(MRMGROUPPK)
	ON DELETE RESTRICT
	ON UPDATE NO ACTION;
ALTER TABLE TTARGETRM
	ADD CONSTRAINT TTARGETRM_FK2 FOREIGN KEY
		(MRMFK)
	REFERENCES MRM
		(MRMPK)
	ON DELETE RESTRICT
	ON UPDATE NO ACTION;
	
CREATE TABLE TPIPELINE  (
	TPIPELINEPK INTEGER NOT NULL PRIMARY KEY , 
	MDEBITURFK INTEGER NOT NULL,
	MSECTORFK INTEGER NOT NULL,
	MUNITFK INTEGER NOT NULL,
	MRMFK INTEGER NOT NULL,
	REGID CHAR(13) DEFAULT '',
	REGTIME TIMESTAMP,
	PROJECT CHAR(100) DEFAULT '',
	RMCREDIT CHAR(40) DEFAULT '',	
	CURRENCY CHAR(5) DEFAULT '',
	PROJECTAMOUNT DECIMAL(17,0) DEFAULT 0,
	CREDITFACILITY DECIMAL(17,0) DEFAULT 0,
	SELFPORTION DECIMAL(17,0) DEFAULT 0,
	FEEPERCENT DECIMAL(4,1) DEFAULT 0,
	FEEAMOUNT DECIMAL(17,0) DEFAULT 0,
	TARGETPK DATE,
	NOTES CHAR(200) DEFAULT '',
	STATUS CHAR(2) DEFAULT '',	
	UPDATEDBY CHAR(30) DEFAULT '',
	LASTUPDATED TIMESTAMP
);
ALTER TABLE TPIPELINE
	ADD CONSTRAINT TPIPELINE_FK1 FOREIGN KEY
		(MDEBITURFK)
	REFERENCES MDEBITUR
		(MDEBITURPK)
	ON DELETE RESTRICT
	ON UPDATE NO ACTION;
ALTER TABLE TPIPELINE
	ADD CONSTRAINT TPIPELINE_FK2 FOREIGN KEY
		(MSECTORFK)
	REFERENCES MSECTOR
		(MSECTORPK)
	ON DELETE RESTRICT
	ON UPDATE NO ACTION;
ALTER TABLE TPIPELINE
	ADD CONSTRAINT TPIPELINE_FK3 FOREIGN KEY
		(MUNITFK)
	REFERENCES MUNIT
		(MUNITPK)
	ON DELETE RESTRICT
	ON UPDATE NO ACTION;
ALTER TABLE TPIPELINE
	ADD CONSTRAINT TPIPELINE_FK4 FOREIGN KEY
		(MRMFK)
	REFERENCES MRM
		(MRMPK)
	ON DELETE RESTRICT
	ON UPDATE NO ACTION;

CREATE TABLE TPIPELINEPART  (
	TPIPELINEPARTPK INTEGER NOT NULL PRIMARY KEY , 
	TPIPELINEFK INTEGER NOT NULL,
	PARTICIPANTNAME CHAR(100) DEFAULT '',
	PORTIONPERCENT DECIMAL(4,1) DEFAULT 0,
	PORTIONAMOUNT DECIMAL(17,0) DEFAULT 0,
	UPDATEDBY CHAR(30) DEFAULT '',
	LASTUPDATED TIMESTAMP
);
ALTER TABLE TPIPELINEPART
	ADD CONSTRAINT TPIPELINEPART_FK1 FOREIGN KEY
		(TPIPELINEFK)
	REFERENCES TPIPELINE
		(TPIPELINEPK)
	ON DELETE CASCADE
	ON UPDATE NO ACTION;
	
CREATE TABLE TMEMO  (
	TMEMOPK INTEGER NOT NULL PRIMARY KEY, 
	TPIPELINEFK INTEGER NOT NULL , 
	MEMO CHAR(200) DEFAULT '' , 
	CREATEDTIME TIMESTAMP DEFAULT NULL , 
	CREATEDBY CHAR(15) DEFAULT ''
); 
ALTER TABLE TMEMO
	ADD CONSTRAINT TMEMO_FK1 FOREIGN KEY
		(TPIPELINEFK)
	REFERENCES TPIPELINE
		(TPIPELINEPK)
	ON DELETE CASCADE
	ON UPDATE NO ACTION;
	
CREATE TABLE TPORTO  (
	TPORTOPK INTEGER NOT NULL PRIMARY KEY , 
	MSECTORFK INTEGER NOT NULL,
	MUNITFK INTEGER NOT NULL,
	REGID CHAR(13) DEFAULT '',
	REGTIME TIMESTAMP,
	DEBITUR CHAR(100) DEFAULT '',
	OFFICEPHONE CHAR(40) DEFAULT '',
	OFFICEFAX CHAR(40) DEFAULT '',
	OFFICEEMAIL CHAR(100) DEFAULT '',
	OFFICEADDRESS CHAR(200) DEFAULT '',
	DIRNAME CHAR(40) DEFAULT '',
	DIRPHONE CHAR(40) DEFAULT '',
	DIREMAIL CHAR(40) DEFAULT '',
	PROJECT CHAR(100) DEFAULT '',
	RMGROUPCODE CHAR(10) DEFAULT '',
	RMID CHAR(10) DEFAULT '',	
	RMNAME CHAR(40) DEFAULT '',	
	RMCREDIT CHAR(40) DEFAULT '',	
	CURRENCY CHAR(5) DEFAULT '',
	PROJECTAMOUNT DECIMAL(17,0) DEFAULT 0,
	CREDITFACILITY DECIMAL(17,0) DEFAULT 0,
	SELFPORTION DECIMAL(17,0) DEFAULT 0,
	FEEPERCENT DECIMAL(4,1) DEFAULT 0,
	FEEAMOUNT DECIMAL(17,0) DEFAULT 0,
	TARGETPK DATE,
	PORTODATE DATE,
	UPDATEDBY CHAR(30) DEFAULT '',
	LASTUPDATED TIMESTAMP
);
ALTER TABLE TPORTO
	ADD CONSTRAINT TPORTO_FK1 FOREIGN KEY
		(MSECTORFK)
	REFERENCES MSECTOR
		(MSECTORPK)
	ON DELETE RESTRICT
	ON UPDATE NO ACTION;
ALTER TABLE TPORTO
	ADD CONSTRAINT TPORTO_FK2 FOREIGN KEY
		(MUNITFK)
	REFERENCES MUNIT
		(MUNITPK)
	ON DELETE RESTRICT
	ON UPDATE NO ACTION;

CREATE TABLE TPORTOPART  (
	TPORTOPARTPK INTEGER NOT NULL PRIMARY KEY , 
	TPORTOFK INTEGER NOT NULL,
	PARTICIPANTNAME CHAR(100) DEFAULT '',
	PORTIONPERCENT DECIMAL(4,1) DEFAULT 0,
	PORTIONAMOUNT DECIMAL(17,0) DEFAULT 0,
	UPDATEDBY CHAR(30) DEFAULT '',
	LASTUPDATED TIMESTAMP
);
ALTER TABLE TPORTOPART
	ADD CONSTRAINT TPORTOPART_FK1 FOREIGN KEY
		(TPORTOFK)
	REFERENCES TPORTO
		(TPORTOPK)
	ON DELETE CASCADE
	ON UPDATE NO ACTION;