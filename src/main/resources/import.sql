INSERT INTO users (id, email, password, username, fullname, role) VALUES (1, 'admin@admin.com', '$2a$12$WOaAG8Z21ON.DuLQbfW9LOTXkL36EILNXPT23X12A0eDoMQKfOHwa', 'ProfesorAdmin', 'Admin Principal', 'ADMIN');
INSERT INTO users (id, email, password, username, fullname, role) VALUES (2, 'gestor@gestor.com', '$2a$12$WOaAG8Z21ON.DuLQbfW9LOTXkL36EILNXPT23X12A0eDoMQKfOHwa', 'GestorTodoList', 'Gestor de Tareas', 'GESTOR');
INSERT INTO users (id, email, password, username, fullname, role) VALUES (3, 'alumno@alumno.com', '$2a$12$WOaAG8Z21ON.DuLQbfW9LOTXkL36EILNXPT23X12A0eDoMQKfOHwa', 'AlumnoDWES', 'Adrian Alumno', 'USER');

INSERT INTO categories (title) VALUES ('Universidad');
INSERT INTO categories (title) VALUES ('Desarrollo');

INSERT INTO tags (name) VALUES ('java');
INSERT INTO tags (name) VALUES ('spring');

INSERT INTO tasks (title, description, completed, author_id, category_id, priority, deadline, created_at) VALUES ('Revisar API REST', 'Probar los endpoints de Swagger y corregir el import.sql', false, 3, 1, 'HIGH', '2026-06-01', CURRENT_TIMESTAMP);

INSERT INTO task_tags (task_id, tag_id) VALUES (1, 1);
INSERT INTO task_tags (task_id, tag_id) VALUES (1, 2);