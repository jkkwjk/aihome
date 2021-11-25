local udptimer = tmr.create()

udpSocket = net.createUDPSocket()
udpSocket:listen(1234)
local lastData = ""
udpSocket:on("receive", function(s, data, port, ip)
    if (data == lastData) then
        dofile("mqtt.lua")
        mqtt_first_init(data)
    end
    lastData = data
    
end)


udptimer:alarm(5000, tmr.ALARM_AUTO, function (t)
    if mqttClient ~= nil then
        udpSocket:close()
        t:unregister()
    else
        udpSocket:send(23333, "255.255.255.255", "aihome-connect")
    end
end)
