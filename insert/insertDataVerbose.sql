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
INSERT INTO Customer (idCountry,noProvince,nameCustomer,idBusinessType,idBusinessCatg,idBusinessSize) VALUES ('ESP',1,'GaliGames','PROD','NAC','P'),
('ESP',8,'Caja Bank','SERV','MUL','G');

-- No podemos eliminar aquellas provincias o paises referenciados por Customer:
-- DELETE FROM Country WHERE idCountry='ESP';

-- Solucion: 
--		- Borrar los clientes cuyo pais de origen es el referenciado: DELETE FROM Customer WHERE idCountry='ESP';
--		- Ahora ya podemos borrar el pais o provincia.


-- ------------------------------------------------------------------------------------
-- --------------------------------- Project ------------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Project (nameProject,descProject,stateDateProject,innerProject,idCountry,noProvince,iniPlanProject) VALUES 
('Zelda','Colaboracion con Nintendo. Creacion de una de las mazmorras del juego.','2016-06-15',true,'ESP',1,'2014-05-20');

UPDATE Project SET idCustomer = 1, nifContact = '54155368H', nameContact='Paco', surnameContact='Perez', emailContact='perez@gmail.com'  WHERE idProject=1;

-- Error: Proyecto con cliente pero sin datos de contacto definidos:
-- INSERT INTO Project (nameProject,stateDateProject,innerProject,idCountry,noProvince,iniPlanProject,idCustomer) VALUES ('Zeta', '2016-06-15', 1, 'ESP', 1, '2014-05-20',1);

-- Error: No podemos insertar datos de planificacion o reales con la 1ra insercion:
-- INSERT INTO Project (nameProject,stateDateProject,innerProject,idCountry,noProvince,iniPlanProject,endPlanProject) VALUES ('Zeta', '2016-06-15', 1, 'ESP', 1, '2014-05-20', '2014-05-28');


-- ------------------------------------------------------------------------------------
-- ----------------------------- HistoryProject ---------------------------------------
-- ------------------------------------------------------------------------------------
-- Error: Estado inicial invalido.
-- INSERT INTO HistoryProject (idProject,idState,iniHProject,endHProject) VALUES (1,'TERM','2016-06-20','2016-06-30'); 

INSERT INTO HistoryProject (idProject,idState,iniHProject,endHProject) VALUES (1,'PLAN','2014-09-20','2014-12-26');

-- Cambio de estado no permitido.
-- INSERT INTO HistoryProject (idProject,idState,iniHProject) VALUES (1,'TERM','2014-12-01');

-- Se establecen los datos planeados:
-- INSERT INTO HistoryProject (idProject,idState,iniHProject) VALUES (1,'EJEC','2015-01-03'); 

-- A partir de aqui no podriamos seguir insertando hasta que el estado actual tenga una fecha de fin.
-- INSERT INTO HistoryProject (idProject,idState,iniHProject) VALUES (1,'TERM','2015-01-20');

-- Le damos una fecha de finalizacion al estado actual.
-- UPDATE HistoryProject SET endHProject='2016-11-02'  WHERE idProject=1 AND noHProject=2;

-- Ahora podemos insertar un nuevo estado. Se establecerian los datos reales.
-- INSERT INTO HistoryProject (idProject,idState,iniHProject,endHProject) VALUES (1,'TERM','2016-11-05','2016-12-10');

-- Fechas incorrectas.
-- INSERT INTO HistoryProject (idProject,idState,iniHProject,endHProject) VALUES (1,'TERM','2017-03-15','2016-07-25'); 


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
INSERT INTO Person (namePerson,surname1Person,surname2Person,emailPerson,hiredatePerson) VALUES ('Raul','Gonzalez','Costa','costa@gmail.com','2011-02-01'),
('Paco','Perez','Perales','pperez@gmail.com','2011-05-10'),
('Laura','Pois','Nada','la.pois@gmail.com','2012-01-08'),
('Maruja','Ramirez','Diez','maruja@gmail.com','2010-08-22');


-- ------------------------------------------------------------------------------------
-- ---------------------------------- Aptitude ----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Aptitude (idPerson,nameApt,idAptType,valueApt) VALUES 
(1,'Bocetista','ART',9),
(2,'Dominio Java','CIE',8),
(3,'Esp. Unity','ESP',7),
(4,'Liderazgo','DIR',10);

-- Error: Valoracion fuera de rango:
-- INSERT INTO Aptitude (idPerson,nameApt,idAptType,valueApt,commentApt) VALUES (4,'Liderazgo2','DIR',11,'Larga experiencia.');


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

-- Error: Salario negativo.
-- INSERT INTO ProfessionalCategory (nameProfCatg,idLevel,minExpProfCatg,salProfCatg,salExtraProfCatg) VALUES ('SysAdmin','ESP',7,-10,14);


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
('Pizarra electronica','Marca: Lenovo',1100,false),
('BD','Oracle',8000,false),
('Proyector','Full HD',0,true);


-- ------------------------------------------------------------------------------------
-- ------------------------------------ Task ------------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Task (idProject,noPhase,nameTask,idState,idPriority,idPerson,noHPerson,daysPlanTask) VALUES
(1,1,'Diseño entorno','PLAN','M',1,1,10);











