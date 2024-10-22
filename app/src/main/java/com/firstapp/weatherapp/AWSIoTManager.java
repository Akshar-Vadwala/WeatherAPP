package com.firstapp.weatherapp;

import android.util.Log;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;
import com.amazonaws.regions.Regions;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class AWSIoTManager {
    private static final String TAG = "AWSIoTManager";
    private static AWSIoTManager instance;
    private final AWSIotMqttManager mqttManager;
    private boolean isConnected = false;
    private final CognitoCachingCredentialsProvider credentialsProvider;

    private static final String IOT_ENDPOINT = "your AWS_IoT endpoint";
    private static final String CLIENT_ID = "AndroidClient-" + UUID.randomUUID().toString();
    private static final String COGNITO_POOL_ID = "your cognito pool id";
    private static final Regions MY_REGION = Regions.AP_SOUTH_1;

    private AWSIoTManager(Context context) {
        mqttManager = new AWSIotMqttManager(CLIENT_ID, IOT_ENDPOINT);
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context.getApplicationContext(),
                COGNITO_POOL_ID,
                MY_REGION
        );
        Log.d(TAG, "AWSIoTManager initialized");
    }

    public static synchronized AWSIoTManager getInstance(Context context) {
        if (instance == null) {
            instance = new AWSIoTManager(context.getApplicationContext());
        }
        return instance;
    }

    public interface ConnectionCallback {
        void onConnectionStatus(boolean isConnected);
    }

    public synchronized void connect(final ConnectionCallback callback) {
        if (isConnected) {
            callback.onConnectionStatus(true);
            return;
        }

        Log.d(TAG, "Connecting to AWS IoT...");

        try {
            mqttManager.connect(credentialsProvider, (status, throwable) -> {
                isConnected = (status == AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connected);
                if (isConnected) {
                    Log.d(TAG, "Connected to AWS IoT");
                } else {
                    Log.e(TAG, "Connection error: " + throwable);
                }
                callback.onConnectionStatus(isConnected);
            });
        } catch (Exception e) {
            Log.e(TAG, "Error initiating connection", e);
            callback.onConnectionStatus(false);
        }
    }

    public interface MessageCallback {
        void onMessageReceived(JSONObject data);
    }

    public void subscribeToTopic(String topic, final MessageCallback callback) {
        if (!isConnected) {
            Log.e(TAG, "Cannot subscribe: not connected to AWS IoT");
            return;
        }

        try {
            mqttManager.subscribeToTopic(topic, AWSIotMqttQos.QOS0, (topic1, data) -> {
                String message = new String(data, StandardCharsets.UTF_8);
                try {
                    JSONObject jsonData = new JSONObject(message);
                    callback.onMessageReceived(jsonData);
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON from message", e);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error subscribing to topic", e);
        }
    }
}