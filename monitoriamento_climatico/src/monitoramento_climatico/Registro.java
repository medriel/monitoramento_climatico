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

    @Override
    public String toString() {
        return ("--------------------------------------------------------------------------------------\n"
                + "\t \t \t \t Histórico de Momitoramento Climático \n"
                + "Umidade do ar: " + umidade + "%" + "\n"
                + "Temperatura: " + temperatura + "ºC" + "\n"
                + "Atualmente é " + ldr + "\n"
                + "A condição do ar indica que " + mq_2 + "\n"
                + "No momento " + chuva + "\n"
                + "O solo está " + higrometro + "\n"
                + "Data e hora do registro das condições climáticas: " + data_hora + "\n"
                + "--------------------------------------------------------------------------------------\n\n");
    }
}
