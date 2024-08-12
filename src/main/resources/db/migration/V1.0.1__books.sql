CREATE TABLE books (
    book_id    SERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    author_id  INT REFERENCES authors (author_id) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
