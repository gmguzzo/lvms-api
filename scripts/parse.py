# Using readlines() 
file1 = open('out', 'r') 
lines = file1.readlines() 

file1 = open('/home/gmguzzo/Documents/projetos/louvemos/db_conversion/formatted_uberchord_chords.txt', 'w')
  
count = 0
root_chords = ["C","D","E","F","G","A","B"]
sus_chords = ["C#","D#","F#","G#","A#"]
bemol_chords = ["Db","Eb","Gb","Ab","Bb"]

# Strips the newline character 
for line in lines: 
    chord = line.strip()
    if chord in root_chords:
        chord = chord.replace('/','_')
        file1.write(chord)
        file1.write("\n")
        continue


    for sus in sus_chords:
        if chord.startswith(sus):
        	chord = chord.replace('/','_')
        	file1.write(chord[:chord.index('#') + 1] + '_' + chord[chord.index('#') + 1:])
        	file1.write("\n")
        	continue

    for bemol in bemol_chords:
        if chord.startswith(bemol):
        	chord = chord.replace('/','_')
        	file1.write(chord[:chord.index('b') + 1] + '_' + chord[chord.index('b') + 1:])
        	file1.write("\n")
        	continue


file1.close() 
