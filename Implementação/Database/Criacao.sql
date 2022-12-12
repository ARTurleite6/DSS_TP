-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema RacingManager
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema RacingManager
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `RacingManager` DEFAULT CHARACTER SET utf8 ;
USE `RacingManager` ;

-- -----------------------------------------------------
-- Table `RacingManager`.`Utilizador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`Utilizador` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `autenticado` BIT(1) NOT NULL,
  `tipo` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`Jogador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`Jogador` (
  `username` VARCHAR(50) NOT NULL,
  `pontuacao_global` INT NOT NULL,
  `premium` BIT(1) NOT NULL,
  `autenticado` BIT(1) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  CONSTRAINT `username`
    FOREIGN KEY (`username`)
    REFERENCES `RacingManager`.`Utilizador` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `RacingManager`.`Administrador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`Administrador` (
  `username` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  CONSTRAINT `fk_Administrador_Utilizador1`
    FOREIGN KEY (`username`)
    REFERENCES `RacingManager`.`Utilizador` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`Carro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`Carro` (
  `modelo` VARCHAR(50) NOT NULL,
  `marca` VARCHAR(50) NOT NULL,
  `cilindrada` INT NOT NULL,
  `potencia_combustao` INT NOT NULL,
  `fiabilidade` FLOAT NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`modelo`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`GT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`GT` (
  `modelo` VARCHAR(50) NOT NULL,
  `fator_desgaste` FLOAT NOT NULL,
  `hibrido` BIT(1) NOT NULL,
  PRIMARY KEY (`modelo`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC) VISIBLE,
  CONSTRAINT `fk_GT_Carro1`
    FOREIGN KEY (`modelo`)
    REFERENCES `RacingManager`.`Carro` (`modelo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`C2`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`C2` (
  `modelo` VARCHAR(50) NOT NULL,
  `afinacao` FLOAT NOT NULL,
  `hibrido` BIT(1) NOT NULL,
  PRIMARY KEY (`modelo`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC) VISIBLE,
  CONSTRAINT `fk_C2_Carro1`
    FOREIGN KEY (`modelo`)
    REFERENCES `RacingManager`.`Carro` (`modelo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`C1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`C1` (
  `modelo` VARCHAR(50) NOT NULL,
  `afinacao` FLOAT NOT NULL,
  `hibrido` BIT(1) NOT NULL,
  PRIMARY KEY (`modelo`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC) VISIBLE,
  CONSTRAINT `fk_C1_Carro1`
    FOREIGN KEY (`modelo`)
    REFERENCES `RacingManager`.`Carro` (`modelo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`GTH`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`GTH` (
  `modelo` VARCHAR(50) NOT NULL,
  `potencia_eletrica` INT NOT NULL,
  PRIMARY KEY (`modelo`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC) VISIBLE,
  CONSTRAINT `fk_GTH_GT1`
    FOREIGN KEY (`modelo`)
    REFERENCES `RacingManager`.`GT` (`modelo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`C2H`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`C2H` (
  `modelo` VARCHAR(50) NOT NULL,
  `potencia_eletrica` INT NOT NULL,
  PRIMARY KEY (`modelo`),
  UNIQUE INDEX `modelo_UNIQUE` (`modelo` ASC) VISIBLE,
  CONSTRAINT `fk_C1H_C21`
    FOREIGN KEY (`modelo`)
    REFERENCES `RacingManager`.`C2` (`modelo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`C1H`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`C1H` (
  `modelo` VARCHAR(50) NOT NULL,
  `potencia_eletrica` INT NOT NULL,
  PRIMARY KEY (`modelo`),
  CONSTRAINT `fk_C1H_C11`
    FOREIGN KEY (`modelo`)
    REFERENCES `RacingManager`.`C1` (`modelo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`Piloto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`Piloto` (
  `nome` VARCHAR(50) NOT NULL,
  `cts` INT NOT NULL,
  `sva` INT NOT NULL,
  PRIMARY KEY (`nome`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`Campeonato`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`Campeonato` (
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`nome`),
  UNIQUE INDEX `Nome_UNIQUE` (`nome` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `RacingManager`.`Circuito`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`Circuito` (
  `nome` VARCHAR(50) NOT NULL,
  `distancia` INT NOT NULL,
  `voltas` INT NOT NULL,
  PRIMARY KEY (`nome`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`CircuitoCampeonato`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`CircuitoCampeonato` (
  `campeonato` VARCHAR(50) NOT NULL,
  `circuito` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`campeonato`, `circuito`),
  INDEX `fk_Campeonato_has_Circuito_Circuito1_idx` (`circuito` ASC) VISIBLE,
  INDEX `fk_Campeonato_has_Circuito_Campeonato1_idx` (`campeonato` ASC) VISIBLE,
  CONSTRAINT `fk_Campeonato_has_Circuito_Campeonato1`
    FOREIGN KEY (`campeonato`)
    REFERENCES `RacingManager`.`Campeonato` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Campeonato_has_Circuito_Circuito1`
    FOREIGN KEY (`circuito`)
    REFERENCES `RacingManager`.`Circuito` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`Lobby`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`Lobby` (
  `numero` INT NOT NULL AUTO_INCREMENT,
  `campeonato` VARCHAR(50) NOT NULL,
  `premium` BIT(1) NOT NULL,
  PRIMARY KEY (`numero`),
  UNIQUE INDEX `numero_UNIQUE` (`numero` ASC) VISIBLE,
  INDEX `fk_Lobby_Campeonato1_idx` (`campeonato` ASC) VISIBLE,
  CONSTRAINT `fk_Lobby_Campeonato1`
    FOREIGN KEY (`campeonato`)
    REFERENCES `RacingManager`.`Campeonato` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`JogadorLobby`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`JogadorLobby` (
  `jogador` VARCHAR(50) NOT NULL,
  `lobby` INT NOT NULL,
  `piloto` VARCHAR(50) NOT NULL,
  `carro` VARCHAR(50) NOT NULL,
  `pontuacao` INT NOT NULL,
  PRIMARY KEY (`jogador`, `lobby`),
  INDEX `fk_Jogador_has_Lobby_Lobby1_idx` (`lobby` ASC) VISIBLE,
  INDEX `fk_Jogador_has_Lobby_Jogador1_idx` (`jogador` ASC) VISIBLE,
  INDEX `fk_JogadorLobby_Piloto1_idx` (`piloto` ASC) VISIBLE,
  INDEX `fk_JogadorLobby_Carro1_idx` (`carro` ASC) VISIBLE,
  CONSTRAINT `fk_Jogador_has_Lobby_Jogador1`
    FOREIGN KEY (`jogador`)
    REFERENCES `RacingManager`.`Jogador` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Jogador_has_Lobby_Lobby1`
    FOREIGN KEY (`lobby`)
    REFERENCES `RacingManager`.`Lobby` (`numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_JogadorLobby_Piloto1`
    FOREIGN KEY (`piloto`)
    REFERENCES `RacingManager`.`Piloto` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_JogadorLobby_Carro1`
    FOREIGN KEY (`carro`)
    REFERENCES `RacingManager`.`Carro` (`modelo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`Corrida`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`Corrida` (
  `numero` INT NOT NULL AUTO_INCREMENT,
  `lobby` INT NOT NULL,
  `circuito` VARCHAR(50) NOT NULL,
  `chuva` BIT(1) NOT NULL,
  PRIMARY KEY (`numero`),
  UNIQUE INDEX `numero_UNIQUE` (`numero` ASC) VISIBLE,
  INDEX `fk_Corrida_Lobby1_idx` (`lobby` ASC) VISIBLE,
  INDEX `fk_Corrida_Circuito1_idx` (`circuito` ASC) VISIBLE,
  CONSTRAINT `fk_Corrida_Lobby1`
    FOREIGN KEY (`lobby`)
    REFERENCES `RacingManager`.`Lobby` (`numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Corrida_Circuito1`
    FOREIGN KEY (`circuito`)
    REFERENCES `RacingManager`.`Circuito` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`Seccao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`Seccao` (
  `numero` INT NOT NULL,
  `circuito` VARCHAR(50) NOT NULL,
  `dificuldade` INT NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`numero`, `circuito`),
  INDEX `fk_Seccao_Circuito1_idx` (`circuito` ASC) VISIBLE,
  CONSTRAINT `fk_Seccao_Circuito1`
    FOREIGN KEY (`circuito`)
    REFERENCES `RacingManager`.`Circuito` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`CorridaPiloto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`CorridaPiloto` (
  `corrida` INT NOT NULL,
  `piloto` VARCHAR(50) NOT NULL,
  `dnf` BIT(1) NOT NULL,
  `tempo_total` INT NOT NULL,
  PRIMARY KEY (`corrida`, `piloto`),
  INDEX `fk_Corrida_has_Piloto_Piloto1_idx` (`piloto` ASC) VISIBLE,
  INDEX `fk_Corrida_has_Piloto_Corrida1_idx` (`corrida` ASC) VISIBLE,
  CONSTRAINT `fk_Corrida_has_Piloto_Corrida1`
    FOREIGN KEY (`corrida`)
    REFERENCES `RacingManager`.`Corrida` (`numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Corrida_has_Piloto_Piloto1`
    FOREIGN KEY (`piloto`)
    REFERENCES `RacingManager`.`Piloto` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingManager`.`TempoVolta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingManager`.`TempoVolta` (
  `volta` INT NOT NULL,
  `piloto` INT NOT NULL,
  `corrida` VARCHAR(50) NOT NULL,
  `tempo` INT NOT NULL,
  PRIMARY KEY (`volta`, `piloto`, `corrida`),
  CONSTRAINT `fk_TempoVolta_CorridaPiloto1`
    FOREIGN KEY (`piloto` , `corrida`)
    REFERENCES `RacingManager`.`CorridaPiloto` (`corrida` , `piloto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
