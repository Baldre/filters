package ee.balder.filters.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Criteria {

    public Criteria(CriteriaType type, CriteriaCondition condition, String compareValue) {
        this.type = type;
        this.condition = condition;
        this.compareValue = compareValue;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CriteriaType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CriteriaCondition condition;

    @NotNull
    private String compareValue;

}
