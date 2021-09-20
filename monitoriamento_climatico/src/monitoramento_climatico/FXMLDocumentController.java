package monitoramento_climatico;

import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    String result;

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
                do {
                    result = "";
                    try {
                        availableBytes = porta.bytesAvailable();
                        if (availableBytes > 0) {
                            byte[] buffer = new byte[1024];
                            int bytesRead = porta.readBytes(buffer, Math.min(buffer.length, porta.bytesAvailable()));
                            String response = new String(buffer, 0, bytesRead);
                            result = response;
                        }
                        Thread.sleep(60000); // 1 min
                        in.close();
                        if (result.length() > 20 && result.length() < 50) { // eliminando possiveis erros q geral valores aleatorios da leitura da porta
                            System.out.println("teste ");
                            System.out.println(result);
                            System.out.println("Resultado: ");
                            String array[] = new String[6];
                            array = result.split(";"); // separando a string lida da porta serial atravez do caracter ; adicionado no arduino
                            String umidade = array[0];
                            String temperatura = array[1];
                            String ldr = array[2];
                            String mq_2 = array[3];
                            String chuva = array[4];
                            String higrometro = array[5];
                            gravar(umidade, temperatura, ldr, mq_2, chuva, higrometro);
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

    protected Connection getConexao() throws SQLException {
        String url = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "monitoramentoclimatico";
        Connection conn = DriverManager.getConnection(url, "postgres", "postgres");
        return conn;
    }

    protected PreparedStatement getPreparedStatement(boolean chavePrimaria, String sql) throws Exception {
        PreparedStatement ps = null;
        if (chavePrimaria) {
            ps = getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            ps = getConexao().prepareStatement(sql);
        }

        return ps;
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void gravar(String umidade, String temperatura, String ldr, String mq_2, String chuva, String higrometro) throws Exception {
        String sql = "insert into registro(umidade, temperatura, ldr, mq_2, chuva, higrometro, data_hora) values (?,?,?,?,?,?,?)";
        PreparedStatement ps = getPreparedStatement(false, sql);

        ps.setString(1, umidade);
        ps.setString(2, temperatura);
        ps.setString(3, ldr);
        ps.setString(4, mq_2);
        ps.setString(5, chuva);
        ps.setString(6, higrometro);
        ps.setString(7, getDateTime());
        ps.executeUpdate();
    }

}
