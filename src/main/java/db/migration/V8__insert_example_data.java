package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V8__insert_example_data extends BaseJavaMigration {
    @Override
    public void migrate(Context context) {
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("insert into PROJECTS(DESCRIPTION) values ( 'ToDo Application' );" +
                        "insert into PROJECT_STEPS(description, days_to_deadline, project_id) values ( 'tests',5,1 );" +
                        "insert into PROJECT_STEPS(description, days_to_deadline, project_id) values ( 'task service',10,1 );" +
                        "insert into PROJECT_STEPS(description, days_to_deadline, project_id) values ( 'task controller',15,1 );" +
                        "insert into TASK_GROUPS(description, done, project_id) values ( 'Major tasks',false,1 );" +
                        "insert into tasks (description, done,TASK_GROUP_ID) values ('Add minus button for tasks', false,1 );" +
                        "insert into tasks (description, done,TASK_GROUP_ID) values ('Add minus button for groups task', false,1);");
    }
}
