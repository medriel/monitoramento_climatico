package monitoramento_climatico;

import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;

public class FXMLDocumentController implements Initializable {

    @FXML
    private ComboBox cbPortas;

    @FXML
    private Button btnConectar;

    @FXML
    private Button btnDesconectar;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;

    @FXML
    private Label lbl4;

    @FXML
    private Label lbl5;

    @FXML
    private Label lbl6;

    @FXML
    private ListView lstRegistros;

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

    List<String> result = new ArrayList<String>();

    @FXML
    private void btnConectarAction() throws SQLException {

        lbl1.setText("teste");
        
        porta = SerialPort.getCommPort(cbPortas.getSelectionModel().getSelectedItem().toString());
        porta.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        porta.setBaudRate(2000000);
        InputStream in = porta.getInputStream();

        thread = new Thread() {

            public void run() {

                int availableBytes = 0;
                System.out.println(availableBytes);
                do {
                    result.removeAll(result);
                    try {
//                        System.out.println("Thread");
                        availableBytes = porta.bytesAvailable();
                        System.out.println(availableBytes);
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (availableBytes > -1);
            }
        };

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() {
                lbl1.setText(result.get(0));
                return null;
            }

            @Override
            protected void succeeded() {
                lbl2.setText(result.get(0));
            }
        };

        Thread threadLabels = new Thread(task);
        threadLabels.setDaemon(true);
        threadLabels.start();

        porta.openPort();
        cbPortas.setDisable(true);
        thread.start();
        btnConectar.setDisable(true);
    }

    public void printarResultado() throws Exception {
//        for (int i = 0; i < result.size(); i++) {
////            if (i == 0) {
////                lbl1.setText("1");
////            }
////            if (i == 1) {
////                lbl2.setText("1");
////            }
////            if (i == 2) {
////                lbl3.setText("1");
////            }
////            if (i == 3) {
////                lbl4.setText("1");
////            }
////            if (i == 4) {
////                lbl5.setText("1");
////            }
////            if (i == 5) {
////                lbl6.setText("1");
////            }
//            System.out.println(result.get(i));
//        }
    }

    @FXML
    public void btnDesconectarAction() {

        thread.interrupt();
        porta.closePort();
        cbPortas.setDisable(false);

        btnConectar.setDisable(false);
    }
;
}
