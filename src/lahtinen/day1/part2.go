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
	foundFrequencies := make([]int, 0)
	for i := 0; ; i++ {
		index := i % len(frequencies)
		currentFrequency += toInt(frequencies[index])
		if frequencyFound(foundFrequencies, currentFrequency) != -1 {
			break
		}
		foundFrequencies = append(foundFrequencies, currentFrequency)
	}

	log.Println(currentFrequency)
}

func parseFrequencies() []string {
	dat, _ := ioutil.ReadFile(inputFile)
	return strings.Split(string(dat), "\n")
}

func frequencyFound(ints []int, i int) int {
	for _, v := range ints {
		if v == i {
			return i
		}
	}
	return -1
}

func toInt(v string) int {
	i, _ := strconv.ParseInt(v, 10, 32)
	return int(i)
}
