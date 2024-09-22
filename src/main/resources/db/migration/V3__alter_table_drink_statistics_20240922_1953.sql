-- delete redundant column
ALTER TABLE drink_statistics
    DROP COLUMN order_count;