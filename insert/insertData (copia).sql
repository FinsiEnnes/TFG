-- -------------------------------- AptitudeType ------------------------------------
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('CIE', 'Cientifica');
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('ESP', 'Espacial');
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('NUM', 'Numerica');
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('MEC', 'Mecanica');
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('SOC', 'Social');
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('DIR', 'Directiva');
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('ORG', 'Organizativa');


-- -------------------------------- TaskLinkType ---------------------------------------
INSERT INTO TaskLinkType (idTaskLinkType,nameTaskLinkType) VALUES ('CC', 'Comienzo-Comienzo');
INSERT INTO TaskLinkType (idTaskLinkType,nameTaskLinkType) VALUES ('CF', 'Comienzo-Fin');
INSERT INTO TaskLinkType (idTaskLinkType,nameTaskLinkType) VALUES ('FC', 'Fin-Comienzo');
INSERT INTO TaskLinkType (idTaskLinkType,nameTaskLinkType) VALUES ('FF', 'Fin-Fin');


-- ------------------------------------ State -----------------------------------------
INSERT INTO State (idState,nameState,descState) VALUES ('PLAN', 'Planificacion','Fase inicial y previa a la ejecucion.');
INSERT INTO State (idState,nameState,descState) VALUES ('EJEC', 'Ejecucion','Proceso en marcha.');
INSERT INTO State (idState,nameState,descState) VALUES ('CANC', 'Cancelado','Proceso que sin cumplirse los objetivos iniciales termina.');
INSERT INTO State (idState,nameState,descState) VALUES ('TERM', 'Terminado','Proceso finalizado satisfactoriamente.');


-- ---------------------------------- Priority ----------------------------------------
INSERT INTO Priority (idPriority,namePriority) VALUES ('MA', 'Muy Alta');
INSERT INTO Priority (idPriority,namePriority) VALUES ('A',  'Alta');
INSERT INTO Priority (idPriority,namePriority) VALUES ('M',  'Media');
INSERT INTO Priority (idPriority,namePriority) VALUES ('B',  'Baja');


-- -------------------------------- LevelProfCatg -------------------------------------
INSERT INTO LevelProfCatg (idLevelProfCatg,nameLevelProfCatg) VALUES ('JUN', 'Junior');
INSERT INTO LevelProfCatg (idLevelProfCatg,nameLevelProfCatg) VALUES ('SJN', 'Semi Junior');
INSERT INTO LevelProfCatg (idLevelProfCatg,nameLevelProfCatg) VALUES ('SEN', 'Senior');
INSERT INTO LevelProfCatg (idLevelProfCatg,nameLevelProfCatg) VALUES ('ESP', 'Especialista');


-- ---------------------------------- Damage -----------------------------------------
INSERT INTO Damage (idDamage,nameDamage) VALUES ('MG', 'Muy Grave');
INSERT INTO Damage (idDamage,nameDamage) VALUES ('G', 'Grave');
INSERT INTO Damage (idDamage,nameDamage) VALUES ('I', 'Importante');
INSERT INTO Damage (idDamage,nameDamage) VALUES ('M', 'Menor');
INSERT INTO Damage (idDamage,nameDamage) VALUES ('D', 'Despreciable');


-- ------------------------------- BusinessType --------------------------------------
INSERT INTO BusinessType (idBusinessType,nameBusinessType) VALUES ('SERV', 'Servicios');
INSERT INTO BusinessType (idBusinessType,nameBusinessType) VALUES ('PROD', 'Produccion');
INSERT INTO BusinessType (idBusinessType,nameBusinessType) VALUES ('EXTR', 'Extraccion');
INSERT INTO BusinessType (idBusinessType,nameBusinessType) VALUES ('MINR', 'Minorista');
INSERT INTO BusinessType (idBusinessType,nameBusinessType) VALUES ('MAYR', 'Mayorista');


-- ----------------------------- BusinessCategory ------------------------------------
INSERT INTO BusinessCategory (idBusinessCatg,nameBusinessCatg) VALUES ('MUL', 'Multinacional');
INSERT INTO BusinessCategory (idBusinessCatg,nameBusinessCatg) VALUES ('NAC', 'Nacional');
INSERT INTO BusinessCategory (idBusinessCatg,nameBusinessCatg) VALUES ('ADM', 'Administracion');
INSERT INTO BusinessCategory (idBusinessCatg,nameBusinessCatg) VALUES ('ONG', 'ONG');
INSERT INTO BusinessCategory (idBusinessCatg,nameBusinessCatg) VALUES ('ASC', 'Asociacion');
INSERT INTO BusinessCategory (idBusinessCatg,nameBusinessCatg) VALUES ('OTH', 'Otra');


-- ------------------------------- BusinessSize --------------------------------------
INSERT INTO BusinessSize (idBusinessSize,nameBusinessSize,minBusinessSize,maxBusinessSize) VALUES ('P', 'Pequena',0,10);
INSERT INTO BusinessSize (idBusinessSize,nameBusinessSize,minBusinessSize,maxBusinessSize) VALUES ('M', 'Mediana',11,50);
INSERT INTO BusinessSize (idBusinessSize,nameBusinessSize,minBusinessSize,maxBusinessSize) VALUES ('G', 'Grande' ,51,NULL);


-- --------------------------------- Country -----------------------------------------
INSERT INTO Country (idCountry,nameCountry) VALUES ('ESP', 'Espana');
INSERT INTO Country (idCountry,nameCountry) VALUES ('JAP', 'Japon');


-- --------------------------------- Province -----------------------------------------
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'A Coruna');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Barcelona');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Madrid');
INSERT INTO Province (idCountry,nameProvince) VALUES ('JAP', 'Tokyo');


-- --------------------------------- Customer -----------------------------------------
INSERT INTO Customer (idCountry,noProvince,nameCustomer,idBusinessType,idBusinessCatg,idBusinessSize) VALUES ('JAP',1,'Nintendo','SERV','MUL','G');


-- --------------------------------- Project ------------------------------------------
INSERT INTO Project (nameProject,descProject,stateDateProject,innerProject,idCountry,noProvince,iniPlanProject,endPlanProject) VALUES ('Zelda Breath of the Wild','Nuevo juego de la saga Zelda','2016-06-15',1,'JAP',1,'2014-05-20','2017-03-15');


-- ----------------------------- HistoryProject ---------------------------------------
-- INSERT INTO HistoryProject (idProject,idState,iniHProject,endHProject) VALUES (1,'TERM','2016-06-20','2016-06-30'); Error: Estado invalido.
INSERT INTO HistoryProject (idProject,idState,iniHProject,endHProject) VALUES (1,'PLAN','2014-05-20','2014-11-20');

-- INSERT INTO HistoryProject (idProject,idState,iniHProject,endHProject) VALUES (1,'EJEC','2014-11-20','2016-10-10');
-- INSERT INTO HistoryProject (idProject,idState,iniHProject) VALUES (1,'TERM','2016-07-20'); Error: iniHProject < endHProject previa
-- INSERT INTO HistoryProject (idProject,idState,iniHProject,endHProject) VALUES (1,'TERM','2017-03-15','2016-07-25'); Error: Fechas incorrectas


-- --------------------------------- Phase -------------------------------------------
INSERT INTO Phase (idProject,namePhase) VALUES (1,'Diseño del videojuego.');
INSERT INTO Phase (idProject,namePhase) VALUES (1,'Implementacion del codigo.');


-- --------------------------------- Milestone -------------------------------------------
INSERT INTO Milestone (idProject,noPhase,nameMilestone,datePlanMilestone) VALUES (1,1,'Finalizacion diseño escenarios','2015-05-20');
INSERT INTO Milestone (idProject,noPhase,nameMilestone,datePlanMilestone) VALUES (1,2,'Finalizacion implementacion de entornos','2015-05-20');

-- ----------------------------------- Person ----------------------------------------
INSERT INTO Person (namePerson,surname1Person,surname2Person,emailPerson,hiredatePerson) VALUES ('Finsi','Ennes','Moscoso','finsi@gmail.com','2016-02-01');
INSERT INTO Person (namePerson,surname1Person,surname2Person,emailPerson,hiredatePerson) VALUES ('Nigel','West','Dikens','west.dikens@gmail.com','2012-01-20');


-- ---------------------------------- Aptitude ---------------------------------------
INSERT INTO Aptitude (idPerson,nameApt,idAptType,valueApt) VALUES (1,'Programacion Java','CIE',9);
INSERT INTO Aptitude (idPerson,nameApt,idAptType,valueApt) VALUES (1,'Exposicion oral','SOC',8);
-- INSERT INTO Aptitude (idPerson,nameApt,idAptType,valueApt) VALUES (1,'Direccion','DIR',12);  Error: nota fuera de rango


-- ----------------------------------- TimeOff ----------------------------------------
INSERT INTO TimeOff (idPerson,iniTimeOff,endTimeOff,reasonTimeOff) VALUES (2,'2013-04-15','2013-04-25','Secuestro');
-- INSERT INTO TimeOff (idPerson,iniTimeOff) VALUES (1,'2013-04-15');  Error: Fecha incorrecta.


-- ---------------------------- Professional Category ----------------------------------
INSERT INTO ProfessionalCategory (nameProfCatg,idLevelProfCatg,minExpProfCatg,salProfCatg,salExtraProfCatg) VALUES ('Desarrollador SW','JUN',0,5,NULL);
INSERT INTO ProfessionalCategory (nameProfCatg,idLevelProfCatg,minExpProfCatg,salProfCatg,salExtraProfCatg) VALUES ('Desarrollador SW','SEN',3,8,12);
INSERT INTO ProfessionalCategory (nameProfCatg,idLevelProfCatg,minExpProfCatg,salProfCatg,salExtraProfCatg) VALUES ('Arquitecto SW','SEN',5,9,13);
INSERT INTO ProfessionalCategory (nameProfCatg,idLevelProfCatg,minExpProfCatg,salProfCatg,salExtraProfCatg) VALUES ('SysAdmin','ESP',7,10,14);
-- INSERT INTO ProfessionalCategory (nameProfCatg,idLevel,minExpProfCatg,salProfCatg,salExtraProfCatg) VALUES ('SysAdmin','ESP',7,-10,14); -- Error


-- ------------------------------- History Person --------------------------------------
INSERT INTO HistoryPerson (idPerson,idProfCatg,iniHPerson,endHPerson,salHPerson,salExtraHPerson) VALUES (1,1,'2016-02-02','2016-04-02',5,0);
INSERT INTO HistoryPerson (idPerson,idProfCatg,iniHPerson,endHPerson,salHPerson,salExtraHPerson) VALUES (2,2,'2012-02-03','2014-01-28',4,6);
INSERT INTO HistoryPerson (idPerson,idProfCatg,iniHPerson,salHPerson,salExtraHPerson) VALUES (2,4,'2012-02-03',8,9);
-- INSERT INTO HistoryPerson (idPerson,idProfCatg,iniHPerson,salHPerson,salExtraHPerson) VALUES (2,4,'2010-02-03',8,9);  Error: Fecha incorrecta.
-- INSERT INTO HistoryPerson (idPerson,idProfCatg,iniHPerson,salHPerson,salExtraHPerson) VALUES (2,4,'2010-02-03',-8,9);  Error: Sal negativo.


-- ---------------------------------- Material -----------------------------------------
INSERT INTO Material (nameMaterial,descMaterial,costMaterial,innerMaterial) VALUES ('Firewall','Marca: Cisco',3650,0); 
INSERT INTO Material (nameMaterial,costMaterial,innerMaterial) VALUES ('Armario Rack',10,1);
-- INSERT INTO Material (nameMaterial,costMaterial,innerMaterial) VALUES ('Armario Rack',10,1);  Error: innerMaterial no es un bool.
-- INSERT INTO Material (nameMaterial,costMaterial,innerMaterial) VALUES ('Armario Rack',-10,1); Error: Cantidad negativa.


-- ----------------------------- AssignmentMaterial -------------------------------------
-- INSERT INTO AssignmentMaterial (idMaterial,planAssigMat,realAssigMat,unitsAssigMat) VALUES (1,1,0,2); 


-- INSERT INTO Task(idProject,noPhase,nameTask,idState,idPriority,idPerson,noHPerson,daysPlanTask) VALUES (1,1,'Modelado','PLAN','A',1,1,5);
-- INSERT INTO HistoryProject (idProject,idState,iniHProject,endHProject) VALUES (1,'EJEC','2014-11-20','2016-10-10');

