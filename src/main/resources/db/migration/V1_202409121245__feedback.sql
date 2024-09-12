DROP TABLE IF EXISTS history;
DROP TABLE IF EXISTS feedback;
DROP TABLE IF EXISTS message;

CREATE TABLE message (
    id SERIAL PRIMARY KEY,
    thread_id VARCHAR(255),
    message_id VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    role VARCHAR(255),
    content TEXT,
    meta jsonb
);

ALTER TABLE message ADD CONSTRAINT uc_message_message UNIQUE (message_id);

CREATE TABLE feedback (
    id SERIAL PRIMARY KEY,
    result VARCHAR(255),
    comment TEXT,
    message_id VARCHAR(255)
);

ALTER TABLE feedback ADD CONSTRAINT uc_feedback_message UNIQUE (message_id);
ALTER TABLE feedback ADD CONSTRAINT FK_FEEDBACK_ON_MESSAGE FOREIGN KEY (message_id) REFERENCES message (message_id);