mqttClient = nil -- udp used
mqtt_config = nil -- handleMqttMessage used

dofile("handleMqttMessage.lua")
function mqtt_first_init(mqtt_config)
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
            print("new msg" .. data)
            local fSwitch = handleMessageFunction[topic]
            local reposion = sjson.decode(data)
            if fSwitch then --key exists 
                if reposion.code ~= nil then
                    if (reposion.code ~= 0) then
                        print("error from server:" .. reposion.msg)
                    elseif canProcess(reposion.data.id) then
                        local result = fSwitch(reposion.data) --do func 
                    end
                end
                 
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
    if(mqtt_config.devId == nil) then
        -- get DevId
        mqttClient:subscribe(mqtt_config.topic.DEV,1)
    else
        -- reconnect
        send(mqtt_config.topic.REPORT, reportAllState())
    end
    mqttClient:subscribe(mqtt_config.topic.CONTROL,1)
    
end

function canProcess(id)
    local len = #id;
    if (string.find(id, ":") ~= nil) then
        local mac = string.sub(id, 1, 17)
        return mac == wifi.sta.getmac()
    elseif (len >= 8) then
        local msgDevId = string.sub(id, 1, 8)
        return msgDevId == mqtt_config.devId
    else
        return len == 1
    end
end

