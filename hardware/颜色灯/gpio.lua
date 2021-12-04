function saveStates()
    if file.open("state.save", "w") then
        file.write(sjson.encode(states))
        file.flush()
        file.close()
    end
end

-- init
gpio_green = 1
gpio_red = 2
gpio_yellow = 5
gpio.mode(gpio_green, gpio.OUTPUT)
gpio.mode(gpio_red, gpio.OUTPUT)
gpio.mode(gpio_yellow, gpio.OUTPUT)

states = nil
if file.exists("state.save") then
    states = sjson.decode(file.getcontents("state.save"))
else
    -- first load
    states = {
        false, "red"
    }
    saveStates()
end

function turnOff()
    gpio.write(gpio_red, gpio.LOW)
    gpio.write(gpio_green, gpio.LOW)
    gpio.write(gpio_yellow, gpio.LOW)
end

function turnOn()
    turnOff()
    if states[2] == "red" then
        gpio.write(gpio_red, gpio.HIGH)
    elseif states[2] == "green" then
        gpio.write(gpio_green, gpio.HIGH)
    elseif states[2] == "yellow" then
        gpio.write(gpio_yellow, gpio.HIGH)
    else
        print("not support color type " .. states[2])
    end
end

function controlByState()
    if states[1] then
        turnOn()
    else
        turnOff()
    end
end

controlByState()
-- do sth when control cmd recived
function handleControl(messageId, id, value)
    states[id + 1] = value
    saveStates()
    
    controlByState()
    
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
