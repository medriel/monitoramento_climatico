package monitoriamento_climatico;

import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class FXMLDocumentController implements Initializable {

    @FXML
    private ComboBox cbPortas;

    @FXML
    private Button btnConectar;

    @FXML
    private Button btnDesconectar;

    private SerialPort porta;

    Thread thread;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarPortas();
    }

    private void carregarPortas() {
        SerialPort[] portNames = SerialPort.getCommPorts();

        for (SerialPort portName : portNames) {
            cbPortas.getItems().add(portName.getSystemPortName());
        }
    }

    @FXML
    private void btnConectarAction() throws SQLException {

        porta = SerialPort.getCommPort(cbPortas.getSelectionModel().getSelectedItem().toString());
        porta.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        porta.setBaudRate(2000000);
        InputStream in = porta.getInputStream();

        thread = new Thread() {
            List<String> result = new ArrayList<String>();

            public void run() {
                int availableBytes = 0;
                System.out.println(availableBytes);
                do {
                    result.removeAll(result);
                    try {
//                        System.out.println("Thread");
                        availableBytes = porta.bytesAvailable();
//                        System.out.println(availableBytes);
                        if (availableBytes > 0) {
                            byte[] buffer = new byte[1024];
                            int bytesRead = porta.readBytes(buffer, Math.min(buffer.length, porta.bytesAvailable()));
                            String response = new String(buffer, 0, bytesRead);
//                            System.out.println(response);
                            result.add(response);
                        }
                        Thread.sleep(2000);
                        in.close();
                        System.out.println(result);
                        System.out.println("Resultado: ");
                        for (int i = 0; i < result.size(); i++) {
                            if(i == 0){

                            }
                            System.out.println(result.get(i));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (availableBytes > -1);
            }
        };

        porta.openPort();
        cbPortas.setDisable(true);
        thread.start();
        btnConectar.setDisable(true);
    }

    @FXML
    public void btnDesconectarAction() {

        thread.interrupt();
        porta.closePort();
        cbPortas.setDisable(false);

        btnConectar.setDisable(false);
    }
}
