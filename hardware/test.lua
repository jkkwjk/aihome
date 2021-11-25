t = sjson.decode('{"key":"value"}')
--for k,v in pairs(t) do print(k,v) end
print(sjson.encode(t))