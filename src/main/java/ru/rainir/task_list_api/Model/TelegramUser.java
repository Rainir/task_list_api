package ru.rainir.task_list_api.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "telegram_users")
public class TelegramUser {

    @JoinColumn(name = "user_id")
    private Long userId;

    private String username;

    @Column(unique = true, nullable = false)
    @Id
    private Long telegramId;

    @Column(nullable = false)
    private String telegramUsername;
}