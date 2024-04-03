[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/BlKyKwkj)
# Checksum
### Validating an IPv4 header via the checksum values

```
check·sum | ˈCHekˌsəm |
noun
a digit representing the sum of the correct digits in a piece of stored or transmitted 
digital data, against which later comparisons can be made to detect errors in the data.
```

 
# Assignment:
In this assignment, you are to write both a Java subroutine and a MIPS subroutine that computes a simple checksum of 8-bit integers.  This program is *based* upon the calculation of the checksum value of a IPv4 header, defined by RFC791. 

This subroutine has been modified from the formal definition of the IPv4 checksum calculation to work with an 8-bit checksum as opposed to a 16-bit checksum.  

The subroutine performs the follow set of calculations:


   ```java
   max_int    = 255;

   sum               = - input_5 + summation i=0,9  input_i;
   header_checksum   =   input_5;

   quotient   = sum / (max_int + 1);
   remainder  = sum % (max_int + 1);

   checksum = max_int - ( quotient + remainder );

   result   = (header_checksum == checksum) ? 0 : checksum;
   ```

Note that "input_n" denotes the n<sup>th</sup> call to the OS to read an integer from stdin.  This call to the OS can be performed via the following Java code:

   ```java
   mips.read_d();
   input_i = mips.retval();
   ```


## Task 1:  Java "checksum.j"

### Prerequisites Task:
   1. Create a file called: checksum.j
   1. Indicate you want to add this file to your local repo:  `git add checksum.j`
   1. Commit your changes to the local repo: `git commit -m "some message"`
   1. Push your local repo to the remote repo: `git push`

### Primary Task:
Your task is to first write a Java method, "checksum", that performs the following steps.
   1. That uses a for-loop to 
      1. read 10 non-negative integers from stdin (`mips.read_d()`)
         - said numbers must be in the range of 0..max_int
         - but you may assume that the provided input values are in this range.
      1. add these 10 numbers together to form a sum
      1. save the header_checksum, which is the 6^th input value (i.e., input[5]),
   1. Updates the sum by subtracting the header_checksum.
   1. Divide, via integer division, the sum by 256 (max_int + 1) to yield:
      - the quotient
      - the remainder
   1. Calculate value of checksum via the equation provided above
   1. Compare the header_checksum with the calculated checksum
      - if they are equal, set your result to be 0 (true)
      - otherwise, set your result to be checksum

Remember: the development process is an iterative one.  Hence, you need to perform a number of "edits, test, commits".  When I grade this assignment, the minimal number of required commits will be larger than 6.  How much? a lot.

You then must test your program to validate all is well.  Here are three test cases:

   ```bash
   $ java_subroutine checksum < 156.txt
   #########################################
   # Above is the output from your program
   #########################################
   
   v0:          0; 0x00 00 00 00; 0b0000 0000 0000 0000 0000 0000 0000 0000;
     
   $ java_subroutine checksum < 229_error.txt
   #########################################
   # Above is the output from your program
   #########################################

   v0:        132; 0x00 00 00 84; 0b0000 0000 0000 0000 0000 0000 1000 0100;

   $ java_subroutine checksum < 81.txt
   #########################################
   # Above is the output from your program
   #########################################

   v0:          0; 0x00 00 00 00; 0b0000 0000 0000 0000 0000 0000 0000 0000;

   ```
You may use the make file to automate the testing of your program.

   * `make`: tests both your Java and MIPS code
   * `make java_code`: tests your Java code
   * `make mips_code`: tests your MIPS code
   * `make validate`: validates your submission

### Postrequisites Task:

If you program is correct, then you need to form the following steps.

   ```bash
   echo >> checksum.j
   echo "// Task 1 complete: $(date)" >> checksum.j
   git commit -m 'Task 1 complete' checksum.j
   git tag task_1
   git push
   git push --tags
   ```

Caution:  Make sure you program is working 100% before you perform the above steps.  If have to modify your code you will need perform the following tasks:

   ```bash
   echo >> checksum.j
   echo "// Task 1 complete: $(date)" >> checksum.j
   git commit -m 'Task 1 complete' checksum.j
   git tag --force task_1
   git push
   git push --force --tags
   ```


## Task 2: Java TAC "checksum.j"

### Primary Task:
1. Rewrite your Java program to follow the TAC convention
1. Test your Java program

### Postrequisites Task:

If you program is correct, then you need to perform the following steps.

   ```bash
   echo >> checksum.j
   echo "// Task 2 complete: $(date)" >> checksum.j
   git commit -m 'Task 2 complete' checksum.j
   git tag --force task_2
   git push
   git push --force  --tags
   ```

### Task 3: MIPS "checksum.s"

### Prerequisites Task:
   1. Copy the file "checksum.j" to "checksum.s"
   1. Edit the file "checksum.s" 
      - to comment out your Java code
      - to indent your comments to be located to the right
   1. Indicate you want to add this file to your local repo: `git add checksum.s`
   1. Commit your changes to the local repo: `git commit -m "some message"`
   1. Push your local repo to the remote repo: `git push`


### Primary Task:
  1. Via the transliteration process, modify your "checksum.s" file to be a MIPS subroutine
  1. Test your MIPS program



### Postrequisites Task:

If you program is correct, then you need to perform the following steps.

   ```bash
   echo >> checksum.s
   echo "# Task 3 complete: $(date)" >> checksum.s
   git commit -m 'Task 2 complete' checksum.s
   git tag --force task_3
   git push
   git push --force --tags
   ```

## Task 4: Validation and Final Submission
Prior to your final submission to the repository, run the `make` command to test programs.

If all is working correctly, you can now run the command `make validate`.  This command will create a file called `validation.output`.  Moreover, this file will automatically be added to your local repository.


Summary of steps for final submission:

  ```bash
  make validate
  echo >> validation.output
  echo "# Task 4 complete: $(date)" >> validation.output
  git commit -m 'Final Submission' 
  git push     
  ```

You may RESUBMIT your work any number of times prior to the due date.  But you must retag each of your sections.  (See the Caution note above.)


# Other Information and Resources:
This program is based upon the structure if a IPv4 packet.  Although you do not need to understand this structure to complete this assignment, you may want to review some of the material associated with IPv4.  More information will be provided in the lecture.
  * https://en.wikipedia.org/wiki/IPv4
  * https://en.wikipedia.org/wiki/IPv4_header_checksum

### Notes:
  * The IPv4 header utilizes a checksum field that is 16-bit quantity. This program reduces the size in half to a 8-bit quantity.  Correspondingly, the following changes are made to keep things consistent: 
  
  * The IPv4 header, without any options, has a total size of 20 bytes. This program reduces the size in half to a 10 byte size.

  * This checksum value is stored in the 11th & 12th byte of the IPv4 header. This program assigns its location to the 6th byte.


