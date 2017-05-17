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
-- ----------------------------------- Damage -----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Damage (idDamage,nameDamage) VALUES 
('MGRV', 'Muy grave'),
('GRAV', 'Grave'),
('IMPT', 'Importante'),
('MENR', 'Menor'),
('IRLV', 'Irrelevante');


-- ------------------------------------------------------------------------------------
-- -------------------------------- TaskLinkType --------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO TaskLinkType (idTaskLinkType,nameTaskLinkType) VALUES 
('CC', 'Comienzo-Comienzo'),
('CF', 'Comienzo-Fin'),
('FC', 'Fin-Comienzo'),
('FF', 'Fin-Fin');



