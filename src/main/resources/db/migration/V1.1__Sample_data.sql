INSERT INTO filter (id, name) VALUES (-1, 'Test Filter No 1');
INSERT INTO filter (id, name) VALUES (-2, 'Test Filter No 2');
INSERT INTO filter (id, name) VALUES (-3, 'Test Filter No 3');

INSERT INTO criteria (id, filter_id, type, condition, compare_value) VALUES (-1, -1, 'AMOUNT', 'LESS', '20');
INSERT INTO criteria (id, filter_id, type, condition, compare_value) VALUES (-2, -2, 'DATE', 'TO', '20/12/2012');
INSERT INTO criteria (id, filter_id, type, condition, compare_value) VALUES (-3, -3, 'TITLE', 'EQUALS', 'Moby Dick');