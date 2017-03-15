package listeners;

import dao.DaoFactory;
import dao.h2.H2DaoFactory;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by iMac on 05/03/17.
 */

//INSERT INTO Messages (user_id, message_date, message_text) VALUES (1, '2014-04-07', 'Admin first tweet');

@WebListener
public class DatabaseInitListener implements ServletContextListener {

    @Resource(name = "jdbc/mtdb")
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Database initialization...");
        Pattern pattern = Pattern.compile("^\\d+\\.sql$");
        Path sqlPath = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/classes/sql"));
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            DirectoryStream<Path> paths = Files.newDirectoryStream(sqlPath);
            for (Path filePath : paths) {
                if (pattern.matcher(filePath.toFile().getName()).find()) {
                    statement.addBatch(
                            Files.lines(filePath)
                                    .collect(Collectors.joining())
                    );
                }
            }

            // Autogenerating tweets
            for (int i = 0; i < 80; i++) {
                statement.addBatch("INSERT INTO Messages (user_id, message_date, message_text) VALUES (1, '" + Timestamp.valueOf(LocalDateTime.now()).toString() + "', '" + "Auto generated tweet #" + i + "');");
                Thread.sleep(10);
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("SQL file error:" + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DaoFactory daoFactory = new H2DaoFactory(dataSource);
        sce.getServletContext().setAttribute("daoFactory", daoFactory);
    }
}
