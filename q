#!/bin/sh -e
# Terminate screenindex and lock screensaver, Copyright (c) 2016 Ma_Sys.ma.
# For further info send an e-mail to Ma_Sys.ma@web.de.

if [ "$1" = "--help" ]; then
	echo "Usage $0 [--noscreensaver]"
	exit 0
fi

reenable_question() {
	if [ -x /usr/bin/zenity ]; then
		zenity --info --text="Click [ok] to re-enable screenindex"
	else
		xmessage "Click [ok] to re-enable screenindex"
	fi
	if [ "$1" = "scr" ]; then
		xscreensaver &
	fi
	exec /usr/bin/screenindex -l
}

screenindex_term || exit $?
if [ "$1" = "--noscreensaver" ]; then
	killall xscreensaver && reenable_question scr &
else
	reenable_question &
	exec /usr/bin/xscreensaver-command -lock
fi
