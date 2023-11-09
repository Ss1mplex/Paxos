# Makefile for Java project in 'paxos' folder

# Java Compiler
JCC = javac

# Output directory
OUTDIR = out

# Source directory
SRCDIR = src

# Find all Java files in the src directory
SOURCES := $(wildcard $(SRCDIR)/**/*.java)

# Corresponding .class files in the OUTDIR
CLASSES := $(SOURCES:$(SRCDIR)/%.java=$(OUTDIR)/%.class)

# Default make target
all: $(CLASSES)

# Rule to compile the Java files
$(OUTDIR)/%.class: $(SRCDIR)/%.java
	@mkdir -p $(@D)
	$(JCC) -d $(OUTDIR) $<

# Clean target to remove compiled .class files
clean:
	rm -rf $(OUTDIR)/**/*.class

# Target to run the Test
test: all
	java -cp $(OUTDIR) Test

# Target to run the PaxosTest
paxostest: all
	java -cp $(OUTDIR) PaxosTest

# Phony targets
.PHONY: all clean test paxostest
