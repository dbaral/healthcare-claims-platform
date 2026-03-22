package com.debish.health.claimintake.application.dto;

import java.math.BigDecimal;

public record ProcedureCommand(String code, Integer units, BigDecimal amount) {
}
