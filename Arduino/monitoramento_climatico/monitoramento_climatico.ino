#include "DHT.h"

// DHT 11 ----------------
#define DHTPIN A0 
#define DHTTYPE DHT11
const int pinoDHT11 = A0;
DHT dht(DHTPIN, DHTTYPE);
// -----------------------

//LDR -------------------
const int pinoLDR = A1;
// ----------------------

// MQ-2 ----------------
#define MQ_analog A2
// ---------------------

//Sensor de chuva ------
const int pinoSensorChuva = A3;
// ---------------------

// Higrômetro ---------
const int pinoSensorHigrometro = A4;
// --------------------

void setup() {
  Serial.begin(2000000);
  dht.begin(); //DHT 11
  pinMode(pinoLDR, INPUT); // LDR
  pinMode(MQ_analog, INPUT); // MQ-2
  pinMode(pinoSensorChuva, INPUT); //Sensor de chuva
  pinMode(pinoSensorHigrometro, INPUT); //Higrômetro
  delay(2000);
}

void sensor_DHT11(){
//  Serial.print("Umidade: ");
  Serial.print((String)dht.readHumidity()+";");  
//  Serial.print("%");
//  Serial.print(" / Temperatura: ");
  Serial.print((String)(dht.readTemperature())+";"); 
//  Serial.println("*C");
}

void sensor_LDR(){
  int valor_analogico = analogRead(pinoLDR);
  Serial.print((String)valor_analogico+";");
//  if(valor_analogico > 350){
//    Serial.println("Noite");
//  }  
//  else{ 
//    Serial.println("Dia");
//  }  
}

void sensor_MQ_2(){
  int valor_analogico = analogRead(MQ_analog);
  Serial.print((String)valor_analogico+";");
//  if(valor_analogico >100){
//    Serial.println("Indicio de incêndio");
//  }else{
//    Serial.println("sem indicio de incêndio");
//  }
}

void sensor_de_chuva(){
  int valor_analogico = analogRead(pinoSensorChuva);
  Serial.print((String)valor_analogico+";");
//  if(valor_analogico<900 && valor_analogico>300){
//    Serial.println("Chuva leve");
//  }else if(valor_analogico<300){
//    Serial.println("Chuva intensa");
//  }else{
//    Serial.println("Não chove no momento");
//  }
}

void sensor_higrometro(){
  int valor_analogico = analogRead(pinoSensorHigrometro);

  Serial.print( (String)valor_analogico+";");
//  
//  if(valor_analogico > 0 && valor_analogico < 400){
//    Serial.println("umido");
//  }
//  if (valor_analogico > 400 && valor_analogico < 800){
//    Serial.println("com umidade moderada");
//  }
//  if (valor_analogico > 800 && valor_analogico < 1024){
//    Serial.println("seco");
//  }
}

void loop() {
//    Serial.println("----------- Sensor DHT 11 -------------");
    sensor_DHT11();
//    Serial.println("------------- Sensor LDR --------------");
    sensor_LDR();
//    Serial.println("------------ Sensor MQ-2 --------------");
    sensor_MQ_2();
//    Serial.println("---------- Sensor de chuva ------------");
    sensor_de_chuva();
//    Serial.println("---- Sensor de Humidade do solo ------");
    sensor_higrometro();
//    Serial.println("\n\n\n\n");
    delay(1000); // mudar para 1 min
}
