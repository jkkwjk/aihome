mqttClient = nil -- udp used
local isFirstConn = false
mqtt_config = nil -- handleMqttMessage used

dofile("handleMqttMessage.lua")
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
        mqttClient = mqtt.Client("c_" .. wifi.sta.getmac(), 120)
        mqttClient:lwt("lwt", "conn down", 1)
        mqttClient:on("offline", function(client)
            print("offline")
            mqtt_init()
        end)
        
        mqttClient:on("message", function(client, topic, data)
            local fSwitch = handleMessageFunction[topic]
            print(data)
--            local reposion = sjson.decode(data)
            if fSwitch then --key exists  
                local result = fSwitch(reposion) --do func  
            else --key not found  
                print("not support topic: " .. topic)
            end
        end)
        
        mqttClient:connect(mqtt_config.address, mqtt_config.port, false, function(client)
            connectSuccess(mqtt_config)
        end, handle_mqtt_error)
    end
end

function handle_mqtt_error(client, reason)
  tmr.create():alarm(10 * 1000, tmr.ALARM_SINGLE, mqtt_init)
end

function connectSuccess()
    print("connect")
    if(isFirstConn) then
        payload = {
            mac = wifi.sta.getmac()
        }
        -- get DevId
        mqttClient:subscribe(mqtt_config.topic.DEV,1)
        send(mqtt_config.topic.DEV, payload)
    else
--        reconnect
    end
    
end



function send(topic, msg)
    if (mqtt_config.devId == nil) then
        msg["id"] = wifi.sta.getmac() .. tmr.now()
    else
        msg["id"] = mqtt_config.devId.. tmr.now()
    end
    mqttClient:publish(topic, sjson.encode(msg), 1, 0)
end
--mqtt_init()
--mqtt_first_init("{\"mqttAddress\":\"192.168.0.109\",\"mqttPort\":1883,\"topic\":{\"discover\": \"discover\",\"online\": \"online\"}}")

