Langsam wird es wieder Winter, weshalb die Pinguine abends wieder mehr Zeit haben, um gemeinsam zu spielen. Ein Pinguin, Carol, hatte eine Idee, von der sie auch die anderen Pinguine sehr schnell überzeugen konnte: Pingu Schach. Doch da Carol noch nicht so viel Programmiererfahrung hat, braucht sie deine Hilfe für die Umsetzung.

**Das Spiel**

**Allgemeine Regeln**

- In jedem Zug muss genau eine Figur bewegt werden. Bewegen um 0 Felder zählt nicht als Bewegung und ist ein ungültiger Zug.
- Auf jedem Feld darf nur eine Figur stehen.
- Man kann gegnerische Figuren schmeißen, indem eine eigene Figur auf ein von einem Gegner belegtes Feld gezogen wird. Die Gegnerische Figur wird dann vom Feld entfernt.
- Alle Figuren können sich nur in 8 Richtungen bewegen: Parallel zu den Koordinatenachsen oder den Diagonalen.
- Wenn nicht anders beschrieben, können Figuren nicht andere Figuren auf ihrem Weg zum neuen Feld überspringen. Bei diagonalen Bewegungen muss auch nur die Diagonale frei sein.

**Figuren**

Alle Figuren werden durch ein char repräsentiert, ein leeres Feld durch ein Leerzeichen. Wie im normalen Schach gibt es auch im Pingu Schach 2 Fraktionen:

**die Pinguine mit den Figuren:**

- Z Zügelpinguine sind streitlustige Pinguine, die jeden Zug nur maximal 3 Felder exakt in Richtung positiver Y Koordinaten ziehen können. In alle andere Richtungen können sie nur 1 Feld pro Runde ziehen.
- F Felsenpinguine können in alle 8 Richtungen über andere Figuren hinweg springen, aber nur maximal 2 Felder weit.
- K Kaiserpinguin, kann bis zu 5 Felder weit in alle 8 Richtungen gehen. Wenn ein Kaiserpinguin zum Start des Zuges auf den benachbarten 8 Feldern mindestens zwei weitere Pinguine hat, kann er unbegrenzt weit gehen.
- C Eine kleine Gruppe an Pinguin Küken. Sie bewegen sich zusammen als Gruppe (auch Crèche genannt) über das Spielfeld, können jede Runde aber nur 1 Feld weit laufen. Auch sie dürfen sich in alle 8 Richtungen bewegen. Wenn sie alle geschmissen wurden, verlieren die Pinguine!

**die "Feinde" der Pinguine mit den Figuren:**

- S Seeleoparden verhalten sich wie Zügelpinguine, haben aber ihre erweiterte Reichweite in negative Y Richtung.
- O Orcas verhalten sich wie Felsenpinguine.
- E Eisbär, verhält sich wie ein Kaiserpinguin.
- A Australische Seelöwen, verhalten sich wie die Pinguin Küken. Wenn kein Seelöwe auf dem Spielfeld ist, haben die Pinguine gewonnen.

**Spielfeld**

Das Spielfeld ist ein Rechteck beliebiger Größe, muss aber mindestens 1 Feld haben. Die X-Koordinate beschreibt die Waagerechte, Die Y-Koordinate die Senkrechte.

Das Standard Spielfeld hat sie Größe 8*16. Die Figuren starten wie folgt:

```
/	1	2	3	4	5	6	7	8
  
1	Z	F	F	K	C	F	F	Z

2	 	 	Z	Z	Z	Z	 	 

3	 	 	 	 	 	

4	 	 	 	 	

5	 	 	 	

6	 	 	 

7	 	 	

8	 	 	 

9	 	 	

10	 

11	 

12	 

13	 

14	

15	 	 	S	S	S	S	 	 

16	S	O	O	A	E	O	O	S
```
Die Nummerierung der einzelnen Zeilen/Spalten ist wie in der Tabelle.

**Spielbeginn**

Zu Beginn eines Spiels wird das Spielbrett meist wie oben beschrieben vorbereitet, auch wenn andere Aufstellungen oder sogar Spielbrett Größen von manchen Pinguinen präferiert werden. Die Pinguine haben immer den ersten Zug.

**Spielende**

Das Spiel endet, wenn ein Team alle Pinguin Küken oder Seelöwen verloren hat. Das Team, dass noch entsprechende Figuren auf dem Brett hat, gewinnt. Wenn keine dieser Figuren auf dem Brett ist, haben beide gewonnen.

**Implementierung**

**Allgemeine Anmerkungen**

- Die Signatur der gegebenen Methoden darf nicht abgeändert werden.
- Bereits Implementiertes muss wiederverwendet werden, inklusive aller Getter/Setter für Zugriffe auf Attribute. Ansonsten können für dich vorteilhafte Folgefehler nicht in der Erstkorrektur berücksichtigt werden. Das wird auch von public Tests überprüft.
- Alle fehlerhafte Eingaben müssen, wenn nicht explizit anders geschrieben, behandelt werden. Dabei oder bei jedem anderen auftretenden Fehler wird ein für den Datentyp spezifischer Wert zurückgegeben:
```
char: '\0' (das null Zeichen)
int: -1
boolean: false
```

- Es darf angenommen werden, dass alle Methoden nur im definierten Wertebereich zurückgeben, der Rückgabewert muss aber immer auf den Fehlerwert überprüft werden.

**Spielfeld**

Die Implementierung arbeitet intern mit einem bei (0|0) beginnenden Koordinatensystem. Trotzdem soll es dem Nutzer als ein bei (1|1) beginnendes Koordinatensystem dargestellt werden. Im Template ist schon eine GameBoard Klasse vorbereitet, die nur noch implementiert werden muss. Wie genau das Spielfeld implementiert wird, ist nicht vorgeschrieben. Sie muss aber die folgenden Methoden den Vorgaben entsprechend implementieren:

- Konstruktor GameBoard(int sizeX, int sizeY): erstellt ein Spielfeld mit den Dimensionen sizeX * sizeY. Alle Felder müssen mit Leerzeichen initialisiert werden. - sizeX und sizeY sind immer >0 und nie >100.
- get(x, y): Gebe die Figur auf Position (x,y) zurück. Wenn (x,y) außerhalb des Spielfelds liegt, gib '\0' (das null Zeichen) zurück.
- set(x, y, piece): Setze das Feld (x,y) auf piece. Wenn (x,y) außerhalb des Spielbretts liegt oder piece '\0' ist, soll das Spielfeld nicht verändert werden und false zurückgegeben werden. Wenn das Spielfeld erfolgreich modifiziert wurde, wird true zurückgegeben.
- getSizeX()/getSizeY(): Gib die im Konstruktor spezifizierte Größe des Spielfeldes zurück.
- newDefault(): Erstelle ein neues 8*16 Spielfeld und initialisiere es mit dem Standard Spielfeld.
- evaluateWinner(): Gibt wenn möglich den Gewinner zurück. Wenn es keinen gibt, wird 0 zurückgegeben, wenn die Pinguine gewonnen haben 1 und für die Feinde der Pinguine 2. Wenn beide gewonnen haben, soll -1 zurückgegeben werden. Dabei muss get genutzt und der Rückgabewert überprüft werden.

**Züge überprüfen**

Für jeden Zug muss überprüft werden, ob er zulässig ist. Dafür werden zuerst ein paar Hilfsmethoden implementiert. Die Methodenrümpfe sind schon in PinguChess vorbereitet. Dabei soll bereits implementierte Funktionalität wiederverwendet werden. Der Rückgabewert all dieser Methoden muss immer auf -1 getestet und entsprechend weitergeben werden.

- getTeam(piece): Gibt das Team zurück, zu dem die Spielfigur gehört. 0 steht für die Pinguine, 1 für die Feinde der Pinguine und -1 für eine unbekannte Spielfigur.
- getDirection(dX, dY): Gibt anhand der Differenz der Koordinaten die Richtung zurück, in welche die Figur bewegt wird. 0 ist nach oben, 1 ist nach oben rechts, danach geht es bis 7 im Uhrzeigersinn weiter, was oben links bedeutet. Wenn die Richtung des Zuges nicht exakt einer der 8 erlaubten Richtungen entspricht oder (0/0) ist, soll -1 zurückgegeben werden.
- getDistance(dX, dY): Gibt die mit diesem Zug zurückgelegte Strecke gemessen in Spielfeldern zurück. Wenn der gewählte Zug nicht einer der 8 Richtungen entspricht, soll -1 zurückgegeben werden. Ein Zug von (0/0) hat die Länge 0.
- getFriendlyNeighbours(x,y): Gibt die Anzahl der Figuren zurück, die auf einem der 8 benachbarten Felder stehen und mit der Figur auf (x|y) im gleichen Team sind. Wenn die Parameter ungültig sind, wird -1 zurückgegeben
- checkPath(fromX, fromY, toX, toY): Überprüft ob der Weg zum Zielfeld frei ist, er eine valide Länge und Richtung hat und ob das Zielfeld nicht von einer eigenen Figur besetzt ist. Gibt false zurück, wenn etwas davon nicht zutrifft. Wenn der Zug erlaubt ist, wird true zurückgegeben.
- checkJump(fromX, fromY, toX, toY): Überprüft, ob die Figur ihren Zug auf dem angegebenen Feld beenden darf und ob der Sprung eine valide Länge und Richtung hat. Gibt, wenn das der Fall ist, true zurück, sonst false. Dies ersetzt checkPath für springende Figuren wie dem Felsenpinguin, die nicht alle Felder auf dem zurückgelegten Weg auf andere Figuren überprüfen müssen.
- checkMove(fromX, fromY, toX, toY): Überprüft mithilfe der anderen Methoden, ob der angegebene Zug für die sich auf dem Startfeld befindende Figur valide ist und gibt für einen validen Zug true zurück, sonst false.

**Ein Zug**

Als nächstes müssen die Abläufe für einen Zug implementiert werden. Im auszugebenden Text wurden für eine bessere Lesbarkeit alle Leerzeichen durch ein '⎵' ersetzt.

runTurn(): Implementiere einen Zug, der wie folgt abläuft:

1. Schreibe den aktuellen Stand auf die Konsole. Dafür gibt es bereits writeGameBoard in PinguLib.
2. Schreibe "Die⎵Pinguine⎵sind⎵am⎵Zug" oder "Die⎵Feinde⎵der⎵Pinguine⎵sind⎵am⎵Zug" auf die Konsole.
3. Erfrage erst mit "Bitte⎵X⎵Koordinate⎵der⎵Figur⎵eingeben" die x Koordinate, dann entsprechend ähnlich die y Koordinate der Figur. Dafür kannst du readInt aus PinguLib verwenden. Vergiss außerdem nicht, die Koordinaten in das intern benutzte Format zu übersetzen.
4. Überprüfe, ob sich in diesem Feld eine Figur des Spielers befindet. Wenn nicht wiederhole die Schritte ab 3.
5. Schreibe "Wohin⎵soll⎵die⎵Figur⎵gehen?" auf die Konsole.
6. Erfrage wieder mit "Bitte⎵X⎵Koordinate⎵des⎵Zielfeldes⎵eingeben" und dem äquivalent für die Y-Koordinate das Zielfeld.
7. Überprüfe, ob das ein valider Zug ist, wenn nein wiederhole die Schritte ab 3.
8. Führe den Zug aus.
9. Der andere Spieler hat den nächsten Zug.

run(): Implementiere einen vollständigen Spieldurchlauf. Für jedes Spiel soll folgendes passieren:

1. Überprüfe, ob es schon einen Gewinner gibt. Wenn ja, schreibe je nach Situation entweder "Die⎵Pinguine⎵haben⎵gewonnen!", "Die⎵Feinde⎵der⎵Pinguine⎵haben⎵gewonnen!" oder "Beide⎵haben⎵gewonnen?" und beende die Methode.
2. Rufe runTurn() aus.
3. Springe zurück zu 1.

Konstruktor: Initialisiere das Spiel wie in Spielbeginn beschrieben und mit dem Standard Brett.

Das Spiel kann nun mit Hilfe der schon vorbereiteten main Methode gespielt werden.

**Tests**

Füge noch zu 3 in der Aufgabenstellung geforderten Methoden JUnit Tests hinzu, die je mindestens 3 edge cases testen. Die Tests können auf verschiedene Klassen verteilt werden solang sie im pgdp.chess.tests package sind. Außerdem soll je kurz in einem Kommentar erklärt werden, wo welcher edge case abgedeckt werden soll. Es dürfen nur Annotations aus junit-jupiter-api und junit-jupiter-params (außer @CsvFileSource) benutzt werden.

