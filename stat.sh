#!/bin/bash
#Usage: to statisfy the number of source file and the number of source lines.
#writted by wangtao at 2014/11/11


filenum=`find ./src -name "*.java" | wc -l`
echo "The total number of src file: " $filenum

numlines1=`find ./src -name "*.java"| xargs cat | grep -v ^$ | wc -l`
echo "The total number of lines: " $numlines1

numlines2=`find ./src -name "*.java"| xargs cat| wc -l`
echo "The total number of lines including blank line: " $numlines2


