#################################
## detect os and set functions ##
#################################
ifeq ($(OS), Windows_NT)
	_LS = dir /B /S
	_RM = rmdir /S /Q
	_MK = -mkdir $(subst /,\, $(1))
#	_MK = $(shell IF NOT EXIST $(1) mkdir $(subst /,\, $(1)))
	_SETSEP = $(subst /,\, $(1))
else
	_LS = ls
	_RM = rm -r
	_MK = mkdir -p $(subst /,\, $(1))
	_SETSEP = $(subst \,/, $(1))
endif

############
## config ##
############
# file title
_ServerTitle = dkserver
_ClientTitle = dkclient

# source root
_ServerRoot = java/src/eu/zoho/chaotx/doppelkopf/server
_ClientRoot = java/src/eu/zoho/chaotx/doppelkopf/client

# main class
_ServerMain = eu.zoho.chaotx.doppelkopf.server.DKServer
_ClientMain = eu.zoho.chaotx.doppelkopf.client.DKClient

# output directory
_BuildDir = build
_JarDir = $(_BuildDir)/jar

# classes directory
_ClassesDir = $(_BuildDir)/classes
_ServerDir = $(_BuildDir)/classes/server
_ClientDir = $(_BuildDir)/classes/client

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
	$(call _MK, $(_BuildDir))

jardir: outputdir
	$(call _MK, $(_JarDir))

serverdir: outputdir
	$(call _MK, $(_ServerDir))

clientdir: outputdir
	$(call _MK, $(_ClientDir))

# compile output
compileserver: serverdir
	javac -d $(_ServerDir) $(_ServerClasses)

compileclient: clientdir
	javac -d $(_ClientDir) $(_ClientClasses)

# build jar
serverjar: jardir compileserver
	jar cvfe $(call _SETSEP, $(_JarDir)/$(_ServerTitle).jar) $(_ServerMain) -C $(_ServerDir) .

clientjar: jardir compileclient
	jar cvfe $(call _SETSEP, $(_JarDir)/$(_ClientTitle).jar) $(_ClientMain) -C $(_ClientDir) .

# run jar
runserver: serverjar
	java -jar $(call _SETSEP, $(_JarDir)/$(_ServerTitle).jar)

runclient: clientjar
	java -jar $(call _SETSEP, $(_JarDir)/$(_ClientTitle).jar)

# clean up
clean:
	$(_RM) $(_BuildDir)
