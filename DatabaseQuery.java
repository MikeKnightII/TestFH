package com.ffh.application;

import com.ffh.application.data.User;

public record DatabaseQuery() {

    /*  No arg constructor for DatabaseQuery  */


    public String getExactMatchUser(String fieldName, String userInput) {
        return "SELECT * FROM users WHERE " + fieldName + "='" + userInput + "'";
    }

    public String getInventoryItemLikeQuery(String[] searchStrings) {
        return "SELECT * FROM inventory WHERE item_name LIKE '" +
                "%" +
                searchStrings[0] +
                "%'" +
                "ORDER BY item_no;";
    }

    public String getUserLikeQuery(String[] searchStrings) {
        return "SELECT * FROM users WHERE Username LIKE '" +
                "%" +
                searchStrings[0] +
                "%'" +
                "ORDER BY Username;";
    }

    /*  Search the database for a particular person by first and/or last name  */
    public String getPersonQuery(String[] searchStrings) {

        String clients = "SELECT * FROM person WHERE ssn IN (SELECT ssn FROM service_participant WHERE isClient=true)";

        if (searchStrings.length == 1) {
            return "SELECT * FROM (" + clients + ") sub WHERE name_first LIKE '" +  // 'sub' is an alias for the inner table
                    "%" +
                    searchStrings[0] +
                    "%" +
                    "' OR name_last LIKE '" +
                    "%" +
                    searchStrings[0] +
                    "%'" +
                    "ORDER BY name_last;";
        } else {    // the only other option is that the effective length of searchStrings is two.
            return "SELECT * FROM (" + clients + ") sub WHERE name_first LIKE '" +
                    "%" +
                    searchStrings[0] +
                    "%" +
                    "' AND name_last LIKE '" +
                    "%" +
                    searchStrings[1] +
                    "%'" +
                    "ORDER BY name_last;";
        }
    }

    /*  Delete a user from the 'users' table in the database  */
    public String deleteUserQuery(User user) {
        return "DELETE FROM users WHERE Username='" + user.getUsername() + "';";
    }

    public String tableUpdateQuery(String tablename, String column, String value, String condition) {
        return "UPDATE " + tablename + " SET " + column + "=" + value + " WHERE " + condition;
    }
}