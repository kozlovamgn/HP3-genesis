package com.example.genesis.model;

import java.util.UUID;

public record UserEntity(
        Long id,
        String name,
        String surname,
        String personId,
        UUID uuid
) {}
