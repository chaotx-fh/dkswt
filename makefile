#################################
## detect os and set functions ##
#################################
ifeq ($(OS), Windows_NT)
	_LS = dir /B /S
	_RM = rmdir /S /Q
	_SETSEP = $(subst /,\, $(1))
else
	_LS = ls
	_RM = rm -r
	_SETSEP = $(subst \,/, $(1))
endif

############
## config ##
############
# file title
_ServerTitle = dkserver
_ClientTitle = dkclient

# source root
_ServerRoot = $(call _SETSEP, java/src/eu/zoho/chaotx/doppelkopf/server)
_ClientRoot = $(call _SETSEP, java/src/eu/zoho/chaotx/doppelkopf/client)

# main class
_ServerMain = eu.zoho.chaotx.doppelkopf.server.DKServer
_ClientMain = eu.zoho.chaotx.doppelkopf.client.DKClient

# output directory
_BuildDir = build
_JarDir = $(call _SETSEP, $(_BuildDir)/jar)

# classes directory
_ClassesDir = $(call _SETSEP, $(_BuildDir)/classes)
_ServerDir = $(call _SETSEP, $(_BuildDir)/classes/server)
_ClientDir = $(call _SETSEP, $(_BuildDir)/classes/client)

#########################
## automatic generated ##
#########################
# classes
_ServerClasses := $(shell $(_LS) $(call _SETSEP, $(_ServerRoot)/*.java))
_ClientClasses := $(shell $(_LS) $(call _SETSEP, $(_ClientRoot)/*.java))

##############
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
	jar cvfe $(call _SETSEP, $(_JarDir)/$(_ServerTitle).jar) $(_ServerMain) -C $(_ServerDir) .

clientjar: outputdir compileclient
	jar cvfe $(call _SETSEP, $(_JarDir)/$(_ClientTitle).jar) $(_ClientMain) -C $(_ClientDir) .

# run jar
runserver: serverjar
	java -jar $(call _SETSEP, $(_JarDir)/$(_ServerTitle).jar)

runclient: clientjar
	java -jar $(call _SETSEP, $(_JarDir)/$(_ClientTitle).jar)

# clean up
clean:
	$(_RM) $(_BuildDir)
