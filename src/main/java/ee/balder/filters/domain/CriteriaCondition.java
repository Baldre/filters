package ee.balder.filters.domain;

import java.util.Collections;
import java.util.List;

public enum CriteriaCondition {

    MORE,
    LESS,
    FROM,
    TO,
    STARTS,
    ENDS,
    CONTAINS,
    EQUALS;

    public static List<CriteriaCondition> conditionsForType(CriteriaType type) {
        switch (type) {
            case AMOUNT -> {
                return List.of(MORE, LESS, EQUALS);
            }
            case DATE -> {
                return List.of(FROM, TO, EQUALS);
            }
            case TITLE -> {
                return List.of(STARTS, ENDS, CONTAINS, EQUALS);
            }
            default -> {
                return Collections.emptyList();
            }
        }
    }
}
