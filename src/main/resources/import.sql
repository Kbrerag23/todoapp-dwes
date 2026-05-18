INSERT INTO users (email, password, username, fullname, role) VALUES ('admin@admin.com', '$2a$10$5dRggeC9b.fkH9TywyZjAOuWcz/uafW.vKdECod0l5HmJxzeKoKLS', 'ProfesorAdmin', 'Admin Principal', 'ADMIN');
INSERT INTO users (email, password, username, fullname, role) VALUES ('gestor@gestor.com', '$2a$10$5dRggeC9b.fkH9TywyZjAOuWcz/uafW.vKdECod0l5HmJxzeKoKLS', 'GestorTest', 'Gestor de Tareas', 'GESTOR');
INSERT INTO users (email, password, username, fullname, role) VALUES ('alumno@alumno.com', '$2a$10$5dRggeC9b.fkH9TywyZjAOuWcz/uafW.vKdECod0l5HmJxzeKoKLS', 'AlumnoTest', 'Alumno DWES', 'USER');

INSERT INTO categories (title) VALUES ('Universidad');
INSERT INTO categories (title) VALUES ('Desarrollo');

INSERT INTO tags (name) VALUES ('java');
INSERT INTO tags (name) VALUES ('spring');

INSERT INTO tasks (title, description, completed, author_id, category_id, priority, deadline, created_at) VALUES ('Revisar API REST', 'Probar los endpoints de Swagger y corregir el import.sql', false, 3, 1, 'HIGH', '2026-06-01', CURRENT_TIMESTAMP);

INSERT INTO task_tags (task_id, tag_id) VALUES (1, 1);
INSERT INTO task_tags (task_id, tag_id) VALUES (1, 2);