ASM=nasm
QEMU=qemu-system-i386

SRC_DIR=src
BUILD_DIR=build

$(BUILD_DIR)/main_floppy.img: $(BUILD_DIR)/main.bin
	cp $(BUILD_DIR)/main.bin $(BUILD_DIR)/main_floppy.img
	truncate -s 1440k $(BUILD_DIR)/main_floppy.img

$(BUILD_DIR)/main.bin: $(SRC_DIR)/main.asm
	$(ASM) $(SRC_DIR)/main.asm -f bin -o $(BUILD_DIR)/main.bin

run: $(BUILD_DIR)/main_floppy.img
	$(QEMU) -fda $(BUILD_DIR)/main_floppy.img

# Optional: 'make clean' target to remove build files
clean:
	# Remove all build files
	rm -rf $(BUILD_DIR)/*