#################################
## detect os and set functions ##
#################################
ifeq ($(OS), Windows_NT)
	_RM = rmdir /S /Q
	_MK = -mkdir $(subst /,\, $(1))
	_LS = dir /B /S $(subst /,\, $(1)/$(2))
	_PATH = $(subst /,\, $(1))
else
	_RM = rm -r
	_MK = mkdir -p $(subst \,/, $(1))
	_LS = find $(subst \,/, $(1)) -name '$(2)'
	_PATH = $(subst \,/, $(1))
endif

############
## config ##
############
# file title
_ServerTitle = dkserver
_ClientTitle = dkclient

# source root
_ServerRoot = src/eu/zoho/chaotx/doppelkopf/server
_ClientRoot = src/eu/zoho/chaotx/doppelkopf/client

# ressources
_ServerRes = res/server
_ClientRes = res/client

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
_ServerClasses := $(shell $(call _LS,$(_ServerRoot),*.java))
_ClientClasses := $(shell $(call _LS,$(_ClientRoot),*.java))

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
	jar cvfe $(call _PATH, $(_JarDir)/$(_ServerTitle).jar) $(_ServerMain) $(call _PATH, $(_ServerRes)) -C $(_ServerDir) .

clientjar: jardir compileclient
	jar cvfe $(call _PATH, $(_JarDir)/$(_ClientTitle).jar) $(_ClientMain) $(call _PATH, $(_ClientRes)) -C $(_ClientDir) .

# run jar
runserver: serverjar
	java -jar $(call _PATH, $(_JarDir)/$(_ServerTitle).jar)

runclient: clientjar
	java -jar $(call _PATH, $(_JarDir)/$(_ClientTitle).jar)

# clean up
clean:
	$(_RM) $(_BuildDir)
