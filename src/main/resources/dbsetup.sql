CREATE TABLE IF NOT EXISTS superhero_players
(
    uuid CHAR(36) NOT NULL,
    hero TEXT NOT NULL,
    hero_cmd_timestamp BIGINT NOT NULL,
    PRIMARY KEY (uuid)
);