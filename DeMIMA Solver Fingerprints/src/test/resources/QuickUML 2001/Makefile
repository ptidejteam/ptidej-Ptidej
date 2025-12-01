PREFIX		= .
CLASSES		= $(PREFIX)/classes
SOURCES		= $(PREFIX)/src
RESOURCES	= $(PREFIX)/resource
LIBS	        = $(PREFIX)/lib
META	        = $(PREFIX)/META-INF

DIAGRAM_DIRS	= diagram diagram.figures diagram.shape diagram.tool
DIAGRAM_JAR  	= Diagram.jar
DIAGRAM_MF	= $(META)/diagram.mf
DIAGRAM_VERSION = 1.0.1

QUICKUML_DIRS 	= uml uml.builder uml.diagram uml.ui util acme
QUICKUML_JAR  	= QuickUML.jar
QUICKUML_MF 	= $(META)/QuickUML.mf
QUICKUML_VERSION = 1.3.4

empty:=
space:=$(empty) $(empty)

## -------------------------------------------------------------------

JAVAC           = javac #jikes
JAVAC_FLAGS     = #-Xdepend
JAR             = jar
JAR_FLAGS       = cmf
DOXYGEN		= doxygen

## -------------------------------------------------------------------

## Diagram dependancies
DIAGRAM_DIRS 	:= $(foreach pkg,$(subst .,/,$(DIAGRAM_DIRS)),$(SOURCES)/$(pkg))
DIAGRAM_SRCS	:= $(foreach dir, $(DIAGRAM_DIRS), $(wildcard $(dir)/*.java))
DIAGRAM_OBJS	:= $(foreach src, $(DIAGRAM_SRCS), $(subst $(SOURCES),$(CLASSES),$(src:.java=.class)))

## QuickUML dependancies
QUICKUML_DIRS 	:= $(foreach pkg,$(subst .,/,$(QUICKUML_DIRS)),$(SOURCES)/$(pkg))
QUICKUML_SRCS	:= $(foreach dir, $(QUICKUML_DIRS), $(wildcard $(dir)/*.java))
QUICKUML_OBJS	:= $(foreach src, $(QUICKUML_SRCS), $(subst $(SOURCES),$(CLASSES),$(src:.java=.class)))

## Resources 
RESOURCE_DIRS	:= $(foreach dir,$(RESOURCES),$(dir))
RESOURCE_OBJS	:= *.gif
RESOURCE_OBJS	:= $(foreach dir, $(RESOURCES_DIRS), \
			$(wildcard $(foreach file, $(RESOURCE_OBJS), \
			$(dir)/$(file))))

## Clean classes (including escaped '$ in class files) 
CLEAN_DIRS	:= $(DIAGRAM_DIRS) $(QUICKUML_DIRS)
CLEAN_OBJS	:= $(subst $(SOURCES),$(CLASSES),$(CLEAN_DIRS))
CLEAN_OBJS	:= $(subst $$,\$$,$(foreach dir, $(CLEAN_OBJS), $(wildcard $(dir)/*.class))) $(LIBS)/*

## Compiling classpath
JAVAC_CLASSPATH_DIRS = src
JAVAC_CLASSPATH := $(subst $(space),,$(foreach dir, $(JAVAC_CLASSPATH_DIRS),:$(dir)))

## WIN32 needs a different classpath ;'s and \'s
ifeq "$(SHELL)" "sh.exe"
JAVAC_CLASSPATH:=$(subst /,\,$(JAVAC_CLASSPATH))
JAVAC_CLASSPATH:=$(subst :,;,$(JAVAC_CLASSPATH))
JAVAC_CLASSPATH:="$(CLASSPATH);$(JAVAC_CLASSPATH)"
else
JAVAC_CLASSPATH:=$(CLASSPATH):$(JAVAC_CLASSPATH)
endif

## Archives
DIAGRAM_JAR	:= $(LIBS)/$(subst $(space),,$(subst .jar,-$(DIAGRAM_VERSION).jar, $(DIAGRAM_JAR)))
QUICKUML_JAR	:= $(LIBS)/$(subst $(space),,$(subst .jar,-$(QUICKUML_VERSION).jar, $(QUICKUML_JAR)))

## -------------------------------------------------------------------
## Rules

$(CLASSES)/%.class: $(SOURCES)/%.java
	$(JAVAC) $(JAVAC_FLAGS) -d $(CLASSES) -classpath $(JAVAC_CLASSPATH) $<


## -------------------------------------------------------------------
## Targets

.PHONY: jar clean 

all:	$(DIAGRAM_JAR) $(QUICKUML_JAR)

diagram::	$(DIAGRAM_JAR)
	@echo $(DIAGRAM_JAR) has been successfully compiled

quickuml::	$(QUICKUML_JAR)
	@echo $(QUICKUML_JAR) has been successfully compiled

doc::
	$(DOXYGEN) doc/diagram.doxygen

help::
	@echo "Usage: make {all|diagram|quickuml|doc|clean}"
	@echo ${SHELL}

## Clean
ifeq "$(SHELL)" "sh.exe"
clean::
## Add a clean for win32 sometime
	@echo "not implemented on Win32 yet"
else
clean::
	$(RM) $(CLEAN_OBJS)
endif

## Dist
ifeq "$(SHELL)" "sh.exe"
dist::
else
dist::
endif

## Build the JAR files
ifeq "$(SHELL)" "sh.exe"
## Quote the files for win32  & convert the /'s
$(DIAGRAM_JAR):	$(DIAGRAM_OBJS) 
	$(JAR) $(JAR_FLAGS) $(DIAGRAM_MF) "$(subst /,\,$(DIAGRAM_JAR))" -C $(CLASSES) diagram -C $(CLASSES) util
else
$(DIAGRAM_JAR):	$(DIAGRAM_OBJS) 
	$(JAR) $(JAR_FLAGS) $(DIAGRAM_MF) $(DIAGRAM_JAR) -C $(CLASSES) diagram -C $(CLASSES) util
endif

ifeq "$(SHELL)" "sh.exe"
## Quote the files for win32  & convert the /'s
$(QUICKUML_JAR):	$(QUICKUML_OBJS) $(DIAGRAM_OBJS) 
	$(JAR) $(JAR_FLAGS) $(QUICKUML_MF) "$(subst /,\,$(QUICKUML_JAR))" -C $(CLASSES) . -C $(RESOURCES) .
else
$(QUICKUML_JAR):	$(QUICKUML_OBJS) $(DIAGRAM_OBJS)
	$(JAR) $(JAR_FLAGS) $(QUICKUML_MF) $(QUICKUML_JAR) -C $(CLASSES) . -C $(RESOURCES) .
endif