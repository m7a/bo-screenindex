<?xml version="1.0" encoding="UTF-8"?>

<!--
	Screenindex Data to LaTeX messages transformation stylesheet,
	Copyright (c) 2012 Ma_Sys.ma. For further info send an e-mail to
	Ma_Sys.ma@web.de.
-->

<stylesheet version="1.0" xmlns="http://www.w3.org/1999/XSL/Transform">
	<output method="text" encoding="UTF-8" media-type="text/latex"/>
	<template match="/">
		<apply-templates/>
	</template>
	<template match="stats">
		<text>\section{Jahr </text>
		<value-of select="@year"/>
		<!-- Remember Hex 0x0A is \n (newline) -->
		<text>}&#x0a;</text>
		<text>This output was generated by a screenindex XSL Transformation.&#x0a;</text>
		<apply-templates/>
	</template>
	<template match="month">
		<text>\subsection{Monat </text>
		<value-of select="@no"/>
		<text>}&#x0a;</text>
		<apply-templates/>
		<!--<text>&#x0a;</text>-->
	</template>
	<template match="day">
		<if test="activity/message">
			<text>\paragraph{Tag </text>
			<value-of select="@no"/>
			<text>}&#x0a;</text>
			<text>\begin{itemize*}&#x0a;</text>
			<apply-templates/>
			<text>\end{itemize*}&#x0a;</text>
		</if>
	</template>
	<template match="activity">
		<apply-templates/>
	</template>
	<template match="message">
		<text>\item </text>
		<variable name="strstor1">
			<call-template name="string-replace-all">
				<with-param name="text" select="."/>
				<with-param name="replace" select="'\'"/>
				<with-param name="by" select="'\textbackslash '"/>
			</call-template>
		</variable>
		<variable name="strstor2">
			<call-template name="string-replace-all">
				<with-param name="text" select="$strstor1"/>
				<with-param name="replace" select="'#'" />
				<with-param name="by" select="'\#'" />
			</call-template>
		</variable>
		<call-template name="string-replace-all">
			<with-param name="text" select="$strstor2"/>
			<with-param name="replace" select="'_'" />
			<with-param name="by" select="'\_'" />
		</call-template>
		<text>&#x0a;</text>
	</template>
	<!--
		String replacing function from 
		http://geekswithblogs.net/Erik/archive/2008/04/01/120915.aspx
	-->
	<template name="string-replace-all">
		<param name="text" />
		<param name="replace" />
		<param name="by" />
		<choose>
			<when test="contains($text, $replace)">
				<value-of select="substring-before($text,$replace)" />
				<value-of select="$by" />
				<call-template name="string-replace-all">
					<with-param name="text" select="substring-after($text,$replace)" />
					<with-param name="replace" select="$replace" />
					<with-param name="by" select="$by" />
				</call-template>
			</when>
			<otherwise>
				<value-of select="$text" />
			</otherwise>
		</choose>
	</template>
</stylesheet>
