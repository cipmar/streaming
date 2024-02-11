CREATE TABLE `streaming`.`temperature`
(
    `id`       INT NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(100) NULL,
    `type`     VARCHAR(100) NULL,
    `category` VARCHAR(100) NULL,
    `state`    DECIMAL(4, 2) NULL,
    PRIMARY KEY (`id`)
);
