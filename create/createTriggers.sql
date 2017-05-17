

-- ----------------------------------------------------------------------------------
-- -------------------------- tBusinessSizeInsert -----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tBusinessSizeInsert;

DELIMITER $$
CREATE TRIGGER tBusinessSizeInsert BEFORE INSERT ON BusinessSize 
FOR EACH ROW BEGIN
-- Checks if the number of employers is positive.
	CALL isPositive(NEW.minBusinessSize,'minBusinessSize');
	CALL isPositive(NEW.maxBusinessSize,'maxBusinessSize');	
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- -------------------------- tBusinessSizeUpdate -----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tBusinessSizeUpdate;

DELIMITER $$
CREATE TRIGGER tBusinessSizeUpdate BEFORE UPDATE ON BusinessSize 
FOR EACH ROW BEGIN
-- Checks if the number of employers is positive.
	CALL isPositive(NEW.minBusinessSize,'minBusinessSize');
	CALL isPositive(NEW.maxBusinessSize,'maxBusinessSize');	
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- -------------------------- tProjectInsert -----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tProjectInsert;

DELIMITER $$
CREATE TRIGGER tProjectInsert BEFORE INSERT ON Project 
FOR EACH ROW BEGIN
	DECLARE thereAreDataDefined,contactDataAreNull BOOLEAN;
	DECLARE msg VARCHAR(128);

-- Checks the plan and real datas. The only that it is not checked is iniPlanProject.
	SET thereAreDataDefined = (NEW.daysPlanProject IS NOT NULL OR NEW.daysRealProject IS NOT NULL OR NEW.hoursPlanProject IS NOT NULL OR NEW.hoursRealProject IS NOT NULL OR NEW.costPlanProject IS NOT NULL OR NEW.costRealProject IS NOT NULL OR NEW.endPlanProject IS NOT NULL OR NEW.iniRealProject IS NOT NULL OR NEW.endRealProject);

-- If the customer is assinged then checks if there are contact datas defined.
	IF (thereAreDataDefined) THEN 
		SET msg = concat('Error: You can not insert planned or real datas (except iniPlanProject).');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
	
-- Checks the budget, stateDate and contactData.
	SET contactDataAreNull = (NEW.nifContact IS NULL OR NEW.nameContact IS NULL OR NEW.surnameContact IS NULL OR NEW.emailContact IS NULL);
	CALL checkProjectData(NEW.stateDateProject, NEW.budgetProject, NEW.idCustomer, contactDataAreNull);

-- The initial progress for the project is 0%
	SET NEW.progressProject = 0;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- -------------------------- tProjectUpdate -----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tProjectUpdate;

DELIMITER $$
CREATE TRIGGER tProjectUpdate BEFORE UPDATE ON Project 
FOR EACH ROW BEGIN
	DECLARE contactDataAreNull BOOLEAN DEFAULT false;

-- Checks the budget, stateDate and contactData.
	SET contactDataAreNull = (NEW.nifContact IS NULL OR NEW.nameContact IS NULL OR NEW.surnameContact IS NULL OR NEW.emailContact IS NULL);
	CALL checkProjectData(NEW.stateDateProject, NEW.budgetProject, NEW.idCustomer, contactDataAreNull);	

-- Checks if a few attributes are positive.
	CALL isPositive(NEW.daysPlanProject,'daysPlanProject');
	CALL isPositive(NEW.daysRealProject,'daysRealProject');
	CALL isPositive(NEW.hoursPlanProject,'hoursPlanProject');
	CALL isPositive(NEW.hoursRealProject,'hoursRealProject');
	CALL isPositive(NEW.costPlanProject,'costPlanProject');
	CALL isPositive(NEW.costRealProject,'costRealProject');

-- Checks if the dates are correct.
	CALL checkUpdatedProjectData (OLD.idProject, OLD.iniPlanProject, NEW.iniPlanProject, NEW.endPlanProject, NEW.iniRealProject, NEW.endRealProject);
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- --------------------------- tHistoryProjectInsert --------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tHistoryProjectInsert;

DELIMITER $$
CREATE TRIGGER tHistoryProjectInsert BEFORE INSERT ON HistoryProject 
FOR EACH ROW BEGIN
	DECLARE previousIdHProject BIGINT;
	DECLARE previousEndDate DATE;
	DECLARE previousState VARCHAR(4);

-- Checks the data of the current historyProject.
	SET previousIdHProject = (SELECT MAX(idHProject) FROM HistoryProject WHERE idProject = NEW.idProject);

	SET previousEndDate = (SELECT endHProject 
					   	   FROM HistoryProject 
					   	   WHERE idHProject = (previousIdHProject));

	CALL checkHistoryProjectData(previousIdHProject, NEW.idState, previousEndDate, NEW.iniHProject, NEW.endHProject);

	IF (previousIdHProject IS NOT NULL) THEN
	-- Check if the change at the new project state is valid.
		SET previousState = (SELECT idState FROM HistoryProject WHERE idHProject = (previousIdHProject));
		CALL checkChangeProjectState(previousState, NEW.idState);

	-- Checks if the task state are compatible with the new project state.
		IF (NEW.idState!='CANC') THEN 
			CALL checkIfTaskAreCompatible(NEW.idProject,NEW.idState);
		END IF;
	END IF;

-- Change the task state if the state project is 'CANC'.
	IF (NEW.idState='CANC') THEN
		CALL updateTaskToCANC(NEW.idProject,NEW.idState);
	END IF;

-- If the new state is 'EJEC', it will calculate the planned data for Project table.
	IF (NEW.idState='EJEC') THEN
		CALL calculatePlanData(NEW.idProject);
	END IF;

-- If the new state is 'TERM' or 'CANC', it will calculate the planned data for Project table.
	IF (NEW.idState='TERM' OR NEW.idState='CANC') THEN
		CALL calculateRealData(NEW.idProject);
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- --------------------------- tHistoryProjectUpdate --------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tHistoryProjectUpdate;

DELIMITER $$
CREATE TRIGGER tHistoryProjectUpdate BEFORE UPDATE ON HistoryProject 
FOR EACH ROW BEGIN
	DECLARE previousEndDate DATE;
	DECLARE msg VARCHAR(128);

-- Checks if the history has ended.
	IF (OLD.endHProject IS NOT NULL AND OLD.endHProject!=NEW.endHProject) THEN 
		SET msg = concat('Error: You can not change these datas because this project state has finished.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

-- Checks if we are trying change the project state.
	IF (OLD.idState != NEW.idState OR OLD.idProject != NEW.idProject) THEN 
		SET msg = concat('Error: You can not change the project id or the project state.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;	

-- Checks the dates of historyProject.
	IF (OLD.iniHProject != NEW.iniHProject) THEN
		SET previousEndDate = (SELECT endHProject 
					   	   	   FROM HistoryProject 
					   	   	   WHERE idHProject = (SELECT MAX(idHProject) FROM HistoryProject WHERE idProject = NEW.idProject));

		SET msg = concat('Error: Invalid value for iniHProject (',cast(NEW.iniHProject as char));
		SET msg = concat(msg,'). It must be greater or equal than the previous endHProject.');
		CALL dateGreaterOrEqualThan(previousEndDate,NEW.iniHProject,msg);
	END IF;

	IF (NEW.endHProject IS NOT NULL) THEN 
		SET msg = concat('Error: Invalid value for endHProject (',cast(NEW.endHProject as char),'). It must be greater than iniHProject.');
		CALL dateGreaterThan(NEW.iniHProject,NEW.endHProject,msg);
	END IF;
	
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- tMilestoneInsert -----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tMilestoneInsert;

DELIMITER $$
CREATE TRIGGER tMilestoneInsert BEFORE INSERT ON Milestone 
FOR EACH ROW BEGIN
	DECLARE iniProject DATE;
	DECLARE project BIGINT;

-- Gets the project of the Milestone
	SET project = (SELECT idProject FROM Phase WHERE idPhase=NEW.idPhase);

-- Checks that milestone date is equal or greater than project ini date.
	CALL compatibleWithIniProject(project, NEW.datePlanMilestone, 'datePlanMilestone');
	CALL compatibleWithIniProject(project, NEW.dateRealMilestone, 'dateRealMilestone');

END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- tMilestoneUpdate -----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tMilestoneUpdate;

DELIMITER $$
CREATE TRIGGER tMilestoneUpdate BEFORE UPDATE ON Milestone 
FOR EACH ROW BEGIN
	DECLARE iniProject DATE;
	DECLARE project BIGINT;

-- Gets the project of the Milestone
	SET project = (SELECT idProject FROM Phase WHERE idPhase=NEW.idPhase);

-- Checks that milestone date is equal or greater than project ini date.
	CALL compatibleWithIniProject(project, NEW.datePlanMilestone, 'datePlanMilestone');
	CALL compatibleWithIniProject(project, NEW.dateRealMilestone, 'dateRealMilestone');

END$$
DELIMITER ;

-- ----------------------------------------------------------------------------------
-- ------------------------------ tPersonUpdate -------------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tPersonUpdate;

DELIMITER $$
CREATE TRIGGER tPersonUpdate BEFORE UPDATE ON Person 
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);

-- Checks nifPerson has changed.
	IF (OLD.nifPerson!=NEW.nifPerson) THEN 
		SET msg = ('Error: You can not update nifPerson.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------------ tAptitudeInsert -----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tAptitudeInsert;

DELIMITER $$
CREATE TRIGGER tAptitudeInsert BEFORE INSERT ON Aptitude 
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);

-- Check that 'valueApt' is in the range 0-10.
	SET msg = concat('Error: Invalid value for valueApt (',cast(NEW.valueApt as char), '). It must be in the range 0-10.');
	CALL withinTheRange(NEW.valueApt,0,10,msg);
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------------ tAptitudeUpdate -----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tAptitudeUpdate;

DELIMITER $$
CREATE TRIGGER tAptitudeUpdate BEFORE UPDATE ON Aptitude 
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);

-- Check that 'valueApt' is in the range 0-10.	
	set msg = concat('Error: Invalid value for valueApt (',cast(NEW.valueApt as char), '). It must be in the range 0-10.');
	CALL withinTheRange(NEW.valueApt,0,10,msg);
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------------ tTimeOffInsert ------------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tTimeOffInsert;

DELIMITER $$
CREATE TRIGGER tTimeOffInsert BEFORE INSERT ON TimeOff 
FOR EACH ROW BEGIN
	DECLARE noTOff INTEGER;	
	DECLARE hiredate, endTOff DATE;
	DECLARE msg VARCHAR(128);


-- Checks the times ini and end in inserts.
	SET hiredate = (SELECT hiredatePerson FROM Person WHERE idPerson = NEW.idPerson);
	CALL checkPersonDates(NEW.iniTimeOff,NEW.endTimeOff,hiredate,'iniTimeOff','endTimeOff');

-- Checks if there are another parallel dates.
	CALL checkTimeOffduplicate(NEW.iniTimeOff,NEW.endTimeOff,NEW.idPerson,NEW.idTimeOff);

-- Checks if the last TimeOff has ended.
	SET noTOff = (SELECT MAX(idTimeOff) FROM TimeOff WHERE idPerson=NEW.idPerson);

	IF (noTOff IS NOT NULL) THEN	
		SET endTOff = (SELECT endTimeOff FROM TimeOff WHERE idPerson=NEW.idPerson AND idTimeOff=noTOff);
		
		IF (endTOff IS NULL) THEN
			SET msg = ('Error: This person has assigned a timeoff that has no ended.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		END IF;	
	END IF;

-- Checks if the person has worked in the defined dates.
	CALL checkWorkloadDates(NEW.idPerson,NEW.iniTimeOff,NEW.endTimeOff);
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------------ tTimeOffUpdate ------------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tTimeOffUpdate;

DELIMITER $$
CREATE TRIGGER tTimeOffUpdate BEFORE UPDATE ON TimeOff 
FOR EACH ROW BEGIN
	DECLARE hiredate DATE;	
	DECLARE msg VARCHAR(128);

-- Checks if we can update.
	IF (OLD.idPerson!=NEW.idPerson OR OLD.iniTimeOff!=NEW.iniTimeOff OR (OLD.endTimeOff IS NOT NULL AND OLD.endTimeOff!=NEW.endTimeOff)) THEN 
		SET msg = ('Error: You only can update endTimeOff if this is null or reasonTimeOff.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

-- Checks the times ini and end in inserts.
	SET hiredate = (SELECT hiredatePerson FROM Person WHERE idPerson = NEW.idPerson);
	CALL checkPersonDates(NEW.iniTimeOff,NEW.endTimeOff,hiredate,'iniTimeOff','endTimeOff');

-- Checks if there are another parallel dates.
	CALL checkTimeOffduplicate(OLD.iniTimeOff,NEW.endTimeOff,NEW.idPerson,NEW.idTimeOff);
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------- tProfessionalCategoryInsert ------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tProfessionalCategoryInsert;

DELIMITER $$
CREATE TRIGGER tProfessionalCategoryInsert BEFORE INSERT ON ProfessionalCategory 
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);

-- Checks if minExpProfCatg is in range 0-50.
	set msg = concat('Error: Invalid value for minExpProfCatg (',cast(NEW.minExpProfCatg as char), '). It must be in the range 0-50.');
	CALL withinTheRange(NEW.minExpProfCatg,0,50,msg);
	
-- Check if the sal is positive.
	CALL isPositive(NEW.salProfCatg,'salProfCatg');
	CALL isPositive(NEW.salExtraProfCatg,'salExtraProfCatg');	
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------- tProfessionalCategoryUpdate ------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tProfessionalCategoryUpdate;

DELIMITER $$
CREATE TRIGGER tProfessionalCategoryUpdate BEFORE UPDATE ON ProfessionalCategory 
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);

-- Checks if the sal has change.
	IF (OLD.salProfCatg != NEW.salProfCatg OR (OLD.salExtraProfCatg IS NOT NULL AND OLD.salExtraProfCatg!=NEW.salExtraProfCatg)) THEN
		SET msg = ('Error: You can not update salProfCatg or salExtraProfCatg.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
	
-- Checks if the salExtraProfCatg is positive.
	CALL isPositive(NEW.salExtraProfCatg,'salExtraProfCatg');	
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- --------------------------- tHistoryPersonInsert ---------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tHistoryPersonInsert;

DELIMITER $$
CREATE TRIGGER tHistoryPersonInsert BEFORE INSERT ON HistoryPerson 
FOR EACH ROW BEGIN
	DECLARE hiredate DATE;
	DECLARE msg VARCHAR(128);
	DECLARE aux INTEGER DEFAULT 0;

-- Check if this Person has profiles currently.
	SET aux = (SELECT COUNT(idHPerson)
			   FROM HistoryPerson
			   WHERE idPerson=NEW.idPerson AND endHPerson IS NULL);

	IF (aux>0) THEN
		SET msg = concat('Error: This person currently has assigned a ProfessionalCategory.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

-- Checks if the dates are correct.
	SET hiredate = (SELECT hiredatePerson FROM Person WHERE idPerson = NEW.idPerson);
	CALL checkHistoryPersonData (NEW.iniHPerson, NEW.endHPerson, hiredate, NEW.salHPerson, NEW.salExtraHPerson);

-- If end date was defined, we check if it is correct
	IF (NEW.endHPerson IS NOT NULL) THEN
		SET msg = concat('Error: Invalid date for endHPerson (',cast(NEW.endHPerson as char), '). It cannot be greater than now.');
		CALL dateGreaterOrEqualThan(NEW.endHPerson,CURDATE(),msg);
	END IF;

-- Check the date interval
	CALL checkHPduplicate(NEW.iniHPerson,NEW.endHPerson, NEW.idPerson);

END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- --------------------------- tHistoryPersonUpdate ---------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tHistoryPersonUpdate;

DELIMITER $$
CREATE TRIGGER tHistoryPersonUpdate BEFORE UPDATE ON HistoryPerson 
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);
	DECLARE changesNotAllowed, changesNotAllowedEnd BOOLEAN DEFAULT false;
	
	SET changesNotAllowed=(OLD.iniHPerson!=NEW.iniHPerson OR OLD.salHPerson!=NEW.salHPerson OR OLD.salExtraHPerson!=NEW.salExtraHPerson);
	SET changesNotAllowedEnd=(OLD.endHPerson IS NOT NULL AND OLD.endHPerson!=NEW.endHPerson);	

-- Checks if there are changes in the data
	IF (changesNotAllowed OR changesNotAllowedEnd) THEN
		SET msg = concat('Error: You only can update commentHPerson or endHPerson if this is null.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

-- Checks if the endHPerson is correct.
	IF (NEW.endHPerson IS NOT NULL) THEN
		SET msg = concat('Error: Invalid date for endHPerson (',cast(NEW.endHPerson as char), '). It must be greater than iniHPerson.');
		CALL dateGreaterThan(OLD.iniHPerson,NEW.endHPerson,msg);

		SET msg = concat('Error: Invalid date for endHPerson (',cast(NEW.endHPerson as char), '). It cannot be greater than now.');
		CALL dateGreaterOrEqualThan(NEW.endHPerson,CURDATE(),msg);
	END IF;

-- Check the date interval
	CALL checkHPduplicate(NEW.iniHPerson,NEW.endHPerson, NEW.idPerson);

END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- --------------------------- tMaterialInsert ---------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tMaterialInsert;

DELIMITER $$
CREATE TRIGGER tMaterialInsert BEFORE INSERT ON Material 
FOR EACH ROW BEGIN

-- Checks if the sal is positive.
	CALL isPositive(NEW.costMaterial,'costMaterial');
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- --------------------------- tMaterialUpdate ---------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tMaterialUpdate;

DELIMITER $$
CREATE TRIGGER tMaterialUpdate BEFORE UPDATE ON Material 
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);
	
-- We can not update the cost.
	IF (OLD.costMaterial!=NEW.costMaterial) THEN
		SET msg = concat('Error: You can not change the material cost.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;	
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- -------------------------------- tTaskInsert -------------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tTaskInsert;

DELIMITER $$
CREATE TRIGGER tTaskInsert BEFORE INSERT ON Task 
FOR EACH ROW BEGIN
	DECLARE thereAreInvalidData BOOLEAN DEFAULT false;
	DECLARE daysToAdd INTEGER DEFAULT 0;
	DECLARE iniProject DATE;
	DECLARE msg VARCHAR(128);
	DECLARE project BIGINT;	
	DECLARE projectState VARCHAR(4);

-- Gets the project of the task
	SET project = (SELECT idProject FROM Phase WHERE idPhase=NEW.idPhase);

-- Gets the state project. 
	SET projectState = (SELECT idState 
						FROM HistoryProject 
						WHERE idHProject = (SELECT MAX(idHProject) FROM HistoryProject WHERE idProject = project));

-- First checks the task state is compatible with the project state.
	IF (projectState IS NULL) THEN 
		SET msg = concat('Error: You can not insert tasks when the project has no state.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;

	ELSEIF (projectState='PLAN' AND NEW.idState!='PLAN') THEN
		SET msg = concat('Error: Invalid value for idState (',cast(NEW.idState as char),'). The state of a new task must be \'PLAN\'.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;

	ELSEIF (projectState='EJEC' AND NEW.idState!='EJEC') THEN 
		SET msg = concat('Error: Invalid value for idState (',cast(NEW.idState as char),'). The state of a new task must be \'EJEC\'.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;

	ELSEIF (projectState='TERM' OR projectState='CANC') THEN
		SET msg = concat('Error: You can not insert tasks when the project state is \'TERM\' or \'CANC\'.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

-- Checks the planned and real datas. We don't check the daysPlanTask and iniPlanTask.
	SET thereAreInvalidData = (NEW.daysRealTask IS NOT NULL OR NEW.hoursPlanTask IS NOT NULL OR NEW.hoursRealTask IS NOT NULL OR NEW.endPlanTask IS NOT NULL OR NEW.endRealTask IS NOT NULL OR NEW.costPlanTask IS NOT NULL OR NEW.costRealTask);

	IF (thereAreInvalidData) THEN 
		SET msg = concat('Error: You can not insert daysRealTask, the end dates, the hours or the costs.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

	IF (NEW.idState='PLAN') THEN
	-- Checks if the number of days is positive.
		CALL isPositive(NEW.daysPlanTask,'daysPlanProject');

	-- If iniPlanTask is defined, checks if is correct and establish endPlanTask. Also we check if the iniPhase is updated.
		IF (NEW.iniPlanTask IS NOT NULL) THEN
			CALL compatibleWithIniProject(project, NEW.iniPlanTask, 'iniPlanTask');
			SET daysToAdd = (NEW.daysPlanTask - 1);
			SET NEW.endPlanTask = DATE_ADD(NEW.iniPlanTask, INTERVAL daysToAdd DAY);
			CALL updateIniPhase(project,NEW.idPhase,NEW.iniPlanTask);
			CALL updateEndPhase(project,NEW.idPhase,NEW.endPlanTask);
		END IF;
	END IF;

	IF (NEW.idState='EJEC') THEN
		-- Set daysPlan to 0 because it cant be null		
		SET NEW.daysPlanTask = 0;	

		-- Checks if iniRealTask is null we dont continue.
		IF (NEW.iniRealTask IS NULL) THEN
			SET msg = concat('Error: You must define iniRealTask.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		ELSE
			CALL compatibleWithIniProject(project, NEW.iniRealTask, 'iniRealTask');
		END IF;

		CALL updateIniPhase(project,NEW.idPhase,NEW.iniRealTask);
		CALL updateEndPhase(project,NEW.idPhase,NEW.endRealTask);
	END IF;

	-- The initial progress for the task is 0%
	SET NEW.progressTask = 0;

END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- -------------------------------- tTaskUpdate -------------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tTaskUpdate;

DELIMITER $$
CREATE TRIGGER tTaskUpdate BEFORE UPDATE ON Task 
FOR EACH ROW BEGIN
	DECLARE changeBeforeInsertPlan, changeAfterInsertPlan, changeReal, thereAreChanges BOOLEAN DEFAULT false;
	DECLARE daysToAdd, hoursP, costP, costM INTEGER DEFAULT 0;
	DECLARE project BIGINT;
	DECLARE projectState VARCHAR(4);
	DECLARE msg VARCHAR(128);

-- Gets the project of the task
	SET project = (SELECT idProject FROM Phase WHERE idPhase=NEW.idPhase);

-- Booleans with the attribute changes.
	SET changeBeforeInsertPlan = (NEW.hoursPlanTask IS NOT NULL OR NEW.costPlanTask IS NOT NULL);
	SET changeAfterInsertPlan = (OLD.daysPlanTask!=NEW.daysPlanTask OR OLD.iniPlanTask!=NEW.iniPlanTask OR OLD.hoursPlanTask!=NEW.hoursPlanTask);
	SET changeAfterInsertPlan = (changeAfterInsertPlan OR OLD.endPlanTask!=NEW.endPlanTask OR OLD.costPlanTask!=NEW.costPlanTask);
	SET changeReal=(NEW.daysRealTask IS NOT NULL OR NEW.endRealTask IS NOT NULL OR NEW.hoursRealTask IS NOT NULL OR NEW.costRealTask IS NOT NULL);

-- We can not change data Task when the state is 'TERM' or 'CANC'.
-- ----------------------------------------------------------------------------------
	IF (OLD.idState='TERM' OR OLD.idState='CANC') THEN
		SET msg = concat('Error: You can not update a task with state \'TERM\' or \'CANC\'.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;

-- If the current state is 'PLAN' we can change some data or the state at 'PRPD' or 'CANC'.
-- ----------------------------------------------------------------------------------
	ELSEIF (OLD.idState='PLAN' AND (NEW.idState='PLAN' OR NEW.idState='PRPD')) THEN

	-- Check if it has changed the invalid attributes.
		IF (changeBeforeInsertPlan OR changeReal OR NEW.iniRealTask IS NOT NULL) THEN
			SET msg = concat('Error: You can not update daysRealTask, iniRealTask, the end dates, hours and costs when task state is \'PLAN\'.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		END IF;

	-- Recalculate endPlan if iniPlan or daysPlan were changed.
		SET thereAreChanges = ((OLD.iniPlanTask IS NULL AND NEW.iniPlanTask IS NOT NULL) OR OLD.iniPlanTask!=NEW.iniPlanTask);
		SET thereAreChanges = (thereAreChanges OR OLD.daysPlanTask!=NEW.daysPlanTask);
		
		IF (thereAreChanges) THEN
			CALL compatibleWithIniProject(project, NEW.iniPlanTask, 'iniPlanTask');
			CALL isPositive(NEW.daysPlanTask,'daysPlanTask');
			SET daysToAdd = (NEW.daysPlanTask - 1);
			SET NEW.endPlanTask = DATE_ADD(NEW.iniPlanTask, INTERVAL daysToAdd DAY);	
		END IF;

	-- If the new state is 'PRPD' we must check the next.
		IF (NEW.idState='PRPD') THEN

		-- Checks if iniPlanTask is null we dont continue.
			IF (NEW.iniPlanTask IS NULL) THEN
				SET msg = concat('Error: You must define iniPlanTask.');
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
			END IF;

		-- Calculate the costs and total hours.
			CALL calculateCostMaterialPlan(OLD.idTask, costM);
			CALL calculateCostHoursPersonPlan(OLD.idTask, costP, hoursP);
		
			SET daysToAdd = (NEW.daysPlanTask - 1);
			SET NEW.endPlanTask = DATE_ADD(NEW.iniPlanTask, INTERVAL daysToAdd DAY);
			SET NEW.costPlanTask = costM + costP;
			SET NEW.hoursPlanTask = hoursP;
		END IF;

	-- Check if the iniPlan or endPlan has changed
		IF (OLD.iniPlanTask!=NEW.iniPlanTask) THEN
			CALL updateIniPhase(project,NEW.idPhase,NEW.iniPlanTask);
		END IF;

		IF (OLD.endPlanTask!=NEW.endPlanTask) THEN
			CALL updateEndPhase(project,NEW.idPhase,NEW.endPlanTask);
		END IF;
	
-- If the current state is 'PRPD' we can define iniRealTask or the state at 'EJEC' or 'CANC'.
-- ----------------------------------------------------------------------------------
	ELSEIF (OLD.idState='PRPD' AND (NEW.idState='EJEC' OR NEW.idState='PRPD')) THEN

	-- Check if it has changed the invalid attributes.
		IF (changeAfterInsertPlan OR changeReal) THEN
			SET msg = concat('Error: You only can update iniRealTask or idState when task state is \'PRPD\'.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		END IF;

	-- If the new state is 'EJEC' we must check the next.
		IF (NEW.idState='EJEC') THEN

		-- Gets the state project. 
			SET projectState = (SELECT idState 
								FROM HistoryProject 
								WHERE idHProject = (SELECT MAX(idHProject) FROM HistoryProject WHERE idProject = project));

			IF (projectState!='EJEC') THEN
				SET msg = concat('Error: You can not change the state at \'EJEC\' until project state will be \'EJEC\'.');
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
			END IF;
	
		-- Checks if iniRealTask is null we dont continue.
			IF (NEW.iniRealTask IS NULL) THEN
				SET msg = concat('Error: You must define iniRealTask.');
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
			ELSE
				CALL compatibleWithIniProject(project, NEW.iniRealTask, 'iniRealTask');
			END IF;
		
		END IF;

	-- Check if the iniPlan has changed
		IF ((OLD.iniRealTask!=NEW.iniRealTask) OR (OLD.iniRealTask IS NULL AND NEW.iniRealTask IS NOT NULL)) THEN
			CALL updateIniPhase(project,NEW.idPhase,NEW.iniRealTask);
		END IF;


-- If the current state is 'EJEC' we can define the state at 'TERM' or 'CANC'.
-- ----------------------------------------------------------------------------------
	ELSEIF (OLD.idState='EJEC' AND (NEW.idState='TERM' OR NEW.idState='EJEC')) THEN
		-- Check if it has changed the invalid attributes.
		IF (changeAfterInsertPlan OR changeReal OR OLD.iniRealTask!=NEW.iniRealTask) THEN
			SET msg = concat('Error: You only can update idState when task state is \'EJEC\'.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		END IF;

		-- If the task ends, we must calculate the real data.
		IF (NEW.idState='TERM' OR NEW.progressTask=100) THEN
			CALL calculateCostMaterialReal(OLD.idTask, costM);
			CALL calculateCostHoursPersonReal(OLD.idTask, costP, hoursP);

			SET NEW.costRealTask = costM + costP;
			SET NEW.hoursRealTask = hoursP;

			SET NEW.endRealTask=(SELECT MAX(dayDateWorkload) 
								 FROM Workload 
								 WHERE (idTask=NEW.idTask));

			SET NEW.daysRealTask = DATEDIFF(NEW.endRealTask, NEW.iniRealTask) + 1;
			SET NEW.progressTask = 100;

			CALL updateEndPhase(project,NEW.idPhase,NEW.endRealTask);
		END IF;

-- If the new state is 'CANC' we can not change the attributes.
-- ----------------------------------------------------------------------------------
	ELSEIF (OLD.idState!=NEW.idState AND NEW.idState='CANC') THEN 
	
		IF (changeAfterInsertPlan OR changeReal OR OLD.iniRealTask!=NEW.iniRealTask) THEN  
			SET msg = concat('Error: You can not change the attributes.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		END IF;


-- If the new state is not the same than the previous and 'CANC', the state change is not allowed.
-- ----------------------------------------------------------------------------------
	ELSEIF (OLD.idState!=NEW.idState AND NEW.idState!='CANC') THEN  
		SET msg = concat('Error: Change state \'', cast(OLD.idState as char),'\' -> \'', cast(NEW.idState as char),'\' invalid.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

	-- If the task progress has changed, we must update the project progress.
	IF (OLD.progressTask != NEW.progressTask) THEN
		CALL updateProjectProgress(project, OLD.idTask, NEW.progressTask);
	END IF;

	IF (NEW.idState='TERM') THEN	
		CALL updateProjectProgress(project, OLD.idTask, 100);
	END IF;

END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ---------------------------- tPredecessorInsert ----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tPredecessorInsert;

DELIMITER $$
CREATE TRIGGER tPredecessorInsert BEFORE INSERT ON Predecessor
FOR EACH ROW BEGIN
	
	DECLARE phaseTask, phaseTaskPred, idProjectTask, idProjectTaskPred BIGINT;
	DECLARE msg VARCHAR(128);
    DECLARE state, statePred VARCHAR(4);

-- First we need the Phase 
	SET phaseTask = (SELECT idPhase FROM Task WHERE idTask=NEW.idTask);
	SET phaseTaskPred = (SELECT idPhase FROM Task WHERE idTask=NEW.idTaskPred);

-- Now get the id projects
	SET idProjectTask = (SELECT idProject FROM Phase WHERE idPhase=phaseTask);
	SET idProjectTaskPred = (SELECT idProject FROM Phase WHERE idPhase=phaseTaskPred);

-- Check if this projects are the same
	IF (idProjectTask != idProjectTaskPred) THEN
		SET msg = ('Error: The projects of the tasks are not the same.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

-- Check the Tasks State
    SET state = (SELECT idState FROM Task WHERE idTask=NEW.idTask);
    SET statePred = (SELECT idState FROM Task WHERE idTask=NEW.idTaskPred);

    IF (state='CANC' OR state='TERM' OR statePred='CANC' OR statePred='TERM') THEN
		SET msg = ('Error: You cannot link cancel or finished Task.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;


END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ---------------------------- tPredecessorUpdate ----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tPredecessorUpdate;

DELIMITER $$
CREATE TRIGGER tPredecessorUpdate BEFORE UPDATE ON Predecessor
FOR EACH ROW BEGIN

	DECLARE msg VARCHAR(128);

-- Check if the idTask has changed
	IF (OLD.idTask!=NEW.idTask OR OLD.idTaskPred!=NEW.idTaskPred) THEN
		SET msg = ('Error: You cannot change the tasks.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- tAssigMatInsert ------------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tAssigMatInsert;

DELIMITER $$
CREATE TRIGGER tAssigMatInsert BEFORE INSERT ON AssignmentMaterial 
FOR EACH ROW BEGIN
	DECLARE costM INTEGER;
	DECLARE msg VARCHAR(128);
	DECLARE stateTask VARCHAR(4);

-- Gets the state task.
	SET stateTask = (SELECT idState FROM Task WHERE idTask=NEW.idTask);

-- In function if the task state we do one thing or other. In the case of PLAN.
	IF (stateTask='PLAN') THEN
		SET NEW.planAssigMat=true;
		SET NEW.realAssigMat=false;
		SET NEW.unitsRealAssigMat=0;
		SET NEW.costRealAssigMat=0;

	-- Checks if the units are greater than 0 and not null.
		IF (NEW.unitsPlanAssigMat IS NULL) THEN
			SET msg = ('Error: You must define unitsPlanAssigMat.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		END IF;

		CALL isPositive(NEW.unitsPlanAssigMat ,'unitsPlanAssigMat');

	-- Calculates the total cost.
		SET costM = (SELECT costMaterial FROM Material WHERE idMaterial=NEW.idMaterial);
		SET NEW.costPlanAssigMat = (costM * NEW.unitsPlanAssigMat);			
	
-- In the case of EJEC
	ELSEIF (stateTask='EJEC') THEN
		SET NEW.planAssigMat=false;
		SET NEW.realAssigMat=true;
		SET NEW.unitsPlanAssigMat=0;
		SET NEW.costPlanAssigMat=0;

	-- Checks if the units are greater than 0 and not null.
		IF (NEW.unitsRealAssigMat IS NULL) THEN
			SET msg = ('Error: You must define unitsRealAssigMat.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		END IF;

		CALL isPositive(NEW.unitsRealAssigMat ,'unitsRealAssigMat');

	-- Calculates the total cost.
		SET costM = (SELECT costMaterial FROM Material WHERE idMaterial=NEW.idMaterial);
		SET NEW.costRealAssigMat = (costM * NEW.unitsRealAssigMat);		

	ELSE 
		SET msg = ('Error: You only can assign materials when then task state is \'PLAN\' or \'EJEC\'.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- tAssigMatUpdate ------------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tAssigMatUpdate;

DELIMITER $$
CREATE TRIGGER tAssigMatUpdate BEFORE UPDATE ON AssignmentMaterial 
FOR EACH ROW BEGIN
	DECLARE costM INTEGER;
	DECLARE msg VARCHAR(128);
	DECLARE changeInPlan, changeInReal, changeInUnitP, changeInUnitR, changeInMaterial BOOLEAN DEFAULT false;
	DECLARE stateTask VARCHAR(4);

-- It is not allowed change the cost
	IF (OLD.idTask != NEW.idTask OR OLD.costPlanAssigMat!=NEW.costPlanAssigMat OR OLD.costRealAssigMat!=NEW.costRealAssigMat) THEN
		SET msg = ('Error: You can not update idTask or costAssigMat.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

-- Check if there are changes in the attributes
	SET changeInPlan = (OLD.planAssigMat!=NEW.planAssigMat);
	SET changeInReal = (OLD.realAssigMat!=NEW.realAssigMat);
	SET changeInUnitP = (OLD.unitsPlanAssigMat!=NEW.unitsPlanAssigMat);
	SET changeInUnitR = (OLD.unitsRealAssigMat!=NEW.unitsRealAssigMat);
	SET changeInMaterial = (OLD.idMaterial!=NEW.idMaterial);

-- Gets the state task.
	SET stateTask = (SELECT idState FROM Task WHERE idTask=NEW.idTask);

-- In function of the task state we do some things or others
	IF (stateTask='PLAN') THEN  
		IF (changeInPlan OR changeInReal OR changeInUnitR) THEN
			SET msg = ('Error: You can not update planAssigMat, realAssigMat or unitsRealAssigMat when the task state is \'PLAN\'.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		END IF;

		IF (changeInUnitP OR changeInMaterial) THEN
			SET costM = (SELECT costMaterial FROM Material WHERE idMaterial=NEW.idMaterial);
			SET NEW.costPlanAssigMat = (costM * NEW.unitsPlanAssigMat);	
		END IF;
	
	ELSEIF (stateTask='EJEC') THEN
		IF (changeInMaterial OR changeInPlan OR changeInUnitP) THEN
			SET msg = ('Error: You can not update idMaterial, planAssigMat or unitsPlanAssigMat when the task state is \'EJEC\'.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		END IF;

		IF (changeInUnitR OR (changeInReal AND NEW.realAssigMat=true)) THEN

		-- Checks if the units are greater than 0 and not null.
			IF (NEW.unitsRealAssigMat IS NULL) THEN
				SET msg = ('Error: You must define unitsRealAssigMat.');
				SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
			END IF;

			CALL isPositive(NEW.unitsRealAssigMat ,'unitsRealAssigMat');

			SET costM = (SELECT costMaterial FROM Material WHERE idMaterial=NEW.idMaterial);
			SET NEW.costRealAssigMat = (costM * NEW.unitsRealAssigMat);	

		ELSEIF (changeInReal AND NEW.realAssigMat=false) THEN
			SET NEW.unitsRealAssigMat=0;
			SET NEW.costRealAssigMat=0;
		
		END IF;

	ELSE 
		SET msg = ('Error: You only can update the materials assigment when then task state is \'PLAN\' or \'EJEC\'.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------------ tFreeDayInsert ------------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tFreeDayInsert;

DELIMITER $$
CREATE TRIGGER tFreeDayInsert BEFORE INSERT ON FreeDay 
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(256);
	DECLARE correctAttributes BOOLEAN DEFAULT false;

	SET correctAttributes = ((NEW.weekDayFreeDay IS NOT NULL AND NEW.iniFreeDay IS NULL AND NEW.endFreeDay IS NULL) OR
					 		(NEW.weekDayFreeDay IS NULL AND NEW.iniFreeDay IS NOT NULL AND NEW.endFreeDay IS NOT NULL));

-- Check the attributes value.
	IF (!correctAttributes) THEN
		SET msg = ('Error: If weekDayFreeDay is not null, then iniFreeDay and endFreeDay must be null. And vice versa.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

-- Check the data FreeDay
	CALL checkFreeDayData(NEW.weekDayFreeDay, NEW.iniFreeDay, NEW.endFreeDay);

END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- tFreeDayUpdate -----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tFreeDayUpdate;

DELIMITER $$
CREATE TRIGGER tFreeDayUpdate BEFORE UPDATE ON FreeDay 
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);
	DECLARE correctUpdateWeekDay, correctUpdateDates BOOLEAN DEFAULT false;

	SET correctUpdateWeekDay = ((OLD.weekDayFreeDay IS NOT NULL AND NEW.weekDayFreeDay IS NOT NULL) 
							   	OR (OLD.weekDayFreeDay IS NULL AND NEW.weekDayFreeDay IS NULL));

	SET correctUpdateDates = ((OLD.iniFreeDay IS NULL AND OLD.endFreeDay IS NULL AND NEW.iniFreeDay IS NULL AND NEW.endFreeDay IS NULL) OR
					(OLD.iniFreeDay IS NOT NULL AND OLD.endFreeDay IS NOT NULL AND NEW.iniFreeDay IS NOT NULL AND NEW.endFreeDay IS NOT NULL));

-- Check the attributes value.
	IF (!correctUpdateWeekDay OR !correctUpdateDates) THEN
		SET msg = ('Error: You cannot change the value of an attribute if it is null.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

-- Check the data FreeDay
	CALL checkFreeDayData(NEW.weekDayFreeDay, NEW.iniFreeDay, NEW.endFreeDay);
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- --------------------------- tProjectFreeDayInsert --------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tProjectFreeDayInsert;

DELIMITER $$
CREATE TRIGGER tProjectFreeDayInsert BEFORE INSERT ON ProjectFreeDay 
FOR EACH ROW BEGIN
	CALL checkProjectFreeDay(NEW.idProject);
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- --------------------------- tProjectFreeDayUpdate --------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tProjectFreeDayUpdate;

DELIMITER $$
CREATE TRIGGER tProjectFreeDayUpdate BEFORE UPDATE ON ProjectFreeDay 
FOR EACH ROW BEGIN
	CALL checkProjectFreeDay(NEW.idProject);
END$$
DELIMITER ;

-- ----------------------------------------------------------------------------------
-- ----------------------------- tWorkloadInsert ------------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tWorkloadInsert;

DELIMITER $$
CREATE TRIGGER tWorkloadInsert BEFORE INSERT ON Workload 
FOR EACH ROW BEGIN
	DECLARE aux,hours,extraHours INTEGER DEFAULT 0;
	DECLARE conclude BOOLEAN DEFAULT false;
	DECLARE iniTask DATE;
	DECLARE msg VARCHAR(128);
	DECLARE taskState VARCHAR(4);
	DECLARE person BIGINT;

-- Checks if the person is assigned in the task.
	SET aux = (SELECT COUNT(idHPerson) 
			   FROM AssignmentPerson	
			   WHERE idTask=NEW.idTask AND idHPerson=NEW.idHPerson);

	IF (aux != 1) THEN 	
		SET msg = concat('Error: This person is not assigned at this task.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

-- Checks if concludeAssigPerson is false.
	SET conclude = (SELECT concludeAssigPerson
			   		FROM AssignmentPerson	
			   		WHERE idTask=NEW.idTask AND idHPerson=NEW.idHPerson);

	IF (conclude = true) THEN 	
		SET msg = concat('Error: This person has concluded her job in this task.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

-- Checks if the day is working day.
	CALL isWorkingDay(NEW.dayDateWorkload);

-- Checks if the ini date is compatible with the ini date task.
	SET iniTask = (SELECT iniRealTask
				   FROM Task 
				   WHERE idTask=NEW.idTask);

	IF (iniTask > NEW.dayDateWorkload) THEN 
		SET msg = concat('Error: The dayDateWorkload is lower than the initial date task.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

-- Checks if the day coincide with any TimeOff
	SET person = (SELECT idPerson FROM HistoryPerson WHERE idHPerson=NEW.idHPerson);
	
	SET aux = (SELECT COUNT(idTimeOff) 
			   FROM TimeOff	
			   WHERE idPerson=person AND NEW.dayDateWorkload>=iniTimeOff AND (endTimeOff IS NULL OR NEW.dayDateWorkload<=endTimeOff));

	IF (aux>0) THEN 	
		SET msg = concat('Error: This person has TimeOff assigned.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

-- Checks if the hours are positive.
	CALL isPositive(NEW.hoursWorkload,'hoursWorkload');
	CALL isPositive(NEW.extraHoursWorkload,'extraHoursWorkload');

-- Get the hours worked this day.
	SET hours = (SELECT IFNULL(SUM(hoursWorkload),0) 
				 FROM Workload 
				 WHERE dayDateWorkload=NEW.dayDateWorkload AND idHPerson=NEW.idHPerson);

	SET extraHours = (SELECT IFNULL(SUM(extraHoursWorkload),0)
					  FROM Workload 
					  WHERE dayDateWorkload=NEW.dayDateWorkload AND idHPerson=NEW.idHPerson);
		
-- Normal hours
	IF ((hours+NEW.hoursWorkload) > 8) THEN 
		SET msg = concat('Error: This employer has worked ',cast(hours as char),'h this day. With this hoursWorkload (');
		SET msg = concat(msg,cast(NEW.hoursWorkload as char),'h) the maximum (8h) is exceed.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

-- Extra hours
	IF ((extraHours+NEW.extraHoursWorkload) > 8) THEN 
		SET msg = concat('Error: This employer has worked ',cast(hours as char),' extra hours this day. With this extraHoursWorkload (');
		SET msg = concat(msg,cast(NEW.extraHoursWorkload as char),'h) the maximum (8h) is exceed.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- tWorkloadUpdate ------------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tWorkloadUpdate;

DELIMITER $$
CREATE TRIGGER tWorkloadUpdate BEFORE UPDATE ON Workload 
FOR EACH ROW BEGIN
	DECLARE hours,extraHours INTEGER DEFAULT 0;
	DECLARE thereAreChanges, conclude BOOLEAN DEFAULT false;
	DECLARE msg VARCHAR(128);

-- Checks if concludeAssigPerson is false.
	SET conclude = (SELECT concludeAssigPerson
			   		FROM AssignmentPerson	
			   		WHERE idTask=NEW.idTask AND idHPerson=NEW.idHPerson);

	IF (conclude = true) THEN 	
		SET msg = concat('Error: This person has concluded her job in this task.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

-- Checks if there are changes in the attributes
	SET thereAreChanges = (OLD.idWorkload!=NEW.idWorkload OR OLD.idTask!=NEW.idTask OR 
						   OLD.idHPerson!=NEW.idHPerson OR OLD.dayDateWorkload!=NEW.dayDateWorkload);

	IF (thereAreChanges) THEN 
		SET msg = concat('Error: You only can update hoursWorkload and extraHoursWorkload.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

-- Checks if the hours have been changed.
	IF (OLD.hoursWorkload!=NEW.hoursWorkload) THEN
		CALL isPositive(NEW.hoursWorkload,'hoursWorkload');

		SET hours = (SELECT IFNULL(SUM(hoursWorkload),0) 
					 FROM Workload 
					 WHERE dayDateWorkload=NEW.dayDateWorkload AND idHPerson=NEW.idHPerson AND idWorkload!=OLD.idWorkload);
		
		IF ((hours+NEW.hoursWorkload) > 8) THEN 
			SET msg = concat('Error: This employer has worked ',cast(hours as char),'h this day. With this hoursWorkload (');
			SET msg = concat(msg,cast(NEW.hoursWorkload as char),'h) the maximum (8h) is exceed.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
		END IF;
	END IF;

	IF (OLD.extraHoursWorkload!=NEW.extraHoursWorkload) THEN
		CALL isPositive(NEW.extraHoursWorkload,'extraHoursWorkload');

		SET extraHours = (SELECT IFNULL(SUM(extraHoursWorkload),0) 
						  FROM Workload 
						  WHERE dayDateWorkload=NEW.dayDateWorkload AND idHPerson=NEW.idHPerson AND idWorkload!=OLD.idWorkload);

		IF ((extraHours+NEW.extraHoursWorkload) > 8) THEN 
			SET msg = concat('Error: This employer has worked ',cast(hours as char),' extra hours this day. With this extraHoursWorkload (');
			SET msg = concat(msg,cast(NEW.extraHoursWorkload as char),'h) the maximum (8h) is exceed.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
		END IF;
	END IF;

END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ---------------------------- tAssigPersonInsert ----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tAssigPersonInsert;

DELIMITER $$
CREATE TRIGGER tAssigPersonInsert BEFORE INSERT ON AssignmentPerson 
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);
	DECLARE endPerson DATE;
	DECLARE taskState VARCHAR(4);

-- Checks if the task is in ejecution.
	SET taskState = (SELECT idState
				   	FROM Task 
				   	WHERE idTask=NEW.idTask);

	IF (taskState!='EJEC') THEN 	
		SET msg = concat('Error: You can not assign work when the task state is different at \'EJEC\'.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

-- Checks the end date of the history person.
	SET endPerson = (SELECT endHPerson
				   	  FROM HistoryPerson 
				   	  WHERE idHPerson=NEW.idHPerson);

	IF (endPerson IS NOT NULL) THEN 	
		SET msg = concat('Error: This person is not working currenty with this role.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;


-- Checks if concludeAssigPerson is false
	IF (NEW.concludeAssigPerson) THEN 
		SET msg = concat('Error: This assignment can not conclude because there is not assigned workload.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

-- Checks if the hours and cost is null
	IF (NEW.totalHoursAssigPerson IS NOT NULL OR NEW.totalExtraHoursAssigPerson IS NOT NULL OR NEW.totalCostAssigPerson IS NOT NULL) THEN
		SET msg = concat('Error: You can not define the hours or cost. These are derivative datas.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ---------------------------- tAssigPersonUpdate ----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tAssigPersonUpdate;

DELIMITER $$
CREATE TRIGGER tAssigPersonUpdate BEFORE UPDATE ON AssignmentPerson 
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);
	DECLARE hours, extraHours, cost, extraCost INTEGER DEFAULT 0;

	-- Checks if the assignment has concluded.
	IF (OLD.concludeAssigPerson) THEN
		SET msg = concat('Error: You can not update this assignment because it has concluded.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

	-- Checks if the task or the HPerson has changed.
	IF (OLD.idTask != NEW.idTask OR OLD.idHPerson != NEW.idHPerson) THEN
		SET msg = concat('Error: You can not update the Task or the HistoryPerson.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

	-- Checks if there are changes.
	IF (NEW.totalHoursAssigPerson IS NOT NULL OR NEW.totalExtraHoursAssigPerson IS NOT NULL OR NEW.totalCostAssigPerson IS NOT NULL) THEN
		SET msg = concat('Error: You can not define the hours or cost. These are derivative datas.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

	-- Calculate the hours and costs.
	IF (OLD.concludeAssigPerson=false AND NEW.concludeAssigPerson=true) THEN 
		SET hours = (SELECT IFNULL(SUM(hoursWorkload),0)
					 FROM Workload 
					 WHERE idTask=NEW.idTask AND idHPerson=NEW.idHPerson);

		SET extraHours = (SELECT IFNULL(SUM(extraHoursWorkload),0)
						  FROM Workload 
						  WHERE idTask=NEW.idTask AND idHPerson=NEW.idHPerson);

		SET cost = (SELECT salHPerson FROM HistoryPerson WHERE idHPerson=NEW.idHPerson);
		SET extraCost = (SELECT salExtraHPerson FROM HistoryPerson WHERE idHPerson=NEW.idHPerson);
	
	-- Final assignment
		SET NEW.totalHoursAssigPerson = hours;
		SET NEW.totalExtraHoursAssigPerson = extraHours;
		SET NEW.totalCostAssigPerson = (hours * cost) + (extraHours * extraCost);
	END IF;
END$$
DELIMITER ;

-- ----------------------------------------------------------------------------------
-- ---------------------------- tAssigPersonDelete ----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tAssigPersonDelete;

DELIMITER $$
CREATE TRIGGER tAssigPersonDelete AFTER DELETE ON AssignmentPerson 
FOR EACH ROW BEGIN
	
	DELETE FROM Workload WHERE idTask=OLD.idTask AND idHPerson=OLD.idHPerson;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ---------------------------- tAssigProfileInsert ---------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tAssigProfileInsert;

DELIMITER $$
CREATE TRIGGER tAssigProfileInsert BEFORE INSERT ON AssignmentProfile
FOR EACH ROW BEGIN
	DECLARE costPerHour INTEGER DEFAULT 0;

-- Checks if the task is in planification.
	CALL checkIfTaskIsPlan(NEW.idTask);

-- Calculate the cost of the profile assignment.
	SET costPerHour = (SELECT salProfCatg FROM ProfessionalCategory WHERE idProfCatg=NEW.idProfCatg);
	SET NEW.costAssigProf = (NEW.hoursPerPersonAssigProf * costPerHour) * NEW.unitsAssigProf;

END$$
DELIMITER ;

-- ----------------------------------------------------------------------------------
-- ---------------------------- tAssigProfileUpdate ---------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tAssigProfileUpdate;

DELIMITER $$
CREATE TRIGGER tAssigProfileUpdate BEFORE UPDATE ON AssignmentProfile
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);
	DECLARE costPerHour INTEGER DEFAULT 0;

-- We can not update the cost
	IF (OLD.idTask != NEW.idTask OR OLD.costAssigProf != NEW.costAssigProf) THEN 	
		SET msg = concat('Error: You can not update idTask or costAssigProf.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

-- Checks if the task is in planification.
	CALL checkIfTaskIsPlan(OLD.idTask);

-- Calculate the cost of the profile assignment.
	IF (OLD.idProfCatg != NEW.idProfCatg OR OLD.hoursPerPersonAssigProf != NEW.hoursPerPersonAssigProf OR OLD.unitsAssigProf != NEW.unitsAssigProf) THEN
		SET costPerHour = (SELECT salProfCatg FROM ProfessionalCategory WHERE idProfCatg=NEW.idProfCatg);
		SET NEW.costAssigProf = (NEW.hoursPerPersonAssigProf * costPerHour) * NEW.unitsAssigProf;
	END IF;

END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ---------------------------- tAssigProfileDelete ---------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tAssigProfileDelete;

DELIMITER $$
CREATE TRIGGER tAssigProfileDelete AFTER DELETE ON AssignmentProfile 
FOR EACH ROW BEGIN
	
-- Checks if the task is in planification.
	CALL checkIfTaskIsPlan(OLD.idTask);
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- tProjecMgmtInsert ----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tProjecMgmtInsert;

DELIMITER $$
CREATE TRIGGER tProjecMgmtInsert BEFORE INSERT ON ProjectMgmt
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);

	IF (NEW.endProjectMgmt IS NOT NULL AND NEW.iniProjectMgmt > NEW.endProjectMgmt) THEN
		SET msg = concat('Error: The date endProjectMgmt is lower than iniProjectMgmt.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- tProjecMgmtUpdate ----------------------------------
-- ----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS tProjecMgmtUpdate;

DELIMITER $$
CREATE TRIGGER tProjecMgmtUpdate BEFORE UPDATE ON ProjectMgmt
FOR EACH ROW BEGIN
	DECLARE msg VARCHAR(128);

	IF (NEW.endProjectMgmt IS NOT NULL AND NEW.iniProjectMgmt > NEW.endProjectMgmt) THEN
		SET msg = concat('Error: The date endProjectMgmt is lower than iniProjectMgmt.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

END$$
DELIMITER ;
