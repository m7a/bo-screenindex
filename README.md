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

_TODO FEHLT_

Spezielle Features
==================

## Zuordnung zu Tagen

_TODO AKTIVITÄTEN KÖNNEN BIS NACH 00:00 GEHEN_

## Hinterlegen von Nachrichten

Screeindex -p

## Behandlung unsauberer Programmbeendung

_TODO Die Switches für -l_

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

_TODO BESCHREIBUNG_

## `YYYY.xml`

_TODO FEHLT_

Export- und Auswertungsmöglichkeiten
====================================

_TODO FEHLT_

Alternativen und ähnliche Programme
===================================

_TODO FEHLT_

Ideen für eine neue Screen Index Version
========================================

_TODO FEHLT_
