CREATE TABLE IF NOT EXISTS filter (
    id SERIAL PRIMARY KEY,
    name text NOT NULL
);

CREATE TABLE IF NOT EXISTS criteria (
    id SERIAL PRIMARY KEY,
    filter_id bigint REFERENCES filter(id),
    type text NOT NULL,
    condition text NOT NULL,
    compare_value text NOT NULL
);