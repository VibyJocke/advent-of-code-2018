package main

import (
	"io/ioutil"
	"log"
	"strconv"
	"strings"
)

const inputFile = "input.txt"

func main() {
	frequencies := parseFrequencies()
	currentFrequency := 0
	for _, v := range frequencies {
		currentFrequency += toInt(v)
	}
	log.Println(currentFrequency)
}

func parseFrequencies() []string {
	dat, _ := ioutil.ReadFile(inputFile)
	return strings.Split(string(dat), "\n")
}

func toInt(v string) int {
	i, _ := strconv.ParseInt(v, 10, 32)
	return int(i)
}
