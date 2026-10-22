
CREATE TABLE categories (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description VARCHAR(255) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL,
                          category_id INT,
                          CONSTRAINT fk_products_categories
                              FOREIGN KEY (category_id)
                                  REFERENCES categories(id)
);
