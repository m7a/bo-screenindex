---
section: 32
x-masysma-name: screenindex
title: Screen Index
date: 2020/03/20 21:07:42
lang: de-DE
author: ["Linux-Fan, Ma_Sys.ma (Ma_Sys.ma@web.de)"]
keywords: ["screenindex", "java", "mdvl", "package"]
x-masysma-version: 1.0.0
x-masysma-repository: https://www.github.com/m7a/bo-screenindex
x-masysma-website: https://masysma.lima-city.de/32/screenindex.xhtml
x-masysma-owned: 1
x-masysma-copyright: |
  Copyright (c) 2020 Ma_Sys.ma.
  For further info send an e-mail to Ma_Sys.ma@web.de.
---
Beschreibung
============

Screen Index ermöglicht es aufzuzeichnen, wie lange der Computer läuft.
Dazu wird das Programm in den Autostart-Mechanismus des Betriebssystemes
aufgenommen und beim Systemstart oder beim Einloggen gestartet. Die Aufzeichnung
endet, sobald das Programm beendet wird, bspw. ausgelöst durch ein
Herunterfahren des Rechners.

Die Aufzeichnung kann über die Kommandozeile oder durch direktes Auswerten der
XML-Aktivitätendatei erfolgen. Das Programm sieht auch einen XHTML+SVG-Export
vor, der die Aktivität eines Jahres in einer interaktiven Übersicht exportiert.

Zusätzlich zur Zeitaufzeichnung können für den aktuell laufenden Zeitraum
Notizen hinterlegt werden, die man bspw. nutzen kann, um herauszufinden, wie
viel Zeit bestimmte Aktivitäten einnehmen.

Grundlegende Benutzung
======================

Zum Test kann man die JAR-Datei direkt aufrufen. In diesem Falle wird im
aktuellen Verzeichnis eine Konfigurationsdatei und Datenbank erstellt.

Hier ist eine Beispielsitzung:

~~~
$ java -jar screenindex.jar -l
Screen Index 1.0.4.5, Copyright (c) 2009, 2010, 2011, 2012, 2015 Ma_Sys.ma.
For further info send an e-mail to Ma_Sys.ma@web.de.

Unable to read configuration data: configuration.ini (No such file or directory)
java.io.FileNotFoundException: configuration.ini (No such file or directory)
	at java.io.FileInputStream.open0(Native Method)
	at java.io.FileInputStream.open(FileInputStream.java:195)
	at java.io.FileInputStream.<init>(FileInputStream.java:138)
	at java.io.FileReader.<init>(FileReader.java:72)
	at ma.screenindex.Configuration.readConfiguration(Configuration.java:50)
	at ma.screenindex.Main.main(Main.java:138)
This can happen at the first startup.
Screenindex logging in progress -- waiting for shutdown...
~~~

Da das Programm zum ersten Mal gestartet wurde, findet es keine
Konfigurationseinstellungen und erzeugt diese in der Datei `configuration.ini`.
Anschließend wird die Aufzeichnung gestartet, bis man das Programm beendet,
bspw. über den Task Manager oder [CTRL]-[C].

Im Folgenden steht die Ausgabe, wenn man das Programm per [CTRL]-[C] beendet:

	^Cdone.

Es wird also die Meldung “done“ (fertig) ausgegeben, um zu signalisieren, dass
die Aktivitätsdaten gespeichert wurden. Anschließend kann die Aktivität des
aktuellen Jahres, Monats und Tages per Kommandozeile abgefragt werden:

~~~
$ java -jar screenindex.jar -v y current
Screen Index 1.0.4.5, Copyright (c) 2009, 2010, 2011, 2012, 2015 Ma_Sys.ma.
For further info send an e-mail to Ma_Sys.ma@web.de.

3.2020: 0 h, 8 min, 33 sec (513 sec)

Total:    0 h, 8 min, 33 sec (513 sec)
Averange: 0 h, 8 min, 33 sec (513 sec)
$ java -jar screenindex.jar -v m 03.2020
Screen Index 1.0.4.5, Copyright (c) 2009, 2010, 2011, 2012, 2015 Ma_Sys.ma.
For further info send an e-mail to Ma_Sys.ma@web.de.

20.03.2020: 0 h, 8 min, 33 sec (513 sec)

Total:    0 h, 8 min, 33 sec (513 sec)
Averange: 0 h, 8 min, 33 sec (513 sec)
$ java -jar screenindex.jar -v d 20.03.2020
Screen Index 1.0.4.5, Copyright (c) 2009, 2010, 2011, 2012, 2015 Ma_Sys.ma.
For further info send an e-mail to Ma_Sys.ma@web.de.

Activity from 21:14:27 to 21:23:00: 0 h, 8 min, 33 sec (513 sec)

Total:    0 h, 8 min, 33 sec (513 sec)
Averange: 0 h, 8 min, 33 sec (513 sec)
~~~

Da lediglich Daten für einen Tag vorliegen, sind die Inhalte für alle drei
Varianten gleich. Auf Tagesebene werden einzelne _Activities_ unterschieden.
Wenn man das Programm erneut startet und beendet, so erhält man in der
Tagesansicht eine nach Aktivitäten aufgeschlüsselte Ansicht:

~~~
Screen Index 1.0.4.5, Copyright (c) 2009, 2010, 2011, 2012, 2015 Ma_Sys.ma.
For further info send an e-mail to Ma_Sys.ma@web.de.

Activity from 21:14:27 to 21:23:00: 0 h, 8 min, 33 sec (513 sec)
Activity from 21:25:54 to 21:26:35: 0 h, 0 min, 41 sec (41 sec)

Total:    0 h, 9 min, 13 sec (554 sec)
Averange: 0 h, 4 min, 36 sec (277 sec)
~~~

Installation
============

Zur Installation von Screen Index muss das `screenindex.jar` kompiliert und in
einem Ordner bereitgestellt werden. Die Kompilation erfolgt mit `ant jar`, die
Installation durch Kopieren des JAR-files in einen Ordner nach Wahl.

Auf Debian-basierten Linuxsystemen kann mit `ant package` ein Paket erzeugt
werden, welches ein Skript `screenindex` mitliefert, um Screen Index aufzurufen.
Mit diesem Skript wird automatisch das Verzeichnis `$HOME/.mdvl/screenindex` als
Ablageort für Konfiguration und Datenbank festgelegt.

Installiert man Screen Index selbst, so ist es sinnvoll, ein entsprechendes
Skript ebenfalls zu erstellen, damit die im Hintergrund laufende Version die
selbe Datenbank verwendet, wie eventuell im Vordergrund erfolgende Anfragen.
Im Einfachsten Falle enthält das Skript nur ein `cd` in ein geeignetes
Verzeichnis gefolgt vom Screen Index Start.

Zusätzlich ist es für die Installation sinnvoll, Screen Index in den
Autostartmechanismus des Betriebssystemes zu integrieren. Unter Windows legt
man eine Verknüpfung im Ordner `shell:startup` an, unter Linux kann man
unter Debian bspw. einen Eintrag in der `.xsession`-Datei oder dem
Autostartmechanismus des Fenstersystemes vornehmen. Je nach Nutzungsweise ist
für den automatisch gestarteten Aufruf ein `screenindex -l -g` oder
`screenindex -l -y` sinnvoll.

Je nach dem, welche Zeit man erfassen will, gibt es weiterhin Sinn, Screen Index
beim Sperren des Bildschirmes zu beenden, um diese Zeit von der Erfassung
auszuklammern. Unter Linux kann man bspw. das Kommando zum Aufrufen des
Bildschirmschoners um einen Eintrag für `screenindex_term` erweitern -- ein
Skript, welches im Repository mitgeliefert wird. Wie eine solche Integration
aussehen kann, zeigt das ebenfalls mitgelieferte Skript `q` am Beispiel des
XScreenSaver.

Spezielle Features
==================

## Zuordnung zu Tagen

Bei der Aufzeichnung werden Activities nur dann beendet, wenn Screen Index
beendet wird. Dadurch wird eine vor 00:00 des nächsten Tages begonnene Aktivität
auch in den neuen Tag fortgesetzt. Beispiel:

~~~
$ screenindex -v d 20.02.2018
Screen Index 1.0.4.5, Copyright (c) 2009, 2010, 2011, 2012, 2015 Ma_Sys.ma.
For further info send an e-mail to Ma_Sys.ma@web.de.

Activity from 21:40:31 to 22:03:38: 0 h, 23 min, 7 sec (1387 sec)
Activity from 22:25:24 to 22:58:18: 0 h, 32 min, 53 sec (1974 sec)
Activity from 23:01:09 to 23:25:18: 0 h, 24 min, 8 sec (1449 sec)
Activity from 23:27:43 to 00:43:10: 1 h, 15 min, 27 sec (4527 sec)

Total:    2 h, 35 min, 37 sec (9337 sec)
Averange: 0 h, 38 min, 53 sec (2334 sec)
~~~

In diesem Beispiel wurde die vierte _Activity_ um 23:27 angefangen und endete
um 00:43 des nächsten Tages. Nachfolgende Aufzeichnungen, die nach 00:00
angefangen werden, werden dann dem nächsten Tag zugerechnet:

~~~
$ screenindex -v d 21.02.2018
Screen Index 1.0.4.5, Copyright (c) 2009, 2010, 2011, 2012, 2015 Ma_Sys.ma.
For further info send an e-mail to Ma_Sys.ma@web.de.

Activity from 01:10:39 to 01:40:20: 0 h, 29 min, 41 sec (1781 sec)
Activity from 18:29:52 to 19:10:32: 0 h, 40 min, 39 sec (2440 sec)
Activity from 19:17:52 to 19:41:57: 0 h, 24 min, 4 sec (1445 sec)
Activity from 20:16:01 to 22:05:26: 1 h, 49 min, 25 sec (6565 sec)
Activity from 22:38:34 to 23:52:35: 1 h, 14 min, 0 sec (4441 sec)

Total:    4 h, 36 min, 52 sec (16672 sec)
Averange: 0 h, 55 min, 34 sec (3334 sec)
~~~

Diese spezielle Aufzeichnungsweise liegt aktuell in der Implementierung
begründet und könnte sich in einer neueren Screen Index Version ändern.
Insgesamt trifft sie aber die Idee nicht schlecht, dass eine angefangene
Aktivität wohl nicht allein durch Springen der Uhr von 23:59 auf 00:00 beendet
werden sollte. Aus menschlicher Sicht ist vielleicht die Zuordnung zu einem
neuen Tag ab dem Moment interessant, wo man inzwischen geschlafen hat. Dieses
kann (und soll) das Programm aber nicht erkennen...

## Hinterlegen von Nachrichten

Den laufenden Aktivitäten können durch folgenden Aufruf Nachrichten zugeordnet
werden:

~~~
$ screenindex -p NACHRICHT 
~~~

Damit wird der laufenden Aktivität die Nachricht `NACHRICHT` zugeordnet. Sie
wird zunächst in der Datei `screenindex-locked.txt` hinterlegt und beim Beenden
der Aktivität in die Datenbank übertragen. Anschließend kann man mit der
Ansicht des aktuellen Tages, die zugeordnete Nachricht betrachten, Beispiel:

~~~
$ java -jar screenindex.jar -p Testnachricht
[... weitere Aktionen inkl. Beenden der Aktivität ...]
$ java -jar screenindex.jar -v d current
Screen Index 1.0.4.5, Copyright (c) 2009, 2010, 2011, 2012, 2015 Ma_Sys.ma.
For further info send an e-mail to Ma_Sys.ma@web.de.

Activity from 14:34:08 to 14:34:30: 0 h, 0 min, 22 sec (22 sec)
  Message: Testnachricht

Total:    0 h, 0 min, 22 sec (22 sec)
Averange: 0 h, 0 min, 22 sec (22 sec)
~~~

In der Monatsansicht können Nachrichten ebenfalls betrachtet werden, indem man
zusätzlich den Parameter `-i` verwendet:

~~~
$ java -jar screenindex.jar -v m current -i
Screen Index 1.0.4.5, Copyright (c) 2009, 2010, 2011, 2012, 2015 Ma_Sys.ma.
For further info send an e-mail to Ma_Sys.ma@web.de.

20.03.2020: 0 h, 9 min, 13 sec (554 sec)
21.03.2020: 0 h, 0 min, 22 sec (22 sec)
  Testnachricht

Total:    0 h, 9 min, 35 sec (576 sec)
Averange: 0 h, 4 min, 47 sec (288 sec)
Messages: 1
~~~

Benutzt man stattdessen `-m`, werden die Nachrichten ohne die Angabe der
Tage angezeigt, sodass man eine übersichtlichere Darstellung der Nachrichten
erhält.

## Behandlung unsauberer Programmbeendung

Da Screen Index die Aktivität nur speichern kann, wenn es ordnungsgemäß, das
heißt unter Linux bspw. mit einem INT oder TERM-Signal, beendet wird, gibt es
spezielle Vorkehrungen für den Fall, dass ein solches sauberes Beenden nicht
möglich war. Das Standardverhalten von `-l` nach einem „unsauberen“ Beenden
bspw. unter Linux durch KILL ist es, die weitere Aufzeichnung zu verweigern:

~~~
$ java -jar screenindex.jar -l
Screen Index 1.0.4.5, Copyright (c) 2009, 2010, 2011, 2012, 2015 Ma_Sys.ma.
For further info send an e-mail to Ma_Sys.ma@web.de.

There is a file called "/data/main/mdvl/rr/x-artifacts-unmanaged/xond/screenindex/tmp/test/screenindex-locked.txt".
That file indicates that Screenindex is already running.
If this is not true, you can just call screenindex with
the "-l -d" option to delete the file. Then just try to
start logging again.
~~~

Grund für die Sinnhaftigkeit dieses Standardverhaltens ist es, dass mehrere
parallel laufende Screen Index Instanzen noch problematischer als das Verweigern
der Aufzeichnung wären: Aktivitäten könnten doppelt oder fehlerhaft gespeichert
werden, sodass der parallele Betrieb mit der selben Datenbank unbedingt zu
vermeiden ist.

Eine entsprechende Meldung auf der Konsole weist den Benutzer darauf hin. Wenn
man sich sicher ist, dass kein Fehler vorliegt, so kann man durch einmaligen
Aufruf mit `-l -d` die entsprechende `screenindex-locked.txt` löschen. Eine
vormalige Aktivität geht dabei verloren. Eine Alternative ist `-l -y`, bei dem
die Daten aus der `screenindex-locked.txt` als (unvollständige) Aktivität
eingetragen werden, sofern möglich. Diese Variante kann man auch standardmäßig
verwenden, wenn man weiß, dass parallel laufende Screen Index Instanzen
ausgeschlossen sind und sich das lokale Betriebssystem nicht so einstellen
lässt, dass Screen Index beim Herunterfahren oder Ausloggen sauber beendet wird.

Die dritte Möglichkeit ist `-l -g`, die den Benutzer mit einem graphischen
Dialog fragt; hier ein Screenshot:

![Aufruf von `screenindex -l -g` bei Vorhandensein einer
`screenindex-locked.txt`](screenindex_att/screenindexlg.png)

In diesem Dialog kann der Benutzer interaktiv [OK] wählen, um den selben
Effekt zu erzielen wie ein einmaliges `screenindex -l -y` oder [CANCEL], um
den Startvorgang von Screen Index abzubrechen. Bei Verwendung von Screenindex
in graphischen Umgebungen ist der Start mit `-l -g` eigentlich immer zu
empfehlen, wenn unsauberes Beenden eine Ausnahmesituation darstellt.

## Aktualität der Datenbank bei gleichzeitig laufendem Screen Index Logging

Wenn man `screenindex -l` im Hintergrund laufen lässt und parallel die
Datenbankinhalte mit den `screenindex -v`-Optionen abfragt, so fällt auf, dass
die aktuelle Aktivität noch nicht in den Daten auftaucht. Dies ist der Logik
geschuldet, nach der Screen Index die Daten ausliest: Es wird nur die
Jahresaufzeichnung, nicht jedoch die aktuelle `screenindex-locked.txt`
betrachtet.

Ausnahmen von dieser Regel sind die Optionen `-t` und `-r`, die jeweils auch die
Zeit aus der `screenindex-locked.txt` einbeziehen. Beide Optionen sind dafür
gedacht sind, eine Art tägliche Nutzungszeit bspw. in einer Statusleiste
anzuzeigen. In diesem Falle ist eine möglichst realitätsgetreue Darstellung
erforderlich; die Auswertung der Aktivitätsdaten mit den anderen `-v`-Optionen
wird erwartungsgemäß hingegen eher länger zurückliegende Aktivitäten betreffen
und daher oft den noch laufenden Tag ausklammern.

Dateiformate
============

## `configuration.ini`

Diese Datei enthält generelle Programmeinstellungen. Die Standardwerte sind
folgendes:

	[configuration]
	; Screen Index configuration, created by version 1.0.4.5
	locked-file=screenindex-locked.txt
	io-date-format=dd.MM.yyyy HH:mm:ss
	delta-t-max-per-day=21600
	backup-interval-secs=600
	database-dir=db

Relevante Einstellungsmöglichkeiten werden im Folgenden erklärt.

`locked-file`
:   Gibt einen Dateinamen für eine `screenindex-locked.txt` an, die genutzt
    wird, um zu verhindern, dass zwei Instanzen gleichzeitig auf der selben
    Datenbank gestartet werden. Damit richten wiederholt aufgerufene Autostarts
    keinen Schaden an.
`delta-t-max-per-day`
:   Gibt eine Anzahl in Sekunden an, ab der ein Tag als „voll“ gilt. Der
    Standardwert sind 6 Stunden. Diese Einstellung wirkt sich nur in Verbindung
    mit der Option `-r` aus.
`backup-interval-secs`
:   Gibt ein Sekundenintervall an, in dem die `screenindex-locked.txt`
    aktualisiert wird. Darin wird dann die aktuelle Zeit festgehalten, um
    im Falle von unerwartetem Programm-Benden zumindest einen Teil der
    tatsächlichen Zeit zu erfassen.
`database-dir`
:   Gibt einen Verzeichnisnamen zur Ablage der „Datenbankdateien“ an.

## `screenindex-locked.txt`

Die Datei `screenindex-locked.txt` hat folgenden strukturellen Aufbau:

	VON to BIS
	NACHRICHT1
	NACHRICHT2
	...

`VON` und `BIS` in der 1. Zeile sind dabei sekundengenaue Datums und
Uhrzeitangaben, die Nachrichten folgen dannach in jeweils einer Zeile pro
Nachricht. Läuft die aktuelle Aktivität kürzer als die konfigurierten
`backup-interval-secs`, steht `unknown` als Wert für `BIS`. Ein Beispiel für
diesen Zustand:

	21.03.2020 14:34:40 to unknown

Hier ist gleichzeitig der Fall zu sehen, in dem keine Nachrichten hinterlegt
wurden -- die Datei hat also nur eine Zeile.

## `YYYY.xml`

Screen Index verwendet ein XML-Format zum Speichern der Aktivitäten. Dabei wird
für jedes angefangene Jahr eine neue Datei angelegt. Es existiert keine DTD und
kein Schema für das Format, von daher wird es hier anhand eines Beispiels
erläutert:

~~~{.xml}
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!--Screenindex year data file, written by Screenindex Version 1.0.4.5-->
<stats created-by-version="1.0.4.5" year="2020">
	<month no="3">
		<day no="20">
			<activity end="20.03.2020 21:23:00" start="20.03.2020 21:14:27"/>
			<activity end="20.03.2020 21:26:35" start="20.03.2020 21:25:54"/>
		</day>
		<day no="21">
			<activity end="21.03.2020 14:34:30" start="21.03.2020 14:34:08">
				<message>Testnachricht</message>
			</activity>
		</day>
	</month>
</stats>
~~~

Der Aufbau der Datei sollte weitgehend selbsterklärend sein. Für die Jahresdatei
gibt es ein `stat`-Element, welches das Jahr der Betrachtung explizit im
Attribut `year` angibt (es lässt sich alternativ aus dem Dateinamen ermitteln).
Innerhalb des Dokuments befindet sich ein `month`-Element für jeden Monat. In
jedem Momantselement befindet sich dann ein `day`-Element für jeden Tag, wobei
im Attribut `no` jeweils der Tag im Monat bzw. die Monatsnummer angegeben
werden.

Innerhalb der `day`-Elemente befinden sich dann ein oder Mehr `activity`
Elemente, die in den Attributen `start` und `end` die Zeit der Aktivität
angeben. Wenn keine Nachrichten hinterlegt sind, sind die `activity`-Elemente
leer, ansonsten erhalten sie für jede Nachricht ein `message`-Element, in dem
der Nachrichtentext als PCDATA gespeichert ist.

Wenn man die Jahresdatei auf dem Dateisystem direkt öffnet, sind die Inhalte
oft fortlaufend komplett auf einer Zeile untergebracht. Eine schnelle,
automatische Formatierung kann durch Webbrowser oder die auf
[StackOvervlow angegebenen Tools](https://stackoverflow.com/questions/16090869)
erreicht werden.

Export- und Auswertungsmöglichkeiten
====================================

Die Auswertemöglichkeiten über die `-v`-Optionen wurden bereits in den
vorangegangenen Abschnitten beleuchtet. Dieser Abschnitt beschreibt Alternative
Möglichkeiten.

## Nutzung des Screenindex Export

Obwohl als „veraltet“ gekennzeichnet, funktioniert die vor langer Zeit
vorgesehene Exportfunktion nach XHTML auch noch 2020. Sie kann über den Aufruf
von `screenindex -s JAHR DATEI.xhtml` genutzt werden und produziert eine
einzige XHTML-Datei, die die Aktivitäten per Maus „erkundbar“ macht. Für die
vorher gezeigte Test-Datenbank sieht die Seite folgendermaßen aus:

![Screen Index XHTML Exportergebnis für eine kleine
Testdatenbank](screenindex_att/exportsmall)

![Screen Index XHTML Exportergebnis für ein ganzes Jahr, Zahlen wurden
unkenntlich gemacht](screenindex_att/exportlarge.png)

Darin kann man ausgehend von der linken Seite durch Klick auf die Tabellenzeilen
die Aktivitäten interaktiv aufrufen; die Darstellung ist ansonsten vergleichbar
mit der von `screenindex -v`. Als Besonderheit werden Diagramme für die Monate
generiert, die die monatliche bzw. tägliche Aktivität zeigen. Bei wenigen
und nur kurzen Aktivitäten wie dem hier gezeigten Beispiel sind diese jedoch
weitgehend leer.

Die Auswahl eines bestimmten Tages ist über das Formularfeld in der oberen
rechten Ecke möglich.

## Direkte Auswertung der XML-Datei

Als Alternative zu den integrierten Export und Analysemöglichkeiten bietet
das Screen Index XML-Format natürlich auch die Möglichkeit einer direkten
Auswertung. Zwei Beispiele hierfür finden sich im Repository unter `auswertung`:

`messages.css`
:   Mit Hilfe eines einfachen CSS-Stylesheets können die Nachrichten aus dem
    XML-Format extrahiert und sichtbargemacht werden werden. Mit diesem
    Stylesheet werden die Aktivitäten zusammengehörender Monate durch Linien
    und die Aktivitäten eines Tages durch einen grauen Strich gekennzeichnet.

![Beispiel für die Anwendung von `messages.css`](screenindex_att/messagescss)

`messages-tex.xsl`
:   Zum Exportieren der Nachrichten in ein LaTeX-Dokument kann dieses Stylsheet
    genutzt werden. Die Dokumentstruktur muss noch ergänzt und das Paket
    `mdwlist` importiert werden. Unter Linux kann ein XSLT-Stylesheet bspw. 
    mit dem Programm `xsltproc` angewendet werden.

![Beispiel für die Anwendung von
`messages-tex.xsl`](screenindex_att/messagesxsl)

Alternativen und ähnliche Programme
===================================

Es gibt zahllose Programme zum Erfassen der Nutzungszeit von Rechnern. Dieser
Abschnitt verlinkt einige für ähnliche Zwecke wie Screen Index geeignete
Programme, die teilweise stark abweichende Designs haben.

 * [gtimer(1)](https://manpages.debian.org/stable/gtimer/gtimer.1.en.html)
 * [gtimelog(1)](https://manpages.debian.org/stable/gtimelog/gtimelog.1.en.html)
   [Blog Post](https://www.linuxinsider.com/story/71917.html)

Weitere Ideen finden sich in der
[Liste auf Stackoverflow](http://stackoverflow.com/questions/398344)

Ideen für eine neue Screen Index Version
========================================

ohne Anspruch auf Vollständigkeit und ohne besondere Reihenfolge

 * Weniger Arbeitsspeicherverbrauch und Hintergrundaktivitäten durch Wahl einer
   systemnahen Programmiersprache ohne GC
 * Datenablage in geeigneterem Format, nicht die ganze Datenbank dauerhaft in
   den Arbeitsspeicher laden
 * Vollständige Unterstützung für Zeitzonen und andere Datumsformate
 * Datensparsame automatische Erfassung genutzer Anwendungen mit Hilfe von
   Kategorie-Regelwerken
 * Möglichkeit zur expliziten Projekt-Zeiterfassung --
   Nachrichten mit dem Datum+Uhrzeit des Hinzufügens versehen.
 * Informativere Auswertungsmöglichkeiten, eventuell auch mehr Statistik?
 * Integration mit Erfassung der Nutzung von Rechenleistung zur Vorhersage
   zukünftigen Hardwarebedarfes? Wichtig hierbei ist, dass der genutzte
   Speicherplatz für diese Daten möglichst klein ausfallen sollte, bspw. nur
   Minimum,Median,Maximum oder eventuell noch ein 0.25 und 0.75 Quantil?
