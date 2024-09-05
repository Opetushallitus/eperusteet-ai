CREATE TABLE app_preference (
   id SERIAL PRIMARY KEY,
   property VARCHAR(255),
   value VARCHAR(255)
);

INSERT INTO app_preference(property, value) VALUES ('SERVICE_AVAILABLE', 'true');