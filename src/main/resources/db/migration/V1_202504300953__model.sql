CREATE TABLE model (
  id VARCHAR(255) NOT NULL,
   default_model BOOLEAN NOT NULL,
   description VARCHAR(255),
   CONSTRAINT pk_model PRIMARY KEY (id)
);

INSERT INTO model (id, default_model, description) VALUES
('gpt-4.1', false, 'Flagship GPT model for complex tasks'),
('gpt-4.1-mini', true, 'Fast, affordable small model for focused tasks'),
('gpt-4o', false, 'Fast, intelligent, flexible GPT model'),
('gpt-4o-mini', false, 'Faster, more affordable reasoning model'),
('o3-mini', false, 'A small model alternative to o3, most powerful reasoning model');
