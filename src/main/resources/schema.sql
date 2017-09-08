CREATE TABLE IF NOT EXISTS users (
    id                  VARCHAR(30)   NOT NULL PRIMARY KEY,
    name                VARCHAR(30)   NOT NULL,
    hashed_password     VARCHAR(1000) NOT NULL,
    role                VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS projects(
    id                  INTEGER         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(128)    NOT NULL,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL,
    description         VARCHAR(1000)   NOT NULL
);

CREATE TABLE IF NOT EXISTS members (
    id                  INTEGER        PRIMARY KEY AUTO_INCREMENT,
    project_id          INTEGER        NOT NULL,
    user_id             VARCHAR(30)    NOT NULL,
    root                BIT            NOT NULL,
    FOREIGN KEY(project_id) REFERENCES projects(id),
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS tasks (
    id                  INTEGER       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(50)   NOT NULL,
    project_id          INTEGER       NOT NULL,
    description         VARCHAR(1000) NOT NULL,
    importance          VARCHAR(10)   NOT NULL,
    progress            INTEGER       NOT NULL,
    created_at          TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deadline            DATETIME      NOT NULL,
    FOREIGN KEY(project_id) REFERENCES projects(id)
);

CREATE TABLE IF NOT EXISTS assignees (
    task_id             INTEGER       NOT NULL,
    user_id             VARCHAR(30)   NOT NULL,
    PRIMARY KEY(task_id, user_id)
);

CREATE TABLE IF NOT EXISTS requests(
    id               INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    task_id          INTEGER, --NOT NULL --FOREIGN KEY
    user_id          VARCHAR(30)   NOT NULL
 --NOT NULL --FOREIGN KEY
);

CREATE TABLE IF NOT EXISTS comments(
    id                  INTEGER         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    task_id             INTEGER,
    content             VARCHAR(1000)   NOT NULL,
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id             VARCHAR(30)     NOT NULL,
    FOREIGN KEY(task_id) REFERENCES tasks(id)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS notices (
    id              INT             PRIMARY KEY AUTO_INCREMENT,
    user_id         VARCHAR(30)     NOT NULL,
    message         VARCHAR(200)    NOT NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    un_read         BIT             NOT NULL
);

CREATE TABLE IF NOT EXISTS taskuser ( 
    id               INTEGER       NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    task_id          INTEGER       NOT NULL, 
    user_id          VARCHAR(30)   NOT NULL, 
    FOREIGN KEY(task_id) REFERENCES tasks(id), 
    FOREIGN KEY(user_id) REFERENCES users(id) 
); 
