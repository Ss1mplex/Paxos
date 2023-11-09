# Define the Java compiler
JAVAC = javac

# Define the source directory
SRC_DIR = src/com/paxos

# Define the output directory
OUT_DIR = out

# Find all .java files in the source directory
SOURCES = $(wildcard $(SRC_DIR)/*.java)

# Create the corresponding .class file paths
CLASSES = $(patsubst $(SRC_DIR)/%.java,$(OUT_DIR)/%.class,$(SOURCES))

# Default target: build all .class files
all: $(CLASSES)

# Compile .java files to .class files
$(OUT_DIR)/%.class: $(SRC_DIR)/%.java
	$(JAVAC) -d $(OUT_DIR) $<

# Clean the compiled .class files
clean:
	rm -rf $(OUT_DIR)

# Run the PaxosServer
run-paxos-server:
	java -cp $(OUT_DIR) PaxosServer

# Run the Test class
run-test:
	java -cp $(OUT_DIR) Test

# Run the PaxosTest class
run-paxos-test:
	java -cp $(OUT_DIR) PaxosTest

.PHONY: all clean run-paxos-server run-test run-paxos-test
