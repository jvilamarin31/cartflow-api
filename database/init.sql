CREATE DATABASE `cartflow`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE `cartflow`;

CREATE TABLE `user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `email` varchar(100) NOT NULL COMMENT 'Identificador principal para el login',
    `password` varchar(100) NOT NULL COMMENT 'Hash encriptado con BCrypt',
    `name` varchar(100) NOT NULL,
    `role` varchar(20) NOT NULL COMMENT 'Rol del usuario: USER o ADMIN',
    `email_verified` boolean NOT NULL DEFAULT FALSE COMMENT 'Estado de verificación del correo',
    `verification_token` varchar(100) DEFAULT NULL COMMENT 'Token único de verificación de correo',
    `verification_token_expires_at` timestamp NULL DEFAULT NULL COMMENT 'Expiración del token de verificación',
    `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_email_unique` (`email`),
    UNIQUE KEY `user_verification_token_unique` (`verification_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `product` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `description` text DEFAULT NULL,
    `price` decimal(10,2) NOT NULL,
    `stock` int(11) NOT NULL DEFAULT 0,
    `is_active` boolean NOT NULL DEFAULT TRUE,
    `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
    `created_by` varchar(100) DEFAULT NULL,
    `updated_by` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `cart` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `cart_user_unique` (`user_id`),
    CONSTRAINT `cart_user_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `cart_item` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `cart_id` bigint(20) NOT NULL,
    `product_id` bigint(20) NOT NULL,
    `quantity` int(11) NOT NULL DEFAULT 1,
    `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `cart_product_unique` (`cart_id`, `product_id`),
    KEY `fk_cart_item_product` (`product_id`),
    CONSTRAINT `fk_cart_item_cart` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_cart_item_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `order` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) NOT NULL,
    `total_amount` decimal(10,2) NOT NULL,
    `status` varchar(20) NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING, PAID, FAILED, CANCELLED',
    `stripe_session_id` varchar(100) DEFAULT NULL COMMENT 'ID de la sesión en Stripe para trazabilidad',
    `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    KEY `order_user_FK` (`user_id`),
    CONSTRAINT `order_user_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `order_item` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `order_id` bigint(20) NOT NULL,
    `product_id` bigint(20) NOT NULL,
    `quantity` int(11) NOT NULL,
    `unit_price` decimal(10,2) NOT NULL COMMENT 'Precio congelado en el momento de la compra',
    `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`),
    KEY `fk_order_item_order` (`order_id`),
    KEY `fk_order_item_product` (`product_id`),
    CONSTRAINT `fk_order_item_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_order_item_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `support_ticket` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) NOT NULL COMMENT 'Cliente que creó el ticket',
    `order_id` bigint(20) DEFAULT NULL COMMENT 'Pedido relacionado (si aplica)',
    `category` varchar(30) NOT NULL COMMENT 'ORDER_ISSUE, PAYMENT_ISSUE, ACCOUNT_ISSUE',
    `subject` varchar(200) NOT NULL,
    `message` text NOT NULL COMMENT 'Mensaje inicial del cliente',
    `status` varchar(20) NOT NULL DEFAULT 'OPEN' COMMENT 'OPEN, IN_PROGRESS, RESOLVED, CLOSED',
    `assigned_to` bigint(20) DEFAULT NULL COMMENT 'Admin que tomó el caso',
    `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
    `updated_at` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    KEY `ticket_user_FK` (`user_id`),
    KEY `ticket_order_FK` (`order_id`),
    KEY `ticket_assigned_FK` (`assigned_to`),
    KEY `ticket_status_idx` (`status`),
    CONSTRAINT `ticket_user_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `ticket_order_FK` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE SET NULL,
    CONSTRAINT `ticket_assigned_FK` FOREIGN KEY (`assigned_to`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `support_ticket_reply` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `ticket_id` bigint(20) NOT NULL,
    `author_id` bigint(20) NOT NULL COMMENT 'Usuario que escribió la respuesta (cliente o admin)',
    `is_admin_reply` boolean NOT NULL DEFAULT FALSE COMMENT 'TRUE si la respuesta es del admin',
    `message` text NOT NULL,
    `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`),
    KEY `reply_ticket_FK` (`ticket_id`),
    KEY `reply_author_FK` (`author_id`),
    CONSTRAINT `reply_ticket_FK` FOREIGN KEY (`ticket_id`) REFERENCES `support_ticket` (`id`) ON DELETE CASCADE,
    CONSTRAINT `reply_author_FK` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
