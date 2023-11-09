# Makefile for the Paxos project

# Variables
JCC = javac
JAVA = java
JFLAGS = -g
SRCDIR = src/com/paxos
OUTDIR = out
MAIN_SERVER = PaxosServer
MAIN_TEST = Test
MAIN_PAXOSTEST = PaxosTest

# Find all Java files in the source directory
SOURCES := $(wildcard $(SRCDIR)/*.java)
# Determine corresponding .class files in the output directory
CLASSES := $(patsubst $(SRCDIR)/%.java,$(OUTDIR)/%.class,$(SOURCES))

# Default target
all: $(CLASSES)

# Pattern rule for compiling Java
$(OUTDIR)/%.class: $(SRCDIR)/%.java
	mkdir -p $(OUTDIR)
	$(JCC) $(JFLAGS) -cp $(OUTDIR) -d $(OUTDIR) $<

# Target to clean the build
clean:
	rm -rf $(OUTDIR)

# Target to run the server
server: $(OUTDIR)/$(MAIN_SERVER).class
	$(JAVA) -cp $(OUTDIR) com.paxos.$(MAIN_SERVER)

# Target to run the Test class
test: $(OUTDIR)/$(MAIN_TEST).class
	$(JAVA) -cp $(OUTDIR) com.paxos.$(MAIN_TEST)

# Target to run the PaxosTest class
paxostest: $(OUTDIR)/$(MAIN_PAXOSTEST).class
	$(JAVA) -cp $(OUTDIR) com.paxos.$(MAIN_PAXOSTEST)

# Phony targets
.PHONY: all clean server test paxostest

# Compile the classes
$(CLASSES): $(SOURCES)
	$(JCC) $(JFLAGS) -cp $(OUTDIR) -d $(OUTDIR) $^
