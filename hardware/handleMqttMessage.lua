handleMessageFunction = {  
    dev = function(data)
        if(data.protocol == 1) then
            -- send device info
            if mqtt_config.devId ~= nil then
                return
            end
            
            if file.exists("config.json") then
                local config = sjson.decode(file.getcontents("config.json"))
                payload = {
                    mac = wifi.sta.getmac(),
                    name = config.name,
                    protocol = 1
                }
                
                send(mqtt_config.topic.DEV, payload)
                print("should discover")
            else
                print("no config.json")
            end
        elseif(data.protocol == 2) then
            -- get generate dev
            mqtt_config.devId = data.devId
            print("generate devId:" .. mqtt_config.devId)
            saveMqttConfig()
            
            if file.exists("config.json") then
                local config = sjson.decode(file.getcontents("config.json"))
                config.devId = mqtt_config.devId
                config.mac = wifi.sta.getmac()

                config_states = config["states"]
                for i=1,#config_states do
                    config_states[i].state = states[i]
                end

                -- unknown bug no mac
                config.mac = wifi.sta.getmac()
                config.protocol = 2
                
                send(mqtt_config.topic.DISCOVER, config)
                canReport = true
            else
                print("no config.json")
            end
        elseif(data.protocol == 3) then
            -- dev remove
            print("device has been removed")
            mqtt_config.devId = nil
            saveMqttConfig()
            canReport = false
            -- zanshi buhuifule
        end
    end,  
    control = function(data)
        local data_states = data.states
        handleControl(data.id, data_states.id, data_states.state)
    end
}

function send(topic, msg, id)
    if (id ~= nil) then
         msg["id"] = id
    else
        if (mqtt_config.devId == nil) then
            msg["id"] = wifi.sta.getmac()
        else
            msg["id"] = mqtt_config.devId
        end
    end
    local text = sjson.encode(msg)
    print("send msg" .. text .. "--to:" .. topic)
    mqttClient:publish(topic, text, 1, 0)
end

function saveMqttConfig()
    if file.open("mqtt.config", "w") then
        file.write(sjson.encode(mqtt_config))
        file.flush()
        file.close()
    end
end
