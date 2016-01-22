#! /bin/sh

while read line
do
	for file in *.json
	do
		name="`echo $file | sed "s/template/$line\_brick/g"`"
		sed "s/template/$line/g" $file > "brick/$name"
	done
done < liste
