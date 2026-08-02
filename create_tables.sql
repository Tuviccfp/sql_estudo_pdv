USE negocio;
CREATE TABLE estabelecimento (id int PRIMARY KEY AUTO_INCREMENT, nome char(255) not null);
CREATE TABLE produto (
	id int PRIMARY KEY AUTO_INCREMENT, 
    nome CHAR(255) NOT NULL, 
    preco int NOT NULL
    );
CREATE TABLE venda (
	id int PRIMARY KEY AUTO_INCREMENT, 
    estabelecimento_id int NOT NULL, 
    data_venda DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento(id)
    );
CREATE TABLE item_venda (
	id int PRIMARY KEY AUTO_INCREMENT, 
    venda_id int NOT NULL, 
    produto_id int NOT NULL,
	FOREIGN KEY (venda_id) REFERENCES venda(id),
    FOREIGN KEY (produto_id) REFERENCES produto(id)
    );