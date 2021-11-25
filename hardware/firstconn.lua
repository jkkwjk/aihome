wifi.setmode(wifi.SOFTAP)
cfg = {}
cfg.ssid = "aihome-connect"
wifi.ap.config(cfg)
print(wifi.ap.getip())

dofile('httpServer.lua')
httpServer:listen(80)

httpServer:use('/submit', function(req, res)
    local ssid = req.query.ssid
    local pwd = req.query.pwd;
    if ssid ~= '' and pwd ~= '' then
        if file.open("wifi.config", "w") then
            file.writeline(ssid)
            file.writeline(pwd)
            file.close()
            res:send('0')
            
            restartTimer = tmr.create()
            restartTimer:alarm(5000, tmr.ALARM_SINGLE, function()
                node.restart()
            end)
        else
            res:send('1,file append error')
        end
    else
        res:send('1,ssid or pwd cant empty')
    end
end)

httpServer:use('/log', function(req, res)
    if file.exists("lasterror") and file.open("lasterror", "r") then
        local log = file.readline()
        file.close()
        file.remove("lasterror")
        res:send(log)
    else
        res:send('')
    end
end)
