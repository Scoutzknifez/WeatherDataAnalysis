package com.scoutzknifez.weatherdataanalysis.sql.insertion;

import com.scoutzknifez.weatherdataanalysis.sql.SQLHelper;
import com.scoutzknifez.weatherdataanalysis.sql.Table;
import com.scoutzknifez.weatherdataanalysis.sql.Worker;
import com.scoutzknifez.weatherdataanalysis.utility.Utils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsertWorker extends Worker {
    private String objectStringForm;

    public InsertWorker(Table table, Databasable databasableObject) {
        super(table);
        setObjectStringForm(SQLHelper.databasableToInsertionForm(databasableObject));
    }

    @Override
    public void run() {
        if (getStatement() == null)
            return;

        doInsertion();
        closeStatement();
    }

    private void doInsertion() {
        String sqlArg = "INSERT INTO " + getTable().name() + " VALUES " + getObjectStringForm();
        try {
            getStatement().execute(sqlArg);
        } catch (Exception e) {
            Utils.log("Failed to do insertion on table: " + getTable().name());
        }
    }
}
