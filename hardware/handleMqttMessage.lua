handleMessageFunction = {  
    dev = function(data)  
--        if(canProcess(data.id)) then
--            
--        end
    end,  
    case2 = function()  
        --case 2  
    end
}  

function canProcess(id)
    local len = #id;
    if (len >= 17) then
        mac = string.sub(id, 1, 17)
        return mac == wifi.sta.getmac()
    else
    end
end
