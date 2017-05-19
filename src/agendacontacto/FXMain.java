/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendacontacto;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author El Rubio
 */
public class FXMain extends Application {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void start(Stage primaryStage) throws IOException {
        StackPane rootMain = new StackPane();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ContactosView.fxml"));
        Pane rootContactosView = fxmlLoader.load();
        rootMain.getChildren().add(rootContactosView);

// Carga del EntityManager, etc ...
        Scene scene = new Scene(rootMain, 650, 400);
        emf = Persistence.createEntityManagerFactory("AgendaContactoPU");
        em = emf.createEntityManager();

        ContactosViewController contactosViewController = (ContactosViewController) fxmlLoader.getController();
        contactosViewController.setEntityManager(em);
        contactosViewController.cargarTodasPersonas();

        primaryStage.setTitle("Agenda Contactos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        em.close();
        emf.close();
        try {
            DriverManager.getConnection("jdbc:derby:BDAgendaContactos;shutdown=true");
        } catch (SQLException ex) {
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
