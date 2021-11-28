function saveStates()
    if file.open("state.save", "w") then
        file.write(sjson.encode(states))
        file.flush()
        file.close()
    end
end

-- init
gpio_2 = 4
gpio.mode(gpio_2, gpio.OUTPUT)
states = nil
if file.exists("state.save") then
    states = sjson.decode(file.getcontents("state.save"))
else
    -- first load
    states = {
        false
    }
    saveStates()
end


function turnOn()
    gpio.write(gpio_2, gpio.HIGH)
end

function turnOff()
    gpio.write(gpio_2, gpio.LOW)
end

if states[1] then
    turnOn()
else
    turnOff()
end


-- do sth when control cmd recived
function handleControl(messageId, id, value)
    if value then
        turnOn()
    else
        turnOff()
    end
    states[id + 1] = value
    saveStates()
    
    payload = {
        id = messageId,
        devId = mqtt_config.devId,
        states = {
            {
                id = id,
                state = value
            }
        }
    }
    send(mqtt_config.topic.REPORT, payload)
--    gpio.mode(gpio_2, gpio.HIGH)
end

-- return value when query cmd recived
function reportAllState()
    payload = {
        devId = mqtt_config.devId,
        states = {}
    }
    for i=1,#states do
        local tmp = {
            id = i - 1,
            state = states[i]
        }
        payload.states[i] = tmp
    end
    print(sjson.encode(payload))
    return payload
end
