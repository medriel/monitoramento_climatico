package monitoramento_climatico;

public class Registro {

    private String umidade;
    private String temperatura;
    private String ldr;
    private String mq_2;
    private String chuva;
    private String higrometro;
    private String data_hora;

    public String getUmidade() {
        return umidade;
    }

    public void setUmidade(String umidade) {
        this.umidade = umidade;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getLdr() {
        return ldr;
    }

    public void setLdr(String ldr) {
        this.ldr = ldr;
    }

    public String getMq_2() {
        return mq_2;
    }

    public void setMq_2(String mq_2) {
        this.mq_2 = mq_2;
    }

    public String getChuva() {
        return chuva;
    }

    public void setChuva(String chuva) {
        this.chuva = chuva;
    }

    public String getHigrometro() {
        return higrometro;
    }

    public void setHigrometro(String higrometro) {
        this.higrometro = higrometro;
    }

    public String getData_hora() {
        return data_hora;
    }

    public void setData_hora(String data_hora) {
        this.data_hora = data_hora;
    }
}
