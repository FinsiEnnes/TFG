-- ----------------------------------------------------------------------------------
-- -------------------------------- checkBools --------------------------------------
-- ----------------------------------------------------------------------------------
-- Throws a exception if both bools are false.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkBools;

DELIMITER $$
CREATE PROCEDURE checkBools (IN num1 TINYINT(1), IN num2 TINYINT(1), IN attrib VARCHAR(45))
BEGIN
	DECLARE msg VARCHAR(128);
	set msg = concat('Error: In ',attrib,'. At least one of them must be 1 (true).');
	IF (num1 = 0 AND num2 = 0) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ---------------------------------- isBool ----------------------------------------
-- ----------------------------------------------------------------------------------
-- Checks if the tinyint has 0 or 1 as value.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS isBool;

DELIMITER $$
CREATE PROCEDURE isBool (IN num TINYINT(1), IN attrib VARCHAR(45))
BEGIN
	DECLARE msg VARCHAR(128);
	set msg = concat('Error: Invalid value for ',attrib,' (',cast(num as char), '). It must be 0 (false) or 1 (true).');
	IF (num!=0 AND num!=1) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- -------------------------------- isPositive --------------------------------------
-- ----------------------------------------------------------------------------------
-- If the number of the param is negative throws a exception.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS isPositive;

DELIMITER $$
CREATE PROCEDURE isPositive (IN num INTEGER, IN attrib VARCHAR(45))
BEGIN
	DECLARE msg VARCHAR(128);
	set msg = concat('Error: Invalid value for ',attrib,' (',cast(num as char), '). It must be positive.');
	IF (num IS NOT NULL AND num < 0) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------------- withinTheRange -----------------------------------
-- ----------------------------------------------------------------------------------
-- This procedure checks that 'valueToCheck' is in the range minInt-maxInt.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS withinTheRange;

DELIMITER $$
CREATE PROCEDURE withinTheRange (IN valueToCheck INTEGER, IN minInt INTEGER, IN maxInt INTEGER, IN msg VARCHAR(128))
BEGIN
	IF (valueToCheck < minInt OR valueToCheck > maxInt) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- dateGreaterThan ------------------------------------
-- ----------------------------------------------------------------------------------
-- Checks that the date 'iniDate' is greater than 'endDate'.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS dateGreaterThan;

DELIMITER $$
CREATE PROCEDURE dateGreaterThan (IN iniDate DATE, IN endDate DATE, IN msg VARCHAR(128))
BEGIN
	IF (iniDate >= endDate) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------- dateGreaterOrEqualThan ---------------------------------
-- ----------------------------------------------------------------------------------
-- Checks that the date 'iniDate' is greater or equal than 'endDate'.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS dateGreaterOrEqualThan;

DELIMITER $$
CREATE PROCEDURE dateGreaterOrEqualThan (IN iniDate DATE, IN endDate DATE, IN msg VARCHAR(128))
BEGIN
	IF (iniDate > endDate) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------------- isWorkingDay -------------------------------------
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS isWorkingDay;

DELIMITER $$
CREATE PROCEDURE isWorkingDay (IN thisDate DATE)
BEGIN
	DECLARE msg VARCHAR(128);
	DECLARE aux INTEGER DEFAULT 0;

	SET msg = concat('Error: Invalid value for dayDateWorkload (',cast(thisDate as char), '). This is a not working day.');

-- weekDayBlacklist	
	SET aux = (SELECT COUNT(idFreeDay)
			   FROM FreeDay 
			   WHERE weekDayFreeDay IS NOT NULL AND weekDayFreeDay = WEEKDAY(thisDate));

	
	IF (aux > 0) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

-- iniFreeDay and endFreeDay
	SET aux = (SELECT COUNT(idFreeDay)
			   FROM FreeDay 
			   WHERE iniFreeDay IS NOT NULL AND endFreeDay IS NOT NULL AND thisDate BETWEEN iniFreeDay AND endFreeDay);

	IF (aux > 0) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- checkProjectData -----------------------------------
-- ----------------------------------------------------------------------------------
-- Checks the data of one Project
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkProjectData;

DELIMITER $$
CREATE PROCEDURE checkProjectData (IN stateDate DATE, IN budget BIGINT, IN customer BIGINT, IN contactDataAreNull BOOLEAN)
BEGIN
	DECLARE msg VARCHAR(128);

-- Check if the budget project is positive.
	CALL isPositive(budget,'budgetProject');

-- Checks if the stateDate is correct (not greater than now).
	SET msg = concat('Error: Invalid stateDateProject (',cast(stateDate as char), '). It must not be greater than the current date.');
	CALL dateGreaterOrEqualThan(stateDate,CURDATE(),msg);

-- If the customer is assinged then checks if there are contact datas defined.
	IF (customer IS NOT NULL AND contactDataAreNull) THEN 
		SET msg = concat('Error: You must define the data contact (nif, name, surname and email) because there is a customer assigned.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------------- checkIniDateTask ---------------------------------
-- ----------------------------------------------------------------------------------
-- Checks if the task iniPlan are compatible with the change of project iniPlan.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkIniDateTask;

DELIMITER $$
CREATE PROCEDURE checkIniDateTask(IN project BIGINT, IN newProjectIniPlan DATE)
BEGIN
 	DECLARE done INT DEFAULT FALSE;
	DECLARE iniTask DATE;
	DECLARE msg VARCHAR(128);
	DECLARE state VARCHAR(4);
	DECLARE cur1 CURSOR FOR SELECT iniPlanTask, idState FROM Task WHERE idProject = project;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	OPEN cur1;

	read_loop: LOOP
	FETCH cur1 INTO iniTask,state;
	IF done THEN
	  LEAVE read_loop;
	END IF;

-- If the task was canceled then is compatible.
	IF (state!='CANC' AND iniTask<newProjectIniPlan) THEN 
		SET msg = concat('Error: There are tasks that have a iniPlan previous at this iniPlanProject (',cast(newProjectIniPlan as char),').');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
	END LOOP;

	CLOSE cur1;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- -------------------------- checkUpdatedProjectData -------------------------------
-- ----------------------------------------------------------------------------------
-- Checks the data of one Project when is updated. 
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkUpdatedProjectData;

DELIMITER $$
CREATE PROCEDURE checkUpdatedProjectData (IN project BIGINT, IN iniPlanOld DATE, IN iniPlanNew DATE, IN endPlan DATE, IN iniReal DATE, IN endReal DATE)
BEGIN
	DECLARE msg VARCHAR(128);
	DECLARE projectState VARCHAR(4) DEFAULT NULL;

-- Checks if iniDate goes before endDate.
	IF (iniPlanOld!=iniPlanNew) THEN

-- Get the current project state.
		SET projectState = (SELECT idState 
							FROM HistoryProject 
							WHERE idHProject = (SELECT MAX(idHProject) FROM HistoryProject WHERE idProject = NEW.idProject));

-- We only can change the iniData when the project is PLAN or the project has not state.
		IF (projectState!='PLAN') THEN
			SET msg = concat('Error: You only can update iniPlanProject when the project is in planification');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		END IF; 

-- Now check if the task iniDate is compatible.
		CALL checkIniDateTask(project,iniPlanNew);
	END IF;

	SET msg = concat('Error: Invalid endPlanProject (',cast(endPlan as char), '). It must be greater than iniPlanProject.');
	CALL dateGreaterThan(iniPlanNew,endPlan,msg);

	SET msg = concat('Error: Invalid endRealProject (',cast(endReal as char), '). It must be greater than iniRealProject.');
	CALL dateGreaterThan(iniReal,endReal,msg);	
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- -------------------------- checkHistoryProjectData -------------------------------
-- ----------------------------------------------------------------------------------
-- Checks the datas of HistoryProject.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkHistoryProjectData;

DELIMITER $$
CREATE PROCEDURE checkHistoryProjectData (IN previousIdHProject BIGINT, IN idState VARCHAR(45), IN previousStateEndDate DATE, IN iniHProject DATE, IN endHProject DATE)
BEGIN
	DECLARE msg VARCHAR(128);
-- If it is the first history the state must be 'PLAN'.
	IF (previousIdHProject IS NULL AND idState != 'PLAN') THEN
		SET msg = concat('Error: Invalid value for idState (',cast(idState as char), '). The first state of one project must be \'PLAN\'.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
-- If it is other history checks the previous history endDate.
	IF (previousIdHProject IS NOT NULL AND previousStateEndDate IS NULL) THEN 
		SET msg = concat('Error: The current status of the project still has no end date.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
-- If it is other history checks if the iniDate is greater than previous history endDate.
	IF (previousIdHProject IS NOT NULL AND previousStateEndDate IS NOT NULL) THEN 
		SET msg = concat('Error: Invalid value for iniHProject (',cast(iniHProject as char));
		SET msg = concat(msg,'). It must be greater or equal than the previous endHProject.');
		CALL dateGreaterOrEqualThan(previousStateEndDate,iniHProject,msg);
	END IF;
-- Checks the endDate if this was defined.
	IF (endHProject IS NOT NULL) THEN 
		SET msg = concat('Error: Invalid value for endHProject (',cast(endHProject as char),'). It must be greater than iniHProject.');
		CALL dateGreaterThan(iniHProject,endHProject,msg);
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- -------------------------- checkChangeProjectState -------------------------------
-- ----------------------------------------------------------------------------------
-- Checks if the task states are compatible with the change of project state.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkChangeProjectState;

DELIMITER $$
CREATE PROCEDURE checkChangeProjectState(IN currentState VARCHAR(4), IN newState VARCHAR(4))
BEGIN
	DECLARE msg VARCHAR(128);
	DECLARE validChange1, validChange2, validChange3 BOOLEAN DEFAULT 0;

	SET validChange1 = (currentState='PLAN' AND newState='EJEC');
	SET validChange2 = (currentState='EJEC' AND newState='TERM');
	SET validChange3 = ((currentState='PLAN' OR currentState='EJEC') AND newState='CANC');

-- Checks if the change state is valid.
	IF (NOT (validChange1 OR validChange2 OR validChange3)) THEN
		SET msg = concat('Error: The change \'',cast(currentState as char),'\' -> \'',cast(newState as char),'\' is not possible');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- -------------------------- checkIfTaskAreCompatible ------------------------------
-- ----------------------------------------------------------------------------------
-- Checks if the task states are compatible with the change of project state.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkIfTaskAreCompatible;

DELIMITER $$
CREATE PROCEDURE checkIfTaskAreCompatible(IN project BIGINT, IN newProjectState VARCHAR(4))
BEGIN
 	DECLARE done INT DEFAULT FALSE;
	DECLARE phase BIGINT;
	DECLARE counter INTEGER DEFAULT 0;
	DECLARE msg VARCHAR(128);
	DECLARE cur1 CURSOR FOR SELECT idPhase FROM Phase WHERE idProject = project;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	OPEN cur1;

	read_loop: LOOP
	FETCH cur1 INTO phase;
	IF done THEN
	  LEAVE read_loop;
	END IF;

-- If the new project state is EJEC task must be planned.
	IF (newProjectState='EJEC') THEN	
		SET counter = (SELECT COUNT(idTask)
					   FROM Task
					   WHERE idPhase = phase AND idState!='PRPD' AND idState!='CANC');
		
		IF (counter>0) THEN
			SET msg = concat('Error: There are tasks that have not yet been planned.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		END IF;
			
-- If the new project state is TERM task must have finnished.
	ELSEIF (newProjectState='TERM') THEN
		SET counter = (SELECT COUNT(idTask)
					   FROM Task
					   WHERE idPhase = phase AND idState!='TERM' AND idState!='CANC');
		
		IF (counter>0) THEN
			SET msg = concat('Error: There are tasks that have not yet been finnished.');
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
		END IF;	
		
	END IF;

	END LOOP;

	CLOSE cur1;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------------ updateTaskToCANC ----------------------------------
-- ----------------------------------------------------------------------------------
-- Update all the task setting the task state at CANC.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS updateTaskToCANC;

DELIMITER $$
CREATE PROCEDURE updateTaskToCANC(IN project BIGINT, IN newProjectState VARCHAR(4))
BEGIN
 	DECLARE done INT DEFAULT FALSE;
	DECLARE phase BIGINT;
	DECLARE counter INTEGER DEFAULT 0;
	DECLARE msg VARCHAR(128);
	DECLARE cur1 CURSOR FOR SELECT idPhase FROM Phase WHERE idProject = project;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	OPEN cur1;

	read_loop: LOOP
	FETCH cur1 INTO phase;
	IF done THEN
	  LEAVE read_loop;
	END IF;
	
-- Update all the task of the project using the Phase
	UPDATE Task
	SET idState = newProjectState
	WHERE idPhase = phase;

	END LOOP;

	CLOSE cur1;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- -------------------------------- getPlanData -------------------------------------
-- ----------------------------------------------------------------------------------
-- Calculate all the plan data task in a Project.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS getPlanData;

DELIMITER $$
CREATE PROCEDURE getPlanData(IN project BIGINT, INOUT totalDays INTEGER, INOUT totalHours INTEGER, INOUT totalCost INTEGER, INOUT endMax DATE)
BEGIN
	DECLARE days,hours,cost INTEGER DEFAULT 0;
	DECLARE ennd DATE DEFAULT CURDATE() + INTERVAL 1 DAY;

 	DECLARE done INT DEFAULT FALSE;
	DECLARE phase BIGINT;
	DECLARE counter INTEGER DEFAULT 0;
	DECLARE msg VARCHAR(128);
	DECLARE cur1 CURSOR FOR SELECT idPhase FROM Phase WHERE idProject = project;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	OPEN cur1;

	read_loop: LOOP
	FETCH cur1 INTO phase;
	IF done THEN
	  LEAVE read_loop;
	END IF;

	SET days = (SELECT SUM(daysPlanTask) FROM Task WHERE idPhase=phase);
	SET totalDays = totalDays + Days;

	SET hours = (SELECT SUM(hoursPlanTask) FROM Task WHERE idPhase=phase);
	SET totalHours = totalHours + hours;

	SET cost = (SELECT SUM(costPlanTask) FROM Task WHERE idPhase=phase);
	SET totalCost = totalCost + cost;

	SET ennd = (SELECT MAX(endPlanTask) FROM Task WHERE idPhase=phase);
	IF (ennd > endMax) THEN 
		SET endMax = ennd;
	END IF;
	END LOOP;

	CLOSE cur1;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ---------------------------- calculatePlanData -----------------------------------
-- ----------------------------------------------------------------------------------
-- Calculate the total of planned data in a Project.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS calculatePlanData;

DELIMITER $$
CREATE PROCEDURE calculatePlanData(IN project BIGINT)
BEGIN
	DECLARE daysPlan,hoursPlan,costPlan INTEGER DEFAULT 0;
	DECLARE iniPlan DATE DEFAULT CURDATE();
	DECLARE endPlan DATE DEFAULT CURDATE() + INTERVAL 1 DAY;

	CALL getPlanData(project, daysPlan, hoursPlan, costPlan, endPlan);

-- Updates the planned data
	UPDATE Project SET  
	daysPlanProject=daysPlan, 
	hoursPlanProject=hoursPlan, 
	endPlanProject=endPlan,
	costPlanProject=costPlan,
	profitPlanProject=(budgetProject - costPlan)
	WHERE idProject=project;
	
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- -------------------------------- getRealData -------------------------------------
-- ----------------------------------------------------------------------------------
-- Calculate all the real data task in a Project.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS getRealData;

DELIMITER $$
CREATE PROCEDURE getRealData(IN project BIGINT, INOUT totalDays INTEGER, INOUT totalHours INTEGER, INOUT totalCost INTEGER, INOUT incidentCost INTEGER, INOUT iniMin DATE, INOUT endMax DATE)
BEGIN
	DECLARE days,hours,cost, incident INTEGER DEFAULT 0;
	DECLARE ini DATE DEFAULT CURDATE();
	DECLARE ennd DATE DEFAULT CURDATE() + INTERVAL 1 DAY;

 	DECLARE done INT DEFAULT FALSE;
	DECLARE phase BIGINT;
	DECLARE counter INTEGER DEFAULT 0;
	DECLARE msg VARCHAR(128);
	DECLARE cur1 CURSOR FOR SELECT idPhase FROM Phase WHERE idProject = project;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	OPEN cur1;

	read_loop: LOOP
	FETCH cur1 INTO phase;
	IF done THEN
	  LEAVE read_loop;
	END IF;

	SET days = (SELECT SUM(daysRealTask) FROM Task WHERE idPhase=phase);
	SET totalDays = totalDays + Days;

	SET hours = (SELECT SUM(hoursRealTask) FROM Task WHERE idPhase=phase);
	SET totalHours = totalHours + hours;

	SET cost = (SELECT SUM(costRealTask) FROM Task WHERE idPhase=phase);
	SET incident = (SELECT  IFNULL(SUM(lossIncident),0) FROM TaskIncident WHERE idTask IN (SELECT idTask FROM Task WHERE idPhase=phase));
	SET totalCost = totalCost + cost;
	SET incidentCost = incidentCost + incident;

	SET ini = (SELECT MIN(iniRealTask) FROM Task WHERE idPhase=phase);
	IF (ini < iniMin) THEN 
		SET iniMin = ini;
	END IF;

	SET ennd = (SELECT MAX(endRealTask) FROM Task WHERE idPhase=phase);
	IF (ennd > endMax) THEN 
		SET endMax = ennd;
	END IF;
	END LOOP;

	CLOSE cur1;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ---------------------------- calculateRealData -----------------------------------
-- ----------------------------------------------------------------------------------
-- Calculate the total of real data in a Project.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS calculateRealData;

DELIMITER $$
CREATE PROCEDURE calculateRealData(IN project BIGINT)
BEGIN
	DECLARE numTask,daysReal,hoursReal,costReal, incidentCost INTEGER DEFAULT 0;
	DECLARE iniReal DATE DEFAULT '2200-01-01';
	DECLARE endReal DATE DEFAULT '0000-01-01';

	CALL getRealData(project, daysReal, hoursReal, costReal, incidentCost, iniReal, endReal);

-- Updates the real data
	UPDATE Project SET  
	daysRealProject=daysReal, 
	hoursRealProject=hoursReal,
	iniRealProject=iniReal,
	endRealProject=endReal,
	costRealProject=costReal,
	profitRealProject=(budgetProject - costReal - incidentCost)
	WHERE idProject=project;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------------ checkDateMilestone --------------------------------
-- ----------------------------------------------------------------------------------
-- Checks if the milestone iniPlan are compatible with the change of project iniPlan.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkDateMilestone;

DELIMITER $$
CREATE PROCEDURE checkDateMilestone(IN project BIGINT, IN newProjectIniPlan DATE)
BEGIN
 	DECLARE done INT DEFAULT FALSE;
	DECLARE dateMilestone DATE;
	DECLARE msg VARCHAR(128);
	DECLARE cur1 CURSOR FOR SELECT datePlanMilestone FROM Milestone WHERE idProject = project;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	OPEN cur1;

	read_loop: LOOP
	FETCH cur1 INTO dateMilestone;
	IF done THEN
	  LEAVE read_loop;
	END IF;

-- Compares with the iniPlanProject.
	IF (dateMilestone<newProjectIniPlan) THEN 
		SET msg = concat('Error: There are milestone that have a date previous at this iniPlanProject (',cast(newProjectIniPlan as char),').');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
	END LOOP;

	CLOSE cur1;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ---------------------------- checkDataMilestone ----------------------------------
-- ----------------------------------------------------------------------------------
-- Checks that this date is equal or greater than project iniDate.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkDataMilestone;

DELIMITER $$
CREATE PROCEDURE checkDataMilestone (IN thisDate DATE, IN iniDateProj DATE, IN attrib VARCHAR(30))
BEGIN
	DECLARE msg VARCHAR(128);
	set msg = concat('Error: Invalid date for ',attrib,' (',cast(thisDate as char), '). It must be greater or equal than project iniDate.');
	CALL dateGreaterOrEqualThan(iniDateProj,thisDate,msg);
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- checkPersonDates -----------------------------------
-- ----------------------------------------------------------------------------------
-- Checks that the date 'iniDate' is greater than 'endDate'.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkPersonDates;

DELIMITER $$
CREATE PROCEDURE checkPersonDates (IN iniDate DATE, IN endDate DATE, IN hireDate DATE, IN attrib1 VARCHAR(30), IN attrib2 VARCHAR(30))
BEGIN
	DECLARE msg VARCHAR(128);
	set msg = concat('Error: Invalid date for ',attrib1,' (',cast(iniDate as char), '). It must be greater or equal than hiredate of the person.');
	CALL dateGreaterOrEqualThan(hireDate,iniDate,msg);
	
	set msg = concat('Error: Invalid date for ',attrib2,' (',cast(endDate as char), '). It must be greater than ',attrib1,'.');
	CALL DateGreaterThan(iniDate,endDate,msg);
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ---------------------------- checkTimeOffduplicate -------------------------------
-- ----------------------------------------------------------------------------------
-- Checks that there are not timeoff of one person in parallel dates.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkTimeOffduplicate;

DELIMITER $$
CREATE PROCEDURE checkTimeOffduplicate(IN thisIni DATE,IN thisEnd DATE, IN person BIGINT, IN id BIGINT)
BEGIN
 	DECLARE done INT DEFAULT FALSE;
	DECLARE iniTo,endTo DATE;
	DECLARE msg VARCHAR(128);
	DECLARE cur1 CURSOR FOR SELECT iniTimeOff,endTimeOff FROM TimeOff WHERE (idPerson=person AND idTimeOff!=id);
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	OPEN cur1;

	read_loop: LOOP
	FETCH cur1 INTO iniTo,endTo;
	IF done THEN
	  LEAVE read_loop;
	END IF;
	
	IF ((iniTo<=thisIni AND endTo>=thisIni) OR (iniTo<=thisEnd AND endTo>=thisEnd) OR (thisIni<=iniTo AND thisEnd>=iniTo) OR endTo IS NULL) THEN 
		SET msg = concat('Error: There is already a TimeOff with in this date range.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

	END LOOP;

	CLOSE cur1;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ---------------------------- checkWorkloadDates ----------------------------------
-- ----------------------------------------------------------------------------------
-- Checks that the date 'iniDate' is greater than 'endDate'.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkWorkloadDates;

DELIMITER $$
CREATE PROCEDURE checkWorkloadDates (IN person BIGINT, IN iniDate DATE, IN endDate DATE)
BEGIN
	DECLARE numRows INTEGER DEFAULT 0;
	DECLARE msg VARCHAR(128);

	IF (endDate IS NULL) THEN
		SET numRows = (SELECT COUNT(idWorkload) FROM Workload WHERE (idHPerson=person AND dayDateWorkload >= iniDate));
	ELSE 	
		SET numRows = (SELECT COUNT(idWorkload) FROM Workload WHERE (idHPerson=person AND dayDateWorkload BETWEEN iniDate AND endDate));
	END IF;

	IF (numRows > 0) THEN
		SET msg = ('Error: This person has assigned work in this dates.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;  
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------------ checkHPduplicate ----------------------------------
-- ----------------------------------------------------------------------------------
-- Checks that there are not history with the same ProfCatg in parallel dates.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkHPduplicate;

DELIMITER $$
CREATE PROCEDURE checkHPduplicate(IN thisIni DATE,IN thisEnd DATE, IN person BIGINT)
BEGIN
 	DECLARE done INT DEFAULT FALSE;
	DECLARE iniHp,endHp DATE;
	DECLARE msg VARCHAR(128);
	DECLARE cur1 CURSOR FOR SELECT iniHPerson,endHPerson FROM HistoryPerson WHERE (idPerson=person);
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	OPEN cur1;

	read_loop: LOOP
	FETCH cur1 INTO iniHp,endHp;
	IF done THEN
	  LEAVE read_loop;
	END IF;
	
	IF ((iniHp<=thisIni AND endHp>=thisIni) OR (iniHp<=thisEnd AND endHp>=thisEnd)) THEN 
		SET msg = concat('Error: The date interval of the new HistoryPerson is parallel at other HistoryProject of the same Person.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

	END LOOP;

	CLOSE cur1;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- --------------------------- checkHistoryPersonData -------------------------------
-- ----------------------------------------------------------------------------------
-- Checks the datas of HistoryPerson.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkHistoryPersonData;

DELIMITER $$
CREATE PROCEDURE checkHistoryPersonData (IN iniHPerson DATE,IN endHPerson DATE,IN hiredatePerson DATE,IN salHPerson INTEGER,IN salExtraHPerson INTEGER)
BEGIN
	DECLARE msg VARCHAR(128);

-- Checks if the dates are correct.
	CALL checkPersonDates(iniHPerson,endHPerson,hiredatePerson,'iniHPerson','endHPerson');

-- Checks if the sal is positive.
	CALL isPositive(salHPerson,'salHPerson');
	CALL isPositive(salExtraHPerson,'salExtraHPerson');	

END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------ compatibleWithIniProject --------------------------------
-- ----------------------------------------------------------------------------------
-- Checks if a date is greater or equal than iniPlanProject.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS compatibleWithIniProject;

DELIMITER $$
CREATE PROCEDURE compatibleWithIniProject(IN project BIGINT, IN thisDate DATE, IN attrb VARCHAR(45))
BEGIN
	DECLARE msg VARCHAR(128);

	SET msg = concat('Error: Invalid value for ',cast(attrb as char),' (',cast(thisDate as char), ').');
	SET msg = concat(msg, 'This must be greater or equal than iniPlanProject.');
	CALL dateGreaterOrEqualThan((SELECT iniPlanProject FROM Project WHERE idProject=project), thisDate, msg);
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------- compatibleWithIniPhase ---------------------------------
-- ----------------------------------------------------------------------------------
-- Checks if a dates are compatible with the datas phase.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS compatibleWithPhase;

DELIMITER $$
CREATE PROCEDURE compatibleWithPhase(IN phase BIGINT, IN iniDate DATE, IN endDate DATE, IN iniAttrb VARCHAR(45), IN endAttrb VARCHAR(45))
BEGIN
	DECLARE msg VARCHAR(128);

	SET msg = concat('Error: Invalid value for ',cast(iniAttrb as char),' (',cast(iniDate as char), ').');
	SET msg = concat(msg, 'This must be greater or equal than iniPlanPhase.');
	CALL dateGreaterOrEqualThan((SELECT iniPlanPhase FROM Phase WHERE idPhase=phase), iniDate, msg);

	SET msg = concat('Error: Invalid value for ',cast(endAttrb as char),' (',cast(endDate as char), ').');
	SET msg = concat(msg, 'This must be greater or equal than endPlanPhase.');
	CALL dateGreaterOrEqualThan(endDate, (SELECT endPlanPhase FROM Phase WHERE idPhase=phase), msg);
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------ calculateCostMaterialPlan -------------------------------
-- ----------------------------------------------------------------------------------
-- Calculate the total cost of planned Materials.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS calculateCostMaterialPlan;

DELIMITER $$
CREATE PROCEDURE calculateCostMaterialPlan(IN t BIGINT, INOUT cost INTEGER)
BEGIN
	SET cost = (SELECT IFNULL(SUM(costPlanAssigMat),0)
				FROM AssignmentMaterial 
				WHERE (idTask=t AND planAssigMat=true));	
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------- calculateHoursPerProfilePlan ---------------------------
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS calculateHoursPerProfilePlan;

DELIMITER $$
CREATE PROCEDURE calculateHoursPerProfilePlan(IN t BIGINT, INOUT hours INTEGER)
BEGIN
 	DECLARE done INT DEFAULT FALSE;
	DECLARE units, hoursPerson, hoursProfile INT DEFAULT 0;
	DECLARE dateMilestone DATE;
	DECLARE msg VARCHAR(128);
	DECLARE cur1 CURSOR FOR SELECT unitsAssigProf, hoursPerPersonAssigProf FROM AssignmentProfile WHERE idTask = t;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	OPEN cur1;

	read_loop: LOOP
	FETCH cur1 INTO units, hoursPerson;
	IF done THEN
	  LEAVE read_loop;
	END IF;

-- Get the hours per profile
	SET hoursProfile = units * hoursPerson;
	SET hours = hours + hoursProfile;
	END LOOP;

	CLOSE cur1;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------- calculateCostHoursPersonPlan -----------------------------
-- ----------------------------------------------------------------------------------
-- Calculate the total cost and hours of planned Profiles.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS calculateCostHoursPersonPlan;

DELIMITER $$
CREATE PROCEDURE calculateCostHoursPersonPlan(IN t BIGINT, INOUT cost INTEGER, INOUT hours INTEGER)
BEGIN	
	DECLARE msg VARCHAR(128);

	SET cost = (SELECT SUM(costAssigProf) FROM AssignmentProfile WHERE (idTask=t));
	CALL calculateHoursPerProfilePlan(t, hours);

	IF (cost IS NULL) THEN
		SET msg = concat('Error: There are not profiles assigned at this task.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------ calculateCostMaterialReal -------------------------------
-- ----------------------------------------------------------------------------------
-- Calculate the total cost of real Materials.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS calculateCostMaterialReal;

DELIMITER $$
CREATE PROCEDURE calculateCostMaterialReal(IN t BIGINT, INOUT cost INTEGER)
BEGIN
	SET cost = (SELECT IFNULL(SUM(costRealAssigMat),0)
				FROM AssignmentMaterial 
				WHERE (idTask=t AND realAssigMat=true));	
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ------------------------- checkIfAssigPersonConclude -----------------------------
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkIfAssigPersonConclude;

DELIMITER $$
CREATE PROCEDURE checkIfAssigPersonConclude(IN t BIGINT)
BEGIN
 	DECLARE done INT DEFAULT FALSE;
	DECLARE conclude BOOLEAN DEFAULT false;
	DECLARE msg VARCHAR(128);
	DECLARE cur1 CURSOR FOR SELECT concludeAssigPerson FROM AssignmentPerson WHERE (idTask=t);
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	OPEN cur1;

	read_loop: LOOP
	FETCH cur1 INTO conclude;
	IF done THEN
	  LEAVE read_loop;
	END IF;
	
	IF (NOT conclude) THEN 
		SET msg = concat('Error: There are AssignmentPerson that have not conclude in this task with idTask[',cast(t as char),'].');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

	END LOOP;

	CLOSE cur1;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------- calculateCostHoursPersonReal -----------------------------
-- ----------------------------------------------------------------------------------
-- Calculate the total cost and hours of the person assignament.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS calculateCostHoursPersonReal;

DELIMITER $$
CREATE PROCEDURE calculateCostHoursPersonReal(IN t BIGINT, INOUT cost INTEGER, INOUT hours INTEGER)
BEGIN	
	DECLARE msg VARCHAR(128);

	CALL checkIfAssigPersonConclude(t);
	SET cost = (SELECT SUM(totalCostAssigPerson) FROM AssignmentPerson WHERE (idTask=t));
	SET hours = (SELECT SUM(totalHoursAssigPerson) FROM AssignmentPerson WHERE (idTask=t));
	SET hours = hours + (SELECT SUM(totalExtraHoursAssigPerson) FROM AssignmentPerson WHERE (idTask=t));

	IF (cost IS NULL) THEN
		SET msg = concat('Error: There are not persons assigned at this task.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- -------------------------------- pdayDateWorkload ----------------------------------------
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS pdayDateWorkload;

DELIMITER $$
CREATE PROCEDURE pdayDateWorkload (IN iniDate DATE, IN v_max INTEGER)
BEGIN
	DECLARE v_counter,thisweekDayBlacklist INTEGER DEFAULT 0;
	DECLARE thisDate DATE;

	WHILE (v_counter < v_max) DO
		SET thisDate = iniDate + INTERVAL v_counter DAY;
		SET thisweekDayBlacklist = weekDayBlacklist(thisDate);
		
		IF (thisweekDayBlacklist< 5) THEN
			INSERT INTO dayDateWorkload (iddayDateWorkload, workingdayDateWorkload) VALUES (thisDate,true);
		ELSE
			INSERT INTO dayDateWorkload (iddayDateWorkload, workingdayDateWorkload) VALUES (thisDate,false);
		END IF;	

		SET v_counter=v_counter+1;
	END WHILE;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- --------------------------- checkPredecessorData ---------------------------------
-- ----------------------------------------------------------------------------------
-- Checks the data of Predecessor.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkPredecessorData;

DELIMITER $$
CREATE PROCEDURE checkPredecessorData (IN proj BIGINT,IN phase BIGINT,IN task BIGINT,IN proj2 INTEGER, IN phase2 INTEGER,IN task2 INTEGER)
BEGIN
	DECLARE dateTask1 DATE;
	DECLARE dateTask2 DATE;
	DECLARE msg VARCHAR(128);

-- Gets the iniPlan date of the task.
	SET dateTask1 = (SELECT iniPlanTask FROM Task WHERE idProject=proj AND noPhase=phase AND idTask=task);
	SET dateTask2 = (SELECT iniPlanTask FROM Task WHERE idProject=proj2 AND noPhase=phase2 AND idTask=task2);

-- Compares the dates.
	SET msg = concat('Error: Incompatible tasks. The initial date of the predecessor task is greater than the main task.');
	CALL dateGreaterOrEqualThan(dateTask2,dateTask1,msg);
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- checkWorkloadData ----------------------------------
-- ----------------------------------------------------------------------------------
-- Checks the data of Workload.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkPredecessorData;

DELIMITER $$
CREATE PROCEDURE checkPredecessorData (IN h INTEGER,IN extraH INTEGER,IN totalH INTEGER,IN totalExtraH INTEGER)
BEGIN
	DECLARE msg VARCHAR(128);

-- Check the hours
	CALL isPositive(h, 'hoursWorkload');
	CALL isPositive(extraH, 'extraHoursWorkload');
	
	IF (h>8 OR hExtra>8) THEN 
		SET msg = concat('Error: The maximum number of hours per day is 8.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

	
-- Checks if the hours dont exceed.
	IF ((h+totalH)>8) THEN 
		SET msg = concat('Error: This person has already assigned ',cast(totalH as char),' hours. So you cant add ',cast(h as char),' hours more.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

	IF ((hExtra+totalExtraH)>8) THEN 
		SET msg = concat('Error: This person has already assigned ',cast(totalExtraH as char),' extra hours. So you cant add ',cast(hExtra as char),' hours more.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- checkIfTaskIsPlan ----------------------------------
-- ----------------------------------------------------------------------------------
-- Checks if the Task is in planification. In negative case, a exception is thrown.
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkIfTaskIsPlan;

DELIMITER $$
CREATE PROCEDURE checkIfTaskIsPlan(IN task BIGINT)
BEGIN
	DECLARE taskState VARCHAR(4);
	DECLARE msg VARCHAR(128);

-- Checks if the task is in planification.
	SET taskState = (SELECT idState
				   	 FROM Task 
				   	 WHERE idTask=task);

	IF (taskState!='PLAN') THEN 	
		SET msg = concat('Error: To insert/update/delete assigned profiles, the task state must be \'PLAN\'.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ----------------------------- checkFreeDayData -----------------------------------
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkFreeDayData;

DELIMITER $$
CREATE PROCEDURE checkFreeDayData(IN weekDayFreeDay INTEGER, IN iniFreeDay DATE, IN endFreeDay DATE)
BEGIN
	DECLARE msg VARCHAR(128);

-- Check if weekDay is a day of the week (between 0 and 6).
	IF (weekDayFreeDay IS NOT NULL AND (weekDayFreeDay<0 OR weekDayFreeDay>6)) THEN
		SET msg = ('Error: The attribute weekDayFreeDay must be a value between 0=Monday and 6=Sunday.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;
	END IF;

-- Check if the data range is correct.
	IF (iniFreeDay IS NOT NULL AND endFreeDay IS NOT NULL) THEN
		SET msg = ('Error: The attribute iniFreeDay must be equal or greater than endFreeDay.');
		CALL dateGreaterOrEqualThan(iniFreeDay, endFreeDay, msg);
	END IF;
END$$
DELIMITER ;


-- ----------------------------------------------------------------------------------
-- ---------------------------- checkProjectFreeDay ---------------------------------
-- ----------------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS checkProjectFreeDay;

DELIMITER $$
CREATE PROCEDURE checkProjectFreeDay(IN project BIGINT)
BEGIN
	DECLARE currentState VARCHAR(4);
	DECLARE msg VARCHAR(128);

-- Get the current state of the Project 
	SET currentState = (SELECT idState
						FROM HistoryProject
						WHERE idProject = project AND idHProject = (SELECT MAX(idHProject) FROM HistoryProject WHERE idProject = project));

-- We can only insert/update when the project state is PLAN.
	IF (currentState!='PLAN') THEN 	
		SET msg = concat('Error: To insert/update FreeDays in a Project, the project state must be \'PLAN\'.');
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = msg;	
	END IF;

END$$
DELIMITER ;
