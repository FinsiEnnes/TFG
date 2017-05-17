-- ------------------------------------------------------------------------------------
-- ---------------------------------- Country -----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Country (idCountry,nameCountry) VALUES ('ESP', 'Espana');


-- ------------------------------------------------------------------------------------
-- --------------------------------- Province -----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'A Coruna');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Albacete');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Asturias');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Badajoz');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Barcelona');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Cantabria');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Ciudad Real');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Granada');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Madrid');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Murcia');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Sevilla');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Valencia');
INSERT INTO Province (idCountry,nameProvince) VALUES ('ESP', 'Zamora');

-- Si borramos el Country=ESP tambien lo hacen sus provincias:
-- DELETE FROM Country WHERE idCountry='ESP';


-- ------------------------------------------------------------------------------------
-- ------------------------------- BusinessType ---------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO BusinessType (idBusinessType,nameBusinessType) VALUES ('SERV', 'Servicios');
INSERT INTO BusinessType (idBusinessType,nameBusinessType) VALUES ('PROD', 'Produccion');
INSERT INTO BusinessType (idBusinessType,nameBusinessType) VALUES ('EXTR', 'Extraccion');
INSERT INTO BusinessType (idBusinessType,nameBusinessType) VALUES ('MINR', 'Minorista');
INSERT INTO BusinessType (idBusinessType,nameBusinessType) VALUES ('MAYR', 'Mayorista');


-- ------------------------------------------------------------------------------------
-- ----------------------------- BusinessCategory -------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO BusinessCategory (idBusinessCatg,nameBusinessCatg) VALUES ('MUL', 'Multinacional');
INSERT INTO BusinessCategory (idBusinessCatg,nameBusinessCatg) VALUES ('NAC', 'Nacional');
INSERT INTO BusinessCategory (idBusinessCatg,nameBusinessCatg) VALUES ('ADM', 'Administracion');
INSERT INTO BusinessCategory (idBusinessCatg,nameBusinessCatg) VALUES ('ONG', 'ONG');
INSERT INTO BusinessCategory (idBusinessCatg,nameBusinessCatg) VALUES ('ASC', 'Asociacion');
INSERT INTO BusinessCategory (idBusinessCatg,nameBusinessCatg) VALUES ('OTH', 'Otra');


-- ------------------------------------------------------------------------------------
-- ------------------------------- BusinessSize ---------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO BusinessSize (idBusinessSize,nameBusinessSize,minBusinessSize,maxBusinessSize) VALUES ('P', 'Pequena',0,10);
INSERT INTO BusinessSize (idBusinessSize,nameBusinessSize,minBusinessSize,maxBusinessSize) VALUES ('M', 'Mediana',11,50);
INSERT INTO BusinessSize (idBusinessSize,nameBusinessSize,minBusinessSize,maxBusinessSize) VALUES ('G', 'Grande' ,51,NULL);


-- ------------------------------------------------------------------------------------
-- ------------------------------------ State -----------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO State (idState,nameState,descState) VALUES ('PLAN', 'Planificacion','Fase inicial y previa a la ejecucion.');
INSERT INTO State (idState,nameState,descState) VALUES ('EJEC', 'Ejecucion','Proceso en marcha.');
INSERT INTO State (idState,nameState,descState) VALUES ('CANC', 'Cancelado','Proceso que sin cumplirse los objetivos iniciales termina.');
INSERT INTO State (idState,nameState,descState) VALUES ('TERM', 'Terminado','Proceso finalizado satisfactoriamente.');

-- ------------------------------------------------------------------------------------
-- -------------------------------- AptitudeType --------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('CIE', 'Cientifica');
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('ART', 'Artistica');
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('ESP', 'Espacial');
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('NUM', 'Numerica');
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('MEC', 'Mecanica');
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('SOC', 'Social');
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('DIR', 'Directiva');
INSERT INTO AptitudeType (idAptType,nameAptType) VALUES ('ORG', 'Organizativa');


-- ------------------------------------------------------------------------------------
-- -------------------------------- LevelProfCatg -------------------------------------
-- ------------------------------------------------------------------------------------
INSERT INTO LevelProfCatg (idLevelProfCatg,nameLevelProfCatg) VALUES ('JUN', 'Junior');
INSERT INTO LevelProfCatg (idLevelProfCatg,nameLevelProfCatg) VALUES ('SJN', 'Semi Junior');
INSERT INTO LevelProfCatg (idLevelProfCatg,nameLevelProfCatg) VALUES ('SEN', 'Senior');
INSERT INTO LevelProfCatg (idLevelProfCatg,nameLevelProfCatg) VALUES ('ESP', 'Especialista');
INSERT INTO LevelProfCatg (idLevelProfCatg,nameLevelProfCatg) VALUES ('PDS', 'Profesional del sector');
