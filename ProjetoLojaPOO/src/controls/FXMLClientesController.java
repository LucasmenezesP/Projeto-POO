package controls;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class FXMLClientesController implements Serializable {

    @FXML
    private ListView<?> lstClientesAtivos;

    @FXML
    private ListView<?> lstClientesInativos;

    @FXML
    void btnCadastrar(ActionEvent event) {
    	Main.changeScreen(3);
    }
    
    @FXML
    void btnLivros(ActionEvent event) {
    	Main.changeScreen(1);
    }
    
}
