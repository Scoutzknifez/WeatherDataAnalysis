package com.scoutzknifez.weatherdataanalysis.sql;

import com.scoutzknifez.weatherdataanalysis.sql.conditions.Conditional;
import com.scoutzknifez.weatherdataanalysis.sql.insertion.Databasable;
import com.scoutzknifez.weatherdataanalysis.sql.insertion.InsertWorker;
import com.scoutzknifez.weatherdataanalysis.utility.HiddenConstants;
import com.scoutzknifez.weatherdataanalysis.utility.Utils;
import lombok.NonNull;

import java.util.List;

public class SQLHelper {
    public static void closeSafely() {
        try {
            Worker.connection.close();
        } catch (Exception e) {
            // Unref and let GC handle it
            // TODO This may be incorrect handling when this occurs
            GetWorker.connection = null;
            Utils.log("Could not close the connection to the database at " + HiddenConstants.DATABASE_URL);
        }
    }

    /**
     * Updates a database entry in table specified with the new data
     * presented by fieldName (the database column) and the
     * updateValue (the value that goes into the database column)
     * when the database entry meets the given conditions (Must have some)
     *
     * @param table         The table to update from
     * @param fieldName     The column to update with new value
     * @param updateValue   The new value in the column
     * @param conditions    The conditions to meet for the update to occur
     * @return              didSucceed
     */
    public static boolean updateTable(Table table, String fieldName, Object updateValue, @NonNull Conditional conditions) {
        UpdateWorker worker = new UpdateWorker(table, fieldName, updateValue, conditions);
        Thread thread = new Thread(worker);

        thread.start();
        try {
            thread.join();
            return true;
        } catch (Exception e) {
            Utils.log("Failed to update on table " + table.name() + ")" + " updating item with conditions of " + conditions.toString());
        }
        return false;
    }

    /**
     * Inserts a item into the table specified
     * @param table         The table to insert into
     * @param databasable   The item you are inserting
     * @return              didSucceed
     */
    public static boolean insertIntoTable(Table table, Databasable databasable) {
        InsertWorker worker = new InsertWorker(table, databasable);
        Thread thread = new Thread(worker);

        thread.start();
        try {
            thread.join();
            return true;
        } catch (Exception e) {
            Utils.log("Could not insert item (" + databasable + ") into table (" + table.name() + ")!");
        }
        return false;
    }

    /**
     * Gets a list of objects with given conditions that are met.
     * @param table         The table to fetch from
     * @param conditions    The conditions to meet to be part of the list
     * @return              List of objects from table meeting conditions.
     */
    public static List<?> getFromTableWithConditions(Table table, Conditional conditions) {
        GetWorker worker = new GetWorker(table, conditions);
        Thread thread = new Thread(worker);

        thread.start();
        try {
            thread.join();
            return worker.getItems();
        } catch (Exception e) {
            Utils.log("Fetch from table (" + table.name() + ") failed!");
            throw new NullPointerException("Fetch from table (" + table.name() + ") failed!");
        }
    }

    public static List<?> getFromTable(Table table) {
        return getFromTableWithConditions(table, null);
    }

    /**
     * Returns an object in raw field -> value form
     *
     * Example.
     * A user object with fields id, name, and password would become:
     * (valueOf(id),valueOf(name),valueOf(password))
     *
     * Where valueOf is the data stored in the field itself
     *
     * @param databasableObject Any object that can be databased
     * @return                  A raw string form of the objects values
     */
    public static String databasableToInsertionForm(Databasable databasableObject) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");

        Object[] fieldsInArray = databasableObject.fieldsToArray();

        for (int i = 0; i < fieldsInArray.length; i++) {
            Object obj = fieldsInArray[i];
            if (obj instanceof String) {
                String built = "\"" + obj + "\"";
                sb.append(built);
            }
            else
                sb.append(obj);

            if (i != fieldsInArray.length - 1)
                sb.append(",");
        }

        sb.append(")");
        return sb.toString();
    }

    /**
     * Makes a conditional SQL phrase out of a field and
     * value in the following form:
     *
     * `columnName` = "value" for String
     *                value for others (May need to be reversed, will think about it)
     *
     * @param field columnName of field to check
     * @param value value to check for
     * @return      string phrase for conditional
     */
    public static String makeConditionalPhrase(String field, Object value) {
        return "`" + field + "` = " + (value instanceof String ?
                "\"" + value + "\"" :
                value);
    }
}
