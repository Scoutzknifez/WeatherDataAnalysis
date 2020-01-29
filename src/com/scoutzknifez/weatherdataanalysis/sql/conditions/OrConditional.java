package com.scoutzknifez.weatherdataanalysis.sql.conditions;

import com.scoutzknifez.weatherdataanalysis.sql.SQLHelper;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrConditional extends Conditional{
    private Conditional condition2;

    public OrConditional(Conditional condition1, Conditional condition2) {
        super(condition1.toString());
        setCondition2(condition2);
    }

    public OrConditional(String condition1, String condition2) {
        this(new Conditional(condition1), new Conditional(condition2));
    }

    public OrConditional(String field1, Object value1, String field2, Object value2) {
        super(SQLHelper.makeConditionalPhrase(field1, value1));
        setCondition2(new Conditional(SQLHelper.makeConditionalPhrase(field2, value2)));
    }

    @Override
    public String toString() {
        return "(" + super.toString() + " OR " + getCondition2().toString() + ")";
    }
}
