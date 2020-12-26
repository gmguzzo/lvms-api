import json

# Using readlines() 
file1 = open('out', 'r') 
lines = file1.readlines() 

error_dump = open('/home/gmguzzo/Documents/projetos/louvemos/db_conversion/error_dump.txt', 'w') 
output = open('/home/gmguzzo/Documents/projetos/louvemos/db_conversion/port.txt', 'w') 

chord_dict = []

cont = 0
for line in lines:
    try:
        cont = cont + 1
        arr = line.split('|')
        chord = {
            'symbol': arr[0]
        }
        sequence = arr[1]

        if sequence.strip().lower() == 'nope':
            continue

        seq_arr = sequence.split('-')

        for el in seq_arr:
            el = el.strip()
            if el == '':
                continue
            if el.startswith('\n'):
                continue
            if el.startswith('p'):
                chord['bar'] = int(el[1:])
                continue

            if el.startswith('0'):
                chord['startFret'] = int(el)
                continue
            if len(el) == 1:
                continue
            if el.startswith('6'):
                chord['bassString'] = int(el[1:2])
                continue
            if el.startswith('7'):
                if 'soundedStrings' not in chord:
                    chord['soundedStrings'] = [int(el[1:2])]
                else:
                    chord['soundedStrings'].append(int(el[1:2]))
                continue
            if el.startswith('8'):
                if 'mutedStrings' not in chord:
                    chord['mutedStrings'] = [int(el[1:2])]
                else:
                    chord['mutedStrings'].append(int(el[1:2]))
                continue
            if el.startswith('1') or el.startswith('2') or el.startswith('3') or el.startswith('4') or el.startswith('5'):
                if 'diagram' not in chord:
                    chord['diagram'] = [[int(el[0:1]),int(el[1:2])]]
                else:
                    chord['diagram'].append([int(el[0:1]),int(el[1:2])])

        chord_dict.append(chord)

    except Exception as e:
        print(el)
        print(sequence)
        raise e
        error_dump.write(str(chord))
        error_dump.write("\n")
        continue


output.write(json.dumps(chord_dict))
