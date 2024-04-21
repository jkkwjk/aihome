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

mq_status = true;
function mqtt_init()
    if file.open("mqtt.config", "r") then
        mqtt_config = sjson.decode(file.read())
        file.close()
        if mqttClient == nil then
            print("init mqtt")
            mqttClient = mqtt.Client("c_" .. wifi.sta.getmac(), 120)
            mqttClient:lwt("lwt", "conn down", 1)
            mqttClient:on("offline", function(client)
                print("offline")
                mq_status = false
            end)
            
            mqttClient:on("message", function(client, topic, data)
                local fSwitch = handleMessageFunction[topic]
                local reposion = sjson.decode(data)
                if fSwitch then --key exists 
                    if reposion.code ~= nil then
                        print("receive msg" .. data .. "--from:" .. topic)
                        if canProcess(reposion.data.id) then
                            local result = fSwitch(reposion.data, reposion.msg) --do func 
                        end
                    end
                end
            end)
        end

        print("mqtt start connecting..")
        mqttClient:connect(mqtt_config.address, mqtt_config.port, false, function(client)
            connectSuccess(mqtt_config)
        end)
        
    end
end

-- mqtt reconnect
tmr.create():alarm(10 * 1000, tmr.ALARM_AUTO, function (t)
    if (mqttClient ~= nil and mq_status == false) then
          print("mqtt_error, retry.....")
          if(status ~= wifi.STA_GOTIP) then
            print("wifi not connect, first retry connect wifi")
            wifi.sta.connect()
          end
          mqtt_init()
    end
end)

function connectSuccess()
    print("connect")
    mq_status = true
    
    mqttClient:subscribe(mqtt_config.topic.CONTROL,1)
    mqttClient:subscribe(mqtt_config.topic.DEV,1)
    
    if(mqtt_config.devId ~= nil) then
        -- reconnect
        send(mqtt_config.topic.REPORT, reportAllState())
        canReport = true -- gpio.lua
    end
    
    
end

function canProcess(id)
    local len = #id
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

