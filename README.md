 ```sql
CREATE DATABASE imobiliaria CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE clientes (
  id            BIGINT PRIMARY KEY AUTO_INCREMENT,
  nome          VARCHAR(120)        NOT NULL,
  email         VARCHAR(150) UNIQUE NULL,
  telefone      VARCHAR(25)         NULL,
  cpf           VARCHAR(14) UNIQUE  NULL
);

CREATE TABLE imoveis (
  id                         BIGINT PRIMARY KEY AUTO_INCREMENT,
  tipo                       ENUM('CASA','APARTAMENTO','SALA','LOJA','TERRENO','GALPAO') NOT NULL,
  endereco                   VARCHAR(200) NOT NULL,
  cidade                     VARCHAR(80)  NOT NULL,
  estado                     CHAR(2)      NOT NULL,
  cep                        VARCHAR(9)   NULL,
  quartos                    TINYINT      NULL,
  banheiros                  TINYINT      NULL,
  mobiliado                  BOOLEAN        NOT NULL DEFAULT FALSE,
  valor_aluguel_sugerido     DECIMAL(12,2) NULL,
  status                     ENUM('DISPONIVEL','ALUGADO','INATIVO') NOT NULL DEFAULT 'DISPONIVEL',
  INDEX idx_imoveis_status (status),
  INDEX idx_imoveis_cidade_estado (cidade, estado)
);

CREATE TABLE contratos (
  id               BIGINT PRIMARY KEY AUTO_INCREMENT,
  imovel_id        BIGINT     NOT NULL,
  cliente_id       BIGINT     NOT NULL,
  valor_mensal     DECIMAL(12,2) NOT NULL,
  data_inicio      DATE       NOT NULL,
  data_fim         DATE       NOT NULL,
  dia_vencimento   TINYINT    NOT NULL CHECK (dia_vencimento BETWEEN 1 AND 28),
  status           ENUM('ATIVO','ENCERRADO','RESCINDIDO','PENDENTE') NOT NULL DEFAULT 'PENDENTE',
  CONSTRAINT fk_contrato_imovel  FOREIGN KEY (imovel_id)  REFERENCES imoveis(id),
  CONSTRAINT fk_contrato_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
  INDEX idx_contratos_status (status),
  INDEX idx_contratos_datas (data_inicio, data_fim),
  INDEX idx_contratos_expira (data_fim)
);

-- CLIENTES
INSERT INTO clientes (nome, email, telefone, cpf) VALUES
('João Silva',    'joao.silva@email.com',    '11988887777', '123.456.789-01'),
('Maria Oliveira','maria.oliveira@email.com','21977776666', '987.654.321-00'),
('Carlos Souza',  'carlos.souza@email.com',  '31966665555', '456.789.123-99'),
('Ana Pereira',   'ana.pereira@email.com',   '41955554444', '321.654.987-11');

-- IMÓVEIS
INSERT INTO imoveis (tipo, endereco, cidade, estado, cep, quartos, banheiros, mobiliado, valor_aluguel_sugerido, status) VALUES
('APARTAMENTO','Rua das Flores, 123','São Paulo','SP','01001-000',2,1,TRUE, 2500.00,'DISPONIVEL'),
('CASA','Av. Paulista, 1000','São Paulo','SP','01310-100',3,2,FALSE, 4500.00,'DISPONIVEL'),
('SALA','Rua XV de Novembro, 200','Curitiba','PR','80020-310',NULL,1,FALSE, 1800.00,'DISPONIVEL'),
('LOJA','Rua Sete de Setembro, 321','Rio de Janeiro','RJ','20050-002',NULL,1,FALSE, 5000.00,'DISPONIVEL'),
('TERRENO','Rodovia BR-101, km 200','Florianópolis','SC','88000-000',NULL,NULL,FALSE, 3500.00,'DISPONIVEL'),
('GALPAO','Rua Industrial, 50','Belo Horizonte','MG','31000-000',NULL,2,FALSE, 7000.00,'DISPONIVEL'),
('CASA','Rua das Palmeiras, 45','Campinas','SP','13010-200',4,3,TRUE, 3200.00,'DISPONIVEL'),
('APARTAMENTO','Av. Atlântica, 500','Rio de Janeiro','RJ','22010-000',2,2,FALSE, 4000.00,'DISPONIVEL');

-- CONTRATOS
INSERT INTO contratos (imovel_id, cliente_id, valor_mensal, data_inicio, data_fim, dia_vencimento, status) VALUES
(1, 1, 2500.00, '2024-01-01', '2024-12-31', 5, 'ATIVO'),
(2, 1, 4500.00, '2024-02-01', '2025-01-31', 10, 'ATIVO'),
(3, 1, 1800.00, '2024-03-01', '2024-09-30', 15, 'ENCERRADO');
INSERT INTO contratos (imovel_id, cliente_id, valor_mensal, data_inicio, data_fim, dia_vencimento, status) VALUES
(4, 2, 5000.00, '2024-04-01', '2025-03-31', 8, 'ATIVO'),
(5, 2, 3500.00, '2024-05-01', '2025-04-30', 12, 'PENDENTE');
INSERT INTO contratos (imovel_id, cliente_id, valor_mensal, data_inicio, data_fim, dia_vencimento, status) VALUES
(6, 3, 7000.00, '2024-06-01', '2025-05-31', 20, 'ATIVO'),
(7, 3, 3200.00, '2024-07-01', '2025-06-30', 25, 'ATIVO');
INSERT INTO contratos (imovel_id, cliente_id, valor_mensal, data_inicio, data_fim, dia_vencimento, status) VALUES
(8, 4, 4000.00, '2024-08-01', '2025-07-31', 18, 'ATIVO');
