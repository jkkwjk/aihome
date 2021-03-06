handleMessageFunction = {  
    dev = function(data)
        if(#data.id ~= 1) then
            mqtt_config.devId = data.devId
            print(mqtt_config.devId)
            saveMqttConfig()
            
            mqttClient:unsubscribe(mqtt_config.topic.DEV,1)
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
                
                send(mqtt_config.topic.DISCOVER, config)
                canReport = true
            else
                print("no config.json")
            end
        else
            if(data.id == '1') then
                if file.exists("config.json") then
                    local config = sjson.decode(file.getcontents("config.json"))
                    payload = {
                        mac = wifi.sta.getmac(),
                        name = config.name
                    }
                    
                    send(mqtt_config.topic.DEV, payload)
                    print("should discover")
                else
                    print("no config.json")
                end
            end
            
        end
    end,  
    control = function(data)
        local data_states = data.states
        handleControl(data.id, data_states.id, data_states.state)
    end,
    report = function(data, msg)
        if (msg == "no devId") then
            print("no devId reset")
            canReport = false
            mqtt_config.devId = nil
            saveMqttConfig()
            mqttClient:subscribe(mqtt_config.topic.DEV,1)
        end
    end
}

function send(topic, msg, id)
    if (id ~= nil) then
         msg["id"] = id
    else
        if (mqtt_config.devId == nil) then
            msg["id"] = wifi.sta.getmac() .. tmr.now()
        else
            msg["id"] = mqtt_config.devId.. tmr.now()
        end
    end
    
    mqttClient:publish(topic, sjson.encode(msg), 1, 0)
end

function saveMqttConfig()
    if file.open("mqtt.config", "w") then
        file.write(sjson.encode(mqtt_config))
        file.flush()
        file.close()
    end
end
