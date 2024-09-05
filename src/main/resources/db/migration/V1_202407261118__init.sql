DROP TABLE IF EXISTS history;

CREATE TABLE history (
   id SERIAL PRIMARY KEY,
   message_id VARCHAR(255),
   thread_id VARCHAR(255),
   created_at TIMESTAMP WITHOUT TIME ZONE,
   role VARCHAR(255),
   source_type VARCHAR(255),
   source_id VARCHAR(255),
   source_language VARCHAR(255),
   source_revision VARCHAR(255),
   content TEXT
);
