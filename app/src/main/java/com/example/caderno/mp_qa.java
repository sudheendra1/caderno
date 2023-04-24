package com.example.caderno;

public class mp_qa {

    public static String questions[]={
            "Which of the following is correct about 8086 microprocessor?",
            "Which of the following is a type of microprocessor?",
            "The microprocessor of a computer can operate on any information if it is present in ______________ only.",
            "Which of the following addressing method does the instruction, MOV AX,[BX] represent?",
            "What is the word length of an 8-bit microprocessor?",
            "Which of the following is not true about the address bus?",
            "Which of the following is the correct sequence of operations in a microprocessor?",
            "Which of the following is not a property of TRAP interrupt in microprocessor?",
            "Which of the following flag is used to mask INTR interrupt?",
            "Which of the following circuit is used as a special signal to demultiplex the address bus and data bus?"
    };

    public static String choices[][]={

            {"Intel’s first x86 processor","Motrola’s first x86 processor","STMICROELECTRONICS’s first x86 processor","NanoXplore x86 processor"},
            {"CISC","RISC","EPIC","All of the mentioned"},
            {"Program Counter","Flag","Main Memory","Secondary Memory"},
            {"register indirect addressing mode","direct addressing mode","register addressing mode","register relative addressing mode"},
            {"8-bits – 64 bits","4-bits – 32 bits"," 8-bits – 16 bits","8-bits – 32 bits"},
            {"It consists of control PIN 21 to 28","It is a bidirectional bus","It is 16 bits in length","Lower address bus lines (AD0 – AD7) are called “Line number”"},
            {"Opcode fetch, memory read, memory write, I/O read, I/O write","Opcode fetch, memory write, memory read, I/O read, I/O write","I/O read, opcode fetch, memory read, memory write, I/O write","I/O read, opcode fetch, memory write, memory read, I/O write"},
            {"It is a non-maskable interrupt"," It is of highest priority","It uses edge-triggered signal","It is a vectored interrupt"},
            {"zero flag","auxiliary carry flag flag","interrupt flag","sign flag"},
            {"Priority Encoder","Decoder","Address Latch Enable","Demultiplexer"}
    };

    public static String correctAnswers[]={
            "Intel’s first x86 processor",
            "All of the mentioned",
            "Main Memory",
            "register indirect addressing mode",
            "8-bits – 64 bits",
            "It is a bidirectional bus",
            "Opcode fetch, memory read, memory write, I/O read, I/O write",
            "It uses edge-triggered signal",
            "interrupt flag",
            "Address Latch Enable"

    };
}
