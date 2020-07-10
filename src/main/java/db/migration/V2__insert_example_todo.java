package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.Connection;

public class V2__insert_example_todo extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        new JdbcTemplate( new SingleConnectionDataSource(context.getConnection(), true))
                .execute("insert into TASKS (description, done) values ('Task from migration H2', true)");
    }
}
