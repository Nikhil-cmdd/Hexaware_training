function countVowels(str) {
    return (str.match(/[aeiou]/gi) || []).length;
}
console.log("Vowels in 'hello world':", countVowels("hello world"));
