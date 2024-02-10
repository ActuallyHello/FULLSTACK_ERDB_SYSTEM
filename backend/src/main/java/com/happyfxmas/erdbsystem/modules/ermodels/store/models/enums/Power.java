package com.happyfxmas.erdbsystem.modules.ermodels.store.models.enums;

import com.happyfxmas.erdbsystem.modules.ermodels.exception.validation.EnumValueException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Power {
    ONE_TO_ONE("1-1"), ONE_TO_MANY("1-N"), MANY_TO_ONE("N-1");

    private final String power;

    public static Power fromString(String shortPower) {
        for (var p : Power.values()) {
            if (p.power.equalsIgnoreCase(shortPower)) {
                return p;
            }
        }
        throw new EnumValueException("No such enum POWER with value '%s'! [ValidationException]".formatted(shortPower));
    }

    public String getValue() {
        return power;
    }
}
