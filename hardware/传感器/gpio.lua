function saveStates()
    if file.open("state.save", "w") then
        file.write(sjson.encode(states))
        file.flush()
        file.close()
    end
end

-- init
gpio_sun = 1
gpio_door = 2
states = {}
canReport = false
function deTwitteInt(pin, time, callback)
    gpio.mode(pin, gpio.INT)
    local t = tmr.create()
    local lastState = gpio.read(pin)
    callback(lastState)
    gpio.trig(pin, "both" , function(level)
        t:stop()
        t:alarm(time, tmr.ALARM_SINGLE, function()
            local nowState = gpio.read(pin)
            if (nowState ~= lastState) then
                lastState = nowState
                callback(nowState)
            end
        end)
    end)
end
deTwitteInt(gpio_sun, 1000, function(ret)
    if (ret == 0) then
        states[1] = true
    else
        states[1] = false
    end
    if (canReport) then
        payload = {
            devId = mqtt_config.devId,
            states = {
                {
                    id = 0,
                    state = states[1]
                }
            }
        }
        send(mqtt_config.topic.REPORT, payload)
    end
end)
deTwitteInt(gpio_door, 1000, function(ret)
    if (ret == 0) then
        states[2] = false
    else
        states[2] = true
    end
    if (canReport) then
        payload = {
            devId = mqtt_config.devId,
            states = {
                {
                    id = 1,
                    state = states[2]
                }
            }
        }
        send(mqtt_config.topic.REPORT, payload)
    end
end)

-- do sth when control cmd recived
function handleControl(messageId, id, value)
    print("not support control")
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
