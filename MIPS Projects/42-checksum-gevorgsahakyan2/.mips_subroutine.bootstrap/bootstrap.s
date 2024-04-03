                .data



subroutine:     .asciiz "checksum"
                # checksum()
                # Register Dump:   

return_value:   .word 0

                # Layout the command line arguments 
                .align 2
argc:           .word  0
argv:           .word 
                .align 2
saved_sp:       .word 0
                .align 2
saved_float:    .float 1.0
                .align 3
saved_double:   .double 1.0

                .text
                .globl main

main:           nop      

                # Set the T registers to be random values
                li $t0, 4814
                li $t1, 18739
                li $t2, 29215
                li $t3, 28825
                li $t4, 12388
                li $t5, 29379
                li $t6, 18770
                li $t7, 21076
                li $t8, 7914
                li $t9, 31393

                # Set the S registers to 0xDeadBeef
                li $s0, 0xDeadBeef
                li $s1, 0xDeadBeef
                li $s2, 0xDeadBeef
                li $s3, 0xDeadBeef
                li $s4, 0xDeadBeef
                li $s5, 0xDeadBeef
                li $s6, 0xDeadBeef
                li $s7, 0xDeadBeef

                # Set the FP and GP registers
                li $fp, 0xDeadBeef
                li $gp, 0xDeadBeef

                # Save the SP registers
                sw $sp, saved_sp

                 # Marshal the input arguments into the registers

                # Marshal the remaining input arguments onto the stack
                # Make a call to the user's subroutine
                jal checksum


                # If we made it here, then all registers that 
                # should have been preserved over the subroutine
                # boundary should be set to 0xDeadBeef;   
                # except $ra and $sp.

                # If we are here than set $ra to 0xDead Beef
                li $ra, 0xDeadBeef

                # If the SP value is what it was prior to the
                #   "jal checksum"
                # then set it to be 0xDeadBeef
                lw $at, saved_sp
                bne $at, $sp, skip
                  li $sp, 0xDeadBeef
        skip:   nop

                # Save the return value from 
                sw $v0, return_value

                # Print a blank line after the users output
                li $a0, '\n'
                li $v0, 11
                syscall

                # Save the return value from 
                lw $v0, return_value

                # Print the integer in $v0
                move $a0, $v0
                li $v0, 1
                syscall
                
                # Exit 0
                li $v0, 10
                syscall

