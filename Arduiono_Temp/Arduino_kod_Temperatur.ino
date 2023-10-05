#include <OneWire.h>
#include <DallasTemperature.h>

#define ONE_WIRE_BUS 7
OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);

void setup() {
  Serial.begin(9600); 
  sensors.begin();
  sensors.setResolution(12);

  for (int pinNumber = 2; pinNumber < 5; pinNumber++) {
    pinMode(pinNumber, OUTPUT);
    digitalWrite(pinNumber, LOW);
  }

  delay(5000); // Extended delay to allow sensor initialization
}

void loop() {
  sensors.requestTemperatures();
 
  float temp = sensors.getTempCByIndex(0);
  if(temp == DEVICE_DISCONNECTED_C) {
    Serial.println("Error: Could not read temperature data");
    return;
  }

  Serial.println(temp);
  Serial.flush();

if(temp >= 15 && temp <= 25){  // Stabil
    digitalWrite(2,LOW);
    digitalWrite(3,HIGH);
    digitalWrite(4,LOW);
} else if(temp > 25 && temp < 30){  // Intermediate (between Stabil and Hög)
    // Define your LED action for this range. For demonstration, I'm turning all LEDs off. Adjust as needed.
    digitalWrite(2,LOW);
    digitalWrite(3,HIGH);
    digitalWrite(4,LOW);
} else if(temp >= 30 && temp <= 40){  // Hög
    digitalWrite(2,LOW);
    digitalWrite(3,LOW);
    digitalWrite(4,HIGH);
} else if(temp <= 14){  // Låg
    digitalWrite(2,HIGH);
    digitalWrite(3,LOW);
    digitalWrite(4,LOW);
}

  
  delay(6000);
}
