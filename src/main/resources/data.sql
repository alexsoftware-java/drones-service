/*
    Add 10 default drones to dispatcher
*/
insert into drones (serial_number, model, state, weight_limit, battery_capacity) values ('DRONE-1', 'LIGHTWEIGHT','IDLE', 100, 100);
insert into drones (serial_number, model, state, weight_limit, battery_capacity) values ('DRONE-2', 'LIGHTWEIGHT','IDLE', 150, 70);
insert into drones (serial_number, model, state, weight_limit, battery_capacity) values ('DRONE-3', 'LIGHTWEIGHT','IDLE', 150, 75);
insert into drones (serial_number, model, state, weight_limit, battery_capacity) values ('DRONE-4', 'MIDDLEWEIGHT','IDLE', 200, 100);
insert into drones (serial_number, model, state, weight_limit, battery_capacity) values ('DRONE-5', 'MIDDLEWEIGHT','IDLE', 220, 100);
insert into drones (serial_number, model, state, weight_limit, battery_capacity) values ('DRONE-6', 'CRUISERWEIGHT','IDLE', 300, 85);
insert into drones (serial_number, model, state, weight_limit, battery_capacity) values ('DRONE-7', 'CRUISERWEIGHT','IDLE', 300, 80);
insert into drones (serial_number, model, state, weight_limit, battery_capacity) values ('DRONE-8', 'CRUISERWEIGHT','IDLE', 400, 99);
insert into drones (serial_number, model, state, weight_limit, battery_capacity) values ('DRONE-9', 'LIGHTWEIGHT','IDLE', 500, 40);
insert into drones (serial_number, model, state, weight_limit, battery_capacity) values ('DRONE-10', 'LIGHTWEIGHT','IDLE', 500, 10);

/*
    Add some initial goods to few of them
*/
insert into goods (goods_type, name, weight, code, drone_id) values (1, 'ASPIRIN', 50, 'ASP_1', 1);
insert into goods (goods_type, name, weight, code, drone_id) values (1, 'PILLS_TO_BECOME_GOOD_DEV', 200, 'SUPER_PIL', 5);
insert into goods (goods_type, name, weight, code, drone_id) values (1, 'ASPIRIN', 150, 'ASP_3', 10);