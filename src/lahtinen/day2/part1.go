package main

import (
	"io/ioutil"
	"log"
	"strings"
)

const inputFile = "input.txt"

func main() {
	ids := parseIds(inputFile)
	numTwoLetterMatches := 0
	numThreeLetterMatches := 0

	for _, id := range ids {
		foundTwoLetter := 0
		foundThreeLetter := 0
		for _, letter := range []rune(id) {
			count := strings.Count(id, string(letter))
			if count == 2 {
				foundTwoLetter = 1
			}
			if count == 3 {
				foundThreeLetter = 1
			}
		}
		numTwoLetterMatches += foundTwoLetter
		numThreeLetterMatches += foundThreeLetter
	}

	log.Println("hash:", numTwoLetterMatches*numThreeLetterMatches)
}

func parseIds(file string) []string {
	dat, _ := ioutil.ReadFile(file)
	return strings.Split(string(dat), "\n")
}
