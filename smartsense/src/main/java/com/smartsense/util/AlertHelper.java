package com.smartsense.util;

import com.smartsense.model.enums.Severity;
import com.smartsense.dto.alert.AlertDTO;
import java.time.LocalDateTime;

public class AlertHelper {

    private static final double TEMP_CRITICAL_HIGH = 40.0;
    private static final double TEMP_CRITICAL_LOW = -10.0;
    private static final double TEMP_HIGH_HIGH = 35.0;
    private static final double TEMP_HIGH_LOW = -5.0;
    private static final double TEMP_MEDIUM_HIGH = 30.0;
    private static final double TEMP_MEDIUM_LOW = 0.0;
    private static final double TEMP_LOW_HIGH = 25.0;
   // private static final double TEMP_NORMAL_HIGH = 25.0;
    private static final double TEMP_NORMAL_LOW = 20.0;

    // Constantes pour l'humidité
    private static final double HUM_CRITICAL_HIGH = 90.0;
    private static final double HUM_CRITICAL_LOW = 20.0;
    private static final double HUM_HIGH_HIGH = 80.0;
    private static final double HUM_HIGH_LOW = 30.0;
    private static final double HUM_MEDIUM_HIGH = 70.0;
    private static final double HUM_MEDIUM_LOW = 40.0;
    private static final double HUM_LOW_HIGH = 70.0;
    private static final double HUM_LOW_LOW = 45.0;
   // private static final double HUM_NORMAL_HIGH = 65.0;
  //  private static final double HUM_NORMAL_LOW = 45.0;

    public static AlertDTO evaluateTemperature(double temperature) {
        Severity severity;
        String message;

        if (temperature > TEMP_CRITICAL_HIGH || temperature < TEMP_CRITICAL_LOW) {
            severity = Severity.CRITICAL;
            message = "Risque immédiat pour les équipements";
        } else if (temperature > TEMP_HIGH_HIGH || temperature < TEMP_HIGH_LOW) {
            severity = Severity.HIGH;
            message = "Situation préoccupante nécessitant une action rapide";
        } else if (temperature > TEMP_MEDIUM_HIGH || temperature < TEMP_MEDIUM_LOW) {
            severity = Severity.MEDIUM;
            message = "Situation à surveiller";
        } else if (temperature > TEMP_LOW_HIGH || temperature < TEMP_NORMAL_LOW) {
            severity = Severity.LOW;
            message = "Légère déviation des valeurs optimales";
        } else {
            severity = Severity.NORMAL;
            message = "Température dans la plage optimale";
        }



        return AlertDTO.builder()
                .severity(severity)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static AlertDTO evaluateHumidity(double humidity) {
        Severity severity;
        String message;

        if (humidity > HUM_CRITICAL_HIGH || humidity < HUM_CRITICAL_LOW) {
            severity = Severity.CRITICAL;
            message = "Risque de dommages matériels";
        } else if (humidity > HUM_HIGH_HIGH || humidity < HUM_HIGH_LOW) {
            severity = Severity.HIGH;
            message = "Conditions défavorables";
        } else if (humidity > HUM_MEDIUM_HIGH || humidity < HUM_MEDIUM_LOW) {
            severity = Severity.MEDIUM;
            message = "Situation à surveiller";
        } else if (humidity > HUM_LOW_HIGH || humidity < HUM_LOW_LOW) {
            severity = Severity.LOW;
            message = "Légère déviation";
        } else {
            severity = Severity.NORMAL;
            message = "Humidité dans la plage optimale";
        }

        return AlertDTO.builder()
                .severity(severity)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
