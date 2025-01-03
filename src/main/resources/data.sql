ALTER TABLE user_roles ADD CONSTRAINT unique_user_role UNIQUE (user_id, role_id);
ALTER TABLE role_dashboard_menu ADD CONSTRAINT unique_role_dashboard_menu UNIQUE (role_id,dashboard_menu_id);
INSERT INTO public.roles (create_date, deleted, update_date, ad) VALUES(now(), 0, now() , 'ROLE_ADMIN');
INSERT INTO public.users (create_date, deleted, update_date, ad, aktif, email, "password", soyad, username) VALUES(now(), 0, now() , 'admin', true, 'admin@admin.com', '$2a$10$H7s08w7IXI7N.1OLmvei/ukegkIAJhDvteOYKm6qNq3y/6rpBF5Pm', 'admin', 'admin');
INSERT INTO public.user_roles (user_id, role_id) VALUES(1,1);

INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Tanımlamalar', 1, 'tanimlamalar', NULL,'FaClipboardList');
INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Role Tanımla', 1, 'rol', 1,'FaCirclePlus');
INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Domain Tanımla', 2, 'domain', 1,'FaCirclePlus');
INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Kullanıcı Tanımla', 3, 'kullanici', 1,'FaCirclePlus');
INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Dil Tanımla', 4, 'dil', 1,'FaCirclePlus');

INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Menü Tanımla', 5,'menu', 1,'FaCirclePlus');
INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Alt Menü Tanımla', 6, 'alt-menu', 1,'FaCirclePlus');
INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Anasayfa İçerik Doldur', 7,'ana-sayfa-menu-icerik', 1,'FaCirclePlus');
INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Menü İçerik Doldur', 8,'menu-icerik-doldur', 1,'FaCirclePlus');
INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Ikinci Sutun Alt Menu  Ekle',9, 'ikinci-sutun-alt-menu-ekle', 1,'FaCirclePlus');

INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Yok', 2, 'yok', NULL,'FaClipboardList');
INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Anasayfa Slider', 1, 'anasayfa-slider', 11,'FaCirclePlus');
INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Anasayfa Story', 2, 'anasayfa-story', 11,'FaCirclePlus');
INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Anasayfa Hızlı Bağlantılar', 3, 'anasayfa-hizli-baglantilar', 11,'FaCirclePlus');
INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Anasayfa Duyuru', 4, 'anasayfa-duyuru', 11,'FaCirclePlus');
INSERT INTO public.dashboard_menu (create_date, deleted, update_date, name, "number", "path", parent_id,icon_name) VALUES('2024-08-04 16:30:32.403', 0, '2024-08-04 16:30:32.403', 'Anasayfa Haber', 5, 'anasayfa-haber', 11,'FaCirclePlus');



INSERT INTO public.role_dashboard_menu (role_id, dashboard_menu_id) VALUES(1, 1);
INSERT INTO public.role_dashboard_menu (role_id, dashboard_menu_id) VALUES(1, 2);
INSERT INTO public.role_dashboard_menu (role_id, dashboard_menu_id) VALUES(1, 3);
INSERT INTO public.role_dashboard_menu (role_id, dashboard_menu_id) VALUES(1, 4);
INSERT INTO public.role_dashboard_menu (role_id, dashboard_menu_id) VALUES(1, 5);



--INSERT INTO public.genel_dil_category (kisaltma_eki, name) VALUES('TR', 'TÜRKÇE');