mqttClient = nil
isFirstConn = false
function mqtt_first_init(mqtt_config)
    isFirstConn = true
    if file.open("mqtt.config", "w") then
        file.write(mqtt_config)
        file.flush()
        file.close()
    end
    mqtt_init()
end

function mqtt_init()
    if file.open("mqtt.config", "r") then
        mqtt_config = sjson.decode(file.read())
        file.close()
        mqttClient = mqtt.Client("c_" .. tmr.now(), 120)
        mqttClient:lwt("lwt", "conn down", 1)
        mqttClient:on("offline", function(client)
            print("offline")
            mqtt_init()
        end)
        
        mqttClient:connect(mqtt_config.address, mqtt_config.port, false, function(client)
            connectSuccess(mqtt_config)
        end, handle_mqtt_error)
    end
end

function handle_mqtt_error(client, reason)
  tmr.create():alarm(10 * 1000, tmr.ALARM_SINGLE, mqtt_init)
end

function connectSuccess(mqtt_config)
    print("connect")
    if(isFirstConn) then
--        discover
        mqttClient:publish(mqtt_config.topic.discover, "hello", 1, 0, function(client) 
            print("send")
        end)
    else
--        reconnect
    end
    
end


--mqtt_first_init("{\"mqttAddress\":\"192.168.0.109\",\"mqttPort\":1883,\"topic\":{\"discover\": \"discover\",\"online\": \"online\"}}")

