DROP TABLE IF EXISTS TaskIncident;
DROP TABLE IF EXISTS Incident;
DROP TABLE IF EXISTS ProjectMgmt;
DROP TABLE IF EXISTS AssignmentProfile;
DROP TABLE IF EXISTS AssignmentPerson;
DROP TABLE IF EXISTS Workload;
DROP TABLE IF EXISTS ProjectFreeDay;
DROP TABLE IF EXISTS FreeDay;
DROP TABLE IF EXISTS AssignmentMaterial;
DROP TABLE IF EXISTS Predecessor;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS Milestone;
DROP TABLE IF EXISTS Phase;
DROP TABLE IF EXISTS HistoryProject;
DROP TABLE IF EXISTS State;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Province;
DROP TABLE IF EXISTS Country;
DROP TABLE IF EXISTS BusinessSize;
DROP TABLE IF EXISTS BusinessCategory;
DROP TABLE IF EXISTS BusinessType;
DROP TABLE IF EXISTS Damage;
DROP TABLE IF EXISTS Priority;
DROP TABLE IF EXISTS TaskLinkType;
DROP TABLE IF EXISTS Material;
DROP TABLE IF EXISTS HistoryPerson;
DROP TABLE IF EXISTS ProfessionalCategory;
DROP TABLE IF EXISTS LevelProfCatg;
DROP TABLE IF EXISTS TimeOff;
DROP TABLE IF EXISTS Aptitude;
DROP TABLE IF EXISTS AptitudeType;
DROP TABLE IF EXISTS Person;


-- ----------------------------------------------------------------------------------
-- -------------------------------- AptitudeType ------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE AptitudeType (
    idAptType VARCHAR(3) NOT NULL,
    nameAptType VARCHAR(45) NOT NULL,
    CONSTRAINT pk_aptitudeType PRIMARY KEY (idAptType),
	CONSTRAINT uq_aptitudeType_name UNIQUE (nameAptType)
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- -------------------------------- TaskLinkType ------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE TaskLinkType (
    idTaskLinkType VARCHAR(2) NOT NULL,
    nameTaskLinkType VARCHAR(45) NOT NULL,
    CONSTRAINT pk_tasklinktype PRIMARY KEY (idTaskLinkType),
	CONSTRAINT uq_tasklinktype_name UNIQUE (nameTaskLinkType)
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ---------------------------------- Priority --------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Priority (
    idPriority VARCHAR(2) NOT NULL,
    namePriority VARCHAR(45) NOT NULL,
    CONSTRAINT pk_priority PRIMARY KEY (idPriority),
	CONSTRAINT uq_priority_name UNIQUE (namePriority)
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ------------------------------- LevelProfCatg ------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE LevelProfCatg (
    idLevelProfCatg VARCHAR(3) NOT NULL,
    nameLevelProfCatg VARCHAR(45) NOT NULL,
    CONSTRAINT pk_levelpc PRIMARY KEY (idLevelProfCatg),
	CONSTRAINT uq_levelpc_name UNIQUE (nameLevelProfCatg)
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ----------------------------------- Damage ---------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Damage (
    idDamage VARCHAR(4) NOT NULL,
    nameDamage VARCHAR(45) NOT NULL,
    CONSTRAINT pk_damage PRIMARY KEY (idDamage),
	CONSTRAINT uq_damage_name UNIQUE (nameDamage)
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- -------------------------------- BusinessType ------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE BusinessType (
    idBusinessType VARCHAR(4) NOT NULL,
    nameBusinessType VARCHAR(45) NOT NULL,
    CONSTRAINT pk_businesstype PRIMARY KEY (idBusinessType),
	CONSTRAINT uq_businesstype_name UNIQUE (nameBusinessType)
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ------------------------------ BusinessCategory ----------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE BusinessCategory (
    idBusinessCatg VARCHAR(3) NOT NULL,
    nameBusinessCatg VARCHAR(45) NOT NULL,
    CONSTRAINT pk_businesscatg PRIMARY KEY (idBusinessCatg),
	CONSTRAINT uq_businesscatg_name UNIQUE (nameBusinessCatg)
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- -------------------------------- BusinessSize ------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE BusinessSize (
    idBusinessSize VARCHAR(1) NOT NULL,
    nameBusinessSize VARCHAR(45) NOT NULL,
	minBusinessSize INTEGER NOT NULL,
	maxBusinessSize INTEGER,
    CONSTRAINT pk_businesssize PRIMARY KEY (idBusinessSize),
	CONSTRAINT uq_businesssize_name UNIQUE (nameBusinessSize)
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ---------------------------------- Country --------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Country (
    idCountry VARCHAR(3) NOT NULL,
    nameCountry VARCHAR(45) NOT NULL,
    CONSTRAINT pk_country PRIMARY KEY (idCountry),
	CONSTRAINT uq_country_name UNIQUE (nameCountry)
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ---------------------------------- Province --------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Province (
    idProvince BIGINT NOT NULL AUTO_INCREMENT,
	idCountry VARCHAR(3) NOT NULL,
    nameProvince VARCHAR(45) NOT NULL,
    CONSTRAINT pk_province PRIMARY KEY (idProvince),
	CONSTRAINT uq_province_name UNIQUE (nameProvince),
	CONSTRAINT fk_province_idCountry FOREIGN KEY (idCountry) REFERENCES Country (idCountry) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ---------------------------------- Customer --------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Customer (
	idCustomer BIGINT NOT NULL AUTO_INCREMENT,
    nameCustomer VARCHAR(45) NOT NULL,
    idProvince BIGINT NOT NULL,
    idBusinessType VARCHAR(4) NOT NULL,
    idBusinessCatg VARCHAR(3) NOT NULL,
	idBusinessSize VARCHAR(1) NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY (idCustomer),
	CONSTRAINT uq_customer_name UNIQUE (nameCustomer),
	CONSTRAINT fk_customer_bType FOREIGN KEY (idBusinessType) REFERENCES BusinessType (idBusinessType) ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT fk_customer_bCatg FOREIGN KEY (idBusinessCatg) REFERENCES BusinessCategory (idBusinessCatg) ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT fk_customer_bSize FOREIGN KEY (idBusinessSize) REFERENCES BusinessSize (idBusinessSize) ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT fk_customer_country FOREIGN KEY (idProvince) REFERENCES Province (idProvince) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ---------------------------------- Project ---------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Project (
	idProject BIGINT NOT NULL AUTO_INCREMENT,
    nameProject VARCHAR(200) NOT NULL,
    descProject VARCHAR(1000),
	stateDateProject DATE NOT NULL,
	innerProject BOOLEAN NOT NULL,
	commentProject VARCHAR(1000),
    idProvince BIGINT NOT NULL,
	idCustomer BIGINT,
	budgetProject INTEGER,
	nifContact VARCHAR(9),
	nameContact VARCHAR(45),
	surnameContact VARCHAR(45),
	emailContact VARCHAR(45),
	daysPlanProject INTEGER,
	daysRealProject INTEGER,
	iniPlanProject DATE NOT NULL,
	iniRealProject DATE,
	endPlanProject DATE,
	endRealProject DATE,
	hoursPlanProject INTEGER,
	hoursRealProject INTEGER,
	costPlanProject INTEGER,
	costRealProject INTEGER,
	profitPlanProject INTEGER,
	profitRealProject INTEGER,
	lossProject INTEGER,
	progressProject INTEGER,
    CONSTRAINT pk_project PRIMARY KEY (idProject),
	CONSTRAINT uq_project_name UNIQUE (nameProject),
	CONSTRAINT check_project_progress CHECK (progressProject BETWEEN 0 AND 100),
	CONSTRAINT fk_project_customer FOREIGN KEY (idCustomer) REFERENCES Customer (idCustomer) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_project_country FOREIGN KEY (idProvince) REFERENCES Province (idProvince) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ----------------------------------- State ----------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE State (
    idState VARCHAR(4) NOT NULL,
    nameState VARCHAR(45) NOT NULL,
    descState VARCHAR(500),
    CONSTRAINT pk_state PRIMARY KEY (idState),
	CONSTRAINT uq_state_name UNIQUE (nameState)
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ------------------------------- HistoryProject -----------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE HistoryProject (
	idHProject BIGINT NOT NULL AUTO_INCREMENT,
	idProject BIGINT NOT NULL,    
	idState VARCHAR(4) NOT NULL,
	iniHProject DATE NOT NULL,
	endHProject DATE,
	commentHProject VARCHAR(500),
    CONSTRAINT pk_historyproject PRIMARY KEY (idHProject),
	CONSTRAINT uq_historyproject_projAndState UNIQUE (idProject,idState),
	CONSTRAINT fk_historyproject_idProject FOREIGN KEY (idProject) REFERENCES Project (idProject) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_historyproject_idState FOREIGN KEY (idState) REFERENCES State (idState) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ----------------------------------- Phase ----------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Phase (
	idPhase BIGINT NOT NULL AUTO_INCREMENT,
    idProject BIGINT NOT NULL,
	namePhase VARCHAR(45) NOT NULL,
	iniPhase DATE,
	endPhase DATE,
	CONSTRAINT pk_phase PRIMARY KEY (idPhase),
	CONSTRAINT uq_phase_projectAndphasename UNIQUE (idProject,namePhase),
	CONSTRAINT fk_phase_idProject FOREIGN KEY (idProject) REFERENCES Project (idProject) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ----------------------------------- Milestone ----------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Milestone (
	idMilestone BIGINT NOT NULL  AUTO_INCREMENT,
	idPhase BIGINT NOT NULL,
	nameMilestone VARCHAR(45) NOT NULL,
	datePlanMilestone DATE NOT NULL,
	dateRealMilestone DATE,
	commentMilestone VARCHAR(500),
	CONSTRAINT pk_milestone PRIMARY KEY (idMilestone),
	CONSTRAINT uq_milestone_phaseAndmilestonename UNIQUE (idPhase,nameMilestone),
	CONSTRAINT fk_milestone_phase FOREIGN KEY (idPhase) REFERENCES Phase (idPhase) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ----------------------------------- Person ---------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Person (
    idPerson BIGINT NOT NULL AUTO_INCREMENT,
    namePerson VARCHAR(45) NOT NULL,
    surname1Person VARCHAR(45) NOT NULL,
	surname2Person VARCHAR(45) NOT NULL,
	nifPerson VARCHAR(9) NOT NULL,
	emailPerson VARCHAR(45) NOT NULL,
	hiredatePerson DATE NOT NULL, 
	commentPerson VARCHAR(500),
    CONSTRAINT pk_person PRIMARY KEY (idPerson),
	CONSTRAINT uq_person_nif UNIQUE (nifPerson),
	CONSTRAINT uq_person_email UNIQUE (emailPerson)
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ---------------------------------- Aptitude --------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Aptitude (
	idApt BIGINT NOT NULL AUTO_INCREMENT,	
	idPerson BIGINT NOT NULL,  
    nameApt VARCHAR(45) NOT NULL,
	idAptType VARCHAR(3) NOT NULL,
	valueApt INTEGER,
	commentApt VARCHAR(500),
    CONSTRAINT pk_aptitude PRIMARY KEY (idApt),
	CONSTRAINT uq_aptitude_personAndname UNIQUE (idPerson,nameApt),
	CONSTRAINT fk_aptitude_idperson FOREIGN KEY (idPerson) REFERENCES Person (idPerson) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_aptitude_idAptType FOREIGN KEY (idAptType) REFERENCES AptitudeType (idAptType) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ---------------------------------- TimeOff ---------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE TimeOff (
	idTimeOff BIGINT NOT NULL AUTO_INCREMENT,
	idPerson BIGINT NOT NULL,  
    iniTimeOff DATE NOT NULL,
	endTimeOff DATE,
	reasonTimeOff VARCHAR(100),
    CONSTRAINT pk_timeoff PRIMARY KEY (idTimeOff),
	CONSTRAINT uq_timeoff_personAndIni UNIQUE (idPerson,iniTimeOff),
	CONSTRAINT fk_timeoff_idperson FOREIGN KEY (idPerson) REFERENCES Person (idPerson) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ---------------------------- ProfessionalCategory --------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE ProfessionalCategory (
    idProfCatg BIGINT NOT NULL AUTO_INCREMENT,
    nameProfCatg VARCHAR(45) NOT NULL,
	minExpProfCatg INTEGER NOT NULL,
	idLevelProfCatg VARCHAR(3) NOT NULL, 
	salProfCatg INTEGER NOT NULL,
	salExtraProfCatg INTEGER,
    CONSTRAINT pk_profcatg PRIMARY KEY (idProfCatg),
	CONSTRAINT uq_profcatg_nameAndlevel UNIQUE (nameProfCatg,idLevelProfCatg),
	CONSTRAINT fk_profcatg_level FOREIGN KEY (idLevelProfCatg) REFERENCES LevelProfCatg (idLevelProfCatg) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ------------------------------- HistoryPerson ------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE HistoryPerson (
    idHPerson BIGINT NOT NULL AUTO_INCREMENT,
    idPerson BIGINT NOT NULL,
	idProfCatg BIGINT NOT NULL,
	iniHPerson DATE NOT NULL,
	endHPerson DATE,
	salHPerson INTEGER NOT NULL,
	salExtraHPerson INTEGER NOT NULL, 
	commentHPerson VARCHAR(500),
    CONSTRAINT pk_historyperson PRIMARY KEY (idHPerson),
	CONSTRAINT fk_historyperson_idperson FOREIGN KEY (idPerson) REFERENCES Person (idPerson) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_historyperson_idprofcatg FOREIGN KEY (idProfCatg) REFERENCES ProfessionalCategory (idProfCatg) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ---------------------------------- Material --------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Material (
    idMaterial BIGINT NOT NULL AUTO_INCREMENT,
    nameMaterial VARCHAR(45) NOT NULL,
	descMaterial VARCHAR(500),
	costMaterial INTEGER NOT NULL,
	innerMaterial BOOLEAN NOT NULL,
    CONSTRAINT pk_material PRIMARY KEY (idMaterial),
	CONSTRAINT uq_material_nameAndCost UNIQUE (nameMaterial,costMaterial)
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ----------------------------------- Task -----------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Task (
	idTask BIGINT NOT NULL AUTO_INCREMENT,
	idPhase BIGINT NOT NULL,
	nameTask VARCHAR(45) NOT NULL,
	commentTask VARCHAR(500),
	idState VARCHAR(4) NOT NULL,
	idPriority VARCHAR(2) NOT NULL,
    idHPerson BIGINT NOT NULL,
	daysPlanTask INTEGER NOT NULL,
	daysRealTask INTEGER,
	iniPlanTask DATE,
	iniRealTask DATE,
	hoursPlanTask INTEGER,
	hoursRealTask INTEGER,
	endPlanTask DATE,
	endRealTask DATE,
	costPlanTask INTEGER,
	costRealTask INTEGER,
	progressTask INTEGER,
	CONSTRAINT pk_task PRIMARY KEY (idTask),
	CONSTRAINT uq_task_phaseAndTaskName UNIQUE (idPhase,nameTask),
	CONSTRAINT check_task_progress CHECK (progressTask BETWEEN 0 AND 100),
	CONSTRAINT fk_task_state FOREIGN KEY (idState) REFERENCES State (idState) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_task_priority FOREIGN KEY (idPriority) REFERENCES Priority (idPriority) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_task_person FOREIGN KEY (idHPerson) REFERENCES HistoryPerson (idHPerson) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_task_phase FOREIGN KEY (idPhase) REFERENCES Phase (idPhase) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- -------------------------------- Predecessor -------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Predecessor (
	idPredecessor BIGINT NOT NULL AUTO_INCREMENT,
	idTask BIGINT NOT NULL,
	idTaskPred BIGINT NOT NULL,
	idTaskLinkType VARCHAR(2) NOT NULL,
	CONSTRAINT pk_predecessor PRIMARY KEY (idPredecessor),
	CONSTRAINT uq_predecessor_tasks UNIQUE (idTask,idTaskPred),
	CONSTRAINT fk_predecessor_linktype FOREIGN KEY (idTaskLinkType) REFERENCES TaskLinkType (idTaskLinkType) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_predecessor_task FOREIGN KEY (idTask) REFERENCES Task (idTask) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_predecessor_taskPred FOREIGN KEY (idTaskPred) REFERENCES Task (idTask) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ---------------------------- AssignmentMaterial ----------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE AssignmentMaterial (
	idAssigMat BIGINT NOT NULL AUTO_INCREMENT,
	idTask BIGINT NOT NULL,  
	idMaterial BIGINT NOT NULL,
	planAssigMat BOOLEAN NOT NULL,
	realAssigMat BOOLEAN NOT NULL,
	unitsPlanAssigMat INTEGER,
	unitsRealAssigMat INTEGER,
	costPlanAssigMat INTEGER,
	costRealAssigMat INTEGER,
    CONSTRAINT pk_assigMat PRIMARY KEY (idAssigMat),
	CONSTRAINT uq_assigMat_taskAndMat UNIQUE (idTask,idMaterial),
	CONSTRAINT fk_assigMat_idMaterial FOREIGN KEY (idMaterial) REFERENCES Material (idMaterial) ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT fk_assigMat_task FOREIGN KEY (idTask) REFERENCES Task (idTask) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- --------------------------------- FreeDay ----------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE FreeDay (
	idFreeDay BIGINT NOT NULL AUTO_INCREMENT,
	nameFreeDay VARCHAR(45) NOT NULL,
	weekDayFreeDay INTEGER,
	iniFreeDay DATE,
	endFreeDay DATE,
	CONSTRAINT pk_freeDay PRIMARY KEY (idFreeDay),
	CONSTRAINT uq_freeDay_name UNIQUE (nameFreeDay),
	CONSTRAINT uq_freeDay_weekday UNIQUE (weekDayFreeDay)
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- ----------------------------- ProjectFreeDay -------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE ProjectFreeDay (
	idProjectFreeDay BIGINT NOT NULL AUTO_INCREMENT,
	idProject BIGINT NOT NULL,
	idFreeDay BIGINT NOT NULL,
	CONSTRAINT pk_projectFreeDay PRIMARY KEY (idProjectFreeDay),
	CONSTRAINT uq_projectFreeDay UNIQUE (idProject, idFreeDay),
	CONSTRAINT fk_projectFreeDay_project FOREIGN KEY (idProject) REFERENCES Project (idProject) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_projectFreeDay_freeday FOREIGN KEY (idFreeDay) REFERENCES FreeDay (idFreeDay) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- --------------------------------- Workload ---------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Workload (
	idWorkload BIGINT NOT NULL AUTO_INCREMENT,	
	idTask BIGINT NOT NULL,
    idHPerson BIGINT NOT NULL,
	dayDateWorkload DATE NOT NULL,
	hoursWorkload INTEGER NOT NULL,
	extraHoursWorkload INTEGER NOT NULL,
	CONSTRAINT pk_workload PRIMARY KEY (idWorkload),
	CONSTRAINT uq_workload_dateAndtaskAndPerson UNIQUE (dayDateWorkload,idTask,idHPerson),
	CONSTRAINT fk_workload_person FOREIGN KEY (idHPerson) REFERENCES HistoryPerson (idHPerson) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_workload_task FOREIGN KEY (idTask) REFERENCES Task (idTask) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB; 


-- ----------------------------------------------------------------------------------
-- ----------------------------- AssignmentPerson -----------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE AssignmentPerson (
	idAssigPerson BIGINT NOT NULL AUTO_INCREMENT,
	idTask BIGINT NOT NULL,
    idHPerson BIGINT NOT NULL,
	concludeAssigPerson BOOLEAN NOT NULL,
	totalHoursAssigPerson INTEGER,
	totalExtraHoursAssigPerson INTEGER,
	totalCostAssigPerson INTEGER,
	CONSTRAINT pk_assigperson PRIMARY KEY (idAssigPerson),
	CONSTRAINT uq_assigperson_taskAndPerson UNIQUE (idTask,idHPerson),
	CONSTRAINT fk_assigperson_person FOREIGN KEY (idHPerson) REFERENCES HistoryPerson (idHPerson) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_assigperson_task FOREIGN KEY (idTask) REFERENCES Task (idTask) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB; 


-- ----------------------------------------------------------------------------------
-- ----------------------------- AssignmentProfile ----------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE AssignmentProfile (
	idAssigProf BIGINT NOT NULL AUTO_INCREMENT,
	idTask BIGINT NOT NULL,
	idProfCatg BIGINT NOT NULL,
	unitsAssigProf INTEGER NOT NULL,
	hoursPerPersonAssigProf INTEGER NOT NULL,
	costAssigProf INTEGER NOT NULL,
	CONSTRAINT pk_assigprof PRIMARY KEY (idAssigProf),
	CONSTRAINT uq_assigprof_taskAndProf UNIQUE (idTask,idProfCatg),
	CONSTRAINT fk_assigprof_idprofcatg FOREIGN KEY (idProfCatg) REFERENCES ProfessionalCategory (idProfCatg) ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT fk_assigprof_task FOREIGN KEY (idTask) REFERENCES Task (idTask) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB;


-- ----------------------------------------------------------------------------------
-- -------------------------------- ProjectMgmt -------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE ProjectMgmt (
	idProjecMgmt BIGINT NOT NULL AUTO_INCREMENT,
	idProject BIGINT NOT NULL,
    idHPerson BIGINT NOT NULL,
	iniProjectMgmt DATE NOT NULL,
	endProjectMgmt DATE,
	CONSTRAINT pk_projectmgmt PRIMARY KEY (idProjecMgmt),
	CONSTRAINT uq_projectmgmt_projectAndPerson UNIQUE (idProject, idHPerson),
	CONSTRAINT fk_projectmgmt_project FOREIGN KEY (idProject) REFERENCES Project (idProject) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_projectmgmt_person FOREIGN KEY (idHPerson) REFERENCES HistoryPerson (idHPerson) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE = InnoDB; -- Fecha fin mayor que la inicial


-- ----------------------------------------------------------------------------------
-- --------------------------------- Incident ---------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE Incident (
	idIncident BIGINT NOT NULL AUTO_INCREMENT,
	idDamage VARCHAR(4) NOT NULL,
	reasonIncident VARCHAR(100) NOT NULL,
	resultIncident VARCHAR(100),
	CONSTRAINT pk_incident PRIMARY KEY (idIncident),
	CONSTRAINT uq_incident_reason UNIQUE (reasonIncident),
	CONSTRAINT fk_incident_damage FOREIGN KEY (idDamage) REFERENCES Damage (idDamage) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE = InnoDB; 


-- ----------------------------------------------------------------------------------
-- ------------------------------- TaskIncident -------------------------------------
-- ----------------------------------------------------------------------------------
CREATE TABLE TaskIncident (
	idTaskIncident BIGINT NOT NULL AUTO_INCREMENT,
	idTask BIGINT NOT NULL,
	idIncident BIGINT NOT NULL,
	lossIncident INTEGER NOT NULL,
	CONSTRAINT pk_taskincident PRIMARY KEY (idTaskIncident),
	CONSTRAINT uq_taskincident UNIQUE (idTask, idIncident),
	CONSTRAINT fk_taskincident_task FOREIGN KEY (idTask) REFERENCES Task (idTask) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_taskincident_incident FOREIGN KEY (idIncident) REFERENCES Incident (idIncident) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE = InnoDB;



