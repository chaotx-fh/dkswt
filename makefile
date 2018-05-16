## config ##
############
# file title
_ServerTitle = dkserver
_ClientTitle = dkclient

# src root
_ServerRoot = java\src\eu\zoho\chaotx\doppelkopf\server
_ClientRoot = java\src\eu\zoho\chaotx\doppelkopf\client

# main class
_ServerMain = eu.zoho.chaotx.doppelkopf.server.DKServer
_ClientMain = eu.zoho.chaotx.doppelkopf.client.DKClient

# output directory
_BuildDir = build
_JarDir = $(_BuildDir)\jar

# classes directory
_ClassesDir = $(_BuildDir)\classes
_ServerDir = $(_BuildDir)\classes\server
_ClientDir = $(_BuildDir)\classes\client

## automatic generated ##
#########################
# classes
_ServerClasses := $(shell dir /B /S $(_ServerRoot)\*.java)
_ClientClasses := $(shell dir /B /S $(_ClientRoot)\*.java)

## commands ##
##############
# output directory
outputdir:
	mkdir $(_JarDir)

serverdir: outputdir
	mkdir $(_ServerDir)

clientdir: outputdir
	mkdir $(_ClientDir)

# compile output
compileserver: serverdir
	javac -d $(_ServerDir) $(_ServerClasses)

compileclient: clientdir
	javac -d $(_ClientDir) $(_ServerClasses)

# build jar
serverjar: outputdir compileserver
	jar cvfe $(_JarDir)\$(_ServerTitle).jar $(_ServerMain) -C $(_ServerDir) .

clientjar: outputdir compileclient
	jar cvfe $(_JarDir)\$(_ClientTitle).jar $(_ClientMain) -C $(_ClientDir) .

# run jar
runserver: serverjar
	java -jar $(_JarDir)\$(_ServerTitle).jar

runclient: clientjar
	java -jar $(_JarDir)\$(_ClientTitle).jar

# clean up
clean:
	rmdir /S /Q $(_BuildDir)