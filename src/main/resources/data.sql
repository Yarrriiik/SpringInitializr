-- Очистить пользователей (на разработке)
delete from users;

-- Пароль = "1234" (BCrypt)
insert into users(username, password, role, enabled) values
  ('yarik',   '1234', 'ROLE_ADMIN', true),
  ('neyarik', '1234', 'ROLE_USER',  true);
