org 0x7c00       ; Bootloader starting address

bits 16          ; 16-bit mode for bootloader

%define ENDL 0x0D, 0x0A  ; Linefeed (CR+LF)

start:
    jmp main   ; Jump to the main function

; Function to print a string using BIOS interrupt 0x10
puts:
    push si    ; Save the SI register (string index)
    push ax    ; Save the AX register

.loop:
    lodsb      ; Load a byte from [SI] into AL and increment SI
    or al, al  ; Check if AL is 0 (end of string)
    jz .done   ; If AL is zero, we've reached the end of the string jz= Jump if Zero
    
    mov ah, 0x0e   ; BIOS teletype output function
    mov bh, 0      ; Page number (0)
    int 0x10       ; Call BIOS interrupt to display the character in AL
    jmp .loop      ; Loop to print the next character

.done:
    pop ax     ; Restore AX register
    pop si     ; Restore SI register
    ret        ; Return to caller

; Main function
main:
    mov ax, 0   ; Set up the data segment registers (use 0 for bootloader)
    mov ds, ax
    mov es, ax

    mov ss, ax  ; Set the stack segment to 0
    mov sp, 0x7C00  ; Set the stack pointer to 0x7C00 (end of boot sector)

    mov si, msg_hello  ; Load address of the message into SI
    call puts           ; Call puts to print the message

    jmp .halt            ; Jump to halt (infinite loop)

.halt:
    jmp .halt            ; Infinite loop to halt execution

; Message to be displayed
msg_hello:
    db 'Hello World!', ENDL, 0  ; The message with a newline and null terminator

; Padding to make the boot sector exactly 512 bytes
times 510-($-$$) db 0
dw 0xAA55            ; Boot sector signature (required for bootable disks)
