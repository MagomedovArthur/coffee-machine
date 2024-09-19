-- delete redundant column
ALTER TABLE drink_statistics
    DROP COLUMN updated_at;
