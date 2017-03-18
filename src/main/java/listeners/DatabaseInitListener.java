package listeners;

import controllers.RegisterUser;
import dao.DaoFactory;
import dao.h2.H2DaoFactory;
import model.Countries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.HashGenerator;

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
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by iMac on 05/03/17.
 */
// INSERT INTO Users (login, password, first_name, last_name, country) VALUES ('admin', 'admin', 'Phil', 'Kuzmin', 'RUSSIA');
// INSERT INTO Users_Instruments (user_id, instrument_id) VALUES (1 , 1);
@WebListener
public class DatabaseInitListener implements ServletContextListener {

    @Resource(name = "jdbc/mtdb")
    private DataSource dataSource;

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Database initialization started...");
        Pattern pattern = Pattern.compile("^\\d+\\.sql$");
        Path sqlPath = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/classes/sql"));
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
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

            HashGenerator hashGenerator = (HashGenerator) sce.getServletContext().getAttribute("hashGenerator");
            Random rand = new Random();

            for (int user = 6; user < 100; user++) {
                statement.addBatch("INSERT INTO Users (login, password, first_name, last_name, country) " +
                        "VALUES ('User" + user + "', " +
                        "'" + hashGenerator.getHash("pass") + "', " +
                        "'Auto', " +
                        "'Generated', " +
                        "'" + Countries.values()[rand.nextInt(4)].toString() + "');");
                statement.addBatch("INSERT INTO Users_Instruments (user_id, instrument_id) VALUES (" + user + " , " + (rand.nextInt(5) + 1) + ");");
                statement.addBatch("INSERT INTO Users_Instruments (user_id, instrument_id) VALUES (" + user  + " , " + (rand.nextInt(5) + 6) + ");");
                for (int msg = 0; msg < rand.nextInt((5)+1); msg++) {
                    statement.addBatch("INSERT INTO Messages (user_id, message_date, message_text) VALUES " +
                            "(" + user + ", '" + Timestamp.valueOf(LocalDateTime.now()).toString() + "', 'Auto generated tweet about music #" + msg + "');");
                    Thread.sleep(1);
                }
            }
            statement.executeBatch();
        } catch (SQLException e) {
            log.warn("SQL script problems: " + e.getMessage());
        } catch (IOException e) {
            log.warn("SQL file error:" + e.getMessage());
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }

        DaoFactory daoFactory = new H2DaoFactory(dataSource);
        sce.getServletContext().setAttribute("daoFactory", daoFactory);
    }
}
