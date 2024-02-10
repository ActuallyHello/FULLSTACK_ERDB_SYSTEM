package com.happyfxmas.erdbsystem.modules.tasks.store.models.enums;

import com.happyfxmas.erdbsystem.modules.ermodels.exception.validation.EnumValueException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Mark {
    EXCELLENT(5), BAD(2), SATISFACTORY(3), GOOD(4);

    private final Integer mark;

    public static Mark fromInteger(Integer mark) {
        for (var m : Mark.values()) {
            if (m.mark.equals(mark)) {
                return m;
            }
        }
        throw new EnumValueException("No such enum PersonType with value '%s'! [ValidationException]".formatted(mark));
    }

    public Integer getValue() {
        return mark;
    }
}
