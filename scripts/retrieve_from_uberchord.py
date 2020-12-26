import requests
import json
import urllib
from time import sleep

uberchord_api_url = 'https://api.uberchord.com/v1/chords/'

# Using readlines() 
file1 = open('/home/gmguzzo/Documents/projetos/louvemos/db_conversion/formatted_uberchord_chords.txt', 'r') 
lines = file1.readlines() 

error_dump = open('/home/gmguzzo/Documents/projetos/louvemos/db_conversion/error_dump.txt', 'w') 
output = open('/home/gmguzzo/Documents/projetos/louvemos/db_conversion/output.txt', 'w') 

chord_dict = []

cont = 0
for line in lines:
    try:
        cont = cont + 1
        chord = line.strip()
        if cont % 10 == 0:
            print(cont)

        quoted_chord_str = urllib.parse.quote(chord)
        res = requests.get('{}{}'.format(uberchord_api_url,quoted_chord_str))
        if res.status_code == 200 and res.text != '' and res.text != '[]':
            output.write(res.text)
            output.write("\n")
        else:
            output.write('error: {}'.format(chord))
            output.write("\n")
        if cont >= 100:
            break;

    except Exception as e:
        print(e)
        error_dump.write(chord)
        error_dump.write("\n")
        continue

