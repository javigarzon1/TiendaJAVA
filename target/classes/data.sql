INSERT INTO servicios (nombre, descripcion, duracion_minutos, precio, activo) VALUES
('Asesoramiento de equipación', 'Sesión para ayudarte a elegir el equipo adecuado a tu deporte', 30, 0.00, true),
('Prueba de calzado / tallaje', 'Medimos tu talla y probamos modelos en tienda', 20, 0.00, true),
('Personalización (grabado/parche)', 'Personalización de una prenda o calzado ya comprado', 45, 12.00, true),
('Reparación de prendas técnicas', 'Revisión y arreglo de prendas y calzado deportivo', 30, 0.00, true);

INSERT INTO productos (id, nombre, deporte, genero, prenda, precio, stock, activo) VALUES
(1, 'Camiseta técnica Run+', 'Running', 'Hombre', 'Camisetas', 29.90, 40, true),
(2, 'Camiseta transpirable Flow', 'Running', 'Mujer', 'Camisetas', 27.90, 35, true),
(3, 'Mallas compresión Pro', 'Running', 'Mujer', 'Pantalones', 39.90, 30, true),
(4, 'Short entreno Speed', 'Fútbol', 'Hombre', 'Pantalones', 24.90, 50, true),
(5, 'Zapatillas running Aero', 'Running', 'Hombre', 'Calzado', 89.90, 20, true),
(6, 'Botas fútbol Grip', 'Fútbol', 'Hombre', 'Calzado', 74.90, 22, true),
(7, 'Chaqueta cortavientos Storm', 'Ciclismo', 'Unisex', 'Chaquetas', 64.90, 18, true),
(8, 'Sudadera gym Core', 'Gimnasio', 'Hombre', 'Chaquetas', 42.90, 28, true),
(9, 'Top deportivo Fit', 'Gimnasio', 'Mujer', 'Camisetas', 19.90, 45, true),
(10, 'Bañador competición Wave', 'Natación', 'Mujer', 'Pantalones', 34.90, 25, true),
(11, 'Gorro natación Splash', 'Natación', 'Unisex', 'Accesorios', 9.90, 60, true),
(12, 'Guantes ciclismo Grip', 'Ciclismo', 'Hombre', 'Accesorios', 14.90, 33, true),
(13, 'Mochila entreno Pack', 'Gimnasio', 'Unisex', 'Accesorios', 32.90, 27, true),
(14, 'Camiseta niño Junior Play', 'Fútbol', 'Niños', 'Camisetas', 17.90, 38, true),
(15, 'Zapatillas gimnasio Flex', 'Gimnasio', 'Mujer', 'Calzado', 69.90, 24, true);

INSERT INTO producto_tallas (producto_id, talla) VALUES
(1,'S'),(1,'M'),(1,'L'),(1,'XL'),
(2,'XS'),(2,'S'),(2,'M'),(2,'L'),
(3,'XS'),(3,'S'),(3,'M'),(3,'L'),
(4,'M'),(4,'L'),(4,'XL'),
(5,'M'),(5,'L'),(5,'XL'),
(6,'M'),(6,'L'),(6,'XL'),
(7,'S'),(7,'M'),(7,'L'),(7,'XL'),
(8,'M'),(8,'L'),(8,'XL'),
(9,'XS'),(9,'S'),(9,'M'),
(10,'XS'),(10,'S'),(10,'M'),(10,'L'),
(12,'S'),(12,'M'),(12,'L'),
(14,'S'),(14,'M'),
(15,'XS'),(15,'S'),(15,'M'),(15,'L');
