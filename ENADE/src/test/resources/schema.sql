CREATE TABLE IF NOT EXISTS  produto (
	id int PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    preco int NOT NULL,
    quantidade int NOT NULL
);
INSERT INTO produto (nome, preco, quantidade) VALUES 
('Celular Motorola 84', 1200, 2),
('Notebook Acer Nitro 5', 3000, 3),
('Tablet Samsung Galaxy Tab', 2000, 1),
('Samsung Galaxy S21', 4000, 10),
('Fone de Ouvido JBL QUANTUM', 300, 2),
('Fone de Ouvido Sony WH-1000XM4', 250, 1),
('Mesa Digitalizadora', 3000, 5),
('Teclado Mecânico Razer', 800, 9),
('Mouse Gamer Logitech G502', 500, 20),
('Monitor LG UltraGear', 1500, 3),
('Cadeira Gamer DXRacer', 2000, 1),
('Webcam Logitech C920', 400, 10),
('Microfone Blue Yeti', 600, 5),
('Placa de Captura Elgato HD60 S', 800, 4),
('Headset HyperX Cloud II', 350, 5),
('SSD Samsung 970 EVO Plus', 700, 3),
('HD Externo Seagate Backup Plus', 400, 2),
('Impressora HP DeskJet 3755', 250, 5),
('Scanner Canon CanoScan LiDE 300', 300, 5);