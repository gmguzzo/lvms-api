import requests
import json
from time import sleep

lvms_api_url = 'http://localhost:8081/v2/chords/'

# Using readlines() 
file1 = open('/home/gmguzzo/Documents/projetos/louvemos/db_conversion/port.txt', 'r') 
payload = file1.read()

error_dump = open('/home/gmguzzo/Documents/projetos/louvemos/db_conversion/error_dump.txt', 'w') 

chords = json.loads(payload)

cont = 0
for chord in chords:
    try:
        res = requests.post(lvms_api_url, json={'chord': chord})
        if res.status_code != 200:
            error_dump.write(str(chord))
            error_dump.write("\n")
        cont = cont + 1
        print(cont)
    except Exception as e:
        print(e)