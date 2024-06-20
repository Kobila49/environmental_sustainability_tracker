-- Insert data into users table
insert into users (id, email, first_name, last_name, date_of_birth, password, role, created_at, updated_at)
values (1, 'ikos1801@gmail.com', 'Igor', 'Kos', '1993-01-18',
        '$2a$10$BDfCgW1h2SM1RRKYymnohu5T.bU0/uqcgK3dtaz9L7nwyn3bkhHm2', 'ADMIN', now(), now());

-- Insert data into food table
insert into food (id, name, emission_factor, category, created_at, updated_at)
values (1, 'Beef', 27.0, 'Meat', now(), now()),
       (2, 'Chicken', 6.9, 'Meat', now(), now()),
       (3, 'Pork', 7.9, 'Meat', now(), now()),
       (4, 'Tofu', 2.0, 'Plant Based', now(), now()),
       (5, 'Milk', 1.9, 'Dairy', now(), now()),
       (6, 'Eggs', 4.8, 'Animal Products', now(), now()),
       (7, 'Rice', 2.7, 'Grains', now(), now()),
       (8, 'Potatoes', 0.3, 'Vegetables', now(), now()),
       (9, 'Carrots', 0.2, 'Vegetables', now(), now()),
       (10, 'Apples', 0.3, 'Fruits', now(), now());

-- Insert data into transportation table
insert into transportation (id, name, emission_factor, created_at, updated_at)
values (1, 'Car - Diesel', 0.192, now(), now()),
       (2, 'Car - Gasoline', 0.171, now(), now()),
       (3, 'Bus', 0.089, now(), now()),
       (4, 'Train', 0.041, now(), now()),
       (5, 'Plane', 0.255, now(), now()),
       (6, 'Bike', 0.0, now(), now()),
       (7, 'Walk', 0.0, now(), now());

-- Insert data into utility table
insert into utility (id, type, emission_factor, measurement_unit, created_at, updated_at)
values (1, 'ELECTRICITY', 0.527, 'kWh', now(), now()),
       (2, 'NATURAL_GAS', 1.99, 'm3', now(), now()),
       (3, 'WASTE', 0.49, 'kg', now(), now());

