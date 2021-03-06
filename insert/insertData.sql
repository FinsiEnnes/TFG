-- ------------------------------------------------------------------------------------
-- Nota: Todas aquellas tablas en las que insertamos los identificadores a mano son las
--		 que tendran predefinidos sus datos en la aplicacion.
-- ------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------------
-- ---------------------------------- Country -----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Country (idCountry,nameCountry) VALUES ('ESP', 'Espana');


-- ------------------------------------------------------------------------------------
-- --------------------------------- Province -----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Province (idCountry,nameProvince) VALUES 
('ESP', 'A Coruna'),
('ESP', 'Albacete'),
('ESP', 'Asturias'),
('ESP', 'Badajoz'),
('ESP', 'Barcelona'),
('ESP', 'Cantabria'),
('ESP', 'Ciudad Real'),
('ESP', 'Granada'),
('ESP', 'Madrid'),
('ESP', 'Murcia'),
('ESP', 'Sevilla'),
('ESP', 'Valencia'),
('ESP', 'Zamora');

-- Si borramos el Country=ESP tambien lo hacen sus provincias:
-- DELETE FROM Country WHERE idCountry='ESP';


-- ------------------------------------------------------------------------------------
-- ------------------------------- BusinessType ---------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO BusinessType (idBusinessType,nameBusinessType) VALUES 
('SERV', 'Servicios'),
('PROD', 'Produccion'),
('EXTR', 'Extraccion'),
('MINR', 'Minorista'),
('MAYR', 'Mayorista');


-- ------------------------------------------------------------------------------------
-- ----------------------------- BusinessCategory -------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO BusinessCategory (idBusinessCatg,nameBusinessCatg) VALUES 
('MUL', 'Multinacional'),
('NAC', 'Nacional'),
('ADM', 'Administracion'),
('ONG', 'ONG'),
('ASC', 'Asociacion'),
('OTH', 'Otra');


-- ------------------------------------------------------------------------------------
-- ------------------------------- BusinessSize ---------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO BusinessSize (idBusinessSize,nameBusinessSize,minBusinessSize,maxBusinessSize) VALUES 
('P', 'Pequena',0,10),
('M', 'Mediana',11,50),
('G', 'Grande' ,51,NULL);


-- ------------------------------------------------------------------------------------
-- ------------------------------------ State -----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO State (idState,nameState,descState) VALUES 
('PLAN', 'Planificacion','Fase inicial y previa a la ejecucion.'),
('PRPD', 'Preparado','Listo para ser ejecutado. Exclusivo de las tareas.'),
('EJEC', 'Ejecucion','Proceso en marcha.'),
('CANC', 'Cancelado','Proceso que sin cumplirse los objetivos iniciales termina.'),
('TERM', 'Terminado','Proceso finalizado satisfactoriamente.');

-- ------------------------------------------------------------------------------------
-- -------------------------------- AptitudeType --------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES 
('CIE', 'Cientifica'),
('ART', 'Artistica'),
('ESP', 'Espacial'),
('NUM', 'Numerica'),
('MEC', 'Mecanica'),
('SOC', 'Social'),
('DIR', 'Directiva'),
('ORG', 'Organizativa');


-- ------------------------------------------------------------------------------------
-- -------------------------------- LevelProfCatg -------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO LevelProfCatg (idLevelProfCatg,nameLevelProfCatg) VALUES 
('JUN', 'Junior'),
('SJN', 'Semi Junior'),
('SEN', 'Senior'),
('ESP', 'Especialista'),
('PDS', 'Profesional del sector');


-- ------------------------------------------------------------------------------------
-- ---------------------------------- Priority ----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Priority (idPriority,namePriority) VALUES 
('MA', 'Muy Alta'),
('A',  'Alta'),
('M',  'Media'),
('B',  'Baja');


-- ------------------------------------------------------------------------------------
-- --------------------------------- Customer -----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Customer (idProvince,nameCustomer,idBusinessType,idBusinessCatg,idBusinessSize) VALUES (1,'GaliGames','PROD','NAC','P'),
(8,'Caja Bank','SERV','MUL','G');


-- ------------------------------------------------------------------------------------
-- --------------------------------- Project ------------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Project (nameProject,descProject,stateDateProject,innerProject,idCountry,noProvince,iniPlanProject) VALUES 
('Zelda','Colaboracion con Nintendo. Creacion de una de las mazmorras del juego.','2016-06-15',true,'ESP',1,'2014-05-20');

UPDATE Project SET idCustomer = 1, nifContact = '54155368H', nameContact='Paco', surnameContact='Perez', emailContact='perez@gmail.com'  WHERE idProject=1;


-- ------------------------------------------------------------------------------------
-- ----------------------------- HistoryProject ---------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO HistoryProject (idProject,idState,iniHProject,endHProject) VALUES (1,'PLAN','2014-09-20','2014-12-26');


-- ------------------------------------------------------------------------------------
-- --------------------------------- Phase --------------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Phase (idProject,namePhase) VALUES 
(1,'Diseño'), 
(1,'Implementacion'), 
(1,'Testing');


-- ------------------------------------------------------------------------------------
-- ----------------------------------- Person -----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Person (namePerson,surname1Person,surname2Person,emailPerson,nifPerson,hiredatePerson) VALUES ('Raul','Gonzalez','Costa','costa@gmail.com','11111111A','2011-02-01'),
('Paco','Perez','Perales','pperez@gmail.com','11111111B','2011-05-10'),
('Laura','Pois','Nada','la.pois@gmail.com','11111111C','2012-01-08'),
('Maruja','Ramirez','Diez','maruja@gmail.com','11111111D','2010-08-22'),
('Jimmy','Pocket','Trian','pocket@gmail.com','11111111E','2011-05-03'),
('Fernan','Mar','Tes','mar.tes@gmail.com','11111111F','2011-10-10'),
('Dolores','Fuertes','De barriga','dolores.barriga@gmail.com','11111111G','2014-02-08'),
('Telma','Ying','Yong','telma@gmail.com','11111111H','2015-11-01');

INSERT INTO Person (namePerson,surname1Person,surname2Person,emailPerson,nifPerson,hiredatePerson, commentPerson) VALUES ('Nigel','West','Dickens','west.dickens@gmail.com','54766345R','2011-08-10', 'Es un vendedor ambulante y nómada que tiene su propio carruaje para poder desplazarse libremente por todo el condado. Se dice de él supuestamente, que es experto en curas milagrosas.');

INSERT INTO Aptitude (idPerson,nameApt,idAptType,valueApt) VALUES 
(9,'Estafador','ART',10),
(9,'Dominio Java','CIE',8),
(9,'Esp. Unity','ESP',7);

-- ------------------------------------------------------------------------------------
-- ---------------------------------- Aptitude ----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Aptitude (idPerson,nameApt,idAptType,valueApt) VALUES 
(1,'Bocetista','ART',9),
(2,'Dominio Java','CIE',8),
(3,'Esp. Unity','ESP',7),
(4,'Liderazgo','DIR',10);


-- ------------------------------------------------------------------------------------
-- ----------------------------------- TimeOff ----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO TimeOff (idPerson,iniTimeOff,endTimeOff,reasonTimeOff) VALUES 
(2,'2013-04-15','2013-04-25','Fiebre');

-- Error: Fecha inferior a la fecha de contratacion de esa persona.
-- INSERT INTO TimeOff (idPerson,iniTimeOff) VALUES (1,'2010-04-15'); 


-- ------------------------------------------------------------------------------------
-- ---------------------------- Professional Category ---------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO ProfessionalCategory (nameProfCatg,idLevelProfCatg,minExpProfCatg,salProfCatg,salExtraProfCatg) VALUES 
('Desarrollador SW','JUN',0,5,NULL),
('Desarrollador SW','SEN',3,8,12), 
('Diseñador Personajes','SJN',1,6,7),
('Arquitecto SW','SEN',5,9,13),
('SysAdmin','ESP',7,10,14),
('Jefe de proyectos','PDS',10,12,18);


-- -------------------------------------------------------------------------------------
-- ------------------------------- History Person --------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO HistoryPerson (idPerson,idProfCatg,iniHPerson,salHPerson,salExtraHPerson) VALUES 
(1,3,'2011-02-01',5,6),
(2,1,'2011-05-10',6,7),
(2,2,'2015-10-11',8,12),
(3,2,'2012-01-08',7,11),
(3,4,'2014-12-20',9,12),
(4,6,'2011-01-01',12,15);

UPDATE HistoryPerson SET endHPerson='2015-10-10'  WHERE idPerson=2 AND noHPerson=1;


-- ------------------------------------------------------------------------------------
-- ---------------------------------- Material ----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Material (nameMaterial,descMaterial,costMaterial,innerMaterial) VALUES 
('Pizarra electronica','Lenovo',1100,false),
('BD','Oracle',8000,false),
('Proyector','Full HD',0,true);


-- ------------------------------------------------------------------------------------
-- ------------------------------------ Task ------------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Task (idProject,noPhase,nameTask,idState,idPriority,idPerson,noHPerson,daysPlanTask,iniPlanTask) VALUES
(1,1,'Diseño entorno','PLAN','M',1,1,10,'2014-06-02');


-- ------------------------------------------------------------------------------------
-- ----------------------------- AssignmentMaterial -----------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO AssignmentMaterial (idProject,noPhase,noTask,idMaterial,unitsAssigMat) VALUES
(1,1,1,1,2),
(1,1,1,2,1);


-- Los siguientes son datos de prueba
-- ------------------------------------------------------------------------------------
-- ----------------------------- AssignmentProfile ------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO AssignmentProfile (idProject,noPhase,noTask,idProfCatg,unitsAssigProf,hoursPerPersonAssigProf,costAssigProf) VALUES
(1,1,1,1,1,50,200),
(1,1,1,2,1,200,100),
(1,1,1,3,1,100,300);

INSERT INTO AssignmentPerson (idProject, noPhase, noTask, idPerson, noHPerson, concludeAssigPerson) VALUES 
(1,1,1,1,1,false);

INSERT INTO DayDate (idDayDate, workingDayDate) VALUES ('2014-06-10',true), ('2014-06-11', false);

INSERT INTO Workload (idDayDate, idProject, noPhase, noTask, idPerson, noHPerson, hoursWorkload, extraHoursWorkload) VALUES ('2014-06-10',1,1,1,1,1,5,2);
-- INSERT INTO AssignmentMaterial (idProject,noPhase,noTask,idPerson,noHPerson,concludeAssigPerson,totalCostAssigPerson) VALUES













