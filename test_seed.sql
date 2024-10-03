SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO departments (name) VALUES ("Engineering");

SET
    @department_id = (
        SELECT id
        FROM departments
        WHERE
            name = "Engineering"
        LIMIT 1
    );

INSERT INTO
    users (
        email,
        password,
        username,
        department_id
    )
VALUES (
        "test@test.com",
        "$2a$12$KQNGx8wl1Zm6ns2AxgnS5esFDpWQXw26rmXIYbwel66rMXq9nhuNS",
        "testuser",
        @department_id
    );

SET
    @user_id = (
        SELECT id
        FROM users
        WHERE
            username = "testuser"
        LIMIT 1
    );

INSERT INTO roles (name) VALUES ("ROLE_ADMIN"), ("ROLE_USER");

SET
    @role_id = (
        SELECT id
        FROM roles
        WHERE
            name = "ROLE_USER"
        LIMIT 1
    );

INSERT INTO
    users_roles (user_id, role_id)
VALUES (@user_id, @role_id);

INSERT INTO
    abbreviations (name, user_id)
VALUES ("B2B", @user_id),
    ("B2C", @user_id),
    ("ROI", @user_id),
    ("KPI", @user_id),
    ("CRM", @user_id);

SET
    @b2b_id = (
        SELECT id
        FROM abbreviations
        WHERE
            name = "B2B"
        LIMIT 1
    );

SET
    @b2c_id = (
        SELECT id
        FROM abbreviations
        WHERE
            name = "B2C"
        LIMIT 1
    );

SET
    @roi_id = (
        SELECT id
        FROM abbreviations
        WHERE
            name = "ROI"
        LIMIT 1
    );

SET
    @kpi_id = (
        SELECT id
        FROM abbreviations
        WHERE
            name = "KPI"
        LIMIT 1
    );

SET
    @crm_id = (
        SELECT id
        FROM abbreviations
        WHERE
            name = "CRM"
        LIMIT 1
    );

INSERT INTO
    meanings (
        description,
        count_of_votes,
        abbreviation_id,
        user_id
    )
VALUES (
        "Business to Business",
        0,
        @b2b_id,
        @user_id
    ),
    (
        "Business to Consumer",
        0,
        @b2c_id,
        @user_id
    ),
    (
        "Return on Investment",
        0,
        @roi_id,
        @user_id
    ),
    (
        "Key Performance Indicator",
        0,
        @kpi_id,
        @user_id
    ),
    (
        "Customer Relationship Management",
        0,
        @crm_id,
        @user_id
    );

SET FOREIGN_KEY_CHECKS = 1;