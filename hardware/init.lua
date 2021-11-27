function connNotSucess(s, t)
    print(s)
    if file.open("lasterror", "w") then
        file.writeline(s)
        file.close()
    end
    file.remove("wifi.config")
    wifi.sta.disconnect()
    dofile("firstconn.lua")
    t:unregister()
end

if file.exists("wifi.config") then
    if file.open("wifi.config", "r") then
        local ssid = string.gsub(file.readline(), '\n', '')
        local pwd = string.gsub(file.readline(), '\n', '')
        station_cfg = {}
        station_cfg.ssid = ssid
        station_cfg.pwd = pwd
        file.close()
        
        wifi.setmode(wifi.STATION)
        wifi.sta.config(station_cfg)
        local startConnectTime = tmr.now()
        wifi.sta.connect()
        
        local wifitimer = tmr.create()
        wifitimer:alarm(5000, tmr.ALARM_AUTO, function (t)           
            local status = wifi.sta.status();
            if (status == wifi.STA_WRONGPWD) then
                connNotSucess("pwd wrong", t)
            elseif (status == wifi.STA_APNOTFOUND) then
                connNotSucess("wifi not found", t)
            elseif (status == wifi.STA_GOTIP) then
                print(wifi.sta.getip())
                t:unregister()
                if (file.exists("mqtt.config")) then
                    dofile("mqtt.lua")
                    mqtt_init()
                else
                    dofile("udp_process.lua")
                end
                
            end
        end)
        
    end
else
    dofile("firstconn.lua")
end
