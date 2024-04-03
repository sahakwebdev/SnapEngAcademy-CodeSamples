COMMITS=$(shell git log --oneline | wc -l)
MIN_COMMITS=20
SHELL=/bin/bash

J_SRC=binaryReal.j strtofrac.j strtol.j whole2bin.j fractional2bin.j
M_SRC=binaryReal.s strtofrac.s strtol.s whole2bin.s fractional2bin.s


ARG_CASE1=10 "\#"  1234 "." 4321
OUTPUT_CASE1="2\# 10011010010.01101110100111100001101"
RESULT_CASE1="10"

ARG_CASE2=16 "\#"  FACE "." DEAF
OUTPUT_CASE2="2\# 1111101011001110.11011110101010110011011"
RESULT_CASE2="16"

ARG_CASE3=8 "\#"  1234 "." 43
OUTPUT_CASE3="2\# 1010011100.10001010001111010111000"
RESULT_CASE3=" 8"

ARG_CASE4=8 "\#"  1234 "." 4300000
OUTPUT_CASE4="2\# 1010011100.100011"
RESULT_CASE4=" 8"


test: tags number_commits
	@echo 
	@echo \"make test_java\" to test your current version of binaryReal.j
	@echo \"make test_mips\" to test your current version of binaryReal.s
	@echo \"make final\" to test all your final versions of binaryReal.\*
	@echo \"make validate\" to validate your final submission
	@echo 
	@echo Make sure you are in the correct directory when you type the above commands

test_java: $(J_SRC)
	@echo $(OUTPUT_CASE1) "<- Correct output"
	@java_subroutine -L '*.j' -S binaryReal $(ARG_CASE1)
	@echo "Correct V0:" $(RESULT_CASE1)
	@echo
	@echo $(OUTPUT_CASE2) "<- Correct output"
	@java_subroutine -L '*.j' -S binaryReal $(ARG_CASE2)
	@echo "Correct V0:" $(RESULT_CASE2)
	@echo
	@echo $(OUTPUT_CASE3) "<- Correct output"
	@java_subroutine -L '*.j' -S binaryReal $(ARG_CASE3)
	@echo "Correct V0:" $(RESULT_CASE3)
	@echo
	@echo $(OUTPUT_CASE4) "<- Correct output"
	@java_subroutine -L '*.j' -S binaryReal $(ARG_CASE4)
	@echo "Correct V0:" $(RESULT_CASE4)
	@echo

test_mips: $(M_SRC)
	@echo $(OUTPUT_CASE1) "<- Correct output"
	@mips_subroutine -L '*.s' -S binaryReal $(ARG_CASE1)
	@echo "Correct V0:" $(RESULT_CASE1)
	@echo
	@echo $(OUTPUT_CASE2) "<- Correct output"
	@mips_subroutine -L '*.s' -S binaryReal $(ARG_CASE2)
	@echo "Correct V0:" $(RESULT_CASE2)
	@echo
	@echo $(OUTPUT_CASE3) "<- Correct output"
	@mips_subroutine -L '*.s' -S binaryReal $(ARG_CASE3)
	@echo "Correct V0:" $(RESULT_CASE3)
	@echo
	@echo $(OUTPUT_CASE4) "<- Correct output"
	@mips_subroutine -L '*.s' -S binaryReal $(ARG_CASE4)
	@echo "Correct V0:" $(RESULT_CASE4)
	@echo


final: final_java_code final_java_tac_code final_mips_code
	@git checkout main


final_java_code:
	@git checkout java_code 2> /dev/null || { echo "Error Unable to checkout java_code" ; false ; }
	cd java ; make test_java ; cd ..
	@git checkout main

final_java_tac_code:
	@git checkout java_tac_code 2> /dev/null || { echo "Error Unable to checkout java_tac_code" ; false ; }
	cd java_tac ; make test_java ; cd ..
	@git checkout main


final_mips_code:
	@git checkout mips_code  2> /dev/null || { echo "Error Unable to checkout mips_code" ; false ; }
	cd mips ; make test_mips ; cd ..
	@git checkout main



validate: tags number_commits
	-make -k final > validation.tmp 2>&1
	@mv validation.tmp validation.output
	@git add validation.output
	@git commit -m 'Auto-commit: validation step' validation.output >/dev/null 2>&1 
	@echo Validation File has been committed if there were changes



# Currently, the number of commits does not work on the server side
# the log file only shows the most recent entry -- 
# not sure why or what the work around is.
number_commits:
	@-test ! $(COMMITS) -lt $(MIN_COMMITS) || \
	  { echo You need a minimum of $(MIN_COMMITS) commits && false ; } 

tags:
	@-git tag | grep -q -e "java_code"      || echo "Missing java_code tag"
	@-git tag | grep -q -e "java_tac_code"  || echo "Missing java_tac_code tag"
	@-git tag | grep -q -e "mips_code"      || echo "Missing mips_code tag"

java_code:
	@-git tag | grep -q -e "java_code"      || echo "Missing java_code tag"

java_tac_code:
	@-git tag | grep -q -e "java_tac_code"  || echo "Missing java_tac_code tag"

mips_code:	
	@-git tag | grep -q -e "mips_code"      || echo "Missing mips_code tag"


#  The following section is the code the prof will use to determine
#    - what he will and what he will not grade.
#  This section is left here for transparency.
#  His criteria for grading for a particular assignment may change!
#
#  At very most, he will grade 
#    - only material that is submitted by the due_date
#      * unless prior arrangements have been made
#    - a task based upon the point in time in which you asserted is done
#      * by virtue of appropriate tagging

# Create a set of "list of students" to be graded based upon a set of criteria
pre_grade:
	bash -lc 'checkout_due_date'
	bash -lc 'meets_criteria "git tag --list java_code --merged     | grep java_code" '     | sort >criteria_task_1
	bash -lc 'meets_criteria "git tag --list java_tac_code --merged | grep java_tac_code" ' | sort >criteria_task_2
	bash -lc 'meets_criteria "git tag --list mips_code --merged     | grep java_mips_code"' | sort >criteria_task_3
	bash -lc 'meets_criteria "ls validation.output" ' | sort >criteria_validate
	sort ${CLASS_ROSTER} > master_list
	comm -23 master_list criteria_task_1       > No_Criteria_met
	comm -23 criteria_task_1 criteria_task_2   > task_1_complete
	comm -23 criteria_task_2 criteria_task_3   > task_2_complete
	comm -23 criteria_task_3 criteria_validate > task_3_complete

# Use the TO_GRADE variable to restate what to grade
grade: $(TO_GRADE)

grade_all: grade_java_code grade_java_tac_code grade_mips_code grade_validation

grade_java_code: final_java_code
	git checkout java_code
	cd java ; subl $(JSRC) ; cd ..
	subl grade.report
	git checkout main

grade_java_tac_code: grade_java_code final_java_tac_code
	git checkout java_tac_code
	cd java_tac ; subl $(JSRC) ; cd ..
	git checkout main

grade_mips_code: grade_java_tac_code final_mips_code
	cd mips ; subl (MSRC) ; cd ..
	git checkout main

grade_validate: grade_mips_code
	git checkout grading_information
	subl validation.output
	git checkout main

