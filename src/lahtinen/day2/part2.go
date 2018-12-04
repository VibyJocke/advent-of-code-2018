package main

import (
	"io/ioutil"
	"log"
	"strings"
)

const inputFile = "input.txt"

func main() {
	ids := parseIds(inputFile)

	for _, id1 := range ids {
		for _, id2 := range ids {
			letters := numIdenticalLetters([]rune(id1), []rune(id2))
			if len(letters) == len(id1)-1 {
				log.Println("found", string(letters))
				return
			}
		}
	}
}

func parseIds(file string) []string {
	dat, _ := ioutil.ReadFile(file)
	return strings.Split(string(dat), "\n")
}

func numIdenticalLetters(id1 []rune, id2 []rune) []rune {
	result := make([]rune, 0)

	for i, v := range id1 {
		if v == id2[i] {
			result = append(result, v)
		}
	}

	return result
}
