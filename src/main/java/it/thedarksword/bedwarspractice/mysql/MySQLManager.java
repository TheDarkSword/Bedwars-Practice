package it.thedarksword.bedwarspractice.mysql;

import it.thedarksword.bedwarspractice.config.ConfigValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@Getter
@RequiredArgsConstructor
public class MySQLManager {

    private final ConfigValue configValue;
    private final MySQL mySQL;

    public void createTables() throws SQLException {
        mySQL.createTable(new String[] {
                "id INT PRIMARY KEY AUTO_INCREMENT",
                "uuid VARCHAR(36) NOT NULL UNIQUE",
                "username VARCHAR(30) NOT NULL"
        }, configValue.TABLE_PLAYERS);

        mySQL.createTable(new String[] {
                "id INT PRIMARY KEY AUTO_INCREMENT",
                "name VARCHAR(50) NOT NULL UNIQUE"
        }, configValue.TABLE_TYPES);

        mySQL.createTable(new String[] {
                "id INT PRIMARY KEY AUTO_INCREMENT",
                "time FLOAT NOT NULL",
                "player_id INT NOT NULL",
                "type_id INT NOT NULL",
                "FOREIGN KEY (player_id) REFERENCES " + configValue.TABLE_PLAYERS + "(id) ON DELETE CASCADE ON UPDATE CASCADE",
                "FOREIGN KEY (type_id) REFERENCES " + configValue.TABLE_TYPES + "(id) ON DELETE CASCADE ON UPDATE CASCADE"
        }, configValue.TABLE_RECORDS);
    }

    public void savePlayer(String uuid, String username) throws SQLException {
        if(!mySQL.rowExists("uuid", uuid, configValue.TABLE_PLAYERS)) {
            mySQL.addRow(new String[]{
                    "uuid",
                    "username"
            }, new Object[]{
                    uuid,
                    username
            }, configValue.TABLE_PLAYERS);
        }
    }

    public void registerType(String name) throws SQLException {
        if(!mySQL.rowExists("name", name, configValue.TABLE_TYPES)) {
            mySQL.addRow(new String[]{
                    "name"
            }, new Object[]{
                    name
            }, configValue.TABLE_TYPES);
        }
    }

    public void saveRecord(String username, String name, float time) throws SQLException {
        if(mySQL.rowExists("name", name, configValue.TABLE_TYPES)) {
            int player_id = mySQL.getInteger("username", username, "id", configValue.TABLE_PLAYERS);
            int type_id = mySQL.getInteger("name", name, "id", configValue.TABLE_TYPES);
            if(mySQL.rowExists(new String[]{"player_id", "type_id"}, new Object[]{player_id, type_id}, configValue.TABLE_RECORDS)) {
                mySQL.set("time", time, new String[]{"player_id", "type_id"}, new Object[]{player_id, type_id},
                        configValue.TABLE_RECORDS);
            } else {
                mySQL.addRow(new String[]{"time", "player_id", "type_id"}, new Object[]{time, player_id, type_id},
                        configValue.TABLE_RECORDS);
            }
        }
    }

    public float getBestTime(String username, String name) throws SQLException {
        if(mySQL.rowExists("name", name, configValue.TABLE_TYPES)) {
            int player_id = mySQL.getInteger("username", username, "id", configValue.TABLE_PLAYERS);
            int type_id = mySQL.getInteger("name", name, "id", configValue.TABLE_TYPES);
            if(mySQL.rowExists(new String[]{"player_id", "type_id"}, new Object[]{player_id, type_id}, configValue.TABLE_RECORDS)) {
                return mySQL.getFloat(new String[]{"player_id", "type_id"}, new Object[]{player_id, type_id}, "time", configValue.TABLE_RECORDS);
            }
        }
        return Float.MAX_VALUE;
    }
}
