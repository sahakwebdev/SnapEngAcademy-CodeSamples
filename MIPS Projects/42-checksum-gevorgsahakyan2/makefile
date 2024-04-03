COMMITS=$(shell git log --oneline | wc -l)
MIN_COMMITS=12

SRC=checksum.j checksum.s


all: java_code mips_code

java_code: checksum.j
	java_subroutine checksum < 156.txt
	java_subroutine checksum < 229_error.txt
	java_subroutine checksum < 81.txt
	touch java_code

mips_code: checksum.s
	mips_subroutine checksum < 156.txt
	mips_subroutine checksum < 229_error.txt
	mips_subroutine checksum < 81.txt
	touch mips_code

validate: $(SRC) tags number_commits
	-make -k  all > validation.output 2>&1
	-git add validation.output
	-git commit -m 'Auto adding validation step' validation.output
	echo Validation File has been committed if there were changes


# Currently, the number of commits does not work on the server side
# the log file only shows the most recent entry -- 
# not sure why or what the work around is.
number_commits:
	@-test ! $(COMMITS) -lt $(MIN_COMMITS) || \
	  { echo "Not enough commits" && false ; } 

tags:
	@-git tag | grep -q -e "task_1"  || echo "Missing task_1 tag"
	@-git tag | grep -q -e "task_2"  || echo "Missing task_2 tag"
	@-git tag | grep -q -e "task_3"  || echo "Missing task_3 tag"










